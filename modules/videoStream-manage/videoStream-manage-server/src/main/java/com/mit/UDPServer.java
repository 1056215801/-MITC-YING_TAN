package com.mit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 用于响应QQ物联摄像头的心跳包
 * @date 2019/5/8
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: mitesofor </p>
 */

@WebListener
public class UDPServer implements ServletContextListener{
    public static final int MAX_UDP_DATA_SIZE = 4096;
    public static final int UDP_PORT = 6008;
    public static DatagramPacket packet = null;
    public static DatagramSocket socket = null;


    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    class UDPProcess implements Runnable {

        public UDPProcess(final int port) throws SocketException {
            //创建服务器端DatagramSocket，指定端口
            socket = new DatagramSocket(port);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
                packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    new Thread(new Process(packet)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Process implements Runnable {
        public Process(DatagramPacket packet) throws UnsupportedEncodingException {
            // TODO Auto-generated constructor stub
            byte[] buffer = packet.getData();// 接收到的UDP信息，然后解码
            String srt2 = new String(buffer, "UTF-8").trim();
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                System.out.println("====向客户端响应数据=====");
                //1.定义客户端的地址、端口号、数据
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] data2 = "{\"response\":\"alive\",\"errcode\":0}".getBytes();
                //2.创建数据报，包含响应的数据信息
                DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
                //3.响应客户端
                socket.send(packet2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println("========启动一个线程，监听UDP数据报.PORT:" + UDP_PORT + "=========");
        //logger.info("========启动一个线程，监听UDP数据报.PORT:" + UDP_PORT + "=========");
        // 启动一个线程，监听UDP数据报
        try{
            new Thread(new UDPProcess(UDP_PORT)).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}


