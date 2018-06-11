package com.baidu.ocrqa.sdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {


    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
