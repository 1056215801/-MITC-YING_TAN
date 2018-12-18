package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Promotion;
import com.mit.community.mapper.PromotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 查询所有的促销活动 ，通过小区code
     * @param communityCode 小区code
     * @return
     * @author Mr.Deng
     * @date 16:21 2018/12/18
     */
    public List<Promotion> list(String communityCode) {
        EntityWrapper<Promotion> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.orderBy("gmt_create", false);
        return promotionMapper.selectList(wrapper);
    }

}
