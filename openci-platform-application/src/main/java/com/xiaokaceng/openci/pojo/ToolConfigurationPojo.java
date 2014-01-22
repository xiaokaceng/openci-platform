package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.CISClientNotInstanceException;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public abstract class ToolConfigurationPojo {

	protected CISClient cisClient;

	protected boolean isInstance = false;

	public abstract void createCISClient(ToolConfiguration toolConfiguration);

	public CISClient getCISClient() {
		if (cisClient == null) {
			throw new CISClientNotInstanceException();
		}
		return cisClient;
	}
	
	public void destory() {
		cisClient = null;
		isInstance = false;
	}

	public boolean isInstance() {
		return isInstance;
	}

}
