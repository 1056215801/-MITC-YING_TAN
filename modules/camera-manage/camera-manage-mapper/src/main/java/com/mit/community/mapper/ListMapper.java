package com.mit.community.mapper;

import com.mit.community.entity.DeviceAddressInfo;
import com.mit.community.entity.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ListMapper {
    List<DeviceAddressInfo> getDeviceAddressInfo();
    List<DeviceInfo> getDeviceInfo();
    public void updateCollect(@Param("id")Integer id, @Param("isCollect")int isCollect);
}
