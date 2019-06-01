package com.mit.community.mapper;

import com.mit.community.entity.HandleProblemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HandleProblemMapper {
    public List<HandleProblemInfo> getProblemSlove(@Param("status") int status, @Param("problemType")String problemType);
}
