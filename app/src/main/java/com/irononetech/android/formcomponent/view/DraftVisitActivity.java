package com.irononetech.android.formcomponent.view;

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
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.claimsone.R;

public class DraftVisitActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	private static ArrayList<String> DraftDATA;
	Button delBtn;
	ListView draftvisits_listview;
	String filepath;
	boolean[] booleanSelectedArr;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LOG.debug("ENTRY onCreate");
		setContentView(R.layout.activity_draft_visit);

		draftvisits_listview = (ListView) findViewById(R.id.draftvisits_listview);
		delBtn = (Button) findViewById(R.id.draftdetebtn);
		//delBtn.requestFocus();

		//delBtn.setFocusable(true);
		//delBtn.setFocusableInTouchMode(true);
		//delBtn.setClickable(true);

		filepath = URL.getSLIC_DRAFTS_VISITS();
		DraftDATA = new ArrayList<String>();
		getDrafts();
		btnManip();

		booleanSelectedArr = new boolean[DraftDATA.size()];
		draftvisits_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));

		draftvisits_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				LOG.debug("ENTRY ", "setOnItemClickListener");

				if(!DraftDATA.get(0).equalsIgnoreCase("No drafts available")){
					if (FormObjectDeserializer.deserializeFormDataForVisits("" + position)) {
						Intent intent = new Intent(DraftVisitActivity.this, VisitActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						DraftVisitActivity.this.startActivity(intent);
						//finish();
					} else {
						AlertDialog.Builder alertbox = new AlertDialog.Builder(DraftVisitActivity.this);
						alertbox.setTitle("Draft Alert:");
						alertbox.setMessage("Error opening the draft.");
						alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface arg0, int arg1) {
							}
						});
						alertbox.show();
					}
				}
				LOG.debug("SUCCESS ", "setOnItemClickListener");
			}
		});
		LOG.debug("SUCCESS onCreate");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LOG.debug("ENTRY onPause");
		try {
			getDrafts();
			btnManip();
			draftvisits_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onPause:1501");
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
		try {
			getDrafts();
			btnManip();
			draftvisits_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:1500");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private void getDrafts(){
		DraftDATA = DBService.getPendingVisitDraftsList();
		if(DraftDATA == null || DraftDATA.size() == 0){
			DraftDATA.add("No drafts available");
		}
	}

	public void on_click_draftdelete_button(View v){
		try {
			LOG.debug("ENTRY ", "on_click_draftdelete_button");
			DraftDATA = FileOperations.fileFilterForVisits(filepath);
			String[] dataArr =  new String[DraftDATA.size()];
			for (int j = 0; j < DraftDATA.size(); j++) {
				dataArr[j] = DraftDATA.get(j).replace("_", "\n");
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(DraftVisitActivity.this)
			.setCancelable(false)
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
					fo.draftFileDeleterForVisits(booleanSelectedArr);

					DraftDATA = new ArrayList<String>();
					//DraftDATA = DBService.getPendingDrafts();
					getDrafts();
					btnManip();
					draftvisits_listview.setAdapter(new PendingDraftsListAdapter(getApplicationContext()));
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
			LOG.debug("SUCCESS ","on_click_draftdelete_button");
		} catch (Exception e) {
			LOG.error("on_click_draftdelete_button:11381");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private void btnManip(){
		try {
			if(DraftDATA.size() > 0){
				//delBtn.setFocusable(true);
				//delBtn.setFocusableInTouchMode(true);
				//delBtn.setClickable(true);
				delBtn.setVisibility(View.VISIBLE);
			}
			if(DraftDATA.size() == 1){
				if(DraftDATA.get(0).equalsIgnoreCase("No drafts available")){
					//delBtn.setFocusable(false);
					//delBtn.setFocusableInTouchMode(false);
					//delBtn.setClickable(false);
					delBtn.setVisibility(View.GONE);
				}
			}
			if(DraftDATA.size() == 0){
				//delBtn.setFocusable(false);
				//delBtn.setFocusableInTouchMode(false);
				//delBtn.setClickable(false);
				delBtn.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			LOG.error("btnManip:11378");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_back_button(View v){
		try {
			LOG.debug("ENTRY ", "on_click_back_button");
			finish();
			LOG.debug("SUCCESS ","on_click_back_button");
		} catch (Exception e) {
			LOG.error("onResume:11380");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
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
			View view = null;
			try {
				// 1.
				view = mInflater.inflate(R.layout.draftlist_custom_list_item, null);

				// 2.	        
				TextView jobNoi = (TextView) view.findViewById(R.id.jobnoi);
				TextView jobNo = (TextView) view.findViewById(R.id.jobno);
				TextView vehicleNoi = (TextView) view.findViewById(R.id.vehiclenoi);
				TextView vehicleNo = (TextView) view.findViewById(R.id.vehicleno);
				TextView visittypei = (TextView) view.findViewById(R.id.visittypetexti);
				TextView visittype = (TextView) view.findViewById(R.id.visittypetext);
				TextView datei = (TextView) view.findViewById(R.id.datei);
				TextView date = (TextView) view.findViewById(R.id.date);

				// 3.
				if(!DraftDATA.get(0).equalsIgnoreCase("No drafts available")){
					if (!DraftDATA.get(position).startsWith("_"))
						jobNo.setText(DraftDATA.get(position).split("_")[0]);
					else
						jobNo.setText("--");

					if (!DraftDATA.get(position).split("_")[1].split(" ")[0].isEmpty())
						//vehicleNo.setText(DraftDATA.get(position).split("_")[1].split(" ")[0]);
						vehicleNo.setText(DraftDATA.get(position).split("\\(")[0].split("_")[1].trim());
					else
						vehicleNo.setText("--");

					visittype.setText(DraftDATA.get(position).split("_")[2].split(" ")[0]);

					String tmp = DraftDATA.get(position).split("\\(")[1];
					tmp = tmp.replace(")", "");
					date.setText(tmp);
				}else {
					jobNo.setText("No drafts available");
					jobNoi.setVisibility(View.GONE);
					vehicleNoi.setVisibility(View.GONE);
					visittypei.setVisibility(View.GONE);
					datei.setVisibility(View.GONE);
				}
			} catch (Exception e) {
			}
			return view;
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
