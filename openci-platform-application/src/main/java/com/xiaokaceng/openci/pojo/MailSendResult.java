package com.xiaokaceng.openci.pojo;

public class MailSendResult {

	private boolean isSuccess;
	
	private String causeOfFailure;

	private MailSendResult(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	private MailSendResult(boolean isSuccess, String causeOfFailure) {
		this.isSuccess = isSuccess;
		this.causeOfFailure = causeOfFailure;
	}
	
	public static MailSendResult createSuccess() {
		return new MailSendResult(true);
	}
	
	public static MailSendResult createFailure(String causeOfFailure) {
		return new MailSendResult(false, causeOfFailure);
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCauseOfFailure() {
		return causeOfFailure;
	}

	public void setCauseOfFailure(String causeOfFailure) {
		this.causeOfFailure = causeOfFailure;
	}
	
	
	
}
