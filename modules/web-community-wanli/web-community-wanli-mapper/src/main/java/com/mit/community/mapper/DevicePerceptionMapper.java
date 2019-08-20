package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.UrgentButton;
import com.mit.community.entity.WellShift;
import com.mit.community.model.WarnInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DevicePerceptionMapper {
    List<WellShift> getWellShiftPage(RowBounds rowBounds, @Param("ew") Wrapper<WellShift> wrapper);
    List<WellShift> smokeListPage(RowBounds rowBounds, @Param("ew") Wrapper<WellShift> wrapper);
    List<WellShift> dcListPage(RowBounds rowBounds, @Param("ew") Wrapper<WellShift> wrapper);
    List<UrgentButton> urgentButtonListPage(RowBounds rowBounds, @Param("ew") Wrapper<UrgentButton> wrapper);
    List<WarnInfo> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<WarnInfo> wrapper);
}
