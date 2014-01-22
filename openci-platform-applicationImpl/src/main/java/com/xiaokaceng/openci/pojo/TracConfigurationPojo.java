package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.support.SSHConnectConfig;
import org.openkoala.opencis.trac.TracCISClient;

import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.TracConfiguration;

public class TracConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof TracConfiguration) {
			SSHConnectConfig sshConnectConfig = new SSHConnectConfig(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword(), ((TracConfiguration) toolConfiguration).getSavePath());
			cisClient = new TracCISClient(sshConnectConfig);
			isInstance = true;
		}
	}

}
