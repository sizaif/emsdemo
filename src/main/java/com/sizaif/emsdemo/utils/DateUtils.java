package com.sizaif.emsdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //时间并转化为字符串
    public static String DatetoString(Date nowtime)
    {
//        Date nowtime = new Date();
        //格式： Wed Jul 06 09:28:19 CST 2016

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将其转换成"yyyy-MM-DD HH:mm:ss"格式输出 y->年  M->月 D->日 H->时 m->份 s->秒
        String stringformattime = sdf.format(nowtime);
        return stringformattime;
    }
    //将字符串时间转化为标准时间格式
    public static Date StrngtoDate(String S){

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
        try {
            Date d = sdf1.parse(S);
            //输出:Wed Jan 08 07:00:00 CST 1986
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
