package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class AppUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DnakeAppApiService dnakeAppApiService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private UserService userService;


    /**
     * 删除用户
     *
     * @param id 用户id
     * @author shuyy
     * @date 2018/12/19 16:45
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleRemove(Integer id) {
        User user = userService.getById(id);
        HouseHold household = houseHoldService.getByHouseholdId(user.getHouseholdId());
        userMapper.deleteById(id);
        if (household == null) {
            throw new RuntimeException("住户数据还没同步，5分钟后重试");
        } else {
            dnakeAppApiService.operateHousehold(user.getHouseholdId(),
                    household.getCommunityCode());
        }
    }

    public Page<User> listPage(String cellphone, Integer pageNum, Integer pageSize) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(cellphone)) {
            wrapper.eq("cellphone", cellphone);
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        List<User> users = userMapper.selectPage(page, wrapper);
        page.setRecords(users);
        return page;
    }
}
