package com.mit.community.module.communitymanagement.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Author qishengjun
 * @Date Created in 19:09 2019/8/22
 * @Company: mitesofor </p>
 * @Description:~
 */
@RestController
@RequestMapping(value = "/room")
@Slf4j
@Api(tags = "房屋管理")
public class RoomController {
   @Autowired
   private RoomService roomService;
   @Autowired
   private HouseHoldService houseHoldService;
   @Autowired
   private ZoneService zoneService;
   @Autowired
   private ClusterCommunityService clusterCommunityService;
   @Autowired
   private RedisService redisService;
   @Autowired
   private HouseMessageService houseMessageService;
    @ApiOperation(value = "新增房间",notes = "传参：Integer zoneId 分区id,Integer buildingId 楼栋id,Integer unitId 单元id,String roomNum 房间号")
    @PostMapping("/save")
    public Result save(Integer zoneId, Integer buildingId, Integer unitId, String roomNum, HttpServletRequest request){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Room room=new Room();
        EntityWrapper<Room> wrapper=new EntityWrapper<>();
        if (zoneId!=null){
            wrapper.eq("zone_id",zoneId);

        }
        if (buildingId!=null){
            wrapper.eq("building_id",buildingId);
        }
        if (unitId!=null){
            wrapper.eq("unit_id",unitId);
        }
        if (StringUtils.isNotEmpty(roomNum)){
            wrapper.eq("room_num",roomNum);
        }
        List<Room> roomList = roomService.selectList(wrapper);
        if (roomList.size()>0){
            return Result.error("该单元下的房间已经存在");
        }
        room.setZoneId(zoneId);
        room.setBuildingId(buildingId);
        room.setUnitId(unitId);
        room.setRoomNum(roomNum);
        room.setGmtCreate(LocalDateTime.now());
        room.setGmtModified(LocalDateTime.now());
        room.setRoomStatus(1);
        room.setCommunityCode(communityCode);
        EntityWrapper<Room> entityWrapper=new EntityWrapper<>();
        entityWrapper.orderBy("room_id");
        entityWrapper.last("limit 1");
        Room roomMax = roomService.selectOne(entityWrapper);
        room.setRoomId(roomMax.getRoomId()+1);
        roomService.insert(room);
        return Result.success("新增成功");
    }

    @ApiOperation(value = "启用房间",notes = "传参：List<Integer> idList id集合")
   @PostMapping("/enable")
   public Result enable(@RequestParam(value = "idList") List<Integer> idList) {
       List<Room> roomList = roomService.selectBatchIds(idList);
       for (Room room : roomList) {
           room.setGmtModified(LocalDateTime.now());
           room.setRoomStatus(1);
       }
       roomService.updateBatchById(roomList);
       return Result.success("启用成功");
   }
   @ApiOperation(value = "停用房间",notes = "传参：List<Integer> idList id集合")
  @PostMapping("/stop")
  public Result stop(@RequestParam(value = "idList") List<Integer> idList){
      List<Room> roomList = roomService.selectBatchIds(idList);
      for (Room room : roomList) {
          String communityCode = room.getCommunityCode();
          EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
          wrapper.eq("community_code",communityCode);
          List<HouseHold> houseHoldList = houseHoldService.selectList(wrapper);
          if (houseHoldList.size()>0){
              return Result.error("房间已被业主关联不能停用");
          }
          room.setRoomStatus(0);
          room.setGmtModified(LocalDateTime.now());
      }
      roomService.updateBatchById(roomList);
      return Result.success("停用成功");
  }
  @ApiOperation(value = "删除房间",notes = "传参：List<Integer> idList id集合")
  @PostMapping("/delete")
  public Result delete(@RequestParam(value = "idList") List<Integer> idList){
      List<Room> roomList = roomService.selectBatchIds(idList);
      for (Room room : roomList) {
          String communityCode = room.getCommunityCode();
          EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
          wrapper.eq("community_code",communityCode);
          List<HouseHold> houseHoldList = houseHoldService.selectList(wrapper);
          if (houseHoldList.size()>0){
              return Result.error("房间已被业主关联不能删除");
          }
         roomService.deleteBatchIds(idList);
          room.setGmtModified(LocalDateTime.now());
      }
      return Result.success("删除成功");
  }
    @ApiOperation(value = "批量添加房间",notes = "传参：Integer zoneId 分区id,Integer buildingId 楼栋id,Integer unitId 单元id\n" +
            "           ,Integer minFloor 最小楼层,Integer maxFloor最大楼层,Integer minRoom 最小房间号,Integer maxRoom 最大房间号")
   @PostMapping("/batchAdd")
    public Result batchAdd(Integer zoneId,Integer buildingId,Integer unitId
           ,Integer minFloor,Integer maxFloor,Integer minRoom,Integer maxRoom,HttpServletRequest request){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Result result=Result.error("该区间所有的房屋号都已经存在");
        String FloorStr="";
        String RoomStr="";
        String roomNum="";
       for (int i=minFloor;i<=maxFloor;i++){
            for (int j=minRoom;j<=maxRoom;j++){
                if (i<10){
                    FloorStr="0"+i;
                }else{
                    FloorStr=Integer.toString(i);
                }
                if (j<10){
                    RoomStr="0"+j;
                }else {
                    RoomStr=Integer.toString(j);
                }
              roomNum=FloorStr+RoomStr;
                EntityWrapper<Room> wrapper=new EntityWrapper<>();
                wrapper.eq("zone_id",zoneId);
                wrapper.eq("building_id",buildingId);
                wrapper.eq("unit_id",unitId);
                wrapper.eq("room_num",roomNum);
                List<Room> roomList = roomService.selectList(wrapper);
                if (roomList.size()>0){
                    continue;
                }else {
                    Room room=new Room();
                    room.setZoneId(zoneId);
                    room.setBuildingId(buildingId);
                    room.setUnitId(unitId);
                    room.setRoomNum(roomNum);
                    room.setRoomStatus(1);
                    room.setCommunityCode(communityCode);
                    room.setGmtCreate(LocalDateTime.now());
                    room.setGmtModified(LocalDateTime.now());
                    EntityWrapper<Room> entityWrapper=new EntityWrapper<>();
                    entityWrapper.orderBy("room_id",false);
                    entityWrapper.last("limit 1");
                    Room roomMax = roomService.selectOne(entityWrapper);
                    room.setRoomId(roomMax.getRoomId()+1);
                    roomService.insert(room);
                    result=Result.success("创建房屋成功");
                }

            }
       }
       return result;
   }
    @ApiOperation(value = "获取房间列表",notes = "传参：Integer zoneId 分区id,Integer buildingId 楼栋id,\n" +
            "                              Integer unitId 单元id,Integer roomStatus 房间状态,Integer pageNum 当前页,Integer pageSize 每页显示的记录数")
   @PostMapping("/roomList")
    public Result getRoomList(Integer zoneId,Integer buildingId,
                              Integer unitId,Integer roomStatus,Integer pageNum,Integer pageSize,HttpServletRequest request){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Page<Room> page=roomService.getRoomList(zoneId,buildingId,unitId,roomStatus,pageNum,pageSize,communityCode);
        return Result.success(page);
   }
    @ApiOperation(value = "保存房屋信息")
    @PostMapping("/roomInfo")
    public Result roomInfo(HouseMessage houseMessage){
        if (houseMessage==null){
            return Result.error("参数异常");
        }
        houseMessage.setGmtCreate(LocalDateTime.now());
        houseMessage.setGmtModified(LocalDateTime.now());
        houseMessageService.insert(houseMessage);
        return Result.success("保存成功");
   }
    @ApiOperation(value = "获取房屋地址")
    @GetMapping("/roomArea")
    public Result getRoomArea(HttpServletRequest request){
         String sessionId = CookieUtils.getSessionId(request);
         SysUser user =(SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
         String communityCode = user.getCommunityCode();
         EntityWrapper<ClusterCommunity> wrapper=new EntityWrapper<>();
         wrapper.eq("community_code",communityCode);
         ClusterCommunity clusterCommunity = clusterCommunityService.selectOne(wrapper);
        String address = clusterCommunity.getAddress();
        String provinceName = clusterCommunity.getProvinceName();
        String cityName = clusterCommunity.getCityName();
        String areaName = clusterCommunity.getAreaName();
        String streetName = clusterCommunity.getStreetName();
        address=provinceName+cityName+areaName+streetName+address;
        return Result.success(address);
    }

    @PostMapping("/update")
    public Result update(Integer id,Integer zoneId,Integer buildingId,Integer unitId,String roomNum){
         Room room=new Room();
        room.setId(id);
        if (zoneId != null) {
            room.setZoneId(zoneId);
        }
        if (buildingId != null) {
            room.setBuildingId(buildingId);
        }
        if (unitId != null) {
            room.setUnitId(unitId);
        }
        if (roomNum != null) {
            room.setRoomNum(roomNum);
        }
        roomService.updateById(room);
        return Result.success("修改成功");
    }
}
