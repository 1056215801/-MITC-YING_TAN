package com.mit.community.module.dnake.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author HuShanLin
 * @Date Created in 10:15 2019/6/20
 * @Company: mitesofor </p>
 * @Description:~人脸模块
 */
@RestController
@RequestMapping("/faceController")
@Slf4j
@Api(tags = "人脸图片")
public class FaceController {

    @RequestMapping("/uploadImg")
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, String photo) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64字符串处理
        byte[] b = decoder.decodeBuffer(photo);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }
        System.out.println(photo);
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; //创建一个Buffer字符串
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        inStream.close();   //关闭输入流
        return outStream.toByteArray();  //把outStream里的数据写入内存
    }
}
