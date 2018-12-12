package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.UserHousehold;
import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.mapper.UserHouseholdMapper;
import com.mit.community.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 住户
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

    @Autowired
    private UserHouseholdMapper userHouseholdMapper;


    /**
     * 查询住户，通过住户列表
     *
     * @param householdIdList 住户列表
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/30 11:15
     * @company mitesofor
     */
    public List<HouseHold> listByHouseholdIdList(List<Integer> householdIdList) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return houseHoldMapper.selectList(wrapper);
    }

    /**
     * 查询住户列表，通过用户id
     * @param userId 用户id
     * @return com.mit.community.entity.HouseHold
     * @author shuyy
     * @date 2018/12/7 10:54
     * @company mitesofor
     */
    public List<HouseHold> listByUserId(Integer userId) {
        EntityWrapper<UserHousehold> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserHousehold> userHouseholds = userHouseholdMapper.selectList(wrapper);
        if (userHouseholds.isEmpty()) {
            return null;
        } else {
            List<Integer> householdIds = userHouseholds.parallelStream().map(UserHousehold::getHouseholdId).collect(Collectors.toList());
            return this.listByHouseholdIdList(householdIds);
        }
    }

    /**
     * 查询住户列表，通过手机号
     * @param cellphone
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @throws
     * @author shuyy
     * @date 2018/12/10 15:35
     * @company mitesofor
    */
    public HouseHold getByCellphone(String cellphone){
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", cellphone);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if(houseHolds.isEmpty()){
            return null;
        }
        return  houseHolds.get(0);
    }

    /**
     * 查找住户信息，通过住户id
     * @param householdId 住户id
     * @return 住户信息
     * @author Mr.Deng
     * @date 16:06 2018/12/7
     */
    public HouseHold getByHouseholdId(Integer householdId) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        return houseHoldMapper.selectList(wrapper).get(0);
    }
}
