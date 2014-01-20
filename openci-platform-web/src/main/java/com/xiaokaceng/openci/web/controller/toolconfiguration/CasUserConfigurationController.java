package com.xiaokaceng.openci.web.controller.toolconfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.domain.CasUserConfiguration;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/casuserconfiguration")
public class CasUserConfigurationController extends ToolConfigurationBaseController {

	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createCasUserConfiguration(CasUserConfiguration casUserConfiguration) {
		toolConfigurationApplication.createConfiguration(casUserConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResultDto updateCasUserConfiguration(CasUserConfiguration casUserConfiguration) {
		toolConfigurationApplication.updateConfiguration(casUserConfiguration);
		return ResultDto.createSuccess();
	}

}
