package com.xiaokaceng.openci.application.dto;

public class ScmConfig {

	private ScmType scmType;

	private String repositoryUrl;

	public ScmType getScmType() {
		return scmType;
	}

	public void setScmType(ScmType scmType) {
		this.scmType = scmType;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

}
