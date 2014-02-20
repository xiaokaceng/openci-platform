package com.xiaokaceng.openci.executor;

import javax.inject.Inject;

import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.email.ProjectEmail;

public class ProjectEmailSendExecutor {

	@Inject
	private TaskExecutor taskExecutor;

	public void execute(Project project) {
		taskExecutor.execute(new EmailSendTask(project));
	}

	private class EmailSendTask implements Runnable {

		private Project project;
		
		public EmailSendTask(Project project) {
			this.project = project;
		}
		
		public void run() {
			new ProjectEmail(project).send();
		}
		
	}
}
