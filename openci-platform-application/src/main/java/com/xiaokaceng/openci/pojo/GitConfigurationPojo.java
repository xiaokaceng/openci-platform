package com.xiaokaceng.openci.pojo;

import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class GitConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof GitConfiguration) {
			System.out.println("========Git");
		}
	}

}
