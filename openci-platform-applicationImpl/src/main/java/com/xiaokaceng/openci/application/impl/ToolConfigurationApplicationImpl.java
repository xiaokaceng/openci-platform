package com.xiaokaceng.openci.application.impl;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.factory.CISClientFactory;

public class ToolConfigurationApplicationImpl implements ToolConfigurationApplication {

	public void createConfiguration(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.save();
	}

	public void setToolUsabled(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.usabled();
	}

	public void setToolUnusabled(ToolConfiguration toolConfiguration) {
		if (toolConfiguration == null) {
			throw new EntityNullException();
		}
		toolConfiguration.unusabled();
	}

	public boolean canConnect(ToolConfiguration toolConfiguration) {
		CISClient cisClient = CISClientFactory.getInstance(toolConfiguration);
		return cisClient.canConnect();
	}

}
