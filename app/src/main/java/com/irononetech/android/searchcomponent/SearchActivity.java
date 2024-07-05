package com.irononetech.android.searchcomponent;

import android.app.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.irononetech.android.Application.Application;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class SearchActivity extends Activity {

	static Logger LOG = LoggerFactory.getLogger(SearchActivity.class);
	SearchUIobject searchUIobject;
	LinearLayout linearLayout;
	EditText searchBox;
	InputMethodManager imm;
	
	boolean isVisit = false;
	Intent extras;
	
	//Used in Application
	public ProgressDialog dialog;
	public synchronized ProgressDialog getDialog() {
		return dialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.search);
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			
			searchUIobject = Application.getSearhUIobjectInstance();
			linearLayout = (LinearLayout) findViewById(R.id.linearLayout_header);
			searchBox = (EditText) findViewById(R.id.searchbyvehiclenotext);
			searchBox.setText(searchUIobject.getJobOrVehicleNo());
			
			/*if (extras != null) {
				isVisit = extras.getBooleanExtra("ISVISIT", false);
			}*/
			isVisit = Application.getIsInVisitPage();
			if(isVisit)
				linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.visit_creation_visit_header));
			else
				linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_background1));
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:11382");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			try {
				Intent intent = new Intent(this, HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			} catch (Exception e) {
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onClick_keyboardClose(View v){
	    v.setOnClickListener(new View.OnClickListener() {
	    	@Override
	        public void onClick(View v) {
	            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	        }
	    });
	}
	
	public void on_click_back_button(View v){
		try {
			Intent intent = new Intent(this, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			LOG.error("on_click_back_button:10383");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void on_click_search_button(View v){
		try {
			String searchTxt = searchBox.getText().toString().trim();
			if(!searchTxt.isEmpty()){
				searchUIobject = Application.getSearhUIobjectInstance();
				searchUIobject.setJobOrVehicleNo(searchTxt);
				try {
					Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.SEARCH_BUTTON_CLICK, this, searchUIobject));
				}
				catch (Exception e) {
					LOG.error("on_click_search_button:10384");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
			}else{
				Toast.makeText(this, "Please provide a search criteria", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			LOG.error("on_click_search_button:11384");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
}
