package com.xiaokaceng.openci;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.spring.factory.SpringInstanceProvider;

@SpringApplicationContext("/applicationContext.xml")
public abstract class AbstractIntegrationTest extends UnitilsJUnit4 {

	protected static final String TEST_USERNAME = "test";
	
	@SpringApplicationContext
	private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception {
		SpringInstanceProvider provider = new SpringInstanceProvider(applicationContext);
		InstanceFactory.setInstanceProvider(provider);
	}
	
	@After
	public void tearDown() throws Exception {
		InstanceFactory.setInstanceProvider(null);
	}

}
