package com.xiaokaceng.openci.application.impl;

import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;

public class ProjectApplicationImpl implements ProjectApplication {

	public void createProject(Project project) {
		if (project == null) {
			throw new EntityNullException();
		}
		project.save();
	}

	public void addIntegrationTool(Project project, Tool tool) {
		if (project == null) {
			throw new EntityNullException();
		}
		project.addTool(tool);
	}

	
	
	
}
