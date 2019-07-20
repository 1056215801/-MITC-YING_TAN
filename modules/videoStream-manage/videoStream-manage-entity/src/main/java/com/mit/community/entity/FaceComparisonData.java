package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("face_comparison_data")
public class FaceComparisonData extends BaseEntity{
	/**
	 * 协议标识
	 */
	private String command;
	/**
	 * 协议标识
	 */
	private String datatype;
	/**
	 * 消息标识
	 */
	private String msgid;
	/**
	 * 抓拍时间
	 */
	@TableField("shoot_time")
	private LocalDateTime shootTime;
	/**
	 * 设备id
	 */
	@TableField("device_id")
	private String deviceId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	@TableField("identity_num")
	private String identityNum;
	/**
	 * 照片url
	 */
	@TableField("image_url")
	private String imageUrl;
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
	private int levelOfAppearance;
	/**
	 * 比对相似度
	 */
	private double similarity;
	/**
	 * 是否已经通过百度人脸属性分析（1是，2否）
	 */
	@TableField("is_detect")
	private int isDetect;
	/**
	 * 是否通过百度人脸比对相似度（1是，2否）
	 */
	@TableField("is_match")
	private int isMatch;
	/**
	 * 百度人脸库user_info
	 */
	@TableField("user_info")
	private String userInfo;
	/**
	 * 百度图库token
	 */
	@TableField("face_token")
	private String faceToken;
}
