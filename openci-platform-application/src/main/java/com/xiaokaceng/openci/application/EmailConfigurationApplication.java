package com.xiaokaceng.openci.application;

import java.util.List;

import com.xiaokaceng.openci.domain.EmailConfiguration;

public interface EmailConfigurationApplication {

	/**
	 * 创建邮箱配置
	 * 
	 * @param emailConfiguration
	 */
	void createEmailConfiguration(EmailConfiguration emailConfiguration);

	/**
	 * 更新邮箱配置
	 * 
	 * @param emailConfiguration
	 */
	void updateEmailConfiguration(EmailConfiguration emailConfiguration);

	/**
	 * 删除邮箱配置
	 * 
	 * @param emailConfigurationId
	 */
	void removeEmailConfiguration(long emailConfigurationId);

	/**
	 * 设置邮箱可用
	 * 
	 * @param emailConfigurationId
	 */
	boolean setUsable(long emailConfigurationId);

	/**
	 * 设置邮箱为默认
	 * 
	 * @param emailConfigurationId
	 */
	void setDefault(long emailConfigurationId);

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	List<EmailConfiguration> findAll();

	/**
	 * 获取默认邮箱配置
	 * 
	 * @return
	 */
	EmailConfiguration getDefault();
}
