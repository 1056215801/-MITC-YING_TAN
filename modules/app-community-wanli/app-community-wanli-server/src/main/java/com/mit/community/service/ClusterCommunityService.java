package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.entity.UserHousehold;
import com.mit.community.mapper.ClusterCommunityMapper;
import com.mit.community.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 小区
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class ClusterCommunityService {

    @Autowired
    private ClusterCommunityMapper clusterCommunityMapper;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private UserHouseholdService userHouseholdService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private RedisService redisService;


    /**
     * 查询小区，通过communityCode列表
     *
     * @param communityCodeList 小区code列表
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/30 11:20
     * @company mitesofor
     */
    public List<ClusterCommunity> listByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<ClusterCommunity> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        return clusterCommunityMapper.selectList(wrapper);
    }

    /**
     * 查询用户授权的所有小区，通过用户手机号
     * @param userCellphone 用户手机号
     * @return java.util.List<com.mit.community.entity.ClusterCommunity>
     * @author shuyy
     * @date 2018/11/30 11:25
     * @company mitesofor
     */
    public List<ClusterCommunity> listClusterCommunityByUserCellphone(String userCellphone) {
        User user = (User) redisService.get(RedisConstant.USER + userCellphone);
        List<UserHousehold> userHouseholds = userHouseholdService.listByUserId(user.getId());
        List<HouseHold> households = houseHoldService.listByUserId(user.getId());
        if (households.isEmpty()) {
            return null;
        }
        List<String> communityCodeList = households.parallelStream().map(HouseHold::getCommunityCode).collect(Collectors.toList());
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listByCommunityCodeList(communityCodeList);
        return clusterCommunities;
    }


}
