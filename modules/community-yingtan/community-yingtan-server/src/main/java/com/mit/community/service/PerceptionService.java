package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.*;
import com.mit.community.module.pass.mapper.BaoJinMapper;
import com.mit.community.module.pass.mapper.PerceptionMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PerceptionService {
    @Autowired
    private PerceptionMapper perceptionMapper;
    @Autowired
    private BaoJinMapper baoJinMapper;

    public WarnInfo getWarnInfoByType(List<String> communityCode){
        EntityWrapper<WarnInfo> wrapper = new EntityWrapper<>();
        wrapper.in("communityCode", communityCode);
        wrapper.orderBy("gmt_create", false);
        wrapper.last("LIMIT 1");
        List<WarnInfo> list = baoJinMapper.selectList(wrapper);
        if (!list.isEmpty()){
            return list.get(0);
        } else {
            return null;
        }
    }
    public CarPerception getCarPerception(int type, List<String> communityCodes){
        CarPerception carPerception = new CarPerception();
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        if (type == 1){//按日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String dateNowStr = sdf.format(d) + "%";
            List<Current> srList = perceptionMapper.getSjByDay("进",dateNowStr,communityCodes);
            Map<String,String> sr = padding(srList, type);
            srList = map2List(sr,type);
            List<Current> scList = perceptionMapper.getSjByDay("出",dateNowStr,communityCodes);
            Map<String,String> sc = padding(scList, type);
            scList = map2List(sc,type);
            List<Current> xqList = perceptionMapper.getXqByDay(dateNowStr,communityCodes);//本小区
            Map<String,String> xq = padding(xqList, type);//本小区
            xqList = map2List(xq,type);
            List<Current> wlList = perceptionMapper.getXqByDay(dateNowStr,communityCodes);//这里需要修改
            Map<String,String> wl = padding(wlList, type);//外来
            wlList = map2List(wl,type);
            carPerception.setSr(srList);
            carPerception.setSc(scList);
            carPerception.setBxq(xqList);
            carPerception.setWl(wlList);
            return carPerception;
        } else if (type == 2){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date d = new Date();
            String dateNowStr = sdf.format(d) + "%";
            List<Current> srList = perceptionMapper.getSjByMonth("进",dateNowStr,communityCodes);
            Map<String,String> sr = padding(srList, type);
            srList = map2List(sr,type);
            List<Current> scList = perceptionMapper.getSjByMonth("出",dateNowStr,communityCodes);
            Map<String,String> sc = padding(scList, type);
            scList = map2List(sc,type);
            List<Current> xqList = perceptionMapper.getXqByMonth(dateNowStr,communityCodes);//本小区
            Map<String,String> xq = padding(xqList, type);//本小区
            xqList = map2List(xq,type);
            List<Current> wlList = perceptionMapper.getXqByMonth(dateNowStr,communityCodes);//这里需要修改
            Map<String,String> wl = padding(wlList, type);//外来
            wlList = map2List(wl,type);
            carPerception.setSr(srList);
            carPerception.setSc(scList);
            carPerception.setBxq(xqList);
            carPerception.setWl(wlList);
            return carPerception;
        }
        return carPerception;
    }

    public WarnPerception getWarnPerception(int type, List<String> communityCodes){
        WarnPerception warnPerception = new WarnPerception();
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }// SELECT DATE_FORMAT(gmt_create,'%d') AS TIME,COUNT(*) AS COUNT FROM household WHERE gmt_create BETWEEN '2019-03-28' AND '2019-04-04' GROUP BY TIME ORDER BY gmt_create ASC
        if (type == 1){//7天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String dateNowStr = sdf.format(d);//当天日期
            String dayBefore = getPastDate(7);//七天之前的日期
            List<Current> dcList = perceptionMapper.getDcBySomeDay(dayBefore,dateNowStr,communityCodes);
            //List<Current> dcList = new ArrayList<>();
            Map<String,String> dc = paddingWarn(dcList, type);
            dcList = map2ListWarn(dc);
            List<Current> xfList = perceptionMapper.getXfBySomeDay(dayBefore,dateNowStr,communityCodes);
            Map<String,String> xf = paddingWarn(xfList, type);
            xfList = map2ListWarn(xf);
            //List<Current> txList = perceptionMapper.getXqByDay(dateNowStr,communityCodes);//本小区
            List<Current> txList = new ArrayList<>();
            Map<String,String> tx = paddingWarn(txList, type);//
            txList = map2ListWarn(tx);
            List<Current> mwgList = perceptionMapper.getMwgBySomeDay(dayBefore,dateNowStr,communityCodes);
            Map<String,String> mwg = paddingWarn(mwgList, type);//
            mwgList = map2ListWarn(mwg);
            warnPerception.setDc(dcList);
            warnPerception.setXf(xfList);
            warnPerception.setTx(txList);
            warnPerception.setMwg(mwgList);
            return warnPerception;
        } else if (type == 2){//30天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String dateNowStr = sdf.format(d);//当天日期
            String dayBefore = getPastDate(7);//七天之前的日期
            //List<Current> dcList = perceptionMapper.getSjByDay("进",dateNowStr,communityCodes);
            List<Current> dcList = new ArrayList<>();
            Map<String,String> dc = paddingWarn(dcList, type);
            dcList = map2ListWarn(dc);
            List<Current> xfList = perceptionMapper.getXfBySomeDay(dayBefore,dateNowStr,communityCodes);
            Map<String,String> xf = paddingWarn(xfList, type);
            xfList = map2ListWarn(xf);
            //List<Current> txList = perceptionMapper.getXqByDay(dateNowStr,communityCodes);//本小区
            List<Current> txList = new ArrayList<>();
            Map<String,String> tx = paddingWarn(txList, type);//
            txList = map2ListWarn(tx);
            List<Current> mwgList = perceptionMapper.getMwgBySomeDay(dayBefore,dateNowStr,communityCodes);
            Map<String,String> mwg = paddingWarn(mwgList, type);//
            mwgList = map2ListWarn(mwg);
            warnPerception.setDc(dcList);
            warnPerception.setXf(xfList);
            warnPerception.setTx(txList);
            warnPerception.setMwg(mwgList);
            return warnPerception;
        }
        return warnPerception;
    }

    public DevicePerception getDevicePerception(List<String> communityCodes){
        DevicePerception devicePerception = new DevicePerception();
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        Map<String,String> yg = new HashedMap();
        int lx = perceptionMapper.getYgStatusByStatus(3,communityCodes);
        yg.put("lx",String.valueOf(lx));
        int zc = perceptionMapper.getYgStatusByStatus(1,communityCodes);
        yg.put("zc",String.valueOf(zc));
        int yc = perceptionMapper.getYgStatusByStatus(2,communityCodes);
        yg.put("yc",String.valueOf(yc));
        int gj = perceptionMapper.getYgWarnByStatus(communityCodes);
        yg.put("gj",String.valueOf(gj));
        devicePerception.setYg(yg);

        Map<String,String> dc = new HashedMap();
        //int dclx = perceptionMapper.getYgStatusByStatus(3,communityCodes);
        dc.put("lx","0");
        //int dczc = perceptionMapper.getYgStatusByStatus(1,communityCodes);
        dc.put("zc","0");
        //int dcyc = perceptionMapper.getYgStatusByStatus(2,communityCodes);
        dc.put("yc","0");
        //int dcgj = perceptionMapper.getYgWarnByStatus(communityCodes);
        dc.put("gj","0");
        devicePerception.setDc(dc);

        Map<String,String> jg = new HashedMap();
        int jglx = perceptionMapper.getJgStatusByStatus(3,communityCodes);
        jg.put("lx",String.valueOf(jglx));
        int jgzc = perceptionMapper.getJgStatusByStatus(1,communityCodes);
        jg.put("zc",String.valueOf(jgzc));
        int jgyc = perceptionMapper.getJgStatusByStatus(2,communityCodes);
        jg.put("yc",String.valueOf(jgyc));
        int jggj = perceptionMapper.getJgWarnByStatus(communityCodes);
        jg.put("gj",String.valueOf(jggj));
        devicePerception.setJg(jg);

        Map<String,String> sxj = new HashedMap();
        //int dclx = perceptionMapper.getYgStatusByStatus(3,communityCodes);
        sxj.put("lx","0");
        //int dczc = perceptionMapper.getYgStatusByStatus(1,communityCodes);
        sxj.put("zc","0");
        //int dcyc = perceptionMapper.getYgStatusByStatus(2,communityCodes);
        sxj.put("yc","0");
        //int dcgj = perceptionMapper.getYgWarnByStatus(communityCodes);
        sxj.put("gj","0");
        devicePerception.setSxj(sxj);

        Map<String,String> mj = new HashedMap();
        //int dclx = perceptionMapper.getYgStatusByStatus(3,communityCodes);
        mj.put("lx","0");
        //int dczc = perceptionMapper.getYgStatusByStatus(1,communityCodes);
        mj.put("zc","0");
        //int dcyc = perceptionMapper.getYgStatusByStatus(2,communityCodes);
        mj.put("yc","0");
        //int dcgj = perceptionMapper.getYgWarnByStatus(communityCodes);
        mj.put("gj","0");
        devicePerception.setMj(mj);

        return devicePerception;
    }

    public int getMjCount(List<String> communityCodes){
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        return perceptionMapper.getMjCount(communityCodes);
    }

    public int getJgCount(List<String> communityCodes){
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        return perceptionMapper.getJgCount(communityCodes);
    }

    public int getYgCount(List<String> communityCodes){
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        return perceptionMapper.getYgCount(communityCodes);
    }

    public int getPerceptionService(List<String> communityCodes){
        if(communityCodes.isEmpty()){
            communityCodes.add(null);
        }
        return perceptionMapper.getPerceptionService(communityCodes);
    }

    public List<Current> map2List(Map<String,String> map,int type){
        List<Current> list = new ArrayList<>();
        List<String> time = new ArrayList(map.keySet());
        List<String> count = new ArrayList(map.values());
        Current current = null;
        for(int i=0; i<time.size(); i++) {
            current = new Current();
            current.setTime(time.get(i));
            current.setCount(Integer.parseInt(count.get(i)));
            list.add(current);
        }

        //在对list进行排序
        if(type == 1){
            Collections.sort(list, new Comparator<Current>(){
                /*
                 * int compare(Person p1, Person p2) 返回一个基本类型的整型，
                 * 返回负数表示：p1 小于p2，
                 * 返回0 表示：p1和p2相等，
                 * 返回正数表示：p1大于p2
                 */
                public int compare(Current p1, Current p2) {
                    //按照Current的time进行升序排列
                    if(Integer.parseInt(p1.getTime().split(":")[0]) > Integer.parseInt(p2.getTime().split(":")[0])){
                        return 1;
                    }
                    if(Integer.parseInt(p1.getTime().split(":")[0]) == Integer.parseInt(p2.getTime().split(":")[0])){
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if (type == 2) {
            Collections.sort(list, new Comparator<Current>(){
                public int compare(Current p1, Current p2) {
                    //按照Current的time进行升序排列
                    if(Integer.parseInt(p1.getTime().split("-")[1]) > Integer.parseInt(p2.getTime().split("-")[1])){
                        return 1;
                    }
                    if(Integer.parseInt(p1.getTime().split("-")[1]) == Integer.parseInt(p2.getTime().split("-")[1])){
                        return 0;
                    }
                    return -1;
                }
            });
        }
        return list;
    }

    public List<Current> map2ListWarn(Map<String,String> map){
        List<Current> list = new ArrayList<>();
        List<String> time = new ArrayList(map.keySet());
        List<String> count = new ArrayList(map.values());
        Current current = null;
        for(int i=0; i<time.size(); i++) {
            current = new Current();
            current.setTime(time.get(i));
            current.setCount(Integer.parseInt(count.get(i)));
            list.add(current);
        }

        //对list进行排序


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Collections.sort(list, new Comparator<Current>(){
                public int compare(Current p1, Current p2) {
                    //按照Current的time进行升序排列
                    try {
                        if(dateFormat.parse(p1.getTime()).getTime() > dateFormat.parse(p2.getTime()).getTime()){
                            return 1;
                        }
                        if(dateFormat.parse(p1.getTime()).getTime() == dateFormat.parse(p2.getTime()).getTime()){
                            return 0;
                        }
                        return -1;
                    } catch (ParseException e){
                        e.printStackTrace();
                    }
                    return -1;
                }
            });
            for (int i=0; i<list.size(); i++) {
                list.get(i).setTime(list.get(i).getTime().substring(list.get(i).getTime().length()-5,list.get(i).getTime().length()));
            }

        return list;
    }

    public Map<String,String> padding(List<Current> list, int type){//要看0是不是第一个
        Map<String,String> sc = new HashedMap();
        if(type == 1){
            if (!list.isEmpty()) {
                for(int i=0; i < list.size(); i++){
                    sc.put(String.valueOf(list.get(i).getTime()) + ":00",String.valueOf(list.get(i).getCount()));
                }
            }
            for (int i=0; i < 24; i++){
                String value = sc.get(i + ":00");
                if(!StringUtils.isNotBlank(value)){
                    sc.put(i + ":00", "0");
                }
            }

        } else if (type == 2){
            Calendar a = Calendar.getInstance();
            a.set(Calendar.DATE, 1);
            a.roll(Calendar.DATE, -1);
            int maxDate = a.get(Calendar.DATE);//当月天数
            int month = a.get(Calendar.MONTH) + 1;//月份
            if (!list.isEmpty()) {
                for(int i=0; i < list.size(); i++){
                    sc.put(month + "-" + list.get(i).getTime(),String.valueOf(list.get(i).getCount()));
                }
            }
            for(int i=1; i <= maxDate; i++){
                String value = null;
                if(i < 10){
                    value = sc.get(month + "-" + "0" + i);
                    if (!StringUtils.isNotBlank(value)){
                        sc.put(month + "-" + "0" + i, "0");
                    }
                } else if (i >= 10){
                    value = sc.get(month + "-" + i);
                    if (!StringUtils.isNotBlank(value)){
                        sc.put(month + "-" + i, "0");
                    }
                }
            }
            //return sc;
        }
        return sc;
    }

    public Map<String,String> paddingWarn(List<Current> list, int type){
        Map<String,String> sc = new HashedMap();
        if (!list.isEmpty()) {
            for (int i=0; i<list.size(); i++){
                sc.put(list.get(i).getTime(),String.valueOf(list.get(i).getCount()));
            }
        }
        if(type == 1){
            List<String> sevenDayBefore =  test(7);//b.substring(b.length()-5,b.length())
            for(int i=0; i < 7; i++){
                String b = sevenDayBefore.get(i);
                String value = sc.get(b);
                if(!StringUtils.isNotBlank(value)){
                    sc.put(b,"0");
                }
            }
        } else if (type == 2){
            List<String> sevenDayBefore =  test(30);
            for(int i=0; i < 30; i++){
                String b = sevenDayBefore.get(i);
                String value = sc.get(b);
                if(!StringUtils.isNotBlank(value)){
                    sc.put(b,"0");
                }
            }
        }
        return sc;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> test(int intervals ) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        //ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = intervals-1; i >=0; i--) {
            pastDaysList.add(getPastDate(i));
            //fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }
}
