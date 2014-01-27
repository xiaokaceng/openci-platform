package com.xiaokaceng.openci.executor;

import java.util.Set;

import javax.inject.Inject;

import org.openkoala.opencis.api.CISClient;
import org.openkoala.opencis.api.Developer;
import org.openkoala.opencis.api.Project;
import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.ToolIntegrationExecutorParamIllegalException;
import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolInterface;
import com.xiaokaceng.openci.domain.ToolInterfaceImplement;
import com.xiaokaceng.openci.factory.CISClientFactory;
import com.xiaokaceng.openci.pojo.JenkinsConfigurationPojo;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

public class ToolIntegrationExecutor {

	@Inject
	private TaskExecutor taskExecutor;

	@Inject
	private OpenciApplication openciApplication;

	public void execute(ProjectIntegration projectIntegration) {
		verify(projectIntegration);
		reloadToolConfigurationIfNecessary(projectIntegration);
		Set<Tool> tools = projectIntegration.getTools();
		if (tools != null && tools.size() > 0) {
			for (Tool each : tools) {
				taskExecutor.execute(new CISClientTask(each, projectIntegration));
			}
		}
		// TODO 是否整合CAS用户管理
	}

	private void reloadToolConfigurationIfNecessary(ProjectIntegration projectIntegration) {
		if (projectIntegration.getScmConfig() != null) {
			CISClientFactory.reloadJenkinsConfiguration(new JenkinsConfigurationPojo(projectIntegration.getScmConfig()));
		}
	}

	private class CISClientTask implements Runnable {

		private Tool tool;

		private ProjectIntegration projectIntegration;

		private Project project;

		private CISClient cisClient;

		public CISClientTask(Tool tool, ProjectIntegration projectIntegration) {
			this.tool = tool;
			this.projectIntegration = projectIntegration;
			cisClient = CISClientFactory.getInstance(tool.getToolConfiguration());
			project = projectIntegration.toCISProject();
		}

		public void run() {
			cisClient.authenticate();
			createProject();
			createUserIfNecessary();
			createRoleIfNecessary();
			assignUserToRole();
			cisClient.close();
			tool.updateToolIntegrationStatus();
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
