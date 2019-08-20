package com.mit.community.module.hik.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.service.DeviceService;
import com.mit.community.service.FaceComparisonService;
import com.mit.community.service.RealTimePhotoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 *
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "(海康设备信息接口)")
public class HKDeviceController {

}
