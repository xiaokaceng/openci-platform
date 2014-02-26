package com.xiaokaceng.openci.executor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.opencis.api.CISClient;
import org.openkoala.opencis.api.Developer;
import org.openkoala.opencis.api.Project;
import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.ToolIntegrationExecutorParamIllegalException;
import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolInterface;
import com.xiaokaceng.openci.domain.ToolInterfaceImplement;
import com.xiaokaceng.openci.factory.CISClientFactory;
import com.xiaokaceng.openci.pojo.GitConfigurationPojo;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.JiraConfigurationPojo;
import com.xiaokaceng.openci.pojo.ProjectIntegration;
import com.xiaokaceng.openci.pojo.SonarConfigurationPojo;
import com.xiaokaceng.openci.pojo.SvnConfigurationPojo;
import com.xiaokaceng.openci.pojo.ToolConfigurationPojo;
import com.xiaokaceng.openci.pojo.TracConfigurationPojo;

@Named
public class ToolIntegrationExecutor {

	@Inject
	private TaskExecutor taskExecutor;

	@Inject
	private OpenciApplication openciApplication;
	
	@Inject
	private ProjectApplication projectApplication;

	public void execute(ProjectIntegration projectIntegration) {
		verify(projectIntegration);
		Set<Tool> tools = projectIntegration.getTools();
		if (tools != null && tools.size() > 0) {
			for (Tool each : tools) {
				taskExecutor.execute(new CISClientTask(each, projectIntegration));
			}
		}
		// TODO 是否整合CAS用户管理
	}

	private class CISClientTask implements Runnable {

		private Tool tool;

		private ProjectIntegration projectIntegration;

		private Project project;

		private CISClient cisClient;

		public CISClientTask(Tool tool, ProjectIntegration projectIntegration) {
			this.tool = tool;
			this.projectIntegration = projectIntegration;
			cisClient = CISClientFactory.getInstance(tool.getToolConfiguration(), initToolConfigurationPojos());
			project = projectIntegration.toCISProject();
		}

		private Set<ToolConfigurationPojo> initToolConfigurationPojos() {
			Set<ToolConfigurationPojo> toolConfigurationPojos = new HashSet<ToolConfigurationPojo>();
			toolConfigurationPojos.add(new SvnConfigurationPojo());
			toolConfigurationPojos.add(new GitConfigurationPojo());
			toolConfigurationPojos.add(createJenkinsConfigurationPojo());
			toolConfigurationPojos.add(new SonarConfigurationPojo());
			toolConfigurationPojos.add(new JiraConfigurationPojo());
			toolConfigurationPojos.add(new TracConfigurationPojo());
			return toolConfigurationPojos;
		}

		private JenkinsConfigurationPojo createJenkinsConfigurationPojo() {
			JenkinsConfigurationPojo jenkinsConfigurationPojo = new JenkinsConfigurationPojo();
			if (projectIntegration.getScmConfig() != null) {
				jenkinsConfigurationPojo.setScmConfig(projectIntegration.getScmConfig());
			}
			return jenkinsConfigurationPojo;
		}

		public void run() {
			cisClient.authenticate();
			createUserIfNecessary();
			createProject();
			createRoleIfNecessary();
			assignUserToRole();
			cisClient.close();
			projectApplication.updateIntegrationToolStatus(tool.getId());
		}

		private void createProject() {
			ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.CREATE_PROJECT, true, null);
			try {
				cisClient.createProject(project);
			} catch (Exception e) {
				e.printStackTrace();
				toolInterfaceImplement.setSuccess(false);
				toolInterfaceImplement.setRecord(e.toString());
			} finally {
				openciApplication.saveEntity(toolInterfaceImplement);
			}
		}

		private void createUserIfNecessary() {
			for (Developer each : projectIntegration.getDevelopers()) {
				ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.CREATE_USER_IF_NECESSARY, true, null);
				try {
					cisClient.createUserIfNecessary(project, each);
				} catch (Exception e) {
					e.printStackTrace();
					toolInterfaceImplement.setSuccess(false);
					toolInterfaceImplement.setRecord(e.toString());
				} finally {
					openciApplication.saveEntity(toolInterfaceImplement);
				}
			}
		}

		private void createRoleIfNecessary() {
			ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.CREATE_ROLE_IF_NECESSARY, true, null);
			try {
				cisClient.createRoleIfNecessary(project, project.getArtifactId());
			} catch (Exception e) {
				e.printStackTrace();
				toolInterfaceImplement.setSuccess(false);
				toolInterfaceImplement.setRecord(e.toString());
			} finally {
				openciApplication.saveEntity(toolInterfaceImplement);
			}
		}

		private void assignUserToRole() {
			for (Developer each : projectIntegration.getDevelopers()) {
				ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.ASSIGN_USER_TO_ROLE, true, null);
				try {
					cisClient.assignUsersToRole(project, project.getArtifactId(), each);
				} catch (Exception e) {
					e.printStackTrace();
					toolInterfaceImplement.setSuccess(false);
					toolInterfaceImplement.setRecord(e.toString());
				} finally {
					openciApplication.saveEntity(toolInterfaceImplement);
				}
			}
		}

	}

	private void verify(ProjectIntegration projectIntegration) {
		if (projectIntegration == null) {
			throw new ToolIntegrationExecutorParamIllegalException();
		}
	}

}
