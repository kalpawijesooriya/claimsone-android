package com.irononetech.android.logincomponent.view;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.TextView;
import com.irononetech.android.claimsone.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SplashActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(SplashActivity.class);
	TextView versionView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			setContentView(R.layout.splashform);
			versionView = (TextView) findViewById(R.id.textViewVersion);
			
			String app_ver = "";
			try
			{
				app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			}
			catch (NameNotFoundException e)
			{
				 LOG.error("onCreate:11196");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			versionView.setText("V " + app_ver);
			
			String SLIC_FOLDER = Environment.getExternalStorageDirectory() + "/SLIC/";
			String SLIC_COPIED = Environment.getExternalStorageDirectory() + "/SLIC/Copied";
			
			File slicFolder = new File(SLIC_FOLDER);
			if(!slicFolder.exists()){
				slicFolder.mkdirs();
			}
			
			File copiedFolder = new File(SLIC_COPIED);
			if(!copiedFolder.exists()){
				copiedFolder.mkdirs();
			}
			
			new SyncHandler().execute();
			LOG.debug("SUCCESS onCreate");

		} catch (Exception e) {
			 LOG.error("onCreate:11198");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	@Override
	protected void onPause() {
		try {
			LOG.debug("ENTRY onPause");
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			super.onPause();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			 LOG.error("onPause:11197");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	@Override
	protected void onResume() {
		try {
			LOG.debug("ENTRY onResume");
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			super.onResume();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			 LOG.error("onResume:11196");
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

	/**
	 * @author Suren Manawatta
	 */
	class SyncHandler extends AsyncTask <String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(2000);
				return "";
			} catch (Exception e) {
				 LOG.error("doInBackground:12198");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return null;
			}
		}

		@Override
		protected void onPostExecute(String xmlText) {
			try{
				Intent mIntent = new Intent(SplashActivity.this, LoginActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(mIntent);
				finish();
			} catch(Exception e){
				 LOG.error("onPostExecute:12197");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}
	}

}
