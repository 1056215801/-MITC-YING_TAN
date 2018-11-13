package com.mit.admin.mapper;

import com.mit.admin.entity.Group;
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
public interface GroupMapper extends Mapper<Group> {
    /**
     * @param groupId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:02 2018/11/9
     */
    void deleteGroupMembersById(@Param("groupId") int groupId);

    /**
     * @param groupId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:02 2018/11/9
     */
    void deleteGroupLeadersById(@Param("groupId") int groupId);

    /**
     * @param groupId
     * @param userId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:02 2018/11/9
     */
    void insertGroupMembersById(@Param("groupId") int groupId, @Param("userId") int userId);

    /**
     * @param groupId
     * @param userId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:02 2018/11/9
     */
    void insertGroupLeadersById(@Param("groupId") int groupId, @Param("userId") int userId);
}