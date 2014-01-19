package com.xiaokaceng.openci.pojo;

import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class GitConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		if (toolConfiguration instanceof GitConfiguration) {
			System.out.println("========Git");
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
