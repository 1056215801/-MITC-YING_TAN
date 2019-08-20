package com.mit.community.service;

import com.mit.community.entity.DeviceAddressInfo;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.entity.ListInterfaceData;
import com.mit.community.mapper.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListMapper listMapper;

    public ListInterfaceData getDeviceList() {
        ListInterfaceData listInterfaceData = new ListInterfaceData();
        List<DeviceAddressInfo> addressInfoList = listMapper.getDeviceAddressInfo();
        List<DeviceInfo> deviceInfoList = listMapper.getDeviceInfo();
        List<DeviceAddressInfo> addressInfoOrder = this.getChildsManyGroup(addressInfoList,0);
        /*for(DeviceAddressInfo l : addressInfoOrder){
            System.out.println(l.getAddressName());
            for(DeviceAddressInfo ll : l.getDeviceAddressInfo()){
                System.out.println("\t"+ll.getAddressName());
                for(DeviceAddressInfo lll : ll.getDeviceAddressInfo()){
                    System.out.println("\t\t"+lll.getAddressName());
                    for(DeviceAddressInfo lllL : lll.getDeviceAddressInfo()){
                        System.out.println("\t\t\t"+lllL.getAddressName());

                    }
                }
            }
        }*/


        listInterfaceData.setDeviceInfo(deviceInfoList);
        listInterfaceData.setDeviceAddressInfo(addressInfoOrder);
        return listInterfaceData;

    }

    public void updateCollect(Integer id, int isCollect) {
        listMapper.updateCollect(id, isCollect);
    }
    public static List<DeviceAddressInfo> getChildsManyGroup(List<DeviceAddressInfo> list,int pid){
        List<DeviceAddressInfo> arr = new ArrayList<DeviceAddressInfo>();
        for(DeviceAddressInfo deviceAddressInfo : list){
            if(pid == deviceAddressInfo.getParentAddressId()){
                deviceAddressInfo.setDeviceAddressInfo(getChildsManyGroup(list, deviceAddressInfo.getId()));
                arr.add(deviceAddressInfo);
            }
        }
        return arr;
    }

}
