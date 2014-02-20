package com.xiaokaceng.openci.pojo;


import org.openkoala.opencis.gitlab.GitlabCISClient;
import org.openkoala.opencis.gitlab.GitlabConfiguration;

import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class GitConfigurationPojo extends ToolConfigurationPojo {

	@Override
	public void createCISClient(ToolConfiguration toolConfiguration) {
		if (toolConfiguration instanceof GitConfiguration) {
			GitlabCISClient gitlabCISClient = new GitlabCISClient(createGitlabConfiguration((GitConfiguration) toolConfiguration));
			cisClient = gitlabCISClient;
			isInstance = true;
		}
	}

	private GitlabConfiguration createGitlabConfiguration(GitConfiguration gitConfiguration) {
		GitlabConfiguration gitlabConfiguration = new GitlabConfiguration();
		gitlabConfiguration.setGitHostURL(gitConfiguration.getServiceUrl());
		gitlabConfiguration.setToken(gitConfiguration.getToken());
		gitlabConfiguration.setAdminUsername(gitConfiguration.getUsername());
		gitlabConfiguration.setAdminPassword(gitConfiguration.getPassword());
		gitlabConfiguration.setAdminEmail(gitConfiguration.getEmail());
		return gitlabConfiguration;
	}
	
}
