package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.jenkins.JenkinsCISClient;
import org.openkoala.opencis.jenkins.authentication.CasAuthentication;
import org.openkoala.opencis.jenkins.authentication.JenkinsOwnAuthentication;
import org.openkoala.opencis.jenkins.configureApi.ScmConfigStrategy;
import org.openkoala.opencis.jenkins.configureImpl.scm.GitConfig;
import org.openkoala.opencis.jenkins.configureImpl.scm.SvnConfig;

import com.xiaokaceng.openci.application.dto.ScmConfig;
import com.xiaokaceng.openci.application.dto.ScmType;
import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class JenkinsConfigurationPojo extends ToolConfigurationPojo {

	private ScmConfig scmConfig;

	public JenkinsConfigurationPojo(ScmConfig scmConfig) {
		this.scmConfig = scmConfig;
	}

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof JenkinsConfiguration) {
			JenkinsCISClient jenkinsCISClient = new JenkinsCISClient(toolConfiguration.getServiceUrl(), authentication);
			jenkinsCISClient.setScmConfig(createScmConfigStrategy());
			System.out.println(scmConfig.getScmType());
			cisClient = jenkinsCISClient;
			isInstance = true;
		}
	}

	@SuppressWarnings("rawtypes")
	private ScmConfigStrategy createScmConfigStrategy() {
		if (scmConfig == null) {
			return null;
		}
		if (scmConfig.getScmType().equals(ScmType.SVN)) {
			return new SvnConfig(scmConfig.getRepositoryUrl(), scmConfig.getUsername(), scmConfig.getPassword());
		}
		if (scmConfig.getScmType().equals(ScmType.GIT)) {
			return new GitConfig(scmConfig.getRepositoryUrl());
		}
		return null;
	}

	@Override
	public void createAuthenticationByDB(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (!isIntegrationCas) {
			authentication = new JenkinsOwnAuthentication(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		}
	}

	@Override
	public void createAuthenticationByCAS(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (isIntegrationCas) {
			authentication = new CasAuthentication(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		}
	}
}
