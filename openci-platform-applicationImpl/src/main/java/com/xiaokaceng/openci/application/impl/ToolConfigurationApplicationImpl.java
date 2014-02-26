package com.xiaokaceng.openci.application.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.opencis.api.CISClient;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryChannelService;
import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.CasUserConfiguration;
import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.JiraConfiguration;
import com.xiaokaceng.openci.domain.SonarConfiguration;
import com.xiaokaceng.openci.domain.SvnConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.TracConfiguration;
import com.xiaokaceng.openci.factory.CISClientFactory;
import com.xiaokaceng.openci.pojo.GitConfigurationPojo;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.JiraConfigurationPojo;
import com.xiaokaceng.openci.pojo.SonarConfigurationPojo;
import com.xiaokaceng.openci.pojo.SvnConfigurationPojo;
import com.xiaokaceng.openci.pojo.ToolConfigurationPojo;
import com.xiaokaceng.openci.pojo.TracConfigurationPojo;

@Named("toolConfigurationApplication")
@Transactional("transactionManager_opencis")
public class ToolConfigurationApplicationImpl implements ToolConfigurationApplication {

	@Inject
	private QueryChannelService queryChannel;

	public void createConfiguration(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.save();
	}

	public void updateConfiguration(ToolConfiguration toolConfiguration) {
		createConfiguration(toolConfiguration);
	}

	public void setToolUsabled(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.usabled();
	}

	public void setToolUnusabled(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.unusabled();
	}

	public boolean canConnect(ToolConfiguration toolConfiguration) {
		try {
			CISClient cisClient = CISClientFactory.getInstance(toolConfiguration, initToolConfigurationPojos());
			if (cisClient.authenticate()) {
				setToolUsabled(toolConfiguration);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Set<ToolConfigurationPojo> initToolConfigurationPojos() {
		Set<ToolConfigurationPojo> toolConfigurationPojos = new HashSet<ToolConfigurationPojo>();
		toolConfigurationPojos.add(new SvnConfigurationPojo());
		toolConfigurationPojos.add(new GitConfigurationPojo());
		toolConfigurationPojos.add(new JenkinsConfigurationPojo());
		toolConfigurationPojos.add(new SonarConfigurationPojo());
		toolConfigurationPojos.add(new JiraConfigurationPojo());
		toolConfigurationPojos.add(new TracConfigurationPojo());
		return toolConfigurationPojos;
	}

	public List<ToolConfiguration> getAllUsable() {
		return ToolConfiguration.findByUsable();
	}

	public Page<JenkinsConfiguration> pagingQeuryJenkinsConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from JenkinsConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Page<SvnConfiguration> pagingQeurySvnConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from SvnConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Page<GitConfiguration> pagingQeuryGitConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from GitConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Page<SonarConfiguration> pagingQeurySonarConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from SonarConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Page<JiraConfiguration> pagingQeuryJiraConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from JiraConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Page<TracConfiguration> pagingQeuryTracConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from TracConfiguration _toolconfiguration where _toolconfiguration.createDate <= ? and _toolconfiguration.abolishDate > ?");
		List<Object> conditionVals = new ArrayList<Object>();
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public CasUserConfiguration getUniqueInstance() {
		return CasUserConfiguration.getUniqueInstance();
	}

	public void abolishToolConfiguration(long toolConfigurationId) {
		ToolConfiguration toolConfiguration = ToolConfiguration.get(ToolConfiguration.class, toolConfigurationId);
		toolConfiguration.abolish(new Date());
	}

	public void abolishToolConfigurations(Collection<ToolConfiguration> toolConfigurations) {
		Date abolishDate = new Date();
		for (ToolConfiguration each : toolConfigurations) {
			each.abolish(abolishDate);
		}
	}

}
