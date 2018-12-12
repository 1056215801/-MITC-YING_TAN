package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.HouseHold;
import com.mit.community.mapper.AccessControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门禁记录业务处理层
 * @author Mr.Deng
 * @date 2018/12/8 10:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService {

    @Autowired
    private AccessControlMapper accessControlMapper;
    @Autowired
    private HouseHoldService houseHoldService;

    /**
     * 查询门禁记录，通过住户id
     * @param communityCode 小区code
     * @param householdId   住户id
     * @return 门禁记录列表
     * @author Mr.Deng
     * @date 10:43 2018/12/8
     */
    public List<AccessControl> listByHouseHoldId(String communityCode, Integer householdId) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("household_id", householdId);
        return accessControlMapper.selectList(wrapper);
    }

    /**
     * 查询门禁记录，通过社区code和手机号码
     * @param communityCode 小区code
     * @param cellphone     手机号码
     * @return 门禁记录信息
     * @author Mr.Deng
     * @date 16:04 2018/12/12
     */
    public List<AccessControl> listByUserId(String communityCode, String cellphone) {
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        if (houseHold != null) {
            return listByHouseHoldId(communityCode, houseHold.getHouseholdId());
        }
        return null;
    }

}
