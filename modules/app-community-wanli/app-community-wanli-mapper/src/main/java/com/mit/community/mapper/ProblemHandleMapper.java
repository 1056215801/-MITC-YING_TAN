package com.mit.community.mapper;

import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ReportProblemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProblemHandleMapper {
    public int getProblemCount(@Param("problemType")String problemType);
    public List<ReportProblemInfo> getProblem(@Param("status") int status, @Param("problemType")String problemType);
    public void updateProblemStatus(@Param("status") int status,@Param("reportProblemId")Integer reportProblemId);

}
