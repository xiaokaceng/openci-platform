package com.xiaokaceng.openci.executor;

import javax.inject.Inject;

import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.domain.Project;
import com.xiaokaceng.openci.domain.ProjectDeveloper;
import com.xiaokaceng.openci.email.ProjectEmail;

public class ProjectEmailSendExecutor {

	@Inject
	private TaskExecutor taskExecutor;

	public void execute(Project project) {
		for (ProjectDeveloper each : project.getDevelopers()) {
			taskExecutor.execute(new EmailSendTask(project, each.getDeveloper().getEmail()));
		}
	}

	private class EmailSendTask implements Runnable {

		private Project project;

		private String receiver;

		public EmailSendTask(Project project, String receiver) {
			this.project = project;
			this.receiver = receiver;
		}

		public void run() {
			new ProjectEmail(project, receiver).send();
		}

	}
}
