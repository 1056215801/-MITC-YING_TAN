package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Building;
import com.mit.community.entity.Zone;
import com.mit.community.mapper.BuildingMapper;
import com.mit.community.mapper.ZoneMapper;
import com.mit.community.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 分区业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 17:50
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ZoneService extends ServiceImpl<ZoneMapper,Zone> {
    @Autowired
    private ZoneMapper zoneMapper;
    @Autowired
    private BuildingMapper buildingMapper;

    /**
     * 查询分区信息，通过小区code
     *
     * @param communityCode 小区code
     * @return 分区列表
     * @author Mr.Deng
     * @date 17:57 2018/12/7
     */
    public List<Zone> listByCommunityCode(String communityCode) {
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("zone_status", 1);
        return zoneMapper.selectList(wrapper);
    }

    /**
     * 查询分区信息，通过分区
     *
     * @param zoneId 分区Id
     * @return 分区信息
     * @author Mr.Deng
     * @date 14:09 2018/12/21
     */
    @Cache(key = "zone:communityCode:zoneId:{1}:{2}")
    public Zone getByZoneId(String communityCode, Integer zoneId) {
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_id", zoneId);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("zone_status", 1);
        List<Zone> zones = zoneMapper.selectList(wrapper);
        if (zones.isEmpty()) {
            return null;
        }
        return zones.get(0);
    }
<<<<<<< HEAD

    public void save(String zoneName, Integer zoneType, String zoneCode, Integer sort, String communityCode) {
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.orderBy("zone_id", false);
        wrapper.last("limit 1");
        Zone zoneIdMax = baseMapper.selectList(wrapper).get(0);
        Zone zone = new Zone();
        zone.setZoneId(zoneIdMax.getZoneId() + 1);
        zone.setCommunityCode(communityCode);
        zone.setZoneName(zoneName);
        zone.setZoneType(zoneType);
        zone.setZoneCode(zoneCode);
        zone.setSort(sort);
        zone.setZoneStatus(1);
        zone.setGmtCreate(LocalDateTime.now());
        zone.setGmtModified(LocalDateTime.now());
        zoneMapper.insert(zone);
    }

    public void update(String zoneName, Integer zoneType, Integer sort, Integer id) {
        Zone zone = zoneMapper.selectById(id);
        if (zone != null) {
            zone.setZoneName(zoneName);
            zone.setZoneType(zoneType);
            zone.setSort(sort);
            zone.setGmtModified(LocalDateTime.now());


        }
        zoneMapper.update(zone, null);
    }

    public Page<Zone> getZoneList(String zoneName, Integer zoneType, Integer zoneStatus, Integer pageNum, Integer pageSize, String communityCode) {
        Page<Zone> page = new Page<>(pageNum, pageSize);
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        if (!StringUtils.isEmpty(zoneName)) {
            wrapper.like("zone_name", zoneName);
        }
        if (zoneType != null) {
            wrapper.eq("zone_type", zoneType);
        }
        if (zoneStatus != null) {
            wrapper.eq("zone_status", zoneStatus);
        }
        if (StringUtils.isNotEmpty(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        wrapper.orderBy("sort", true);
        List<Zone> zones = zoneMapper.selectPage(page, wrapper);
        page.setRecords(zones);
        return page;
    }

    public Result delete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            EntityWrapper<Zone> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("id", ids.get(i));
            Zone zone = zoneMapper.selectList(entityWrapper).get(0);
            EntityWrapper<Building> wrapper = new EntityWrapper<>();
            wrapper.eq("zone_id", zone.getZoneId());
            List<Building> buildingList = buildingMapper.selectList(wrapper);
            if (buildingList.size() > 0) {
                return Result.error("分区已经被楼栋关联不能删除");
            }
        }
        for (int i = 0; i < ids.size(); i++) {

            EntityWrapper<Zone> wrapper1 = new EntityWrapper<>();
            zoneMapper.deleteById(ids.get(i));
        }
        return Result.success("删除成功");
    }

    public Result stop(List<Integer> idList) {

        for (int i = 0; i < idList.size(); i++) {
            EntityWrapper<Zone> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("id", idList.get(i));
            Zone zone = zoneMapper.selectList(entityWrapper).get(0);
            EntityWrapper<Building> wrapper = new EntityWrapper<>();
            wrapper.eq("zone_id", zone.getZoneId());
            List<Building> buildingList = buildingMapper.selectList(wrapper);
            if (buildingList.size() > 0) {
                return Result.error("分区楼栋已经被关联不能禁用");
            }
        }
        for (int i = 0; i < idList.size(); i++) {
            Zone zone = new Zone();
            zone.setZoneStatus(0);
            EntityWrapper<Zone> wrapper = new EntityWrapper<>();
            wrapper.eq("id", idList.get(i));
            baseMapper.update(zone, wrapper);
        }
        return Result.success("禁用成功");
    }

    public void enable(List<Integer> idList) {
        for (int i = 0; i < idList.size(); i++) {
            zoneMapper.updateStatus(1, idList.get(i));
        }
    }
    public Zone getByCommunityName (String communityName){
=======
    public Zone getByCommunityName(String communityName) {
>>>>>>> remotes/origin/newdev
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_name", communityName);
        List<Zone> zones = zoneMapper.selectList(wrapper);
        if (zones.isEmpty()) {
            return null;
        }
        return zones.get(0);
    }

<<<<<<< HEAD
    public Zone getByCommunityCode (String communityCode){
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq(false, "zone_name", "默认分区");
=======
    public Zone getByCommunityCode(String communityCode) {
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq(false,"zone_name","默认分区");
>>>>>>> remotes/origin/newdev
        List<Zone> zones = zoneMapper.selectList(wrapper);
        if (zones.isEmpty()) {
            return null;
        }
        return zones.get(0);
    }

<<<<<<< HEAD
    public Zone getByZoneId (Integer zoneId){
=======
    public Zone getByZoneId( Integer zoneId) {
>>>>>>> remotes/origin/newdev
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_id", zoneId);
        wrapper.eq("zone_status", 1);
        List<Zone> zones = zoneMapper.selectList(wrapper);
        if (zones.isEmpty()) {
            return null;
        }
        return zones.get(0);
<<<<<<< HEAD

=======
>>>>>>> remotes/origin/newdev
    }
}
