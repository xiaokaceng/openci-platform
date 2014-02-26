package com.xiaokaceng.openci.domain;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;

public class DeveloperTest extends AbstractIntegrationTest {

	private static final String STR = "test";
	private Developer developer;
	
	@Before
	public void init() {
		developer = new Developer(STR, STR, STR, STR);
		developer.save();
	}
	
	@Test
	public void testCheckDeveloperIdIsExist() {
		assertTrue(Developer.checkDeveloperIdIsExist(STR));
		assertFalse(Developer.checkDeveloperIdIsExist("abc"));
	}
	
	@After
	public void destory() {
		developer.abolish(new Date());
	}
}
