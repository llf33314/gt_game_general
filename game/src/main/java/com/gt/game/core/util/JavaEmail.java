package com.gt.game.core.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发送邮件
 * @author qusk
 * @date 2015年11月4日
 * @description
 */
public class JavaEmail {
	private MimeMessage message;
	private Session session;
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";

	private Properties properties = new Properties();

	private static JavaEmail sendEmail=null;
	/*
	 * 初始化方法
	 */
	public JavaEmail(String host, String username, String password) {
		InputStream in = JavaMailWithAttachment.class.getResourceAsStream("/MailServer.properties");
        try {
            properties.load(in);
            this.mailHost =host;
            this.sender_username = username;
            this.sender_password = password;
        } catch (IOException e) {
            e.printStackTrace();
        }

        session = Session.getInstance(properties);
        session.setDebug(true);// 开启后有调试信息
        message = new MimeMessage(session);
	}
	
	public static JavaEmail getInstence(String host,String username,String password){
		if(sendEmail == null){
			sendEmail = new JavaEmail(host,username,password);
		}
		return sendEmail;
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 * @param attachment
	 *            附件
	 */
	@SuppressWarnings("finally")
	public boolean doSendHtmlEmail(String subject, String sendHtml,
			String receiveUser, File attachment) {
		boolean result = true;
		try {
			// 发件人
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);

			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);

			// 邮件主题
			message.setSubject(subject,"utf-8");
			//message.setSubject(MimeUtility.encodeText(subject, "big5", "B"));
			
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility
						.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();

			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, sender_username, sender_password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean doSendHtmlEmail1(String subject, String sendHtml,
			String receiveUser, File attachment) {
		boolean result = true;
		try {
			// 发件人
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);

			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);

			// 邮件主题
			message.setSubject(subject,"utf-8");
			//message.setSubject(MimeUtility.encodeText(subject, "big5", "B"));
			
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility
						.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();

			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, sender_username, sender_password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	}

	public static void main(String[] args) {
		JavaEmail se = new JavaEmail("smtp.163.com","15170222742@163.com","chendan123456");
		System.out.println("ss");
		se.doSendHtmlEmail("邮件主题", "邮件内容<a href=\"http://www.baidu.com\">请点击</a>", "843964653@qq.com", null);
	}
}
