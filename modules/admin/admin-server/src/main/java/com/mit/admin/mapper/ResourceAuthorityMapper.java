package com.mit.admin.mapper;

import com.mit.admin.entity.ResourceAuthority;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>Description:<p>
 *
 * @Author: Mr.Deng
 * @Date: 2018/11/8 11:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface ResourceAuthorityMapper extends Mapper<ResourceAuthority> {
    /**
     * @param authorityId
     * @param resourceType
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:04 2018/11/9
     */
    void deleteByAuthorityIdAndResourceType(@Param("authorityId") String authorityId, @Param("resourceType") String resourceType);
}