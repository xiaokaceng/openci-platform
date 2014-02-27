package com.xiaokaceng.openci.web.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.application.RoleApplication;
import com.xiaokaceng.openci.domain.Role;
import com.xiaokaceng.openci.web.controller.BaseController;
import com.xiaokaceng.openci.web.dto.ResultDto;
import com.xiaokaceng.openci.web.dto.ResultMsgDto;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Inject
	private RoleApplication roleApplication;

	@ResponseBody
	@RequestMapping("/create")
	public ResultDto createRole(Role role) {
		if (roleApplication.checkNameIsExist(role.getName())) {
			return ResultMsgDto.createFailure("角色名已存在!");
		}
		roleApplication.createRole(role);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResultDto updateRole(Role role) {
		roleApplication.updateRole(role);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/abolish")
	public ResultDto abolishRole(Role role) {
		roleApplication.abolishRole(role);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping(value = "/abolishs", method = RequestMethod.POST, consumes = "application/json")
	public ResultDto abolishRole(@RequestBody Role[] roles) {
		roleApplication.abolishRole(roles);
		return ResultDto.createSuccess();
	}

	@ResponseBody
	@RequestMapping("/pagingquery")
	public Map<String, Object> pagingQuery(int page, int pagesize) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page<Role> rolePage = roleApplication.pagingQeuryRoles(page, pagesize);

		dataMap.put("Rows", rolePage.getResult());
		dataMap.put("start", page * pagesize - pagesize);
		dataMap.put("limit", pagesize);
		dataMap.put("Total", rolePage.getTotalCount());
		return dataMap;
	}

	@ResponseBody
	@RequestMapping("/findall")
	public List<Role> findAll() {
		return roleApplication.findAll();
	}

}
