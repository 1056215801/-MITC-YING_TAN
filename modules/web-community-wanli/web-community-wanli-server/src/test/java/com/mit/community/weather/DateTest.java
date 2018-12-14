package com.mit.community.weather;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Mr.Deng
 * @date 2018/12/8 17:41
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class DateTest {

    public static void trafficControl() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = localDate.format(formatter);
        Integer dateType = dateInit(dateStr);
        String[] s = {"1和6", "2和7", "3和8", "4和9", "5和0"};
        if (dateType == 0) {
            int value = localDate.getDayOfWeek().getValue();
            System.out.println("限行尾号为" + s[value - 1]);
        } else {
            System.out.println("不限行");
        }

    }

    public static Integer dateInit(String dateStr) {
        String url = "http://api.goseek.cn/Tools/holiday?date=" + dateStr;
        String s = HttpUtil.sendGet(url);
        Integer i = null;
        if (StringUtils.isNotBlank(s)) {
            //0、为工作日
            JSONObject jsonObject = JSONObject.parseObject(s);
            i = jsonObject.getInteger("data");
        }
        return i;
    }

    public static void main(String[] args) {
        trafficControl();
    }
}