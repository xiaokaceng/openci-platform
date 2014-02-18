package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.support.SvnConfig;
import org.openkoala.opencis.svn.SvnCISClient;

import com.xiaokaceng.openci.domain.SvnConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class SvnConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof SvnConfiguration) {
			SvnConfig svnConfig = new SvnConfig(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword(),
					((SvnConfiguration) toolConfiguration).getSavePath(), getSvnAddress((SvnConfiguration) toolConfiguration), null, null);
			cisClient = new SvnCISClient(svnConfig);
			isInstance = true;
		}
	}

	private String getSvnAddress(SvnConfiguration toolConfiguration) {
		return "http://" + toolConfiguration.getServiceUrl() + toolConfiguration.getRequestRootAddress();
	}
}
