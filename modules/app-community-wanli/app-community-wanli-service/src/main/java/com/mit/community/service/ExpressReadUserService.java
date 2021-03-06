package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class ExpressReadUserService extends ServiceImpl<ExpressReadUserMapper, ExpressReadUser> {
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
     * @param userId        用户id
     * @param expressInfoId 快递地址信息id
     * @return 快递已读信息
     * @author Mr.Deng
     * @date 9:07 2018/12/18
     */
    public ExpressReadUser ByUserIdAndExpressInfoId(Integer userId, Integer expressInfoId) {
        EntityWrapper<ExpressReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_info_id", expressInfoId);
        List<ExpressReadUser> expressReadUsers = expressReadUserMapper.selectList(wrapper);
        if (expressReadUsers.isEmpty()) {
            return null;
        }
        return expressReadUsers.get(0);
    }

    /**
     * 查询已读总数，通过用户id和快递位置id
     * @param userId           用户id
     * @param expressAddressId 快递位置id
     * @return 总数
     * @author Mr.Deng
     * @date 11:51 2019/1/3
     */
    public Integer countByUserIdAndExpressAddressId(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        return expressReadUserMapper.selectCount(wrapper);
    }

    /**
     * 查询已读条数
     * @param userId
     * @return java.lang.Integer
     * @throws
     * @author shuyy
     * @date 2019-01-02 15:39
     * @company mitesofor
     */
    public Integer countNotRead(Integer userId) {
        EntityWrapper<ExpressReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return expressReadUserMapper.selectCount(wrapper);
    }

}
