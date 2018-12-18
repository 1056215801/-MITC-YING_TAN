package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.SysUserRole;
import com.mit.community.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户角色关联
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 保存用户角色
     * @param roleIdList
     * @param uid
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/17 20:11
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void save(List<Integer> roleIdList, Integer uid){
        roleIdList.forEach(item -> {
            SysUserRole sysUserRole = new SysUserRole(item, uid);
            sysUserRole.setGmtCreate(LocalDateTime.now());
            sysUserRole.setGmtModified(LocalDateTime.now());
            sysUserRoleMapper.insert(sysUserRole);
        });
    }

    /**
     * 查询用户角色列表， 通过用户id
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.SysUserRole>
     * @author shuyy
     * @date 2018/12/17 20:19
     * @company mitesofor
    */
    public List<SysUserRole> listByUserId(Integer userId){
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("uid", userId);
        return sysUserRoleMapper.selectList(wrapper);
    }

    /**
     * 查询列表，通过角色id
     * @param roleId 角色id
     * @return java.util.List<com.mit.community.entity.SysUserRole>
     * @author shuyy
     * @date 2018/12/18 10:02
     * @company mitesofor
    */
    public List<SysUserRole> listByRoleId(Integer roleId){
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id", roleId);
        return sysUserRoleMapper.selectList(wrapper);
    }
}
