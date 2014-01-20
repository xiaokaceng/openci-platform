package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.support.SSHConnectConfig;
import org.openkoala.opencis.trac.TracCISClient;

import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.TracConfiguration;

public class TracConfigurationPojo extends ToolConfigurationPojo {

	@Override
	protected void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof TracConfiguration) {
			SSHConnectConfig sshConnectConfig = new SSHConnectConfig(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
			cisClient = new TracCISClient(sshConnectConfig);
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
