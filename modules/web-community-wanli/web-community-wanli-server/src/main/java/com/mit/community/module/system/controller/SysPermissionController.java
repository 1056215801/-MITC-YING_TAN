package com.mit.community.module.system.controller;

import com.mit.community.entity.SysPermission;
import com.mit.community.service.SysPermissionService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限设置
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@RequestMapping(value = "/permission")
@RestController
@Slf4j
@Api(tags = "系统菜单权限")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     *
     * @param name 菜单名
     * @param url 菜单url
     * @param permission 菜单标识符
     * @param parentId 父类菜单id
     * @param parentIds 祖先菜单id
     * @param menuType 菜单类型
     * @param menuOrder 菜单排序
     * @param menuIcon 菜单图标
     * @param parentMenuHaveChildren 父菜单原本是否有子菜单
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 16:31
     * @company mitesofor
    */
    @PostMapping("/addMenu")
    @ApiOperation(value = "新增", notes = "传参：name 菜单名、url 菜单地址、permission 权限标识符，以:menu结尾，" +
            "比如系统管理：systemManager:menu、 parentId 父菜单id、parentIds 祖先id，比如一级菜单:0/1，二级菜单0/1/2、 " +
            "menuType 菜单类型，0：系统菜单、1：业务菜单, menuOrder 菜单排序、 menuIcon 菜单标识符，为空则默认menu-icon fa fa-leaf black、" +
            " parentMenuHaveChildren 父菜单原本是否有子菜单，0：否。1：是。")
    public Result addMenu(String name, String url, String permission, Integer parentId, String parentIds,
                      boolean menuType, Short menuOrder, String menuIcon, boolean parentMenuHaveChildren){
        if(StringUtils.isBlank(menuIcon)){
            menuIcon = "menu-icon fa fa-leaf black";
        }
       sysPermissionService.handleAndsave(name, url, permission, parentId, parentIds, menuType,
               menuOrder, menuIcon, parentMenuHaveChildren);
        return Result.success("保存成功");
    }


    /**
     *
     * @param id 菜单id
     * @param name 菜单名
     * @param url 菜单url
     * @param permission 菜单标识符
     * @param menuType 菜单类型
     * @param menuOrder 菜单排序
     * @param menuIcon 菜单图标
     * @param oldPermission 没更新前的菜单标识
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 16:28
     * @company mitesofor
    */
    @PatchMapping("/updateMenu")
    @ApiOperation(value = "更新菜单", notes = "传参：id 菜单id required, name 菜单名、url 菜单地址、permission 权限标识符，以:menu结尾，" +
            "比如系统管理：systemManager:menu、 " +
            "menuType 菜单类型，0：系统菜单、1：业务菜单, menuOrder 菜单排序、 menuIcon 菜单标识符、oldPermission 没更新前的菜单标识 permission和oldPermission两者都required或都没有")
    public Result updateMenu(Integer id, String name, String url, String permission,
                          Boolean menuType, Short menuOrder, String menuIcon, String oldPermission){
        sysPermissionService.handleAndUpdate(id, name, url, permission, menuType, menuOrder,
                menuIcon, oldPermission);
        return Result.success("更新成功");
    }

    /**
     * 删除菜单
     * @param id 菜单id
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/17 17:26
     * @company mitesofor
    */
    @DeleteMapping("/removeMenu")
    @ApiOperation(value = "删除菜单", notes = "传参：id 菜单id")
    public Result removeMenu(Integer id){
        String s = sysPermissionService.handleAndRemove(id);
        if(s.equals("success")){
            return Result.success("删除成功");
        }
        return Result.error(s);
    }

    /**
     * @param parentId 父菜单id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 17:36
     * @company mitesofor
    */
    @DeleteMapping("/listMenuByParentId")
    @ApiOperation(value = "菜单列表", notes = "传参：parentId 父菜单id")
    public Result listMenuByParentId(Integer parentId){
        if(parentId == null){
            parentId = 0;
        }
        List<SysPermission> sysPermissions = sysPermissionService.listByParentId(parentId);
        return Result.success(sysPermissions);
    }
}
