package com.xiaokaceng.openci.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("svn")
public class SvnConfiguration extends ToolConfiguration {

	private static final long serialVersionUID = 2373533363609866542L;

	@Column(name = "save_path")
	private String savePath;

	public SvnConfiguration(String name, String serviceUrl, String username, String password, String savePath) {
		super(name, serviceUrl, username, password);
		this.savePath = savePath;
	}

	public SvnConfiguration() {
		super();
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

}