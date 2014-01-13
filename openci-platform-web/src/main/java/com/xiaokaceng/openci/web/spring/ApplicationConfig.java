package com.xiaokaceng.openci.web.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.application.impl.OpenciApplicationImpl;
import com.xiaokaceng.openci.application.impl.ProjectApplicationImpl;

@Configuration
@ImportResource({ "classpath*:applicationContext.xml"})
@EnableTransactionManagement
public class ApplicationConfig {

	@Bean
	public ProjectApplication projectApplication() {
		return new ProjectApplicationImpl();
	}
	
	@Bean
	public OpenciApplication openciApplication() {
		return new OpenciApplicationImpl();
	}
	
}
