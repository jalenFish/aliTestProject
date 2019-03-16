package yjl;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by yjl on 2018-05-21.
 */
public class MaopaoFile {
    public static void main(String[] args) {
        try {
            File csv = new File("E:maopao/VERSION2.txt"); // 写入的数据文件
            if(!csv.exists()){
                csv.createNewFile();

            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true)); // 附加

            BufferedReader reader = new BufferedReader(new FileReader("E:maopao/VERSION"));//要读取的文件名
            //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;

            LinkedList<String> versionList = new LinkedList();
            LinkedList<String> versionLine = new LinkedList();
            String content="";
            while((line=reader.readLine())!=null){
                System.out.println(line);

                String item[] = line.split("v1");
                boolean v1 = line.trim().startsWith("v1");

                if (v1){
                    //versionList.add(versionLine.toString());
                    versionList.add(content);
                    content="";
                    //versionLine.clear();
                }

                content+=line+"\r\n";

            }
            versionList.add(content);//最后一个aaa没有加进来，现在加进来
            int len = versionList.size();

            //倒叙输出
            for (int i =0;i<len;i++){
                String s = versionList.get(len-1-i);
                bw.write(s);

            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
