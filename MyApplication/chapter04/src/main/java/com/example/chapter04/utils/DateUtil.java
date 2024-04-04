package com.example.chapter04.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static  String getNowTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
