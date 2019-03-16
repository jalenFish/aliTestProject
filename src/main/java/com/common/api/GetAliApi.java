package com.common.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by yjl on 2019-03-12.
 */
public class GetAliApi {

    public static String sendGet(String url, String param) {
        String result = "";
        String urlName = url + "?" + param;
        try {

            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            //伪造ip访问
            String ip = randIP();
            System.out.println("目前伪造的ip："+ip);
            conn.setRequestProperty("X-Forwarded-For", ip);
            conn.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);
            conn.setRequestProperty("HTTP_CLIENT_IP", ip);
            conn.setRequestProperty("REMOTE_ADDR", ip);
            conn.setRequestProperty("Host", "help.aliyun.com/");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
            conn.setRequestProperty("Referer","https://help.aliyun.com/"); //伪造访问来源
            conn.setRequestProperty("Origin", "https://help.aliyun.com/"); //伪造访问域名
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                System.out.println(s + "-->" + map.get(s));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url,String param){
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            //伪造ip访问
            String ip = randIP();
            System.out.println("目前伪造的ip："+ip);
            conn.setRequestProperty("X-Forwarded-For", ip);
            conn.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);
            conn.setRequestProperty("HTTP_CLIENT_IP", ip);
            conn.setRequestProperty("REMOTE_ADDR", ip);
            conn.setRequestProperty("Host", "help.aliyun.com/");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
            conn.setRequestProperty("Referer","https://help.aliyun.com/"); //伪造访问来源
            conn.setRequestProperty("Origin", "https://help.aliyun.com/"); //伪造访问域名
            //post设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while((line = in.readLine()) != null){
                result +="\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
}
