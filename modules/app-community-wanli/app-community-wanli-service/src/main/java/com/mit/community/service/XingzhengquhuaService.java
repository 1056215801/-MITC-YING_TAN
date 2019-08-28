package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Xingzhengquhua;
import com.mit.community.mapper.XingzhengquhuaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 全国行政区划信息 服务实现类
 * </p>
 *
 * @author qsj
 * @since 2019-08-26
 */
@Service
public class XingzhengquhuaService extends ServiceImpl<XingzhengquhuaMapper, Xingzhengquhua> {
    @Autowired
    private XingzhengquhuaMapper xingzhengquhuaMapper;

    public List<Xingzhengquhua> selectListA(){
        List<Xingzhengquhua> xingzhengquhuaList = xingzhengquhuaMapper.selectProvinceList();
        return xingzhengquhuaList;
    }

    public List<Xingzhengquhua> selectCityList(String provinceId) {
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaMapper.selectCityList(provinceId);
        return xingzhengquhuaList;
    }

    public List<Xingzhengquhua> getDistrictList(String cityId) {
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaMapper.getDistrictList(cityId);
        return xingzhengquhuaList;
    }

    public List<Xingzhengquhua> getTownList(String districtId) {
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaMapper.getTownList(districtId);
        return xingzhengquhuaList;
    }

    public List<Xingzhengquhua> getCommittee(String townId) {
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaMapper.getCommitteeList(townId);
        return xingzhengquhuaList;
    }
}
