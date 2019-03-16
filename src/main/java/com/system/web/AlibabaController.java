package com.system.web;

import com.alibaba.fastjson.JSONObject;
import com.common.api.GetAliApi;
import com.system.bean.Alidocs;
import com.system.service.AlibabaService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yjl on 2019-03-12.
 */
@Controller
@RequestMapping("ali")
public class AlibabaController {
    protected final static Logger log = LoggerFactory.getLogger(AlibabaController.class);

    @Autowired
    AlibabaService alibabaService;

    /**
     * 爬取数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDocs")
    public String getDocs() {
        Map<String, Object> mapReturn = new HashMap<>(); //返回结果
        try {
        //爬取前先在solr上建林索引属性
        alibabaService.addDefaultField();

        //开始爬取指定url的数据
        String htmlResult = GetAliApi.sendGet("https://help.aliyun.com/document_detail/48851.html", "");
        //获取到 <ul id="J_MenuListContainer"> 树文档的内容
        String[] mainMenuListContainer = htmlResult.split("<ul id=\"J_MenuListContainer\">")[1].split("<div class=\"all-products\" id=\"J_AllProducts\">");
        //log.debug(mainMenuListContainer[0]);
        //log.debug("------------------------------");

        //进行正则获取数据
            String searchReg = "<span class=\"menu-item-text\">(.*?)</span>";
            Pattern pattern = Pattern.compile(searchReg); 	// 讲编译的正则表达式对象赋给pattern
            Matcher matcher = pattern.matcher(mainMenuListContainer[0]);

            int i = 0;
            String pre = "A";
            while (matcher.find()) {
                i++;
                String title = matcher.group(1);
                log.debug(title);
                //将数据放到solr里，添加索引
                Alidocs alidocs = new Alidocs();
                alidocs.setId(pre+i);
                alidocs.setTitle(title);
                alibabaService.addIndex(alidocs);
            }

            mapReturn.put("returnCode","00");
            mapReturn.put("content","爬取成功");
        }catch (Exception e){
            e.printStackTrace();
            mapReturn.put("returnCode","-1");
            mapReturn.put("content","爬取失败,请重试");
        }
        String mapStr = JSONObject.toJSONString(mapReturn);
        return mapStr;

    }

    /**
     * 通过关键词获取数据
     * @param title
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDocs")
    public String findDocs(String title)  {
        Map<String, Object> mapReturn = new HashMap<>(); //返回结果
        try {
            String result = alibabaService.findIndex(title);
            mapReturn.put("returnCode","00");
            mapReturn.put("content",result);
        }catch (Exception e){
            e.printStackTrace();
            mapReturn.put("returnCode","-1");
            mapReturn.put("content","查询异常");
        }
        String mapStr = JSONObject.toJSONString(mapReturn);
        return mapStr;
    }

    public static void main(String[] args) throws Exception{
        AlibabaController alibabaController = new AlibabaController();
        alibabaController.getDocs();
    }

}
