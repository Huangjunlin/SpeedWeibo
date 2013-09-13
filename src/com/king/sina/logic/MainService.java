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
 * @author ��ɳ������ �����������ҵ���߼���������Ϣ������UI
 */
public class MainService extends Service implements Runnable {

	// ����һ������ļ��϶���
	private static Queue<Task> tasks = new LinkedList<Task>();
	// �Ƿ������߳�
	private boolean isRun;
	// ����һ��activity�ļ��ϣ����ô����µ�activity
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Task.WEIBO_LOGIN:// �û���¼������UI
				IWeiboActivity activity = (IWeiboActivity) getActivityByName("LoginActivity");
				activity.refresh(msg.obj);
				break;

			default:
				break;
			}

		}

		/**
		 * ����activity��name��ȡactivity����
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
	 * ���Activity��������
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		appActivities.add(activity);
	}
	

	/**
	 * ������񵽶�����
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
		// ��ͣ�ļ�������
		while (isRun) {
			Task task = null;
			if (!tasks.isEmpty()) {
				task = tasks.poll();// ִ���������Ӷ������Ƴ�
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
	 * ����ҵ���߼������ظ�����Ϣ�����߳�
	 */
	private void dotask(Task t) {

		Message msg = handler.obtainMessage();
		msg.what = t.getTaskId();

		switch (t.getTaskId()) {
		case Task.WEIBO_LOGIN:
			System.out.println("doTask......user login");
			msg.obj = "��¼�ɹ�";
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
