package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.DeviceInfo;
import org.apache.ibatis.annotations.Param;

public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {
    void updateToken(@Param("deviceId")String deviceId, @Param("token")String token);
    public void updateOutCount();
}
