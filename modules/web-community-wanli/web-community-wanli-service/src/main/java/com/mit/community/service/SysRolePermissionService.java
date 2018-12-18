package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.entity.SysPermission;
import com.mit.community.entity.SysRolePermission;
import com.mit.community.mapper.SysRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色权限关联
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@Service
public class SysRolePermissionService extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 保存
     * @param roleId
     * @param permissionIdList
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/17 19:05
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void save(Integer roleId, List<Integer> permissionIdList) {
        EntityWrapper<SysRolePermission> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id", roleId);
        sysRolePermissionMapper.delete(wrapper);
        List<SysRolePermission> sysRolePermissionList = Lists.newArrayListWithCapacity(permissionIdList.size());
        permissionIdList.forEach(item -> {
            SysRolePermission sysRolePermission = new SysRolePermission(roleId, item);
            sysRolePermission.setGmtCreate(LocalDateTime.now());
            sysRolePermission.setGmtModified(LocalDateTime.now());
            sysRolePermissionList.add(sysRolePermission);
        });
        this.insertBatch(sysRolePermissionList);
    }

    /**
     * 查询权限，通过权限id
     * @return java.util.List<java.security.Permission>
     * @author shuyy
     * @date 2018/12/17 19:16
     * @company mitesofor
    */
    public List<SysPermission> listByRoleId(Integer roleId){
        EntityWrapper<SysRolePermission> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id", roleId);
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(wrapper);
        if(sysRolePermissionList.isEmpty()){
            return Lists.newArrayListWithCapacity(0);
        }
        List<Integer> list = sysRolePermissionList.parallelStream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        return sysPermissionService.listByIdList(list);
    }
    /**
     * 查询权限，通过角色列表
     * @param roleIdList
     * @return java.util.List<com.mit.community.entity.SysPermission>
     * @throws
     * @author shuyy
     * @date 2018/12/17 20:21
     * @company mitesofor
    */
    public List<SysPermission> listByRoleIdList(List<Integer> roleIdList){
        EntityWrapper<SysRolePermission> wrapper = new EntityWrapper<>();
        wrapper.in("role_id", roleIdList);
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(wrapper);
        List<Integer> list = sysRolePermissionList.parallelStream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        return sysPermissionService.listByIdList(list);
    }


}
