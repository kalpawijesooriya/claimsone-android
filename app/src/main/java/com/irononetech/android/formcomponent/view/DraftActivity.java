package com.irononetech.android.formcomponent.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjectDeserializer;

public class DraftActivity extends TabActivity implements OnTabChangeListener{
	Logger LOG = LoggerFactory.getLogger(DraftActivity.class);

	TabHost tabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LOG.debug("ENTRY onCreate"); 
		setContentView(R.layout.activity_draft);

		tabHost = getTabHost();

		// Tab for JobDrafts
		TabSpec draftJob = tabHost.newTabSpec("JobDrafts");
		//setting Title and Icon for the Tab
		draftJob.setIndicator("", getResources().getDrawable(R.drawable.empty_icon));
		Intent jobIntent = new Intent(this, DraftJobActivity.class);
		draftJob.setContent(jobIntent);

		// Tab for VisitDrafts
		TabSpec draftVisit = tabHost.newTabSpec("VisitDrafts");
		draftVisit.setIndicator("", getResources().getDrawable(R.drawable.empty_icon));
		Intent visitIntent = new Intent(this, DraftVisitActivity.class);
		draftVisit.setContent(visitIntent);

		// Tab for PendingSubmissions
		TabSpec pendingSubmissions = tabHost.newTabSpec("PendingSubmissions");
		pendingSubmissions.setIndicator("", getResources().getDrawable(R.drawable.empty_icon));
		Intent pendingsubmissionsIntent = new Intent(this, DraftSentItemsActivity.class);
		pendingSubmissions.setContent(pendingsubmissionsIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(draftJob);
		tabHost.addTab(draftVisit);
		tabHost.addTab(pendingSubmissions);

		try {
			Bundle extras = this.getIntent().getExtras();
			tabHost.setCurrentTab(0);
			if(extras != null)
			{
				int tab = Integer.parseInt(extras.getString("TAB").toString());
				tabHost.setCurrentTab(tab);
			}
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			tabHost.setCurrentTab(0);
		}

		for(int i=0; i<tabHost.getTabWidget().getChildCount(); i++)
		{
			//unselected
			if(i==0)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.jobicon));
			if(i==1)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.visiticon));
			if(i==2)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.pendingicon));
		}
		
		// selected
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.jobicon_active)); 
		tabHost.setOnTabChangedListener(this);
	}

	@Override
	public void onTabChanged(String tabId) {

		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		{
			//tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.jobicon));
			//tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#e8e8c2")); //unselected
			//unselected
			if(i==0)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.jobicon));
			if(i==1)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.visiticon));
			if(i==2)
				tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.pendingicon));
		}
		
		if(tabId.equalsIgnoreCase("JobDrafts"))
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.jobicon_active));
			//tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#b3a683")); // selected
		if(tabId.equalsIgnoreCase("VisitDrafts"))
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.visiticon_active));
		if(tabId.equalsIgnoreCase("PendingSubmissions"))
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.pendingicon_active));
	}    

}
