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
			switch (btnId) {//�жϵ���İ�ť
			case R.id.main_login_btn://��¼��ť
				Intent intent = new Intent(welcomeActivity.this, logInActivity.class);
				startActivity(intent);//������Ӧ��Activity  �˴�ΪӲ����  ��������ôд  д��action��ʽ ���
				break;
			default:
				break;
			
			}
			
		}
		
	}
}
