package com.system.service;

import com.system.bean.Alidocs;
import org.springframework.stereotype.Service;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yjl on 2019-03-13.
 */
@Service("AlibabaService")
public class AlibabaService {
    // 添加默认索引属性
    public void addDefaultField() throws SolrServerException, IOException {
        // 声明要连接solr服务器的地址
        String url = "http://localhost:8983/solr";
        SolrServer solr = new HttpSolrServer(url);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "默认情况下必须添加的字段，用来区分文档的唯一标识");
        doc.addField("title", "默认的名称属性字段");
        solr.add(doc);

        solr.commit();
    }

    // 添加索引
    public void addIndex(Alidocs alidocs) throws SolrServerException, IOException {
        // 声明要连接solr服务器的地址
        String url = "http://localhost:8983/solr";
        SolrServer solr = new HttpSolrServer(url);
        solr.addBean(alidocs);
        solr.commit();
    }


    // 查找索引
    public String findIndex(String titleInput) throws SolrServerException {
        // 声明要连接solr服务器的地址
        String url = "http://localhost:8983/solr";
        SolrServer solr = new HttpSolrServer(url);

        // 查询条件
        SolrQuery solrParams = new SolrQuery();
        solrParams.setStart(0);
        solrParams.setRows(10);
        solrParams.setQuery("title:"+titleInput);
        // 开启高亮
        solrParams.setHighlight(true);
        solrParams.setHighlightSimplePre("<font color='red'>");
        solrParams.setHighlightSimplePost("</font>");

        // 设置高亮的字段
        solrParams.setParam("hl.fl", "title");
        // SolrParams是SolrQuery的子类
        QueryResponse queryResponse = solr.query(solrParams);

        // (一)获取查询的结果集合
        SolrDocumentList solrDocumentList = queryResponse.getResults();

        // (二)获取高亮的结果集
        // 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名
//        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

//        for (SolrDocument solrDocument : solrDocumentList) {
//            System.out.println("=====" + solrDocument.toString());
//            Map<String, List<String>> fieldsMap = highlighting.get(solrDocument.get("id"));
//            List<String> hightitle = fieldsMap.get("title");
//            System.out.println("hightitle======" + hightitle);
//
//        }

        // (三) 将响应结果封装到Bean
//        List<Alidocs> alidocsList = queryResponse.getBeans(Alidocs.class);

//        System.out.println(alidocsList + "+++++++++++");
//        for (Alidocs product : alidocsList) {
//            System.out.println(product);
//        }

        List contentList = new LinkedList();
        for (SolrDocument solrDocument : solrDocumentList) {
           Map<String,Object> map = new HashMap<>();
           map.put("id",solrDocument.get("id"));
           map.put("title",solrDocument.get("title"));
           contentList.add(map);

        }
        return contentList.toString();
    }
}
