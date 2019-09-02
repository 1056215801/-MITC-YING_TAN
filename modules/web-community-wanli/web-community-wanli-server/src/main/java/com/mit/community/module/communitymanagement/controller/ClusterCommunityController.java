package com.mit.community.module.communitymanagement.controller;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.entity.SysUser;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;
import com.mit.community.service.RedisService;
import com.mit.community.service.SysUserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Author qishengjun
 * @Date Created in 9:43 2019/8/19
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/clusterCommunity")
@Slf4j
@Api(tags = "小区管理")
public class ClusterCommunityController {
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DeviceService deviceService;
    @ApiOperation(value = "修改小区基本信息",notes = "传参：ClusterCommunity clusterCommunity 小区对象")
/*    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户" ,required = true,dataType = "String", paramType = "body")
    } )*/
    @PostMapping("/update")
    public Result update(ClusterCommunity clusterCommunity){
          if (clusterCommunity==null){
              return Result.error("参数异常");
          }
          clusterCommunity.setGmtModified(LocalDateTime.now());
//          clusterCommunityService.insert(clusterCommunity);
          clusterCommunityService.updateById(clusterCommunity);
       return Result.success("更新成功");
    }
    @ApiOperation(value = "新增小区基本信息",notes = "传参：String communityName 小区名字,String provinceName 省份名称,String provinceId 省份编码,\n" +
            "                       String cityName 城市名称,String cityId 城市编码,String areaName 区域名称,String areaId 区域编码,\n" +
            "                       String streetName 街道名称,String streetId 街道编码,String committee 居委名称,String committeeId 居委编码,String address 地址,String communityType 小区类型,\n" +
            "                       String username 用户名,String password 密码,String adminName 管理员,String phone 电话号码,String managementUnit 管理单位,\n" +
            "                       String remark 备注")
    @PostMapping("/save")
    public Result save(String communityName,String provinceName,String provinceId,
                       String cityName,String cityId,String areaName,String areaId,
                       String streetName,String streetId,String committee,String committeeId,String address,String communityType,
                       String username,String password,String adminName,String phone,String managementUnit,
                       String remark){
        EntityWrapper<SysUser> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("username",username);
        List<SysUser> sysUserList = sysUserService.selectList(entityWrapper);
        if (sysUserList.size()>0){
            return Result.error("当前用户已存在");
        }
        SysUser sysUser=new SysUser();
        ClusterCommunity clusterCommunity=new ClusterCommunity();
        String communityCode = UUID.randomUUID().toString().replace("-", "");
        clusterCommunity.setCommunityCode(communityCode);
        clusterCommunity.setGmtModified(LocalDateTime.now());
        clusterCommunity.setGmtCreate(LocalDateTime.now());
        EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
        wrapper.orderBy("community_id",false);
        wrapper.last("limit 1");
        ClusterCommunity clusterCommunityMax = clusterCommunityService.selectOne(wrapper);
        clusterCommunity.setCommunityId(clusterCommunityMax.getCommunityId()+1);
        clusterCommunity.setAreaBelong(areaName);
        if (StringUtils.isNotEmpty(communityName)){
            clusterCommunity.setCommunityName(communityName);
            sysUser.setCommunityName(communityName);
        }
        if (StringUtils.isNotEmpty(provinceName)){
            clusterCommunity.setProvinceName(provinceName);
            sysUser.setProvinceName(provinceName);
        }
        if (StringUtils.isNotEmpty(cityName))
        {
            clusterCommunity.setCityName(cityName);
            sysUser.setCityName(cityName);
        }
        if (StringUtils.isNotEmpty(areaName)){
            clusterCommunity.setAreaName(areaName);
            sysUser.setAreaName(areaName);
        }
        if (StringUtils.isNotEmpty(streetName)){
            clusterCommunity.setStreetName(streetName);
            sysUser.setStreetName(streetName);
        }
        if (StringUtils.isNotEmpty(committee)){
            clusterCommunity.setCommittee(committee);
            sysUser.setCommittee(committee);
        }
        if (StringUtils.isNotEmpty(address)){
            clusterCommunity.setAddress(address);
            sysUser.setAddress(address);
        }
        if (StringUtils.isNotEmpty(communityType)){
            clusterCommunity.setCommunityType(communityType);
        }
        if (StringUtils.isNotEmpty(remark)){
            clusterCommunity.setRemark(remark);
            sysUser.setRemark(remark);
        }
        if (StringUtils.isNotEmpty(provinceId)){
            clusterCommunity.setProvinceId(provinceId);
        }
        if (StringUtils.isNotEmpty(cityId))
        {
            clusterCommunity.setCityId(cityId);
        }
        if (StringUtils.isNotEmpty(areaId))
        {
            clusterCommunity.setAreaId(areaId);
        }
        if (StringUtils.isNotEmpty(streetId))
        {
            clusterCommunity.setStreetId(streetId);
        }
        if (StringUtils.isNotEmpty(committeeId))
        {
            clusterCommunity.setCommitteeId(committeeId);
        }
        if (StringUtils.isNotEmpty(username)){
            sysUser.setUsername(username);
        }
        if (StringUtils.isNotEmpty(password)){
            sysUser.setPassword(password);
        }else{
            sysUser.setPassword("654321");
        }
        if (StringUtils.isNotEmpty(adminName)){
            sysUser.setAdminName(adminName);
        }
        if (StringUtils.isNotEmpty(phone)){
            sysUser.setPhone(phone);
        }
        if (StringUtils.isNotEmpty(managementUnit))
        {
            sysUser.setManagementUnit(managementUnit);
        }
        clusterCommunityService.insert(clusterCommunity);
        sysUser.setCommunityCode(communityCode);
        sysUser.setRole("小区管理员");
        sysUser.setCreatetime(LocalDateTime.now());
        sysUser.setAlterTime(LocalDateTime.now());
        sysUserService.insert(sysUser);
        return Result.success("新增成功");

    }
    @ApiOperation(value = "获取小区管理账户信息")
    @GetMapping("/communityInfo")
    public Result getCommunityInfo(HttpServletRequest request)
    {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
        wrapper.eq("community_code",communityCode);
        ClusterCommunity clusterCommunity = clusterCommunityService.selectOne(wrapper);
        String communityName = clusterCommunity.getCommunityName();
        user.setCommunityName(communityName);
        user.setCommunityType(clusterCommunity.getCommunityType());
        user.setPassword(null);
        return Result.success(user);
    }
    @ApiOperation(value = "删除小区",notes = "传参：List<Integer> idList id集合")
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "idList")List<Integer> idList){
        for (int i = 0; i < idList.size(); i++) {
            ClusterCommunity clusterCommunity = clusterCommunityService.selectById(idList.get(i));
            String communityCode = clusterCommunity.getCommunityCode();
            EntityWrapper<Device> wrapper=new EntityWrapper<>();
            wrapper.eq("community_code",communityCode);
            List<Device> deviceList = deviceService.selectList(wrapper);
            if (deviceList.size()>0){
                return Result.error("该小区已关联设备，不能删除");
            }
        }
        clusterCommunityService.deleteBatchIds(idList);
        return Result.success("删除成功");
    }
    @ApiOperation(value = "修改小区",notes = "传参：Integer id,String communityName 小区名称,String provinceName 省,\n" +
            "                         String cityName 市,String areaName 区,\n" +
            "                         String streetName 街道,String committeem 居委,String address地址,String communityType 小区类型,\n" +
            "                         String username 账户,String password 密码,String adminName 管理员,String phone 联系电话,String managementUnit 管理单位,\n" +
            "                         String remark 备注")
    @PostMapping("/updateCommunity")
    public Result update(Integer id,String communityName,String provinceName,String provinceId,
                         String cityName,String cityId,String areaName,String areaId,
                         String streetName,String streetId,String committee,String committeeId,String address,String communityType,
                         String username,String password,String adminName,String phone,String managementUnit,
                         String remark){
        EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
        wrapper.eq("id",id);
        ClusterCommunity clusterCommunity = clusterCommunityService.selectOne(wrapper);
        String communityCode = clusterCommunity.getCommunityCode();
        EntityWrapper<SysUser> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("community_code",communityCode);
        SysUser sysUser = sysUserService.selectOne(entityWrapper);
        if (StringUtils.isNotEmpty(communityName)){
            clusterCommunity.setCommunityName(communityName);
            sysUser.setCommunityName(communityName);
        }
        if (StringUtils.isNotEmpty(provinceName)){
            clusterCommunity.setProvinceName(provinceName);
            sysUser.setProvinceName(provinceName);
        }
        if (StringUtils.isNotEmpty(cityName))
        {
            clusterCommunity.setCityName(cityName);
            sysUser.setCityName(cityName);
        }
        if (StringUtils.isNotEmpty(areaName)){
            clusterCommunity.setAreaName(areaName);
            sysUser.setAreaName(areaName);
        }
        if (StringUtils.isNotEmpty(streetName)){
            clusterCommunity.setStreetName(streetName);
            sysUser.setStreetName(streetName);
        }
        if (StringUtils.isNotEmpty(committee)){
            clusterCommunity.setCommittee(committee);
             sysUser.setCommittee(committee);
        }
        if (StringUtils.isNotEmpty(address)){
            clusterCommunity.setAddress(address);
            sysUser.setAddress(address);
        }
        if (StringUtils.isNotEmpty(communityType)){
            clusterCommunity.setCommunityType(communityType);
        }
        if (StringUtils.isNotEmpty(remark)){
            clusterCommunity.setRemark(remark);
            sysUser.setRemark(remark);
        }
        if (StringUtils.isNotEmpty(provinceId)){
            clusterCommunity.setProvinceId(provinceId);
        }
        if (StringUtils.isNotEmpty(cityId))
        {
            clusterCommunity.setCityId(cityId);
        }
        if (StringUtils.isNotEmpty(areaId))
        {
            clusterCommunity.setAreaId(areaId);
        }
        if (StringUtils.isNotEmpty(streetId))
        {
            clusterCommunity.setStreetId(streetId);
        }
        if (StringUtils.isNotEmpty(committeeId))
        {
            clusterCommunity.setCommitteeId(committeeId);
        }
        if (StringUtils.isNotEmpty(username)){
            sysUser.setUsername(username);
        }
        if (StringUtils.isNotEmpty(password)){
            sysUser.setPassword(password);
        }
        if (StringUtils.isNotEmpty(adminName)){
            sysUser.setAdminName(adminName);
        }
        if (StringUtils.isNotEmpty(phone)){
            sysUser.setPhone(phone);
        }
        if (StringUtils.isNotEmpty(managementUnit))
        {
            sysUser.setManagementUnit(managementUnit);
        }
        clusterCommunity.setGmtModified(LocalDateTime.now());
        sysUser.setAlterTime(LocalDateTime.now());
        sysUserService.updateById(sysUser);
        clusterCommunityService.updateById(clusterCommunity);
            return Result.success("修改成功");
    }
    @ApiOperation(value = "获取小区列表")
    @PostMapping("/communityList")
    public Result getCommunityList(String communityName,String communityCode,String username,String provinceName, String cityName,String areaName,String streetName,
                                   String committee,String communityType,Integer pageNum,Integer pageSize) {
        Page<ClusterCommunity> page = clusterCommunityService.getCommunityList(communityName, communityCode, username, provinceName, cityName, areaName, streetName,communityType, pageNum, pageSize,committee);

        return Result.success(page);
    }

    @ApiOperation(value = "获取小区基本信息")
    @PostMapping("/getBaseInfo")
    public Result getBaseInfo(HttpServletRequest request){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
        wrapper.eq("community_code",communityCode);
        ClusterCommunity clusterCommunity = clusterCommunityService.selectOne(wrapper);
        return Result.success(clusterCommunity);
    }
   /* @ApiOperation(value = "获取修改信息")
    @PostMapping("/getModifyInfo")
    public Result getModifyInfo(Integer id){
        ClusterCommunity clusterCommunity=clusterCommunityService.getModifyInfo(id);
        return Result.success(clusterCommunity);
    }*/
}
