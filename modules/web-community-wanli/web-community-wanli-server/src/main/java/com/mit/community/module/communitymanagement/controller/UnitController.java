package com.mit.community.module.communitymanagement.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Room;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Unit;
import com.mit.community.service.RedisService;
import com.mit.community.service.RoomService;
import com.mit.community.service.UnitService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author qishengjun
 * @Date Created in 14:47 2019/8/20
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/unit")
@Slf4j
@Api(tags = "单元管理")
public class UnitController {

    @Autowired
    private UnitService unitService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RedisService redisService;
    @ApiOperation(value = "新增单元",notes = "传参：Unit unit 单元对象")
    @PostMapping("/save")
    public Result save(Unit unit, HttpServletRequest request){
        if (unit==null){
            return Result.error("参数异常");
        }
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        EntityWrapper<Unit> wrapper=new EntityWrapper<>();
        wrapper.orderBy("unit_id",false);
        wrapper.last("limit 1");
         Unit unitMax = unitService.selectOne(wrapper);
         unit.setCommunityCode(communityCode);
         unit.setUnitId(unitMax.getUnitId()+1);
         unit.setGmtCreate(LocalDateTime.now());
         unit.setGmtModified(LocalDateTime.now());
         unit.setUnitStatus(1);
         unitService.insert(unit);
         return Result.success("新增成功");
    }
    @ApiOperation(value = "修改单元",notes = "传参：Integer id 单元id,String unitName 单元名字,Integer sort 单元排序")
    @PostMapping("/update")
    public Result update(Integer id,String unitName,Integer sort){
        if (id==null){
            return Result.error("参数异常");
        }
        if (StringUtils.isEmpty(unitName))
        {
            return Result.error("参数异常");
        }
        if (sort==null){
            return Result.error("参数异常");
        }
        Unit unit = unitService.selectById(id);
        if (unit!=null){

            unit.setUnitName(unitName);
            unit.setSort(sort);
            unit.setGmtModified(LocalDateTime.now());
        }
        boolean b = unitService.updateById(unit);
        if (b){
            return Result.success("修改成功");

        }else {
            return Result.error("修改失败");
        }
    }
    @ApiOperation(value = "删除单元",notes = "传参：List<Integer> idList id集合")
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "idList")List<Integer> idList){
        if (idList==null){
            return Result.error("参数异常");
        }
        Result result=unitService.deleteByIdList(idList);
        return result;
    }
    @ApiOperation(value = "启用单元",notes = "传参：List<Integer> idList id集合")
    @GetMapping("/enable")
    public Result enable(@RequestParam(value = "idList")List<Integer> idList){
        if (idList==null){
            return Result.error("参数异常");
        }
        List<Unit> unitList=new ArrayList<>();
        for (int i = 0; i <idList.size() ; i++) {
            Unit unit=new Unit();
            unit.setUnitStatus(1);
            unit.setId(idList.get(i));
            unit.setGmtModified(LocalDateTime.now());
            unitList.add(unit);
        }
        unitService.updateBatchById(unitList);
        return Result.success("启用单元成功");
    }
    @ApiOperation(value = "停用单元",notes = "传参：List<Integer> idList id集合")
    @GetMapping("/stop")
    public Result stop(@RequestParam(value = "idList")List<Integer> idList){
        for (int i = 0; i <idList.size() ; i++) {
            EntityWrapper<Unit> entityWrapper=new EntityWrapper<>();
            entityWrapper.eq("id",idList.get(i));
            Unit unit = unitService.selectList(entityWrapper).get(0);
            EntityWrapper<Room> wrapper=new EntityWrapper<>();
            wrapper.eq("unit_id",unit.getUnitId());
            List<Room> roomList = roomService.selectList(wrapper);
            if (roomList.size()>0){
                return Result.error("单元已经被房屋关联不能停用");
            }
        }
        List<Unit> unitList=new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Unit unit=new Unit();
            unit.setId(idList.get(i));
            unit.setUnitStatus(0);
            unit.setGmtModified(LocalDateTime.now());
            unitList.add(unit);
        }
        unitService.updateBatchById(unitList);
       return Result.success("停用单元成功");
    }
    @ApiOperation(value = "获取单元列表",notes = "传参：Unit unit 单元对象,Integer pageNum 当前页,Integer pageSize 每页显示记录数")
     @PostMapping("/unitList")
    public Result getUnitList(Unit unit,Integer pageNum,Integer pageSize,HttpServletRequest request){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        if (unit==null){
           return Result.error("参数异常");
        }
        Page<Unit> page=unitService.getUnitList(unit,pageNum,pageSize,communityCode);
        return Result.success(page);
     }
}
