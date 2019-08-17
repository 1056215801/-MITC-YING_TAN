package com.mit.community.util;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        //1.创建客户端Socket，指定服务器地址和端口
        try{
            Socket so=new Socket("192.168.1.139", 1234);//端口号要和服务器端相同
            //2.获取输出流，向服务器端发送登录的信息
            OutputStream os=so.getOutputStream();//字节输出流
            PrintWriter pw=new PrintWriter(os);//字符输出流
            BufferedWriter bw=new BufferedWriter(pw);//加上缓冲流
            bw.write("用户名：admin;密码：123");
            bw.flush();
            so.shutdownOutput();//关闭输出流
            //3.关闭资源
            bw.close();
            pw.close();
            os.close();
            so.close();
        }catch (Exception e){e.printStackTrace();}

    }
}

