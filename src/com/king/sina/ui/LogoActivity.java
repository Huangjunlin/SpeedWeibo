package com.king.sina.ui;

import com.king.sina.R;
import com.king.sina.R.id;
import com.king.sina.R.layout;
import com.king.sina.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity {
	ImageView ivLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);

		ivLogo = (ImageView) findViewById(R.id.iv_logo);
		// 动画渐变效果(从完全不透明到完全透明)
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(5000);
		ivLogo.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(LogoActivity.this,
						LoginActivity.class);
				startActivity(intent);

			}
		});

	}


}
