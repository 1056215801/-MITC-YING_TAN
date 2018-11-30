package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.alibaba.fastjson.JSON;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.Visitor;
import com.mit.community.entity.Zone;
import com.mit.community.module.pass.mapper.VisitorMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访客业务层
 * @author Mr.Deng
 * @date 2018/11/14 20:19
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorService extends ServiceImpl<VisitorMapper, Visitor> {

    private final VisitorMapper visitorMapper;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;

    @Autowired
    public VisitorService(VisitorMapper visitorMapper, ClusterCommunityService clusterCommunityService,
                          ZoneService zoneService, BuildingService buildingService, UnitService unitService) {
        this.visitorMapper = visitorMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
    }

    /**
     * 添加访客信息
     * @param visitor 访客信息
     * @author Mr.Deng
     * @date 20:20 2018/11/14
     */
    public void save(Visitor visitor) {
        visitorMapper.insert(visitor);
    }

    /**
     * 获取所有的访客信息
     * @return 访客信息列表
     * @author Mr.Deng
     * @date 20:21 2018/11/14
     */
    public List<Visitor> list() {
        return visitorMapper.selectList(null);
    }

    /**
     * 统计到访访客人数，通过小区code
     * @param communityCode 小区code
     * @return 到访访客人数
     * @author Mr.Deng
     * @date 15:17 2018/11/21
     */
    public Integer countByCommunityCode(String communityCode) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("visitor_status", 1);
        return visitorMapper.selectCount(wrapper);
    }

    /**
     * 统计已到访访客记录总数，通过一组小区code
     * @param communityCodes 小区code列表
     * @return 已到访访客记录总数
     * @author Mr.Deng
     * @date 15:19 2018/11/21
     */
    public Integer countByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        wrapper.eq("visitor_status", 1);
        return visitorMapper.selectCount(wrapper);
    }

    /***
     * 查询所有的访客，从dnake接口，通过小区code列表
     * @param communityCodeList 小区code列表
     * @return java.util.List<com.mit.community.entity.Visitor>
     * @author shuyy
     * @date 2018/11/21 17:18
     * @company mitesofor
     */
    public List<Visitor> listFromDnakeByCommunityCodeList(List<String> communityCodeList) {
        List<Visitor> allVisitors = Lists.newArrayListWithCapacity(10000);
        communityCodeList.forEach(item -> {
            List<Visitor> visitors = this.listFromDnakeByCommunityCode(item);
            allVisitors.addAll(visitors);
        });
        return allVisitors;
    }

    /***
     * 查询所有的访客，从dnake接口，通过小区code
     * @param communityCode 小区code
     * @return java.util.List<com.mit.community.entity.Visitor>
     * @author shuyy
     * @date 2018/11/21 17:15
     * @company mitesofor
     */
    private List<Visitor> listFromDnakeByCommunityCode(String communityCode) {
        List<Visitor> allVisitors = Lists.newArrayListWithCapacity(5000);
        int index = 1;
        while (true) {
            List<Visitor> visitors = this.listFromDnakeByCommunityCodePage(communityCode, index, 100, null);
            boolean isEnd = false;
            if (visitors.size() < 100) {
                isEnd = true;
            }
            allVisitors.addAll(visitors);
            if (isEnd) {
                break;
            } else {
                index++;
            }
        }
        return allVisitors;
    }

    /***
     * 从dnake接口获取访客列表
     * @param communityCode 社区code
     * @param pageNum 页数
     * @param pageSize 一页大小
     * @param param 参数
     * @return java.util.List<com.mit.community.entity.Visitor>
     * @author shuyy
     * @date 2018/11/21 16:59
     * @company mitesofor
     */
    private List<Visitor> listFromDnakeByCommunityCodePage(String communityCode, Integer pageNum,
                                                           Integer pageSize, Map<String, Object> param) {
        String url = "/v1/visitor/getVisitorList";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        if (param != null && !param.isEmpty()) {
            map.putAll(param);
        }
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("visitorList");
        List<Visitor> visitors = JSON.parseArray(jsonArray.toString(), Visitor.class);
        for (int i = 0; i < visitors.size(); i++) {
            Visitor item = visitors.get(i);
            item.setCommunityCode(communityCode);
            String communityName = clusterCommunityService.getByCommunityCode(communityCode).getCommunityName();
            item.setCommunityName(communityName);
            Zone zone = zoneService.getByNameAndCommunityCode(item.getZoneName(), communityCode);
            if (item.getVisitorName() == null || zone == null) {
                visitors.remove(i--);
                continue;
            }
            if (item.getVisiterMobile() == null) {
                item.setVisiterMobile(StringUtils.EMPTY);
            }
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
            Integer zoneId = zoneService.getByNameAndCommunityCode(item.getZoneName(), communityCode).getZoneId();
            item.setZoneId(zoneId);
            Integer buildingId = buildingService.getByNameAndZoneId(item.getBuildingName(), zoneId).getBuildingId();
            item.setBuildingId(buildingId);
            Integer unitId = unitService.getByNameAndBuildingId(item.getUnitName(), buildingId).getUnitId();
            item.setUnitId(unitId);
        }
        return visitors;
    }

    /***
     * 删除所有记录
     * @author shuyy
     * @date 2018/11/21 17:21
     * @company mitesofor
     */
    public void remove() {
        this.visitorMapper.delete(null);
    }

    /***
     * 查询陌生人通行人数
     * 不传参数返回所有
     *
     * @param communityCode 小区编号 传空参数返回所有
     * @return java.lang.Integer
     * @author lw
     * @date 2018/11/23 14:04
     * @company mitesofor
     */
    public Integer getPassNumber(String communityCode) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(DISTINCT room_num,visitor_name) as i");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        Map<String, Object> map = visitorMapper.selectMaps(wrapper).get(0);

        return Integer.parseInt(map.get("i").toString());
    }

    /***
     * 查询陌生人通行人次
     * 不传参数返回所有
     *
     * @param communityCode 小区编号 传空参数返回所有
     * @return java.lang.Integer
     * @author lw
     * @date 2018/11/23 14:04
     * @company mitesofor
     */
    public Integer getPassPersonTime(String communityCode) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(*) as i");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        Map<String, Object> map = visitorMapper.selectMaps(wrapper).get(0);

        return Integer.parseInt(map.get("i").toString());
    }
}
