package com.mit.community.module.communitymanagement.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Building;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Unit;
import com.mit.community.service.BuildingService;
import com.mit.community.service.RedisService;
import com.mit.community.service.UnitService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author qishengjun
 * @Date Created in 17:43 2019/8/19
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/build")
@Slf4j
@Api(tags = "楼栋管理")
public class buildController {
     @Autowired
     private BuildingService buildingService;
     @Autowired
     private UnitService unitService;
     @Autowired
     private RedisService redisService;
     @ApiOperation(value = "新增楼栋",notes = "传参：Integer zoneId 分区id，String buildingName 楼栋名字,String buildingCode 楼栋编码,Integer sort 排序")
    @PostMapping("/save")
    public Result save(Integer zoneId, String buildingName, String buildingCode, Integer sort, HttpServletRequest request){
         String sessionId = CookieUtils.getSessionId(request);
         SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
         String communityCode = user.getCommunityCode();
         buildingService.save(zoneId,buildingName,buildingCode,sort,communityCode);
        return Result.success("新增成功");

    }
    @ApiOperation(value = "修改楼栋",notes = "传参：Integer id 楼栋id，String buildingName 楼栋名字,Integer sort 排序")
    @PostMapping("/put")
    public Result put(Integer id,String BuildingName,Integer sort){
           Building building=null;
           if (id!=null){
                building = buildingService.selectById(id);
           }
      if (building!=null){
               building.setBuildingName(BuildingName);
               building.setSort(sort);
               building.setGmtModified(LocalDateTime.now());
      }
        boolean b = buildingService.updateById(building);
        if (b){
               return Result.success("修改成功");
           }else {
               return Result.error("修改失败");
           }

    }
    @ApiOperation(value = "删除楼栋",notes = "传参：List<Integer> idList 楼栋id集合")
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "idList") List<Integer> idList){
         if (idList==null){
             return Result.error("参数异常");
         }
        for (int i = 0; i <idList.size() ; i++) {
            EntityWrapper<Building> buildingEntityWrapper=new EntityWrapper<>();
            buildingEntityWrapper.eq("id",idList.get(i));
            Building building = buildingService.selectOne(buildingEntityWrapper);
            EntityWrapper<Unit> wrapper=new EntityWrapper<>();
            wrapper.eq("building_id",building.getBuildingId());
            List<Unit> unitList = unitService.selectList(wrapper);
            if (unitList.size()>0){
                return Result.error("楼栋已被单元关联，不能删除");
            }
        }
        buildingService.deleteBatchIds(idList);
        return Result.success("删除成功");
    }
    @ApiOperation(value = "启用楼栋",notes = "传参：List<Integer> idList 楼栋id集合")
    @GetMapping("/enable")
    public Result enable(@RequestParam(value = "idList") List<Integer> idList){
         if (idList==null){
             return Result.error("参数异常");
         }
         List<Building> buildingList=new ArrayList<>();
        for (int i = 0; i <idList.size() ; i++) {
            Building building=new Building();
            building.setBuildingStatus(1);
            building.setId(idList.get(i));
            buildingList.add(building);
        }
        buildingService.updateBatchById(buildingList);
        return Result.success("启用楼栋成功");
  }
    @ApiOperation(value = "禁用楼栋",notes = "传参：List<Integer> idList 楼栋id集合")
    @GetMapping("/stop")
    public Result stop(@RequestParam(value = "idList") List<Integer> idList){
         if (idList==null){
             return Result.error("参数异常");
         }
        for (int i = 0; i <idList.size() ; i++) {
             EntityWrapper<Building> buildingEntityWrapper=new EntityWrapper<>();
             buildingEntityWrapper.eq("id",idList.get(i));
            Building building = buildingService.selectOne(buildingEntityWrapper);
            EntityWrapper<Unit> wrapper=new EntityWrapper<>();
            wrapper.eq("building_id",building.getBuildingId());
            List<Unit> unitList = unitService.selectList(wrapper);
            if (unitList.size()>0){
                return Result.error("楼栋已被单元关联不能停用");
            }
        }
        List<Building> buildingList=new ArrayList<>();
        for (int i = 0; i <idList.size() ; i++) {
            Building building=new Building();
            building.setBuildingStatus(0);
            building.setId(idList.get(i));
            buildingList.add(building);
        }
        buildingService.updateBatchById(buildingList);
         return Result.success("停用楼栋成功");
  }
      @ApiOperation(value = "查询楼栋",notes = "传参：Building building 楼栋对象,Integer pageNum 当前页,Integer pageSize 每页显示的记录数")
      @GetMapping("/getbuildingList")
      public Result getBuildingList(Building building,Integer pageNum,Integer pageSize,HttpServletRequest request){
          String sessionId = CookieUtils.getSessionId(request);
          SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
          String communityCode = user.getCommunityCode();
         Page<Building> page=buildingService.getbuildingList(building,pageNum,pageSize,communityCode);
         return Result.success(page);
      }
}
