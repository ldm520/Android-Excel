package com.ldm.support.tool;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;



public class SendEmailTool {

	private int port = 25; // smtp协议使用的是25号端口
	private String server; // 发件人邮件服务器
	private String user; // 使用者账号
	private String password; // 使用者密码

	// 构造发送邮件帐户的服务器，端口，帐户，密码
	public SendEmailTool(String server, int port, String user, String passwd) {
		this.port = port;
		this.user = user;
		this.password = passwd;
		this.server = server;
	}

	/** 
	*   email  手机人电子邮箱 
	*   subject 邮件标题 
	*   body 正文内容 
	*   paths  发送的附件路径集合 
	**/
	public void sendEmail(String email, String subject, String body, List<String> paths) {
		Properties props = new Properties();
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.port", String.valueOf(port));
		props.put("mail.smtp.auth", "true");
		Transport transport = null;
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			transport = session.getTransport("smtp");
			transport.connect(server, user, password); // 建立与服务器连接
			msg.setSentDate(new Date());
			InternetAddress fromAddress = null;
			fromAddress = new InternetAddress(user);
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(email);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, "UTF-8"); // 设置邮件标题
			MimeMultipart multi = new MimeMultipart(); // 代表整个邮件邮件
			BodyPart textBodyPart = new MimeBodyPart(); // 设置正文对象
			textBodyPart.setText(body); // 设置正文
			multi.addBodyPart(textBodyPart); // 添加正文到邮件
			for (String path : paths) {
				FileDataSource fds = new FileDataSource(path); // 获取磁盘文件
				BodyPart fileBodyPart = new MimeBodyPart(); // 创建BodyPart
				fileBodyPart.setDataHandler(new DataHandler(fds)); // 将文件信息封装至BodyPart对象
				String fileNameNew = MimeUtility.encodeText(fds.getName(), "utf-8", null); // 设置文件名称显示编码，解决乱码问题
				fileBodyPart.setFileName(fileNameNew); // 设置邮件中显示的附件文件名
				multi.addBodyPart(fileBodyPart); // 将附件添加到邮件中
			}
			msg.setContent(multi); // 将整个邮件添加到message中
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients()); // 发送邮件
			transport.close();
		}
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
