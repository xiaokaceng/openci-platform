package com.xiaokaceng.openci.application;

import java.util.Collection;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.domain.Developer;

public interface DeveloperApplication {

	/**
	 * 分页查询开发者信息
	 * @param example
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<Developer> pagingQeuryDevelopers(Developer example, int currentPage, int pagesize);
	
	/**
	 * 创建一个开发者
	 * @param developer
	 */
	void createDeveloper(Developer developer);
	
	/**
	 * 修改一个开发者信息
	 * @param developer
	 */
	void updateDeveloperInfo(Developer developer);
	
	/**
	 * 撤销一个开发者
	 * @param developer
	 */
	void abolishDeveloper(Developer developer);
	
	/**
	 * 撤销一批开发者
	 * @param developers
	 */
	void abolishDevelopers(Collection<Developer> developers);
	
}
