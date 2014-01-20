package com.xiaokaceng.openci.web.controller.toolconfiguration;

import java.util.HashMap;
import java.util.Map;

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

	@ResponseBody
	@RequestMapping("/get")
	public Map<String, Object> getInstance() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CasUserConfiguration casUserConfiguration = toolConfigurationApplication.getUniqueInstance();
		if (casUserConfiguration == null) {
			dataMap.put("result", "false");
		} else {
			dataMap.put("result", "true");
			dataMap.put("data", casUserConfiguration);
		}
		return dataMap;
	}
}
