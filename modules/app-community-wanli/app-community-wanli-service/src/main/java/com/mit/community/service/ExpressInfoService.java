package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ExpressInfo;
import com.mit.community.mapper.ExpressInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 快递信息业务处理层
 * @author Mr.Deng
 * @date 2018/12/14 16:54
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressInfoService {
    @Autowired
    private ExpressInfoMapper expressInfoMapper;

    /**
     * 修改快递信息数据
     * @param expressInfo 垮堤信息数据
     * @author Mr.Deng
     * @date 16:55 2018/12/14
     */
    public void update(ExpressInfo expressInfo) {
        expressInfoMapper.updateById(expressInfo);
    }

    /**
     * 查询快递未领取个数，通过用户id和快递位置信息
     * @param userId           用户id
     * @param expressAddressId 垮堤位置信息
     * @return 快递个数
     * @author Mr.Deng
     * @date 17:08 2018/12/14
     */
    public Integer countNotExpressNum(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        wrapper.eq("receive_status", 2);
        return expressInfoMapper.selectCount(wrapper);
    }

    /**
     * 查询快递详细信息
     * @param userId           用户id
     * @param expressAddressId 快递地址信息
     * @return 快递信息
     * @author Mr.Deng
     * @date 17:58 2018/12/17
     */
    public List<ExpressInfo> listExpressInfo(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        return expressInfoMapper.selectList(wrapper);
    }

}
