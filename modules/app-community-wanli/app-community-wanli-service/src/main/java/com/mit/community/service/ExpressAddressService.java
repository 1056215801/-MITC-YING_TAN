package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mit.community.entity.ExpressAddress;
import com.mit.community.entity.ExpressReadUser;
import com.mit.community.mapper.ExpressAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ExpressAddressService
 * @author Mr.Deng
 * @date 2018/12/14 16:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressAddressService {
    @Autowired
    private ExpressAddressMapper expressAddressMapper;
    @Autowired
    private ExpressInfoService expressInfoService;
    @Autowired
    private ExpressReadUserService expressReadUserService;

    /**
     * 查询快递位置信息，通过小区code
     * @param communityCode 小区code
     * @return 快递位置信息
     * @author Mr.Deng
     * @date 17:02 2018/12/14
     */
    public List<Map<String, Object>> listByCommunityCode(String communityCode) {
        EntityWrapper<ExpressAddress> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return expressAddressMapper.selectMaps(wrapper);
    }

    /**
     * 查询快递位置信息，通过用户id和小区code
     * @param userId        用户id
     * @param communityCode 小区code
     * @return 快递位置信息
     * @author Mr.Deng
     * @date 17:15 2018/12/14
     */
    public List<Map<String, Object>> listExpressAddress(Integer userId, String communityCode) {
        List<Map<String, Object>> list = Lists.newArrayListWithExpectedSize(30);
        List<Map<String, Object>> expressAddresses = this.listByCommunityCode(communityCode);
        if (!expressAddresses.isEmpty()) {
            for (Map<String, Object> expressAddress : expressAddresses) {
                Integer integer = expressInfoService.countNotExpressNum(userId, Integer.parseInt(expressAddress.get("id").toString()));
                ExpressReadUser expressReadUser = expressReadUserService.ByUserIdAndExpressAddressId(userId, Integer.parseInt(expressAddress.get("id").toString()));
                if (expressReadUser == null) {
                    expressAddress.put("readStatus", false);
                }
                expressAddress.put("readStatus", true);
                expressAddress.put("expressNum", integer);
                list.add(expressAddress);
            }
        }
        return list;
    }

}
