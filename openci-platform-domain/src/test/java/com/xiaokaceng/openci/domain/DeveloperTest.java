package com.xiaokaceng.openci.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class DeveloperTest extends AbstractIntegrationTest {

	private static final String name = "test";
	private Developer developer;
	private Role role;
	
	@Test
	public void test() {
		System.out.println("==");
	}
	
	@Before
	public void init() {
		developer = new Developer(name, name, name);
		role = new Role(name);
		developer.save();
		role.save();
	}
	
	@After
	public void destory() {
		developer.remove();
		role.remove();
	}
	
	
}
