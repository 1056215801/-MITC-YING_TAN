package com.mit.community.util;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class Test4 {
    public static void main(String[] args){
       /* Map<String,String> params = new HashedMap();
        params.put("cellphone","18770914039");
        params.put("dateTag","0");
        params.put("times","1");
        params.put("deviceGroupId","174560555");
        params.put("communityCode","ab497a8a46194311ad724e6bf79b56de");
        String result = HttpPostUtil.sendPost1("http://192.168.1.12:9766/api/web/communitywanli/faceController/getInviteCode",params);
        System.out.println(result);*/

       /* Map<String,String> params = new HashedMap();
        params.put("cellphone","18770914039");
        params.put("dateTag","0");
        params.put("times","1");
        params.put("deviceGroupId","174560555");
        params.put("communityCode","ab497a8a46194311ad724e6bf79b56de");
        String result = HttpPostUtil.sendPost1("http://192.168.1.12:9766/api/web/communitywanli/faceController/getInviteCode",params);*/
        int a = 1;
        int b = a/100;//百位
        int c = (a%100)/10;//十位
        int d = d=a%10;//个位
        System.out.println("百位="+b);
        System.out.println("十位="+c);
        System.out.println("个位="+d);
    }
}
