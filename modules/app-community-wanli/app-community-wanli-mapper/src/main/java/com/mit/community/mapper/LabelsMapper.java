package com.mit.community.mapper;

import com.mit.community.entity.LabelsInfo;
import org.apache.ibatis.annotations.Param;

public interface LabelsMapper {
    public LabelsInfo getLabelsInfoByUserId(@Param("userid") Integer userid);
    String getLsryId(@Param("userid") Integer userid);
    String getJwryId(@Param("userid") Integer userid);
    String getDyId(@Param("userid") Integer userid);
    String getJsId(@Param("userid") Integer userid);
    String getById(@Param("userid") Integer userid);
    String getXmsfId(@Param("userid") Integer userid);
    String getYscxId(@Param("userid") Integer userid);
    String getSfId(@Param("userid") Integer userid);
    String getSqjz(@Param("userid") Integer userid);
    String getZszhId(@Param("userid") Integer userid);
    String getXdId(@Param("userid") Integer userid);
    String getAzbId(@Param("userid") Integer userid);
    String getZdqsnId(@Param("userid") Integer userid);
}
