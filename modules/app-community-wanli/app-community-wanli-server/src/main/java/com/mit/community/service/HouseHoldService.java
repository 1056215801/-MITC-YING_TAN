package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.entity.UserHousehold;
import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 住户
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class HouseHoldService {

    @Autowired
    private HouseHoldMapper houseHoldMapper;

    @Autowired
    private UserHouseholdService userHouseholdService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClusterCommunityService clusterCommunityService;



    /**
     * 查询住户，通过住户列表
     * @param householdIdList 住户列表
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/30 11:15
     * @company mitesofor
    */
    public List<HouseHold> listByHouseholdIdList(List<Integer> householdIdList){
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return houseHoldMapper.selectList(wrapper);
    }
}
