package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.VehicleInfo;
import com.mit.community.mapper.VehicleInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HuShanLin
 * @Date Created in 18:04 2019/6/27
 * @Company: mitesofor </p>
 * @Description:~
 */
@Service
public class VehicleInfoService {

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    /**
     * @Author HuShanLin
     * @Date 18:23 2019/6/27
     * @Description:~分页查询车辆信息
     */
    public Page<VehicleInfo> listPage(String communityCode, String carnum, String carphone, String brand,
                                      Integer pageNum, Integer pageSize) {
        Page<VehicleInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<VehicleInfo> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(carnum)) {
            wrapper.eq("car_num", carnum);
        }
        if (StringUtils.isNotBlank(carphone)) {
            wrapper.eq("owner_phone", carphone);
        }
        if (StringUtils.isNotBlank(brand)) {
            wrapper.eq("car_brand", brand);
        }
        wrapper.orderBy("gmt_create", false);
        List<VehicleInfo> list = vehicleInfoMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }
}
