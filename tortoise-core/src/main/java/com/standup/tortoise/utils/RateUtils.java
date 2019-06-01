package com.standup.tortoise.utils;

import java.math.BigDecimal;

/**
 * Created by Jiang Biao 2019/5/28
 */
public class RateUtils {


    public static Double computeRate(Long molecule,Long denominator){

        if(molecule == 0 || denominator == 0){
            return 0.0;
        }
        Double rate = molecule*1.0/denominator;
        BigDecimal bigDecimal = new BigDecimal(rate);
        return bigDecimal.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
    }



}
