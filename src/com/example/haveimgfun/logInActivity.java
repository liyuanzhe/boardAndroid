package com.example.haveimgfun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class logInActivity extends Activity{
	
	private static final String STRNAME = "liyuanzhe";
	private static final String STRCODE =  "12345678";
	private EditText loginName,loginCode;
	private Button loginButton;
	private TextView loginText;
	private CheckBox rememberPass;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		loginName = (EditText)findViewById(R.id.login_user_edit);
		loginCode = (EditText)findViewById(R.id.login_passwd_edit);
		loginButton = (Button)findViewById(R.id.login_login_btn);
		rememberPass = (CheckBox)findViewById(R.id.remember_pass);
		
		boolean isRemember = pref.getBoolean("remember_password", false);
		if(isRemember){
			String strAccount = pref.getString("account","");
			String strPassword = pref.getString("password", "");
			loginName.setText(strAccount);
			loginCode.setText(strPassword);
			rememberPass.setChecked(true);
		}
		
		loginButton.setOnClickListener(new ButtonListener());
		
	}

	class ButtonListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			String strAccount = loginName.getText().toString();
			String strPassword = loginCode.getText().toString();
			
			if(strAccount.equals(STRNAME)&&strPassword.equals(STRCODE)){
				
				//save account and password
				editor = pref.edit();
				if(rememberPass.isChecked()){
					editor.putBoolean("remember_password", true);
					editor.putString("account", strAccount);
					editor.putString("password", strPassword);
				}else
					editor.clear();
				editor.commit();
				
				Intent intent = new Intent();
				intent.setClass(logInActivity.this, MainActivity.class);
				startActivity(intent);
			}else{
				loginText.setText("’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
			}
			
		}
		
	}
}
