/**

 *
 */

//取实际长度，中文汉子占2位长度
function getBLen(str) {
    if (str == null) return 0;
    if (typeof str != "string"){
        str += "";
    }
    return str.replace(/[^\x00-\xff]/g,"01").length;
}

//中文汉子占2位长度，向右补全长度
function rpad(sourceStr,padStr,len){
    var pstr="";
    for(var i=0;i<len;i++){
        pstr=pstr+""+padStr;
    }
    return sourceStr+""+pstr.substring(0,len-getBLen(sourceStr));
}

function isreal(thestr) {
    if ((thestr=="0.00")||(thestr=="0")||(thestr=='')) {
        return 0;
    }
    var dotlst=thestr.indexOf(".");
    var dotend=thestr.length;
    if ((dotlst==-1)||(dotend - dotlst<=3)) {
        return thestr;
    }
    else {
        var dot2nd=thestr.indexOf(".");
        var intpart=thestr.substring(0, dot2nd+3);
        var decpart=thestr.substring(dot2nd+3,dot2nd+4);
        if(parseInt(decpart)>=5) {
            decpart="0.01";
            var thestr1=parseFloat(intpart);
            if(thestr1>0) {
                thestr1=thestr1+parseFloat(decpart);
            }
            else {
                thestr1=thestr1-parseFloat(decpart);
            }
            thestr=String(thestr1).substring(0,dot2nd+3);
        }
        else {
            thestr=intpart;
        }
        return thestr;
    }
}

function convert(thestr) {
    if ((thestr=="0.00")||(thestr=="0")||(thestr=='')) {
        return 0;
    }
    var dotlst=thestr.toString().indexOf(".");
    var dotend=thestr.length;
    if ((dotlst==-1)||(dotend - dotlst<=3)) {
        return thestr;
    }
    else {
        var dot2nd=thestr.indexOf(".");
        var intpart=thestr.substring(0, dot2nd+3);
        var decpart=thestr.substring(dot2nd+3,dot2nd+4);
        if(parseInt(decpart)>=5) {
            decpart="0.01";
            var thestr1=parseFloat(intpart);
            if(thestr1>0) {
                thestr1=thestr1+parseFloat(decpart);
            }
            else {
                thestr1=thestr1-parseFloat(decpart);
            }
            thestr=String(thestr1).substring(0,dot2nd+3);
        }
        else {
            thestr=intpart;
        }
        return thestr;
    }
}

function IsNumber(string) {
    var number;
    var i_blank=string.toString().indexOf(" ");
    if(string==null) {
        return false;
    }
    if(string.length==0) {
        return false;
    }
    if(i_blank==0) {
        return false;
    }
    number = new Number(string);
    if(isNaN(number)) {
        return false;
    }
    else {
        return true;
    }
}

function IsInt(string) {
    var number;
    var i_blank=string.toString().indexOf(" ");
    if (string==null) {
        return false;
    }
    if(string.length==0) {
        return false;
    }
    if(i_blank==0) {
        return false;
    }
    number = new Number(string);
    if(isNaN(number)) {
        return false;
    }
    else {
        if(number>=0) {
            var dotlst=string.indexOf(".");
            if(dotlst==-1) {
                return true;
            }
        }
        else {
            return false;
        }
    }
}

function str_lpad (v,length,pad_str) {
    var len = length;
    var strV = v.toString(),
        tem = [];
    if (len > strV.length) {
        for (var i = 0; i < len - strV.length; i++) {
            tem.push(pad_str);
        }
    }
    return tem.join('') + strV;
}

/**
 * format date to date string
 * @param date  {Date}
 * @returns {string}
 */
function Dateformatter (date) {
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
/**
 * parse date value
 * @param s  date {String}
 * @returns {Date}
 */
function Dateparser (s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

/**
 * 保留两位小数， 四舍五入
 * @param v {Number} 1.2312312321
 */
function rou (v) {
    return Math.round(v * 100) / 100
}

function strOutput (num) {

    var strOutput = "";
    var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';
    num += "00";
    var intPos = num.indexOf('.');
    if (intPos >= 0)
        num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
    strUnit = strUnit.substr(strUnit.length - num.length);
    for (var i=0; i < num.length; i++)
        strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);
    return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");
}


function ajaxLoading(){
    $("<div class=\"datagrid-mask datagrid-mask-m\"></div>").css({display:"block",width:"100%",height:$(window.document).height(),zIndex:99999}).appendTo("body");
    $("<div class=\"datagrid-mask-msg datagrid-mask-s\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",zIndex:99999,left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}


function ajaxLoading(loadingMsg){
    if(loadingMsg==undefined || loadingMsg==""){
        loadingMsg="正在处理，请稍候";
    }
    $("<div class=\"datagrid-mask datagrid-mask-m\"></div>").css({display:"block",width:"100%",height:$(window.document).height(),zIndex:99999}).appendTo("body");
    $("<div class=\"datagrid-mask-msg datagrid-mask-s\"></div>").html(loadingMsg+"。。。").appendTo("body").css({display:"block",zIndex:99999,left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

function ajaxLoadEnd(){
    $(".datagrid-mask-m").remove();
    $(".datagrid-mask-s").remove();
}

function getBirthdayFromIdCard(idCard) {
    var birthday = "";
    if(idCard != null && idCard != ""){
        if(idCard.length == 15){
            birthday = "19"+idCard.substr(6,6);
        } else if(idCard.length == 18){
            birthday = idCard.substr(6,8);
        }

        birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
    }

    return birthday;
}

function banBackSpace(e){
    var ev = e || window.event;
    //各种浏览器下获取事件对象
    var obj = ev.relatedTarget || ev.srcElement || ev.target ||ev.currentTarget;
    //按下Backspace键
    if(ev.keyCode == 8){
        var tagName = obj.nodeName //标签名称
        //如果标签不是input或者textarea则阻止Backspace
        if(tagName!='INPUT' && tagName!='TEXTAREA'){
            return stopIt(ev);
        }
        var tagType = obj.type.toUpperCase();//标签类型
        //input标签除了下面几种类型，全部阻止Backspace
        if(tagName=='INPUT' && (tagType!='TEXT' && tagType!='TEXTAREA' && tagType!='PASSWORD')){
            return stopIt(ev);
        }
        //input或者textarea输入框如果不可编辑则阻止Backspace
        if((tagName=='INPUT' || tagName=='TEXTAREA') && (obj.readOnly==true || obj.disabled ==true)){
            return stopIt(ev);
        }
    }
}


function stopIt(ev){
    if(ev.preventDefault ){
        //preventDefault()方法阻止元素发生默认的行为
        ev.preventDefault();
    }
    if(ev.returnValue){
        //IE浏览器下用window.event.returnValue = false;实现阻止元素发生默认的行为
        ev.returnValue = false;
    }
    return false;
}


$(function(){
    //实现对字符码的截获，keypress中屏蔽了这些功能按键
    document.onkeypress = banBackSpace;
    //对功能按键的获取
    document.onkeydown = banBackSpace;
})
