package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.constants.Constants;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.entity.entity.PersonBaseInfo;

import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 住户
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class HouseHoldService extends ServiceImpl<HouseHoldMapper,HouseHold> {

}
