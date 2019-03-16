package com.common.commonUtil;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具类
 *
 */
public class MailUitls {
	/**
	 * 发送邮件的方法
	 * 
	 * @param to
	 *            :收件人
	 * @param content
	 *            :正文内容
	 * @param title
	 *            :标题
	 */
	public static void sendMail(String to, String content ,String title) {
		/**
		 * 1.获得一个Session对象. 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
		 */
		// 1.获得连接对象
		// 收件人电子邮箱
		//String to = "3054481668@qq.com";

		// 发件人电子邮箱
//		String from = "13646718520@sina.cn";
		//String from = "zygxsq@163.com";
		String from = "sslm@jxlgnc.cn";

		// 指定发送邮件的主机为 smtp.sina.cn
//		String host = "smtp.sina.cn"; //  新浪邮件服务器
		//String host = "smtp.163.com"; //  新浪邮件服务器
		String host = "smtp.exmail.qq.com"; //  腾讯邮件服务器

		// 获取系统属性
		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//设置发件人的用户名与密码
//				return new PasswordAuthentication("13646718520", "13646718520123"); // 发件人邮件用户名、密码
				//return new PasswordAuthentication("zygxsq", "yujianlin520"); // 发件人邮件用户名、密码
				return new PasswordAuthentication("sslm@jxlgnc.cn", "jxustJXUST1180"); // 发件人邮件用户名、密码
			}
		});
		// 2.创建邮件对象:
		Message message = new MimeMessage(session);
		// 设置发件人:
		try {
			message.setFrom(new InternetAddress(from));
			// 设置收件人:
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 抄送 CC 密送BCC
			// 设置标题
			message.setSubject(title);
			// 设置邮件正文:
			message.setContent(
					"<html><body>"+content+"</body></html>",
					"text/html;charset=UTF-8");
			// 3.发送邮件:
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}


	/**1520386137@qq.com
	 * zygxsq@163.com
	 * @param args
	 */
	public static void main(String[] args) {
		sendMail("1247849227@qq.com", "我要投稿啦啦啦啦","投稿通知");
	}
}