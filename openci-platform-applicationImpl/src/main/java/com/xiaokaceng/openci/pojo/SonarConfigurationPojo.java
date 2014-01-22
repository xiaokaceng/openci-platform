package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.sonar.SonarCISClient;
import org.openkoala.opencis.sonar.SonarConnectConfig;

import com.xiaokaceng.openci.domain.SonarConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class SonarConfigurationPojo extends ToolConfigurationPojo {

	@Override
	protected void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof SonarConfiguration) {
			SonarConnectConfig sonarConnectConfig = new SonarConnectConfig(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
			cisClient = new SonarCISClient(sonarConnectConfig);
			isInstance = true;
		}
	}

	@Override
	protected void createAuthenticationByDB(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createAuthenticationByCAS(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		// TODO Auto-generated method stub

	}

}
