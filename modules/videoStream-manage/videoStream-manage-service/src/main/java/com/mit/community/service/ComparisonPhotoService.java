package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ComparisonInterfaceData;
import com.mit.community.entity.FaceComparisonInterfaceData;
import com.mit.community.mapper.ComparisonPhotoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComparisonPhotoService {
	@Autowired
	private ComparisonPhotoMapper comparisonPhotoMapper;


	/**
	 *发现目标(获取当天所有比对记录)
	 * @param deviceId 设备id
	 * @return
	 * @company mitesofor
	 */
	public FaceComparisonInterfaceData getComparisonPhoto(String deviceId) {
		// TODO Auto-generated method stub
		FaceComparisonInterfaceData faceComparisonInterfaceData = new FaceComparisonInterfaceData();
		List<ComparisonInterfaceData> list = comparisonPhotoMapper.getComparisonPhoto(deviceId);
		if(!list.isEmpty()){
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			for(int i=0; i<list.size();i++){
				String[] a = list.get(i).getImageUrl().split("\\\\");
				String path = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
				list.get(i).setImageUrl(path);
			}

		}
		faceComparisonInterfaceData.setComparisonInterfaceData(list);
		faceComparisonInterfaceData.setCount(list.size());
		return faceComparisonInterfaceData;
	}

	/**
	 * 根据条件分页查询比对的照片
	 * @param deviceId 设备id
	 * @param age 年龄
	 * @param sex 性别
	 * @param race 种族
	 * @param expression 表情
	 * @param glasses 眼镜
	 * @param mood 情绪
	 * @param name 姓名
	 * @param cardNum 身份证号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @company mitesofor
	 */
	public Page<ComparisonInterfaceData> listPage(String deviceId, int age, String sex, String race, String expression, String glasses, String mood, String name, String cardNum,
												  LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
		Page<ComparisonInterfaceData> page = new Page<>(pageNum, pageSize);
		EntityWrapper<ComparisonInterfaceData> wrapper = new EntityWrapper<>();
		if (StringUtils.isNotBlank(deviceId)) {
            wrapper.eq("a.device_id", deviceId);
        }
		if (age != 0) {//大于
            wrapper.ge("a.age", age - 5);
        }
		if (age != 0) {//大于
            wrapper.le("a.age", age + 5);
        }
		if (StringUtils.isNotBlank(sex)) {
            wrapper.eq("a.sex", sex);
        }
		if (StringUtils.isNotBlank(race)) {
            wrapper.eq("a.race", race);
        }
		if (StringUtils.isNotBlank(expression)) {
            wrapper.eq("a.expression", expression);
        }
		if (StringUtils.isNotBlank(glasses)) {
            wrapper.eq("a.glasses", glasses);
        }
		if (StringUtils.isNotBlank(mood)) {
            wrapper.eq("a.mood", mood);
        }
		if (StringUtils.isNotBlank(name)) {
            wrapper.eq("a.name", name);
        }
		if (StringUtils.isNotBlank(cardNum)) {
            wrapper.eq("a.identity_num", cardNum);
        }
		wrapper.ne("a.face_token","noface");
		wrapper.isNotNull("b.id");
		if (startTime != null) {
            wrapper.ge("a.gmt_create", startTime);
        }
		if (endTime != null) {
            wrapper.ge("a.gmt_create", endTime);
        }
		wrapper.orderBy("a.gmt_create", false);
		List<ComparisonInterfaceData> list = comparisonPhotoMapper.selectComparisonPage(page, wrapper);
		if(!list.isEmpty()){
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			for(int i=0; i<list.size();i++){
				String[] a = list.get(i).getImageUrl().split("\\\\");
				String path = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
				list.get(i).setImageUrl(path);
			}
		}
        page.setRecords(list);
        return page;
	}

}
