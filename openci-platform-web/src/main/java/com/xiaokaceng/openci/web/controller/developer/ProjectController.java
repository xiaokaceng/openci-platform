package com.xiaokaceng.openci.web.controller.developer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.koala.queryvo.TypeDef;
import org.openkoala.koala.util.ModuleDependencyUtils;
import org.openkoala.koala.widget.Module;
import org.openkoala.koala.widget.ModuleAdd;
import org.openkoala.koala.widget.Project;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.application.dto.ProjectDto;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	@Inject
	private ProjectApplication projectApplication;
	
	@ResponseBody
    @RequestMapping("/create")
	public ResultDto createProject(ProjectDto projectDto) {
		projectApplication.createProject(projectDto);
		return ResultDto.createSuccess();
	}
	
	@ResponseBody
    @RequestMapping("/get-functions")
	public Map<String, Map<String, String>> getFunctions(String moduleType) {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String,String>>();
		TypeDef typeDef = TypeDef.getInstance();
		result.put("functions", typeDef.getFunctions(moduleType));
		return result;
	}
	
	@ResponseBody
    @RequestMapping("/get-dependables")
	public List<Module> getDependables(Project project, String moduleType) {
		ModuleDependencyUtils moduleDependencyUtils = new ModuleDependencyUtils(project, moduleType);
		return moduleDependencyUtils.getCouldDependencyModules();
	}
	
	@ResponseBody
    @RequestMapping("/generate-default-modules")
	public ProjectDto generateDefaultModules(ProjectDto projectDto) {
		projectDto.getProjectForCreate().initSSJProject();
		return projectDto;
	}
	
	
//	@ResponseBody
//    @RequestMapping(value = "/abolish_developers", method = RequestMethod.POST, consumes = "application/json")
//	public ResultDto abolishDevelopers(@RequestBody Developer[] developers) {
//		developerApplication.abolishDevelopers(Arrays.asList(developers));
//		return ResultDto.createSuccess();
//	}
	
}
