package com.mit.admin.mapper;

import com.mit.admin.entity.Element;
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
public interface ElementMapper extends Mapper<Element> {
    /**
     * selectAuthorityElementByUserId
     *
     * @param userId
     * @Return
     * @Author Mr.Deng
     * @Date 17:23 2018/11/9
     */
    List<Element> selectAuthorityElementByUserId(@Param("userId") String userId);

    /**
     * selectAuthorityMenuElementByUserId
     *
     * @param userId
     * @param menuId
     * @Return List<Element>
     * @Author Mr.Deng
     * @Date 17:01 2018/11/9
     */
    List<Element> selectAuthorityMenuElementByUserId(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     * @param clientId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:01 2018/11/9
     */
    List<Element> selectAuthorityElementByClientId(@Param("clientId") String clientId);

    /**
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:02 2018/11/9
     */
    List<Element> selectAllElementPermissions();
}