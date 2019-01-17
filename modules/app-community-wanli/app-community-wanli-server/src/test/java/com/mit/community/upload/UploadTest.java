package com.mit.community.upload;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.File;

/**
 * @author shuyy
 * @date 2019-01-14
 * @company mitesofor
 */
public class UploadTest {

    @Test
    public void test(){
        String fileName = "a.jpg";
        String fileTyle=fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileTyle);

    }

    public static void main(String[] args) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "dEylXBCK0aahaL9FBlgqt9fXHw6p_g4TWSvx32N4";
        String secretKey = "fTXZ-GVqqQNDWnCi7Rv1LimWSY81nrX1w4LRO_KI";
        String bucket = "app-community";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:/Users/wulia/Pictures/b07b-hnstwwr4456981.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "FnKLTd2yY3aLthweALiyvfDhGKlj" + ".jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
            Response response = uploadManager.put(new File(localFilePath), key, upToken, null, "application/x-jpg", false);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }

}
