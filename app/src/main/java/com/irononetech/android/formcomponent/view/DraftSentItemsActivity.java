package com.irononetech.android.formcomponent.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.WebService;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.database.DBService;
import com.irononetech.android.homecomponent.HomeActivity;

public class DraftSentItemsActivity extends Activity {

	static Logger LOG = LoggerFactory.getLogger(DraftSentItemsActivity.class);
	private static ArrayList<String> DATA;
	ProgressBar progressBar;
	InputMethodManager imm;
	Button searchBtn;
	TextView userId;
	String statusCode = "0";
	EditText dayTxt;
	EditText monthTxt;
	EditText yearTxt;

	static List<NameValuePair> statList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LOG.debug("ENTRY onCreate");
		setContentView(R.layout.activity_draft_sentitems);

		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		/*dayTxt = (EditText)findViewById(R.id.dateTextboxi);
		monthTxt = (EditText)findViewById(R.id.monthTextboxi);
		yearTxt = (EditText)findViewById(R.id.yearTextboxi);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		String StartDateTime = formatter.format(Calendar.getInstance().getTime());
		String fulldatettime = StartDateTime;

		dayTxt.setText(fulldatettime.split("/")[0]);
		monthTxt.setText(fulldatettime.split("/")[1]);
		int startPos = fulldatettime.split("/")[2].length()-2;
		yearTxt.setText(fulldatettime.split("/")[2].substring(startPos));

		Spinner statusSpinner = (Spinner) findViewById(R.id.status_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.itemSentStatus,
				R.layout.textviewforspinner_small);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statusSpinner.setAdapter(adapter);
		statusSpinner.setOnItemSelectedListener(new MyOnItemSelectedListenerStatusSelector());
		statusSpinner.setSelection(0);

		userId = (TextView)findViewById(R.id.userId);
		userId.setText(Application.getCurrentUser());

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		searchBtn = (Button)findViewById(R.id.search_button);*/
		LOG.debug("SUCCESS onCreate");
	}


	public void on_click_search(View vx){
		try {
			LOG.debug("ENTRY ",  "on_click_search");

			String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
			Pattern pattern = Pattern.compile(expression);
			String date = dayTxt.getText().toString() + "/" + monthTxt.getText().toString() + "/" + yearTxt.getText().toString();
			Matcher matcher = pattern.matcher(date);
			
			if(!matcher.matches()){
				//totalDateTextboxError = "Please enter a valid 'Policy Cover Note Period From'.\r\n";
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setTitle(getString(R.string.sent_items_alert));
				alertbox.setMessage(R.string.please_enter_a_valid_date_);
				alertbox.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {}
				});
				alertbox.show();
				return;
			}

			vx.setEnabled(false);
			progressBar.setVisibility(View.VISIBLE);
			new GetMyVisitsStatusHandler().execute();

			LOG.debug("SUCCESS ",  "on_click_search");
		} catch (Exception e) {
			LOG.error("on_click_search:12524");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			vx.setEnabled(true);
			progressBar.setVisibility(View.GONE);
		}
	}

	public void on_click_back_button(View v){
		try {
			LOG.debug("ENTRY ",  "on_click_back_button");
			finish();
			LOG.debug("SUCCESS ",  "on_click_back_button");
		} catch (Exception e) {
			LOG.error("onResume:10380");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	//Old pending jobs section now comes as a popup 
	public void on_click_submittedItems(View vx){
		try {			
			DATA = new ArrayList<String>();
			getPendingJobs();

			AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.activity_draft_pendingsubmissions, null);
			myDialog.setView(view);
			myDialog.setTitle(getString(R.string.pending_items));
			ListView pendList = (ListView) view.findViewById(R.id.pendingjobs_listview);
			pendList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DATA));

			myDialog.setPositiveButton(getString(R.string.close),
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			myDialog.show();
		} catch (Exception e) {
			LOG.error("onResume:12001");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private void getPendingJobs(){
		DATA = DBService.getPendingJobNoList();
		if(DATA == null || DATA.size() == 0){
			DATA.add(getString(R.string.no_pending_jobs_available));
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

	public void onClick_keyboardClose(View v){
		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
	}


	private class MyOnItemSelectedListenerStatusSelector implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				for (ItemSentStatus sStat : ItemSentStatus.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(sStat.getString())) {
						statusCode = String.valueOf(sStat.getInt());
						break;
					}
				}
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_small_ultra));
				((TextView) parent.getChildAt(0)).setTextSize(20);

			} catch (Exception e) {
				LOG.error("onItemSelected:12002");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}


	/**
	 * 
	 * @author Suren Manawatta
	 */
	class GetMyVisitsStatusHandler extends AsyncTask <String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				//TODO
				String username = userId.getText().toString();
				String date = dayTxt.getText().toString() + "/" + monthTxt.getText().toString() + "/" + yearTxt.getText().toString();
				String status = statusCode;
				return WebService.getAllVisitStatus(username, date, status);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String xmlText) {
			try{
				if(xmlText != null) {
					//xml get response if 0 process and show the popup else show error msg
					WebServiceObject ws = new WebServiceObject();
					ws.setXmlText(xmlText);
					XMLHandler.getResponse(ws);
					if(ws.getCode().equalsIgnoreCase("0")) {
						//TODO
						statList = XMLHandler.getVisitStatusRecords(ws);
						displayHistory();
					} else {
						if(ws.getDescription() != null && !ws.getDescription().isEmpty())
							displayErrMessage(ws.getDescription());
						else
							displayErrMessage(getString(R.string.network_failed_));
					}
				}else {
					displayErrMessage(getString(R.string.network_failed_));
				}
			} catch(Exception e){
				displayErrMessage(getString(R.string.network_failed_));
			} finally{
				searchBtn.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}
		}
	}

	private void displayErrMessage(String msg) {
		String title = getString(R.string.sent_items_alert);
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle(title);
		alertbox.setMessage(msg);
		alertbox.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		alertbox.show();
	}

	private void displayHistory() {
		try {
			LOG.debug("SUCCESS ",  "displayHistory");

			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.activity_draft_sentitems, null);
			ListView sntItmList = (ListView) view.findViewById(R.id.sentitems_listview);
			sntItmList.setAdapter(new StatListAdapter(this));
			sntItmList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				}
			});

			LOG.debug("SUCCESS ", "displayHistory");
		} catch (Exception e) {
			LOG.error("displayHistory:12005");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static class StatListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public StatListAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return statList.size();
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
			// 1.
			View view = mInflater.inflate(R.layout.visit_status_custom_bubble_item, null);

			// 2.
			LinearLayout listLayout = (LinearLayout) view.findViewById(R.id.listlayoutview);
			LinearLayout bottomBar = (LinearLayout) view.findViewById(R.id.bottombar);
			TextView jobVehicleNo = (TextView) view.findViewById(R.id.jobvehicleno);
			TextView visitStatus = (TextView) view.findViewById(R.id.status);
			TextView vistType = (TextView) view.findViewById(R.id.visttype);
			Button draftBtn = (Button) view.findViewById(R.id.draftbtn);

			// 3.
			//jobVehicleNo:  vehicleno_jobno		| draftBtn
			//visitStatus: status					| vistType
			NameValuePair itm = statList.get(position);
			String[] arr = itm.getName().split("_");

			if(itm.getValue().equals("-1")) {
				listLayout.setBackgroundResource(R.drawable.pendingjobs_only_row_i);
				bottomBar.setVisibility(View.GONE);
				jobVehicleNo.setText(arr[0] + "  (" + arr[1] + ")");
			}else {
				bottomBar.setVisibility(View.VISIBLE);
				//jobVehicleNo.setText();
				//visitStatus.setText();
				//vistType.setText();
			}
			return view;
		}
	}	
}
