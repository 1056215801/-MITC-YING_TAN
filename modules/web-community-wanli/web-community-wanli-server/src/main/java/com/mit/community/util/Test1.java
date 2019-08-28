package com.mit.community.util;

import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/*
在java中网络通讯业称作为Socket(插座)通讯，要求通讯 的两台器都必须要安装Socket。

不同的协议就有不同 的插座（Socket）

UDP通讯协议的特点：
    1. 将数据极封装为数据包，面向无连接。
    2. 每个数据包大小限制在64K中
    3.因为无连接，所以不可靠
    4. 因为不需要建立连接，所以速度快
    5.udp 通讯是不分服务端与客户端的，只分发送端与接收端。


    比如： 物管的对讲机, 飞Q聊天、 游戏...

udp协议下的Socket:

    DatagramSocket(udp插座服务)
    DatagramPacket(数据包类)
        DatagramPacket(buf, length, address, port)
        buf: 发送的数据内容
        length : 发送数据内容的大小。
        address : 发送的目的IP地址对象
        port : 端口号。

发送端的使用步骤：
    1. 建立udp的服务。
    2. 准备数据，把数据封装到数据包中发送。 发送端的数据包要带上ip地址与端口号。
    3. 调用udp的服务，发送数据。
    4. 关闭资源。


*/


//发送端
@SuppressWarnings("unused")
public class Test1 {

    public static void main(String[] args) throws IOException {
        //建立udp的服务
       /* DatagramSocket datagramSocket = new DatagramSocket();
        //准备数据，把数据封装到数据包中。
        String data = "这个是我第一个udp的例子..";
        //创建了一个数据包
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length,InetAddress.getByName("127.0.0.1") , 8237);
        //调用udp的服务发送数据包
        datagramSocket.send(packet);
        //关闭资源 ---实际上就是释放占用的端口号
        datagramSocket.close();*/
       //String a = "吸毒人员";
       //String[] s = a.split(",");
        /*String a = "http://192.168.1.251:1987/S-T-A-R-T||DOWNFILE||20190723155733006.jpg||E-N-D";

        String deviceGroupIds = a.substring(a.length()-28,a.length()-7);
       System.out.println(deviceGroupIds);*/
        /*BASE64Encoder encoder = new BASE64Encoder();
        File file = new File("f://face//out.fea");
        String feaBase64 = encoder.encode(Files.readAllBytes(file.toPath()));
        Map<String,String> params = new HashedMap();
        params.put("cmd","faceRegister");
        params.put("feaName","out.fea");
        params.put("fea",feaBase64);
        params.put("houseHoldId","12345");
        String result = HttpPostUtil.sendPost1("http://192.168.1.140:28085",params);
        System.out.println(result);*/
<<<<<<< HEAD
        /*Map<String,String> params = new HashedMap();
=======
        Map<String,String> params = new HashedMap();
>>>>>>> remotes/origin/newdev
        params.put("cmd","cardAdd");
        params.put("id","123");
        params.put("cardNum","4170481");
        String result = HttpPostUtil.sendPost1("http://192.168.1.140:28085",params);
<<<<<<< HEAD
        System.out.println(result);*/
=======
        System.out.println(result);
>>>>>>> remotes/origin/newdev
        /*JSONObject json = JSONObject.fromObject(result);
        String base64 = json.getString("base64");
        System.out.println(base64);*/
        /*Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println(base64);*/
        /*String a = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(a);
        System.out.println(a.substring(a.length()-15,a.length()));*/
<<<<<<< HEAD

        long time = System.currentTimeMillis();
        String timeStr = String.valueOf(time);
        System.out.println(timeStr.substring(timeStr.length()-11, timeStr.length()));
=======
>>>>>>> remotes/origin/newdev
    }

}
