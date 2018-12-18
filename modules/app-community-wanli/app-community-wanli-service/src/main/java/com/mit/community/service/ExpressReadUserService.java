package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ExpressReadUser;
import com.mit.community.mapper.ExpressReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 快递提醒已读表业务处理层
 * @author Mr.Deng
 * @date 2018/12/14 16:52
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressReadUserService {
    @Autowired
    private ExpressReadUserMapper expressReadUserMapper;

    /**
     * 添加快递信息已读记录
     * @param expressReadUser 快递信息已读记录
     * @author Mr.Deng
     * @date 16:53 2018/12/14
     */
    public void save(ExpressReadUser expressReadUser) {
        expressReadUser.setGmtCreate(LocalDateTime.now());
        expressReadUser.setGmtModified(LocalDateTime.now());
        expressReadUserMapper.insert(expressReadUser);
    }

    /**
     * 查询快递信息已读，通过用户id，快递地址信息id
     * @param userId           用户id
     * @param expressAddressId 快递地址信息id
     * @return 快递已读信息
     * @author Mr.Deng
     * @date 9:07 2018/12/18
     */
    public ExpressReadUser ByUserIdAndExpressAddressId(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        List<ExpressReadUser> expressReadUsers = expressReadUserMapper.selectList(wrapper);
        if (expressReadUsers.isEmpty()) {
            return null;
        }
        return expressReadUsers.get(0);
    }
}
