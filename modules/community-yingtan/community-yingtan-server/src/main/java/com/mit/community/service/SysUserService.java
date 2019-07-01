package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.SysUser;
import com.mit.community.module.pass.mapper.SysUserMapper;
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

    /**
     * 查询小区管理员的账户信息
     *
     * @return 用户信息列表
     * @author Mr.Deng
     * @date 14:56 2018/11/27
     */
    public List<SysUser> list() {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("role", "小区管理员");
        return sysUserMapper.selectList(wrapper);
    }

    /**
     * 修改密码
     *
     * @param sysUser 修改的数据
     * @return 改变条数
     * @author Mr.Deng
     * @date 15:53 2018/11/28
     */
    public Integer update(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 添加用户信息
     *
     * @param sysUser 用户信息
     * @return 添加数据
     * @author Mr.Deng
     * @date 15:36 2018/11/28
     */
    public Integer save(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    /***
     * 通过用户名查询用户
     * @param username 用户名
     * @return com.mit.community.entity.SysUser
     * @author shuyy
     * @date 2018/11/14 14:20
     * @company mitesofor
     */
    public SysUser getSysUser(String username) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * @Author HuShanLin
     * @Date 11:29 2019/6/29
     * @Description:~根据地区名称查询用户列表
     */
    public List<SysUser> listUsesByArea(String areaName) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("area_name", areaName);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        return list;
    }
}
