package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.Xingzhengquhua;

import java.util.List;

/**
 * @Author qishengjun
 * @Date Created in 15:47 2019/8/26
 * @Company: mitesofor </p>
 * @Description:~
 */
public interface XingzhengquhuaMapper extends BaseMapper<Xingzhengquhua> {
    List<Xingzhengquhua> selectProvinceList();

    List<Xingzhengquhua> selectCityList(String provinceId);

    List<Xingzhengquhua> getDistrictList(String cityId);

    List<Xingzhengquhua> getTownList(String districtId);

    List<Xingzhengquhua> getCommitteeList(String townId);
}
