package com.xiaokaceng.openci.factory;

import java.util.HashSet;
import java.util.Set;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.CISClientNotInstanceException;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.pojo.GitConfigurationPojo;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.JiraConfigurationPojo;
import com.xiaokaceng.openci.pojo.SonarConfigurationPojo;
import com.xiaokaceng.openci.pojo.SvnConfigurationPojo;
import com.xiaokaceng.openci.pojo.ToolConfigurationPojo;
import com.xiaokaceng.openci.pojo.TracConfigurationPojo;

public class CISClientFactory {

	private static Set<ToolConfigurationPojo> toolConfigurationPojos = new HashSet<ToolConfigurationPojo>();
	
	static {
		toolConfigurationPojos.add(new SvnConfigurationPojo());
		toolConfigurationPojos.add(new GitConfigurationPojo());
		toolConfigurationPojos.add(new JenkinsConfigurationPojo(null));
		toolConfigurationPojos.add(new SonarConfigurationPojo());
		toolConfigurationPojos.add(new JiraConfigurationPojo());
		toolConfigurationPojos.add(new TracConfigurationPojo());
	}
	
	public static void reloadJenkinsConfiguration(ToolConfigurationPojo jenkinsConfigurationPojo) {
		toolConfigurationPojos.remove(JenkinsConfigurationPojo.class);
		toolConfigurationPojos.add(jenkinsConfigurationPojo);
	}
	
	public static CISClient getInstance(ToolConfiguration toolConfiguration) {
		for (ToolConfigurationPojo each : toolConfigurationPojos) {
			each.instanceCISClient(toolConfiguration, false);
			if (each.isInstance()) {
				return each.getCISClient();
			}
		}
		throw new CISClientNotInstanceException();
	}
	
	public static CISClient getInstanceByCAS(ToolConfiguration toolConfiguration) {
		for (ToolConfigurationPojo each : toolConfigurationPojos) {
			each.instanceCISClient(toolConfiguration, true);
			if (each.isInstance()) {
				return each.getCISClient();
			}
		}
		throw new CISClientNotInstanceException();
	}

}
