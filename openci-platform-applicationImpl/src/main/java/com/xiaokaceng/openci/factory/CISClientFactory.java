package com.xiaokaceng.openci.factory;

import java.util.HashSet;
import java.util.Set;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.CISClientNotInstanceException;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.pojo.GitConfigurationPojo;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.ToolConfigurationPojo;
import com.xiaokaceng.openci.pojo.TracConfigurationPojo;

public class CISClientFactory {

	private static Set<ToolConfigurationPojo> toolConfigurationPojos = new HashSet<ToolConfigurationPojo>();
	
	static {
		toolConfigurationPojos.add(new GitConfigurationPojo());
		toolConfigurationPojos.add(new JenkinsConfigurationPojo());
		toolConfigurationPojos.add(new TracConfigurationPojo());
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
