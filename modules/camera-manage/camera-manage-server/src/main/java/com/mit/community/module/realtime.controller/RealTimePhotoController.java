package com.mit.community.module.realtime.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.entity.SnapFaceInterfaceData;
import com.mit.community.service.RealTimePhotoService;
import com.mit.community.util.GetBaiDuAccessToken;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * 实时抓拍
 * @author xiong
 * @date 2019/4/28
 */
@RestController
@Slf4j
@Api(tags = "实时抓拍照片获取接口")
@RequestMapping(value = "/realTimePhoto")
public class RealTimePhotoController {
	
	@Autowired
	private RealTimePhotoService realTimePhotoService;

	@Autowired
	WebApplicationContext applicationContext;

	@GetMapping("/getAllUrl")
	@ResponseBody
	public List<String> getAllUrl(){
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		//获取url与类和方法的对应信息
		Map<RequestMappingInfo,HandlerMethod> map = mapping.getHandlerMethods();
		List<String> urlList = new ArrayList<>();
		for (RequestMappingInfo info : map.keySet()){
			//获取url的Set集合，一个方法可能对应多个url
			Set<String> patterns = info.getPatternsCondition().getPatterns();
			for (String url : patterns){
				urlList.add(url);
			}
		}
		return urlList;
	}


	/**
	 * 下方实时抓拍照片滚动数据
	 * @param deviceId
	 * @return
	 * 查找当天记录select * from 表名 where to_days(时间字段名) = to_days(now());
	 */
	@RequestMapping("/realTimePhoto")
	@ApiOperation(value = "下方实时抓拍照片滚动数据", notes = "传参：deviceId 设备的ID")
	public Result realTimePhoto(String deviceId){
		SnapFaceInterfaceData data = realTimePhotoService.getRealTimePhoto(deviceId);
		return Result.success(data);
	}


	/*@RequestMapping("/testp")
	@ApiOperation(value = "获取项目路径", notes = "")
	public Result testp(HttpServletRequest request,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime ){
		String path = request.getSession().getServletContext()
				.getRealPath("/imgs");
		System.out.println("========================="+endTime);
		String basePath = request.getServletContext().getRealPath("imgs/");
		//"C:\\Users\\10372\\AppData\\Local\\Temp\\tomcat-docbase.5751724874646899012.8080\\imgs\\",
		//String path = System.getProperty("user.dir");
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Result.success(endTime);
	}*/
	
	/**
	 * 抓拍照片查询
	 */
	@RequestMapping("/listPage")
	@ApiOperation(value = "分页查询抓拍照片", notes = "传参：deviceId 设备的ID，age 年龄，sex 性别，race 种族，expression 表情，glasses 眼镜，mood 情绪，"  +
			"startTime 开始时间，endTime 结束时间,Integer pageNum, Integer pageSize")
	public Result listPage(String deviceId, @RequestParam( required = false, defaultValue = "0")Integer age, String sex, String race, String expression, String glasses, String mood,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,Integer pageNum, Integer pageSize) {
		if (StringUtils.isNotBlank(deviceId)) {
			Page<SnapFaceData> page = realTimePhotoService.listPage(deviceId, age, sex, race, expression, glasses, mood, startTime, endTime, pageNum, pageSize);
			return Result.success(page);
		}
		return Result.error("参数不能为空");
	}
	

	@RequestMapping("/photoSearch")
	@ApiOperation(value = "根据照片查询抓拍照片", notes = "传参：photoBase64 照片base64")
	public Result listPhotoSearch(String photoBase64) {
		String accessToken = GetBaiDuAccessToken.getAccessToken();
		List<SnapFaceData> list = realTimePhotoService.getPhotoByImage(photoBase64, accessToken);
		return Result.success(list);
	}
}
