package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.google.common.collect.Lists;
import com.mit.community.entity.SmokeDetectorStatus;
import com.mit.community.mapper.SmokeDetectorStatusMapper;
import org.apache.ibatis.session.RowBounds;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpSendCenter;
import util.RandomUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class SmokeDetectorStatusService extends ServiceImpl<SmokeDetectorStatusMapper, SmokeDetectorStatus> {

    @Autowired
    private SmokeDetectorStatusMapper smokeDetectorStatusMapper;




    public void insertDataNY() {
        String communityCode = "047cd4ab796a419a80a4d362b9da1c8f";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-2单元-1层", "1栋-2单元-2层", "1栋-2单元-3层", "1栋-2单元-4层", "1栋-2单元-5层", "1栋-2单元-6层", "1栋-3单元-1层", "1栋-3单元-2层", "1栋-3单元-3层", "1栋-3单元-4层", "1栋-3单元-5层", "1栋-3单元-6层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-1单元-12层", "3栋-1单元-13层", "3栋-1单元-14层", "3栋-1单元-15层", "3栋-1单元-16层", "3栋-1单元-17层", "3栋-1单元-18层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-1单元-12层", "4栋-1单元-13层", "4栋-1单元-14层", "4栋-1单元-15层", "4栋-1单元-16层", "4栋-1单元-17层", "4栋-1单元-18层", "4栋-1单元-19层", "4栋-1单元-20层", "4栋-1单元-21层", "4栋-1单元-22层", "4栋-1单元-23层", "4栋-1单元-24层", "4栋-1单元-25层", "4栋-1单元-26层", "4栋-1单元-27层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "4栋-2单元-12层", "4栋-2单元-13层", "4栋-2单元-14层", "4栋-2单元-15层", "4栋-2单元-16层", "4栋-2单元-17层", "4栋-2单元-18层", "4栋-2单元-19层", "4栋-2单元-20层", "4栋-2单元-21层", "4栋-2单元-22层", "4栋-2单元-23层", "4栋-2单元-24层", "4栋-2单元-25层", "4栋-2单元-26层", "4栋-2单元-27层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-1单元-12层", "5栋-1单元-13层", "5栋-1单元-14层", "5栋-1单元-15层", "5栋-1单元-16层", "5栋-1单元-17层", "5栋-1单元-18层", "5栋-1单元-19层", "5栋-1单元-20层", "5栋-1单元-21层", "5栋-1单元-22层", "5栋-1单元-23层", "5栋-2单元-1层", "5栋-2单元-2层", "5栋-2单元-3层", "5栋-2单元-4层", "5栋-2单元-5层", "5栋-2单元-6层", "5栋-2单元-7层", "5栋-2单元-8层", "5栋-2单元-9层", "5栋-2单元-10层", "5栋-2单元-11层", "5栋-2单元-12层", "5栋-2单元-13层", "5栋-2单元-14层", "5栋-2单元-15层", "5栋-2单元-16层", "5栋-2单元-17层", "5栋-2单元-18层", "5栋-2单元-19层", "5栋-2单元-20层", "5栋-2单元-21层", "5栋-2单元-22层", "5栋-2单元-23层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-1单元-12层", "6栋-1单元-13层", "6栋-1单元-14层", "6栋-1单元-15层", "6栋-1单元-16层", "6栋-1单元-17层", "6栋-1单元-18层", "6栋-1单元-19层", "6栋-1单元-20层", "6栋-1单元-21层", "6栋-1单元-22层", "6栋-1单元-23层", "6栋-1单元-24层"};
        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "047cd4ab796a419a80a4d362b9da1c8f");
        wrapper.orderBy("gmt_upload", false);
        List<SmokeDetectorStatus> s = smokeDetectorStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 出现大量烟雾，环境正常
        Short[] statusArray = {1, 2};
        String deviceName = "烟雾感知设备";
        String deviceType = "NB-IoT";
        List<SmokeDetectorStatus> smokeDetectorStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytny-yg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytny-yg-0" + (i + 1);
                } else {
                    deviceNum = "ytny-yg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 5) {
                        deviceStatus = 2;
                    } else if (r < 100) {
                        deviceStatus = 1;
                    }
                }
                SmokeDetectorStatus smokeDetectorStatus = new SmokeDetectorStatus(communityCode, deviceNum,
                        deviceName, "南苑小区" + devicePlace, deviceType,
                        statusArray[1], deviceStatus, localDateTime);
                smokeDetectorStatus.setGmtCreate(LocalDateTime.now());
                smokeDetectorStatus.setGmtModified(LocalDateTime.now());
                smokeDetectorStatuses.add(smokeDetectorStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!smokeDetectorStatuses.isEmpty()) {
            this.insertBatch(smokeDetectorStatuses);
        }
    }

    public List<SmokeDetectorStatus> selectDeviceByThirdOnLine(String keyWords) {
        String appkey="MFN9MM=4xnbFJZjN5nAeNhr4LuI=";
        String url="http://api.heclouds.com/devices?key_words=烟感";

        JSONObject result= HttpSendCenter.get(appkey,url);
        List<SmokeDetectorStatus> list=new ArrayList<>();
        try {
            if(result!=null && "0".equals(result.get("errno").toString())){
              JSONObject data= (JSONObject) result.get("data");
              JSONArray list1= (JSONArray) data.get("devices");
              if(list1!=null && list1.length()>0){
                  for(int i=0;i<list1.length();i++){
                      JSONObject map = (JSONObject) list1.get(i);
                      SmokeDetectorStatus temp=new SmokeDetectorStatus();
                      temp.setDeviceName(map.get("title").toString());
                      temp.setDeviceNum(map.get("id").toString());
                      Boolean b= (Boolean) map.get("online");
                      if(b=true){
                          temp.setDeviceStatus((short)1);
                      }else{
                          temp.setDeviceStatus((short)3);
                      }

                      String  gmtUpload= (String) map.get("create_time");
                      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                      LocalDateTime ldt = LocalDateTime.parse(gmtUpload,df);
                      temp.setGmtUpload(ldt);
                      LocalDateTime localDateTime=LocalDateTime.now();
                      temp.setGmtCreate(localDateTime);
                      temp.setGmtModified(localDateTime);
                      list.add(temp);


                  }

              }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return list;

    }
}
