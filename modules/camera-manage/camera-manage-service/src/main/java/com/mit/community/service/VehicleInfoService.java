package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.VehicleInfo;
import com.mit.community.mapper.VehicleInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
        wrapper.eq("isdel", 0);
        wrapper.orderBy("gmt_create", false);
        List<VehicleInfo> list = vehicleInfoMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    /**
     * @Author HuShanLin
     * @Date 11:13 2019/6/28
     * @Description:~插入车辆信息
     */
    public void save(String communityCode, Integer id, String carnum, String brand, String model, String color, Date prodTime, Date purchaseTime,
                     String displacement, String driverLicense, String vehicleLicense, String phone) {
        if (id == null) {//新增
            VehicleInfo vehicleInfo = new VehicleInfo(communityCode, carnum, brand, model, color, prodTime, purchaseTime,
                    Double.valueOf(displacement), driverLicense, vehicleLicense, phone, 0);
            vehicleInfo.setGmtModified(LocalDateTime.now());
            vehicleInfo.setGmtCreate(LocalDateTime.now());
            vehicleInfoMapper.insert(vehicleInfo);
        } else {//修改
            EntityWrapper<VehicleInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            VehicleInfo vehicleInfo = new VehicleInfo(communityCode, carnum, brand, model, color, prodTime, purchaseTime,
                    Double.valueOf(displacement), driverLicense, vehicleLicense, phone, 0);
            vehicleInfo.setId(id);
            vehicleInfo.setGmtModified(LocalDateTime.now());
            vehicleInfoMapper.updateById(vehicleInfo);
        }
    }

    /**
     * @Author HuShanLin
     * @Date 11:13 2019/6/28
     * @Description:~删除车辆信息
     */
    public void delete(Integer id) {
        EntityWrapper<VehicleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<VehicleInfo> list = vehicleInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            VehicleInfo vehicleInfo = list.get(0);
            vehicleInfo.setIsdel(1);
            vehicleInfo.setGmtModified(LocalDateTime.now());
            vehicleInfoMapper.updateById(vehicleInfo);
        }
    }
}
