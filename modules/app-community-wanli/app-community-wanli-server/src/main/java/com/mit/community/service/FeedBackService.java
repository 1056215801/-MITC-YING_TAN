package com.mit.community.service;

import com.mit.community.entity.FeedBack;
import com.mit.community.entity.FeedBackImg;
import com.mit.community.mapper.FeedBackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 反馈表业务处理层
 * @author Mr.Deng
 * @date 2018/12/6 18:04
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class FeedBackService {
    @Autowired
    private FeedBackMapper feedBackMapper;

    @Autowired
    private FeedBackImgService feedBackImgService;

    /**
     * 添加反馈信息
     * @param feedBack 反馈信息
     * @return 添加成功数
     * @author Mr.Deng
     * @date 18:06 2018/12/6
     */
    public Integer save(FeedBack feedBack) {
        feedBack.setGmtCreate(LocalDateTime.now());
        feedBack.setGmtModified(LocalDateTime.now());
        return feedBackMapper.insert(feedBack);
    }

    /**
     * 提交反馈意见
     * @param title     标题
     * @param content   反馈内容
     * @param type      类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题
     * @param userId    用户id。关联user表id
     * @param imageUrls 图片地址列表
     * @author Mr.Deng
     * @date 18:18 2018/12/6
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitFeedBack(String title, String content, String type, Integer userId, List<String> imageUrls) {
        FeedBack feedBack = new FeedBack(title, content, type, userId);
        this.save(feedBack);
        if (!imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                FeedBackImg feedBackImg = new FeedBackImg(feedBack.getId(), imageUrl);
                feedBackImgService.save(feedBackImg);
            }
        }
    }

}
