package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.Developer;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class ToolConfigurationApplicationImplTest extends AbstractIntegrationTest {

	@Inject
	private ToolConfigurationApplication toolConfigurationApplication;
	
	private ToolConfiguration toolConfiguration;
	
	@Test
	public void testCreateConfiguration() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		toolConfiguration.remove();
	}
	
	@Test
	public void testUpdateConfiguration() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		toolConfiguration.setName("abc");
		toolConfigurationApplication.updateConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		toolConfiguration.remove();
	}
	
	@Test
	public void testSetToolUsabled() {
		
	}
	
	@Before
	public void init() {
		toolConfiguration = new ToolConfiguration("test", null, null, null, null);
	}
	
	@After
	public void destory() {
		toolConfiguration = null;
	}
}
