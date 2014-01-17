package com.xiaokaceng.openci.web.controller.developer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.application.DeveloperApplication;
import com.xiaokaceng.openci.application.ProjectApplication;
import com.xiaokaceng.openci.application.dto.ProjectDto;
import com.xiaokaceng.openci.domain.Developer;
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
	
	
	
//	@ResponseBody
//    @RequestMapping(value = "/abolish_developers", method = RequestMethod.POST, consumes = "application/json")
//	public ResultDto abolishDevelopers(@RequestBody Developer[] developers) {
//		developerApplication.abolishDevelopers(Arrays.asList(developers));
//		return ResultDto.createSuccess();
//	}
	
}
