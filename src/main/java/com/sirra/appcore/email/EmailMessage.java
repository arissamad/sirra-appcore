package com.sirra.appcore.email;

import java.util.*;

public class EmailMessage {
	protected String htmlEmail;
	protected List<Attachment> attachments;
	
	public EmailMessage(String htmlEmail, Attachment... attachmentFiles) {
		this.htmlEmail = htmlEmail;
		
		attachments = new ArrayList();
		for(int i=0; i<attachmentFiles.length; i++) {
			attachments.add(attachmentFiles[i]);
		}
	}
	
	public void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public String getHtmlEmail() {
		return htmlEmail;
	}
}