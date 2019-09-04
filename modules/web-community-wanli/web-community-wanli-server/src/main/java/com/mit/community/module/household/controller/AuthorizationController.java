package com.mit.community.module.household.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.HouseholdRoomService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author qishengjun
 * @Date Created in 9:12 2019/8/29
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/authorization")
@Slf4j
@Api(tags = "住户登记授权")
public class AuthorizationController {

    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private HouseholdRoomService householdRoomService;
    @ApiOperation("获取第一步修改信息")
    @ApiImplicitParam(name = "householdId",value = "住户id",required = true,dataType = "Integer",paramType = "query")
    @PostMapping("/getModifyInfo")
    public Result getModifyInfo(Integer householdId){
        EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
        wrapper.eq("household_id",householdId);
        HouseHold houseHold = houseHoldService.selectOne(wrapper);
        EntityWrapper<HouseholdRoom> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("household_id",householdId);
        HouseholdRoom householdRoom = householdRoomService.selectOne(entityWrapper);
        HashMap<String,Object> map=new HashMap<>();
        map.put("houseHold",houseHold);
        map.put("householdRoom",householdRoom);
        return Result.success(map);

    }

   /* @PostMapping("/getInfoList")
    public Result getInfoList(String householdName, String mobile, Integer zoneId,
                              Integer buildingId, Integer unitId, String roomNum,
                              Short householdType, Integer householdStatus,
                              Date validityTime,Integer authorizeStatus,Integer pageNum,Integer pageSize)
    {
        if (houseHoldService == null) {
            System.out.println("出现空指针");
        }
        *//*Page<HouseHold> page=householdRoomService.getInfoList(householdName,mobile,zoneId,
                buildingId,unitId,roomNum,householdType,householdStatus,validityTime,authorizeStatus,pageNum,pageSize);*//*
        //HouseHold houseHold = houseHoldService.selectById(1372220);
        Page<HouseHold> page=houseHoldService.getInfoList(householdName,mobile,zoneId,
                buildingId,unitId,roomNum,householdType,householdStatus,validityTime,authorizeStatus,pageNum,pageSize);
        return Result.success(page);
    }*/
    @ApiOperation("注销")
    @ApiImplicitParam(name = "householdId",value = "住户id",required = true,dataType = "Integer",paramType = "query")
    @PostMapping("/logout")
    public Result logout(Integer householdId){
        EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
        wrapper.eq("household_id",householdId);
        houseHoldService.delete(wrapper);
        EntityWrapper<HouseholdRoom> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("household_id",householdId);
        householdRoomService.delete(entityWrapper);
        return Result.success("success");
    }
}
