package com.mit.community.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.FaceSimilarity;
import com.mit.community.entity.PhotoSearchResult;
import com.mit.community.service.BaiDuFaceService;
import com.mit.community.util.GetBaiDuAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import util.HttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class IsStrangeTask {
    @Autowired
    private BaiDuFaceService baiDuFaceService;

    /**
     * 抓拍照片判断是否为陌生人
     * @company mitesofor
     */
    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void isStrange() {
        List<FaceSimilarity> list = baiDuFaceService.getUncheckIsStrange();//一次十条
        if (!list.isEmpty()) {
            for(FaceSimilarity faceSimilarity : list) {
                String result = this.search(faceSimilarity.getFaceTokenOne(), "snapPhoto");
                if (result != null) {
                    JSONObject json = JSONObject.parseObject(result);
                    String errorMsg = json.getString("error_msg");
                    if ("SUCCESS".equals(errorMsg)) {
                        JSONObject jsonToken = JSONObject.parseObject(json.getString("result"));
                        String userInfo = jsonToken.getString("user_list");
                        //JSONArray array = jsonToken.getJSONArray(jsonToken.getString("user_list"));
                        List<PhotoSearchResult> ts = (List<PhotoSearchResult>) JSONArray.parseArray(userInfo, PhotoSearchResult.class);//toArray(array, PhotoSearchResult.class);
                        if(ts != null) {
                            for(PhotoSearchResult rs : ts) {
                                double similarity = rs.getScore();
                                int isStrange = 0;
                                if (similarity >= 80) {
                                    isStrange = 1;
                                } else if (similarity < 80) {
                                    isStrange = 2;
                                }
                                baiDuFaceService.upIsStrange(faceSimilarity.getId(), isStrange);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String search(String faceToken,String userId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
           /* byte[] bytes1 = FileUtil.readFileByBytes("F:/111.jpg");
            String image1 = Base64Util.encode(bytes1);*/
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", faceToken);
            map.put("liveness_control", "NONE");
            map.put("group_id_list", "faceMatch");
            map.put("image_type", "FACE_TOKEN");
            map.put("quality_control", "NONE");
            map.put("user_id", "userPhoto");
            map.put("max_user_num", 20);
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
