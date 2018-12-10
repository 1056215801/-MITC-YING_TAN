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
 *
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
        String url = "/auth/base/checkSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", telNum);
        map.put("smsCode", smsCode);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if(jsonObject.get("errorCode") == null){
            return true;
        }
        return false;
    }

    /**
     * 登陆
     * @param cellphone
     * @param password
     * @return com.mit.community.entity.DnakeLoginResponse
     * @throws
     * @author shuyy
     * @date 2018/12/7 15:32
     * @company mitesofor
    */
    public DnakeLoginResponse login(String cellphone, String password) {
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String url = "/auth/base/login";
        Map<String, Object> map = new HashMap<>();
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
     * @param username      用户名
     * @param password      密码
     * @param deviceNum     设备编号
     * @return 登录成功数据
     * @author Mr.Deng
     * @date 14:01 2018/12/4
     */
    public boolean httpOpenDoor(String cellphone, String communityCode, String username, String password, String deviceNum) {
        String result = StringUtils.EMPTY;
        String url = "/auth/api/device/unlock";
        DnakeLoginResponse dnakeLoginResponse = (DnakeLoginResponse) redisService.get(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone);
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeLoginResponse.getAppUserId());
        map.put("communityCode", communityCode);
        map.put("deviceNum", deviceNum);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if(jsonObject.get("errorCode") != null){
            return false;
        }
        return true;
    }

    /**
     * 设置呼叫转移号码
     * @param cellphone  手机号码
     * @param sipMobile 转移号码
     * @return 返回操作成功是否
     * @author Mr.Deng
     * @date 10:25 2018/12/5
     */
    public String callForwarding(String cellphone, String sipMobile) {
        String result = StringUtils.EMPTY;
        String url = "/auth/api/common/callForwarding";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("sipMobile", sipMobile);
        DnakeLoginResponse dnakeLoginResponse = (DnakeLoginResponse) redisService.get(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone);
        map.put("appUserId", dnakeLoginResponse.getAppUserId());
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
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
     * @param username      用户名
     * @param password      密码
     * @return 申请成功返回信息
     * @author Mr.Deng
     * @date 16:32 2018/12/4
     */
    public String getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode, String username,
                                String password) {
        String result = StringUtils.EMPTY;
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/common/inviteCode";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(6);
        map.put("dateTag", dateTag);
        DnakeLoginResponse dnakeLoginResponse = (DnakeLoginResponse) redisService.get(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone);
        map.put("appUserId", dnakeLoginResponse.getAppUserId());
        map.put("times", times);
        map.put("deviceGroupId", deviceGroupId);
        map.put("communityCode", communityCode);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        return result;
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
        String url = "/auth/api/device/getDeviceGroup";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("communityCode", communityCode);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();

        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
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
    public Map<String, Object> getMyKey(String cellphone, String username, String password, String communityCode) {
        List<MyKey> unitKeys = Lists.newArrayListWithExpectedSize(10);
        List<MyKey> CommunityKeys = Lists.newArrayListWithExpectedSize(10);
        Map<String, Object> maps = Maps.newHashMapWithExpectedSize(2);
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/user/myKey";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        DnakeLoginResponse dnakeLoginResponse = (DnakeLoginResponse) redisService.get(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone);
        map.put("appUserId", dnakeLoginResponse.getAppUserId());
        map.put("communityCode", communityCode);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
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
                    CommunityKeys.add(myKey);
                }
            }
            maps.put("unitKeys", unitKeys);
            maps.put("CommunityKeys", CommunityKeys);
        }
        return maps;
    }
}
