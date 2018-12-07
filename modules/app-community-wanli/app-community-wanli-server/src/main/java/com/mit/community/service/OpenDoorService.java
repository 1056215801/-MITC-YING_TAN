package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.MyKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开门控制类
 * @author Mr.Deng
 * @date 2018/12/4 13:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OpenDoorService {

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期 如：yyyy-MM-dd HH:mm:ss
     * @return unix时间戳字符串
     */
    public static String DateToTimeStamp(String dateStr) {
        String result = StringUtils.EMPTY;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




//    public static void main(String[] args) {
//        String[] times = {"2018-12-4 18:10:10,2018-12-4 19:10:10", "2018-12-5 18:10:10,2018-12-5 19:10:10"};
//        List<Object> list = Lists.newArrayListWithCapacity(3);
//        for (int i = 0; i < times.length; i++) {
//            String time = times[i];
//            String[] unixTimes = time.split(",");
//            Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
//            map.put("startTime", DateToTimeStamp(unixTimes[0]));
//            map.put("endTime", DateToTimeStamp(unixTimes[1]));
//            map.put("once", 0);
//            list.add(map);
//        }
//        System.out.println(list);
//    }

}
