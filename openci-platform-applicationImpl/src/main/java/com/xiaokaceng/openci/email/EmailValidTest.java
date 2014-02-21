package com.xiaokaceng.openci.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.xiaokaceng.openci.domain.EmailConfiguration;

public class EmailValidTest {

	private static final String CHAREST = "UTF-8";

	private EmailConfiguration emailConfiguration;

	public EmailValidTest(EmailConfiguration emailConfiguration) {
		this.emailConfiguration = emailConfiguration;
	}

	public boolean valid() {
		try {
			Email email = new SimpleEmail();
			email.setHostName(emailConfiguration.getSmtpAddress());
			email.setSmtpPort(emailConfiguration.getSmtpPort());
			email.setAuthenticator(new DefaultAuthenticator(emailConfiguration.getUsername(), emailConfiguration.getPassword()));
			email.setSSLOnConnect(false);
			email.setFrom(emailConfiguration.getUsername(), emailConfiguration.getName());
			email.addTo("luomin@foreveross.com");
			email.setCharset(CHAREST);
			email.setSubject("OPENCI-PLATFORM邮箱测试");
			email.setMsg("来自OPENCI-PLATFORM,This is a test mail....");
			email.send();
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
	}

}
