package com.zdy.mystarter.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO 和时间相关的工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 12:54
 * @desc
 */
public class TimeUtils {

    /**
     * 根据日期格式获取日期字符串
     * @param timeFormat 日期格式，默认：yyyyMMddmmss
     * @return
     */
    public static String getServetTime(String timeFormat){
        String format=(null==timeFormat)?"yyyyMMddmmss":timeFormat;
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat formatter=new SimpleDateFormat(format);
        return formatter.format(date);
    }


}
