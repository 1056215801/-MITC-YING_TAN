package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HouseHoldPhoto;
import com.mit.community.mapper.HouseHoldPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseHoldPhotoService {
    @Autowired
    private HouseHoldPhotoMapper houseHoldPhotoMapper;

    public HouseHoldPhoto getByHouseHoldIdAndDeviceNumAndPhotoUrl (Integer householdId, String deviceNum, String photoUrlNet) {
        EntityWrapper<HouseHoldPhoto> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("device_num", deviceNum);
        wrapper.eq("photo_url_net", photoUrlNet);
        List<HouseHoldPhoto> list = houseHoldPhotoMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public HouseHoldPhoto getByPhotoUrlNet (String photoUrlNet) {
        EntityWrapper<HouseHoldPhoto> wrapper = new EntityWrapper<>();
        wrapper.eq("photo_url_net", photoUrlNet);
        List<HouseHoldPhoto> list = houseHoldPhotoMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void save (HouseHoldPhoto houseHoldPhoto) {
        houseHoldPhotoMapper.insert(houseHoldPhoto);
    }

    public void save (Integer houseHoldId, String photoUrlNet, String photoUrl, String feaUrl, Integer deviceGroupId, int isUpload, String deviceNum) {
        HouseHoldPhoto houseHoldPhoto = new HouseHoldPhoto();
        houseHoldPhoto.setHouseHoldId(houseHoldId);
        houseHoldPhoto.setPhotoUrlNet(photoUrlNet);
        houseHoldPhoto.setPhotoUrl(photoUrl);
        houseHoldPhoto.setFeaUrl(feaUrl);
        houseHoldPhoto.setDeviceGroupId(deviceGroupId);
        houseHoldPhoto.setIsUpload(isUpload);
        houseHoldPhoto.setDeviceNum(deviceNum);
        houseHoldPhoto.setGmtCreate(LocalDateTime.now());
        houseHoldPhoto.setGmtModified(LocalDateTime.now());
        houseHoldPhotoMapper.insert(houseHoldPhoto);
    }

    public HouseHoldPhoto getById (Integer id) {
        return houseHoldPhotoMapper.selectById(id);
    }

    public void updateUploadById(int uploadStatus, Integer id) {
        HouseHoldPhoto houseHoldPhoto = new HouseHoldPhoto();
        houseHoldPhoto.setId(id);
        houseHoldPhoto.setIsUpload(uploadStatus);
        houseHoldPhoto.setGmtModified(LocalDateTime.now());
        houseHoldPhotoMapper.updateById(houseHoldPhoto);
    }
}
