package com.king.sina.ui;

/**
 * Activity接口
 * @author Administrator
 *
 */
public interface IWeiboActivity {
	
	/*
	 * 初始化数据
	 */
	void init();
	
	/*
	 * 刷新UI
	 */
	void refresh(Object... params);
}
