package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class ToolConfigurationApplicationImplTest extends AbstractIntegrationTest {

	@Inject
	private ToolConfigurationApplication toolConfigurationApplication;

	private ToolConfiguration toolConfiguration;

	@Test
	public void testCreateConfiguration() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		assertEquals(toolConfiguration, ToolConfiguration.get(ToolConfiguration.class, toolConfiguration.getId()));
		toolConfiguration.remove();
	}

	@Test
	public void testUpdateConfiguration() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		toolConfiguration.setName("abc");
		toolConfigurationApplication.updateConfiguration(toolConfiguration);
		assertEquals(1, ToolConfiguration.findAll(ToolConfiguration.class).size());
		assertEquals("abc", ToolConfiguration.get(ToolConfiguration.class, toolConfiguration.getId()).getName());
		toolConfiguration.remove();
	}

	@Test
	public void testSetToolUsabled() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertFalse(toolConfiguration.isUsable());
		toolConfigurationApplication.setToolUsabled(toolConfiguration);
		assertTrue(toolConfiguration.isUsable());
		toolConfiguration.remove();
	}

	@Test
	public void testSetToolUnUsabled() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertFalse(toolConfiguration.isUsable());
		toolConfigurationApplication.setToolUsabled(toolConfiguration);
		assertTrue(toolConfiguration.isUsable());
		toolConfigurationApplication.setToolUnusabled(toolConfiguration);
		assertFalse(toolConfiguration.isUsable());
		toolConfiguration.remove();
	}

	@Test
	public void testCanConnect() {

	}

	@Test
	public void testGetAllUsable() {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		assertEquals(0, ToolConfiguration.findByUsable().size());
		toolConfigurationApplication.setToolUsabled(toolConfiguration);
		assertEquals(1, ToolConfiguration.findByUsable().size());
		toolConfiguration.remove();
	}

	@Test
	public void testPagingQeuryToolConfigurations() {
		toolConfiguration.save();
		ToolConfiguration toolConfiguration2 = new JenkinsConfiguration("test2", null, null, null);
		toolConfiguration2.save();
		assertEquals(2, ToolConfiguration.findAll(ToolConfiguration.class).size());

		List<ToolConfiguration> toolConfigurations = toolConfigurationApplication.pagingQeuryToolConfigurations(1, 2).getResult();
		assertEquals(2, toolConfigurations.size());

		toolConfiguration.remove();
		toolConfiguration2.remove();
	}

	@Before
	public void init() {
		toolConfiguration = new GitConfiguration("test", null, null, null, "token");
	}

	@After
	public void destory() {
		toolConfiguration = null;
	}
}
