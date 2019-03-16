import org.junit.Test;

import java.io.*;

/**
 * Created by ASUS on 2018/4/19.
 */
public class TestOk {




    @Test
    public void test(){
        String str = "1246789809";
        System.out.println(str.substring(1,5));
        System.out.println(str);
    }



    /**
     * 生成关系为1的数据   train.csv
     *
     */
    @Test
    public void fgTrain(){
        File fileName= new File("F:\\腾讯\\preliminary_contest_data\\train.csv");
        System.out.println(fileName.length());
        File fileName2= null;
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;
        fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\train\\0.csv");
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
                fw = new FileWriter(fileName2);
                bw = new BufferedWriter(fw);
                while ((str = br.readLine()) != null) {
                    if(",1".equals(str.substring(str.length()-2,str.length()))){//生成关系为-1的数据
//                    if("-1".equals(str.substring(str.length()-2,str.length()))){//生成关系为1的数据
                        continue ;
                    }else{
                        System.out.println(str);
                    }
                    bw.write(str);///写不出来 问题
                    bw.newLine();
                    bw.flush();
                }
            System.out.println("生成完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(bw,br);
        }

    }





    //查找该uid所对应的数据
    public void test00001(int[] uids){
        File fileName= new File("F:\\腾讯\\preliminary_contest_data\\userFeature.data");
//        System.out.println(fileName.length());
        File fileName2= null;
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;
        fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\user\\cs02.txt");
        for(int i = 0; i<uids.length;i++) {
            try {
                fr = new FileReader(fileName);
                br = new BufferedReader(fr);
//                System.out.println("正在生成！");
                fw = new FileWriter(fileName2, true);
                bw = new BufferedWriter(fw);
                flag:
                while ((str = br.readLine()) != null) {
                    if (String.valueOf(uids[i]).equals(str.substring(4, String.valueOf(uids[i]).length() + 4))) {//截头不截尾
//                        System.out.println(str);
                        bw.write(str);//
                        bw.newLine();
                        bw.flush();
                        break flag;
                    }
                }
//                System.out.println("生成完毕！");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeStream(bw, br);
            }
        }
        System.out.println("ok");
    }

    @Test
    public void getUser(){
        /**
         * uid
         */
        //注意改变  test00001 方法里的文件名称和位置 44072024
//        String uid = "48708770";
//        int[] data1 = {48708770,44072024,64954925,51156977,32300456,3596334,63545149,10068646,21547984,27974696,3202763,
//            27868908,4603811,33500782,31963951,43701447,71250935,20478481,41107086,42922290,21637710,64434563,80899782,43065549,
//            37592270,69754610,16445120,65246753,68658636,15661643,56671983,1343973,14502938,69160640,21043507,23422628};
//        int[] data1 = {67639909,64859229,73894430,68307467,39839363};
        int[] data1 = {56671983,4052561,41445013,69119483,37437020,38800423,27816236,310274,60102799,71507961,4889279,39040984,4106919,
                41656308,38633706,78467921,27860654,46174717};//userid  查找该id的信息
            test00001(data1);
    }



    //判断是不是数字
  public static boolean isNumeric(String str){
             for (int i = str.length();--i>=0;){
                if (!Character.isDigit(str.charAt(i))){
                         return false;
                  }
              }
        return true;
  }


  //将用户按照所选广告的进行整理
  @Test
  public void csSearchUserByAdid(){
       /**测试
        *  File fileName1 = new File("F:\\腾讯\\preliminary_contest_data\\案例数据\\ad.txt");
        File fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\案例数据\\train.txt");
        File fileName3 = new File("F:\\腾讯\\preliminary_contest_data\\案例数据\\user.txt");*/
        File fileName1 = new File("F:\\腾讯\\preliminary_contest_data\\adFeature.csv");
        File fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\train\\1.csv");
        File fileName3 = new File("F:\\腾讯\\preliminary_contest_data\\userFeature.data");
        searchUserByAdid(fileName1,fileName2,fileName3);
  }


    //查找 拥有相同的广告所对应的所有user的信息
    public void searchUserByAdid(File fileName1,File fileName2, File fileName3) {
//        File fileName1 = new File("F:\\腾讯\\preliminary_contest_data\\adFeature.csv");
//        File fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\train\\1.csv");
//        File fileName3 = new File("F:\\腾讯\\preliminary_contest_data\\userFeature.data");
        //        String fileName="F:\\腾讯\\preliminary_contest_data\\1.txt";
        File newFileName = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String str1 = null;
        String str2 = null;
        String str3 = null;
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        try {
            br1 = getFileReader(fileName1);
            while ((str1 = br1.readLine()) != null) { //读取广告信息
                int index1 = str1.indexOf(",");
                String temp1 = str1.substring(0, index1);//广告id
                if (isNumeric(temp1)) {
                    newFileName = new File("F:\\腾讯\\preliminary_contest_data\\cs01\\" + temp1 + ".csv");//正式
//                    newFileName = new File("F:\\腾讯\\preliminary_contest_data\\案例数据\\result\\"+temp1+".txt");//测试
                    fw = new FileWriter(newFileName);
                    bw = new BufferedWriter(fw);
//                System.out.println(temp1);
                    br2 = getFileReader(fileName2);
                    while ((str2 = br2.readLine()) != null) { //读取train信息
                        int index2 = str2.indexOf(",");
                        String temp2 = str2.substring(0, index2);// 广告id
                        if (isNumeric(temp2)) {
                            if (temp1.equals(temp2)) {//train里面匹配到了ad  获取其uid
                                String uid = str2.substring(index2 + 1, str2.length() - 1);//这就是uid 正式
//                                String uid = str2.substring(index2 + 1);//这就是uid 测试
                                br3 = getFileReader(fileName3);
                                flag:
                                while ((str3 = br3.readLine()) != null) {
                                        String tempUser = str3.substring(4, uid.length() + 4);//用户表里面的uid   正式
//                                        String tempUser = str3.substring(0, uid.length());//用户表里面的uid   测试
                                        if (tempUser.equals(uid)) {
                                            bw.write(str3);
                                            bw.newLine();
                                            bw.flush();
                                            break flag;
                                        }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(bw, br1);//流关闭
            try{
                if(br2 != null)
                br2.close();
                if(br3 != null)
                br3.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }



    //获取输入流
    public BufferedReader  getFileReader(File fileName){
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return br;
    }





    //关闭流
    public void  closeStream(BufferedWriter bw,BufferedReader br){
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    /**
     * 将分割后的 train 关系为0或者 1的 信息 再次分割到 excel可打开的数量   刚开始时用到，后期不怎么用
     */
    @Test
    public  void  ok001() {
        File fileName= new File("F:\\腾讯\\preliminary_contest_data\\分析\\1.csv");
        System.out.println(fileName.length());
//        String fileName="F:\\腾讯\\preliminary_contest_data\\1.txt";
        File fileName2= null;
        int i = 1;//文件名
        long sum = 0;//总文件
        int j = 0;//切割用的计数器
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;
        fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\test\\1-1"+i+".csv");
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            System.out.println("正在生成！");
            over:
            while(true) {
                fw = new FileWriter(fileName2);
                bw = new BufferedWriter(fw);
                flag:
                while ((str = br.readLine()) != null) {
                    if("-1".equals(str.substring(str.length()-2,str.length()))){
                        str="~~~~~~~~~~~~~~~~";
                    }else{
                        System.out.println(str);
                    }
                    bw.write(str);///写不出来 问题
                    j++;
                    bw.newLine();
                    bw.flush();
                    if (j>10000){//
                        j=0;
                        i++;
                        sum = sum+fileName2.length();
                        fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\分析\\1-"+i+".csv");
                        System.out.println("生成" + i + "个文件");
                        break flag;
                    }
                    if(sum>=fileName.length()){
                        System.out.println(sum);
                        System.out.println(fileName.length());
                        break over;
                    }
                }
            }
            System.out.println("生成完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(bw,br);
        }

    }



    /**
     * 观察打印 广告/用户/train 的信息的形式，以便于截取
     *
     */
    /*@Test
    public void getAdvertise(){
        File fileName= new File("F:\\腾讯\\preliminary_contest_data\\train.csv");
        System.out.println(fileName.length());
        File fileName2= null;
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;
        fileName2 = new File("F:\\腾讯\\preliminary_contest_data\\adFeature\\test.csv");
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            fw = new FileWriter(fileName2);
            bw = new BufferedWriter(fw);
            while ((str = br.readLine()) != null) {
//                    if(",1".equals(str.substring(str.length()-2,str.length()))){//生成关系为-1的数据
                if("-1".equals(str.substring(str.length()-2,str.length()))){//生成关系为1的数据
                    continue ;
                }else{
                    System.out.println(str);
                }
                bw.write(str);///写不出来 问题
                bw.newLine();
                bw.flush();
            }
            System.out.println("生成完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(bw,br);
        }

    }*/


}
