package com.xiaokaceng.openci.pojo;

import com.xiaokaceng.openci.domain.JiraConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class JiraConfigurationPojo extends ToolConfigurationPojo {

	@Override
	protected void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof JiraConfiguration) {
			
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
