package com.neuedu.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    //两者相加的方法
    public static BigDecimal add(String value1, String value2){

        BigDecimal bigDecimal1=new BigDecimal(value1);
        BigDecimal bigDecimal2=new BigDecimal(value2);
        return bigDecimal1.add(bigDecimal2);
    }

    //两个数的乘法
    public static BigDecimal multi(String value1,String value2){

        BigDecimal bigDecimal1=new BigDecimal(value1);
        BigDecimal bigDecimal2=new BigDecimal(value2);
        return bigDecimal1.multiply(bigDecimal2);
    }


}
