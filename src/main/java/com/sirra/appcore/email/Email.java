package com.sirra.appcore.email;

import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.sirra.appcore.util.*;

/**
 * Emails are ALWAYS sent asynchronously.
 * 
 * @author aris
 *
 */
public class Email implements Runnable {
	
	public static void send(String subject, EmailMessage emailMessage, EmailPerson from, List<EmailPerson> targetList) {
		new Email(subject, emailMessage, from, targetList);
	}
	
	public static void send(String subject, EmailMessage emailMessage, EmailPerson from, EmailPerson... targets) {
		List<EmailPerson> targetList = new ArrayList();
		for(int i=0; i<targets.length; i++) {
			targetList.add(targets[i]);
		}
		new Email(subject, emailMessage, from, targetList);
	}

	protected String subject;
	protected EmailMessage emailMessage;
	protected EmailPerson from;
	protected List<EmailPerson> targets;
	
	protected int resendCounter = 3;
	
	public Email(String subject, EmailMessage emailMessage, EmailPerson from, List<EmailPerson> targets) {
		this.subject = subject;
		this.emailMessage = emailMessage;
		this.from = from;
		this.targets = targets;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		try {
			Properties props = new Properties();
			
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.host", "smtp.sendgrid.net");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");
			
	        Session mailSession = Session.getDefaultInstance(props, SMTPAuthenticator.get());
	
	        // mailSession.setDebug(true);
	
	        MimeMessage message = new MimeMessage(mailSession);
	
	        Multipart multipart = new MimeMultipart("alternative");
	
	        BodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(emailMessage.getHtmlEmail(), "text/html");
	
	        multipart.addBodyPart(htmlPart);
	        
	        for(Attachment attachment: emailMessage.getAttachments()) {
				
				MimeBodyPart fileBodyPart = new MimeBodyPart();

				// attach the file to the message
				if(attachment instanceof UrlAttachment) {
					UrlAttachment ua = (UrlAttachment) attachment;
							
					InputStreamDataSource isds = new InputStreamDataSource(ua.getFileName(), ua.getContentType(), ua.getInputStream());
					fileBodyPart.setDataHandler(new DataHandler(isds));
					fileBodyPart.setFileName(ua.getFileName());
				}
				
				/* Not written yet 
				else if(attachment instanceof FileAttachment) {
					FileDataSource fds = new FileDataSource(attachment.getFile());
					fileBodyPart.setDataHandler(new DataHandler(fds));
					fileBodyPart.setFileName(attachment.getFileName());	
				}*/
				
				multipart.addBodyPart(fileBodyPart);
			}
	
	        message.setContent(multipart);
	        message.setFrom(new InternetAddress(from.getEmail(), from.getName()));
	        message.setSubject(subject);
	        
	        for(int i=0; i<targets.size(); i++) {
	        	EmailPerson emailPerson = targets.get(i);
	        	message.addRecipient(emailPerson.getRecipientType(), new InternetAddress(emailPerson.getEmail(), emailPerson.getName()));
	        }
	
	        Transport.send(message);
	        System.out.println("Sent email");
	        
		} catch(Exception e) {
			resendCounter--;
			
			if(resendCounter == 0) {
				throw new RuntimeException(e);
			} else {
				ExceptionUtil.print(e);
				System.out.println("We had a problem sending email. Sleeping for 15 seconds and trying again. resendCounter: " + resendCounter);
				try {
					Thread.currentThread().sleep(1000 * 15);
				} catch (InterruptedException e1) {
				}
				
				run();
			}
		}
	}
}