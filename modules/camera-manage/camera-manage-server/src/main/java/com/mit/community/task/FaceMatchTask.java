package com.mit.community.task;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.FaceSimilarity;
import com.mit.community.service.BaiDuFaceService;
import com.mit.community.util.GetBaiDuAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FaceMatchTask {
	@Autowired
	private BaiDuFaceService baiDuFaceService;

	/**
	 * 摄像头上传的比对照片与原照片进行相似度比对
	 * @company mitesofor
	 */
	@Scheduled(cron = "0/1 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void faceMatch() {
		System.out.println("------比对相似度在运行相似度=");
		List<FaceSimilarity> list = baiDuFaceService.getFaceSimilarityRecords();//一次十条
		if (list != null) {
			System.out.println("------比对相似度在运行相似度");
			for(int i = 0; i < list.size(); i++) {
				String result = this.match(list.get(i).getFaceTokenOne(), list.get(i).getFaceTokenTwo());
				if (result != null) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
					String errorMsg = json.getString("error_msg");
					if ("SUCCESS".equals(errorMsg)) {
						net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(json.getString("result"));
						double similarity = jsonToken.getDouble("score");
						baiDuFaceService.saveFaceSimilarity(similarity, list.get(i).getId());
					}
				}
			}
		}
	}
	
	
	 /**
	    * 重要提示代码中所需工具类
	    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * 下载
	    * {"error_code":0,"error_msg":"SUCCESS","log_id":1345050766031247171,"timestamp":1556603124,"cached":0,"result":{"score":96.01836395,"face_list":[{"face_token":"807547e2f5ba96826c08b74420a2dca6"},{"face_token":"e8eb101fd7a1a6d82282d3f6969f39ec"}]}}
	    */
	    public static String match(String faceTokenOne, String faceTokenTwo) {
	        // 请求url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
	        try {

	            /*byte[] bytes1 = FileUtil.readFileByBytes("F:/111.jpg");
	            byte[] bytes2 = FileUtil.readFileByBytes("F:/000.jpg");
	            String image1 = Base64Util.encode(bytes1);
	            String image2 = Base64Util.encode(bytes2);*/

	            List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();

	            Map<String, Object> map1 = new HashMap<String, Object>();
	            map1.put("image", faceTokenOne);
	            map1.put("image_type", "FACE_TOKEN");
	            map1.put("face_type", "LIVE");
	            map1.put("quality_control", "NONE");
	            map1.put("liveness_control", "NONE");

	            Map<String, Object> map2 = new HashMap<String, Object>();
	            map2.put("image", faceTokenTwo);
	            map2.put("image_type", "FACE_TOKEN");
	            map2.put("face_type", "LIVE");
	            map2.put("quality_control", "NONE");
	            map2.put("liveness_control", "NONE");

	            images.add(map1);
	            images.add(map2);

	            String param = JSONObject.toJSONString(images);

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
