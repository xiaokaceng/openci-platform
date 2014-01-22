package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.support.SSHConnectConfig;
import org.openkoala.opencis.svn.SvnCISClient;

import com.xiaokaceng.openci.domain.SvnConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class SvnConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof SvnConfiguration) {
			SSHConnectConfig sshConnectConfig = new SSHConnectConfig(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword(), ((SvnConfiguration) toolConfiguration).getSavePath());
			cisClient = new SvnCISClient(sshConnectConfig);
			isInstance = true;
		}
	}

}
