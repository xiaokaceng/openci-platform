package com.xiaokaceng.openci.application.impl;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.koala.mojo.KoalaProjectCreate;
import org.springframework.transaction.annotation.Transactional;

import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.application.dto.ProjectDto;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.executor.ToolIntegrationExecutor;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

@Named
@Transactional("transactionManager_opencis")
public class ProjectApplicationImpl implements ProjectApplication {

	@Inject
	private ToolIntegrationExecutor toolIntegrationExecutor;
	
	public void createProject(ProjectDto projectDto) {
		Project projectForCis = projectDto.getProjectForCis();
		if (projectForCis == null) {
			throw new EntityNullException();
		} 
		createProjectFile(projectDto.getProjectForCreate());
		projectForCis.save();
		
		integrateProjectToTools(projectDto.getProjectForCreate(), projectDto.getProjectForCis().getTools());
	}

	private void integrateProjectToTools(org.openkoala.koala.widget.Project project, Set<Tool> tools) {
		ProjectIntegration projectIntegration = new ProjectIntegration();
		projectIntegration.setGroupId(project.getGroupId());
		projectIntegration.setArtifactId(project.getArtifactId());
		projectIntegration.setProjectName(project.getAppName());
		projectIntegration.setTools(tools);
		toolIntegrationExecutor.execute(projectIntegration);
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
