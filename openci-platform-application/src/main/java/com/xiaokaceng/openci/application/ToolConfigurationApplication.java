package com.xiaokaceng.openci.application;

import com.xiaokaceng.openci.domain.ToolConfiguration;

public interface ToolConfigurationApplication {

	void createConfiguration(ToolConfiguration toolConfiguration);
	
	void setToolUsabled(ToolConfiguration toolConfiguration);
	
	void setToolUnusabled(ToolConfiguration toolConfiguration);
	
	boolean canConnect(ToolConfiguration toolConfiguration);
	
}