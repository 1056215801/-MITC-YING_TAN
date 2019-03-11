package com.mit.community.util;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 七牛文件上传
 * @author shuyy
 * @date 2019-01-14
 * @company mitesofor
 */
public class UploadUtil {

    private static final String ACCESS_KEY = "dEylXBCK0aahaL9FBlgqt9fXHw6p_g4TWSvx32N4";
    private static final String SECRET_KEY = "fTXZ-GVqqQNDWnCi7Rv1LimWSY81nrX1w4LRO_KI";
    private static final String BUCKET = "app-community";
    /**
     * url前缀
     */
    public static final String URL = "http://www.miesofor.tech/";

    public static String upload(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String ext = "";
        if (name.contains(".")) {
            ext = name.substring(name.lastIndexOf("."));
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString() + ext;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);
        Response response = uploadManager.put(file.getBytes(), key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson()
                .fromJson(response.bodyString(), DefaultPutRet.class);
        return URL + putRet.key;
    }

}
