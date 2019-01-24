package com.mit.community.feigin;

import com.mit.community.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通行
 *
 * @author shuyy
 * @date 2019-01-22
 * @company mitesofor
 */
@FeignClient("app-community-wanli")
public interface PassThroughFeign {

    /**
     * 查询分区，通过小区code
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:30
     * @company mitesofor
    */
    @GetMapping("/api/app/communitywanli/passThrough/listZoneByCommunityCode")
    Result listZoneByCommunityCode(@RequestParam("communityCode") String communityCode);

    /**
     * 查询楼栋，通过分区id
     * @param zoneId 分区id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:29
     * @company mitesofor
    */
    @GetMapping("/api/app/communitywanli/passThrough/listBuildingByZoneId")
    Result listBuildingByZoneId(@RequestParam("zoneId")Integer zoneId);

    /**
     *
     * 查询单元信息，通过楼栋id
     * @param buildingId 楼栋id
     * @author shuyy
     * @date 2019-01-22 9:35
     * @company mitesofor
    */
    @GetMapping("/api/app/communitywanli/passThrough/listUnitByBuildingId")
    Result listUnitByBuildingId(@RequestParam("buildingId")Integer buildingId);

    /**
     *
     * 查询房间信息，通过单元id
     * @param unitId 单元id
     * @author shuyy
     * @date 2019-01-22 9:34
     * @company mitesofor
    */
    @GetMapping("/api/app/communitywanli/passThrough/listRoomByUnitId")
    Result listRoomByUnitId(@RequestParam("unitId")Integer unitId);

    /**
     * 查询设备组，通过小区code
     * @param communityCode
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-23 13:55
     * @company mitesofor
    */
    @GetMapping("/api/app/communitywanli/passThrough/getDeviceGroup")
    Result getDeviceGroup(@RequestParam("communityCode") String communityCode);

}
