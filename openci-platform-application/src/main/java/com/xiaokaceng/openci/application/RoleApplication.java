package com.xiaokaceng.openci.application;

import java.util.List;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.domain.Role;

public interface RoleApplication {

	/**
	 * 创建角色
	 * 
	 * @param role
	 */
	void createRole(Role role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 */
	void updateRole(Role role);

	/**
	 * 废除角色
	 * 
	 * @param role
	 */
	void abolishRole(Role role);

	/**
	 * 分页查询角色信息
	 * 
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<Role> pagingQeuryRoles(int currentPage, int pagesize);

	/**
	 * 废除角色
	 * 
	 * @param role
	 */
	void abolishRole(Role[] roles);

	/**
	 * 查找所有有效的角色
	 * 
	 * @return
	 */
	List<Role> findAll();
}
