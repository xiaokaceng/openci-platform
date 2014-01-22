package com.xiaokaceng.openci.dto;

import com.xiaokaceng.openci.domain.ToolType;

public class ToolConfigurationDto {

	private Long id;
	
	private ToolType toolType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}
	
}
