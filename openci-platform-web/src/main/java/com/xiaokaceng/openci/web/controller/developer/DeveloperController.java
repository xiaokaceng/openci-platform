package com.xiaokaceng.openci.web.controller.developer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokaceng.openci.domain.Developer;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;

@Controller
@RequestMapping("/developer")
public class DeveloperController extends BaseController {

	@ResponseBody
    @RequestMapping("/create")
	public ResultDto createDeveloper(Developer developer) {
		
		
		
		return ResultDto.createSuccess();
	}
	
}
