package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.jira.JiraCISClient;

import com.xiaokaceng.openci.domain.JiraConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class JiraConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof JiraConfiguration) {
			JiraCISClient jiraCISClient = new JiraCISClient(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
			cisClient = jiraCISClient;
			isInstance = true;
		}
	}

}
