package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.MyKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开门控制类
 * @author Mr.Deng
 * @date 2018/12/4 13:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OpenDoorService {
    /**
     * http开门
     * @param communityCode 小区code
     * @param username      用户名
     * @param password      密码
     * @param deviceNum     设备编号
     * @return 登录成功数据
     * @author Mr.Deng
     * @date 14:01 2018/12/4
     */
    public String httpOpenDoor(String communityCode, String username, String password, String deviceNum) {
        String result = StringUtils.EMPTY;
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/device/unlock";
        appLogin(username, password);
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", DnakeAppUser.appUserId);
        map.put("communityCode", communityCode);
        map.put("deviceNum", deviceNum);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        if (StringUtils.isNotBlank(invoke)) {
            result = invoke;
        }
        return result;
    }

    /**
     * 获取我的钥匙
     * @param username      用户名
     * @param password      密码
     * @param communityCode 小区code
     * @return 我的钥匙信息
     * @author Mr.Deng
     * @date 14:08 2018/12/4
     */
    public Map<String, Object> getMyKey(String username, String password, String communityCode) {
        List<MyKey> unitKeys = Lists.newArrayListWithExpectedSize(10);
        List<MyKey> CommunityKeys = Lists.newArrayListWithExpectedSize(10);
        Map<String, Object> maps = Maps.newHashMapWithExpectedSize(2);
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/user/myKey";
        appLogin(username, password);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", DnakeAppUser.appUserId);
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        if (StringUtils.isNotBlank(invoke)) {
            JSONObject jsonObject = JSON.parseObject(invoke);
            JSONArray devices = jsonObject.getJSONArray("devices");
            for (Object json : devices) {
                MyKey myKey = JSONObject.parseObject(json.toString(), MyKey.class);
                String buildingCode = myKey.getBuildingCode();
                //判断是否为单元机和门口机
                if (StringUtils.isNotBlank(buildingCode)) {
                    unitKeys.add(myKey);
                } else {
                    CommunityKeys.add(myKey);
                }
            }
            maps.put("unitKeys", unitKeys);
            maps.put("CommunityKeys", CommunityKeys);
        }
        return maps;
    }

    /**
     * 查询小区设备组信息,通过小区code
     * @param username      用户名
     * @param password      密码
     * @param communityCode 小区code
     * @return 小区设备组信息
     * @author Mr.Deng
     * @date 16:51 2018/12/4
     */
    public String getDeviceGroup(String username, String password, String communityCode) {
        String result = StringUtils.EMPTY;
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        appLogin(username, password);
        String url = "/auth/api/device/getDeviceGroup";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        if (StringUtils.isNotBlank(invoke)) {
            result = invoke;
        }
        return result;
    }

    /**
     * 申请访客邀请码
     * @param dateTag       日期标志：今天:0；明天：1;
     * @param times         开锁次数：无限次：0；一次：1；
     * @param deviceGroupId 设备分组id，默认只传公共权限组
     * @param communityCode 社区编号
     * @param username      用户名
     * @param password      密码
     * @return 申请成功返回信息
     * @author Mr.Deng
     * @date 16:32 2018/12/4
     */
    public String getInviteCode(String dateTag, String times, String deviceGroupId, String communityCode, String username,
                                String password) {
        String result = StringUtils.EMPTY;
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        appLogin(username, password);
        String url = "/auth/api/common/inviteCode";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(6);
        map.put("dateTag", dateTag);
        map.put("appUserId", DnakeAppUser.appUserId);
        map.put("times", times);
        map.put("deviceGroupId", deviceGroupId);
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        if (StringUtils.isNotBlank(invoke)) {
            result = invoke;
        }
        return result;
    }

    /**
     * 设置呼叫转移号码
     * @param username  用户名
     * @param password  密码
     * @param sipMobile 转移号码
     * @return 返回操作成功是否
     * @author Mr.Deng
     * @date 10:25 2018/12/5
     */
    public String callForwarding(String username, String password, String sipMobile) {
        String result = StringUtils.EMPTY;
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        appLogin(username, password);
        String url = "/auth/api/common/callForwarding";
        Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(2);
        map1.put("sipMobile", sipMobile);
        map1.put("appUserId", DnakeAppUser.appUserId);
        String invoke = DnakeAppApiUtil.invoke(url, map1);
        if (StringUtils.isNotBlank(invoke)) {
            result = invoke;
        }
        return result;
    }

    /**
     * 登录用户
     * @param username 用户名
     * @param password 密码
     * @author Mr.Deng
     * @date 14:05 2018/12/4
     */
    private void appLogin(String username, String password) {
        DnakeAppUser.loginName = username;
        DnakeAppUser.password = password;
        DnakeAppApiUtil.apiLogin();
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期 如：yyyy-MM-dd HH:mm:ss
     * @return unix时间戳字符串
     */
    public static String DateToTimeStamp(String dateStr) {
        String result = StringUtils.EMPTY;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




//    public static void main(String[] args) {
//        String[] times = {"2018-12-4 18:10:10,2018-12-4 19:10:10", "2018-12-5 18:10:10,2018-12-5 19:10:10"};
//        List<Object> list = Lists.newArrayListWithCapacity(3);
//        for (int i = 0; i < times.length; i++) {
//            String time = times[i];
//            String[] unixTimes = time.split(",");
//            Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
//            map.put("startTime", DateToTimeStamp(unixTimes[0]));
//            map.put("endTime", DateToTimeStamp(unixTimes[1]));
//            map.put("once", 0);
//            list.add(map);
//        }
//        System.out.println(list);
//    }

}
