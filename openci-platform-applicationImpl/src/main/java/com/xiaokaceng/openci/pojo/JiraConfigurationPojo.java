package com.xiaokaceng.openci.pojo;

import com.xiaokaceng.openci.domain.JiraConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class JiraConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof JiraConfiguration) {
			
		}
	}

}
