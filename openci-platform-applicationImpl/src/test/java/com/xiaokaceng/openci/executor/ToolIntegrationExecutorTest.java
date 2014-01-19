package com.xiaokaceng.openci.executor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.xiaokaceng.openci.AbstractIntegrationTest;
import com.xiaokaceng.openci.domain.JenkinsConfiguration;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

@Ignore
public class ToolIntegrationExecutorTest extends AbstractIntegrationTest {

	@Inject
	private ToolIntegrationExecutor toolIntegrationExecutor;

	@Test
	public void testExecutor() {
		toolIntegrationExecutor.execute(createProjectIntegration());
		// System.setProperty("webdriver.firefox.bin",
		// "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		// WebDriver driver = new FirefoxDriver();
		//
		// driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		// ToolConfiguration toolConfiguration = createToolConfiguration();
		// AuthenticationStrategy authentication = new JenkinsOwnAuthentication(
		// toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(),
		// toolConfiguration.getPassword());
		//
		// JenkinsCISClient jenkinsCISClient = new
		// JenkinsCISClient(toolConfiguration.getServiceUrl(), authentication);
		// jenkinsCISClient.setScmConfig(new
		// SvnConfig("http://git.oschina.net/xiaokaceng/openci-platform.git",
		// "test", "test"));
		// jenkinsCISClient.authenticate();
		// jenkinsCISClient.createProject(createProjectIntegration().toCISProject());
		// toolIntegrationExecutor.execute(createProjectIntegration());
	}

	private ProjectIntegration createProjectIntegration() {
		ProjectIntegration projectIntegration = new ProjectIntegration();
		projectIntegration.setGroupId("com.xiaokaceng.openci");
		projectIntegration.setArtifactId("openci-platform");
		projectIntegration.setProjectName("openci-platform");
		projectIntegration.setTools(createTools());
		return projectIntegration;
	}

	private Set<Tool> createTools() {
		Set<Tool> tools = new HashSet<Tool>();
		tools.add(new Tool(createToolConfiguration(), null));
		return tools;
	}

	private ToolConfiguration createToolConfiguration() {
		ToolConfiguration toolConfiguration = new JenkinsConfiguration("jenkins", "http://localhost:8080/jenkins", "admin", "admin");
		return toolConfiguration;
	}

}
