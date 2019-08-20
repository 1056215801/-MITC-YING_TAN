package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.SnapFaceData;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RealTimePhotoMapper extends BaseMapper<SnapFaceData> {
	public List<SnapFaceData> getRealTimePhoto(@Param("deviceId") String deviceId);

	public String getImageUrlByUserInfo(@Param("user_info") String user_info);
}
