package com.xiaokaceng.openci.application.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.openkoala.koala.mojo.KoalaProjectCreate;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryChannelService;
import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDetail;
import com.xiaokaceng.openci.domain.ProjectStatus;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.dto.ProjectDto;
import com.xiaokaceng.openci.dto.ProjectQueryDto;
import com.xiaokaceng.openci.executor.ProjectEmailSendExecutor;

@Named
@Transactional("transactionManager_opencis")
public class ProjectApplicationImpl implements ProjectApplication {

	@Inject
	private QueryChannelService queryChannel;
	
	@Inject
	private ProjectEmailSendExecutor projectEmailSendExecutor;

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
	}

	private ProjectStatus getProjectStatus(boolean createResult) {
		if (createResult) {
			return ProjectStatus.INTEGRATION_TOOL;
		}
		return ProjectStatus.CREATE_MAVEN_PROJECT_FAILURE;
	}

	private String getProjectSavePath() {
		return System.getProperty("java.io.tmpdir");
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

	public void addIntegrationTool(Project project, Tool tool) {
		if (project == null) {
			throw new EntityNullException();
		}
		project.addTool(tool);
	}

	public Page<Project> pagingQueryProject(ProjectQueryDto projectQueryDto, int currentPage, int pagesize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _project from Project _project where _project.createDate <= ? and _project.abolishDate > ?");
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);
		
		if (projectQueryDto != null) {
			if (!StringUtils.isBlank(projectQueryDto.getName())) {
				jpql.append(" and _project.name like ?");
				conditionVals.add(MessageFormat.format("%{0}%", projectQueryDto.getName()));
			}
			if (projectQueryDto.getStartDate() != null) {
				jpql.append(" and _project.projectCreateDate >= ?");
				conditionVals.add(projectQueryDto.getStartDate());
			}
			if (projectQueryDto.getEndDate() != null) {
				jpql.append(" and _project.projectCreateDate <= ?");
				conditionVals.add(projectQueryDto.getEndDate());
			}
		}
		return queryChannel.queryPagedResultByPageNo(jpql.toString(), conditionVals.toArray(), currentPage, pagesize);
	}

	public Project getDetail(long projectId) {
		return Project.get(Project.class, projectId);
	}

	public boolean isExistByName(String name) {
		return Project.isExixtByName(name);
	}

	public void updateIntegrationToolStatus(long toolId) {
		Tool tool = Tool.get(Tool.class, toolId);
		tool.updateIntegrationStatus();
		updateProjectStatus(tool);
	}

	private void updateProjectStatus(Tool tool) {
		Project project = tool.getProject(new Date());
		project.updateProjectStatus();
		sendEmailToDeveloper(project);
	}

	private void sendEmailToDeveloper(Project project) {
		if (project.getProjectStatus().equals(ProjectStatus.SUCCESS)) {
			projectEmailSendExecutor.execute(project);
		}
	}

	public boolean remove(long projectId) {
		Project project = Project.get(Project.class, projectId);
		if (project == null) {
			return false;
		}
		project.abolish(new Date());
		return true;
	}

}
