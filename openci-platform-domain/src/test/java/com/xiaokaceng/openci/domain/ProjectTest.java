package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.EntityNullException;

public class ProjectTest extends AbstractIntegrationTest {

	private static final String NAME = "TEST";
	
	private Project project;
	
	@Before
	public void init() {
		project = new Project(NAME);
		project.setProjectStatus(ProjectStatus.INTEGRATION_TOOL);
		project.save();
	}
	
	@Test
	public void testAddTool() {
		Tool tool = new Tool(null, project);
		project.addTool(tool);
		assertEquals(1, project.getTools().size());
	}
	
	@Test(expected = EntityNullException.class)
	public void testAddToolIfNull() {
		project.addTool(null);
	}
	
	@Test
	public void testUpdateProjectStatus() {
		Tool tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.SUCCESS);
		project.addTool(tool);
		tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.ONGOING);
		project.addTool(tool);
		
		assertEquals(2, project.getTools().size());
		project.updateProjectStatus();
		
		assertEquals(ProjectStatus.INTEGRATION_TOOL, project.getProjectStatus());
	}
	
	@Test
	public void testUpdateProjectStatus2() {
		Tool tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.SUCCESS);
		project.addTool(tool);
		tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.FAILURE);
		project.addTool(tool);
		
		assertEquals(2, project.getTools().size());
		project.updateProjectStatus();
		
		assertEquals(ProjectStatus.INTEGRATION_TOOL_FAILURE, project.getProjectStatus());
	}

	@Test
	public void testUpdateProjectStatus3() {
		Tool tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.SUCCESS);
		project.addTool(tool);
		tool = new Tool(null, project);
		tool.setStatus(ToolIntegrationStatus.SUCCESS);
		project.addTool(tool);
		
		assertEquals(2, project.getTools().size());
		project.updateProjectStatus();
		
		assertEquals(ProjectStatus.SUCCESS, project.getProjectStatus());
	}

	@Test
	public void testIsExixtByName() {
		assertTrue(Project.isExixtByName(NAME));
		assertFalse(Project.isExixtByName("abc"));
		project.setName("abc");
		project.save();
		assertTrue(Project.isExixtByName("abc"));
		assertFalse(Project.isExixtByName(NAME));
	}
	
	@After
	public void destory() {
		project.abolish(new Date());
	}
	
}
