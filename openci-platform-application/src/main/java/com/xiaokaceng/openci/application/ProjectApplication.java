package com.xiaokaceng.openci.application;

import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;

public interface ProjectApplication {

	void createProject(Project project);
	
	void addIntegrationTool(Project project, Tool tool);
	
}