package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.FormResubmitObject;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.utilities.JPGFileFilter;

public class CommentActivity extends Activity {

	TextView dateseparatorC1;
	TextView dateseparatorC2;
	TextView timeseparatorC;

	FormObject formObject;
	public ProgressDialog dialog;
	InputMethodManager imm;
	EditText appCostRprTxtbox;
	Spinner onsiteestimation;
	EditText commentsTxtbox;


	EditText datetextboxVisited;
	EditText monthtextboxVisited;
	EditText yeartextboxVisited;
	EditText hourstextboxVisited;
	EditText minutestextboxVisited;

	Spinner ampmspinnerVisited;
	
	Button buttonSubmit;
	Button commentFormCancelButton;
	Button commentformSearchCancelButton;
	Button commentFormBackButton;
	Button commentFormSearchBackButton;
	
	Logger LOG = LoggerFactory.getLogger(CommentActivity.class);
	
	public synchronized ProgressDialog getDialog() {
		return dialog;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			setContentView(R.layout.commentform);
			
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			formObject = Application.getFormObjectInstance();

			dateseparatorC1 = (TextView) findViewById(R.id.dateseparatorC1);
			dateseparatorC2 = (TextView) findViewById(R.id.dateseparatorC2);
			timeseparatorC = (TextView) findViewById(R.id.timeseparatorC);

			datetextboxVisited = (EditText) findViewById(R.id.datetextboxVisited);
			monthtextboxVisited = (EditText) findViewById(R.id.monthtextboxVisited);
			yeartextboxVisited = (EditText) findViewById(R.id.yeartextboxVisited);
			hourstextboxVisited = (EditText) findViewById(R.id.hourstextboxVisited);
			minutestextboxVisited = (EditText) findViewById(R.id.minutestextboxVisited);

			commentFormCancelButton = (Button) findViewById(R.id.commentFormCancelButton);
			commentformSearchCancelButton = (Button) findViewById(R.id.commentform_search_cancel_button);
			commentFormBackButton = (Button) findViewById(R.id.commentFormBackButton);
			commentFormSearchBackButton = (Button) findViewById(R.id.commentFormSearchBackButton);

			ampmspinnerVisited = (Spinner) findViewById(R.id.ampmspinnerVisited);
			ArrayAdapter<CharSequence> adapterTimeVisited = ArrayAdapter.createFromResource(
					this, R.array.timePeriodArr,
					R.layout.textviewforspinner_small);
			adapterTimeVisited.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			ampmspinnerVisited.setAdapter(adapterTimeVisited);
			ampmspinnerVisited.setOnItemSelectedListener(new MyOnItemSelectedListenertimeVisitedPeriodSelector());
			ampmspinnerVisited.setSelection(0); //Integer.parseInt(formObject.getTimePeriods())); //.trim())-1
			
			appCostRprTxtbox = (EditText) findViewById(R.id.appCostRprTxtbox);
			onsiteestimation = (Spinner) findViewById(R.id.onsiteestimation_spinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					this, R.array.onsiteestOpt, R.layout.textviewforspinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			onsiteestimation.setAdapter(adapter);
			onsiteestimation
					.setOnItemSelectedListener(new MyOnItemSelectedListenerOnSiteEstimation());
			onsiteestimation.setSelection(Integer.parseInt(formObject.getOnSiteEstimation().trim()));
			
			commentsTxtbox = (EditText) findViewById(R.id.commentsTxtbox);
			buttonSubmit = (Button)findViewById(R.id.commentform_submit_button);

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");
		} catch (NumberFormatException e) {
			LOG.error("onCreate:10430");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("onCreate:10431");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	@Override
	protected void onResume() {
		try {
			super.onResume();
			LOG.debug("ENTRY onResume");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10431");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	@Override
	protected void onPause() {
		try {
			super.onPause();
			onSave();
			LOG.debug("ENTRY onPause");
			System.gc();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onResume:10432");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void formComponentControler(){
		try {
			if(formObject.getisDRAFT() || formObject.getisSMS()){/*
				onsiteestimation.setEnabled(true);	
								
				appCostRprTxtbox.setFocusable(true);
				appCostRprTxtbox.setFocusableInTouchMode(true); 
				appCostRprTxtbox.setClickable(true);

				commentsTxtbox.setFocusable(true);
				commentsTxtbox.setFocusableInTouchMode(true); 
				commentsTxtbox.setClickable(true); */
				int maxLength = 500;
				InputFilter[] fArray = new InputFilter[1];
				fArray[0] = new InputFilter.LengthFilter(maxLength);
				commentsTxtbox.setFilters(fArray);
				}
			if(formObject.getisSEARCH()){
				onsiteestimation.setEnabled(false);

				dateseparatorC1.setTextColor(Color.GRAY);
				dateseparatorC2.setTextColor(Color.GRAY);
				timeseparatorC.setTextColor(Color.GRAY);

				datetextboxVisited.setEnabled(false);
				datetextboxVisited.setFocusable(false);
				datetextboxVisited.setTextColor(Color.GRAY);

				monthtextboxVisited.setEnabled(false);
				monthtextboxVisited.setFocusable(false);
				monthtextboxVisited.setTextColor(Color.GRAY);

				yeartextboxVisited.setEnabled(false);
				yeartextboxVisited.setFocusable(false);
				yeartextboxVisited.setTextColor(Color.GRAY);

				hourstextboxVisited.setEnabled(false);
				hourstextboxVisited.setFocusable(false);
				hourstextboxVisited.setTextColor(Color.GRAY);

				minutestextboxVisited.setEnabled(false);
				minutestextboxVisited.setFocusable(false);
				minutestextboxVisited.setTextColor(Color.GRAY);

				ampmspinnerVisited.setFocusable(false);
				ampmspinnerVisited.setFocusableInTouchMode(false);
				ampmspinnerVisited.setClickable(false);

				appCostRprTxtbox.setTextColor(Color.GRAY);
				appCostRprTxtbox.setFocusable(false);
				appCostRprTxtbox.setFocusableInTouchMode(false); 
				appCostRprTxtbox.setClickable(false);

				commentsTxtbox.setTextColor(Color.GRAY);
				commentsTxtbox.setFocusable(false);
				commentsTxtbox.setFocusableInTouchMode(false); 
				commentsTxtbox.setClickable(false);
				commentsTxtbox.setTextSize(22);

				buttonSubmit.setVisibility(View.GONE);
				commentFormCancelButton.setVisibility(View.GONE);
				commentformSearchCancelButton.setVisibility(View.VISIBLE);

				commentFormSearchBackButton.setVisibility(View.VISIBLE);
				commentFormBackButton.setVisibility(View.GONE);

			}
		} catch (Exception e) {
			LOG.error("formComponentController:10432");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			//Application.cancelForm(this);
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
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
	
	public void onLoad() {
		try {
			appCostRprTxtbox.setText(formObject.getAppCost());

			//set time visited
			if(formObject.getTimeVisited() != ""){
				String fullDateTimeVisited = formObject.getTimeVisited();
				String datepart = "";
				datepart = fullDateTimeVisited.split(" ")[0];
				datetextboxVisited.setText(datepart.split("/")[0]);
				monthtextboxVisited.setText(datepart.split("/")[1]);
				int startPos = datepart.split("/")[2].length()-2;
				yeartextboxVisited.setText(datepart.split("/")[2].substring(startPos));
				String timepart = fullDateTimeVisited.split(" ")[1];
				String ampm = "";

				if(!formObject.isEditable()){ //when do search (just view)
					String hour = timepart.split(":")[0];
					int realHour = Integer.parseInt(hour);
					if(realHour > 12 && realHour <= 23){ //PM Selection
						ampm = "PM";
						hourstextboxVisited.setText(String.valueOf(realHour - 12));
					}
					else{ //AM Selection
						ampm = "AM";
						if(realHour == 0){
							hourstextboxVisited.setText("12");
						}
						else{
							hourstextboxVisited.setText(String.valueOf(realHour));
						}
					}
					if(ampm.toUpperCase().equals("AM")){	ampmspinnerVisited.setSelection(0);	}
					else { ampmspinnerVisited.setSelection(1); }
				}
				else{ // On The SA Form
					ampm = fullDateTimeVisited.split(" ")[2]; // "PM"; //timepart.split(" ")[2];

					if(timepart.split(":")[0].equals("00")){
						hourstextboxVisited.setText("12");
					}else { hourstextboxVisited.setText(timepart.split(":")[0]); }

					if(ampm.toUpperCase().equals("AM")){	ampmspinnerVisited.setSelection(0);	}
					else { ampmspinnerVisited.setSelection(1); }
				}
				//txtnote

				minutestextboxVisited.setText(timepart.split(":")[1]);
			}

			onsiteestimation.setSelection(Integer.parseInt(formObject.getOnSiteEstimation().trim()));
			//if(!formObject.getisSEARCH()){
				commentsTxtbox.setText(formObject.getComments());
			//}
		} catch (NumberFormatException e) {
			LOG.error("onLoad:10433");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void onSave(){
		try {
			formObject.setAppCost(appCostRprTxtbox.getText().toString().trim());

			int pos = onsiteestimation.getSelectedItemPosition();
			for (CommentOpt fm : CommentOpt.values()) {
				if (onsiteestimation.getItemAtPosition(pos).toString().equals(fm.getString())) {
					formObject.setOnSiteEstimation(Integer.toString(fm.getInt()));
					break;
				}
			}

			//get the selected AM PM value
			int pos2 = ampmspinnerVisited.getSelectedItemPosition();
			String period = "";

			if(pos2 == 0){ period = "AM"; } else period = "PM";

			// Time Visited
			formObject.setTimeVisited(datetextboxVisited.getText().toString().trim() + "/" + monthtextboxVisited.getText().toString().trim() + "/" +
					yeartextboxVisited.getText().toString().trim() + " " + hourstextboxVisited.getText().toString().trim() + ":" +
					minutestextboxVisited.getText().toString().trim() + " " + period);

			formObject.setComments(commentsTxtbox.getText().toString().trim());

			if(!formObject.getisSEARCH()){
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave:10434");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_back_button(View v){
		 try {
			 LOG.debug("ENTRY ",  "on_click_back_button");
			 Application.goForward = false;
			 Application.goBackward = true;
			 onSave();
			 Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.COMMENTFORM_BACK_BUTTON_CLICK, this, formObject));
			 LOG.debug("SUCCESS ","on_click_back_button");
		 } catch (Exception e) {
			 LOG.error("onCron_click_back_button:10435eate");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		 }
	}
	
	public void on_click_cancel_button(View v){
		//Application.cancelForm(this);
		Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
	}

	public void on_click_submit_button(View v){
		try {
			LOG.debug("ENTRY ", "on_click_submit_button");
			onSave();

			String datetextboxErrorVisited = "";
			String monthtextboxErrorVisited = "";
			String yeartextboxErrorVisited = "";
			String hourstextboxErrorVisited = "";
			String minutestextboxErrorVisited = "";

			String totalDateTextboxErrorVisited = "";


			if(formObject.isEditable()){
				if (datetextboxVisited.getText().toString().trim().equals("")) {
					datetextboxErrorVisited = "Please Enter 'Date' in 'Visited Time'.\r\n";
				}
				if (monthtextboxVisited.getText().toString().trim().equals("")) {
					monthtextboxErrorVisited = "Please Enter 'Month' in 'Visited Time'.\r\n";
				}
				if (yeartextboxVisited.getText().toString().trim().equals("")) {
					yeartextboxErrorVisited = "Please Enter 'Year' in 'Visited Time'.\r\n";
				}
				if (hourstextboxVisited.getText().toString().trim().equals("")) {
					hourstextboxErrorVisited = "Please Enter 'Time-Hour' in 'Visited Time'.\r\n";
				}
				if (minutestextboxVisited.getText().toString().trim().equals("")) {
					minutestextboxErrorVisited = "Please Enter 'Time-Minutes' in 'Visited Time'.\r\n";
				}
			}

			//if any field in visited date time being null
			if(datetextboxVisited.getText().toString().trim().equals("")
			|| monthtextboxVisited.getText().toString().trim().equals("")
			|| yeartextboxVisited.getText().toString().trim().equals("")
			|| hourstextboxVisited.getText().toString().trim().equals("")
			|| minutestextboxVisited.getText().toString().trim().equals("")){

				AlertDialog.Builder alertDailog = new AlertDialog.Builder(CommentActivity.this);
				alertDailog.setTitle("SA Form")
						.setMessage("Please fill in details for Visited date and Time")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								})
						.show();
			}else{
				//(valid ones are  \b  \t  \n  \f  \r  \"  \'  \\ )
				//Original exp : ^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$
				String expression = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

				CharSequence inputStr = formObject.getTimeVisited().replace("00:", "12:");

				Pattern pattern = Pattern.compile(expression);
				Matcher matcher = pattern.matcher(inputStr);

				if(!matcher.matches()){
					totalDateTextboxErrorVisited = "Please Enter a valid 'Visited Time'.\r\n";
				}

				if(!matcher.matches()){

					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							CommentActivity.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage(totalDateTextboxErrorVisited);
					alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {}
					});
					alertbox.show();
				}else{
					//check for the future date
					String msg = "";
					String msgA = "";
					Date today = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm a");

					today = (Date)dateFormat.parse(dateFormat.format(today));
					Date timeVisited = (Date)dateFormat.parse(inputStr.toString().trim());
					//check entered date is a future date time compared to today date time
					if(today.before(timeVisited)){
						msg = "'Visited Time' can't be a future date.\r\n";
					}
					if(today.before(timeVisited)){
						AlertDialog.Builder alertbox = new AlertDialog.Builder(
								CommentActivity.this);
						alertbox.setTitle(R.string.saform);
						alertbox.setMessage(msg);
						alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {}
						});
						alertbox.show();
					}else{
						if (Application.getIsOnline() == 0) { //If Offline   0==Offline

							AlertDialog.Builder alertbox = new AlertDialog.Builder(CommentActivity.this);
							alertbox.setTitle(R.string.saform);
							alertbox.setMessage("Do the job submission in online mode.");
							alertbox.setPositiveButton("Save & Goto Home",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {
											onSave();

											Intent mIntent = new Intent(CommentActivity.this, HomeActivity.class);
											mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
											CommentActivity.this.startActivity(mIntent);
											CommentActivity.this.finish();
										}});
							alertbox.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									removeDialog(arg1);
								}});
							alertbox.show();

						} else if (!NetworkCheck.isNetworkAvailable(this)) {//If No network
							AlertDialog.Builder alertbox = new AlertDialog.Builder(CommentActivity.this);
							alertbox.setTitle(R.string.saform);
							alertbox.setMessage(R.string.submit_no_network_msg);
							alertbox.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {
											removeDialog(arg1);
										}});
							alertbox.show();
						} else { //If Online

							//Set the image count
							File f = new File(URL.getSLIC_JOBS() + formObject.getJobNo());
							if(f.exists()){
								List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);

								if(!formObject.getisSEARCH()){
									formObject.setImageCount(Integer.toString(fileList.size()));
								}
							}

							//Set the random number as reference no
							Random randomGenerator = new Random();
							int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);

							//Application.deleteFormResubmitObjectInstance();
							FormResubmitObject objR = Application.createFormResubmitObjectInstance();
							objR.setisSEARCH(Application.getFormObjectInstance().getisSEARCH());
							objR.setRefNo(randomInt+"");
							formObject.setRefNo(randomInt+"");

							AlertDialog.Builder alertbox = new AlertDialog.Builder(CommentActivity.this);
							alertbox.setTitle(R.string.saform);
							alertbox.setCancelable(false);
							alertbox.setMessage("Are you sure you want to submit?");
							alertbox.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {
											onSave();
											//if(Application.automaticlogin())
											Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.SUBMIT_BUTTON_CLICK, CommentActivity.this, formObject));
										}
									});
							alertbox.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									removeDialog(arg1);
								}
							});
							alertbox.show();
						}
					}


				}
			}
			LOG.debug("SUCCESS ","on_click_submit_button");
		} catch (Exception e) {
			LOG.error("on_click_submit_button:10437");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public class MyOnItemSelectedListenertimeVisitedPeriodSelector implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			try {
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (TimePeriods tp : TimePeriods.values()) {
					if (parent.getItemAtPosition(position).toString().equals(tp.getString())) {
						formObject.setRelationshipTimePeriod(Integer.toString(tp.getInt()));
						break;
					}
				}
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				LOG.error("onItemSelected");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			//Do nothing
		}
	}
	
	public class MyOnItemSelectedListenerOnSiteEstimation implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));
				
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
				
				for (Confirmation fm : Confirmation.values()) {
					if (parent.getItemAtPosition(pos).toString()
					.equals(fm.getString())) {
						formObject.setOnSiteEstimation(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10438");
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
}