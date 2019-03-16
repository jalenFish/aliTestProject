package yjl;

import java.io.*;

/**
 * Created by yjl on 2018-04-21.
 */
public class Txreade {
    public static void main(String[] args) {
        try {
            File csv = new File("F:suanfa/submission.csv"); // 写入的CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true)); // 附加

            BufferedReader reader = new BufferedReader(new FileReader("F:suanfa/test1.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                System.out.println(last);
                // 添加新的数据行
                int a = (int)(Math.random()*10);
                switch (a){
                    case 0:a=0;break;
                    case 1:a=1;break;
                    case 2:a=1;break;
                    case 3:a=2;break;
                    case 4:a=2;break;
                    case 5:a=3;break;
                    case 6:a=7;break;
                    case 7:a=8;break;
                    case 8:a=8;break;
                    case 9:a=8;break;
                    default:a=5;
                }

                bw.write(last + "," + a/10.0);
                bw.newLine();

            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
