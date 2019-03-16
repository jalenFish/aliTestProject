package com.common.commonUtil;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by yjl on 2018-04-23.
 */
public class CommonUtil {



    /**
     * 判断list里面是否有不为null的数据
     * @param list
     * @authon yjl
     * @time 2018-06-28
     * @return
     */
    public static Boolean ListIsHaveData(List list){
        int size = list.size();
        return size > 0 && list.get(size - 1) != null &&
                !"".equals(list.get(size - 1));
    }




    // 上传文件/复制文件。
    public static void copyFile(File src, File dst) {
        try {
            int BUFFER_SIZE = 16 * 1024;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src),
                        BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                for (int byteRead = 0; (byteRead = in.read(buffer)) > 0; )
                {
                    out.write(buffer, 0, byteRead);
                }

            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 伪造ip地址
     * @return
     */
    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

    public static JSONObject returnAjax(String returnCode, String returnMessage)
    {
        JSONObject jo = new JSONObject();
        jo.put("returnCode", returnCode);
        jo.put("returnMessage", returnMessage);
        return jo;
    }

    public static JSONObject returnSuccess(){
        JSONObject jo = new JSONObject();
        jo.put("returnCode", "00");
        jo.put("returnMessage", "");
        return jo;
    }

    /**
     * 是否regx包含str，包含返回ture
     * @param str
     * @param regx
     * @return
     */
    public static boolean isContains(String str, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (str.indexOf(regx[i]) != -1) { //包含指定参数，返回true
                return true;
            }
        }
        return result;
    }


    /**方
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
  */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
