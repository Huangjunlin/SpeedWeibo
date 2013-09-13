package com.king.sina.ui;


import com.king.sina.R;
import com.king.sina.logic.TokenKeeper;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity implements IWeiboActivity {

	private Button login_button;
	private Intent intent;
	private Weibo weibo;
	// 应用的key 请到官方申请正式的appkey替换APP_KEY
	public static final String APP_KEY = "3766042878";
	// 替换为开发者REDIRECT_URL
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	// 新支持scope 支持传入多个scope权限，用逗号分隔
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog";

	public static Oauth2AccessToken accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		intent = new Intent(this, AuthActivity.class);

		weibo = Weibo.getInstance(APP_KEY, REDIRECT_URL, SCOPE);
		LoginActivity.accessToken = TokenKeeper.readAccessToken(this);

		/*
		 * // 启动服务 Intent intent = new Intent(this, MainService.class);
		 * startService(intent);
		 */

		login_button = (Button) findViewById(R.id.login_bt);
		login_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(intent);
				weibo.anthorize(LoginActivity.this, new AuthDialogListener());

			}
		});

	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			String code = values.getString("code");
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
//			LoginActivity.accessToken = new Oauth2AccessToken();
			System.out.println("111111111");
//			if (LoginActivity.accessToken.isSessionValid()) {
				System.out.println("222222222");
				TokenKeeper.keepAccessToken(LoginActivity.this, accessToken);
				Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_LONG)
						.show();
//			}

		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth Cancel : ",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();

		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth Exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();

		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

	}

}
