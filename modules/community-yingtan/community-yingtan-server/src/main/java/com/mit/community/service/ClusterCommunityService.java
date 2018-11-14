package com.mit.community.service;

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
    public List<ClusterCommunity> getClusterCommunityList() {
        return clusterCommunityMapper.selectList(null);
    }

}
