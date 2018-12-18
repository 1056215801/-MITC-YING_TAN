package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.PromotionContent;
import com.mit.community.mapper.PromotionContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
