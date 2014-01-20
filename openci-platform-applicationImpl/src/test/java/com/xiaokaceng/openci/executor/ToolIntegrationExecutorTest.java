package com.xiaokaceng.openci.executor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.openkoala.opencis.api.AuthenticationStrategy;
import org.openkoala.opencis.api.Developer;
import org.openkoala.opencis.api.Project;
import org.openkoala.opencis.jenkins.JenkinsCISClient;
import org.openkoala.opencis.jenkins.authentication.JenkinsOwnAuthentication;
import org.openkoala.opencis.jenkins.configureImpl.scm.GitConfig;
import org.openkoala.opencis.jenkins.configureImpl.scm.SvnConfig;

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
		// toolIntegrationExecutor.execute(createProjectIntegration());
		// System.setProperty("webdriver.firefox.bin",
		// "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		// WebDriver driver = new FirefoxDriver();
		//
		// driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		ToolConfiguration toolConfiguration = createToolConfiguration();
		AuthenticationStrategy authentication = new JenkinsOwnAuthentication(toolConfiguration.getServiceUrl(), toolConfiguration.getUsername(), toolConfiguration.getPassword());

		JenkinsCISClient jenkinsCISClient = new JenkinsCISClient(toolConfiguration.getServiceUrl(), authentication);
		jenkinsCISClient.setScmConfig(new SvnConfig("http://git.oschina.net/xiaokaceng/openci-platform.git", "test", "test"));
		jenkinsCISClient.authenticate();
		Project project = createProjectIntegration().toCISProject();
		jenkinsCISClient.createProject(project);
		jenkinsCISClient.createUserIfNecessary(project, createDeveloper());
		jenkinsCISClient.createRoleIfNecessary(project, project.getArtifactId());
		jenkinsCISClient.assignUsersToRole(project, project.getArtifactId(), createDeveloper());
		
		// toolIntegrationExecutor.execute(createProjectIntegration());
		// ToolConfiguration toolConfiguration = createToolConfiguration();
		// SSHConnectConfig sshConnectConfig = new
		// SSHConnectConfig(toolConfiguration.getServiceUrl(),
		// toolConfiguration.getUsername(), toolConfiguration.getPassword());
		// TracCISClient cisClient = new TracCISClient(sshConnectConfig);
		// cisClient.createProject(createProjectIntegration().toCISProject());
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
		// ToolConfiguration toolConfiguration = new TracConfiguration("trac",
		// "10.108.1.138", "root", "luomin888", "/tmp");
		ToolConfiguration toolConfiguration = new JenkinsConfiguration("jenkins", "http://10.108.1.138:8080/jenkins", "admin", "admin");
		return toolConfiguration;
	}

	private Developer createDeveloper() {
		Developer developer = new Developer();
		developer.setId("test");
		developer.setName("test");
		developer.setEmail("test@test.com");
		developer.setPassword("test");
		developer.setFullName("test");
		return developer;
	}
}
