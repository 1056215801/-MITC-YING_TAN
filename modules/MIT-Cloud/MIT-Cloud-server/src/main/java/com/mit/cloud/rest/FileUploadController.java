package com.mit.cloud.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.mit.cloud.util.FastDFSClient;

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
}