package com.mit.community.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class FaceUtil {
    public static boolean faceAnalyse(String disk, String faceExePath, String inputPhotoName, String outputPhotoName, String feaFileName) {
        boolean flag = false;
        String cmdCommand = "cmd /c " + disk + " && cd " + faceExePath + " && face -i " + inputPhotoName + " -o " + outputPhotoName + " -f " + feaFileName +" -v model";
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line+"\n");
                System.out.println(line);
            }
            File file = new File(faceExePath + "\\" + feaFileName);
            if (file.exists()) {
                flag = true;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
    }
}
