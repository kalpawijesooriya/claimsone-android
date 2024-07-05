package com.irononetech.android.logincomponent.view;

import android.app.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class LoginActivity extends Activity {
	Logger LOG = LoggerFactory.getLogger(LoginActivity.class);
    EditText username;
    EditText password;
    TextView versionView;
    Button loginButton;
    UserObject userObject;
    InputMethodManager imm;
    public ProgressDialog dialog;
    
    public synchronized ProgressDialog getDialog() {
		return dialog;
	}

	public Toast msg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	LOG.debug("ENTRY onCreate");
        try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
			username = (EditText) findViewById(R.id.Username);
			password = (EditText) findViewById(R.id.Password);
			loginButton = (Button) findViewById(R.id.Loginbutton);
			versionView = (TextView) findViewById(R.id.textViewVersion);
			userObject = new UserObject();
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			
			String app_ver = "";
			try
			{
				app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			}
			
			catch (Exception e)
			{
				 LOG.error("onCreate:10196");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			versionView.setText("V " + app_ver);
			
			password.setOnEditorActionListener(new OnEditorActionListener() {
			    @Override
			    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			        if (actionId == EditorInfo.IME_ACTION_DONE) {
			        	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			        	userLogin();
			            return true;
			        }
			        else {
			            return false;
			        }
			    }
			});
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			 LOG.error("onCreate:10195");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
    }
    
    @Override
	protected void onPause() {
    	LOG.debug("ENTRY onPause");
		try {
			//overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			super.onPause();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			 LOG.error("onPause:10197");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
    
    public void onClick_keyboardClose(View v){
	    try {
			v.setOnClickListener(new View.OnClickListener() {
			    
				@Override
			    public void onClick(View v) {
			        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			    }
			});
		} catch (Exception e) {
			 LOG.error("onClick_keyboardClose:10198");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

    public void on_button_click(View v){
    	userLogin();
    }

    private void loginAlertShow(String message) {
		try {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage(message);
			alertbox.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
								}
					});
					alertbox.show();
		}
		catch (Exception e) {
			 LOG.error("loginAlertShow:10568");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false; 
		}
		return super.onKeyDown(keyCode, event);
	}
    
    private void userLogin() {
    	try {
			LOG.debug("ENTRY userLogin");
    		final String usrnam = username.getText().toString().trim();
    		final String pass = password.getText().toString().trim();

    		if (usrnam != null && usrnam.trim().equalsIgnoreCase("")) {
    			loginAlertShow(getString(R.string.please_enter_username));
    			throw new GenException("",	getString(R.string.please_enter_username));
			}
			if (pass != null && pass.trim().equalsIgnoreCase("")) {
				loginAlertShow(getString(R.string.please_enter_password));
				throw new GenException("",	getString(R.string.please_enter_password));
			}
    		
    		userObject.setUsername(usrnam);
			userObject.setPassword(pass);
			
			LOG.info("Username: " + usrnam);
			
			//Offline Check
			if (NetworkCheck.isNetworkAvailable(this)) {
				
				//Online (Global selection)
				Application.setIsOnline(1);
				Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.LOGIN_BUTTON_CLICK, this, userObject));
			} else {
				Application.goOffline(this, userObject, true);
			}

			LOG.debug("SUCCESS userLogin");
		} catch (Exception e) {
			 LOG.error("on_button_click:10199");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
    
    }
}