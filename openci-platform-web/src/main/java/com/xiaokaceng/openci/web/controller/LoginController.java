package com.xiaokaceng.openci.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaokaceng.openci.web.dto.ResultMsgDto;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	public ResultMsgDto login(String username, String password) {
		if (username.equals("admin") && password.equals("admin")) {
			return ResultMsgDto.createSuccess(null);
		}
		return ResultMsgDto.createFailure("用户名或密码错误");
	}
	
}
