package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dayatang.utils.DateUtils;
import com.xiaokaceng.openci.AbstractIntegrationTest;

public class TimeIntervalEntityTest extends AbstractIntegrationTest {

	private static final String STR = "test";
	private Role role;
	
	@Before
	public void init() {
		role = new Role(STR, STR);
		role.save();
	}
	
	@Test
	public void testIsAbolished() {
		assertTrue(role.isAbolished(DateUtils.MAX_DATE));
		assertFalse(role.isAbolished(new Date()));
		role.setAbolishDate(null);
		assertFalse(role.isAbolished(new Date()));
	}
	
	@Test
	public void testAbolish() {
		role.abolish(DateUtils.MAX_DATE);
		assertNotNull(Role.get(Role.class, role.getId()));
		role.abolish(new Date());
		assertNull(Role.get(Role.class, role.getId()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAbolishIfNull() {
		role.abolish(null);
	}
	
	@Test
	public void testGet() {
		assertNotNull(Role.get(Role.class, role.getId()));
		assertEquals(role, Role.get(Role.class, role.getId()));
		role.abolish(new Date());
		assertNull(Role.get(Role.class, role.getId()));
	}
	
	@Test
	public void testGetByQueryDate() {
		assertNotNull(Role.get(Role.class, role.getId(), new Date()));
	}
	
	@Test
	public void testFindAllByQueryDate() {
		assertNotNull(Role.findAll(Role.class, new Date()));
	}
	
	@Test
	public void testFindAll() {
		Role role2 = new Role("test1", STR);
		role2.save();
		
		List<Role> roles = Role.findAll(Role.class);
		assertTrue(roles.contains(role));
		assertTrue(roles.contains(role2));
		assertEquals(2, roles.size());
		
		role.abolish(new Date());
		roles = Role.findAll(Role.class);
		assertFalse(roles.contains(role));
		assertTrue(roles.contains(role2));
		assertEquals(1, roles.size());
	}
	
	@After
	public void destory() {
		role.abolish(new Date());
	}
}
