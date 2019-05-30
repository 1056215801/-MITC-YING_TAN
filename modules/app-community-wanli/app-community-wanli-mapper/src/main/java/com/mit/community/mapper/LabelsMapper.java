package com.mit.community.mapper;

import com.mit.community.entity.LabelsInfo;
import org.apache.ibatis.annotations.Param;

public interface LabelsMapper {
    public LabelsInfo getLabelsInfoByUserId(@Param("userid") Integer userid);
}
