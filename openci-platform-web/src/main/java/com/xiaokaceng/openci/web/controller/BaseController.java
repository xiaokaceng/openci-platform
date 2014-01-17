package com.xiaokaceng.openci.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

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
	
	//数据绑定  
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。  
    }   
}
