package com.xiaokaceng.openci.web.controller.toolconfiguration;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.Developer;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/toolconfiguration")
public class ToolConfigurationController extends BaseController {

	@Inject
	private ToolConfigurationApplication toolConfigurationApplication;
	
	@ResponseBody
    @RequestMapping("/create")
	public ResultDto createToolConfiguration(ToolConfiguration toolConfiguration) {
		toolConfigurationApplication.createConfiguration(toolConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
    @RequestMapping("/update")
	public ResultDto updateToolConfiguration(ToolConfiguration toolConfiguration) {
		toolConfigurationApplication.updateConfiguration(toolConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
    @RequestMapping("/usable/{toolConfigurationId}")
	public ResultDto setToolUsable(@PathVariable long toolConfigurationId) {
		toolConfigurationApplication.updateConfiguration(toolConfiguration);
		return ResultDto.createSuccess();
	}
	
}
