package com.mit.community.module.dnake.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void uploadImg(HttpServletRequest request, HttpServletResponse response) {
        byte[] res = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = request.getInputStream();
            out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
            out.close();
            res = out.toByteArray();
            if (in != null) {
                System.out.println("==========result:" + res.length);
            } else {
                System.out.println("未获取到图片数据!");
            }
            //DataInputStream ds = new DataInputStream(in);
            //byte[] b = new byte[10240];
            //in.read(b);
            //System.out.println(Integer.toBinaryString(b[0]));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException exx) {
                exx.printStackTrace();
            }
        }
    }
}
