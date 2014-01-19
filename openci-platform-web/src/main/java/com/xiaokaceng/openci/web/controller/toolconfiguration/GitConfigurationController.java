package com.xiaokaceng.openci.web.controller.toolconfiguration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.domain.GitConfiguration;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/gitconfiguration")
public class GitConfigurationController extends ToolConfigurationBaseController {

	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createGitConfiguration(GitConfiguration gitConfiguration) {
		toolConfigurationApplication.createConfiguration(gitConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResultDto updateGitConfiguration(GitConfiguration gitConfiguration) {
		toolConfigurationApplication.updateConfiguration(gitConfiguration);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/pagingquery")
	public Map<String, Object> pagingQuery(int page, int pagesize) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page<GitConfiguration> toolConfigurationPage = toolConfigurationApplication.pagingQeuryGitConfigurations(page, pagesize);
		dataMap.put("Rows", toolConfigurationPage.getResult());
		dataMap.put("start", page * pagesize - pagesize);
		dataMap.put("limit", pagesize);
		dataMap.put("Total", toolConfigurationPage.getTotalCount());
		return dataMap;
	}
	
}
