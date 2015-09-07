package com.example.haveimgfun;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class welcomeActivity extends Activity{
	private Button loginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		loginBtn = (Button)findViewById(R.id.main_login_btn);
		loginBtn.setOnClickListener(new ButtonListener());
	}

	class ButtonListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {			
			int btnId = v.getId();
			switch (btnId) {//判断点击的按钮
			case R.id.main_login_btn://登录按钮
				Intent intent = new Intent(welcomeActivity.this, logInActivity.class);
				startActivity(intent);//启动对应的Activity  此处为硬编码  不介意这么写  写成action形式 最好
				break;
			default:
				break;
			
			}
			
		}
		
	}
}
