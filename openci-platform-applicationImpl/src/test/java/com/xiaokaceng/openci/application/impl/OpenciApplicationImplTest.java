package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.domain.Developer;

public class OpenciApplicationImplTest extends AbstractIntegrationTest {

	private static final String NAME = "test";
	private Developer developer;
	
	@Test
	public void testSave() {
		openciApplication.saveEntity(developer);
		assertNotNull(developer.getId());
		assertEquals(1, Developer.findAll(Developer.class).size());
		developer.remove();
	}
	
	@Test(expected = EntityNullException.class)
	public void testSaveIfEntityIsNull() {
		openciApplication.saveEntity(null);
	}
	
	@Test
	public void testRemove() {
		openciApplication.saveEntity(developer);
		developer.remove();
		assertEquals(0, Developer.findAll(Developer.class).size());
	}
	
	@Test(expected = EntityNullException.class)
	public void testRemoveIfEntityIsNull() {
		openciApplication.removeEntity(null);
	}
	
	@Before
	public void init() {
		developer = new Developer(NAME, NAME, NAME);
	}
	
	@After
	public void destory() {
		developer = null;
	}
	
}
