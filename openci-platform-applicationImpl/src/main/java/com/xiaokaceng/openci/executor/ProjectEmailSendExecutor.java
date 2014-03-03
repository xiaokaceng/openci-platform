package com.xiaokaceng.openci.executor;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.application.OpenciApplication;
import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.email.ProjectEmail;
import com.xiaokaceng.openci.pojo.MailSendResult;

@Named
public class ProjectEmailSendExecutor {

	@Inject
	private TaskExecutor taskExecutor;
	
	@Inject
	private OpenciApplication openciApplication;

	public void execute(Project project) {
		for (ProjectDeveloper each : project.getDevelopers()) {
			taskExecutor.execute(new EmailSendTask(project, each));
		}
	}

	private class EmailSendTask implements Runnable {

		private Project project;

		private ProjectDeveloper projectDeveloper;

		public EmailSendTask(Project project, ProjectDeveloper projectDeveloper) {
			this.project = project;
			this.projectDeveloper = projectDeveloper;
		}

		public void run() {
			ProjectEmail projectEmail = new ProjectEmail(project, projectDeveloper.getDeveloper().getEmail());
			MailSendResult mailSendResult = projectEmail.send();
			projectDeveloper.setNoticeSuccess(mailSendResult.isSuccess());
			projectDeveloper.setRecord(mailSendResult.getCauseOfFailure());
			openciApplication.saveEntity(projectDeveloper);
		}

	}
}
