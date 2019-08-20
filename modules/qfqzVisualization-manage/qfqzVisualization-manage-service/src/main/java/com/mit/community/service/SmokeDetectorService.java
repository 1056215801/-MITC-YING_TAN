package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.SmokeDetector;
import com.mit.community.mapper.SmokeDetectorMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class SmokeDetectorService extends ServiceImpl<SmokeDetectorMapper, SmokeDetector> {



}
