package com.mit.community.module.hik.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.entity.com.mit.community.entity.hik.FaceInfo;
import com.mit.community.service.DeviceService;
import com.mit.community.service.FaceComparisonService;
import com.mit.community.service.RealTimePhotoService;
import com.mit.community.util.Result;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "(海康设备信息接口)")
@RequestMapping(value = "/hkVideoDevice")
public class HKDeviceController {

    private NativeLong lUserID;

    @RequestMapping("/test")
    @ApiOperation(value = "返回前台设备名和地址", notes = "")
    public Result addFaceToHK(MonitorCameraInfo cameraInfo) {

        Resource resource = new ClassPathResource("");
       String path=null;
        try {
            path=resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HCNetSDK sdk=(HCNetSDK) Native.loadLibrary(   path+"\\lib\\"+"HCNetSDK",
                HCNetSDK.class);
        cameraInfo=new MonitorCameraInfo();
        cameraInfo.setIp("192.168.1.163");
        cameraInfo.setPort(8000);
        cameraInfo.setUserName("admin");
        cameraInfo.setPassword("admin123");
        //判断摄像头是否开启
        if (!sdk.NET_DVR_Init()) {
            System.out.println("SDK初始化失败");
            return  Result.error("初始化设备失败");
        }

        HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();// 设备信息

        //登录信息

        NativeLong id = sdk.NET_DVR_Login_V30(cameraInfo.getIp(), (short) cameraInfo.getPort(),
                cameraInfo.getUserName(), cameraInfo.getPassword(), devinfo);

        cameraInfo.setId(id);// 返回一个用户编号，同时将设备信息写入devinfo
        if (cameraInfo.getId().intValue() < 0) {
            System.out.println("设备注册失败");
            return  Result.error("设备注册失败");
        }
        //DVR工作状态
        HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();
        if (!sdk.NET_DVR_GetDVRWorkState_V30(cameraInfo.getId(), devwork)) {
            // 返回Boolean值，判断是否获取设备能力
            return  Result.error("返回设备状态失败");
        }

        IntByReference ibrBytesReturned = new IntByReference(0);// 获取IP接入配置参数
        HCNetSDK.NET_DVR_IPPARACFG ipcfg = new HCNetSDK.NET_DVR_IPPARACFG();//IP接入配置结构
        ipcfg.write();
        Pointer lpIpParaConfig = ipcfg.getPointer();
        //获取相关参数配置
        sdk.NET_DVR_GetDVRConfig(cameraInfo.getId(), HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0),
                lpIpParaConfig, ipcfg.size(), ibrBytesReturned);
        ipcfg.read();
        System.out.print("IP地址:" + cameraInfo.getId());
        System.out.println("|设备状态：" + devwork.dwDeviceStatic);// 0正常，1CPU占用率过高，2硬件错误，3未知
        //System.out.println("ChanNum"+devinfo.byChanNum);
        // 显示模拟通道
        for (int i = 0; i < devinfo.byChanNum; i++) {
            System.out.print("Camera" + i + 1);// 模拟通道号名称
            System.out.print("|是否录像:" + devwork.struChanStatic[i].byRecordStatic);// 0不录像，不录像
            System.out.print("|信号状态:" + devwork.struChanStatic[i].bySignalStatic);// 0正常，1信号丢失
            System.out.println("|硬件状态:" + devwork.struChanStatic[i].byHardwareStatic);// 0正常，1异常
        }

        List<Map> list=new ArrayList<>();  //获取设备的名称


        //设备支持IP通道
        for (int iChannum = 0; iChannum < devinfo.byChanNum; iChannum++)
        {
            if(ipcfg.byAnalogChanEnable[iChannum] == 1)
            {

            }
        }
        for(int iChannum =0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++) {
            if (ipcfg.struIPChanInfo[iChannum].byEnable == 1) {
                Map map = new HashMap();
                map.put("name", "IPCamera" + iChannum);
               // map.put("url", "rtsp://admin:admin123@192.168.1.163:554/h264/ch" + (32 + iChannum) + "/main/av_stream");
                map.put("url","rtmp://192.168.1.141:1935/live/video"+(iChannum+devinfo.byStartChan));
                list.add(map);
            }
        }

        //注销用户
        sdk.NET_DVR_Logout(cameraInfo.getId());//释放SDK资源
        sdk.NET_DVR_Cleanup();

        return  Result.success(list);
    }


    @RequestMapping("/findVideoFile")
    @ApiOperation(value = "返回前台设备名和地址", notes = "")
    public Result findVideoFile(MonitorCameraInfo cameraInfo) {

        Resource resource = new ClassPathResource("");
        String path=null;
        try {
            path=resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HCNetSDK sdk=(HCNetSDK) Native.loadLibrary(   path+"\\lib\\"+"HCNetSDK",
                HCNetSDK.class);
        cameraInfo=new MonitorCameraInfo();
        cameraInfo.setIp("192.168.1.163");
        cameraInfo.setPort(8000);
        cameraInfo.setUserName("admin");
        cameraInfo.setPassword("admin123");
        //判断摄像头是否开启
        if (!sdk.NET_DVR_Init()) {
            System.out.println("SDK初始化失败");
            return  Result.error("初始化设备失败");
        }

        HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();// 设备信息

        //登录信息

        NativeLong id = sdk.NET_DVR_Login_V30(cameraInfo.getIp(), (short) cameraInfo.getPort(),
                cameraInfo.getUserName(), cameraInfo.getPassword(), devinfo);

        cameraInfo.setId(id);// 返回一个用户编号，同时将设备信息写入devinfo
        if (cameraInfo.getId().intValue() < 0) {
            System.out.println("设备注册失败");
            return  Result.error("设备注册失败");
        }
        //DVR工作状态
        HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();
        if (!sdk.NET_DVR_GetDVRWorkState_V30(cameraInfo.getId(), devwork)) {
            // 返回Boolean值，判断是否获取设备能力
            return  Result.error("返回设备状态失败");
        }

        IntByReference ibrBytesReturned = new IntByReference(0);// 获取IP接入配置参数
        HCNetSDK.NET_DVR_IPPARACFG ipcfg = new HCNetSDK.NET_DVR_IPPARACFG();//IP接入配置结构
        ipcfg.write();
        Pointer lpIpParaConfig = ipcfg.getPointer();
        //获取相关参数配置
        sdk.NET_DVR_GetDVRConfig(cameraInfo.getId(), HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0),
                lpIpParaConfig, ipcfg.size(), ibrBytesReturned);
        ipcfg.read();
        System.out.print("IP地址:" + cameraInfo.getId());
        System.out.println("|设备状态：" + devwork.dwDeviceStatic);// 0正常，1CPU占用率过高，2硬件错误，3未知
        //System.out.println("ChanNum"+devinfo.byChanNum);
        // 显示模拟通道
        for (int i = 0; i < devinfo.byChanNum; i++) {
            System.out.print("Camera" + i + 1);// 模拟通道号名称
            System.out.print("|是否录像:" + devwork.struChanStatic[i].byRecordStatic);// 0不录像，不录像
            System.out.print("|信号状态:" + devwork.struChanStatic[i].bySignalStatic);// 0正常，1信号丢失
            System.out.println("|硬件状态:" + devwork.struChanStatic[i].byHardwareStatic);// 0正常，1异常
        }

        List<Map> list=new ArrayList<>();  //获取设备的名称


        //设备支持IP通道
        for (int iChannum = 0; iChannum < devinfo.byChanNum; iChannum++)
        {
            if(ipcfg.byAnalogChanEnable[iChannum] == 1)
            {

            }
        }
        for(int iChannum =0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++) {
            if (ipcfg.struIPChanInfo[iChannum].byEnable == 1) {
                Map map = new HashMap();
                map.put("name", "IPCamera" + iChannum);
                // map.put("url", "rtsp://admin:admin123@192.168.1.163:554/h264/ch" + (32 + iChannum) + "/main/av_stream");
                map.put("url","rtmp://192.168.1.141:1935/live/video"+(iChannum+devinfo.byStartChan));
                list.add(map);
            }
        }

        //注销用户
        sdk.NET_DVR_Logout(cameraInfo.getId());//释放SDK资源
        sdk.NET_DVR_Cleanup();

        return  Result.success(list);
    }




}