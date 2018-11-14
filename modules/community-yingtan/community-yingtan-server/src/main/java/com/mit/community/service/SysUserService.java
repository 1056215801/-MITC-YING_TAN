package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.SysUser;
import com.mit.community.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/14
 * @company mitesofor
 */
@Service
public class SysUserService {
    private final SysUserMapper sysUserMapper;

    @Autowired
    public SysUserService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /***
     * 通过用户名查询用户
     * @param username 用户名
     * @return com.mit.community.entity.SysUser
     * @author shuyy
     * @date 2018/11/14 14:20
     * @company mitesofor
    */
    public SysUser getSysUser(String username){
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

}
