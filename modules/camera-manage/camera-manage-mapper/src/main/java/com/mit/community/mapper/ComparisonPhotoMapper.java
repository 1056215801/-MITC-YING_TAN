package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.ComparisonInterfaceData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ComparisonPhotoMapper{

	List<ComparisonInterfaceData> getComparisonPhoto(@Param("deviceId") String deviceId);
	List<ComparisonInterfaceData> selectComparisonPage(RowBounds rowBounds, @Param("ew") Wrapper<ComparisonInterfaceData> wrapper);

}
