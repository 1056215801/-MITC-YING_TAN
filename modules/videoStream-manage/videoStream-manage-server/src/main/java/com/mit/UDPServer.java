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


public class UDPServer {
    public static final int MAX_UDP_DATA_SIZE = 4096;
    public static final int UDP_PORT = 6008;
    public static DatagramPacket packet = null;
    public static DatagramSocket socket = null;



}


