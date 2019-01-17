package com.mit.community.conmon;

/**
 * @author shuyy
 * @date 2019-01-17
 * @company mitesofor
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 百度文字识别demo
 */
public class BaiduOcr {
    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.c9303e47f0729c40f2bc2be6f8f3d589.2592000.1530936208.282335-
     * 1234567",
     * "expires_in":2592000
     * }
     */
    public static String getAuth() {
// 官网获取的 API Key
        String clientId = "cgumzfCkMYhLDU6HUESS7cG9";
// 官网获取的 Secret Key
        String clientSecret = "p5jdQfFjyaBie2EjzomMl9WCIdLarGw8";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云的 API Key
     * @param sk - 百度云的 Securet Key
     * @return assess_token 示例：
     * "24.c9303e47f0729c40f2bc2be6f8f3d589.2592000.1530936208.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
// 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
// 1. grant_type为固定参数
                + "grant_type=client_credentials"
// 2. 官网获取的 API Key
                + "&client_id=" + ak
// 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
// 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");//百度推荐使用POST请求
            connection.connect();
// 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
// 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.err.println("result:" + result);
            JSONObject jsonObject1 = JSON.parseObject(result);
            String access_token = jsonObject1.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
//用java JDK自带的URL去请求
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
//设置该请求的消息头
//设置HTTP方法：POST
            connection.setRequestMethod("POST");
//设置其Header的Content-Type参数为application/x-www-form-urlencoded
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
// 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "uml8HFzu2hFd8iEG2LkQGMxm");
//将第二步获取到的token填入到HTTP header
            connection.setRequestProperty("access_token", BaiduOcr.getAuth());
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String invoke(byte[] byteArray){
        String imageBase = BASE64.encodeImgageToBase64(byteArray);
        imageBase = imageBase.replaceAll("\r\n","");
        imageBase = imageBase.replaceAll("\\+","%2B");
//百度云的文字识别接口,后面参数为获取到的token
        String httpUrl="https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token="+ BaiduOcr.getAuth();
        String httpArg = "detect_direction=true&id_card_side=front&image="+imageBase;
        String jsonResult = request(httpUrl, httpArg);
        System.out.println("返回的结果--------->"+jsonResult);
        return jsonResult;
       /* HashMap<String, String> map = getHashMapByJson(jsonResult);
        Collection<String> values=map.values();
        Iterator<String> iterator2=values.iterator();
        while (iterator2.hasNext()){
            System.out.print(iterator2.next()+", ");
        }*/
    }


}
