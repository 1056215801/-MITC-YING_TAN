package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.mapper.DeviceInfoMapper;
import com.mit.community.mapper.FaceComparisonMapper;
import com.mit.community.util.SmsCommunityAppUtil;
import com.mit.community.util.WebPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FaceComparisonService {

    @Autowired
    private FaceComparisonMapper faceComparisonMapper;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private ReportProblemService reportProblemService;

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
            String[] a = path.split("\\\\");
            //String path = address.getHostAddress()+":8010/cloud-service/imgs/"+a[a.length-1];
            String url = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
            List<String> urls = new ArrayList<>();
            urls.add(url);
            if ("熊起".equals(data.getData().getName())) {
                SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                String title = "消息通知";
                WebPush.sendAllsetNotification("收到新的问题反馈，请登录网格助手进行处理",title);
                reportProblemService.save(91,"上访人员外出","上访","东湖区",1, urls);
            }
            if ("艾武德".equals(data.getData().getName())) {
                SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                String title = "消息通知";
                WebPush.sendAllsetNotification("收到新的问题反馈，请登录网格助手进行处理",title);
                reportProblemService.save(70,"上访人员外出","上访","东湖区",1, urls);
            }
            if ("柯尊勇".equals(data.getData().getName())) {
                SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                String title = "消息通知";
                WebPush.sendAllsetNotification("收到新的问题反馈，请登录网格助手进行处理",title);
                reportProblemService.save(65,"上访人员外出","上访","东湖区",1, urls);
            }
            if ("徐韶群".equals(data.getData().getName())) {
                SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                String title = "消息通知";
                WebPush.sendAllsetNotification("收到新的问题反馈，请登录网格助手进行处理",title);
                reportProblemService.save(80,"上访人员外出","上访","东湖区",1, urls);
            }
        }
    }
}
