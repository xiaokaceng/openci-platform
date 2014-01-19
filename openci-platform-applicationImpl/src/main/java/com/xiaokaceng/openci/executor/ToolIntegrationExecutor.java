package com.xiaokaceng.openci.executor;

import java.util.Set;

import javax.inject.Inject;

import org.openkoala.opencis.api.CISClient;
import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.ToolIntegrationExecutorParamIllegalException;
import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolInterface;
import com.xiaokaceng.openci.domain.ToolInterfaceImplement;
import com.xiaokaceng.openci.factory.CISClientFactory;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

public class ToolIntegrationExecutor {

	@Inject
	private TaskExecutor taskExecutor;
	
	@Inject
	private OpenciApplication openciApplication;

	public void execute(ProjectIntegration projectIntegration) {
		verify(projectIntegration);
		Set<Tool> tools = projectIntegration.getTools();
		if (tools.size() > 0) {
			for (Tool each : tools) {
				taskExecutor.execute(new CISClientTask(each, projectIntegration));
			}
		}
	}

	private class CISClientTask implements Runnable {

		private Tool tool;
		
		private ProjectIntegration projectIntegration;
		
		private CISClient cisClient;
		
		public CISClientTask(Tool tool, ProjectIntegration projectIntegration) {
			this.tool = tool;
			this.projectIntegration = projectIntegration;
			
			// TODO 是否整合CAS
			cisClient = CISClientFactory.getInstance(tool.getToolConfiguration());
		}
		
		public void run() {
			System.out.println("=======");
			cisClient.authenticate();
			createProject();
//			createRoleIfNecessary();
//			assignUserToRole();
		}

		private void createProject() {
			ToolInterfaceImplement toolInterfaceImplement = new ToolInterfaceImplement(tool, ToolInterface.CREATE_PROJECT, true, null);
			try {
				System.out.println("=======");
				
				cisClient.createProject(projectIntegration.toCISProject());
			} catch (Exception e) {
				System.out.println("=======" + e.toString());
				e.printStackTrace();
				toolInterfaceImplement.setSuccess(false);
				toolInterfaceImplement.setRecord(e.toString());
			} finally {
				openciApplication.saveEntity(toolInterfaceImplement);
			}
		}
	}
	
	private void verify(ProjectIntegration projectIntegration) {
		if (projectIntegration == null) {
			throw new ToolIntegrationExecutorParamIllegalException();
		}
	}

}
