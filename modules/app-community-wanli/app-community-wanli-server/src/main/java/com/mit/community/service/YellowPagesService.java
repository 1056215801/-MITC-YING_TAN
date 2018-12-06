package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.YellowPages;
import com.mit.community.mapper.YellowPagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生活黄页业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 17:15
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class YellowPagesService {
    @Autowired
    private YellowPagesMapper yellowPagesMapper;

    /**
     * 查询生活黄页信息，通过黄页类型id
     * @param yellowPagesTypeId 黄页类型id
     * @return 生活黄页信息
     * @author Mr.Deng
     * @date 17:19 2018/12/5
     */
    public List<YellowPages> listByYellowPagesTypeId(Integer yellowPagesTypeId) {
        EntityWrapper<YellowPages> wrapper = new EntityWrapper<>();
        wrapper.eq("yellow_pages_type_id", yellowPagesTypeId);
        return yellowPagesMapper.selectList(wrapper);
    }

}
