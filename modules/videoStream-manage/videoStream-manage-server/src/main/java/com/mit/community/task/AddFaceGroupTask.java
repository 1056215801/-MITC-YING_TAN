package com.mit.community.task;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.PersonInfo;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.service.BaiDuFaceService;
import com.mit.community.util.GetBaiDuAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import util.Base64Util;
import util.FileUtil;
import util.HttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Component
public class AddFaceGroupTask {
	@Autowired
	private BaiDuFaceService baiDuFaceService;
	
	/**
	 * 抓拍照片加入百度人脸库
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void snapAddFaceGroupTask(){
		System.out.println("****抓拍照片加入百度人脸库****");
		List<SnapFaceData> list = baiDuFaceService.getNoAddSnapFacePhoto();//一次十条
		if (list != null) {
			for(int i = 0; i < list.size(); i++) {
				String result = this.add(list.get(i).getImageUrl(), list.get(i).getUserInfo(), "snapPhoto");
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						String face_token = jsonToken.getString("face_token");
						baiDuFaceService.saveFaceToken(list.get(i).getId(),face_token);
					}else if ("pic not has face".equals(errorMsg)) {
						String face_token = "noface";
						baiDuFaceService.saveFaceToken(list.get(i).getId(),face_token);
					} else if ("the number of user's faces is beyond the limit".equals(errorMsg)){
						String face_token = "noface";
						baiDuFaceService.saveFaceToken(list.get(i).getId(),face_token);
					}
				}
			}
		}
		
	}
	
	/**
	 * 人脸比对照片加入百度人脸库
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void comparisonAddFaceGroupTask(){
		System.out.println("****比对照片加入百度人脸库****");
		List<FaceComparisonData> list = baiDuFaceService.getNoAddComparisonFacePhoto();//一次十条
		if (list != null) {
			for(int i = 0; i < list.size(); i++) {
				String result = this.add(list.get(i).getImageUrl(), list.get(i).getUserInfo(), "comparisonPhoto");
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						String face_token = jsonToken.getString("face_token");
						baiDuFaceService.saveComparisonFaceToken(list.get(i).getId(),face_token);
					} else if ("pic not has face".equals(errorMsg)) {
						String face_token = "noface";
						baiDuFaceService.saveComparisonFaceToken(list.get(i).getId(),face_token);
					} else if ("the number of user's faces is beyond the limit".equals(errorMsg)){
						String face_token = "noface";
						baiDuFaceService.saveComparisonFaceToken(list.get(i).getId(),face_token);
					}
				}
			}
		}
	}
	
	/**
	 * 用户照片加入百度人脸库
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void userAddFaceGroupTask(){
		System.out.println("****人员照片加入百度人脸库****");
		List<PersonInfo> list = baiDuFaceService.getNoAddUserFacePhoto();//一次十条
		if (list != null) {
			String userInfo = UUID.randomUUID().toString().replaceAll("-","");
			for(int i = 0; i < list.size(); i++) {
				String result = this.add(list.get(i).getPhotoUrl(), userInfo, "userPhoto");
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						String face_token = jsonToken.getString("face_token");
						baiDuFaceService.saveUserFaceToken(list.get(i).getId(), face_token, userInfo);
					}
				}
			}
		}
	}
	
	public static String add(String path, String user_info, String userId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
        	byte[] bytes1 = FileUtil.readFileByBytes(path);
        	String image1 = Base64Util.encode(bytes1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", image1);
            map.put("group_id", "faceMatch");
            map.put("user_id", userId);
            map.put("user_info", user_info);
            map.put("liveness_control", "NONE");
            map.put("image_type", "BASE64");
            map.put("quality_control", "NONE");

            String param = JSONObject.toJSONString(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetBaiDuAccessToken.getAccessToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
