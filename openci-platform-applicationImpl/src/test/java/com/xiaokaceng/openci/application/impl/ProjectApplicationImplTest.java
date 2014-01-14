package com.xiaokaceng.openci.application.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.domain.Developer;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.domain.Role;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolType;

@Ignore
public class ProjectApplicationImplTest extends AbstractIntegrationTest {

	private static final String NAME = "test";
	
	private Developer developer;
	private Role role;
	
	@Test
	public void testCreateProject() {
		Project project = getProjectInstance();
		projectApplication.createProject(project);
		assertEquals(1, project.getDevelopers().size());
		assertEquals(2, project.getTools().size());
		project.remove();
	}
	
	@Test(expected = EntityNullException.class)
	public void testCreateProjectIfNull() {
		projectApplication.createProject(null);
	}
	
	@Test
	public void testAddIntegrationTool() {
		Project project = getProjectInstance();
		projectApplication.createProject(project);
		projectApplication.addIntegrationTool(project, new Tool(null, project));
		assertEquals(3, project.getTools().size());
		project.remove();
	}
	
	@Test(expected = EntityNullException.class)
	public void testAddIntegrationToolIfNull() {
		Project project = getProjectInstance();
		projectApplication.addIntegrationTool(project, null);
	}
	
	private Project getProjectInstance() {
		Project project = new Project(NAME);
		project.setDevelopers(createProjectDeveloper(project));
		project.setTools(createTool(project));
		return project;
	}

	private Set<Tool> createTool(Project project) {
		Set<Tool> tools = new HashSet<Tool>();
		tools.add(new Tool(null, project));
		tools.add(new Tool(null, project));
		return tools;
	}

	private Set<ProjectDeveloper> createProjectDeveloper(Project project) {
		Set<ProjectDeveloper> projectDevelopers = new HashSet<ProjectDeveloper>();
		projectDevelopers.add(new ProjectDeveloper(createRoles(), developer, project));
		return projectDevelopers;
	}

	private Set<Role> createRoles() {
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		return roles;
	}

	@Before
	public void init() {
		developer = new Developer(NAME, NAME, NAME);
		developer.save();
		role = new Role(NAME, NAME);
		role.save();
	}
	
	@After
	public void destory() {
		List<Developer> developers = Developer.findAll(Developer.class);
		if (developers.size() > 0) {
			for (Developer each : developers) {
				each.remove();
			}
		}
	}
	
}
