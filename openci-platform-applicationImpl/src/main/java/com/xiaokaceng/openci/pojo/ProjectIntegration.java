package com.xiaokaceng.openci.pojo;

import java.util.Set;

import org.openkoala.opencis.api.Project;

import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class ProjectIntegration {

	private String artifactId;

	private String groupId;

	private String projectName;

	private Set<Tool> tools;

	// 集成CAS用户管理
	private ToolConfiguration casUserManageToolConfiguration;

	// 项目存放路径
	private String projectSavePath;
	
	public Project toCISProject() {
		Project project = new Project();
		project.setArtifactId(artifactId);
		project.setGroupId(groupId);
		project.setProjectName(projectName);
		return project;
	}

	public boolean isIntegrationCas() {
		if (casUserManageToolConfiguration == null) {
			return false;
		}
		return true;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Set<Tool> getTools() {
		return tools;
	}

	public void setTools(Set<Tool> tools) {
		this.tools = tools;
	}

	public ToolConfiguration getCasUserManageToolConfiguration() {
		return casUserManageToolConfiguration;
	}

	public void setCasUserManageToolConfiguration(ToolConfiguration casUserManageToolConfiguration) {
		this.casUserManageToolConfiguration = casUserManageToolConfiguration;
	}

	public String getProjectSavePath() {
		return projectSavePath;
	}

	public void setProjectSavePath(String projectSavePath) {
		this.projectSavePath = projectSavePath;
	}

}
