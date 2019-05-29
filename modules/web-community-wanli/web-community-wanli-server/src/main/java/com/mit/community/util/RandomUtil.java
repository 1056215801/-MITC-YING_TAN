package com.mit.community.util;

import java.math.BigDecimal;

/**
*  随机数工具类
* @Author: shuyy
* @Date: 2018/11/2 16:02
* @company mitesofor
*/
public class RandomUtil {

	public static int random(int begin,int end){
		int rtn = begin + (int)(Math.random() * (end - begin));
//		if(rtn == begin || rtn == end){
//			return random(begin,end);
//		}
		return rtn;
	}
	
	public static Float randomFloat(float max, float min) {
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);  
        return Float.parseFloat(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());  
	}
	
	public static void main(String[] args) {
//		 int rtn = 2 + (int)(Math.random() * 1);
//		 System.out.println(rtn);
		RandomUtil.random(2, 3);
	}
}


