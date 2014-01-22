package com.xiaokaceng.openci.application.impl;

import java.util.List;

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
			CISClient cisClient = CISClientFactory.getInstance(toolConfiguration);
			if (cisClient.authenticate()) {
				setToolUsabled(toolConfiguration);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ToolConfiguration> getAllUsable() {
		return ToolConfiguration.findByUsable();
	}

	public Page<JenkinsConfiguration> pagingQeuryJenkinsConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from JenkinsConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public Page<SvnConfiguration> pagingQeurySvnConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from SvnConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public Page<GitConfiguration> pagingQeuryGitConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from GitConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public Page<SonarConfiguration> pagingQeurySonarConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from SonarConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public Page<JiraConfiguration> pagingQeuryJiraConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from JiraConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public Page<TracConfiguration> pagingQeuryTracConfigurations(int currentPage, int pagesize) {
		StringBuilder jpql = new StringBuilder("select _toolconfiguration from TracConfiguration _toolconfiguration");
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), null, currentPage, pagesize);
	}

	public CasUserConfiguration getUniqueInstance() {
		return CasUserConfiguration.getUniqueInstance();
	}

}
