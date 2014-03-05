package com.xiaokaceng.openci.application;

import com.dayatang.querychannel.support.Page;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.Tool;
import com.xiaokaceng.openci.dto.ProjectDto;
import com.xiaokaceng.openci.dto.ProjectQueryDto;

public interface ProjectApplication {

	/**
	 * 创建项目
	 * 
	 * @param projectDto
	 */
	void createProject(ProjectDto projectDto);

	/**
	 * 分页查找项目
	 * 
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<Project> pagingQueryProject(ProjectQueryDto projectQueryDto, int currentPage, int pagesize);

	/**
	 * 获取项目详情
	 * 
	 * @param projectId
	 * @return
	 */
	Project getDetail(long projectId);

	/**
	 * 根据项目名检测项目是否存在
	 * 
	 * @param name
	 * @return
	 */
	boolean isExistByName(String name);

	/**
	 * 添加整合工具
	 * 
	 * @param project
	 * @param tool
	 */
	void addIntegrationTool(Project project, Tool tool);

	/**
	 * 更新整合工具状态
	 * 
	 * @param toolId
	 */
	void updateIntegrationToolStatus(long toolId);

	/**
	 * 删除项目
	 * 
	 * @param projectId
	 */
	boolean remove(long projectId);

	/**
	 * 获取整合项目进展
	 * 
	 * @param projectId
	 * @return
	 */
	String integrationProcess(long projectId);

	/**
	 * 对项目集成工具中失败的再一次发起整合
	 * 
	 * @param projectId
	 */
	void againIntegration(long projectId);
}