package com.sizaif.emsdemo.utils;

import javax.xml.crypto.Data;
import java.text.ParseException;
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
    public static Date StringtoDate(String S){

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

    public static String DataLength(String str1,String str2){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(str1);
            d2 = sdf.parse(str2);

            System.out.println(d1);
            System.out.println(d2);

            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;

            long diff = d2.getTime() - d1.getTime();

            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;
            System.out.println(day+" "+hour+" "+min);
            return  day+"天"+hour+"时"+min+"分";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
