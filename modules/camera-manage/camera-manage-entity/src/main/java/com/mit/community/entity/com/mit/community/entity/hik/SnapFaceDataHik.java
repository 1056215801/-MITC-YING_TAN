package com.mit.community.entity.com.mit.community.entity.hik;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("snap_face_data_hik")
public class SnapFaceDataHik extends BaseEntity {
	private int age;
	private String sex;
	/**
	 * 是否带眼镜（0 是，1 否）
	 */
	private String glasses;

	/**
	 * 拍照时间
	 */
	@TableField("shoot_time")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime shootTime;
	/**
	 * 照片url
	 */
	@TableField("image_url")
	private String imageUrl;
	/**
	 * 设备id
	 */
	@TableField("device_id")
	private String deviceId;
	/**
	 * 是否为陌生人(1是，2不是,3不确定)
	 */


	
}
