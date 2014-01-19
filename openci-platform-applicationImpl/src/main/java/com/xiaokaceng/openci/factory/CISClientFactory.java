package com.xiaokaceng.openci.factory;

import java.util.HashSet;
import java.util.Set;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.CISClientInstantiationException;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.pojo.GitConfigurationPojo;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.ToolConfigurationPojo;

public class CISClientFactory {

	private static Set<ToolConfigurationPojo> toolConfigurationPojos = new HashSet<ToolConfigurationPojo>();
	
	static {
		toolConfigurationPojos.add(new GitConfigurationPojo());
		toolConfigurationPojos.add(new JenkinsConfigurationPojo());
	}
	
	public static CISClient getInstance(ToolConfiguration toolConfiguration) {
		for (ToolConfigurationPojo each : toolConfigurationPojos) {
			each.instanceCISClient(toolConfiguration, false);
			if (each.isInstance()) {
				return each.getCISClient();
			}
		}
		throw new CISClientInstantiationException();
	}
<<<<<<< HEAD
	
	public static CISClient getInstanceByCAS(ToolConfiguration toolConfiguration) {
		for (ToolConfigurationPojo each : toolConfigurationPojos) {
			each.instanceCISClient(toolConfiguration, true);
			if (each.isInstance()) {
				return each.getCISClient();
			}
		}
		throw new CISClientInstantiationException();
=======

	private static CISClient createJenkinsCISClient(ToolConfiguration toolConfiguration) {
		AuthenticationStrategy authentication = new JenkinsOwnAuthentication(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());
		JenkinsCISClient jenkinsCISClient = new JenkinsCISClient(toolConfiguration.getServiceUrl(), null);
		// jenkinsCISClient.setScmConfig(new SvnConfig("http://abc", "test", "test"));
		return jenkinsCISClient;
	}

	private static CISClient createSvnCISClient(ToolConfiguration toolConfiguration) {
		// TODO 需修改configuration
		return null;
>>>>>>> b900b1bda5e4fe68db10b8cb2d6f9f000ba5d830
	}

}
