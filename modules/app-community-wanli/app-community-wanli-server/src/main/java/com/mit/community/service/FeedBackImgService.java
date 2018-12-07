package com.mit.community.service;

import com.mit.community.entity.FeedBackImg;
import com.mit.community.mapper.FeedBackImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 反馈图片业务处理层
 * @author Mr.Deng
 * @date 2018/12/6 18:05
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class FeedBackImgService {
    @Autowired
    private FeedBackImgMapper feedBackImgMapper;

    /**
     * 添加反馈图片
     * @param feedBackImg 反馈图片信息
     * @return 添加成功数
     * @author Mr.Deng
     * @date 18:07 2018/12/6
     */
    public Integer save(FeedBackImg feedBackImg) {
        feedBackImg.setGmtCreate(LocalDateTime.now());
        feedBackImg.setGmtModified(LocalDateTime.now());
        return feedBackImgMapper.insert(feedBackImg);
    }

}
