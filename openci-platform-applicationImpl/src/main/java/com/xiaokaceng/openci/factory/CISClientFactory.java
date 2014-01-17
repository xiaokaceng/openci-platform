package com.xiaokaceng.openci.factory;

import org.openkoala.opencis.api.AuthenticationStrategy;
import org.openkoala.opencis.api.CISClient;
import org.openkoala.opencis.git.impl.GitlabCISClient;
import org.openkoala.opencis.git.impl.GitlabConfiguration;
import org.openkoala.opencis.jenkins.JenkinsCISClient;
import org.openkoala.opencis.jenkins.authentication.JenkinsOwnAuthentication;
import org.openkoala.opencis.pojo.SonarServerConfiguration;
import org.openkoala.opencis.sonar.SonarCISClient;
import org.openkoala.opencis.svn.SvnCISClient;
import org.openkoala.opencis.trac.TracCISClient;

import com.xiaokaceng.openci.ToolTypeNotExistException;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.ToolType;

public class CISClientFactory {

	public static CISClient getInstance(ToolConfiguration toolConfiguration) {
		ToolType toolType = toolConfiguration.getToolType();
		if (toolType.equals(ToolType.GIT)) {
			return createGitCISClient(toolConfiguration);
		}
		if (toolType.equals(ToolType.SVN)) {
			return createSvnCISClient(toolConfiguration);
		}
		if (toolType.equals(ToolType.JENKINS)) {
			return createJenkinsCISClient(toolConfiguration);
		}
		if (toolType.equals(ToolType.SONAR)) {
			return createSonarCISClient(toolConfiguration);
		}
		if (toolType.equals(ToolType.JIRA)) {
			return createJiraCISClient(toolConfiguration);
		}
		if (toolType.equals(ToolType.TRAC)) {
			return createTracCISClient(toolConfiguration);
		}
		throw new ToolTypeNotExistException();
	}

	private static CISClient createTracCISClient(ToolConfiguration toolConfiguration) {
		// TODO 需要添加tracConfiguration
		//return new TracCISClient(null);
		return null;
	}

	private static CISClient createJiraCISClient(ToolConfiguration toolConfiguration) {
		// GiraConfiguration giraConfiguration = new GiraConfiguration(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		return null;
	}

	private static CISClient createSonarCISClient(ToolConfiguration toolConfiguration) {
		SonarServerConfiguration sonarServerConfiguration = new SonarServerConfiguration(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		return new SonarCISClient(sonarServerConfiguration);
	}

	private static CISClient createJenkinsCISClient(ToolConfiguration toolConfiguration) {
		AuthenticationStrategy authentication = new JenkinsOwnAuthentication(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		return new JenkinsCISClient(toolConfiguration.getServiceUrl(), authentication);
	}

	private static CISClient createSvnCISClient(ToolConfiguration toolConfiguration) {
		// TODO 需修改configuration
		return null;
	}

	private static CISClient createGitCISClient(ToolConfiguration toolConfiguration) {
		// TODO 需验证git
		GitlabConfiguration gitLabConfiguration = new GitlabConfiguration();
		return new GitlabCISClient(gitLabConfiguration);
	}
	
	
	
}
