package com.xiaokaceng.openci.executor;

import java.util.Set;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.ToolIntegrationExecutorParamIllegalException;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.factory.CISClientFactory;

public class ToolIntegrationExecutor {

	private Project project;

	public ToolIntegrationExecutor(Project project) {
		this.project = project;
		verify();
	}
	
	public void execute() {
		
	}
	
	private void verify() {
		if (project == null) {
			throw new ToolIntegrationExecutorParamIllegalException();
		}
	}

}
