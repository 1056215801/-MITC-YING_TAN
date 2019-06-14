package com.mit.community.mapper.mapper;

import org.apache.ibatis.annotations.Param;

public interface UpdateRKCFMapper {
    public void updaterkcf(@Param("person_baseinfo_id") Integer person_baseinfo_id, @Param("status") int status);
}
