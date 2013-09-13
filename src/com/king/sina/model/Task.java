package com.king.sina.model;

import java.util.Map;


public class Task {

	// 任务ID
	private int taskId;
	// 参数
	private Map<String, Object> taskParams;
	//登录
	public static final int WEIBO_LOGIN = 1;
	

	public Task(int taskId, Map<String, Object> taskParams) {
		this.taskId = taskId;
		this.taskParams = taskParams;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}
	
	

}
