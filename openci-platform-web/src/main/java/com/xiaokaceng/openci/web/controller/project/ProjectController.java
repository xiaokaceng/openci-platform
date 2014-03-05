package com.xiaokaceng.openci.web.controller.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.openkoala.koala.queryvo.TypeDef;
import org.openkoala.koala.util.ModuleDependencyUtils;
import org.openkoala.koala.widget.Module;
import org.openkoala.koala.widget.Project;
import org.openkoala.opencis.api.Developer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.domain.CasUserConfiguration;
import com.xiaokaceng.openci.domain.ProjectDetail;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.domain.ProjectStatus;
import com.xiaokaceng.openci.domain.Role;
import com.xiaokaceng.openci.dto.ProjectDto;
import com.xiaokaceng.openci.dto.ProjectQueryDto;
import com.xiaokaceng.openci.dto.ScmConfig;
import com.xiaokaceng.openci.executor.ToolIntegrationExecutor;
import com.xiaokaceng.openci.pojo.ProjectIntegration;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	@Inject
	private ProjectApplication projectApplication;

	@Inject
	private ToolIntegrationExecutor toolIntegrationExecutor;
	
	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createProject(@RequestBody ProjectDto projectDto) {
		projectApplication.createProject(projectDto);
		integrateProjectToTools(projectDto.getProjectForCis());
		return ResultDto.createSuccess();
	}
	
	private void integrateProjectToTools(com.xiaokaceng.openci.domain.Project project) {
		ProjectDetail projectDetail = project.getProjectDetail();
		ProjectIntegration projectIntegration = new ProjectIntegration();
		projectIntegration.setGroupId(projectDetail.getGroupId());
		projectIntegration.setArtifactId(projectDetail.getArtifactId());
		projectIntegration.setProjectName(project.getName());
		projectIntegration.setTools(project.getTools());
		projectIntegration.setProjectSavePath(projectDetail.getProjectSavePath());
		projectIntegration.setDevelopers(transformDevelopers(project.getDevelopers()));
		projectIntegration.setScmConfig(getScmConfig(projectDetail));
		projectIntegration.setProjectLead(projectDetail.getLead());
		if (projectDetail.isIntegrationCas()) {
			projectIntegration.setCasUserConfiguration(CasUserConfiguration.getUniqueInstance());
		}

		toolIntegrationExecutor.execute(projectIntegration);
	}

	private ScmConfig getScmConfig(ProjectDetail projectDetail) {
		ScmConfig scmConfig = new ScmConfig();
		scmConfig.setScmType(projectDetail.getScmType());
		scmConfig.setRepositoryUrl(projectDetail.getScmRepositoryUrl());
		return scmConfig;
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


	@ResponseBody
	@RequestMapping("/get-functions")
	public Map<String, Map<String, String>> getFunctions(String moduleType) {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		TypeDef typeDef = TypeDef.getInstance();
		result.put("functions", typeDef.getFunctions(moduleType));
		return result;
	}

	@ResponseBody
	@RequestMapping("/get-dependables")
	public List<Module> getDependables(@RequestBody Project project, String moduleType) {
		ModuleDependencyUtils moduleDependencyUtils = new ModuleDependencyUtils(project, moduleType);
		return moduleDependencyUtils.getCouldDependencyModules();
	}

	@ResponseBody
	@RequestMapping("/generate-default-modules")
	public ProjectDto generateDefaultModules(ProjectDto projectDto) {
		Project project = projectDto.getProjectForCreate();
		project.initSSJProject();
		project.initModulePrefix(project.getAppName());
		return projectDto;
	}

	@ResponseBody
	@RequestMapping("/pagingquery")
	public Map<String, Object> pagingQuery(ProjectQueryDto projectQueryDto, int page, int pagesize) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page<com.xiaokaceng.openci.domain.Project> projectPage = projectApplication.pagingQueryProject(projectQueryDto, page, pagesize);
		dataMap.put("Rows", projectPage.getResult());
		dataMap.put("start", page * pagesize - pagesize);
		dataMap.put("limit", pagesize);
		dataMap.put("Total", projectPage.getTotalCount());
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping("/detail/{projectId}")
	public com.xiaokaceng.openci.domain.Project getProjectDetail(@PathVariable long projectId) {
		return projectApplication.getDetail(projectId);
	}
	
	@ResponseBody
	@RequestMapping("/is-exist/{name}")
	public boolean isExistByName(@PathVariable String name) {
		return projectApplication.isExistByName(name);
	}
	
	@ResponseBody
	@RequestMapping("/remove/{projectId}")
	public boolean removeProject(@PathVariable long projectId) {
		return projectApplication.remove(projectId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/process/{projectId}", produces = "application/json;charset=UTF-8")
	public Map<String, Object> integrationProcess(@PathVariable long projectId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		com.xiaokaceng.openci.domain.Project project = projectApplication.getDetail(projectId);
		dataMap.put("result", project.getProjectStatus().equals(ProjectStatus.INTEGRATION_TOOL));
		dataMap.put("msg", projectApplication.integrationProcess(projectId));
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping("/again-integeration/{projectId}")
	public boolean againIntegeration(@PathVariable long projectId) {
		projectApplication.againIntegration(projectId);
		integrateProjectToTools(projectApplication.getDetail(projectId));
		return true;
	}
}
