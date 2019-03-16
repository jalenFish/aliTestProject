package com.common.commonUtil;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Date;
import java.util.Properties;

/**
 * Created by yjl on 2018-04-23.
 */
public class CommonEmail {

    public static void main(String[] args) {
        CommonEmail commonEmail = new CommonEmail();
        try {
            commonEmail.sendHtmlMail2("1247849227@qq.com","http://jxl.jxlgnc.cn/Public/qwadmin/css/images/banner.jpg",
                    "你好","这是一跳测试邮件，我是内容，恭喜发财");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private String host = "smtp.163.com";
    private int port = 465;
    private String userName = "zygxsq@qq.com";  //邮件发送者
    private String password = "yujianlin520";
    private String to = "";                      //邮件接收者


    /**
     * 发送HTML邮件
     * @param emailRecept   邮件接收者
     * @param img  图片地址
     * @param emailTitle  邮件标题
     * @param emailContent  邮件内容
     * @throws Exception
     */
    public void sendHtmlMail(String emailRecept,String img,String emailTitle,String emailContent) throws Exception
    {
        HtmlEmail mail = new HtmlEmail();
        // 设置邮箱服务器信息
        mail.setSmtpPort(port);
        mail.setHostName(host);
        // 设置密码验证器
        mail.setAuthentication(userName, password);
        // 设置邮件发送者
        mail.setFrom(userName);
        // 设置邮件接收者
        mail.addTo(emailRecept);
        // 设置邮件编码
        mail.setCharset("UTF-8");
        // 设置邮件主题
        mail.setSubject(emailTitle);
        // 设置邮件内容
        mail.setHtmlMsg(
                "<html><body><div>"+emailContent+"</div><img src='"+img+"'/></body></html>");
        // 设置邮件发送时间
        mail.setSentDate(new Date());
        // 发送邮件
        mail.send();
    }

    /**
     * 发送Html邮件
     *
     * @throws Exception
     */
    public void sendHtmlMail2(String emailRecept,String img,String emailTitle,String emailContent) throws Exception
    {
        Properties pro = System.getProperties();
        pro.put("mail.smtp.host", host);
        pro.put("mail.smtp.port", port);
        pro.put("mail.smtp.auth", "true");

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,
                new Authenticator()
                {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(userName, password);
                    }
                });
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 设置邮件消息的发送者
        mailMessage.setFrom(new InternetAddress(userName));
        // 创建邮件的接收者地址，并设置到邮件消息中
        mailMessage.setRecipient(Message.RecipientType.TO,
                new InternetAddress(emailRecept));
        // 设置邮件消息的主题
        mailMessage.setSubject(emailTitle);
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());

        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(
                "<html><body><div>"+emailContent+"</div><img src='"+img+"'/></body></html>",
                "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);
        // 发送邮件
        Transport.send(mailMessage);
    }
}
