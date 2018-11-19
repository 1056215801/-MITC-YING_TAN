package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.mapper.ClusterCommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param nameList 社区名集合
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/16 17:13
     * @company mitesofor
    */
    public List<ClusterCommunity> listByNames(List<String> nameList){
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_name", nameList);
        return clusterCommunityMapper.selectList(wrapper);
    }

}
