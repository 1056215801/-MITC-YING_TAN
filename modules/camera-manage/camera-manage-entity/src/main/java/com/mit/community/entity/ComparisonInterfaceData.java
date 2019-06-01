package com.mit.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComparisonInterfaceData {
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
	private int levelOfAppearance;
	
	/**
	 * 比对相似度
	 */
	private double similarity;
	/**
	 * 摄像头比对上传的照片
	 */
	private String imageUrl;
	
	
	private String name;
	/**
	 * 用户信息性别
	 */
	private String sexuality;
	/**
	 * 用户信息年龄
	 */
	private String userAge;
	/**
	 * 用户信息照片
	 */
	private String imageUrlUser;

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime shootTime;

	public LocalDateTime getShootTime() {
		return shootTime;
	}

	public void setShootTime(LocalDateTime shootTime) {
		this.shootTime = shootTime;
	}

}
