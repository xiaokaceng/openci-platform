package com.xiaokaceng.openci.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.application.ProjectApplication;

public class BaseController {

	protected static final String USERNAME = "USERNAME";
	
	@Inject
	protected OpenciApplication openciApplication;
	
	@Inject
	protected ProjectApplication projectApplication;
	
	protected void putDataToSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}
	
	protected Object getDataForSession(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}
	
	protected boolean isNotNull(Object object) {
		return object != null;
	}
	
	protected boolean isNull(Object object) {
		return !isNotNull(object);
	}
}
