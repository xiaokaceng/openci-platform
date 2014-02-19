package com.xiaokaceng.openci.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.openkoala.koala.widget.Project;

import com.xiaokaceng.openci.domain.Developer;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.domain.Role;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = -6708098893349388801L;

	private String projectName;

	private Project projectForCreate;

	private com.xiaokaceng.openci.domain.Project projectForCis;

	private ScmConfig scmConfig;

	private boolean userCas;

	private Set<ToolConfigurationDto> toolConfigurationDtos;

	private Set<ProjectDeveloperDto> projectDeveloperDtos;
	
	private String projectLead;

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
			ToolConfiguration toolConfiguration = ToolConfiguration.get(ToolConfiguration.class, toolConfigurationDto.getId());
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

	public void setProjectDeveloperDtos(Set<ProjectDeveloperDto> projectDeveloperDtos) {
		this.projectDeveloperDtos = projectDeveloperDtos;
		configProjectDevelopers();
	}

	private void configProjectDevelopers() {
		if (projectDeveloperDtos != null && projectDeveloperDtos.size() > 0) {
			Set<ProjectDeveloper> developers = new HashSet<ProjectDeveloper>();
			for (ProjectDeveloperDto each : projectDeveloperDtos) {
				ProjectDeveloper projectDeveloper = new ProjectDeveloper(getRoles(each.getRoleIds()), getDeveloper(each.getDeveloperId()), projectForCis);
				developers.add(projectDeveloper);
			}
			projectForCis.setDevelopers(developers);
		}
	}

	private Developer getDeveloper(Long developerId) {
		return Developer.get(Developer.class, developerId);
	}

	private Set<Role> getRoles(Long[] roleIds) {
		Set<Role> roles = new HashSet<Role>();
		if (roleIds != null && roleIds.length > 0) {
			for (Long roleId : roleIds) {
				roles.add(Role.get(Role.class, roleId));
			}
		}
		return roles;
	}

	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}

}
