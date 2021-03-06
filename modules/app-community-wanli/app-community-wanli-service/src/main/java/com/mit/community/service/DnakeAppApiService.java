package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.mapper.AuthorizeAppHouseholdDeviceGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    @Autowired
    private UserService userService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupMapper authorizeAppHouseholdDeviceGroupMapper;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceGroupService;
    @Autowired
    private DeviceGroupService deviceGroupService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private HouseholdRoomService householdRoomService;
    @Autowired
    private AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceGroupService;

    /**
     * 验证手机验证码
     *
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
        String invoke = this.invokeProxy(url, map, dnakeAppUser, telNum);
//        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        boolean b = dnakeReponseStatus(invoke);
        if (b) {
            return true;
        }
        return false;
    }

    /**
     * 登陆
     *
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
        //System.out.println("====登录返回的结果invoke="+invoke);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if (jsonObject.get("errorCode") != null) {
            if (jsonObject.get("errorCode").equals(12)) {
                // 住户不存在
                DnakeLoginResponse dnakeLoginResponse = new DnakeLoginResponse();
                dnakeLoginResponse.setStatus(3);
                return dnakeLoginResponse;
            } else if (jsonObject.get("errorCode").equals(19)) {
                // 用户名或密码错误
                DnakeLoginResponse dnakeLoginResponse = new DnakeLoginResponse();
                dnakeLoginResponse.setStatus(2);
                return dnakeLoginResponse;
            }
        }
        DnakeLoginResponse dnakeLoginResponse = JSON.parseObject(invoke, DnakeLoginResponse.class);
        dnakeLoginResponse.setStatus(1);
        return dnakeLoginResponse;
    }

    /**
     * 注册
     *
     * @param cellphone 手机号
     * @param password  密码
     * @author shuyy
     * @date 2018/12/18 11:13
     * @company mitesofor
     */
    public boolean register(String cellphone, String password) {
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String url = "/auth/base/register";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        map.put("loginName", cellphone);
        map.put("password", password);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if (jsonObject.get("isSuccess").equals(1)) {
            return true;
        }
        return false;
    }

    /**
     * http开门
     *
     * @param communityCode 小区code
     * @param cellphone     电话号码
     * @param deviceNum     设备编号
     * @return boolean
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
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone, deviceNum, communityCode);
        log.info(invoke);
        boolean b = dnakeReponseStatus(invoke);
        if (b) {
            return true;
        }
        return false;
    }

    public boolean httpOpenDoor1(String cellphone, String communityCode, String deviceNum) {
        try{
            Map<String,String> params = new HashedMap();
            params.put("cellphone",cellphone);
            params.put("communityCode",communityCode);
            params.put("deviceNum",deviceNum);
            String result = this.sendPost1("http://192.168.1.12:9766/api/web/communitywanli/faceController/appHttpOpenDoor",params);
            System.out.println("===================="+result);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String sendPost1(String url, Map<String,String> parameters) throws Exception{
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name));
                }
                params = sb.toString();
            } else if(parameters.size() > 1){
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name)).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            //System.out.println("=====================发送上传参数请求请求");
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置呼叫转移号码
     *
     * @param cellphone 手机号码
     * @param sipMobile 转移号码
     * @return 返回操作成功是否
     * @author Mr.Deng
     * @date 10:25 2018/12/5
     */
    public String callForwarding(String cellphone, String sipMobile) {
        String url = "/auth/api/common/callForwarding";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("sipMobile", sipMobile);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
        log.info(invoke);
        boolean b = dnakeReponseStatus(invoke);
        return invoke;
    }

    /**
     * 申请访客邀请码
     *
     * @param dateTag       日期标志：今天:0；明天：1;
     * @param times         开锁次数：无限次：0；一次：1；
     * @param deviceGroupId 设备分组id，默认只传公共权限组
     * @param communityCode 社区编号
     * @return 申请成功返回信息
     * @author Mr.Deng
     * @date 16:32 2018/12/4
     */
    public String getInviteCode(String cellphone, String dateTag, String times,
                                String deviceGroupId, String communityCode) {
        String url = "/auth/api/common/inviteCode";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(6);
        map.put("dateTag", dateTag);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("times", times);
        map.put("deviceGroupId", deviceGroupId);
        map.put("communityCode", communityCode);
        String invoke = this.invokeInviteProxy(url, map, dnakeAppUser, cellphone,
                dateTag, times, deviceGroupId, communityCode);
        log.info(invoke);
        return invoke;
    }

    /**
     * 访客高级邀请，
     *
     * @param cellphone     手机号
     * @param time          日期时间段
     * @param deviceGroupId 设备组id
     * @param communityCode 小区code
     * @return 返回信息
     * @author Mr.Deng
     * @date 15:07 2018/12/17
     */
    public String highGrade(String cellphone, List<Map<String, Object>> time, String deviceGroupId, String communityCode) {
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        String url = "/auth/api/common/inviteCode/highGrade";
        String timeStr = JSON.toJSONString(time);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
//        String s = "[{ \"start_time\": \"1517212800\",\"end_time\": \"1517220000\",\"once\":0,\"room\":0801}]";
        map.put("time", timeStr);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("deviceGroupId", deviceGroupId);
        map.put("communityCode", communityCode);
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
        System.out.println(invoke);
        return invoke;
    }

    /**
     * 重置密码
     *
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
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
        log.info(invoke);
    }

//    @Test
//    public void test() {
//        this.resetPwd("13064102937", "111111");
//    }
//
////    /**
////     * 查询小区设备组信息,通过小区code
////     * @param cellphone     手机号码
////     * @param communityCode 小区code
////     * @return 小区设备组信息
////     * @author Mr.Deng
////     * @date 16:51 2018/12/4
////     */
////    public String getDeviceGroup(String cellphone, String communityCode) {
////        String result = StringUtils.EMPTY;
////        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
////        String url = "/auth/api/device/getDeviceGroup";
////        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(1);
////        map.put("communityCode", communityCode);
////        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
////        if (StringUtils.isNotBlank(invoke)) {
////            result = invoke;
////        }
////        return result;
////    }

    /**
     * 获取邀请码记录
     *
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
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
//        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        log.info(invoke);
        return invoke;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/20 21:02
     * @Company mitesofor
     * @Description:~请求代理方法
     */
    public String invokeProxy(String url, Map<String, Object> map, DnakeAppUser dnakeAppUser, String cellphone) {
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        if (invoke.equals("请登录")) {
            User user = userService.getByCellphone(cellphone);
            DnakeLoginResponse dnakeLoginResponse = login(cellphone, user.getPassword());
            if (dnakeLoginResponse.getStatus() == 1) {
                redisService.set(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone,
                        dnakeLoginResponse, RedisConstant.LOGIN_EXPIRE_TIME);
                invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
            }
        }
        return invoke;

    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/20 21:03
     * @Company mitesofor
     * @Description:~httpOpenDoor请求代理
     */
    public String invokeProxy(String url, Map<String, Object> map, DnakeAppUser dnakeAppUser, String cellphone,
                              String deviceNum, String communityCode) {
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        //System.out.println("=====================invoke="+invoke);
        if (invoke.equals("请登录")) {
            User user = userService.getByCellphone(cellphone);
            DnakeLoginResponse dnakeLoginResponse = this.login(cellphone, user.getPassword());
            redisService.set(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone,
                    dnakeLoginResponse, RedisConstant.LOGIN_EXPIRE_TIME);
            boolean flag = this.httpOpenDoor(cellphone, communityCode, deviceNum);
            if (flag) {
                invoke = "success";
            }
        }
        return invoke;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/20 21:03
     * @Company mitesofor
     * @Description:~申请访客邀请码请求代理
     */
    public String invokeInviteProxy(String url, Map<String, Object> map, DnakeAppUser dnakeAppUser,
                                    String cellphone, String dateTag, String times,
                                    String deviceGroupId, String communityCode) {
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        if (invoke.equals("请登录")) {
            User user = userService.getByCellphone(cellphone);
            DnakeLoginResponse dnakeLoginResponse = this.login(cellphone, user.getPassword());
            redisService.set(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone,
                    dnakeLoginResponse, RedisConstant.LOGIN_EXPIRE_TIME);
            String res = this.getInviteCode(cellphone, dateTag, times, deviceGroupId, communityCode);
            boolean b = dnakeReponseStatus(res);
            if (b) {
                invoke = res;
            }
        }
        return invoke;
    }

    /**
     * 获取我的钥匙
     *
     * @param communityCode 小区code
     * @param cellphone     手机号
     * @return 我的钥匙信息
     * @author Mr.Deng
     * @date 14:08 2018/12/4
     */
//    @Cache(key = "key:cellphone:communityCode:{1}:{2}", expire = 1440)
    public List<MyKey> getMyKey(String cellphone, String communityCode) {
        List<MyKey> list = Lists.newArrayListWithExpectedSize(20);
//        List<MyKey> unitKeys = Lists.newArrayListWithExpectedSize(10);
//        List<MyKey> communityKeys = Lists.newArrayListWithExpectedSize(10);
//        Map<String, Object> maps = Maps.newHashMapWithExpectedSize(2);
        String url = "/auth/api/user/myKey";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("communityCode", communityCode);
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
        if (dnakeReponseStatus(invoke)) {
            JSONObject jsonObject = JSON.parseObject(invoke);
            JSONArray devices = jsonObject.getJSONArray("devices");
            for (Object json : devices) {
                MyKey myKey = JSONObject.parseObject(json.toString(), MyKey.class);
//                String buildingCode = myKey.getBuildingCode();
//                //判断是否为单元机和门口机
//                if (StringUtils.isNotBlank(buildingCode)) {
//                    unitKeys.add(myKey);
//                } else {
//                    communityKeys.add(myKey);
//                }
                list.add(myKey);
            }
        }
        return list;
    }

    /**
     * 获取我的本地数据库钥匙信息
     *
     * @param communityCode 小区code
     * @param cellphone     手机号
     * @return 我的钥匙信息
     * @author Mr.Deng
     * @date 14:08 2018/12/4
     */
//    @Cache(key = "key:cellphone:communityCode:{1}:{2}", expire = 1440)
    public List<MyKey> getMyKeyWithLocal(String cellphone, String communityCode) {
        List<MyKey> list = Lists.newArrayListWithExpectedSize(20);
        /**
         * 查询本地住户信息
         */
        //String[] communityCodeList = communityCode.split(",");
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        /**
         * 查询本地设备授权信息
         */
        List<AuthorizeAppHouseholdDeviceGroup> groups = authorizeAppHouseholdDeviceGroupService.listByHouseholdId(houseHold.getHouseholdId());
        List<Integer> groupList = new ArrayList<>();
        if (groups.size() != 0) {
            for (AuthorizeAppHouseholdDeviceGroup group : groups) {
                if (!groupList.contains(group.getDeviceGroupId())) {
                    groupList.add(group.getDeviceGroupId());
                }
            }
            /**
             *查询已经存在的房屋信息
             */
            //List<HouseholdRoom> rooms = householdRoomService.listByHouseholdId(houseHold.getHouseholdId());
            //if(rooms.size() != 0){
            //for(HouseholdRoom room : rooms){
            List<DeviceDeviceGroup> deviceDeviceGroups = deviceDeviceGroupService.listByDeviceGroupIds(groupList);
            for (DeviceDeviceGroup deviceDeviceGroup : deviceDeviceGroups) {
                MyKey myKey = new MyKey();
                myKey.setSipAccount(houseHold.getSipAccount());
                //myKey.setRoomNum(room.getRoomNum());
                myKey.setDeviceGps("0");
                myKey.setDeviceNum(deviceDeviceGroup.getDeviceNum());
                /**
                 * 时间信息
                 */
                LocalDate residenceTime = houseHold.getResidenceTime();
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = residenceTime.atStartOfDay().atZone(zone).toInstant();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
                myKey.setDueDate(localDateTime);
                list.add(myKey);
            }
            //}
            //}
        }
        return list;
    }

    /**
     * 用户免打扰
     *
     * @param cellphone 手机号
     * @param status    状态 1关，0开
     * @author Mr.Deng
     * @date 15:32 2019/1/2
     */
    public boolean changeSwitch(String cellphone, Integer status) {
        String url = "/auth/api/user/changeSwitch";
        DnakeAppUser dnakeAppUser = getDnakeAppUser(cellphone);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", dnakeAppUser.getAppUserId());
        map.put("sipSwitch", status);
        map.put("callSwitch", status);
        String invoke = this.invokeProxy(url, map, dnakeAppUser, cellphone);
        Integer isSuccess = JSONObject.parseObject(invoke).getInteger("isSuccess");
        return isSuccess == 1;
    }

    /**
     * 更新用户手机号
     *
     * @param householdId    住户id
     * @param communityCode  小区code
     * @param householdName  住户姓名
     * @param householdRooms 住户房号
     * @param mobile         手机号
     * @return boolean
     * @author shuyy
     * @date 2018/12/13 16:09
     * @company mitesofor
     */
    public boolean updateHouseholdCellphone(Integer householdId, String communityCode, String householdName, List<HouseholdRoom> householdRooms, String mobile) {
        String url = "/v1/household/saveOrUpdateHouseholdMore";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("id", householdId);
        List<Map<String, Object>> houseList = Lists.newArrayListWithCapacity(householdRooms.size());
        householdRooms.forEach(item -> {
            Map<String, Object> h = Maps.newHashMapWithExpectedSize(4);
            h.put("zoneId", item.getZoneId());
            h.put("buildingId", item.getBuildingId());
            h.put("unitId", item.getUnitId());
            h.put("roomId", item.getRoomId());
            houseList.add(h);
        });
        String s = JSON.toJSONString(houseList);
        map.put("houseList", s);
        map.put("mobile", mobile);
        map.put("householdName", householdName);
        String result = DnakeWebApiUtil.invoke(url, map);
        Integer isSuccess = JSON.parseObject(result).getInteger("isSuccess");
        if (isSuccess == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通用获取DnakeAppUser对象，通过手机号码
     *
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

    /**
     * 判断狄耐克请求是否成功
     *
     * @param invoke json
     * @return boolean true成功，false失败
     * @author Mr.Deng
     * @date 15:22 2018/12/12
     */
    private boolean dnakeReponseStatus(String invoke) {
        //防止HTTP开门出现"系统繁忙"
        if (invoke.equals("success")) {
            return true;
        }
        JSONObject json = JSONObject.parseObject(invoke);
        String errorCode = json.getString("errorCode");
        if (StringUtils.isNotBlank(errorCode)) {
            return false;
        }
        return true;
    }

    /**
     * 保存住户
     *
     * @param communityCode 小区code
     * @param mobile        手机号
     * @param householdName 住户名
     * @param residenceTime 居住期限
     * @param houseList     房产列表
     * @author shuyy
     * @date 2018/12/18 20:02
     * @company mitesofor
     */
    public JSONObject saveHousehold(String communityCode, String mobile, Integer gender, String householdName,
                                    String residenceTime, List<Map<String, Object>> houseList) {
        String url = "/v1/household/saveOrUpdateHouseholdMore";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        String s = JSON.toJSONString(houseList);
        map.put("houseList", s);
        map.put("mobile", mobile);
        map.put("residenceTime", residenceTime);
        map.put("householdName", householdName);
        map.put("gender", gender);
        String result = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(result);
    }

    /**
     * 授权用户设备组
     *
     * @param communityCode   小区code
     * @param householdId     用户id
     * @param expiryDate      过期时间
     * @param deviceGroupList 设备组id，逗号分隔
     * @author shuyy
     * @date 2018/12/19 9:31
     * @company mitesofor
     */
    public void authorizeHousehold(String communityCode, Integer householdId, String expiryDate, List<String> deviceGroupList) {
        String url = "/v1/household/authorizeHousehold";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        map.put("householdId", householdId);
        map.put("communityCode", communityCode);
        map.put("expiryDate", expiryDate);
        String join = String.join(",", deviceGroupList);
        map.put("deviceGroups", join);
        map.put("appOperateType", "1");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        log.info(invoke);
    }

    /**
     * 注销用户
     *
     * @author shuyy
     * @date 2018/12/19 16:15
     * @company mitesofor
     */
    public void operateHousehold(Integer householdId, String communityCode) {
        String url = "/v1/household/operateHousehold";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("id", householdId);
        map.put("operateType", "0");
        map.put("communityCode", communityCode);
        String result = DnakeWebApiUtil.invoke(url, map);
        log.info(result);
    }

    /**
     * 授权用户设备组
     *
     * @param communityCode   小区code
     * @param householdId     用户id
     * @param expiryDate      过期时间
     * @param deviceGroupList 设备组id，逗号分隔
     * @author hsl
     * @date 2018/12/19 9:31
     * @company mitesofor
     */
    public JSONObject authorizeHouse(String communityCode, Integer householdId, String expiryDate, String appOperateType, List<String> deviceGroupList) {
        String join = String.join(",", deviceGroupList);
        String url = "/v1/household/authorizeHousehold";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        map.put("householdId", householdId);
        map.put("communityCode", communityCode);
        map.put("expiryDate", expiryDate);
        map.put("deviceGroups", join);
        map.put("appOperateType", appOperateType);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(invoke);
    }

    /**
     * 新增住户
     *
     * @param communityCode 小区code
     * @param mobile        手机号
     * @param householdName 住户名
     * @param residenceTime 居住期限
     * @param houseList     房产列表
     * @author shuyy
     * @date 2018/12/18 20:02
     * @company mitesofor
     */
    public JSONObject addHousehold(Integer id, String communityCode, Integer zoneId, Integer buildingId, Integer unitId, Integer roomId,
                                   Integer householdType, String householdName,
                                   String mobile, String residenceTime, List<Map<String, Object>> houseList) {
        String url = "/v1/household/saveOrUpdateHousehold";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(9);
        map.put("id", id);
        map.put("communityCode", communityCode);
        map.put("zoneId", zoneId);
        map.put("buildingId", buildingId);
        map.put("unitId", unitId);
        map.put("roomId", roomId);
        map.put("householdType", householdType);
        map.put("householdName", householdName);
        map.put("mobile", mobile);
        String result = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(result);
    }

    /**
     * 保存住户(返回参数)
     *
     * @param communityCode 小区code
     * @param mobile        手机号
     * @param householdName 住户名
     * @param residenceTime 居住期限
     * @param houseList     房产列表
     * @author shuyy
     * @date 2018/12/18 20:02
     * @company mitesofor
     */
    public JSONObject editHouseholdRoom(Integer id, String communityCode, String mobile, Integer gender, String householdName,
                                        String residenceTime, List<Map<String, Object>> houseList) {
        String url = "/v1/household/saveOrUpdateHouseholdMore";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("id", id);
        map.put("communityCode", communityCode);
        String s = JSON.toJSONString(houseList);
        map.put("houseList", s);
        map.put("mobile", mobile);
        map.put("residenceTime", residenceTime);
        map.put("householdName", householdName);
        map.put("gender", gender);
        String result = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(result);
    }

    /**
     * 注销用户(返回参数)
     *
     * @author shuyy
     * @date 2018/12/19 16:15
     * @company mitesofor
     */
    public JSONObject logOut(Integer householdId, String communityCode) {
        String url = "/v1/household/operateHousehold";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("id", householdId);
        map.put("operateType", "0");
        map.put("communityCode", communityCode);
        String result = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(result);
    }

    /**
     * 根据设备编号查询设备组列表
     *
     * @param communityCode
     * @return
     */
    public JSONObject getDeviceGroupList(String communityCode) {
        String url = "/v1/deviceGroup/getDeviceGroupList";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("communityCode", communityCode);
        String result = DnakeWebApiUtil.invoke(url, map);
        return JSON.parseObject(result);
    }

}
