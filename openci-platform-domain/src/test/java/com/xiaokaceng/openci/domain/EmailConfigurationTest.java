package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class EmailConfigurationTest extends AbstractIntegrationTest {

	private static final String STR = "test";
	private EmailConfiguration emailConfiguration;
	
	@Before
	public void init() {
		emailConfiguration = new EmailConfiguration(STR, STR, STR, 25, STR);
		emailConfiguration.save();
	}
	
	@Test
	public void testSetDefault() {
		emailConfiguration.setDefault();
		assertTrue(emailConfiguration.isDefault());
		
		EmailConfiguration emailConfiguration2 = new EmailConfiguration(STR, STR, STR, 25, STR);
		emailConfiguration2.save();
		emailConfiguration2.setDefault();
		
		assertTrue(emailConfiguration2.isDefault());
		assertFalse(emailConfiguration.isDefault());
		
		emailConfiguration2.remove();
	}
	
	@Test
	public void testGetDefault() {
		emailConfiguration.setDefault();
		assertTrue(emailConfiguration.isDefault());
		assertEquals(emailConfiguration, EmailConfiguration.getDefault());
	}
	
	@Test
	public void testGetDefaultIfNotSettingsDefault() {
		assertNull(EmailConfiguration.getDefault());
	}
	
	@Test
	public void testSetUsable() {
		assertFalse(emailConfiguration.isUsable());
		emailConfiguration.setUsable();
		assertTrue(emailConfiguration.isUsable());
	}
	
	@After
	public void destory() {
		emailConfiguration.remove();
	}
}
