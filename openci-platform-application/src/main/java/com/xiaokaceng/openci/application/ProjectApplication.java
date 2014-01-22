package com.xiaokaceng.openci.application;

import java.util.List;

import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.dto.ProjectDto;

public interface ProjectApplication {

	void createProject(ProjectDto projectDto);
	
	List<Project> findAllProjects();
	
	void addIntegrationTool(Project project, Tool tool);
	
}