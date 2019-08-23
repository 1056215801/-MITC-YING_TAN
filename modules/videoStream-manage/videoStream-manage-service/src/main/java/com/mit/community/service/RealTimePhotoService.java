package com.mit.community.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.entity.PhotoSearchResult;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.entity.SnapFaceInterfaceData;
import com.mit.community.mapper.DeviceInfoMapper;
import com.mit.community.mapper.RealTimePhotoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpUtil;
import util.Utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class RealTimePhotoService {
	@Autowired
	private RealTimePhotoMapper realTimePhotoMapper;

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;

	/**
	 * 获取QQ物联摄像头今日抓拍的所有照片
	 * @param deviceId
	 * @return
	 * @company mitesofor
	 */
	public SnapFaceInterfaceData getRealTimePhoto(String deviceId) {
		SnapFaceInterfaceData data = new SnapFaceInterfaceData();
		List<SnapFaceData> list = realTimePhotoMapper.getRealTimePhoto(deviceId);
		if(!list.isEmpty()){
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			for(int i=0; i<list.size();i++){
				String[] a = list.get(i).getImageUrl().split("\\\\");
				//String path = address.getHostAddress()+":8010/cloud-service/imgs/"+a[a.length-1];
				String path = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
				list.get(i).setImageUrl(path);
			}

		}
		data.setList(list);
		data.setRecordsCount(list.size());
		return data;
	}

	/**
	 *通过图片查询实时抓拍记录
	 * @param photoBase64 照片 base64
	 * @return
	 * @company mitesofor
	 */
	public List<SnapFaceData> getPhotoByImage(String photoBase64, String accessToken) {
		List<SnapFaceData> list = new ArrayList<SnapFaceData>();
		String result = this.search(photoBase64, accessToken);
		if (result != null) {
			JSONObject json = JSONObject.parseObject(result);
			String errorMsg = json.getString("error_msg");
			if ("SUCCESS".equals(errorMsg)) {
				JSONObject jsonToken = JSONObject.parseObject(json.getString("result"));
				String userInfo = jsonToken.getString("user_list");
				//JSONArray array = jsonToken.getJSONArray(jsonToken.getString("user_list"));
				List<PhotoSearchResult> ts = (List<PhotoSearchResult>) JSONArray.parseArray(userInfo, PhotoSearchResult.class);//toArray(array, PhotoSearchResult.class);
				if(ts != null) {
					InetAddress address = null;
					try {
						address = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					SnapFaceData snapFaceData = null;
					for(PhotoSearchResult rs : ts) {
						snapFaceData = new SnapFaceData();
						String imageUrl = realTimePhotoMapper.getImageUrlByUserInfo(rs.getUser_info());
						String[] a = imageUrl.split("\\\\");
						String path = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
						snapFaceData.setImageUrl(path);
						snapFaceData.setSimilarity(rs.getScore());
						list.add(snapFaceData);
					}
				}
			}
		}
		//解析result
		return list;
	}

	/**
	 * 分页查询抓拍照片
	 * @param deviceId 设备id
	 * @param age 年龄
	 * @param sex 性别
	 * @param race 种族
	 * @param expression 表情
	 * @param glasses 眼镜
	 * @param mood 情绪
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @company mitesofor
	 */
	public Page<SnapFaceData> listPage(String deviceId, int age, String sex, String race, String expression, String glasses, String mood,
									   LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
		Page<SnapFaceData> page = new Page<>(pageNum, pageSize);
		EntityWrapper<SnapFaceData> wrapper = new EntityWrapper<>();
		if (StringUtils.isNotBlank(deviceId)) {
            wrapper.eq("device_id", deviceId);
        }
		if (age != 0) {//大于
            wrapper.ge("age", age - 5);
        }
		if (age != 0) {//大于
            wrapper.le("age", age + 5);
        }
		if (StringUtils.isNotBlank(sex)) {
            wrapper.eq("sex", sex);
        }
		if (StringUtils.isNotBlank(race)) {
            wrapper.eq("race", race);
        }
		if (StringUtils.isNotBlank(expression)) {
            wrapper.eq("expression", expression);
        }
		if (StringUtils.isNotBlank(glasses)) {
            wrapper.eq("glasses", glasses);
        }
		if (StringUtils.isNotBlank(mood)) {
            wrapper.eq("mood", mood);
        }
		if (startTime != null) {
            wrapper.ge("gmt_create", startTime);
        }
		if (endTime != null) {
            wrapper.ge("gmt_create", endTime);
        }
		List<SnapFaceData> snapFaceDatas = realTimePhotoMapper.selectPage(page, wrapper);
		if(!snapFaceDatas.isEmpty()){
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			for(int i=0; i<snapFaceDatas.size();i++){
				String[] a = snapFaceDatas.get(i).getImageUrl().split("\\\\");
				String path = "http://120.79.67.123"+":8010/cloud-service/imgs/"+a[a.length-1];
				snapFaceDatas.get(i).setImageUrl(path);
			}

		}
		page.setRecords(snapFaceDatas);
		//解析result
		return page;
	}


	/**
	 * 保存QQ物联摄像头抓拍的照片
	 * @param deviceNum
	 * @param photoBase64
	 * @param time
	 * @throws Exception
	 * @company mitesofor
	 */
	public void saveSnapPhoto(String deployPath, String deviceNum, String photoBase64, long time) throws Exception{
		EntityWrapper<DeviceInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("device_num", deviceNum);
		List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(wrapper);
		String deviceId = deviceInfos.get(0).getDeviceId();
		String userInfo = UUID.randomUUID().toString();
		String path = deployPath + userInfo + ".jpg";
		byte[] b = Utils.base64ToByte(photoBase64);
		File file = new File(path);
		FileImageOutputStream fos = new FileImageOutputStream(file);
		fos.write(b,0,b.length);
		fos.close();
		SnapFaceData snapFaceData = new SnapFaceData(0, null, null, null, null, null, 0, Utils.getDateTimeOfTimestamp(time), path, deviceId, 0,0, null, userInfo, 0);
		snapFaceData.setGmtCreate(LocalDateTime.now());
		snapFaceData.setGmtModified(LocalDateTime.now());
		realTimePhotoMapper.insert(snapFaceData);

	}
	
	public static String search(String photoBase64, String accessToken) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
        	/*byte[] bytes1 = FileUtil.readFileByBytes("F:/111.jpg");
        	String image1 = Base64Util.encode(bytes1);*/
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", photoBase64);
            map.put("liveness_control", "NONE");
            map.put("group_id_list", "faceMatch");
            map.put("image_type", "BASE64");
            map.put("quality_control", "NONE");
			map.put("user_id", "snapPhoto");
            map.put("max_user_num", 20);
            String param = JSONObject.toJSONString(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			//String accessToken = GetBaiDuAccessToken.getAccessToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
