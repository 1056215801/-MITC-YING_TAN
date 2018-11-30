package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Zone;
import com.mit.community.mapper.ClusterCommunityMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集群小区业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 13:49
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ClusterCommunityService {

    private final ClusterCommunityMapper clusterCommunityMapper;

    @Autowired
    public ClusterCommunityService(ClusterCommunityMapper clusterCommunityMapper) {
        this.clusterCommunityMapper = clusterCommunityMapper;
    }

    /**
     * 删除所有
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/30 14:34
     * @company mitesofor
    */
    public void remove(){
        clusterCommunityMapper.delete(null);
    }

    /**
     * 添加集群小区信息
     *
     * @param clusterCommunity 集群小区信息
     * @author Mr.Deng
     * @date 13:51 2018/11/14
     */
    public void save(ClusterCommunity clusterCommunity) {
        clusterCommunityMapper.insert(clusterCommunity);
    }

    /**
     * 获取所有小区信息
     *
     * @return 小区列表信息
     * @author Mr.Deng
     * @date 15:56 2018/11/14
     */
    public List<ClusterCommunity> list() {
        return clusterCommunityMapper.selectList(null);
    }


    /***
     * 获取小区，通过小区编码
     * @param communityCode 小区编码
     * @return com.mit.community.entity.ClusterCommunity
     * @author shuyy
     * @date 2018/11/19 15:36
     * @company mitesofor
     */
    public ClusterCommunity getByCommunityCode(String communityCode) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCode);
        List<ClusterCommunity> clusterCommunities = clusterCommunityMapper.selectList(wrapper);
        if (!clusterCommunities.isEmpty()) {
            return clusterCommunities.get(0);
        }
        return null;
    }

    /**
     * 查询小区列表，通过城市名称
     *
     * @param cityName 城市名
     * @return 小区信息列表
     * @author Mr.Deng
     * @date 11:53 2018/11/21
     */
    public List<ClusterCommunity> listByCityName(String cityName) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.eq("city_name", cityName);
        return clusterCommunityMapper.selectList(wrapper);
    }
    /**
     * 查询社区code列表，通过城市名
     * @param cityName 城市名
     * @return java.util.List<java.lang.String>
     * @author shuyy
     * @date 2018/11/22 15:15
     * @company mitesofor
    */
    public List<String> listCommunityCodeListByCityName(String cityName){
        List<ClusterCommunity> clusterCommunities = this.listByCityName(cityName);
        return clusterCommunities.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
    }

    /**
     * 从dnake接口查询所有小区
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @throws
     * @author shuyy
     * @date 2018/11/30 14:30
     * @company mitesofor
    */
    public List<ClusterCommunity> listFromDnake(){
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/community/queryClusterCommunity";
        HashMap<String, Object> map = new HashMap<>();
        map.put("clusterAccountId", DnakeAppUser.clusterAccountid);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("communityList");
        List<ClusterCommunity> clusterCommunityList = JSON.parseArray(jsonArray.toString(), ClusterCommunity.class);
        clusterCommunityList.forEach(item -> {
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
        });
        return clusterCommunityList;
    }
}
