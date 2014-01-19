package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.jenkins.JenkinsCISClient;
import org.openkoala.opencis.jenkins.authentication.CasAuthentication;
import org.openkoala.opencis.jenkins.authentication.JenkinsOwnAuthentication;

import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class JenkinsConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof JenkinsConfiguration) {
			cisClient = new JenkinsCISClient(toolConfiguration.getServiceUrl(), authentication);
			isInstance = true;
		}
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
