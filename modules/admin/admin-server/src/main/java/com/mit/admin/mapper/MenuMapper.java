package com.mit.admin.mapper;

import com.mit.admin.entity.Menu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>Description:<p>
 *
 * @Author: Mr.Deng
 * @Date: 2018/11/8 11:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface MenuMapper extends Mapper<Menu> {
    /**
     * @param authorityId
     * @param authorityType
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:03 2018/11/9
     */
    List<Menu> selectMenuByAuthorityId(@Param("authorityId") String authorityId, @Param("authorityType") String authorityType);

    /**
     * 根据用户和组的权限关系查找用户可访问菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectAuthorityMenuByUserId(@Param("userId") int userId);

    /**
     * 根据用户和组的权限关系查找用户可访问的系统
     *
     * @param userId
     * @return
     */
    List<Menu> selectAuthoritySystemByUserId(@Param("userId") int userId);
}