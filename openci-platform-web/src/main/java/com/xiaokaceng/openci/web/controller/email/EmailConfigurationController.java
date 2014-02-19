package com.xiaokaceng.openci.web.controller.email;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.application.EmailConfigurationApplication;
import com.xiaokaceng.openci.domain.EmailConfiguration;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/emailconfiguration")
public class EmailConfigurationController extends BaseController {

	@Inject
	private EmailConfigurationApplication emailConfigurationApplication;
	
	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createEmailConfiguration(EmailConfiguration emailConfiguration) {
		emailConfigurationApplication.createEmailConfiguration(emailConfiguration);
		return ResultDto.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public ResultDto updateEmailConfiguration(EmailConfiguration emailConfiguration) {
		emailConfigurationApplication.updateEmailConfiguration(emailConfiguration);
		return ResultDto.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/remove/{emailConfigurationId}")
	public ResultDto removeEmailConfiguration(long emailConfigurationId) {
		emailConfigurationApplication.removeEmailConfiguration(emailConfigurationId);
		return ResultDto.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/setusable/{emailConfigurationId}")
	public ResultDto setUsable(long emailConfigurationId) {
		ResultDto resultDto = new ResultDto();
		resultDto.setResult(emailConfigurationApplication.setUsable(emailConfigurationId));
		return resultDto;
	}
	
	@ResponseBody
	@RequestMapping("/setdefault/{emailConfigurationId}")
	public ResultDto setDefault(long emailConfigurationId) {
		emailConfigurationApplication.setDefault(emailConfigurationId);
		return ResultDto.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/findall")
	public List<EmailConfiguration> findAll() {
		return emailConfigurationApplication.findAll();
	}
	
}
