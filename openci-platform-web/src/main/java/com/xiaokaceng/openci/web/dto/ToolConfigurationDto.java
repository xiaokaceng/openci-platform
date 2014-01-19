package com.xiaokaceng.openci.web.dto;

import java.util.Date;

import com.xiaokaceng.openci.domain.ToolConfiguration;
import com.xiaokaceng.openci.domain.ToolType;

public class ToolConfigurationDto {

	private Long id;
	
	private String name;

	private String serviceUrl;

	private String username;

	private String password;

	private ToolType toolType;
	
	private boolean usable = false;
	
	private Date createDate;

	private int version;
	
	public ToolConfiguration toToolConfiguration() {
//		ToolConfiguration toolConfiguration = new ToolConfiguration(name, serviceUrl, username, password, toolType);
//		toolConfiguration.setId(id);
//		toolConfiguration.setVersion(version);
//		return toolConfiguration;
		return null;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
