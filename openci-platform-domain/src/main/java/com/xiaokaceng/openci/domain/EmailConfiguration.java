package com.xiaokaceng.openci.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

@Entity
@Table(name = "email_configurations")
public class EmailConfiguration extends AbstractEntity {

	private static final long serialVersionUID = 5592544471956665586L;

	private String username;

	private String password;

	@Column(name = "smtp_address")
	private String smtpAddress;

	@Column(name = "smtp_port")
	private int smtpPort;

	@Column(name = "is_usable")
	private boolean isUsable = false;

	@Column(name = "is_default")
	private boolean isDefault = false;

	private String name;

	public EmailConfiguration(String username, String password, String smtpAddress, int smtpPort, String name) {
		this.username = username;
		this.password = password;
		this.smtpAddress = smtpAddress;
		this.smtpPort = smtpPort;
		this.name = name;
	}

	EmailConfiguration() {
	}

	public static EmailConfiguration getDefault() {
		return getRepository().getSingleResult(QuerySettings.create(EmailConfiguration.class).eq("isDefault", true));
	}
	
	public void setDefault() {
		cancelDefaultEmailConfiguration();
		isDefault = true;
		save();
	}
	
	private void cancelDefaultEmailConfiguration() {
		EmailConfiguration emailConfiguration = EmailConfiguration.getDefault();
		if (emailConfiguration != null) {
			emailConfiguration.setDefault(false);
			emailConfiguration.save();
		}
	}

	public void setUsable() {
		isUsable = true;
		save();
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

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public boolean isUsable() {
		return isUsable;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(username).append(password).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmailConfiguration)) {
			return false;
		}
		EmailConfiguration castOther = (EmailConfiguration) other;
		return new EqualsBuilder().append(getUsername(), castOther.getUsername()).append(getPassword(), castOther.getPassword()).isEquals();
	}

	@Override
	public String toString() {
		return getUsername() + ":" + getPassword();
	}

}
