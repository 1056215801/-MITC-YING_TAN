package com.mit.admin.mapper;

import com.mit.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    /**
     * @param groupId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:05 2018/11/9
     */
    List<User> selectMemberByGroupId(@Param("groupId") int groupId);

    /**
     * @param groupId
     * @Description
     * @Return
     * @Author Mr.Deng
     * @Date 17:05 2018/11/9
     */
    List<User> selectLeaderByGroupId(@Param("groupId") int groupId);
}