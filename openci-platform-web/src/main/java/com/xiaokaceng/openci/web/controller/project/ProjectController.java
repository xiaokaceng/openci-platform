package com.xiaokaceng.openci.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.koala.queryvo.TypeDef;
import org.openkoala.koala.util.ModuleDependencyUtils;
import org.openkoala.koala.widget.Module;
import org.openkoala.koala.widget.Project;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.dto.ProjectDto;
import com.xiaokaceng.openci.dto.ProjectQueryDto;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	@Inject
	private ProjectApplication projectApplication;

	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createProject(@RequestBody ProjectDto projectDto) {
		projectApplication.createProject(projectDto);
		return ResultDto.createSuccess();
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
	public Map<String, Object> pagingQuery() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		ProjectQueryDto projectQueryDto = new ProjectQueryDto();
		projectQueryDto.setName("dd");
		projectQueryDto.setEndDate(new Date());
		Page<com.xiaokaceng.openci.domain.Project> projectPage = projectApplication.pagingQueryProject(projectQueryDto, 1, 10);

		dataMap.put("Rows", projectPage.getResult());
		dataMap.put("Total", projectPage.getTotalCount());
		return dataMap;
	}

}
