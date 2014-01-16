package com.xiaokaceng.openci.application;

import java.util.List;

import com.xiaokaceng.openci.domain.ToolConfiguration;

public interface ToolConfigurationApplication {

	void createConfiguration(ToolConfiguration toolConfiguration);
	
	void updateConfiguration(ToolConfiguration toolConfiguration);
	
	void setToolUsabled(ToolConfiguration toolConfiguration);
	
	void setToolUnusabled(ToolConfiguration toolConfiguration);
	
	boolean canConnect(ToolConfiguration toolConfiguration);
	
	List<ToolConfiguration> getAllUsable();
}