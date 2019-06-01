package com.mit.community.module.comparison.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ComparisonInterfaceData;
import com.mit.community.entity.FaceComparisonInterfaceData;
import com.mit.community.service.ComparisonPhotoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 比对照片接口
 * @author xiong
 *<p>Copyright: Copyright (c) 2019</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@Slf4j
@Api(tags = "比对照片接口")
@RequestMapping(value = "/comparisonPhoto")
public class ComparisonPhotoController {
	@Autowired
	private ComparisonPhotoService comparisonPhotoService;
	/**
	 * 发现目标(获取当天所有比对记录)
	 * @param deviceId
	 * @return
	 * 查找当天记录select * from 表名 where to_days(时间字段名) = to_days(now());
	 */
	@GetMapping("/comparisonList")
	@ApiOperation(value = "发现目标", notes = "传参：deviceId 设备的ID")
	public Result realTimePhoto(String deviceId){
		FaceComparisonInterfaceData data = comparisonPhotoService.getComparisonPhoto(deviceId);
		return Result.success(data);
	}
	
	@GetMapping("/comparisonlistPage")
	@ApiOperation(value = "根据条件分页查询比对的照片", notes = "传参：deviceId 设备的ID，age 年龄，sex 性别，race 种族，expression 表情，glasses 眼镜，mood 情绪，name 姓名，cardNum 身份证，" +
			"startTime 开始时间，endTime 结束时间")
	public Result listPage(String deviceId, @RequestParam( required = false, defaultValue = "0")Integer age, String sex, String race, String expression, String glasses, String mood, String name, String cardNum,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,Integer pageNum, Integer pageSize) {
		if (StringUtils.isNotBlank(deviceId)) {
			Page<ComparisonInterfaceData> page = comparisonPhotoService.listPage(deviceId, age, sex, race, expression, glasses, mood, name, cardNum, startTime, endTime, pageNum, pageSize);
			return Result.success(page);
		}
		return Result.error("参数不能为空");
	}
}
