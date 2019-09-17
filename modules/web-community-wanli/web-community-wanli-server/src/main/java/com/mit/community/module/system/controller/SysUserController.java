package com.mit.community.module.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.auth.client.annotation.IgnoreClientToken;
import com.mit.auth.client.annotation.IgnoreUserToken;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysPermission;
import com.mit.community.entity.SysRole;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.SysUserRole;
import com.mit.community.entity.entity.SysUserVo;
import com.mit.community.service.RedisService;
import com.mit.community.service.SysRolePermissionService;
import com.mit.community.service.SysUserRoleService;
import com.mit.community.service.SysUserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@RequestMapping(value = "/user")
@RestController
@Slf4j
@Api(tags = "用户")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private RedisService redisService;

    /**
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/17 19:42
     * @company mitesofor
     */
    @PostMapping("/listUser")
    @ApiOperation(value = "查询所有用户")
    public Result listUser(HttpServletRequest request) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        List<SysUser> list = sysUserService.list(sysUser.getCommunityCode());
        return Result.success(list);
    }

    /**
     * @param roleId 角色id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/18 10:16
     * @company mitesofor
     */
    @PostMapping("/listUserByRoleId")
    @ApiOperation(value = "查询角色下的用户")
    public Result listUserByRoleId(Integer roleId) {
        List<SysUser> sysUsers = sysUserService.listByRoleId(roleId);
        return Result.success(sysUsers);
    }

    /**
     * @param userId 用户id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/18 10:16
     * @company mitesofor
     */
    @PostMapping("/listRoleByUserId")
    @ApiOperation(value = "查询用户下的所有角色")
    public Result listRoleByUserId(Integer userId) {
        List<SysRole> sysRoles = sysUserService.listByUserId(userId);
        return Result.success(sysRoles);
    }

    /**
     * 判断用户名是否已经存在
     *
     * @author shuyy
     * @date 2017年12月7日
     */
    @PostMapping("/hasUsername")
    @ApiOperation(value = "用户名是否存在", notes = "传参：username 用户名")
    public Result hasUsername(String username) {
        boolean status = sysUserService.hasUsername(username);
        if (status) {
            return Result.error("用户名已存在");
        }
        return Result.success("用户名不存在");
    }


    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户", notes = "传参：username 用户名 required、" +
            "password:密码 required、communityCode 小区code required、" +
            "email 电子邮件、" +
            "phone 手机号 required、remak 备注" + "accoutType 账号类型、 managementScope 管辖范围  role 、 provinceName 省份名称 required、" +
            "cityName 城市名称 required、 areaName 区/县名称 required 、streetName 镇/街道 required 、communityCode 小区code required、 " +
            "communityName 小区名称 required 、 adminName 管理员名称、address 地址")
    public Result saveUser(Integer id, String username, String password, String communityCode, String email,
                           String phone, String remark, String accountType, String managementScope, String role, String provinceName, String cityName,
                           String areaName, String streetName, String address, String communityName, String adminName, HttpServletRequest request) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        SysUser sysUser = sysUserService.existUsername(username);
        if (sysUser == null) {
            sysUserService.save(id, username, password, role, managementScope, accountType, phone, provinceName,
                    cityName, areaName, streetName, address, communityCode, communityName, adminName, email, remark);
        } else {
            if (id == null) {
                return Result.success("用户名已存在");
            } else {
                if (id == sysUser.getId()) {
                    sysUserService.save(id, username, password, role, managementScope, accountType, phone, provinceName,
                            cityName, areaName, streetName, address, communityCode, communityName, adminName, email, remark);
                } else {
                    return Result.success("用户名已存在");
                }
            }
        }
        return Result.success("保存成功");
    }

    /**
     * @param roleIdList
     * @param uid
     * @return void
     * @author shuyy
     * @date 2018/12/17 20:13
     * @company mitesofor
     */
    @PostMapping("/saveUserRole")
    @ApiOperation(value = "保存用户角色", notes = "传参：roleIdList 角色id列表、 uid 用户id")
    public Result saveUserRole(@RequestParam("roleIdList") List<Integer> roleIdList, Integer uid) {
        sysUserRoleService.save(roleIdList, uid);
        return Result.success("保存成功");
    }

    /**
     * @param uid
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/17 20:23
     * @company mitesofor
     */
    @PostMapping("/listPermissionByUserId")
    @ApiOperation(value = "查询用户权限，通过用户id", notes = "传参：uid 用户id")
    public Result listPermissionByUserId(Integer uid) {
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(uid);
        List<Integer> list = sysUserRoles.parallelStream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysPermission> sysPermissions = sysRolePermissionService.listByRoleIdList(list);
        return Result.success(sysPermissions);
    }

    /**
     * @Author HuShanLin
     * @Date 17:02 2019/7/1
     * @Description:~删除用户
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "删除本地用户信息", notes = "传参：id 用户id")
    public Result delete(Integer id) {
        sysUserService.remove(id);
        return Result.success("删除成功");
    }
    @ApiOperation(value = "添加账户信息")
    @PostMapping("/addSysUser")
    public Result addSysUser(SysUserVo sysUserVo,HttpServletRequest request){
        String username = sysUserVo.getUsername();
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = sysUser.getCommunityCode();
        EntityWrapper<SysUser> wrapper=new EntityWrapper();
        wrapper.like("username",username);
        List<SysUser> sysUserList = sysUserService.selectList(wrapper);
        if (sysUserList.size()>0)
        {
            return Result.error("当前用户已存在");
        }
        if (StringUtils.isBlank(sysUserVo.getPassword()))
        {
            sysUserVo.setPassword("654321");
        }
        SysUser sysUser1=new SysUser();
        BeanUtils.copyProperties(sysUserVo,sysUser1);
        sysUser1.setCommunityCode(communityCode);
        sysUser1.setCreatetime(LocalDateTime.now());
        sysUser1.setAlterTime(LocalDateTime.now());
        sysUserService.insert(sysUser1);
        return Result.success("新增成功");
    }

    @ApiOperation(value = "修改账户信息")
    @PostMapping("/updateSysUser")
    public Result updateSysUser(SysUserVo sysUserVo){
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(sysUserVo,sysUser);
        sysUser.setAlterTime(LocalDateTime.now());
        sysUserService.updateById(sysUser);
        return Result.success("修改成功");
    }

    @ApiOperation(value = "删除账户信息")
    @ApiImplicitParam(name = "id",value = "账户id",dataType = "Integer",paramType = "query",required = true)
    @PostMapping("/deleteSysUser")
    public Result deleteSysUser(Integer id){
        sysUserService.deleteById(id);
        return Result.success("删除成功");
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "id",value = "账户id",dataType = "Integer",paramType = "query",required = true)
    @PostMapping("/resetPassword")
    public Result resetPassword(Integer id){
        SysUser sysUser = sysUserService.selectById(id);
        sysUser.setPassword("654321");
        sysUserService.updateById(sysUser);
        return Result.success("重置成功");
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "管理员账户",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "adminName",value = "管理员姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "phone",value = "联系电话",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "role",value = "账户角色",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "当前页",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "每页记录数",dataType = "Integer",paramType = "query")
    })
    @PostMapping("/getSysUserList")
    public Result getSysUserList(String username,String adminName,String phone,String role,Integer pageSize,Integer pageNum){

        Page<SysUser> page=sysUserService.getSysUserList(username,adminName,phone,role,pageSize,pageNum);
        return Result.success(page);
    }
}
