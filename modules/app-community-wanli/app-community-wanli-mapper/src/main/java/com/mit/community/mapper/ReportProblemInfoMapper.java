package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ReportProblemInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ReportProblemInfoMapper {
    List<ReportProblemInfo> selecInfoPage(RowBounds rowBounds, @Param("ew") EntityWrapper<ReportProblemInfo> wrapper, @Param("timeYear")String timeYear, @Param("timeMonth")String timeMonth);
    List<HandleProblemInfo> getSchedulePhoto(@Param("reportProblemId")Integer reportProblemId);
}
