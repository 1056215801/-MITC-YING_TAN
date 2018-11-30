package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.Zone;
import com.mit.community.module.pass.mapper.ZoneMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 分区业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 15:53
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ZoneService extends ServiceImpl<ZoneMapper, Zone> {

    private final ZoneMapper zoneMapper;

    @Autowired
    public ZoneService(ZoneMapper zoneMapper) {
        this.zoneMapper = zoneMapper;
    }

    /**
     * 保存分区信息
     *
     * @param zone 分区信息
     * @author Mr.Deng
     * @date 15:55 2018/11/14
     */
    public void save(Zone zone) {
        zoneMapper.insert(zone);
    }

    /**
     * 获取所有的小区信息
     *
     * @return 小区列表
     * @author Mr.Deng
     * @date 16:16 2018/11/14
     */
    public List<Zone> list() {
        return zoneMapper.selectList(null);
    }

    /***
     * 获取分区。通过分区name和社区code
     * @param zoneName 分区名
     * @param communityCode 社区code
     * @return com.mit.community.entity.Zone
     * @author shuyy
     * @date 2018/11/19 15:57
     * @company mitesofor
    */
    public Zone getByNameAndCommunityCode(String zoneName, String communityCode){
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode).eq("zone_name", zoneName);
        List<Zone> zones = zoneMapper.selectList(wrapper);
        if(zones.isEmpty()){
           return null;
        }else{
            return zones.get(0);
        }
    }

    /***
     * 查询分区列表，通过社区code列表
     * @param communityCodeList 社区code列表
     * @return java.util.List<com.mit.community.entity.Zone>
     * @author shuyy
     * @date 2018/11/20 10:07
     * @company mitesofor
    */
    public List<Zone> listByCommunityCodeList(List<String> communityCodeList){
        List<Zone> result = Lists.newArrayListWithCapacity(20);
        communityCodeList.forEach(item -> {
            List<Zone> zones = this.listByCommunityCode(item);
            result.addAll(zones);
        });
        return result;
    }

    /***
     * 查询分区列表，通过社区code
     * @param communityCode 社区code
     * @return java.util.List<com.mit.community.entity.Zone>
     * @author shuyy
     * @date 2018/11/20 10:03
     * @company mitesofor
    */
    public List<Zone> listByCommunityCode(String communityCode){
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return zoneMapper.selectList(wrapper);
    }

    /***
     * 查询分区列表，通过社区code列表
     * @param communityCodeList 社区code
     * @return java.util.List<com.mit.community.entity.Zone>
     * @author shuyy
     * @date 2018/11/20 11:22
     * @company mitesofor
    */
    public List<Zone> listFromDnakeByCommunityCodeList(List<String> communityCodeList){
        List<Zone> result = Lists.newArrayListWithCapacity(5 * communityCodeList.size());
        communityCodeList.forEach(item -> {
            List<Zone> zones = this.listFromDnakeByCommunityCode(item);
            result.addAll(zones);
        });
        return result;
    }

    /***
     * 查询分区列表，通过社区code
     * @param communityCode 社区code
     * @return java.util.List<com.mit.community.entity.Zone>
     * @author shuyy
     * @date 2018/11/20 11:20
     * @company mitesofor
    */
    public List<Zone> listFromDnakeByCommunityCode(String communityCode) {
        String url = "/v1/zone/getZoneList";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put("communityCode", communityCode);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("zoneList");
        List<Zone> zoneList = JSON.parseArray(jsonArray.toString(), Zone.class);
        zoneList.forEach(item -> {
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
            item.setCommunityCode(communityCode);
        });
        return zoneList;
    }

    /**
     * 删除所有分区
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/20 11:24
     * @company mitesofor
    */
    public void remove(){
        zoneMapper.delete(null);
    }

}
