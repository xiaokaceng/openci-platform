package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

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
	public void testSetUsable() {
		//  因为需要发送邮件
//		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
//		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
//		assertTrue(emailConfigurationApplication.setUsable(emailConfiguration.getId()));
//		emailConfiguration.remove();
	}
	
	@Test
	public void testSetDefault() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		emailConfigurationApplication.setDefault(emailConfiguration.getId());
		assertTrue(emailConfiguration.isDefault());
		emailConfiguration.remove();
	}
	
	@Test
	public void testFindAll() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		EmailConfiguration emailConfiguration2 = new EmailConfiguration("openci@sina.com2", "openci2", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration2);
		List<EmailConfiguration> emailConfigurations = emailConfigurationApplication.findAll();
		assertTrue(emailConfigurations.contains(emailConfiguration));
		assertTrue(emailConfigurations.contains(emailConfiguration2));
		emailConfiguration.remove();
		emailConfiguration2.remove();
	}
	
	@Test
	public void testGetDefault() {
		emailConfiguration = new EmailConfiguration("openci@sina.com", "openci", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		emailConfigurationApplication.setDefault(emailConfiguration.getId());
		EmailConfiguration emailConfiguration2 = new EmailConfiguration("openci@sina.com2", "openci2", "smtp.sina.com", 25, "开放持续构建平台");
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration2);
		emailConfigurationApplication.setDefault(emailConfiguration2.getId());
		assertEquals(emailConfiguration2, emailConfigurationApplication.getDefault());
		assertFalse(emailConfiguration.isDefault());
		emailConfiguration.remove();
		emailConfiguration2.remove();
	}
	
}
