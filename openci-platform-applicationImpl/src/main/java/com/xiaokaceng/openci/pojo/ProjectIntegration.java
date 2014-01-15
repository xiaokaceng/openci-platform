package com.xiaokaceng.openci.pojo;

import java.util.Map;
import java.util.Set;

import org.openkoala.opencis.api.Project;

import com.xiaokaceng.openci.domain.Tool;

public class ProjectIntegration {

	private String artifactId;
	
	private String groupId;
	
	private String projectName;
	
	// 解析pom.xml对应Map
	private Map<String, Object> pomFileNodeElements;
	
	private Set<Tool> tools;

	public Project toCISProject() {
		Project project = new Project();
		project.setArtifactId(artifactId);
		project.setGroupId(groupId);
		project.setProjectName(projectName);
		project.setNodeElements(pomFileNodeElements);
		return project;
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

	public Map<String, Object> getPomFileNodeElements() {
		return pomFileNodeElements;
	}

	public void setPomFileNodeElements(Map<String, Object> pomFileNodeElements) {
		this.pomFileNodeElements = pomFileNodeElements;
	}

	public Set<Tool> getTools() {
		return tools;
	}

	public void setTools(Set<Tool> tools) {
		this.tools = tools;
	}
	
}
