<%--
  Created by IntelliJ IDEA.
  User: yjl
  Date: 2019-03-13
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>阿里测试题</title>
    <script src="/public/js/jquery-2.1.4.min.js"></script>
    <script src="/public/js/selfMsgBox.js"></script>
    <script src="/public/js/jquery.easyui.min.js" ></script>
    <script src="/js/public.js" ></script>
    <link type="text/css" rel="stylesheet" href="pages/AliDocsTest/css/weui.css" />
    <style>

    </style>
</head>
<body>
<div class="weui-btn-area">
    <div class="weui-cell__hd">1、先爬取文档数据</div>
    <a  class="weui-btn weui-btn_mini weui-btn_primary" id="getDocs">开始爬取</a>
</div>
<div class="weui-cells weui-cells_form">
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">搜索关键词</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input keytitle"  type="text"  placeholder="请输入关键词"/>
        </div>
    </div>
</div>
<div class="weui-btn-area">
    <a  class="weui-btn weui-btn_mini weui-btn_primary" id="findDocs">查询</a>
</div>


<script>

    $('#getDocs').click(function () {
        ajaxLoading('爬取中,请稍后...');
        $.ajax({
            url: "/ali/getDocs",
            data: {},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                ajaxLoadEnd();

                $.MsgBox.Alert("提示",data.content,"确定");

            },
            error: function () {
                $.MsgBox.Alert("异常","爬取发生异常，请联系管理员！","确定");
            }
        })
    })


    $('#findDocs').click(function () {
        var keytitle = $('.keytitle').val();
        if(keytitle==""){
            $.MsgBox.Alert("提示","淘气！请输入内容","确定");
            return
        }
        ajaxLoading('查询中...');
        $.ajax({
            url: "/ali/findDocs",
            data: {"title":keytitle},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                ajaxLoadEnd();
                if (data.returnCode=="00"){
                    $.MsgBox.Alert("提示",data.content,"确定");
                }else {
                    $.MsgBox.Alert("提示",data.content,"确定");
                }

            },
            error: function () {
                $.MsgBox.Alert("异常","查询发生异常，请联系管理员！","确定");
            }
        })
    })

    function ajaxLoading(text){
        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html(text).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
    }
    function ajaxLoadEnd(){
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }

</script>
</body>
</html>
