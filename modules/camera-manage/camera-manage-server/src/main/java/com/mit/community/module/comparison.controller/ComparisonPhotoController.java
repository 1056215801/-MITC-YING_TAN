package com.mit.community.module.comparison.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.AccessRecord;
import com.mit.community.entity.ComparisonInterfaceData;
import com.mit.community.entity.FaceComparisonInterfaceData;
import com.mit.community.service.AccessRecordService;
import com.mit.community.service.ComparisonPhotoService;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
	@Autowired
	private AccessRecordService accessRecordService;
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

	@Description("需要获取的车辆照片记录")
	@PostMapping( "/getCarPhoto")
	public Result getCarPhoto(String communityCode){
		System.out.println("============communityCode="+communityCode);
		AccessRecord accessRecord = accessRecordService.getNoPhotoRecord(communityCode);
		if (accessRecord != null) {
			String photo = accessRecord.getImage();
			String phototName = photo.substring(photo.length()-28,photo.length()-7);
			accessRecord.setImage(phototName);
			return Result.success(accessRecord);
			/*Map<String, String> params = new HashMap();
			params.put("photo",phototName);
			String backResult = HttpUtil.sendPost1("http://"+accessRecord.getOwnerHouse()+":8010/API/getCarPhoto", params);*/
		}
		return Result.error("发生错误或者没有数据");
	}

	@Description("获取车辆照片")
	@PostMapping( "/carPhoto")
	public Result uploadCarPhoto(String id, String photoBase64) throws Exception{
		/*File file = new File("F://car//123.jpg");
		FileImageOutputStream fos = new FileImageOutputStream(file);
		fos.write(b,0,b.length);
		fos.close();*/
		if(StringUtils.isNotBlank(photoBase64)){
			if ("图片不存在".equals(photoBase64)) {
				AccessRecord accessRecord = new AccessRecord();
				accessRecord.setId(Integer.valueOf(id));
				accessRecord.setImage("图片不存在");
				accessRecordService.updateObjectById(accessRecord);
				return Result.success("图片不存在");
			} else {
				BASE64Decoder decoder = new BASE64Decoder();
				System.out.println(photoBase64);
				photoBase64 = photoBase64.replaceAll(" ","+" );
				System.out.println(photoBase64);
				byte[] b = decoder.decodeBuffer(photoBase64);
				String imageUrl = UploadUtil.uploadWithByte(b);
				AccessRecord accessRecord = new AccessRecord();
				accessRecord.setId(Integer.valueOf(id));
				accessRecord.setImage(imageUrl);
				accessRecordService.updateObjectById(accessRecord);
				return Result.success("上传成功");
			}
		}
		return Result.error("参数为空");
	}
}
