package com.mit.community.service;

import com.mit.community.entity.SysPermission;
import com.mit.community.entity.SysRole;
import com.mit.community.mapper.SysRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 角色列表
     *
     * @return java.util.List<com.mit.community.entity.SysRole>
     * @author shuyy
     * @date 2018/12/17 17:46
     * @company mitesofor
     */
    public List<SysRole> list() {
        return sysRoleMapper.selectList(null);
    }

    /**
     * 查询角色，通过角色列表
     * @param roleIdList 角色id列表
     * @return java.util.List<com.mit.community.entity.SysRole>
     * @author shuyy
     * @date 2018/12/18 10:07
     * @company mitesofor
    */
    public List<SysRole> listByIdList(List<Integer> roleIdList){
        return sysRoleMapper.selectBatchIds(roleIdList);
    }

    /**
     * 新增角色
     * @param name 角色名
     * @param role 角色标识符
     * @author shuyy
     * @date 2018/12/17 17:49
     * @company mitesofor
    */
    public void save(String name, String role) {
        SysRole sysRole = new SysRole(true, name, role);
        sysRole.setGmtCreate(LocalDateTime.now());
        sysRole.setGmtModified(LocalDateTime.now());
        sysRoleMapper.insert(sysRole);
    }

    /**
     * 更新
     * @param id id
     * @param available 是否可用
     * @param name 角色名
     * @param role 角色标识符
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/17 17:54
     * @company mitesofor
    */
    public void update(Integer id, Boolean available, String name, String role){
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        if(available != null){
            sysRole.setAvailable(available);
        }
        if(StringUtils.isNotBlank(name)){
            sysRole.setName(name);
        }
        if(StringUtils.isNotBlank(role)){
            sysRole.setRole(role);
        }
        sysRole.setGmtModified(LocalDateTime.now());
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * 删除角色
     * @param id 角色id
     * @author shuyy
     * @date 2018/12/17 18:41
     * @company mitesofor
    */
    public String remove(Integer id){
        List<SysPermission> sysPermissions = sysRolePermissionService.listByRoleId(id);
        if(sysPermissions.isEmpty()){
            sysRoleMapper.deleteById(id);
            return "success";
        }else{
            return "请先解除角色的权限，再删除";
        }
    }

}
