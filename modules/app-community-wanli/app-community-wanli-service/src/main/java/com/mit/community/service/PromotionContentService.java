package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.PromotionContent;
import com.mit.community.mapper.PromotionContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销详情业务处理层
 * @author Mr.Deng
 * @date 2018/12/18 15:57
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class PromotionContentService {
    @Autowired
    private PromotionContentMapper promotionContentMapper;

    /**
     * 查询促销详情信息，通过促销id
     * @param promotionId 促销id
     * @return 促销详情信息
     * @author Mr.Deng
     * @date 16:10 2018/12/18
     */
    public PromotionContent getByPromotionId(Integer promotionId) {
        EntityWrapper<PromotionContent> wrapper = new EntityWrapper<>();
        wrapper.eq("promotion_id", promotionId);
        List<PromotionContent> promotionContents = promotionContentMapper.selectList(wrapper);
        if (promotionContents.isEmpty()) {
            return null;
        }
        return promotionContents.get(0);
    }

    /**
     * 保存
     * @param promotionContent 促销内容
     * @author shuyy
     * @date 2018/12/27 14:10
     * @company mitesofor
    */
    public void save(PromotionContent promotionContent){
        promotionContent.setGmtCreate(LocalDateTime.now());
        promotionContent.setGmtModified(LocalDateTime.now());
        promotionContentMapper.insert(promotionContent);
    }

    /**
     * 更新
     * @param promotionContent 促销内容
     * @author shuyy
     * @date 2018/12/27 14:21
     * @company mitesofor
    */
    public void update(PromotionContent promotionContent){
        EntityWrapper<PromotionContent> wrapper = new EntityWrapper<>();
        wrapper.eq("promotion_id", promotionContent.getPromotionId());
        promotionContent.setGmtModified(LocalDateTime.now());
        promotionContentMapper.update(promotionContent, wrapper);
    }

    /**
     * 删除，通过促销id
     * @param promotionId 促销id
     * @author shuyy
     * @date 2018/12/27 14:36
     * @company mitesofor
    */
    public void removeByPromotionId(Integer promotionId){
        EntityWrapper<PromotionContent> wrapper = new EntityWrapper<>();
        wrapper.eq("promotion_id", promotionId);
        promotionContentMapper.delete(wrapper);
    }
}
