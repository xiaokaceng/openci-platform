package com.xiaokaceng.openci.application;

import java.util.List;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public interface ToolConfigurationApplication {

	/**
	 * 创建工具配置
	 * @param toolConfiguration
	 */
	void createConfiguration(ToolConfiguration toolConfiguration);
	
	/**
	 * 更新工具配置
	 * @param toolConfiguration
	 */
	void updateConfiguration(ToolConfiguration toolConfiguration);
	
	/**
	 * 设置工具可用
	 * @param toolConfiguration
	 */
	void setToolUsabled(ToolConfiguration toolConfiguration);
	
	/**
	 * 设置工具不可用
	 * @param toolConfiguration
	 */
	void setToolUnusabled(ToolConfiguration toolConfiguration);
	
	/**
	 * 测试工具是否可用
	 * @param toolConfiguration
	 * @return
	 */
	boolean canConnect(ToolConfiguration toolConfiguration);
	
	/**
	 * 获取所有可用的工具配置列表
	 * @return
	 */
	List<ToolConfiguration> getAllUsable();
	
	/**
	 * 分页查询工具配置信息
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<ToolConfiguration> pagingQeuryToolConfigurations(int currentPage, int pagesize);
}