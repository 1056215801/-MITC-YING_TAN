package com.mit.community.module.system.controller;

import com.mit.community.entity.SysPermission;
import com.mit.community.entity.SysRole;
import com.mit.community.service.SysRolePermissionService;
import com.mit.community.service.SysRoleService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@RequestMapping(value = "/role")
@RestController
@Slf4j
@Api(tags = "角色")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 保存角色
     * @param name
     * @param role
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/17 17:58
     * @company mitesofor
    */
    @PostMapping("/saveRole")
    @ApiOperation(value = "新增角色", notes = "传参：name 角色名、role 角色标识符")
    public Result saveRole(String name, String role) {
        sysRoleService.save(name, role);
        return Result.success("保存成功");
    }

    /**
     * 更新角色
     * @param id 角色id
     * @param available 是否可用
     * @param name 角色名
     * @param role 角色标识
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/17 18:38
     * @company mitesofor
    */
    @PostMapping("/updateRole")
    @ApiOperation(value = "修改角色", notes = "传参：id 角色id, name 角色名、role 角色标识符")
    public Result updateRole(Integer id, Boolean available, String name, String role) {
        sysRoleService.update(id, available, name, role);
        return Result.success("更新成功");
    }


    /**
     * 删除角色
     * @param id 角色id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 18:42
     * @company mitesofor
    */
    @PostMapping("/removeRole")
    @ApiOperation(value = "删除角色", notes = "传参：id 角色id")
    public Result removeRole(Integer id) {
        String status = sysRoleService.remove(id);
        if(!status.equals("success")){
            return Result.error(status);
        }
        return Result.success("删除成功");
    }

    /**
     * 查询所有角色
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 20:16
     * @company mitesofor
    */
    @PostMapping("/listRole")
    @ApiOperation(value = "查询所有角色")
    public Result listRole() {
        List<SysRole> list = sysRoleService.list();
        return Result.success(list);
    }

    /**
     * @param roleId 角色id
     * @param permissionIdList 权限id列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 19:08
     * @company mitesofor
    */
    @PostMapping("/saveRolePermission")
    @ApiOperation(value = "保存角色权限", notes = "传参：roleId 角色id， permissionIdList 权限列表")
    public Result saveRolePermission(Integer roleId, @RequestParam(value = "permissionIdList") List<Integer> permissionIdList) {
        sysRolePermissionService.save(roleId, permissionIdList);
        return Result.success("保存成功");
    }
    /**
     *
     * @param roleId
     * @return java.util.List<com.mit.community.entity.SysPermission>
     * @throws
     * @author shuyy
     * @date 2018/12/17 19:20
     * @company mitesofor
    */
    @GetMapping("/listPermissionByRoleId")
    @ApiOperation(value = "查询角色权限", notes = "传参：roleId 角色id")
    public Result listPermissionByRoleId(Integer roleId){
        List<SysPermission> sysPermissions = sysRolePermissionService.listByRoleId(roleId);
        return Result.success(sysPermissions);
    }

}
