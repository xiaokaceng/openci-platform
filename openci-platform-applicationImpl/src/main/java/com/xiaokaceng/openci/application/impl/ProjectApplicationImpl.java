package com.xiaokaceng.openci.application.impl;

import javax.inject.Named;

import org.openkoala.koala.mojo.KoalaProjectCreate;
import org.springframework.transaction.annotation.Transactional;

import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.application.dto.ProjectDto;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;

@Named
@Transactional("transactionManager_opencis")
public class ProjectApplicationImpl implements ProjectApplication {

	public void createProject(ProjectDto projectDto) {
		Project projectForCis = projectDto.getProjectForCis();
		if (projectForCis == null) {
			throw new EntityNullException();
		}
		createProjectFile(projectDto.getProjectForCreate());
		
		projectForCis.save();
	}

	private void createProjectFile(org.openkoala.koala.widget.Project projectForCreate) {
		KoalaProjectCreate koalaProjectCreate = new KoalaProjectCreate();
		try {
			koalaProjectCreate.createProject(projectForCreate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addIntegrationTool(Project project, Tool tool) {
		if (project == null) {
			throw new EntityNullException();
		}
		project.addTool(tool);
	}

	
	
	
}
