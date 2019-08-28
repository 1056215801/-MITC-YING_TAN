package com.mit.community.module.communitymanagement.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Zone;
import com.mit.community.service.RedisService;
import com.mit.community.service.ZoneService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author HuShanLin
 * @Date Created in 10:44 2019/8/19
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/zone")
@Slf4j
@Api(tags = "分区管理")
public class ZoneController {

        @Autowired
        private ZoneService zoneService;
        @Autowired
        private RedisService redisService;
        @PostMapping("/save")
        @ApiOperation(value = "新增分区信息",notes="传参：String zoneName 分区名称，Integer zoneType 分区类型，String zoneCode 分区编号，Integer sort 排序")
        public Result save(String zoneName, Integer zoneType, String zoneCode, Integer sort, HttpServletRequest request){
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            String communityCode = user.getCommunityCode();

            zoneService.save(zoneName,zoneType,zoneCode,sort,communityCode);
            return Result.success("保存成功");
        }

        @PostMapping("/update")
        @ApiOperation(value = "分区管理修改",notes="传参：String zoneName 分区名称，Integer zoneType 分区类型，Integer sort 排序")
        public Result update(String zoneName,Integer zoneType,Integer sort,Integer id){
            Zone zone=new Zone();
            zone.setId(id);
            zone.setZoneName(zoneName);
            zone.setZoneType(zoneType);
            zone.setSort(sort);
            zone.setGmtModified(LocalDateTime.now());
//            zoneService.update(zoneName,zoneType,sort,id);
            zoneService.updateById(zone);
            return Result.success("更新成功");
        }
        @GetMapping("/getZoneList")
        @ApiOperation(value = "查询分区",notes="传参：String zoneName 分区名称，Integer zoneType 分区类型，Integer zoneStatus 账号状态")
        public Result getZoneList(String zoneName,Integer zoneType,Integer zoneStatus,Integer pageNum,Integer pageSize,HttpServletRequest request){
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            String communityCode = user.getCommunityCode();
            Page<Zone> page = zoneService.getZoneList(zoneName, zoneType, zoneStatus, pageNum, pageSize,communityCode);
            return Result.success(page);
        }
        @PostMapping("/delete")
        @ApiOperation(value = "删除分区",notes="传参：Integer id 分区id")
        public Result delete(@RequestParam(value = "idList") List<Integer> idList){
            Result result=zoneService.delete(idList);
            return result;
        }

    /**
     *
     * @param idList
     * @return
     * @deprecated 禁用分区
     */
       @ApiOperation(value = "禁用分区",notes="传参：List<Integer> idList 分区id集合")
       @ApiImplicitParam(name = "idList", value = "id集合",  allowMultiple = true, dataType = "Integer", paramType = "query")
        @PostMapping("/stop")
        public Result stop(@RequestParam(value = "idList") List<Integer> idList){
            Result result = zoneService.stop(idList);

            return result;
        }

    /**
     *
     * @param idList
     * @return
     * @deprecated 启用分区
     */
        @ApiOperation(value = "启用分区",notes="传参：List<Integer> idList 分区id集合")
        @GetMapping("/enable")
        public Result enable(@RequestParam(value = "idList") List<Integer> idList){
            zoneService.enable(idList);
             return Result.success("启用成功");
        }
}

