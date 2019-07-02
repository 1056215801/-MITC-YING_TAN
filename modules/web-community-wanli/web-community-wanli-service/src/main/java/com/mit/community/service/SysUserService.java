package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.entity.SysRole;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.SysUserRole;
import com.mit.community.mapper.SysUserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询用户列表
     *
     * @return java.util.List<com.mit.community.entity.SysUser>
     * @throws
     * @author shuyy
     * @date 2018/12/17 19:41
     * @company mitesofor
     */
    public List<SysUser> list() {
        return sysUserMapper.selectList(null);
    }

    /**
     * 查询用户， 通过角色id
     *
     * @param roleId 角色id
     * @return java.util.List<com.mit.community.entity.SysUserRole>
     * @author shuyy
     * @date 2018/12/18 10:04
     * @company mitesofor
     */
    public List<SysUser> listByRoleId(Integer roleId) {
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByRoleId(roleId);
        if (sysUserRoles.isEmpty()) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<Integer> list = sysUserRoles.parallelStream().map(SysUserRole::getUid).collect(Collectors.toList());
        return this.selectBatchIds(list);
    }


    /**
     * 查询角色，通过用户id
     *
     * @param uid 用户id
     * @return java.util.List<com.mit.community.entity.SysRole>
     * @author shuyy
     * @date 2018/12/18 10:12
     * @company mitesofor
     */
    public List<SysRole> listByUserId(Integer uid) {
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(uid);
        List<Integer> list = sysUserRoles.parallelStream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return sysRoleService.listByIdList(list);
    }


    /**
     * 用户名是否已存在
     *
     * @param username 用户名
     * @return boolean
     * @author shuyy
     * @date 2018/12/17 19:45
     * @company mitesofor
     */
    public boolean hasUsername(String username) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (sysUsers.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @Author HuShanLin
     * @Date 16:48 2019/7/1
     * @Description:~查询已存在的用户
     */
    public SysUser existUsername(String username) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (!sysUsers.isEmpty()) {
            return sysUsers.get(0);
        }
        return null;
    }

    /**
     * 保存用户
     *
     * @param username1
     * @param password1
     * @param s
     * @param scope
     * @param name          姓名
     * @param username      用户名
     * @param password      密码
     * @param communityCode 小区code
     * @param email         email
     * @param phone         电话号码
     * @param remark        备注
     * @author shuyy
     * @date 2018/12/17 19:56
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Integer id, String username, String password, String role, String managementScope, String accountType, String phone, String provinceName,
                     String cityName, String areaName, String streetName, String address, String communityCode, String communityName, String adminName, String email, String remark) {
        if (StringUtils.isBlank(email)) {
            email = StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(remark)) {
            remark = StringUtils.EMPTY;
        }
        if (id == null) {//新增
            SysUser sysUser = new SysUser(username, password, communityCode, provinceName, cityName, areaName, streetName, address, adminName, role, null,
                    phone, remark, null, null, managementScope, accountType, communityName, LocalDateTime.now(), LocalDateTime.now());
            sysUserMapper.insert(sysUser);
        } else {//修改
            SysUser sysUser = new SysUser(username, password, communityCode, provinceName, cityName, areaName, streetName, address, adminName, role, null,
                    phone, remark, null, null, managementScope, accountType, communityName, LocalDateTime.now(), null);
            sysUser.setId(id);
            sysUserMapper.updateById(sysUser);
        }
    }

    /**
     * 获取用户， 根据用户名
     *
     * @param username 用户名
     * @return com.mit.community.entity.SysUser
     * @author shuyy
     * @date 2018/12/18 19:25
     * @company mitesofor
     */
    public SysUser getByUsername(String username) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (sysUsers.isEmpty()) {
            return null;
        }
        return sysUsers.get(0);
    }

    /**
     * @Author HuShanLin
     * @Date 14:58 2019/7/1
     * @Description:~根据社区编码查询用户列表
     */
    public List<SysUser> list(String communityCode) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        return list;
    }

    /**
     * @Author HuShanLin
     * @Date 17:03 2019/7/1
     * @Description:~删除用户信息
     */
    public void remove(Integer id) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper();
        wrapper.eq("id", id);
        sysUserMapper.delete(wrapper);
    }
}
