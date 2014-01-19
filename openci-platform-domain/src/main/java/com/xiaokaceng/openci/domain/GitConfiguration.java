package com.xiaokaceng.openci.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("git")
public class GitConfiguration extends ToolConfiguration {

	private static final long serialVersionUID = 2960979556527118613L;

	private String token;

	public GitConfiguration(String name, String serviceUrl, String username, String password, String token) {
		super(name, serviceUrl, username, password);
		this.token = token;
	}

	public GitConfiguration() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
