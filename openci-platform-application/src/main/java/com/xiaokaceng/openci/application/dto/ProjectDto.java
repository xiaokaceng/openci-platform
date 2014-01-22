package com.xiaokaceng.openci.application.dto;

import java.io.Serializable;

import org.openkoala.koala.widget.Project;

import com.xiaokaceng.openci.domain.ProjectDetail;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = -6708098893349388801L;

	private String projectName;
	
	private Project projectForCreate;
	
	private com.xiaokaceng.openci.domain.Project projectForCis;

	private ScmConfig scmConfig;
	
	private boolean userCas;
	
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
	
	public ScmConfig getScmConfig() {
		return scmConfig;
	}

	public void setScmConfig(ScmConfig scmConfig) {
		this.scmConfig = scmConfig;
	}

	public boolean isUserCas() {
		return userCas;
	}

	public void setUserCas(boolean userCas) {
		this.userCas = userCas;
	}

	public ProjectDto() {
		projectForCis = new com.xiaokaceng.openci.domain.Project(null);
		initProjectInfo();
	}
	
	public ProjectDto(String projectName) {
		this.projectName = projectName;
		projectForCis = new com.xiaokaceng.openci.domain.Project(projectName);
		initProjectInfo();
	}
	
	private void initProjectInfo() {
		projectForCreate = new Project();
		projectForCreate.setPackaging("pom");
	}
	
}
