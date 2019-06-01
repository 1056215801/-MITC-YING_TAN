package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("snap_face_data")
public class SnapFaceData extends BaseEntity{
	private int age;
	private String sex;
	/**
	 * 种族
	 */
	private String race;
	/**
	 * 表情
	 */
	private String expression;
	/**
	 * 眼镜
	 */
	private String glasses;
	/**
	 * 情绪
	 */
	private String mood;
	/**
	 * 颜值
	 */
	@TableField("level_of_appearance")
	private double levelOfAppearance;
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
	@TableField("is_stranger")
	private int isStranger;
	/**
	 * 是否已经通过百度人脸属性分析（1是，2否）
	 */
	@TableField("is_detect")
	private int isDetect;
	/**
	 * 百度图库中的id
	 */
	@TableField("face_token")
	private String faceToken;
	/**
	 * 添加到百度图库中的user_info
	 * 
	 */
	@TableField("user_info")
	private String userInfo;
	
	/**
	 * 比对相似度,非数据库字段
	 */
	@TableField(exist = false)
	private double similarity;
	
}
