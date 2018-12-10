package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.DnakeLoginResponse;
import com.mit.community.entity.MyKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dnake接口调用
 * @author shuyy
 * @date 2018/12/7
 * @company mitesofor
 */
@Slf4j
@Service
public class DnakeAppApiService {

    @Autowired
    private RedisService redisService;

    /**
     * 验证手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    public boolean checkSmsCode(String telNum, String smsCode) {
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String url = "/auth/base/checkSmsCode";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("telNum", telNum);
        map.put("smsCode", smsCode);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if (jsonObject.get("errorCode") == null) {
            return true;
        }
        return false;
    }

    /**
     * 登陆
     * @param cellphone 电话号码
     * @param password  密码
     * @return com.mit.community.entity.DnakeLoginResponse
     * @author shuyy
     * @date 2018/12/7 15:32
     * @company mitesofor
     */
    public DnakeLoginResponse login(String cellphone, String password) {
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String url = "/auth/base/login";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        map.put("loginName", cellphone);
        map.put("password", password);
        map.put("registrationId", dnakeAppUser.getRegistrationId());
        map.put("platform", dnakeAppUser.getPlatform());
        map.put("clusterAccountId", DnakeConstants.CLUSTER_ACCOUNT_ID);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        return JSON.parseObject(invoke, DnakeLoginResponse.class);
    }

    /**
     * http开门
     * @param communityCode 小区code
     * @param cellphone     电话号码
     * @param deviceNum     设备编号
     * @return 登录成功数据
     * @author Mr.Deng
     * @date 14:01 2018/12/4
     */
    public boolean httpOpenDoor(String cellphone, String communityCode, String deviceNum) {
        String url = "/auth/api/device/unlock";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("communityCode", communityCode);
        map.put("deviceNum", deviceNum);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if (jsonObject.get("errorCode") != null) {
            return false;
        }
        return true;
    }

    /**
     * 设置呼叫转移号码
     * @param cellphone 手机号码
     * @param sipMobile 转移号码
     * @return 返回操作成功是否
     * @author Mr.Deng
     * @date 10:25 2018/12/5
     */
    public String callForwarding(String cellphone, String sipMobile) {
        String result = StringUtils.EMPTY;
        String url = "/auth/api/common/callForwarding";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("sipMobile", sipMobile);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
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
     * @return 申请成功返回信息
     * @author Mr.Deng
     * @date 16:32 2018/12/4
     */
    public String getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode) {
        String result = StringUtils.EMPTY;
        String url = "/auth/api/common/inviteCode";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(6);
        map.put("dateTag", dateTag);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("times", times);
        map.put("deviceGroupId", deviceGroupId);
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        return result;
    }

    /**
     * 重置密码
     * @param cellphone 电话号码
     * @param password  密码
     * @author Mr.Deng
     * @date 13:47 2018/12/8
     */
    public void resetPwd(String cellphone, String password) {
        String url = "/auth/base/resetPwd";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("telNum", cellphone);
        map.put("password", password);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
    }

    /**
     * 查询小区设备组信息,通过小区code
     * @param cellphone     手机号码
     * @param communityCode 小区code
     * @return 小区设备组信息
     * @author Mr.Deng
     * @date 16:51 2018/12/4
     */
    public String getDeviceGroup(String cellphone, String communityCode) {
        String result = StringUtils.EMPTY;
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        String url = "/auth/api/device/getDeviceGroup";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        if (StringUtils.isNotBlank(invoke)) {
            result = invoke;
        }
        return result;
    }

    /**
     * 获取邀请码记录
     * @param cellphone 手机号
     * @param pageIndex 页码，从0开始
     * @param pageSize  页大小
     * @return 查询数据json
     * @author Mr.Deng
     * @date 14:34 2018/12/10
     */
    public String openHistory(String cellphone, Integer pageIndex, Integer pageSize) {
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        String url = "/auth/api/common/openHistory";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        return invoke;
    }

    /**
     * 获取我的钥匙
     * @param communityCode 小区code
     * @return 我的钥匙信息
     * @author Mr.Deng
     * @date 14:08 2018/12/4
     */
    public Map<String, Object> getMyKey(String cellphone, String communityCode) {
        List<MyKey> unitKeys = Lists.newArrayListWithExpectedSize(10);
        List<MyKey> communityKeys = Lists.newArrayListWithExpectedSize(10);
        Map<String, Object> maps = Maps.newHashMapWithExpectedSize(2);
        String url = "/auth/api/user/myKey";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("communityCode", communityCode);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
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
                    communityKeys.add(myKey);
                }
            }
            maps.put("unitKeys", unitKeys);
            maps.put("CommunityKeys", communityKeys);
        }
        return maps;
    }

    /**
     * 通用获取DnakeAppUser对象，通过手机号码
     * @param cellphone 手机号码
     * @return DnakeAppUser
     * @author Mr.Deng
     * @date 19:01 2018/12/10
     */
    private DnakeAppUser getDnakeAppUser(String cellphone) {
        DnakeLoginResponse dnakeLoginResponse = (DnakeLoginResponse) redisService.get(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        if (dnakeLoginResponse != null) {
            dnakeAppUser.setAppUserId(dnakeLoginResponse.getAppUserId());
            dnakeAppUser.setUuid(dnakeLoginResponse.getUuid());
        }
        return dnakeAppUser;
    }
}
