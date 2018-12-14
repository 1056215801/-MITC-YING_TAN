package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mit.community.entity.ExpressAddress;
import com.mit.community.mapper.ExpressAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查询快递位置信息，通过小区code
     * @param communityCode 小区code
     * @return 快递位置信息
     * @author Mr.Deng
     * @date 17:02 2018/12/14
     */
    public List<ExpressAddress> listByCommunityCode(String communityCode) {
        EntityWrapper<ExpressAddress> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return expressAddressMapper.selectList(wrapper);
    }

    /**
     * 查询快递位置信息，通过用户id和小区code
     * @param userId        用户id
     * @param communityCode 小区code
     * @return 快递位置信息
     * @author Mr.Deng
     * @date 17:15 2018/12/14
     */
    public List<Object> listExpressAddress(Integer userId, String communityCode) {
        List<Object> list = Lists.newArrayListWithExpectedSize(30);
        List<ExpressAddress> expressAddresses = this.listByCommunityCode(communityCode);
        if (!expressAddresses.isEmpty()) {
            for (ExpressAddress expressAddress : expressAddresses) {
                List<Object> expressList = Lists.newArrayListWithExpectedSize(2);
                Integer integer = expressInfoService.countExpressNum(userId, expressAddress.getId());
                if (integer > 0) {
                    expressList.add(expressAddress);
                    expressList.add(integer);
                    list.add(expressList);
                }
            }
        }
        return list;
    }

}
