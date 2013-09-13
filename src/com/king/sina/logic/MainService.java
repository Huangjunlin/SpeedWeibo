package com.king.sina.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.king.sina.model.Task;
import com.king.sina.ui.IWeiboActivity;

/**
 * 
 * @author 完成程序调度 接收任务，完成业务逻辑，返回信息，更新UI
 */
public class MainService extends Service implements Runnable {

	// 定义一个任务的集合队列
	private static Queue<Task> tasks = new LinkedList<Task>();
	// 是否运行线程
	private boolean isRun;
	// 定义一个activity的集合，放置待更新的activity
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Task.WEIBO_LOGIN:// 用户登录，更新UI
				IWeiboActivity activity = (IWeiboActivity) getActivityByName("LoginActivity");
				activity.refresh(msg.obj);
				break;

			default:
				break;
			}

		}

		/**
		 * 根据activity的name获取activity对象
		 * @param name
		 * @return
		 */
		private Activity getActivityByName(String name) {
			if (!appActivities.isEmpty()) {
				for (Activity activity : appActivities) {
					if (null != activity) {
						if (activity.getClass().getName().indexOf(name) > 0) {
							return activity;
						}
					}
				}
			}
			
			return null;
		};
	};

	
	/**
	 * 添加Activity到集合中
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		appActivities.add(activity);
	}
	

	/**
	 * 添加任务到队列中
	 * 
	 * @param t
	 */
	public static void newTask(Task t) {
		tasks.add(t);
	}

	@Override
	public void onCreate() {
		isRun = true;
		Thread thread = new Thread(this);
		thread.start();
		super.onCreate();
	}

	@Override
	public void run() {
		// 不停的监听任务
		while (isRun) {
			Task task = null;
			if (!tasks.isEmpty()) {
				task = tasks.poll();// 执行完任务后从队列中移除
				if (null != task) {
					dotask(task);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * 处理业务逻辑，发回更新消息给主线程
	 */
	private void dotask(Task t) {

		Message msg = handler.obtainMessage();
		msg.what = t.getTaskId();

		switch (t.getTaskId()) {
		case Task.WEIBO_LOGIN:
			System.out.println("doTask......user login");
			msg.obj = "登录成功";
			break;

		default:
			break;
		}
		handler.sendMessage(msg);

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
