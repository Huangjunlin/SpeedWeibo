package com.king.sina.ui;

import com.king.sina.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class AuthActivity extends Activity {
	
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.auth);
		
//		View dialogView = View.inflate(this, R.layout.authorize_dialog, null);
		dialog = new Dialog(this,R.style.Dialog);
		dialog.setContentView(R.layout.authorize_dialog);
		dialog.getWindow().getAttributes().width=380;
		dialog.show();
	}

}
