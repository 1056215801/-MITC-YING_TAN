package com.mit.community.task;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.FaceComparisonData;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.service.BaiDuFaceService;
import com.mit.community.util.GetBaiDuAccessToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import util.HttpUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class FaceDetectTask {
	@Autowired
	private BaiDuFaceService baiDuFaceService;

	/**
	 * 抓拍照片属性分析
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void snapFaceDetectTask(){
		System.out.println("****抓拍照片属性分析****");
		List<SnapFaceData> list = baiDuFaceService.getNoDetectSnapPhoto();
		if (!list.isEmpty()) {
			for(int i = 0; i < list.size(); i++) {
				//System.out.println("****token****"+list.get(i).getFaceToken());
				String result = this.detect(list.get(i).getFaceToken());
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						JSONArray array = jsonToken.getJSONArray("face_list");
						net.sf.json.JSONObject data = (net.sf.json.JSONObject) array.get(0); 
						int age = data.getInt("age");
						double beauty = data.getDouble("beauty");
						//net.sf.json.JSONObject expressionJson = net.sf.json.JSONObject.fromObject(data.getString("expression"));
						String expression = net.sf.json.JSONObject.fromObject(data.getString("expression")).getString("type");
						String sex = net.sf.json.JSONObject.fromObject(data.getString("gender")).getString("type");
						String glasses = net.sf.json.JSONObject.fromObject(data.getString("glasses")).getString("type");
						String race = net.sf.json.JSONObject.fromObject(data.getString("race")).getString("type");//种族
						String mood = net.sf.json.JSONObject.fromObject(data.getString("emotion")).getString("type");//情绪
						baiDuFaceService.saveSnapFaceDetect(list.get(i).getId(), age, beauty, expression, sex, glasses, race, mood);
					}
				}
			}
		}
	}

	/**
	 * 比对照片进行属性分析
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void comparisionFaceDetectTask(){
		List<FaceComparisonData> list = baiDuFaceService.getNoDetectComparisionPhoto();
		if (list != null) {
			for(int i = 0; i < list.size(); i++) {
				String result = this.detect(list.get(i).getFaceToken());
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						JSONArray array = jsonToken.getJSONArray("face_list");
						net.sf.json.JSONObject data = (net.sf.json.JSONObject) array.get(0); 
						int age = data.getInt("age");
						double beauty = data.getDouble("beauty");
						//net.sf.json.JSONObject expressionJson = net.sf.json.JSONObject.fromObject(data.getString("expression"));
						String expression = net.sf.json.JSONObject.fromObject(data.getString("expression")).getString("type");
						String sex = net.sf.json.JSONObject.fromObject(data.getString("gender")).getString("type");
						String glasses = net.sf.json.JSONObject.fromObject(data.getString("glasses")).getString("type");
						String race = net.sf.json.JSONObject.fromObject(data.getString("race")).getString("type");//种族
						String mood = net.sf.json.JSONObject.fromObject(data.getString("emotion")).getString("type");//情绪
						baiDuFaceService.saveComparisonFaceDetect(list.get(i).getId(), age, beauty, expression, sex, glasses, race, mood);
					}
				}
			}
		}
	}
	
	
	public static String detect(String faceToken) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        /*
        	InputStream inputStream = null;
    	    byte[] data = null;
    	    try {
    	        inputStream = new FileInputStream("F:/face.jpg");
    	        data = new byte[inputStream.available()];
    	        inputStream.read(data);
    	        inputStream.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	    // 加密
    	    BASE64Encoder encoder = new BASE64Encoder();
    	    String imageBase64 = encoder.encode(data);*/
		try {
			//System.out.println("=====facetoken="+faceToken);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", faceToken);
            map.put("face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,race,quality,eye_status,emotion,face_type");
            map.put("image_type", "FACE_TOKEN");
            
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
