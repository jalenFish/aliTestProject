package com.common.commonUtil;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yjl on 2018-06-05.
 * 该类用于公共的处理下载相关的类
 */
@Service
public class CommonDownUtil {

    /**
     *下载导出Excel类
     * @param excel_name 导出excel文件的文件名
     * @param titleStr  表头标题
     * @param fieldJa   正文内容对应的key的字段
     * @param titleLength  表头占了几行
     * @param listContent  正文内容
     * @param response
     * @return
     * @throws Exception
     */
    public String commonDownExcel(String excel_name, String[][] titleStr, String[] fieldJa,Integer titleLength, List<Object> listContent,HttpServletRequest request, HttpServletResponse response) throws IOException{
        OutputStream output = response.getOutputStream();
        String filename = excel_name + ".xls";
        try {
            filename = encodeChineseDownloadFileName( request,filename); //避免不同浏览器中文件名中文乱码
        }catch (Exception e){
            e.printStackTrace();
            return "文件名称乱码异常";
        }

        response.setCharacterEncoding("UTF-8");// 设置字符集
        /*response.setContentType("application/msexcel");
        response.addHeader("Content-Disposition","attachment;   filename=\""+ excel_name +   "\"");*/
        response.setHeader("Content-disposition", filename);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="+filename);
        response.setHeader("Pragma", "No-cache");
        String result = Excel.CommonExcel(filename, titleStr, fieldJa, listContent, titleLength, output);


        return result;

    }

    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
     */
    public static String encodeChineseDownloadFileName(
            HttpServletRequest request, String pFileName) throws Exception {

        String filename = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent){
            if (-1 != agent.indexOf("Firefox")) {//Firefox
                filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8"))))+ "?=";
            }else if (-1 != agent.indexOf("Chrome")) {//Chrome
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {//IE7+
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = filename.replace("+", "%20");
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }


    /**
     * 文件下载功能  以后可以根据需要重载此方法
     * @param request
     * @param response
     * @param url  下载的路径
     * @param fileName  下载文件的名字
     * @throws Exception
     */
    public static void down(HttpServletRequest request,HttpServletResponse response,String url,String fileName) throws Exception{
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(url)));
        //假如以中文名下载的话txt";
//        String filename = "下载文件.
        //转码，免得文件名中文乱码
        fileName = URLEncoder.encode(fileName,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }


}
