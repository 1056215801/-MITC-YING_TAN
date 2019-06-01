package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ReportProblem;
import com.mit.community.entity.ReportProblemInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ReportProblemMapper extends BaseMapper<ReportProblem> {

}

