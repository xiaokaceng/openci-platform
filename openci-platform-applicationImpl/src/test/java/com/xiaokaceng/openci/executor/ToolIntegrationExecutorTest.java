package com.xiaokaceng.openci.executor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

public class ToolIntegrationExecutorTest extends AbstractIntegrationTest {

	@Inject
	private ToolIntegrationExecutor toolIntegrationExecutor;
	
	@Test
	public void testExecutor() {
		toolIntegrationExecutor.execute(createProjectIntegration());
	}
	
	private ProjectIntegration createProjectIntegration() {
		ProjectIntegration projectIntegration = new ProjectIntegration();
		projectIntegration.setTools(createTools());
		return projectIntegration;
	}

	private Set<Tool> createTools() {
		Set<Tool> tools = new HashSet<Tool>();
		tools.add(new Tool());
		return tools;
	}
}
