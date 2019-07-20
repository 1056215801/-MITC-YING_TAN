package com.mit.cloud.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.mit.cloud.util.FastDFSClient;

import java.io.*;

/**
 * <p>Description:<p>
 *
 * @Author: Mr.Deng
 * @Date: 2018/11/8 11:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Controller
@RequestMapping("/fileUpload")
@Api(value = "上传图片controller", tags = {"上传图片接口"})
public class FileUploadController {

    @ResponseBody
    @RequestMapping("/test")
    @ApiOperation(value = "上传图片", tags = {"上传图片"}, notes = "注意")
    public String picUpload(MultipartFile file) {
        String url = "";
        try {
            url = FastDFSClient.getInstance().uploadFile(file);
        } catch (Exception e1) {
            e1.printStackTrace();
            return "上传失败";
        }
        return url;
    }

    @RequestMapping("/test1")
    @ApiOperation(value = "上传图片", tags = {"上传图片"}, notes = "注意")
    public String picUpload1(MultipartFile file) throws FileNotFoundException {
        String url = "";
        File file1 = new File("D:\\test.jpg");
        long fileSize = file1.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
        }


            try {
                FileInputStream fi = new FileInputStream(file1);
                byte[] buffer = null;
                buffer = new byte[(int) fileSize];
                int offset = 0; int numRead = 0;
                while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0)
                { offset += numRead; } // 确保所有数据均被读取
                 if (offset != buffer.length) {
                    throw new IOException("Could not completely read file " + file.getName()); }

                url = FastDFSClient.getInstance().uploadFile(buffer, "jpg");
            } catch (Exception e1) {
                e1.printStackTrace();
                return "上传失败";
            }
            return url;
        }

    }
