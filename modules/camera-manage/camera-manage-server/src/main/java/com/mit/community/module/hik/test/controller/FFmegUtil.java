package com.mit.community.module.hik.test.controller;



import cc.eguid.FFmpegCommandManager.FFmpegManagerImpl;

import java.util.HashMap;
import java.util.Map;

public class FFmegUtil {


public static String  startTransCoding(String appName,String name,String password,String ip,int channelNumber){

    String channelNumberStr="ch" +channelNumber;
    FFmpegManagerImpl s=new FFmpegManagerImpl(10);
    Map<String,String> map=new HashMap<>();
    map.put("appName",appName);
    map.put("input","rtsp://admin:admin123@192.168.1.163:554/h264/ch34/main/av_stream");
    map.put("output","rtmp://localhost:1935/live/");
    map.put("codec","libx264");
    map.put("fmt","flv");
    map.put("fps","25");
    map.put("rs","640x360");
    map.put("twoPart","2");
    map.put("qscale ","1");
    return s.start(map);
}

    public static void main(String[] args) {

       String s= startTransCoding("video","admin","admin123","192.168.1.163",34);
       System.out.print(s);

    }

}
