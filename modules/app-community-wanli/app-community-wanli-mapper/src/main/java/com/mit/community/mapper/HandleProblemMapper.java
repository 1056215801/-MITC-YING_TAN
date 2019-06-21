package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.WebHandleProblemInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HandleProblemMapper {
    public List<HandleProblemInfo> getProblemSlove(@Param("status") int status, @Param("problemType")String problemType);
    List<HandleProblemInfo> getWebProblem(RowBounds rowBounds, @Param("ew") EntityWrapper<HandleProblemInfo> wrapper);
    List<WebHandleProblemInfo> getWebHandleProblem(RowBounds rowBounds, @Param("ew") EntityWrapper<WebHandleProblemInfo> wrapper);
}
