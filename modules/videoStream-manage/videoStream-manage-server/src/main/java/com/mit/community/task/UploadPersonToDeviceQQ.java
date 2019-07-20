package com.mit.community.task;

import com.mit.community.entity.PersonInfo;
import com.mit.community.service.BaiDuFaceService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

//@Component
public class UploadPersonToDeviceQQ {
	@Autowired
	private BaiDuFaceService baiDuFaceService;

	/**
	 * 将人员信息下发到设备
	 * @company mitesofor
	 */
	//@Scheduled(cron = "0/1 * * * * ?")
	//@Transactional(rollbackFor = Exception.class)
	public void uploadPersonInfo() {
		System.out.println("****人员照片加入设备****");
		List<PersonInfo> list = baiDuFaceService.getPersonInfo();
		if (!list.isEmpty()) {
			for (PersonInfo personInfo : list) {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("id", personInfo.getSerialnumber());
				param.put("name", personInfo.getName());
				param.put("nric", personInfo.getIdCardNum());
				//param.put("uid", "0000000005");//模拟门禁卡
				//param.put("cardnum", "0000000005");//卡号
				param.put("image", personInfo.getPhotoBase64());
				String backResult = this.doPost("http://192.168.1.40/cgi/facedata.cgi?addface=1", param);
				//System.out.println("添加人脸返回的结果：" + backResult);
				net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(backResult);
				if (json.getInt("errcode") == 0) {
					baiDuFaceService.upIsSynchro(personInfo.getId(), 1);
				}
			}
		}

	}



	public static String doPost(String url, Map<String, Object> paramMap) {
		// 获取输入流
		InputStream is = null;
		BufferedReader br = null;
		String result = null;
		// 创建httpClient实例对象
		HttpClient httpClient = new HttpClient();
		// 设置httpClient连接主机服务器超时时间：15000毫秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
		// 创建post请求方法实例对象
		PostMethod postMethod = new PostMethod(url);

		postMethod.setRequestHeader("Authorization", "Basic YWRtaW46MTIzNDU2");
		// 设置post请求超时时间
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
		NameValuePair[] nvp = null;
		// 判断参数map集合paramMap是否为空
		if (null != paramMap && paramMap.size() > 0) {// 不为空
			// 创建键值参数对象数组，大小为参数的个数
			nvp = new NameValuePair[paramMap.size()];
			// 循环遍历参数集合map
			Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
			// 获取迭代器
			Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

			int index = 0;
			while (iterator.hasNext()) {
				Map.Entry<String, Object> mapEntry = iterator.next();
				// 从mapEntry中获取key和value创建键值对象存放到数组中
				try {
					nvp[index] = new NameValuePair(mapEntry.getKey(),
							new String(mapEntry.getValue().toString().getBytes("UTF-8"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				index++;
			}
		}
		// 判断nvp数组是否为空
		if (null != nvp && nvp.length > 0) {
			// 将参数存放到requestBody对象中
			postMethod.setRequestBody(nvp);
		}
		// 执行POST方法
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			// 判断是否成功
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method faild: " + postMethod.getStatusLine());
			}
			// 获取远程返回的数据
			is = postMethod.getResponseBodyAsStream();
			// 封装输入流
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			StringBuffer sbf = new StringBuffer();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sbf.append(temp).append("\r\n");
			}

			result = sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;
	}
	
	

}
