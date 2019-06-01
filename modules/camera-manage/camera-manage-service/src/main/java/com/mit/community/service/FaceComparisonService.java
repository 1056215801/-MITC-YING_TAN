package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.mapper.DeviceInfoMapper;
import com.mit.community.mapper.FaceComparisonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FaceComparisonService {

    @Autowired
    private FaceComparisonMapper faceComparisonMapper;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 保存QQ物联摄像头上传的比对照片
     * @param data
     * @param token 登录时下发的token
     * @throws Exception
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUploadFaceComparisonData(String basePath, UploadFaceComparisonData data, String token) throws Exception{
        EntityWrapper<FaceComparisonData> wrapper = new EntityWrapper<>();
        wrapper.eq("msgid", data.getMsgid());
        List<FaceComparisonData> faceComparisonDatas = faceComparisonMapper.selectList(wrapper);
        if (faceComparisonDatas.isEmpty()) {
            String userInfo = UUID.randomUUID().toString();
            String path = basePath + userInfo + ".jpg";
            byte[] b = Utils.base64ToByte(data.getData().getShot());
            File file = new File(path);
            /*if(!file.exists()) {
                file.mkdir();
            }*/
            FileImageOutputStream fos = new FileImageOutputStream(file);
            fos.write(b,0,b.length);
            fos.close();
            EntityWrapper<DeviceInfo> wrapperDevice = new EntityWrapper<>();
            wrapperDevice.eq("token", token);
            List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(wrapperDevice);
            String deviceId = deviceInfos.get(0).getDeviceId();
            FaceComparisonData faceComparisonData = new FaceComparisonData(data.getCommand(), data.getDatatype(), data.getMsgid(), Utils.getDateTimeOfTimestamp(data.getData().getTime()), deviceId, data.getData().getName(),
                    data.getData().getIdentity(), path, 0, null, null, null, null, null, 0, 0, 0, 0, userInfo, null);
            faceComparisonData.setGmtCreate(LocalDateTime.now());
            faceComparisonData.setGmtModified(LocalDateTime.now());
            faceComparisonMapper.insert(faceComparisonData);
        }
    }
}
