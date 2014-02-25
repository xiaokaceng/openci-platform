package com.xiaokaceng.openci.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import com.xiaokaceng.openci.DefaultEmailNotSettingsException;
import com.xiaokaceng.openci.domain.EmailConfiguration;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.domain.Tool;

public class ProjectEmail {

	private static final String CHAREST = "UTF-8";

	private Project project;
	
	private String receiver;

	public ProjectEmail(Project project, String receiver) {
		this.project = project;
		this.receiver = receiver;
	}

	public void send() {
		String htmlContent = createHtmlEmailContent();
		try {
			HtmlEmail htmlEmail = initHtmlEmail();
			htmlEmail.addTo(receiver);
			htmlEmail.setCharset(CHAREST);
			htmlEmail.setSubject("OPENCI-PLATFORM项目通知");
			htmlEmail.setHtmlMsg(htmlContent);
			htmlEmail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createHtmlEmailContent() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("项目名:").append(project.getName()).append("<br>");
		buffer.append("创建时间:").append(project.getCreateDate()).append("<br>");
		buffer.append("项目成员:").append("<br>");
		for (ProjectDeveloper each : project.getDevelopers()) {
			buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(each.getDeveloper().getName()).append("<br>");
		}
		buffer.append("集成工具:").append("<br>");
		for (Tool each : project.getTools()) {
			buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(each.getToolConfiguration().getName()).append("&nbsp;&nbsp;访问地址:").append(each.getToolRequestAddress()).append("<br>");
		}
		return buffer.toString();
	}

	private HtmlEmail initHtmlEmail() {
		EmailConfiguration emailConfiguration = getSystemDefaultConfiguration();
		HtmlEmail email = new HtmlEmail();
		email.setHostName(emailConfiguration.getSmtpAddress());
		email.setSmtpPort(emailConfiguration.getSmtpPort());
		email.setAuthenticator(new DefaultAuthenticator(emailConfiguration.getUsername(), emailConfiguration.getPassword()));
		email.setSSLOnConnect(false);
		try {
			if (emailConfiguration.getName() != null) {
				email.setFrom(emailConfiguration.getUsername(), emailConfiguration.getName());
			} else {
				email.setFrom(emailConfiguration.getUsername());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

	private EmailConfiguration getSystemDefaultConfiguration() {
		EmailConfiguration emailConfiguration = EmailConfiguration.getDefault();
		if (emailConfiguration != null) {
			return emailConfiguration;
		}
		throw new DefaultEmailNotSettingsException();
	}

}
