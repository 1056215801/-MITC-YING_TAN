package com.mit.community.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.common.HttpLogin;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Warning;
import com.mit.community.service.SysUserService;
import com.mit.community.service.WarningService;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 警报定时同步数据
 * @author Mr.Deng
 * @date 2018/11/27 14:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
//@Component
public class WarningSchedule {

    private final WarningService warningService;

    private final SysUserService sysUserService;

    @Autowired
    public WarningSchedule(WarningService warningService, SysUserService sysUserService) {
        this.warningService = warningService;
        this.sysUserService = sysUserService;
    }

    @Scheduled(cron = "*/30 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveWaring() {
        warningService.remove();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<SysUser> list1 = sysUserService.list();
        if (list1 != null) {
            for (SysUser sysUser : list1) {
                HttpLogin httpLogin = new HttpLogin(sysUser.getUsername(), sysUser.getPassword());
                httpLogin.loginUser();
                int s = 2;
                for (int i = 1; i <= s; i++) {
                    String url = "http://cmp.ishanghome.com/cmp/deviceAlarm/load";
                    NameValuePair[] data = {new NameValuePair("page", i + ""),
                            new NameValuePair("list", "10")};
                    String post = httpLogin.post(url, data, httpLogin.getCookie());
                    JSONObject jsonObject = JSONObject.parseObject(post);
                    JSONArray list = jsonObject.getJSONArray("list");
                    s = jsonObject.getInteger("lastPage");
                    if (list != null && s >= 1) {
                        for (int j = 0; j < list.size(); j++) {
                            JSONObject jsonObject1 = JSONObject.parseObject(list.get(j).toString());
                            Warning warning = new Warning();
                            String alarmMsg = jsonObject1.getString("alarmMsg");
                            String buildingCode = jsonObject1.getString("buildingCode");
                            String buildingName = jsonObject1.getString("buildingName");
                            String deviceName = jsonObject1.getString("deviceName");
                            String deviceNum = jsonObject1.getString("deviceNum");
                            String deviceType = jsonObject1.getString("DeviceType");
                            String gpsAddress = jsonObject1.getString("gpsAddress");
                            String unitCode = jsonObject1.getString("unitCode");
                            String unitName = jsonObject1.getString("unitName");
                            warning.setCommunityCode(sysUser.getCommunityCode());
                            warning.setAlarmDataStatus(jsonObject1.getInteger("alarmDataStatus"));
                            warning.setAlarmGrade(jsonObject1.getInteger("alarmGrade"));
                            warning.setAlarmId(jsonObject1.getInteger("alarmId"));
                            warning.setAlarmMsg(alarmMsg == null ? StringUtils.EMPTY : alarmMsg);
                            warning.setAlarmType(jsonObject1.getInteger("alarmType"));
                            warning.setBuildingCode(buildingCode == null ? StringUtils.EMPTY : buildingCode);
                            warning.setBuildingName(buildingName == null ? StringUtils.EMPTY : buildingName);
                            warning.setDataStatus(jsonObject1.getInteger("dataStatus"));
                            warning.setDeviceCode(jsonObject1.getString("deviceCode"));
                            warning.setDeviceName(deviceName == null ? StringUtils.EMPTY : deviceName);
                            warning.setDeviceNum(deviceNum == null ? StringUtils.EMPTY : deviceNum);
                            warning.setDeviceType(deviceType == null ? StringUtils.EMPTY : deviceType);
                            warning.setEnterFlag(jsonObject1.getInteger("enterFlag"));
                            warning.setGpsAddress(gpsAddress == null || "null".equals(gpsAddress) ? StringUtils.EMPTY : gpsAddress);
                            warning.setUnitCode(unitCode == null ? StringUtils.EMPTY : unitCode);
                            warning.setUnitName(unitName == null ? StringUtils.EMPTY : unitName);
                            warning.setMaxTime(LocalDateTime.parse(jsonObject1.getString("maxTime"), formatter));
                            warning.setCreateTime(LocalDateTime.parse(jsonObject1.getString("createtime"), formatter));
                            warning.setGmtCreate(LocalDateTime.now());
                            warning.setGmtModified(LocalDateTime.now());
                            warningService.save(warning);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

}
