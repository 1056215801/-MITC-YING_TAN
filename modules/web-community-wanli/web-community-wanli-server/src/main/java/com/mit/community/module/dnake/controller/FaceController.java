package com.mit.community.module.dnake.controller;

import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.HttpPostUtil;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @Author HuShanLin
 * @Date Created in 10:15 2019/6/20
 * @Company: mitesofor </p>
 * @Description:~人脸模块
 */
@RestController
@RequestMapping("/faceController")
@Slf4j
@Api(tags = "与门禁设备交互")
public class FaceController {

}
