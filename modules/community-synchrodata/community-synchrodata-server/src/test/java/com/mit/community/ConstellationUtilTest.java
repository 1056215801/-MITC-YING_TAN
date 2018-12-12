package com.mit.community;

import com.mit.community.util.ConstellationUtil;

import java.time.LocalDate;

/**
 * 星座
 *
 * @author shuyy
 * @date 2018/12/12
 * @company mitesofor
 */
public class ConstellationUtilTest {

//    @Test
    public void test() {
        String calc = ConstellationUtil.calc(LocalDate.now());
        System.out.println(calc);
    }

}
