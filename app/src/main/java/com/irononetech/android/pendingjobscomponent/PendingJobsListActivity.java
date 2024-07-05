package com.irononetech.android.pendingjobscomponent;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.database.DBService;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.view.AccidentDetailsActivity;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.claimsone.R;

public class PendingJobsListActivity extends Activity {
	//
	Logger LOG = LoggerFactory.getLogger(PendingJobsListActivity.class);

	
	ListView pendingjobListView;
	ListView draftjobs_listview;
	Button delBtn;
	private static ArrayList<String> DATA;
	private static ArrayList<String> DraftDATA;
	boolean[] booleanSelectedArr;
	String filepath;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
		super.onCreate(savedInstanceState);
		LOG.debug("ENTRY onCreate");
		//setContentView(R.layout.pendingjoblist);
		pendingjobListView = (ListView) findViewById(R.id.pendingjobs_listview);
		draftjobs_listview = (ListView) findViewById(R.id.draftjobs_listview);
		delBtn = (Button) findViewById(R.id.draftdetebtn);
		//delBtn.requestFocus();
		
			//delBtn.setFocusable(true);
			//delBtn.setFocusableInTouchMode(true);
			//delBtn.setClickable(true);
			
			filepath = URL.getSLIC_DRAFTS_JOBS();
			DATA = new ArrayList<String>();
			getPendingJobs();
			pendingjobListView.setAdapter(new PendingJobsListAdapter(getApplicationContext()));
				
			DraftDATA = new ArrayList<String>();
			//DraftDATA = DBService.getPendingDrafts();
			getDrafts();
			
			btnManip();
			
			booleanSelectedArr = new boolean[DraftDATA.size()];
			draftjobs_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));

			pendingjobListView.setOnItemClickListener(new OnItemClickListener() {
				  public void onItemClick(AdapterView<?> parent, View view,
					      int position, long id) {
					  
					  /*ImageUploadController imageUploadController = new ImageUploadController();
					  try {
						  imageUploadController.doAction(null);
					  	} catch (GenException e) {
					  		LogFile.d("\n\nimageUploadController.doAction88", e.getMessage());
					  		e.printStackTrace();
					  	} catch (Exception e) {
					  		LogFile.d("\n\nimageUploadController.doAction77", e.getMessage());
					  		e.printStackTrace();
					  }*/
				  }
			});
			
			draftjobs_listview.setOnItemClickListener(new OnItemClickListener() {
				  public void onItemClick(AdapterView<?> parent, View view,
				      int position, long id) {

					  LOG.debug("ENTRY onItemClick");
					  
					 if(!DraftDATA.get(0).equalsIgnoreCase("No drafts available")){
						if (FormObjectDeserializer.deserializeFormData(""
								+ position)) {
							Intent intent = new Intent(
									PendingJobsListActivity.this,
									AccidentDetailsActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							PendingJobsListActivity.this.startActivity(intent);
							finish();
						} else {
							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									PendingJobsListActivity.this);
							alertbox.setTitle("Draft Alert:");
							alertbox.setMessage("Error opening the draft.");
							alertbox.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
										}
									});
							alertbox.show();
						}
					}
					LOG.debug("SUCCESS onItemClick");
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
	
	
	private void btnManip(){
		try {
			if(DraftDATA.size() > 0){
				delBtn.setFocusable(true);
				delBtn.setFocusableInTouchMode(true);
				delBtn.setClickable(true);
			}
			if(DraftDATA.size() == 1){
				if(DraftDATA.get(0).equalsIgnoreCase("No drafts available")){
					delBtn.setFocusable(false);
					delBtn.setFocusableInTouchMode(false);
					delBtn.setClickable(false);
				}
			}
			if(DraftDATA.size() == 0){
				delBtn.setFocusable(false);
				delBtn.setFocusableInTouchMode(false);
				delBtn.setClickable(false);
			}
		} catch (Exception e) { 
			LOG.error("btnManip:10378");
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
	
	private void getDrafts(){
		DraftDATA = DBService.getPendingJobDraftsList();
		if(DraftDATA == null || DraftDATA.size() == 0){
			DraftDATA.add("No drafts available");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LOG.debug("ENTRY onResume");
		try {
			getPendingJobs();
			//DraftDATA = DBService.getPendingDrafts();
			getDrafts();
			btnManip();
			LOG.debug("SUCCESS onResume");
			
		} catch (Exception e) { 
			LOG.error("onResume:10379");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	
	public void on_click_back_button(View v){
		try {
			LOG.debug("ENTRY on_click_back_button");
			finish();
			LOG.debug("SUCCESS on_click_back_button");
		} catch (Exception e) {
			LOG.error("onResume:10380");
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
	
	
	private static class PendingDraftsListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public PendingDraftsListAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return DraftDATA.size();
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
				//if (getCount() == 1) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);
				/*
				} else if (position == 0) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);

				} else if (position == getCount() - 1) {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);

				} else {
					convertView = mInflater.inflate(
							R.layout.pendingjobslist_only_row, null);

				}*/
				holder = new ViewHolder();
				holder.jobNo = (TextView) convertView
						.findViewById(R.id.pedingjobslist_item_jobno);
				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}
			String job = DraftDATA.get(position);
			// Bind the data efficiently with the holder.
			holder.jobNo.setText(job);
			return convertView;
		}

		static class ViewHolder {
			TextView jobNo;
		}
	}
	
	
	public void on_click_draftdelete_button(View v){
		try {
			LOG.debug("ENTRY on_click_draftdelete_button");
			DraftDATA = FileOperations.fileFilter(filepath);
			String[] dataArr =  new String[DraftDATA.size()];
				for (int j = 0; j < DraftDATA.size(); j++) {
					dataArr[j] = DraftDATA.get(j);
				}
							
			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(PendingJobsListActivity.this)
					.setTitle("Select Drafts to Delete:")
					.setMultiChoiceItems(dataArr,
							booleanSelectedArr,
							new DialogInterface.OnMultiChoiceClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton, boolean isChecked) {

									/* User clicked on a check box do some stuff */
								}
							})
					.setPositiveButton("Delete",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									FileOperations fo = new FileOperations();
									fo.draftFileDeleter(booleanSelectedArr);
									
									DraftDATA = new ArrayList<String>();
									//DraftDATA = DBService.getPendingDrafts();
									getDrafts();
									btnManip();
									draftjobs_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));
									//booleanSelectedArr = null;

									//just to clear the previously selected check-boxes
									boolean[] a = new boolean[DraftDATA.size()];
									booleanSelectedArr = a;
									removeDialog(0);
								}
							}
					 )
					.setNegativeButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									/* User clicked No so do some stuff */
									boolean[] a = new boolean[DraftDATA.size()];
									booleanSelectedArr = a;
									removeDialog(0);
								}
							});
							dialog = builder.create();
							dialog.show();
				LOG.debug("SUCCESS on_click_draftdelete_button");
		} catch (Exception e) {
			LOG.error("on_click_draftdelete_button:10381");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
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