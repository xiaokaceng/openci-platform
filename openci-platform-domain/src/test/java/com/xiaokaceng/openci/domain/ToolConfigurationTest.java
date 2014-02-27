package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class ToolConfigurationTest extends AbstractIntegrationTest {

	private static final String STR = "test";
	private GitConfiguration gitConfiguration;
	
	@Before
	public void init() {
		gitConfiguration = new GitConfiguration(STR, STR, STR, STR, STR, STR);
		gitConfiguration.save();
	}
	
	@Test
	public void testUsabled() {
		List<ToolConfiguration> toolConfigurations = ToolConfiguration.findByUsable();
		assertTrue(toolConfigurations.isEmpty());
		gitConfiguration.usabled();
		toolConfigurations = ToolConfiguration.findByUsable();
		assertEquals(1, toolConfigurations.size());
	}
	
	@Test
	public void testUnusabled() {
		gitConfiguration.usabled();
		List<ToolConfiguration> toolConfigurations = ToolConfiguration.findByUsable();
		assertEquals(1, toolConfigurations.size());
		gitConfiguration.unusabled();
		toolConfigurations = ToolConfiguration.findByUsable();
		assertTrue(toolConfigurations.isEmpty());
	}
	
	@Test
	public void testEndsWith() {
		assertEquals("abc/", gitConfiguration.endsWith("abc"));
		assertEquals("abc/", gitConfiguration.endsWith("abc/"));
	}
	
	@Test
	public void testGetRequestAddress() {
		assertEquals("test/test/test", gitConfiguration.getRequestAddress(STR));
	}
	
	@After
	public void destory() {
		gitConfiguration.abolish(new Date());
	}
}
