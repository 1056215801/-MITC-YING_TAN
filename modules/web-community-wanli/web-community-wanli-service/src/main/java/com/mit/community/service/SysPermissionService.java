package com.mit.community.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.WebConstants;
import com.mit.community.entity.SysPermission;
import com.mit.community.mapper.SysPermissionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统资源
 *
 * @author shuyy
 * @date 2018/12/14
 * @company mitesofor
 */
@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 保存菜单
     * @param name 菜单名
     * @param url 菜单url
     * @param permission 菜单权限
     * @param parentId 父菜单id
     * @param parentIds 祖先菜单路径
     * @param menuType 菜单类型
     * @param menuOrder 菜单排序
     * @param menuIcon 菜单图标
     * @param parentMenuHaveChildren 是否
     * @author shuyy
     * @date 2018/12/17 14:38
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void handleAndsave(String name, String url, String permission, Integer parentId, String parentIds,
                     boolean menuType, Short menuOrder, String menuIcon, boolean parentMenuHaveChildren) {
        //保存菜单
        //默认菜单图标
        if (menuIcon == null) {
            menuIcon = "menu-icon fa fa-leaf black";
        }
        SysPermission sysPermission = new SysPermission(name,
                WebConstants.PERMISSION_RESOUCE_TYPE_MENU,
                url, permission, parentId, parentIds,
                true, menuType,
                menuOrder, menuIcon, false);
        sysPermission.setGmtCreate(LocalDateTime.now());
        sysPermission.setGmtModified(LocalDateTime.now());
        sysPermissionMapper.insert(sysPermission);
        // 保存菜单的增删改查权限
        saveCRUDPermission(permission, sysPermission);
        //如果父菜单之前haveChildren为0，则改为1
        if(!parentMenuHaveChildren){
            SysPermission parentPermission = this.getById(sysPermission.getParentId());
            parentPermission.setHaveChildren(true);
            this.updateById(parentPermission);
        }
    }

    /**
     * 保存增删该查权限
     * @param permission 菜单权限标识符
     * @param sysPermission 菜单
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/17 16:15
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    private void saveCRUDPermission(String permission, SysPermission sysPermission) {
        String addPermissionName = sysPermission.getName() + WebConstants.PERMISSION_NAME_ADD;
        String updatePermissionName = sysPermission.getName() + WebConstants.PERMISSION_NAME_UPDATE;
        String deletePermissionName = sysPermission.getName() + WebConstants.PERMISSION_NAME_DELETE;
        String viewPermissionName = sysPermission.getName() + WebConstants.PERMISSION_NAME_VIEW;
        SysPermission addSyspermission = new SysPermission(addPermissionName,
                WebConstants.PERMISSION_RESOUCE_TYPE_CRUDBUTTON,
                StringUtils.EMPTY, sysPermission.getPermission() + WebConstants.PERMISSION_ADD,
                sysPermission.getParentId(), sysPermission.getParentIds(),
                true, sysPermission.getMenuType(),
                (short)0, StringUtils.EMPTY, false);
        addSyspermission.setGmtModified(LocalDateTime.now());
        addSyspermission.setGmtCreate(LocalDateTime.now());
        SysPermission deleteSyspermission = new SysPermission(deletePermissionName,
                WebConstants.PERMISSION_RESOUCE_TYPE_CRUDBUTTON,
                StringUtils.EMPTY, sysPermission.getPermission() + WebConstants.PERMISSION_DELETE,
                sysPermission.getParentId(), sysPermission.getParentIds(),
                true, sysPermission.getMenuType(),
                (short)0, StringUtils.EMPTY, false);
        deleteSyspermission.setGmtCreate(LocalDateTime.now());
        deleteSyspermission.setGmtModified(LocalDateTime.now());
        SysPermission updateSyspermission = new SysPermission(updatePermissionName,
                WebConstants.PERMISSION_RESOUCE_TYPE_CRUDBUTTON,
                StringUtils.EMPTY, sysPermission.getPermission() + WebConstants.PERMISSION_UPDATE,
                sysPermission.getParentId(), sysPermission.getParentIds(),
                true, sysPermission.getMenuType(),
                (short)0, StringUtils.EMPTY, false);
        updateSyspermission.setGmtModified(LocalDateTime.now());
        updateSyspermission.setGmtCreate(LocalDateTime.now());
        SysPermission viewSyspermission = new SysPermission(viewPermissionName,
                WebConstants.PERMISSION_RESOUCE_TYPE_CRUDBUTTON,
                StringUtils.EMPTY, sysPermission.getPermission() + WebConstants.PERMISSION_VIEW,
                sysPermission.getParentId(), sysPermission.getParentIds(),
                true, sysPermission.getMenuType(),
                (short)0, StringUtils.EMPTY, false);
        viewSyspermission.setGmtCreate(LocalDateTime.now());
        viewSyspermission.setGmtModified(LocalDateTime.now());
        sysPermissionMapper.insert(addSyspermission);
        sysPermissionMapper.insert(deleteSyspermission);
        sysPermissionMapper.insert(updateSyspermission);
        sysPermissionMapper.insert(viewSyspermission);

    }

    /**
     * 处理然后更新菜单
     * @param id 菜单id
     * @param name 菜单名
     * @param url 菜单url
     * @param permission 权限标识符
     * @param menuType 菜单类型
     * @param menuOrder 菜单排序
     * @param menuIcon 菜单图标
     * @param oldPermission 更新前菜单标识符
     * @author shuyy
     * @date 2018/12/17 16:13
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void handleAndUpdate(Integer id, String name, String url, String permission,
                                Boolean menuType, Short menuOrder, String menuIcon, String oldPermission){
        SysPermission sysPermission = this.getById(id);
        if(StringUtils.isNotBlank(name)){
            sysPermission.setName(name);
        }
        if(StringUtils.isNotBlank(url)){
            sysPermission.setUrl(url);
        }
        if(StringUtils.isNotBlank(permission)){
            sysPermission.setPermission(permission);
        }
        if(menuType != null){
            sysPermission.setMenuType(menuType);
        }
        if(menuOrder != null){
            sysPermission.setMenuOrder(menuOrder);
        }
        if (StringUtils.isNotBlank(menuIcon)){
            sysPermission.setMenuIcon(menuIcon);
        }
        sysPermission.setGmtModified(LocalDateTime.now());
        sysPermissionMapper.updateById(sysPermission);
        if(StringUtils.isNotBlank(oldPermission) && StringUtils.isNotBlank(permission)){
            String oldPermissionPrefix = oldPermission.split(":")[0];
            String oldAdd = oldPermissionPrefix + WebConstants.PERMISSION_ADD;
            String oldDelete = oldPermissionPrefix + WebConstants.PERMISSION_DELETE;
            String oldView = oldPermissionPrefix + WebConstants.PERMISSION_VIEW;
            String oldUpdate = oldPermissionPrefix + WebConstants.PERMISSION_UPDATE;
            this.removeByPermission(oldAdd);
            this.removeByPermission(oldDelete);
            this.removeByPermission(oldView);
            this.removeByPermission(oldUpdate);
            SysPermission sysPermission1 = this.getById(id);
            this.saveCRUDPermission(permission, sysPermission1);
        }
    }

    /**
     * 处理和删除
     * @param id
     * @return java.lang.String
     * @throws
     * @author shuyy
     * @date 2018/12/17 17:24
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public String handleAndRemove(Integer id){
        List<SysPermission> sysPermissions = this.listByParentId(id);
        if(!sysPermissions.isEmpty()){
            return "有子菜单不能删除";
        }
        SysPermission sysPermission = this.getById(id);
        String prefix = sysPermission.getPermission().split(":")[0];
        this.removeByPermissionPrefix(prefix);
        return "success";
    }

    /**
     * 删除权限，通过权限前缀
     * @param prefix 权限前缀
     * @author shuyy
     * @date 2018/12/17 17:19
     * @company mitesofor
    */
    public void removeByPermissionPrefix(String prefix){
        EntityWrapper<SysPermission> wrapper = new EntityWrapper<>();
        wrapper.like("permission", prefix, SqlLike.RIGHT);
        sysPermissionMapper.delete(wrapper);

    }

    public List<SysPermission> listByParentId(Integer parentId){
        EntityWrapper<SysPermission> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_id", parentId);
        return sysPermissionMapper.selectList(wrapper);
    }

    public void removeByPermission(String permission){
        EntityWrapper<SysPermission> wrapper = new EntityWrapper<>();
        wrapper.eq("permission", permission);
        sysPermissionMapper.delete(wrapper);
    }




    /**
     * 获取通过id
     * @param id
     * @return com.mit.community.entity.SysPermission
     * @throws
     * @author shuyy
     * @date 2018/12/17 14:13
     * @company mitesofor
    */
    public SysPermission getById(Integer id){
        return sysPermissionMapper.selectById(id);
    }

    /**
     * 更新通过id
     * @param sysPermission
     * @author shuyy
     * @date 2018/12/17 14:13
     * @company mitesofor
    */
    public void updateById(SysPermission sysPermission){
        sysPermissionMapper.updateById(sysPermission);
    }
    /**
     * 查询所有权限，通过权限id列表
     * @param ids id列表
     * @return java.util.List<com.mit.community.entity.SysPermission>
     * @author shuyy
     * @date 2018/12/17 19:14
     * @company mitesofor
    */
    public List<SysPermission> listByIdList(List<Integer> ids){
        return sysPermissionMapper.selectBatchIds(ids);
    }
}
