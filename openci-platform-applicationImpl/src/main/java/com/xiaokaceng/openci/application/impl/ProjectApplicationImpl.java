package com.xiaokaceng.openci.application.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.openkoala.koala.mojo.KoalaProjectCreate;
import org.openkoala.opencis.api.Developer;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryChannelService;
import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.domain.CasUserConfiguration;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDetail;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.domain.ProjectStatus;
import com.xiaokaceng.openci.domain.Role;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.dto.ProjectDto;
import com.xiaokaceng.openci.dto.ProjectQueryDto;
import com.xiaokaceng.openci.executor.ToolIntegrationExecutor;
import com.xiaokaceng.openci.pojo.ProjectIntegration;

@Named
@Transactional("transactionManager_opencis")
public class ProjectApplicationImpl implements ProjectApplication {

	@Inject
	private ToolIntegrationExecutor toolIntegrationExecutor;

	@Inject
	private QueryChannelService queryChannel;

	public void createProject(ProjectDto projectDto) {
		Project projectForCis = projectDto.getProjectForCis();
		if (projectForCis == null) {
			throw new EntityNullException();
		}
		projectDto.getProjectForCreate().setPath(getProjectSavePath());
		persistProject(projectDto, projectForCis);
	}

	private void persistProject(ProjectDto projectDto, Project projectForCis) {
		projectForCis.setProjectDetail(createProjectDetail(projectDto));
		boolean createResult = createProjectFile(projectDto.getProjectForCreate());
		projectForCis.setProjectStatus(getProjectStatus(createResult));
		projectForCis.save();
		if (createResult) {
			System.out.println("=====");
			// integrateProjectToTools(projectDto);
		}
	}

	private ProjectStatus getProjectStatus(boolean createResult) {
		if (createResult) {
			return ProjectStatus.INTEGRATION_TOOL;
		}
		return ProjectStatus.CREATE_MAVEN_PROJECT_FAILURE;
	}

	private String getProjectSavePath() {
		return System.getenv("TMP");
	}

	private ProjectDetail createProjectDetail(ProjectDto projectDto) {
		ProjectDetail projectDetail = new ProjectDetail();
		org.openkoala.koala.widget.Project project = projectDto.getProjectForCreate();
		projectDetail.setArtifactId(project.getArtifactId());
		projectDetail.setGroupId(project.getGroupId());
		projectDetail.setIntegrationCas(projectDto.isUserCas());
		projectDetail.setProjectSavePath(getProjectSavePath());
		projectDetail.setScmRepositoryUrl(projectDto.getScmConfig().getRepositoryUrl());
		projectDetail.setScmType(projectDto.getScmConfig().getScmType());
		return projectDetail;
	}

	private boolean createProjectFile(org.openkoala.koala.widget.Project projectForCreate) {
		KoalaProjectCreate koalaProjectCreate = new KoalaProjectCreate();
		try {
			koalaProjectCreate.createProject(projectForCreate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void integrateProjectToTools(ProjectDto projectDto) {
		org.openkoala.koala.widget.Project project = projectDto.getProjectForCreate();
		ProjectIntegration projectIntegration = new ProjectIntegration();
		projectIntegration.setGroupId(project.getGroupId());
		projectIntegration.setArtifactId(project.getArtifactId());
		projectIntegration.setProjectName(project.getAppName());
		projectIntegration.setTools(projectDto.getProjectForCis().getTools());
		projectIntegration.setProjectSavePath(project.getPath());
		projectIntegration.setDevelopers(transformDevelopers(projectDto.getProjectForCis().getDevelopers()));
		if (projectDto.isUserCas()) {
			projectIntegration.setCasUserConfiguration(CasUserConfiguration.getUniqueInstance());
		}

		toolIntegrationExecutor.execute(projectIntegration);
	}

	private Set<Developer> transformDevelopers(Set<ProjectDeveloper> projectDevelopers) {
		Set<Developer> results = new HashSet<Developer>();
		for (ProjectDeveloper each : projectDevelopers) {
			Developer developer = new Developer();
			developer.setId(each.getDeveloper().getDeveloperId());
			developer.setName(each.getDeveloper().getName());
			developer.setEmail(each.getDeveloper().getEmail());
			developer.setPassword(each.getDeveloper().getPassword());

			List<String> roles = new ArrayList<String>();
			for (Role role : each.getRoles()) {
				roles.add(role.getName());
			}
			developer.setRoles(roles);
			results.add(developer);
		}
		return results;
	}

	public void addIntegrationTool(Project project, Tool tool) {
		if (project == null) {
			throw new EntityNullException();
		}
		project.addTool(tool);
	}

	public Page<Project> pagingQueryProject(ProjectQueryDto projectQueryDto, int currentPage, int pagesize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _project from Project _project");
		
		if (projectQueryDto != null) {
			jpql.append(" where 1=1");
			if (!StringUtils.isBlank(projectQueryDto.getName())) {
				jpql.append(" and _project.name like ?");
				conditionVals.add(MessageFormat.format("%{0}%", projectQueryDto.getName()));
			}
			if (projectQueryDto.getStartDate() != null) {
				jpql.append(" and _project.createDate > ?");
				conditionVals.add(MessageFormat.format("{0}", projectQueryDto.getStartDate()));
			}
			if (projectQueryDto.getEndDate() != null) {
				jpql.append(" and _project.createDate < ?");
				conditionVals.add(MessageFormat.format("{0}", projectQueryDto.getEndDate()));
			}
		}
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

}
