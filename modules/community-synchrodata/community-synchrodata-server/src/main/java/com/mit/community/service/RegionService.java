package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Region;
import com.mit.community.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地区编码表业务处理层
 * @author Mr.Deng
 * @date 2018/12/11 14:34
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class RegionService extends ServiceImpl<RegionMapper, Region> {

    @Autowired
    private RegionMapper regionMapper;

    /**
     * 查询城市编码信息，通过城市英文名
     * @param englishNames 城市英文名列表
     * @return 城市编码信息
     * @author Mr.Deng
     * @date 15:15 2018/11/29
     */
    public List<Region> getByEnglishName(String[] englishNames) {
        EntityWrapper<Region> wrapper = new EntityWrapper<>();
        wrapper.in("english_name", englishNames);
        return regionMapper.selectList(wrapper);
    }

    /**
     * 查询所有地区
     * @return java.util.List<com.mit.community.entity.Region>
     * @author shuyy
     * @date 2018/12/28 11:15
     * @company mitesofor
    */
    public List<Region> list() {
        return regionMapper.selectList(null);
    }

}
