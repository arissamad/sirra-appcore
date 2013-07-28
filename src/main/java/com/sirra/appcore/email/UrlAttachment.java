package com.sirra.appcore.email;

import java.io.*;
import java.net.*;

/**
 * For sending an email attachment, content coming from a URL.
 * 
 * @author aris
 */
public class UrlAttachment extends Attachment {
	protected String url;
	
	public UrlAttachment(String url, String name, String contentType) {
		this.contentType = contentType;
		this.fileName = name;
		this.url = url;
	}
	
	public InputStream getInputStream() {
		try {
	        URL urlInstance = new URL(url);
	        return urlInstance.openStream();
	        
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}