package com.xiaokaceng.openci.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.openkoala.koala.widget.Project;

import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.JiraConfiguration;
import com.xiaokaceng.openci.domain.SonarConfiguration;
import com.xiaokaceng.openci.domain.SvnConfiguration;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.ToolType;
import com.xiaokaceng.openci.domain.TracConfiguration;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = -6708098893349388801L;

	private String projectName;
	
	private Project projectForCreate;
	
	private com.xiaokaceng.openci.domain.Project projectForCis;

	private ScmConfig scmConfig;
	
	private boolean userCas;
	
	private Set<ToolConfigurationDto> toolConfigurationDtos;
	
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

	public Set<ToolConfigurationDto> getToolConfigurationDtos() {
		return toolConfigurationDtos;
	}

	public void setToolConfigurationDtos(Set<ToolConfigurationDto> toolConfigurationDtos) {
		this.toolConfigurationDtos = toolConfigurationDtos;
		configTools(toolConfigurationDtos);
	}
	
	private void configTools(Set<ToolConfigurationDto> toolConfigurationDtos) {
		Set<Tool> tools = new HashSet<Tool>();
		for (ToolConfigurationDto toolConfigurationDto : toolConfigurationDtos) {
			ToolConfiguration toolConfiguration = toolConfigurationDto.getToolType().getToolConfiguration();
			toolConfiguration.setId(toolConfigurationDto.getId());
			Tool tool = new Tool(toolConfiguration, projectForCis);
			tools.add(tool);
		}
		projectForCis.setTools(tools);
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
