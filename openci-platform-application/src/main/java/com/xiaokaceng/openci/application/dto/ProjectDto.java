package com.xiaokaceng.openci.application.dto;

import java.io.Serializable;

import org.openkoala.koala.widget.Project;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = -6708098893349388801L;

	private String projectName;
	
	private Project projectForCreate;
	
	private com.xiaokaceng.openci.domain.Project projectForCis;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
		if (projectForCreate != null) {
			projectForCreate.setAppName(projectName);
		}
		if (projectForCis != null) {
			projectForCis.setName(projectName);
		}
	}

	public Project getProjectForCreate() {
		return projectForCreate;
	}

	public void setProjectForCreate(Project projectForCreate) {
		this.projectForCreate = projectForCreate;
	}

	public com.xiaokaceng.openci.domain.Project getProjectForCis() {
		return projectForCis;
	}

	public void setProjectForCis(com.xiaokaceng.openci.domain.Project projectForCis) {
		this.projectForCis = projectForCis;
	}
	
	public ProjectDto() {
		projectForCis = new com.xiaokaceng.openci.domain.Project(null);
		projectForCreate = new Project();
		initProjectInfo();
	}
	
	public ProjectDto(String projectName) {
		this.projectName = projectName;
		projectForCis = new com.xiaokaceng.openci.domain.Project(projectName);
		projectForCreate = new Project();
		initProjectInfo();
	}
	
	private void initProjectInfo() {
		projectForCreate = new Project();
		projectForCreate.initSSJProject();
	}
	
}
