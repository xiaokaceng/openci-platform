package com.xiaokaceng.openci.application.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.application.DeveloperApplication;
import com.xiaokaceng.openci.domain.Developer;

public class DeveloperApplicationImplTest extends AbstractIntegrationTest {

	@Inject
	private DeveloperApplication developerApplication;
	
	@Test
	public void testCreateDeveloper() {
		Developer developer = new Developer("zhuyuanbiao", "朱远标", "zhuyuanbiao@foreveross.com");
		developerApplication.createDeveloper(developer);
		assertEquals(developer, Developer.get(Developer.class, developer.getId()));
		Developer.findAll(Developer.class);
	}

	@Test
	public void testUpdateDeveloper() {
		Developer developer = new Developer("zhuyuanbiao", "朱远标", "zhuyuanbiao@foreveross.com");
		developer.save();
		assertEquals("朱远标", Developer.get(Developer.class, developer.getId()).getName());
		
		developer.setName("猪肉标");
		developerApplication.updateDeveloperInfo(developer);
		assertEquals("猪肉标", Developer.get(Developer.class, developer.getId()).getName());
	}
	
	@Test
	public void testPagingQueryDeveloper() {
		Developer developer1 = new Developer("zhuyuanbiao1", "朱远标1", "zhuyuanbiao1@foreveross.com");
		Developer developer2 = new Developer("zhuyuanbiao2", "朱远标2", "zhuyuanbiao2@foreveross.com");
		developer1.save();
		developer2.save();
		
		List<Developer> developersInRepository = new ArrayList<Developer>();
		developersInRepository.add(developer1);
		developersInRepository.add(developer2);
		
		List<Developer> developersFromPagingQuery = developerApplication.pagingQeuryDevelopers(new Developer(), 1, 2).getResult();
		assertEquals(2, developersFromPagingQuery.size());
//		assertEquals(developersInRepository, developersFromPagingQuery);
	}
	
	@Test
	public void testAbolishDeveloper() {
		Developer developer = new Developer("zhuyuanbiao", "朱远标", "zhuyuanbiao@foreveross.com");
		developer.save();
		assertEquals(developer, Developer.get(Developer.class, developer.getId()));
		
		developerApplication.abolishDeveloper(developer);
		assertNull(Developer.get(Developer.class, developer.getId()));
	}
	
	@Test
	public void testAbolishDevelopers() {
		Developer developer1 = new Developer("zhuyuanbiao1", "朱远标1", "zhuyuanbiao1@foreveross.com");
		Developer developer2 = new Developer("zhuyuanbiao2", "朱远标2", "zhuyuanbiao2@foreveross.com");
		developer1.save();
		developer2.save();
		assertTrue(Developer.findAll(Developer.class).contains(developer1));
		assertTrue(Developer.findAll(Developer.class).contains(developer2));

		List<Developer> developers = new ArrayList<Developer>();
		developers.add(developer1);
		developers.add(developer2);

		
		developerApplication.abolishDevelopers(developers);
		assertNull(Developer.get(Developer.class, developer1.getId()));
		assertNull(Developer.get(Developer.class, developer2.getId()));
	}
	
}
