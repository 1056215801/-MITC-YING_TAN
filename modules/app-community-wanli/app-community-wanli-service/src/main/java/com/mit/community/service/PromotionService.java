package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Promotion;
import com.mit.community.entity.PromotionContent;
import com.mit.community.entity.PromotionReadUser;
import com.mit.community.mapper.PromotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 查询所有的促销信息
     * @param userId        用户id
     * @param communityCode 小区code
     * @return 促销信息
     * @author Mr.Deng
     * @date 17:25 2018/12/18
     */
    public List<Promotion> listAll(Integer userId, String communityCode) {
        List<Promotion> promotions = this.list(communityCode);
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
        return promotions;
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
            Integer integer = promotionReadUserService.countByUserIdAndPromotionId(promotion.getId());
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
}
