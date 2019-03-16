package com.common.commonUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by yjl on 2018-06-27.
 */
public class WifiUtils {
    private static ArrayList<String> parseMac(String str) {
        ArrayList<String> list=new ArrayList<>();
        String reg="([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}";
        Pattern p=Pattern.compile(reg);
        Matcher m=p.matcher(str);
        while(m.find()){
            //System.out.println(m.group());
            // System.out.println(m.start()+"...."+m.end());//找到每个字符的角标    }
            list.add(m.group());
        }
        return list;
    }
    /**
     * @param args
     */
    static  String macstr="Scan done, 7 wifi devices found.STA[000]: C0:61:18:93:3B:36, -75\nSTA[001]: 00:36:76:5C:7D:FC, -58\nData End.";
    public static void main(String[] args) {
        ArrayList<String> list=parseMac(macstr);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

    }
}
