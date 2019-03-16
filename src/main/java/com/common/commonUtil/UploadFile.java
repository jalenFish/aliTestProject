package com.common.commonUtil;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.common.constant.CommonConst.FAIL_RESULT_RETURN_CODE;
import static com.common.constant.CommonConst.SUCCESS_RESULT_RETURN_CODE;

/**
 * Created by yjl on 2018-04-23.
 */
public class UploadFile {
    public static Log log = LogFactory.getLog(UploadFile.class);


    public String commonupload( HttpServletRequest request, HttpServletResponse response) {
        String uploadPath = request.getSession().getServletContext().getRealPath("uploadfiles/biyewall/");//设置文件上传的路径
        File file = new File(uploadPath);
        //如果目录不存在
        if (!file.isDirectory()) {
            //创建文件上传目录
            file.mkdirs();
        }
        Map<String, String> mapReturn = new HashMap<>(); //返回结果

        Map<String, String> mapObj = new HashMap<>();
        String uuidxh = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        mapObj.put("uuidxh", uuidxh);
        try {
            //检查请求类型
            //当enctype="multipart/form-data"并且method是post时，isMultipart为真
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            String[] mapKeyName = {"author", "liuyan", "photo"};
            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();//为该请求创建一个DiskFileItemFactory对象，通过它来解析请求

                factory.setSizeThreshold(4096); // 设置缓冲区大小
                //factory.setRepository(tempPathFile);// 设置缓冲区目录
                ServletFileUpload upload = new ServletFileUpload(factory);
                //解决上传文件名的中文乱码
                upload.setHeaderEncoding("UTF-8");
                upload.setSizeMax(41943040); // 设置最大文件尺寸，这里是40MB

                List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
                Iterator<FileItem> i = items.iterator();
                int success = 0;//保存成功的次数
                int pre = 0;//执行while循环的次数
                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (fi.isFormField()) {
                        //如果是普通表单字段
                        String name = fi.getFieldName();
                        String value = fi.getString();
                        if (mapKeyName[0].equals(name)) {
                            mapObj.put(mapKeyName[0], value);
                        } else if (mapKeyName[1].equals(name)) {
                            mapObj.put(mapKeyName[1], value);
                        }

                    } else {
                        /*//将文件作为流传给实名认证平台
                        InputStream inStream = fi.getInputStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        int ch;
                        String res = "" ;
                        while ((ch = inStream.read()) != -1) {
                            out.write(ch);
                        }
                        byte [] result = out.toByteArray();
                        //res = Base64.byteArrayToBase64(result);
                        System.out.println("outputStreamResult----"+result);
                        if(mapKeyName[2].equals(fi.getFieldName())){
                            mapObj.put(mapKeyName[2], Base64.byteArrayToBase64(result));
                        }else if (mapKeyName[3].equals(fi.getFieldName())) {
                            mapObj.put(mapKeyName[3], Base64.byteArrayToBase64(result));
                        }else if (mapKeyName[4].equals(fi.getFieldName())) {
                            mapObj.put(mapKeyName[4], Base64.byteArrayToBase64(result));
                        }*/
                        //将文件保存到本地，
                        //byte[] bytes = fi.getName().getBytes();
                        //File fullfile = new File(new String(bytes, "utf-8"));
                        //File uploadfile = new File(uploadPath,fullfile.getName());
                        String fileName = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()).toString() + ("" + ((int) ((Math.random() * 9 + 1) * 100000))) + ".jpg";
                        mapObj.put("photourl", "biyewall/" + fileName);
//                        int s = biyeWallService.saveBiyeWallxx(mapObj);
//                        if (s >= 1) {
//                            success++;
//                        }
                        File uploadfile = new File(uploadPath, fileName);
                        fi.write(uploadfile);
                    }
                    pre++;

                }
                if (pre == success && success>=1) {
                    mapReturn.put("returnCode", SUCCESS_RESULT_RETURN_CODE);
                    mapReturn.put("returnMessage", "上传成功");
                }

            } else {
                mapReturn.put("returnCode", FAIL_RESULT_RETURN_CODE);
                mapReturn.put("returnMessage", "提交的信息不是流文件");
            }
        } catch (Exception e) {
            mapReturn.put("returnCode", FAIL_RESULT_RETURN_CODE);
            mapReturn.put("returnMessage", "上传图片出现异常，请联系管理人员修复");
            e.printStackTrace();
        }
        String mapStr = JSONObject.toJSONString(mapReturn);
        System.out.println("mapStr----" + mapStr);
        return mapStr;
    }

    public Object uploadSjjTpc(HttpServletRequest request) throws IOException {
        Map resultMap = new HashMap();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        // 1. build an iterator
        Iterator<String> itr = multipartHttpServletRequest.getFileNames();
        MultipartFile mpf = null;
        // 2. get each file
        while (itr.hasNext()) {
            // 2.1 get next MultipartFile
            mpf = multipartHttpServletRequest.getFile(itr.next());
            // System.out.println(mpf.getOriginalFilename() + " uploaded! ");
            log.info(mpf.getOriginalFilename() + " uploaded! " + "---" + mpf.getSize() / 1024 + " Kb");
            log.info(mpf.getContentType());
            InputStream inputStream = mpf.getInputStream();
            String contextPath = request.getContextPath();
            String CurrentClassFilePath = this.getClass().getResource("").getPath();
            String realPath = request.getSession().getServletContext().getRealPath("");
            log.debug("realpath---" + realPath);
            log.debug("contextPath---" + contextPath);
            log.debug("CurrentClassFilePath---" + CurrentClassFilePath);
            InputStream excelInputStream = mpf.getInputStream();
            try {
                // 拷贝文件到指定的目录
                FileUtils.copyInputStreamToFile(inputStream,
                        new File(realPath + "/files/" + mpf.getOriginalFilename()));
                // 判断上传的文件的格式是否符合要求
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("拷贝文件失败" + e.getMessage());
            }
        }
        return resultMap;

    }
}
