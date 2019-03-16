package com.common.constant;

/**
 * 静态常量类，常量在这里定义
 * Created by yjl on 2017/9/5.
 */
public class CommonConst {
    /**
     * 是否使用redis
     * true 表示使用，其他表示不使用
     */
    public static final String IS_USE_REDIS = "true";

    /**
     * 返回成功的returnCode
     */
    public static final String SUCCESS_RESULT_RETURN_CODE = "00";


    /**
     * 返还失败的returnCode
     */
    public static final String FAIL_RESULT_RETURN_CODE ="-1";


    /**
     * 每月赠送的电费
     */
    public static final Double DF_EVERYMONTH_GIVING = 30d;
    /**
     * 电费单价
     */
    public static final Double DF_Dj = 0.62d;
}
