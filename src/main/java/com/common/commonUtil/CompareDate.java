package com.common.commonUtil;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yjl on 2018-03-09.
 * 关于时间比较、时间和字符串互转的公共类
 */
@Component
public class CompareDate {


    public static void main(String[] args) throws Exception{
//        int i = compareMonth("2018-03-01", null);
        int i = CompareDate.compareDate("2017-02-13", null, 1);
        System.out.println(i);
    }


    /**
     * 比较前后两个时间，相差几年或几月或几日
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;

        String[] u = {"天","月","年"};
        String formatStyle = stype==1?"yyyy-MM-dd":"yyyy-MM-dd";

        date2 = date2==null?CompareDate.getCurrentDate():date2;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("比较时间异常");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }

        n = n-1;

        if(stype==2){
            n = (int)n/365;
        }

        System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
        return n;
    }


    /**
     * 比较前后两个时间是否在同月
     * @param beginTime
     * @param endTime
     * @return
     */
    public int compareMonth(String beginTime,String endTime){
       endTime=(endTime==null?CompareDate.getCurrentDate():endTime);
        System.out.println(endTime);

        //将String转为Date
        Date beginDate = CompareDate.StrToDate(beginTime);
        Date endDate = CompareDate.StrToDate(endTime);

        //获取前者中的月份
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        int beginMonth = beginCal.get(Calendar.MONTH)+1;
        //获取后者中的月份
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endMonth = endCal.get(Calendar.MONTH)+1;

        return endMonth-beginMonth;
    }


    /**
     * 得到当前日期
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);

    }

    /**
     * 获得当前年份
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }


    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 日期转换成字符串  包含时分秒
     * @param date
     * @return str
     */
    public static String DateToStrIncludeHMS(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 字符串转换成日期 包含时分秒
     * @param str
     * @return date
     */
    public static Date StrToDateIncludeHMS(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
