package com.xiaokaceng.openci.web.controller.toolconfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.application.ToolConfigurationApplication;
import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.ToolType;
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
		ToolConfiguration toolConfiguration = ToolConfiguration.get(ToolConfiguration.class, toolConfigurationId);
		if (toolConfiguration == null) {
			return ResultDto.createFailure();
		}
		toolConfigurationApplication.setToolUsabled(toolConfiguration);
		return ResultDto.createSuccess();
	}
	
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
    @RequestMapping("/pagingquery")
	public Map<String, Object> pagingQuery(int page, int pagesize) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page<ToolConfiguration> toolConfigurationPage  = toolConfigurationApplication.pagingQeuryToolConfigurations(page, pagesize);
		dataMap.put("Rows", toolConfigurationPage.getResult());
		dataMap.put("start", page * pagesize - pagesize);
		dataMap.put("limit", pagesize);
		dataMap.put("Total", toolConfigurationPage.getTotalCount());
		return dataMap;
	}
	
	@ResponseBody
    @RequestMapping("/get-tool-type")
	public Map<String, Object> getToolType() {
		Map<String, Object> toolTypes = new HashMap<String, Object>();
		for (ToolType each : ToolType.values()) {
			toolTypes.put(each.toString(), each);
		}
		return toolTypes;
	}

}
