package com.irononetech.android.future;

import com.irononetech.android.Application.Application;

import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class FutureActivity extends Activity {

Logger LOG = LoggerFactory.getLogger(FutureActivity.class);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOG.debug("ENTRY onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.futureview);
		LOG.debug("SUCCESS onCreate");
	}
	
	 public void on_button_click(View v){
		    	Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.FUTURE_BUTTON_CLICK, this, null));
	    }
}
