package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.application.EmailConfigurationApplication;
import com.xiaokaceng.openci.domain.EmailConfiguration;

public class EmailConfigurationImplTest extends AbstractIntegrationTest {

	private EmailConfiguration emailConfiguration;
	
	@Inject
	private EmailConfigurationApplication emailConfigurationApplication;

	@Test
	public void testCreateEmailConfiguration() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		assertEquals(emailConfiguration, EmailConfiguration.get(EmailConfiguration.class, emailConfiguration.getId()));
		emailConfiguration.remove();
	}
	
	@Test
	public void testUpdateEmailConfiguration() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		emailConfiguration.setDefault(true);
		emailConfiguration.setUsername("openci2@sina.com");
		emailConfigurationApplication.updateEmailConfiguration(emailConfiguration);
		assertFalse(emailConfiguration.isDefault());
		emailConfiguration.remove();
	}
	
	@Test
	public void testRemoveEmailConfiguration() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		emailConfigurationApplication.removeEmailConfiguration(emailConfiguration.getId());
		assertNull(EmailConfiguration.get(EmailConfiguration.class, emailConfiguration.getId()));
	}
	
	@Test
	@Ignore
	public void testSetUsable() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		assertTrue(emailConfigurationApplication.setUsable(emailConfiguration.getId()));
		emailConfiguration.remove();
	}
	
	@Test
	public void testSetDefault() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		emailConfigurationApplication.setDefault(emailConfiguration.getId());
		assertTrue(emailConfiguration.isDefault());
		emailConfiguration.remove();
	}
	
	
}
