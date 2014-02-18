package com.xiaokaceng.openci.application.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.EmailConfigurationApplication;
import com.xiaokaceng.openci.domain.EmailConfiguration;
import com.xiaokaceng.openci.email.EmailSendTest;

@Named("emailConfigurationApplication")
@Transactional("transactionManager_opencis")
public class EmailConfigurationApplicationImpl implements EmailConfigurationApplication {

	public void createEmailConfiguration(EmailConfiguration emailConfiguration) {
		if (emailConfiguration == null) {
			throw new EntityNullException();
		}
		emailConfiguration.save();
	}

	public void updateEmailConfiguration(EmailConfiguration emailConfiguration) {
		if (emailConfiguration == null) {
			throw new EntityNullException();
		}
		emailConfiguration.setUsable(false);
		emailConfiguration.setDefault(false);
		emailConfiguration.save();
	}

	public void removeEmailConfiguration(long emailConfigurationId) {
		EmailConfiguration emailConfiguration = EmailConfiguration.get(EmailConfiguration.class, emailConfigurationId);
		if (emailConfiguration != null) {
			emailConfiguration.remove();
		}
	}

	public boolean setUsable(long emailConfigurationId) {
		EmailConfiguration emailConfiguration = EmailConfiguration.get(EmailConfiguration.class, emailConfigurationId);
		if (emailConfiguration != null) {
			EmailSendTest emailSendTest = new EmailSendTest(emailConfiguration);
			if (emailSendTest.send()) {
				emailConfiguration.setUsable();
				return true;
			}
		}
		return false;
	}

	public void setDefault(long emailConfigurationId) {
		EmailConfiguration emailConfiguration = EmailConfiguration.get(EmailConfiguration.class, emailConfigurationId);
		if (emailConfiguration != null) {
			emailConfiguration.setDefault();
		}
	}

	public List<EmailConfiguration> findAll() {
		return EmailConfiguration.findAll(EmailConfiguration.class);
	}

	public EmailConfiguration getDefault() {
		return EmailConfiguration.getDefault();
	}

}
