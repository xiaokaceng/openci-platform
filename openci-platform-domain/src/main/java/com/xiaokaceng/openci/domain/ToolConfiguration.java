package com.xiaokaceng.openci.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;

@Entity
@Table(name = "tool_configurations")
public class ToolConfiguration extends AbstractEntity {

	private static final long serialVersionUID = -7992490907551882249L;

	private String name;
	
	@Column(name = "service_url")
	private String serviceUrl;
	
	private String username;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tool_type")
	private ToolType toolType;
	
	private boolean usable = false;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	private Date createDate;
	
	public ToolConfiguration(String name, String serviceUrl, String username, String password, ToolType toolType) {
		this.name = name;
		this.serviceUrl = serviceUrl;
		this.username = username;
		this.password = password;
		this.toolType = toolType;
		this.createDate = new Date();
	}
	
	public void usabled() {
		usable = true;
		save();
	}
	
	public void unusabled() {
		usable = false;
		save();
	}

	public String getName() {
		return name;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public boolean isUsable() {
		return usable;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ToolConfiguration)) {
			return false;
		}
		ToolConfiguration that = (ToolConfiguration) other;
		return new EqualsBuilder().append(getName(), that.getName()).append(getServiceUrl(), that.getServiceUrl()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getServiceUrl()).hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
