package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class ProjectDeveloperTest extends AbstractIntegrationTest {

	private ProjectDeveloper projectDeveloper;
	
	@Before
	public void init() {
		Set<Role> roles = new HashSet<Role>();
		projectDeveloper = new ProjectDeveloper(roles, null, null);
		projectDeveloper.save();
	}
	
	@Test
	public void testAssignRole() {
		Role role = new Role("test");
		role.save();
		projectDeveloper.assignRole(role);
		assertTrue(projectDeveloper.getRoles().contains(role));
	}
	
	@After
	public void destory() {
		projectDeveloper.remove();
	}
}
