package com.xiaokaceng.openci.web.controller.toolconfiguration;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/toolconfiguration")
public class ToolConfigurationController extends ToolConfigurationBaseController {

	@ResponseBody
	@RequestMapping("/unusable/{toolConfigurationId}")
	public ResultDto setToolUnUsable(@PathVariable long toolConfigurationId) {
		ToolConfiguration toolConfiguration = ToolConfiguration.get(ToolConfiguration.class, toolConfigurationId);
		if (toolConfiguration == null) {
			return ResultDto.createFailure();
		}
		toolConfigurationApplication.setToolUnusabled(toolConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/get-all-usable")
	public List<ToolConfiguration> getAllUsable() {
		return toolConfigurationApplication.getAllUsable();
	}

	@ResponseBody
	@RequestMapping("/can-connect/{toolConfigurationId}")
	public ResultDto canConnect(@PathVariable long toolConfigurationId) {
		ToolConfiguration toolConfiguration = ToolConfiguration.get(ToolConfiguration.class, toolConfigurationId);
		if (toolConfiguration == null) {
			return ResultDto.createFailure();
		}
		boolean result = toolConfigurationApplication.canConnect(toolConfiguration);
		return new ResultDto(result);
	}

}
