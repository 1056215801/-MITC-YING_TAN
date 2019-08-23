package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.FeedBack;
import com.mit.community.entity.FeedBackImg;
import com.mit.community.entity.Test;
import com.mit.community.entity.WebFeedBack;
import com.mit.community.mapper.FeedBackMapper;
import com.mit.community.mapper.WebFeedBackMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 反馈表业务处理层
 *
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

    @Autowired
    private WebFeedBackMapper webFeedBackMapper;

    /**
     * 添加反馈信息
     *
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
     *
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

    /**
     * 查询所有意见反馈信息
     *
     * @param communityCode
     * @param status
     * @param gmtCreateTimeStart
     * @param gmtCreateTimeEnd
     * @param pageNum
     * @param pageSize
     * @return 反馈的信息、反馈人个人信息
     */
    public Page<WebFeedBack> listPage(String communityCode, String status,
                                      LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd,
                                      Integer pageNum, Integer pageSize) {
        EntityWrapper<WebFeedBack> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("c.community_code", communityCode);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("a.status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("a.gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("a.gmt_create", gmtCreateTimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        Page<WebFeedBack> page = new Page<>(pageNum, pageSize);
        List<WebFeedBack> webFeedBacks = webFeedBackMapper.selectTestPage(page, wrapper);
        page.setRecords(webFeedBacks);
        return page;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/16 9:17
     * @Company mitesofor
     * @Description:~处理反馈意见
     */
    public String manage(Integer id, Integer handler, Integer status, String remark) {
        String msg = "";
        try {
            EntityWrapper<WebFeedBack> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            WebFeedBack feedBack = webFeedBackMapper.selectList(wrapper).get(0);
            if (feedBack.getReceiverTime() != null) {
                webFeedBackMapper.updateFeedback(status, handler, null, remark, id);
            } else {
                webFeedBackMapper.updateFeedback(status, handler, new Date(), remark, id);
            }
            msg = "success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "fail";
        }
        return msg;
    }

    /*public void receive(Integer id, String receiverName) {
        WebFeedBack webFeedBack = this.getById(id);
        webFeedBack.setStatus("acceptance");
        webFeedBack.setReceiverTime(LocalDateTime.now());
        webFeedBack.setReceiver(receiverName);
        this.update(webFeedBack);
    }*/

    /**
     * 查询所有意见反馈信息
     * @param communityCode
     * @param status
     * @param gmtCreateTimeStart
     * @param gmtCreateTimeEnd
     * @param pageNum
     * @param pageSize
     * @return 反馈的信息、反馈人个人信息
     */
    /*public Page<Test> listTestPage(String communityCode, String status, LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        EntityWrapper<Test> wrapper = new EntityWrapper<>();
        //wrapper.setSqlSelect("a.id,a.content,a.gmt_create,a.status,b.cellphone as feedBackMoblie,c.household_name as feedBackName,d.community_name as communityName,d.zone_name as zoneName,d.building_name as buildingName,d.unit_name as unitName,d.room_num as roomNum");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("c.community_code", communityCode);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("a.status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("a.gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("a.gmt_create", gmtCreateTimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        Page<Test> page = new Page<>(pageNum, pageSize);
        List<Test> webFeedBacks = webFeedBackMapper.selectTestPage(page, wrapper);
        page.setRecords(webFeedBacks);
        return page;
    }*/


}
