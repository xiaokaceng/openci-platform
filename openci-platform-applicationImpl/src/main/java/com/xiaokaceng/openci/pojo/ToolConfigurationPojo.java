package com.xiaokaceng.openci.pojo;

import org.openkoala.opencis.api.AuthenticationStrategy;
import org.openkoala.opencis.api.CISClient;

import com.xiaokaceng.openci.CISClientNotInstanceException;
import com.xiaokaceng.openci.domain.ToolConfiguration;

public abstract class ToolConfigurationPojo {

	protected CISClient cisClient;

	protected AuthenticationStrategy authentication;

	protected boolean isInstance = false;

	public void instanceCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		createAuthentication(toolConfiguration, isIntegrationCas);
		createCISClient(toolConfiguration, isIntegrationCas);
	}

	private void createAuthentication(ToolConfiguration toolConfiguration, boolean isIntegrationCas) {
		createAuthenticationByDB(toolConfiguration, isIntegrationCas);
		createAuthenticationByCAS(toolConfiguration, isIntegrationCas);
	}

	protected abstract void createCISClient(ToolConfiguration toolConfiguration, boolean isIntegrationCas);

	protected abstract void createAuthenticationByDB(ToolConfiguration toolConfiguration, boolean isIntegrationCas);

	protected abstract void createAuthenticationByCAS(ToolConfiguration toolConfiguration, boolean isIntegrationCas);

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
