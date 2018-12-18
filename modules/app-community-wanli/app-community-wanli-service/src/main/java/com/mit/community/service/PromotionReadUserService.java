package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.PromotionReadUser;
import com.mit.community.mapper.PromotionReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销已读业务处理层
 * @author Mr.Deng
 * @date 2018/12/18 15:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class PromotionReadUserService {
    @Autowired
    private PromotionReadUserMapper promotionReadUserMapper;

    /**
     * 添加促销已读信息
     * @param promotionReadUser 促销已读信息
     * @author Mr.Deng
     * @date 16:01 2018/12/18
     */
    public void save(PromotionReadUser promotionReadUser) {
        promotionReadUser.setGmtCreate(LocalDateTime.now());
        promotionReadUser.setGmtModified(LocalDateTime.now());
        promotionReadUserMapper.insert(promotionReadUser);
    }

    /**
     * 查询某个促销活动的浏览量
     * @param promotionId 促销id
     * @return 浏览量
     * @author Mr.Deng
     * @date 16:05 2018/12/18
     */
    public Integer countByUserIdAndPromotionId(Integer promotionId) {
        EntityWrapper<PromotionReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("promotion_id", promotionId);
        return promotionReadUserMapper.selectCount(wrapper);
    }

    /**
     * 查询促销活动已读信息，通过用户id和促销id
     * @param userId      用户id
     * @param promotionId 促销id
     * @return 促销已读信息
     * @author Mr.Deng
     * @date 16:06 2018/12/18
     */
    public PromotionReadUser getByUserIdAndPromotionId(Integer userId, Integer promotionId) {
        EntityWrapper<PromotionReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("promotion_id", promotionId);
        List<PromotionReadUser> promotionReadUsers = promotionReadUserMapper.selectList(wrapper);
        if (promotionReadUsers.isEmpty()) {
            return null;
        }
        return promotionReadUsers.get(0);
    }
}
