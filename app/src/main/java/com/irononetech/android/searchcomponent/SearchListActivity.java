package com.irononetech.android.searchcomponent;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.NameValuePair;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.irononetech.android.Application.Application;
import com.irononetech.android.dataretrievecomponent.DataRetrieveUIObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.view.VisitActivity;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class SearchListActivity extends Activity {
	static Logger LOG = LoggerFactory.getLogger(SearchListActivity.class);


	SearchListActivity searchListActivity;
	SearchUIobject searchUIobject;
	static ArrayList<String> DATA;
	ListView searchListView;
	TextView joborVehNo;

	public ProgressDialog progressDialog;
	Intent extras;
	boolean isVisit = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			this.searchListActivity = this;
			setContentView(R.layout.searchjoblist);
			
			isVisit = Application.getIsInVisitPage();
			searchUIobject = Application.getSearhUIobjectInstance();
			DATA = searchUIobject.getSearchedJobs();
			final List<NameValuePair> tmpList = Application.getJobOrVehicleNoWithVisitId();

			joborVehNo = (TextView) findViewById(R.id.vorjNo);
			joborVehNo.setText(searchUIobject.getJobOrVehicleNo());

			searchListView = (ListView) findViewById(R.id.pendingjobs_listview);
			searchListView.setAdapter(new SearchListAdapter(this));
			searchListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					LOG.debug("ENTRY onItemClick");

					DataRetrieveUIObject dataRetrieveUIObject = new DataRetrieveUIObject();

					if(!tmpList.get(position).getName().equals("-1")) {

						dataRetrieveUIObject.setVisitId(tmpList.get(position).getName());
						VisitObject vObj = null;
						if (DATA.get(position).contains("SA Form"))
							Application.setIsVisit(false);
						else {
							Application.setIsVisit(true);

							String vNo = "";							
							vNo = tmpList.get(position).getValue().split("_")[1];													

							vObj = Application.createVisitObjectInstance();
							vObj.setVehicleNo(vNo);
						}
						if (Application.isInVisitPage) {
							Application.setIsVisit(true);

							//vObj = Application.createVisitObjectInstance();
							vObj.setJobNo(DATA.get(position).split("_")[0].trim());
							vObj.setVisitId(tmpList.get(position).getName());
							vObj.setVehicleNo(DATA.get(position).split("_")[1].trim());
							vObj.setisSMS(false);
							vObj.setisDRAFT(true);
							vObj.setisSEARCH(false);
							Intent mIntent = new Intent(SearchListActivity.this, VisitActivity.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
							//finish();
						}else{
							Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DATA_RETRIEVE_BUTTON_CLICK, searchListActivity, dataRetrieveUIObject));
						}						
					}
					LOG.debug("SUCCESS onItemClick");
				}
			});	
			LOG.debug("SUCCESS onCreate");
		}
		catch (Exception e) {
			LOG.error("onCreate:10390");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			
		}
	}

	@Override
	protected void onResume() {
		try {
			/*searchUIobject = Application.getSearhUIobjectInstance();
			DATA = searchUIobject.getSearchedJobs();*/

			/*if(DATA.size() == 1){
				DataRetrieveUIObject dataRetrieveUIObject = new DataRetrieveUIObject();
				dataRetrieveUIObject.setVisitId(DATA.get(0));
				Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DATA_RETRIEVE_BUTTON_CLICK, searchListActivity, dataRetrieveUIObject));
			}
			if(DATA.size() == 0){
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						SearchListActivity.this);
				alertbox.setTitle("Search Results:");
				alertbox.setMessage(getString(R.string.no_records));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(SearchListActivity.this,
								SearchActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						SearchListActivity.this.startActivity(intent);
						finish();
					}
				});
				alertbox.show();
			}*/

			LOG.debug("ENTRY onResume");
			super.onResume();
			
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10391");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_back_button(View v){
		Intent intent = new Intent(this, SearchActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				Intent intent = new Intent(this, SearchActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			} catch (Exception e) {
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	private static class SearchListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public SearchListAdapter(Context context) {
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

		String vNo = "";

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 1.
			View view = mInflater.inflate(R.layout.searchlist_custom_list_item, null);

			if(!Application.isInVisitPage && !DATA.get(position).contains("|")){ //Normal search
				view = mInflater.inflate(R.layout.searchlist_custom_list_item1, null);
			}

			// 2.	        
			LinearLayout listLayout = (LinearLayout) view.findViewById(R.id.listlayoutview);
			TextView vehicleNo_i = (TextView) view.findViewById(R.id.position);
			TextView vehicleNo = (TextView) view.findViewById(R.id.vehicleno);
			TextView jobNo_i = (TextView) view.findViewById(R.id.id);
			TextView jobNo = (TextView) view.findViewById(R.id.jobno);
			TextView date_i = (TextView) view.findViewById(R.id.item);
			TextView date = (TextView) view.findViewById(R.id.date);

			// 3.
			if(!Application.isInVisitPage){  //Normal search
				if(DATA.get(position).contains("|")){  //heading list item
					listLayout.setBackgroundResource(R.drawable.pendingjobs_only_row_i);
					listLayout.setClickable(false);
					listLayout.setEnabled(false);
					listLayout.setFocusable(false);
					listLayout.setFocusableInTouchMode(false);
					listLayout.setActivated(false);
					
					vNo = DATA.get(position).split("\\|")[1];
					vehicleNo.setText(vNo);

					jobNo.setText(DATA.get(position).split("\\|")[0]);
					date_i.setText("");
					date.setText("");
				}else{									//data items
					vehicleNo_i.setVisibility(View.GONE);
					vehicleNo.setText(DATA.get(position).split("_")[0]);
					//vehicleNo.setTextSize(27);
					//vehicleNo.setPadding(4, 0, 0, 0);
					
					jobNo_i.setText("Vehicle No:");
					jobNo.setText(vNo);
					date.setText(DATA.get(position).split("_")[1]);
				}
			}else{						//visit creation page
				jobNo.setText(DATA.get(position).split("_")[0]);
				vehicleNo.setText(DATA.get(position).split("_")[1]);
				date.setText(DATA.get(position).split("_")[2]);
			}
			return view;
		}
	}	

}