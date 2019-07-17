package com.mit.community.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
    /*public static void main(String[] args){
        String command="ipconfig -all";
        String s="IPv4";
        String line = null;
        StringBuilder sb = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
            BufferedReader	bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream(),"GB2312"));

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println("===="+line);
                if (line.contains(s)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }*/
    public static void main(String [] args) throws IOException {
        /*ProcessBuilder builder = new ProcessBuilder();
        List<String> list = new ArrayList<>();
        list.add("cmd.exe");
        list.add("ipconfig -all");
        *//*list.add("dir");
        list.add("d:\\");*//*
        Process process = builder.command(list).start();
        InputStream inputStream = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
        String line = null;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }*/

            String cmdCommand = "cmd /c f: && cd F:\\face\\ && face.exe && face -i 1.jpg -o out.jpg -f out.fea -v model";
            //String cmdCommand = "cmd /c f: && cd f:\\face && face -i 1.jpg -o out.jpg -f out.fea -v model";
        //String path = "f:\\face\\start.bat";
        StringBuilder stringBuilder = new StringBuilder();
            Process process = null;
            try {
                //process = Runtime.getRuntime().exec("cmd.exe /k start " + path);
                process = Runtime.getRuntime().exec(cmdCommand);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), "GBK"));
                String line = null;
                while((line=bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line+"\n");
                    System.out.println(line);
                }
                //return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                //return null;
            }

    }
}
