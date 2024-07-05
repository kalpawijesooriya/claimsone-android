package com.irononetech.android.formcomponent.view;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.irononetech.android.claimsone.R;
import com.irononetech.android.database.DBService;
import com.irononetech.android.homecomponent.HomeActivity;

public class DraftPendingSubmissionsActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(DraftPendingSubmissionsActivity.class);
	
	ListView pendingjobListView;
	private static ArrayList<String> DATA;
	boolean[] booleanSelectedArr;
	String filepath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.activity_draft_pendingsubmissions);
			pendingjobListView = (ListView) findViewById(R.id.pendingjobs_listview);

			DATA = new ArrayList<String>();
			getPendingJobs();
			pendingjobListView.setAdapter(new PendingJobsListAdapter(getApplicationContext()));

			pendingjobListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				}
			});
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10377");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	private void getPendingJobs(){
		DATA = DBService.getPendingJobNoList();
		if(DATA == null || DATA.size() == 0){
			DATA.add("No pending jobs available");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		LOG.debug("ENTRY onPause");
		try {
			getPendingJobs();
			pendingjobListView.setAdapter(new PendingJobsListAdapter(getApplicationContext()));
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onPause:11379");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LOG.debug("ENTRY onResume");
		pendingjobListView.setAdapter(new PendingJobsListAdapter(getApplicationContext()));
		try {
			getPendingJobs();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10379");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	

	private static class PendingJobsListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public PendingJobsListAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return DATA.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if (convertView == null) {
				if (getCount() == 1) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);
				} else if (position == 0) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);

				} else if (position == getCount() - 1) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);
				} else {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);
				}
				holder = new ViewHolder();
				holder.jobNo = (TextView) convertView
						.findViewById(R.id.pedingjobslist_item_jobno);
				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}
			String job = DATA.get(position);
			// Bind the data efficiently with the holder.
			holder.jobNo.setText(job);
			return convertView;
		}

		static class ViewHolder {
			TextView jobNo;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				//return false;
				Intent intent = new Intent(this, HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			} catch (Exception e) {
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
