package com.irononetech.android.ui.components;

import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.claimsone.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopupDialog extends Activity {
	TextView textView;
	FormObject formObject;
	static Logger LOG = LoggerFactory.getLogger(PopupDialog.class);

			
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			LOG.debug("ENTRY onCreate");
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.submitpopup);
			formObject = Application.getFormObjectInstance();
			textView = (TextView) findViewById(R.id.customText1);
			textView.setText(formObject.getActivityNotificationDialogStr());
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10406");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	protected void onPause() {
		LOG.debug("ENTRY onPause");
		finish();
		super.onPause();
		LOG.debug("SUCCESS onPause");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void backtomenu_button_click(View v) {
		finish();
	}
}
