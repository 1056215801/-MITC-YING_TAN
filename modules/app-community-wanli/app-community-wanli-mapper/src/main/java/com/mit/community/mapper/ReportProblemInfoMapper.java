package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ProblemScheduleInfo;
import com.mit.community.entity.ReportProblemInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ReportProblemInfoMapper {
    List<ReportProblemInfo> selecInfoPage(RowBounds rowBounds, @Param("ew") EntityWrapper<ReportProblemInfo> wrapper);
    List<HandleProblemInfo> getSchedulePhoto(@Param("reportProblemId")Integer reportProblemId);
}
