package com.mit.community.util;

import org.apache.commons.collections.map.HashedMap;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Map;

public class HttpPostUtil {
    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost1(String url, Map<String,String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name));
                }
                params = sb.toString();
            } else if(parameters.size() > 1){
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name)).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            //System.out.println("=====================发送上传参数请求请求");
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 人脸下发
     * @param feaFilePath fea文件完整文件路径
     * @param feaFileName fea完整文件名
     * @param houseHoldId houseHouldId
     * @param deviceIP 设备IP
     * @return
     * @throws Exception
     */
    public static String faceRegister (String feaFilePath, String feaFileName, String houseHoldId, String deviceIP) throws Exception{
        BASE64Encoder encoder = new BASE64Encoder();
        File file = new File(feaFilePath);
        String feaBase64 = encoder.encode(Files.readAllBytes(file.toPath()));
        Map<String,String> params = new HashedMap();
        params.put("cmd","faceRegister");
        params.put("feaName",feaFileName);
        params.put("fea",feaBase64);
        params.put("houseHoldId",houseHoldId);
        String result = sendPost1("http://" + deviceIP + ":28085",params);
        return result;
    }

    public static String httpOpen (String deviceIP, String cellphone, String communityCode, String deviceNum) throws Exception{
        Map<String,String> params = new HashedMap();
        params.put("cmd","httpOpen");
        params.put("cellphone",cellphone);
        params.put("communityCode",communityCode);
        params.put("deviceNum",deviceNum);
        String result = sendPost1("http://" + deviceIP + ":28085",params);
        return result;
    }
}
