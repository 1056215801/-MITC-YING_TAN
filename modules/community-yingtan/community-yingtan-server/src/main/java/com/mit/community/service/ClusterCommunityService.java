package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.module.pass.mapper.ClusterCommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 通过社区名列表查询所有社区
     *
     * @param nameList 社区名集合
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/16 17:13
     * @company mitesofor
     */
    public List<ClusterCommunity> listByNames(List<String> nameList) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_name", nameList);
        return clusterCommunityMapper.selectList(wrapper);
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
     *
     * @param cityName 城市名
     * @return java.util.List<java.lang.String>
     * @author shuyy
     * @date 2018/11/22 15:15
     * @company mitesofor
     */
    public List<String> listCommunityCodeListByCityName(String cityName) {
        List<ClusterCommunity> clusterCommunities = this.listByCityName(cityName);
        return clusterCommunities.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
    }

    /**
     * @Author HuShanLin
     * @Date 15:26 2019/6/27
     * @Description:~查询社区code列表，通过地区名
     */
    public List<ClusterCommunity> listByAreaName(String areaName) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.eq("area_name", areaName);
        return clusterCommunityMapper.selectList(wrapper);
    }

    /**
     * @Author HuShanLin
     * @Date 16:10 2019/6/27
     * @Description:~查询社区code列表，通过区县名
     */
    public List<String> listCommunityCodeListByAreaName(String areaName) {
        List<ClusterCommunity> clusterCommunities = this.listByAreaName(areaName);
        return clusterCommunities.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
    }

    /**
     * @Author HuShanLin
     * @Date 14:49 2019/6/29
     * @Description:~根据区名称查询小区数
     */
    public List<ClusterCommunity> getByAreaName(String areaName) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.eq("area_name", areaName);
        List<ClusterCommunity> list = clusterCommunityMapper.selectList(wrapper);
        return list;
    }
}
