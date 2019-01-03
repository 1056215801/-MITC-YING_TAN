package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Promotion;
import com.mit.community.entity.PromotionContent;
import com.mit.community.entity.PromotionReadUser;
import com.mit.community.mapper.PromotionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销业务处理表
 * @author Mr.Deng
 * @date 2018/12/18 15:56
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class PromotionService {
    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private PromotionContentService promotionContentService;
    @Autowired
    private PromotionReadUserService promotionReadUserService;

    /**
     * 查询所有的促销活动 ，通过小区code
     * @param communityCode 小区code
     * @return
     * @author Mr.Deng
     * @date 16:21 2018/12/18
     */
    public List<Promotion> list(String communityCode) {
        EntityWrapper<Promotion> wrapper = new EntityWrapper<>();
        wrapper.orderBy("gmt_create", false);
        wrapper.eq("community_code", communityCode);
        return promotionMapper.selectList(wrapper);
    }

    /**
     * 查询所有的促销活动 ，通过小区code
     * @param communityCode 小区code
     * @return
     * @author Mr.Deng
     * @date 16:21 2018/12/18
     */
    public Page<Promotion> listPage(Integer userId, String communityCode, Integer pageNum, Integer pageSize) {
        EntityWrapper<Promotion> wrapper = new EntityWrapper<>();
        wrapper.orderBy("gmt_create", false);
        wrapper.eq("community_code", communityCode);
        Page<Promotion> page = new Page<>(pageNum, pageSize);
        List<Promotion> promotions = promotionMapper.selectPage(page, wrapper);
        if (!promotions.isEmpty()) {
            for (Promotion promotion : promotions) {
                //查询状态
                String promotionStatus = getPromotionStatus(promotion.getStartTime(), promotion.getEndTime());
                promotion.setPromotionStatus(promotionStatus);
                //查询已读
                List<PromotionReadUser> promotionReadUsers = promotionReadUserService.getByUserIdAndPromotionId(userId, promotion.getId());
                if (promotionReadUsers.isEmpty()) {
                    promotion.setReadStatus(false);
                } else {
                    promotion.setReadStatus(true);
                }
            }
        }
        page.setRecords(promotions);
        return page;
    }

    /**
     * 查询促销信息，通过id
     * @param promotionId 促销id
     * @return 促销信息
     * @author Mr.Deng
     * @date 17:29 2018/12/18
     */
    public Promotion getByPromotionId(Integer promotionId) {
        return promotionMapper.selectById(promotionId);
    }

    /**
     * 查询促销信息详情信息，通过促销id
     * @param promotionId 促销id
     * @return 促销信息
     * @author Mr.Deng
     * @date 17:34 2018/12/18
     */
    public Promotion getPromotionInfo(Integer promotionId) {
        Promotion promotion = this.getByPromotionId(promotionId);
        if (promotion != null) {
            String promotionStatus = getPromotionStatus(promotion.getStartTime(), promotion.getEndTime());
            promotion.setPromotionStatus(promotionStatus);
            PromotionContent promotionContent = promotionContentService.getByPromotionId(promotionId);
            if (promotionContent != null) {
                promotion.setContent(promotionContent.getContent());
            }
            Integer integer = promotionReadUserService.countByPromotionId(promotion.getId());
            promotion.setReadNum(integer);
        }
        return promotion;
    }

    /**
     * 判断当前活动
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     * @return 活动状态
     * @author Mr.Deng
     * @date 17:09 2018/12/18
     */
    private static String getPromotionStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        if (nowTime.isBefore(startTime)) {
            return "未开始";
        }
        if (nowTime.isAfter(endTime)) {
            return "已结束";
        }
        return "进行中";
    }

    /**
     * 保存
     * @param promotionType    促销累并
     * @param title            标题
     * @param imgUrl           图片url
     * @param issuer           发布人
     * @param issuerPhone      发布手机号
     * @param promotionAddress 促销地址
     * @param issueTime        发布时间
     * @param discount         折扣
     * @param activityContent  活动内容
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param communityCode    小区code
     * @param content          内容
     * @author shuyy
     * @date 2018/12/27 14:12
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String promotionType, String title, String imgUrl, String issuer, String issuerPhone,
                     String promotionAddress, LocalDateTime issueTime, Float discount, String activityContent,
                     LocalDateTime startTime, LocalDateTime endTime,
                     String communityCode, String content) {
        Promotion promotion = new Promotion(promotionType,
                title, imgUrl, issuer, issuerPhone,
                promotionAddress, issueTime,
                discount, activityContent,
                startTime, endTime, communityCode, null,
                null, null, 0);
        promotion.setGmtCreate(LocalDateTime.now());
        promotion.setGmtModified(LocalDateTime.now());
        promotionMapper.insert(promotion);
        PromotionContent promotionContent = new PromotionContent(promotion.getId(), content);
        promotionContentService.save(promotionContent);
    }

    /**
     * 更新
     * @param id
     * @param promotionType    促销累并
     * @param title            标题
     * @param imgUrl           图片url
     * @param issuer           发布人
     * @param issuerPhone      发布手机号
     * @param promotionAddress 促销地址
     * @param issueTime        发布时间
     * @param discount         折扣
     * @param activityContent  活动内容
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param content          内容
     * @author shuyy
     * @date 2018/12/27 14:12
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String promotionType, String title, String imgUrl, String issuer, String issuerPhone,
                       String promotionAddress, LocalDateTime issueTime, Float discount, String activityContent,
                       LocalDateTime startTime, LocalDateTime endTime, String content) {
        Promotion promotion = new Promotion(promotionType,
                title, imgUrl, issuer, issuerPhone,
                promotionAddress, issueTime,
                discount, activityContent,
                startTime, endTime, null, null,
                null, null, 0);
        promotion.setId(id);
        promotion.setGmtModified(LocalDateTime.now());
        promotionMapper.updateById(promotion);
        if (StringUtils.isNotBlank(content)) {
            PromotionContent promotionContent = new PromotionContent(id, content);
            promotionContentService.update(promotionContent);
        }
    }

    /**
     * 删除
     * @param id
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/27 14:37
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        promotionMapper.deleteById(id);
        promotionContentService.removeByPromotionId(id);
    }

    /**
     * 分页查询
     * @param communityCode  小区code
     * @param promotionType  促销类型
     * @param title          标题
     * @param issuer         发布人
     * @param issuerPhone    发布手机号
     * @param issueTimeStart 发布开始时间
     * @param issueTimeEnd   发布结束时间
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param pageNum        当前页
     * @param pageSize       分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.Promotion>
     * @throws
     * @author shuyy
     * @date 2018/12/27 14:47
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public Page<Promotion> listPage(String communityCode, String promotionType, String title, String issuer, String issuerPhone,
                                    LocalDateTime issueTimeStart, LocalDateTime issueTimeEnd,
                                    LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        Page<Promotion> page = new Page<>(pageNum, pageSize);
        EntityWrapper<Promotion> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(promotionType)) {
            wrapper.eq("promotion_type", promotionType);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.eq("title", title);
        }
        if (StringUtils.isNotBlank(issuer)) {
            wrapper.eq("issuer", issuer);
        }
        if (StringUtils.isNotBlank(issuerPhone)) {
            wrapper.eq("issuer_phone", issuerPhone);
        }
        if (issueTimeStart != null) {
            wrapper.ge("issue_time", issueTimeStart);
        }
        if (issueTimeEnd != null) {
            wrapper.le("issue_time", issueTimeEnd);
        }
        if (startTime != null) {
            wrapper.ge("start_time", startTime);
        }
        if (endTime != null) {
            wrapper.le("end_time", endTime);
        }
        List<Promotion> promotions = promotionMapper.selectPage(page, wrapper);
        page.setRecords(promotions);
        return page;
    }

    /**
     * 统计未读数
     * @param communityCode
     * @param userId
     * @return java.lang.Integer
     * @throws
     * @author shuyy
     * @date 2019-01-02 16:04
     * @company mitesofor
    */
    public Integer countNotReadNum(String communityCode, Integer userId){
        EntityWrapper<Promotion> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        Integer num = promotionMapper.selectCount(wrapper);
        Integer notNum = promotionReadUserService.countByUserId(userId);
        return num - notNum;
    }

}
