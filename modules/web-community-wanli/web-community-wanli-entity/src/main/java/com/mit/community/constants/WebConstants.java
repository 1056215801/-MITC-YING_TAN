package com.mit.community.constants;

/**
 * 常量
 *
 * @author shuyy
 * @date 2018/12/14
 * @company mitesofor
 */
public class WebConstants {

    /**资源类型-菜单*/
    public static final Short PERMISSION_RESOUCE_TYPE_MENU  = 1;
    /**资源类型-增删改查*/
    public static final Short PERMISSION_RESOUCE_TYPE_CRUDBUTTON  = 2;
    /**资源类型-按钮*/
    public static final Short PERMISSION_RESOUCE_TYPE_BUTTON  = 3;
    /**权限标识后缀，增*/
    public static final String PERMISSION_ADD = ":add";
    /**权限标识后缀，删*/
    public static final String PERMISSION_UPDATE = ":update";
    /**权限标识后缀，改*/
    public static final String PERMISSION_DELETE = ":delete";
    /**权限标识后缀，查*/
    public static final String PERMISSION_VIEW = ":view";


    /**权限名后缀，增*/
    public static final String PERMISSION_NAME_ADD = "-新增";
    /**权限名后缀，删*/
    public static final String PERMISSION_NAME_UPDATE = "-更新";
    /**权限名后缀，改*/
    public static final String PERMISSION_NAME_DELETE = "-删除";
    /**权限名后缀，查*/
    public static final String PERMISSION_NAME_VIEW = "-查看";
    /**菜单类型，系统菜单*/
    public static final boolean PERMISSION_MENU_TYPE_SYSTEM = true;
    /**菜单类型，业务菜单*/
    public static final boolean PERMISSION_MENU_TYPE_BUSSINESS = false;

}
