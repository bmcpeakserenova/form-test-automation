/**
 * Class contains the logic to compose and send mail.
 *   
 * @author Shrikant Jaiswal
 **/

package com.serenova.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	public static String emailResultMEssage = "TESTNAME --- EXPECTEDRESULTURL --- RESULTURL --- STATUS\n";

	public static void send(/*
							 * String from,String password,String to,String
							 * sub,String msg
							 */) {

		String from = Utils.checkConfigFileParams("email.config", "EmailFrom");
		String to = Utils.checkConfigFileParams("email.config", "EmailTO");
		String password = Utils.checkConfigFileParams("email.config", "Password");

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Serenova Automation Results");
			// message.setText(msg);
			System.out.println("sending msg :" + emailResultMEssage);
			message.setText(emailResultMEssage);
			// send message
			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) {
		Mailer.send();
	}
}
/*
 * public class Test{ public static void main(String[] args) {
 * //from,password,to,subject,message
 * Mailer.send("shrikant.gen@gmail.com","shrikantgen",
 * "shrikant86jaiswal@gmail.com","hello javatpoint","How r u?"); //change from,
 * password and to } }
 */