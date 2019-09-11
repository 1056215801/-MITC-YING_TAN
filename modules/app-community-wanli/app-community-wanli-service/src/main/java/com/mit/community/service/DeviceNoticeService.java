package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.DeviceNotice;
import com.mit.community.entity.DeviceNoticePhoto;
import com.mit.community.mapper.DeviceNoticeMapper;
import com.mit.community.mapper.DeviceNoticePhotoMapper;
import com.mit.community.util.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceNoticeService {
    @Autowired
    private DeviceNoticeMapper deviceNoticeMapper;
    @Autowired
    private DeviceNoticePhotoMapper deviceNoticePhotoMapper;

    public void save(String communityCode, String communityName, String title, Integer type, Integer canal, String startTime, String endTime, String playTime, String content, MultipartFile[] photos) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DeviceNotice deviceNotice = new DeviceNotice();
            deviceNotice.setCommunityCode(communityCode);
            deviceNotice.setCommunityName(communityName);
            deviceNotice.setTitle(title);
            deviceNotice.setType(type);
            deviceNotice.setCanal(canal);
            deviceNotice.setStartTime(format.parse(startTime));
            deviceNotice.setEndTime(format.parse(endTime));
            deviceNotice.setPlayTime(playTime);
            deviceNotice.setContent(content);
            deviceNotice.setStatus(1);
            deviceNotice.setGmtCreate(LocalDateTime.now());
            deviceNotice.setGmtModified(LocalDateTime.now());
            deviceNoticeMapper.insert(deviceNotice);
            Integer deviceNoticeId = deviceNotice.getId();
            if (photos != null) {
                DeviceNoticePhoto deviceNoticePhoto = null;
                for (MultipartFile photo : photos) {
                    deviceNoticePhoto = new DeviceNoticePhoto();
                    String netUrl = UploadUtil.upload(photo);
                    String uuid = UUID.randomUUID().toString();
                    String fileHz =  uuid + ".jpg";
                    String basePath = "d:\\deviceNoticePhoto";
                    File file = new File(basePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    byte[] b = photo.getBytes();
                    String localUrl = basePath + "\\" +fileHz;//本地保存地址
                    File aa = new File(localUrl);
                    FileImageOutputStream fos = new FileImageOutputStream(aa);
                    fos.write(b, 0, b.length);
                    fos.close();
                    deviceNoticePhoto.setDeviceNoticeId(deviceNoticeId);
                    deviceNoticePhoto.setNetUrl(netUrl);
                    deviceNoticePhoto.setLocalUrl(localUrl);
                    deviceNoticePhoto.setGmtCreate(LocalDateTime.now());
                    deviceNoticePhoto.setGmtModified(LocalDateTime.now());
                    deviceNoticePhotoMapper.insert(deviceNoticePhoto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDeviceNotice(Integer id, String title, Integer type, Integer canal, String startTime, String endTime, String playTime, String content) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DeviceNotice deviceNotice = new DeviceNotice();
            deviceNotice.setId(id);
            deviceNotice.setTitle(title);
            deviceNotice.setType(type);
            deviceNotice.setCanal(canal);
            deviceNotice.setStartTime(format.parse(startTime));
            deviceNotice.setEndTime(format.parse(endTime));
            deviceNotice.setPlayTime(playTime);
            deviceNotice.setContent(content);
            deviceNotice.setGmtModified(LocalDateTime.now());
            deviceNoticeMapper.updateById(deviceNotice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDeviceNoticePhoto(Integer id, MultipartFile photo) {
        try {
            if (photo != null) {
                DeviceNoticePhoto deviceNoticePhoto = new DeviceNoticePhoto();
                String netUrl = UploadUtil.upload(photo);
                String uuid = UUID.randomUUID().toString();
                String fileHz =  uuid + ".jpg";
                String basePath = "d:\\deviceNoticePhoto";
                File file = new File(basePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                byte[] b = photo.getBytes();
                String localUrl = basePath + "\\" +fileHz;//本地保存地址
                File aa = new File(localUrl);
                FileImageOutputStream fos = new FileImageOutputStream(aa);
                fos.write(b, 0, b.length);
                fos.close();
                deviceNoticePhoto.setDeviceNoticeId(id);
                deviceNoticePhoto.setNetUrl(netUrl);
                deviceNoticePhoto.setLocalUrl(localUrl);
                deviceNoticePhoto.setGmtCreate(LocalDateTime.now());
                deviceNoticePhoto.setGmtModified(LocalDateTime.now());
                deviceNoticePhotoMapper.insert(deviceNoticePhoto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Page<DeviceNotice> deviceNoticeListPage (String communityCode, String title, Integer type, String startTime, String endTime, Integer status, Integer pageNum, Integer pageSize) {
        Page<DeviceNotice> page = new Page<>(pageNum, pageSize);
        EntityWrapper<DeviceNotice> wrapper = new EntityWrapper<> ();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(title)) {
            wrapper.like("title", title);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (startTime != null) {
            wrapper.ge("start_time", startTime);
        }
        if (endTime != null) {
            wrapper.le("end_time", endTime);
        }
        wrapper.orderBy("gmt_create", false);
        List<DeviceNotice> list = deviceNoticeMapper.selectPage(page, wrapper);
        if (!list.isEmpty()) {
            List<DeviceNoticePhoto> photos = null;
            for (int i=0; i<list.size(); i++) {
                long a = list.get(i).getEndTime().getTime();
                long b = System.currentTimeMillis();
                if (b > a) {
                    list.get(i).setStatus(3);
                }
                if (list.get(i).getCanal() == 2) {
                    photos = new ArrayList<>();
                    EntityWrapper<DeviceNoticePhoto> photo = new EntityWrapper<> ();
                    photo.eq("device_notice_id", list.get(i).getId());
                    photos = deviceNoticePhotoMapper.selectList(photo);
                    list.get(i).setPhotos(photos);
                }
            }
        }
        page.setRecords(list);
        return page;
    }

    public void deleteDeviceNotice(Integer id) {
        deviceNoticeMapper.deleteById(id);
        EntityWrapper<DeviceNoticePhoto> photo = new EntityWrapper<> ();
        photo.eq("device_notice_id", id);
        deviceNoticePhotoMapper.delete(photo);
    }

    public void deviceNoticeChange(Integer id, Integer status) {
        DeviceNotice deviceNotice = new DeviceNotice();
        deviceNotice.setId(id);
        deviceNotice.setStatus(status);
        deviceNoticeMapper.updateById(deviceNotice);
    }

    public void deleteDeviceNoticePhoto(Integer id) {
        deviceNoticePhotoMapper.deleteById(id);
    }


}
