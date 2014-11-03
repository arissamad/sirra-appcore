package com.sirra.appcore.email;

import javax.mail.*;

import com.sirra.appcore.util.*;
import com.sirra.appcore.util.config.*;
import com.sirra.server.util.*;

public class SMTPAuthenticator extends Authenticator {
	private static SMTPAuthenticator instance;
	
	public static SMTPAuthenticator get() {
		if(instance == null) {
			instance = new SMTPAuthenticator();
		}
		return instance;
	}
	
	protected PasswordAuthentication passwordAuthentication;
	
	private SMTPAuthenticator() {
		String username = Config.getInstance().get("SG_USER");
        String password = Config.getInstance().get("SG_PASS");
        
        passwordAuthentication = new PasswordAuthentication(username, password);
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;
     }
}