package com.xiaokaceng.openci.application;

import com.xiaokaceng.openci.application.dto.ProjectDto;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;

public interface ProjectApplication {

	void createProject(ProjectDto projectDto);
	
	void addIntegrationTool(Project project, Tool tool);
	
}