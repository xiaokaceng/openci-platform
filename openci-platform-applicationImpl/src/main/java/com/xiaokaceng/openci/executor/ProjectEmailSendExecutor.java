package com.xiaokaceng.openci.executor;

import javax.inject.Inject;

import org.springframework.core.task.TaskExecutor;

import com.xiaokaceng.openci.domain.Project;

public class ProjectEmailSendExecutor {

	@Inject
	private TaskExecutor taskExecutor;

	public void execute(Project project) {
		
	}
	

	private class EmailSendTask implements Runnable {

		
		
		public EmailSendTask() {
			
		}
		
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
