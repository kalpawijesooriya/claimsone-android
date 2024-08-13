package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.smsreceiver.SMSProcessor;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class AccidentDetailsActivity extends Activity {
	FormObject formObject;
	EditText jobNo;

	TextView dateseparator1;
	TextView dateseparator2;
	TextView timeseparator;
	TextView dateseparator1A;
	TextView dateseparator2A;
	TextView timeseparatorA;
	
	EditText datetextbox;
	EditText monthtextbox;
	EditText yeartextbox;
	EditText hourstextbox;
	EditText minutestextbox;

	EditText datetextboxA;
	EditText monthtextboxA;
	EditText yeartextboxA;
	EditText hourstextboxA;
	EditText minutestextboxA;

	EditText contactNo;
	EditText nameofCaller;
	EditText vehicleNo;
	EditText vehicleTypeandColor;
	EditText locationofAccident;
	
	Spinner timePeriodSpinner;
	Spinner timePeriodSpinnerA;

	InputMethodManager imm;

	final static int TIME_REPORTED_DATEPICKER = 0;
	final static int ACCIDENT_DATEPICKER = 1;

	Logger LOG = LoggerFactory.getLogger(AccidentDetailsActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("onCreate");

			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			setContentView(R.layout.accidentform);

			formObject = Application.getFormObjectInstance();
			Application.setIsVisit(false); //just to be safe

			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			jobNo = (EditText) findViewById(R.id.jobnotext);
			datetextbox = (EditText) findViewById(R.id.datetextbox);
			monthtextbox = (EditText) findViewById(R.id.monthtextbox);
			yeartextbox = (EditText) findViewById(R.id.yeartextbox);
			hourstextbox = (EditText) findViewById(R.id.hourstextbox);
			minutestextbox = (EditText) findViewById(R.id.minutestextbox);

			dateseparator1 = (TextView) findViewById(R.id.dateseparator1);
			dateseparator2 = (TextView) findViewById(R.id.dateseparator2);
			timeseparator = (TextView) findViewById(R.id.timeseparator);
			dateseparator1A = (TextView) findViewById(R.id.dateseparator1A);
			dateseparator2A = (TextView) findViewById(R.id.dateseparator2A);
			timeseparatorA = (TextView) findViewById(R.id.timeseparatorA);
			
			timePeriodSpinner = (Spinner) findViewById(R.id.ampmspinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					this, R.array.timePeriodArr,
					R.layout.textviewforspinner_small);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			timePeriodSpinner.setAdapter(adapter);
			timePeriodSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenertimePeriodSelector());
			timePeriodSpinner.setSelection(0); //Integer.parseInt(formObject.getTimePeriods())); //.trim())-1

			contactNo = (EditText) findViewById(R.id.contactnotext);
			nameofCaller = (EditText) findViewById(R.id.nameofcallertext);
			vehicleNo = (EditText) findViewById(R.id.vehiclenotext);
			vehicleTypeandColor = (EditText) findViewById(R.id.vehicletypeandcolortext);
			
			datetextboxA = (EditText) findViewById(R.id.datetextboxA);
			monthtextboxA = (EditText) findViewById(R.id.monthtextboxA);
			yeartextboxA = (EditText) findViewById(R.id.yeartextboxA);
			hourstextboxA = (EditText) findViewById(R.id.hourstextboxA);
			minutestextboxA = (EditText) findViewById(R.id.minutestextboxA);

			timePeriodSpinnerA = (Spinner) findViewById(R.id.ampmspinnerA);
			ArrayAdapter<CharSequence> adapterA = ArrayAdapter.createFromResource(
					this, R.array.timePeriodArr,
					R.layout.textviewforspinner_small);
			adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			timePeriodSpinnerA.setAdapter(adapterA);
			timePeriodSpinnerA
			.setOnItemSelectedListener(new MyOnItemSelectedListenertimePeriodSelector());
			timePeriodSpinnerA.setSelection(0);
			locationofAccident = (EditText) findViewById(R.id.locationofaccidenttext);

			//According to the state different source will update the FormObject
			if(formObject.getisSMS()){
				isSMSFunc();
			}
			/*if(formObject.getisDRAFT() && (formObject.getOrgTimeReported() == null || formObject.getOrgTimeReported().isEmpty())){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");	// "MMMMM d, yyyy h:mm a");
				String StartDateTime = formatter.format(Calendar.getInstance().getTime());
				formObject.setOrgTimeReported(StartDateTime);
			}*/

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");			
		}
		catch (Exception e) {
			LOG.error("onCreate");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return;
		}
	}

	private void isSMSFunc(){
		// load the data from a SMS
		String MSG = "";
		Boolean FROM_SMS = false;

		boolean[] errArr = new boolean[6];

		try {
			Bundle extras = this.getIntent().getExtras();
			if(extras != null)
			{
				MSG = extras.getString("MSG").toString();
				FROM_SMS = Boolean.valueOf(extras.getString("FROM_SMS"));

				if(FROM_SMS){  // if data is from a msg
					formObject.setEditable(true);

					// Used to freeze the jobNo field
					//formComponentControler();

					//split it and assign it to the formObject
					String[] sms = MSG.toString().split("#");
					//LogFile.d("\n-*-*-", MSG.toString() + "\n");

					if (sms.length == 18) { //for new sms format
						formObject.setJobNo(sms[2]); 							//ok
						formObject.setVehicleNo(sms[3]);						//ok
						formObject.setNameofInsured(sms[4]);					//ok
						formObject.setDriversName(sms[5]);						//ok
						formObject.setContactNo(sms[6]);						//ok
						formObject.setPolicyCoverNoteNo(sms[7]);				//ok

						String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
						Pattern pattern = Pattern.compile(expression);

						if (!sms[8].equals("")) {
							CharSequence inputStr = sms[8];
							Matcher matcher = pattern.matcher(inputStr);
							if(!matcher.matches()){
								errArr[0] = true;
							}
							else{
								formObject.setPolicyCoverNotePeriodFrom(sms[8]);
							}
						}

						if (!sms[9].equals("")) {
							CharSequence inputStr = sms[9];
							Matcher matcher = pattern.matcher(inputStr);
							if(!matcher.matches()){
								errArr[1] = true;
							}
							else{
								formObject.setPolicyCoverNotePeriodTo(sms[9]);
							}
						}

						if (!sms[12].equals("")) {
							String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

							CharSequence inputStr1 = sms[12].toUpperCase();
							Pattern pattern1 = Pattern.compile(expression1);
							Matcher matcher1 = pattern1.matcher(inputStr1);
							if(!matcher1.matches()){
								//No need to set default date will be assigned
								//It assigned when formObject's init process
								//formObject.setTimeReported(date);
								formObject.setOrgTimeReported("");
								errArr[2] = true;
							} else {
								formObject.setTimeReported(sms[12].toUpperCase());
								formObject.setOrgTimeReported(sms[12].toUpperCase());
							}
						} else{

							//formObject.setTimeReported(date);
							formObject.setOrgTimeReported("");
						}

						if (!sms[13].equals("")) {
							String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

							CharSequence inputStr2 = sms[13].toUpperCase();
							Pattern pattern1 = Pattern.compile(expression1);
							Matcher matcher1 = pattern1.matcher(inputStr2);
							if(!matcher1.matches()){
								//No need to set default date will be assigned
								//It assigned when formObject's init process
								//formObject.setDateandTimeofAccident(date);
								errArr[5] = true;
							} else {
								formObject.setDateandTimeofAccident(sms[13].toUpperCase());
							}
						} else{
							//No need to set default date will be assigned
							//It assigned when formObject's init process
							//formObject.setDateandTimeofAccident(date);
						}

						formObject.setNameofCaller(sms[10]);					//ok
						formObject.setVehicleTypeandColor(sms[11]);				//ok
						formObject.setLocationofAccident(sms[14]);				//ok
						if(sms[7] == null || sms[7].trim().equals(""))
							formObject.setPolicyCoverNoteNo(sms[15]);			//ok
						formObject.setNearestPoliceStation(sms[16]);			//ok

						formObject.setisDRAFT(false);
						formObject.setisSMS(true);
						formObject.setisSEARCH(false);
					}
					else if(sms.length == 17){ //for old sms format
						formObject.setJobNo(sms[2]); 							//ok
						formObject.setVehicleNo(sms[3]);						//ok
						formObject.setNameofInsured(sms[4]);					//ok
						formObject.setDriversName(sms[5]);						//ok
						formObject.setContactNo(sms[6]);						//ok
						formObject.setPolicyCoverNoteNo(sms[7]);				//ok

						String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
						Pattern pattern = Pattern.compile(expression);

						if (!sms[8].equals("")) {
							CharSequence inputStr = sms[8];
							Matcher matcher = pattern.matcher(inputStr);
							if(!matcher.matches()){
								errArr[0] = true;
							}
							else{
								formObject.setPolicyCoverNotePeriodFrom(sms[8]);
							}
						}

						if (!sms[9].equals("")) {
							CharSequence inputStr = sms[9];
							Matcher matcher = pattern.matcher(inputStr);
							if(!matcher.matches()){
								errArr[1] = true;
							}
							else{
								formObject.setPolicyCoverNotePeriodTo(sms[9]);
							}
						}

						if (!sms[12].equals("")) {
							String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

							CharSequence inputStr1 = sms[12].toUpperCase();
							Pattern pattern1 = Pattern.compile(expression1);
							Matcher matcher1 = pattern1.matcher(inputStr1);
							if(!matcher1.matches()){
								//No need to set default date will be assigned
								//It assigned when formObject's init process
								//formObject.setTimeReported(date);
								formObject.setOrgTimeReported("");
								errArr[2] = true;
							} else {
								formObject.setTimeReported(sms[12].toUpperCase());
								formObject.setOrgTimeReported(sms[12].toUpperCase());
							}
						} else{

							//formObject.setTimeReported(date);
							formObject.setOrgTimeReported("");
						}

						/*if (!sms[13].equals("")) {
							String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

							CharSequence inputStr2 = sms[13].toUpperCase();
							Pattern pattern1 = Pattern.compile(expression1);
							Matcher matcher1 = pattern1.matcher(inputStr2);
							if(!matcher1.matches()){
								//No need to set default date will be assigned
								//It assigned when formObject's init process
								//formObject.setDateandTimeofAccident(date);
								errArr[5] = true;
							} else {
								formObject.setDateandTimeofAccident(sms[13].toUpperCase());
							}
						} else{
							//No need to set default date will be assigned
							//It assigned when formObject's init process
							//formObject.setDateandTimeofAccident(date);
						}*/

						formObject.setNameofCaller(sms[10]);					//ok
						formObject.setVehicleTypeandColor(sms[11]);				//ok
						formObject.setLocationofAccident(sms[13]);				//ok
						if(sms[7] == null || sms[7].trim().equals(""))
							formObject.setPolicyCoverNoteNo(sms[14]);			//ok
						formObject.setNearestPoliceStation(sms[15]);			//ok

						formObject.setisDRAFT(false);
						formObject.setisSMS(true);
						formObject.setisSEARCH(false);
					}
					else{
						errArr[3] = true;
					}
				}
			}
		}
		catch (Exception e) {
			errArr[4] = true;
			LOG.error("isSMSFunc");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		composeNsendSMS(errArr, this.getString(R.string.CALL_CENTER_NO));
	}


	private void  composeNsendSMS(boolean[] arr, String CC_No){
		try {
			String tabMsg = SMSProcessor.composeNsendSMS(arr, CC_No);

			if(!tabMsg.isEmpty()){
				//sendSMS(CC_No, "Received SMS was incomplete.");
				if(formObject.getJobNo().isEmpty()){
					sendSMS(CC_No, getString(R.string.mtabicomp_));
				}else{
					sendSMS(CC_No, getString(R.string.mtabicomp_) + formObject.getJobNo());
				}

				String title = "SMS Details:";
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setTitle(title);
				alertbox.setMessage(tabMsg);
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
			}
		} catch (Exception e) {
			LOG.error("onCreate");
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
			
			formObject = Application.getFormObjectInstance();
			formComponentControler();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onCreate");
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
			LOG.error("onPause");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void formComponentControler(){
		try {
			if(formObject.getisDRAFT()){/*
				jobNo.setFocusable(true);
				jobNo.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
				jobNo.setClickable(true);

				datetextbox.setFocusable(true);
				datetextbox.setFocusableInTouchMode(true);
				datetextbox.setClickable(true);

				monthtextbox.setFocusable(true);
				monthtextbox.setFocusableInTouchMode(true);
				monthtextbox.setClickable(true);

				yeartextbox.setFocusable(true);
				yeartextbox.setFocusableInTouchMode(true);
				yeartextbox.setClickable(true);

				hourstextbox.setFocusable(true);
				hourstextbox.setFocusableInTouchMode(true);
				hourstextbox.setClickable(true);

				minutestextbox.setFocusable(true);
				minutestextbox.setFocusableInTouchMode(true);
				minutestextbox.setClickable(true);

				timePeriodSpinner.setFocusable(true);
				timePeriodSpinner.setFocusableInTouchMode(true);
				timePeriodSpinner.setClickable(true);

				contactNo.setFocusable(true);
				contactNo.setFocusableInTouchMode(true); 
				contactNo.setClickable(true);

				nameofCaller.setFocusable(true);
				nameofCaller.setFocusableInTouchMode(true); 
				nameofCaller.setClickable(true);

				vehicleNo.setFocusable(true);
				vehicleNo.setFocusableInTouchMode(true); 
				vehicleNo.setClickable(true);

				vehicleTypeandColor.setFocusable(true);
				vehicleTypeandColor.setFocusableInTouchMode(true); 
				vehicleTypeandColor.setClickable(true);

				datetextboxA.setFocusable(true);
				datetextboxA.setFocusableInTouchMode(true);
				datetextboxA.setClickable(true);

				monthtextboxA.setFocusable(true);
				monthtextboxA.setFocusableInTouchMode(true);
				monthtextboxA.setClickable(true);

				yeartextboxA.setFocusable(true);
				yeartextboxA.setFocusableInTouchMode(true);
				yeartextboxA.setClickable(true);

				hourstextboxA.setFocusable(true);
				hourstextboxA.setFocusableInTouchMode(true);
				hourstextboxA.setClickable(true);

				minutestextboxA.setFocusable(true);
				minutestextboxA.setFocusableInTouchMode(true);
				minutestextboxA.setClickable(true);

				timePeriodSpinnerA.setFocusable(true);
				timePeriodSpinnerA.setFocusableInTouchMode(true);
				timePeriodSpinnerA.setClickable(true);

				locationofAccident.setFocusable(true);
				locationofAccident.setFocusableInTouchMode(true); 
				locationofAccident.setClickable(true);
			*/}

			if(formObject.getisSMS()){
				jobNo.setEnabled(false);
				jobNo.setTextColor(Color.GRAY);

				//----------Temporary deactivated as client requested 2017-01-26----------
				//datetextboxA.setEnabled(false);
				//datetextboxA.setTextColor(Color.GRAY);

				//monthtextboxA.setEnabled(false);
				//monthtextboxA.setTextColor(Color.GRAY);

				//yeartextboxA.setEnabled(false);
				//yeartextboxA.setTextColor(Color.GRAY);

				//hourstextboxA.setEnabled(false);
				//hourstextboxA.setTextColor(Color.GRAY);

				//minutestextboxA.setEnabled(false);
				//minutestextboxA.setTextColor(Color.GRAY);
				
				//timePeriodSpinnerA.setFocusable(false);
				//timePeriodSpinnerA.setFocusableInTouchMode(false);
				//timePeriodSpinnerA.setClickable(false);
				//-------------------------------------------------------------
				
				/*datetextbox.setFocusable(true);
				datetextbox.setFocusableInTouchMode(true);
				datetextbox.setClickable(true);

				monthtextbox.setFocusable(true);
				monthtextbox.setFocusableInTouchMode(true);
				monthtextbox.setClickable(true);

				yeartextbox.setFocusable(true);
				yeartextbox.setFocusableInTouchMode(true);
				yeartextbox.setClickable(true);

				hourstextbox.setFocusable(true);
				hourstextbox.setFocusableInTouchMode(true);
				hourstextbox.setClickable(true);

				minutestextbox.setFocusable(true);
				minutestextbox.setFocusableInTouchMode(true);
				minutestextbox.setClickable(true);

				timePeriodSpinner.setFocusable(true);
				timePeriodSpinner.setFocusableInTouchMode(true);
				timePeriodSpinner.setClickable(true);

				contactNo.setFocusable(true);
				contactNo.setFocusableInTouchMode(true); 
				contactNo.setClickable(true);

				nameofCaller.setFocusable(true);
				nameofCaller.setFocusableInTouchMode(true); 
				nameofCaller.setClickable(true);

				vehicleNo.setFocusable(true);
				vehicleNo.setFocusableInTouchMode(true); 
				vehicleNo.setClickable(true);

				vehicleTypeandColor.setFocusable(true);
				vehicleTypeandColor.setFocusableInTouchMode(true); 
				vehicleTypeandColor.setClickable(true);

				datetextboxA.setFocusable(true);
				datetextboxA.setFocusableInTouchMode(true);
				datetextboxA.setClickable(true);

				monthtextboxA.setFocusable(true);
				monthtextboxA.setFocusableInTouchMode(true);
				monthtextboxA.setClickable(true);

				yeartextboxA.setFocusable(true);
				yeartextboxA.setFocusableInTouchMode(true);
				yeartextboxA.setClickable(true);

				hourstextboxA.setFocusable(true);
				hourstextboxA.setFocusableInTouchMode(true);
				hourstextboxA.setClickable(true);

				minutestextboxA.setFocusable(true);
				minutestextboxA.setFocusableInTouchMode(true);
				minutestextboxA.setClickable(true);

				timePeriodSpinnerA.setFocusable(true);
				timePeriodSpinnerA.setFocusableInTouchMode(true);
				timePeriodSpinnerA.setClickable(true);

				locationofAccident.setFocusable(true);
				locationofAccident.setFocusableInTouchMode(true); 
				locationofAccident.setClickable(true);*/
			}

			if(formObject.getisSEARCH()){
				jobNo.setEnabled(false);
				jobNo.setTextColor(Color.GRAY);

				dateseparator1.setTextColor(Color.GRAY);
				dateseparator2.setTextColor(Color.GRAY);
				timeseparator.setTextColor(Color.GRAY);
				dateseparator1A.setTextColor(Color.GRAY);
				dateseparator2A.setTextColor(Color.GRAY);
				timeseparatorA.setTextColor(Color.GRAY);
				
				datetextbox.setEnabled(false);
				datetextbox.setTextColor(Color.GRAY);

				monthtextbox.setEnabled(false);
				monthtextbox.setTextColor(Color.GRAY);

				yeartextbox.setEnabled(false);
				yeartextbox.setTextColor(Color.GRAY);

				hourstextbox.setEnabled(false);
				hourstextbox.setTextColor(Color.GRAY);

				minutestextbox.setEnabled(false);
				minutestextbox.setTextColor(Color.GRAY);

				timePeriodSpinner.setFocusable(false);
				timePeriodSpinner.setFocusableInTouchMode(false);
				timePeriodSpinner.setClickable(false);

				contactNo.setEnabled(false);
				contactNo.setTextColor(Color.GRAY);

				nameofCaller.setTextColor(Color.GRAY);
				nameofCaller.setFocusable(false);
				nameofCaller.setFocusableInTouchMode(false); 
				nameofCaller.setClickable(false);

				vehicleNo.setEnabled(false);
				vehicleNo.setTextColor(Color.GRAY);

				vehicleTypeandColor.setTextColor(Color.GRAY);
				vehicleTypeandColor.setFocusable(false);
				vehicleTypeandColor.setFocusableInTouchMode(false); 
				vehicleTypeandColor.setClickable(false);

				datetextboxA.setEnabled(false);
				datetextboxA.setTextColor(Color.GRAY);

				monthtextboxA.setEnabled(false);
				monthtextboxA.setTextColor(Color.GRAY);

				yeartextboxA.setEnabled(false);
				yeartextboxA.setTextColor(Color.GRAY);

				hourstextboxA.setEnabled(false);
				hourstextboxA.setTextColor(Color.GRAY);

				minutestextboxA.setEnabled(false);
				minutestextboxA.setTextColor(Color.GRAY);
				
				timePeriodSpinnerA.setFocusable(false);
				timePeriodSpinnerA.setFocusableInTouchMode(false);
				timePeriodSpinnerA.setClickable(false);

				locationofAccident.setTextColor(Color.GRAY);
				locationofAccident.setFocusable(false);
				locationofAccident.setFocusableInTouchMode(false); 
				locationofAccident.setClickable(false);
			}
		} catch (Exception e) {
			LOG.error("onCreate");
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

	

	public void on_click_cancel_button(View v){
		//Application.cancelForm(this);
		Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
	}

	public void on_click_next_button(View v) {
		try {
			LOG.debug("on_click_next_button");
		
			onSave();

			String jobNoError = "";
			String vehicleNoError = "";
			String accidentLocationError = "";
			String totalDateTextboxError = "";
			String totalDateTextboxErrorA = "";

			String datetextboxError = "";
			String monthtextboxError = "";
			String yeartextboxError = "";
			String hourstextboxError = "";
			String minutestextboxError = "";

			String datetextboxErrorA = "";
			String monthtextboxErrorA = "";
			String yeartextboxErrorA = "";
			String hourstextboxErrorA = "";
			String minutestextboxErrorA = "";

			//-------------------------------------------------------------------------------
			if(formObject.isEditable()){

				if (jobNo.getText().toString().trim().equals("")) {
					jobNoError = "Please Enter 'Job No'.\r\n";
				}

				if (datetextbox.getText().toString().trim().equals("")) {
					datetextboxError = "Please Enter 'Date' in 'Time Reported'.\r\n";
				}
				if (monthtextbox.getText().toString().trim().equals("")) {
					monthtextboxError = "Please Enter 'Month' in 'Time Reported'.\r\n";
				}
				if (yeartextbox.getText().toString().trim().equals("")) {
					yeartextboxError = "Please Enter 'Year' in 'Time Reported'.\r\n";
				}
				if (hourstextbox.getText().toString().trim().equals("")) {
					hourstextboxError = "Please Enter 'Time-Hour' in 'Time Reported'.\r\n";
				}
				if (minutestextbox.getText().toString().trim().equals("")) {
					minutestextboxError = "Please Enter 'Time-Minutes' in 'Time Reported'.\r\n";
				}

				if (datetextboxA.getText().toString().trim().equals("")) {
					datetextboxErrorA = "Please Enter 'Date' in 'Date and Time of Accident'.\r\n";
				}
				if (monthtextboxA.getText().toString().trim().equals("")) {
					monthtextboxErrorA = "Please Enter 'Month' in 'Date and Time of Accident'.\r\n";
				}
				if (yeartextboxA.getText().toString().trim().equals("")) {
					yeartextboxErrorA = "Please Enter 'Year' in 'Date and Time of Accident'.\r\n";
				}
				if (hourstextboxA.getText().toString().trim().equals("")) {
					hourstextboxErrorA = "Please Enter 'Time-Hour' in 'Date and Time of Accident'.\r\n";
				}
				if (minutestextboxA.getText().toString().trim().equals("")) {
					minutestextboxErrorA = "Please Enter 'Time-Minutes' in 'Date and Time of Accident'.\r\n";
				}

				if (vehicleNo.getText().toString().trim().equals("")) {
					vehicleNoError = "Please Enter 'Vehicle No'.\r\n";
				}
				if (locationofAccident.getText().toString().trim().equals("")) {
					accidentLocationError = "Please Enter 'Location of Accident'.";
				}
				/*if (timeReportedDemo.getText().toString().equals("")) {
				dateTextboxError = "Please Enter 'Time Reported'.\r\n";
			}*/

				//If fields are NULL
				if (jobNo.getText().toString().trim().equals("")
						|| vehicleNo.getText().toString().trim().equals("")
						|| locationofAccident.getText().toString().trim().equals("")
						|| datetextbox.getText().toString().trim().equals("")
						|| monthtextbox.getText().toString().trim().equals("")
						|| yeartextbox.getText().toString().trim().equals("")
						|| hourstextbox.getText().toString().trim().equals("")
						|| minutestextbox.getText().toString().trim().equals("")

						|| datetextboxA.getText().toString().trim().equals("")
						|| monthtextboxA.getText().toString().trim().equals("")
						|| yeartextboxA.getText().toString().trim().equals("")
						|| hourstextboxA.getText().toString().trim().equals("")
						|| minutestextboxA.getText().toString().trim().equals("")){

					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							AccidentDetailsActivity.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage(jobNoError + datetextboxError + monthtextboxError +
							yeartextboxError + hourstextboxError + minutestextboxError + 
							datetextboxErrorA + monthtextboxErrorA +
							yeartextboxErrorA + hourstextboxErrorA + minutestextboxErrorA +
							vehicleNoError + accidentLocationError);
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertbox.show();
				} else {
					try {
						//(valid ones are  \b  \t  \n  \f  \r  \"  \'  \\ )
						//Original exp : ^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$
						String expression = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

						CharSequence inputStr = formObject.getTimeReported().replace("00:", "12:");
						CharSequence inputStrA = formObject.getDateandTimeofAccident().replace("00:", "12:");

						Pattern pattern = Pattern.compile(expression);
						Matcher matcher = pattern.matcher(inputStr);
						Matcher matcherA = pattern.matcher(inputStrA);

						if(!matcher.matches()){
							totalDateTextboxError = "Please Enter a valid 'Time Reported'.\r\n";
						}
						if(!matcherA.matches()){
							totalDateTextboxErrorA = "Please Enter a valid 'Date and Time of Accident'.\r\n";
						}

						if(!matcher.matches() || !matcherA.matches()){

							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									AccidentDetailsActivity.this);
							alertbox.setTitle(R.string.saform);
							alertbox.setMessage(totalDateTextboxError + totalDateTextboxErrorA);
							alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {}
							});
							alertbox.show();
						}
						/*if(true)
					{
						AlertDialog.Builder alertbox = new AlertDialog.Builder(
								AccidentDetailsActivity.this);
						alertbox.setTitle("Accident Details:");
						alertbox.setMessage("input str: " + inputStr.toString() +" input strA :"+ inputStrA.toString());
						alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {}
								});
						alertbox.show();
					}*/
						else
						{
							//check for the future date
							String msg = "";
							String msgA = "";
							Date today = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm a");

							today = (Date)dateFormat.parse(dateFormat.format(today));
							Date timereported = (Date)dateFormat.parse(inputStr.toString().trim());
							Date timereportedA = (Date)dateFormat.parse(inputStrA.toString().trim());

							//check entered date is a future date time compared to today date time 
							if(today.before(timereported)){
								msg = "'Time Reported' can't be a future date.\r\n";
							}
							if(today.before(timereportedA)){
								msgA = "'Date and Time of Accident' can't be a future date.\r\n";
							}
							if(today.before(timereported) || today.before(timereportedA)){
								AlertDialog.Builder alertbox = new AlertDialog.Builder(
										AccidentDetailsActivity.this);
								alertbox.setTitle(R.string.saform);
								alertbox.setMessage(msg + msgA);
								alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {}
								});
								alertbox.show();
							}
							else{
								if(!formObject.getisSEARCH()){

									FileOperations.draftRenamer(formObject);
									//if(!FileOperations.draftsMaxReached()){

									//used to change the job folder name if job no changed
									//after adding images.
									if(!formObject.getJobNo().trim().equalsIgnoreCase("")){
										if(!formObject.getJobNo().trim().equalsIgnoreCase(jobNo.getText().toString().trim())){
											String filepath = URL.getSLIC_JOBS();
											File oldName = new File(filepath + formObject.getJobNo().trim());
											File newName = new File(filepath + jobNo.getText().toString().trim());
											if(oldName.exists()){
												oldName.renameTo(newName);
											}
										}
									}

									onSave();

									Application.goForward = true;
									Application.goBackward = false;
									Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.ACCIDENTDETAILS_NEXT_BUTTON_CLICK, this, formObject));

									/*}else{

										AlertDialog.Builder alertbox = new AlertDialog.Builder(
												AccidentDetailsActivity.this);
										alertbox.setTitle("Draft Alert:");
										alertbox.setMessage(getString(R.string.max_no_of_drafts_10_has_been_reached_please_deal_with_some_drafts_to_continue_));
										alertbox.setPositiveButton("Ok", 
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface arg0, int arg1) {
														Intent intent = new Intent(AccidentDetailsActivity.this, DraftActivity.class);
														  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
														  AccidentDetailsActivity.this.startActivity(intent);
														  //finish();
													}
												});
										alertbox.show();
									}*/
								} else{
									Application.goForward = true;
									Application.goBackward = false;
									Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.ACCIDENTDETAILS_NEXT_BUTTON_CLICK, this, formObject));
								}
							}

						}} catch (Exception e) {
							LOG.error("on_click_next_button");
							   if(e != null){
								LOG.error("Message: " + e.getMessage());
								LOG.error("StackTrace: " + e.getStackTrace());
							  }
						}
				} //end else
			}
			else{  //goto next page without any validation
				Application.goForward = true;
				Application.goBackward = false;
				Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.ACCIDENTDETAILS_NEXT_BUTTON_CLICK, this, formObject));
			}
			LOG.debug("SUCCESS on_click_next_button");;
		} catch (Exception e) {
			LOG.error("on_click_next_button");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public void onLoad() {
		try {
			// Job No:
			jobNo.setText(formObject.getJobNo());
			// Time Reported:
			String fulldatettime = formObject.getTimeReported();
			String datepart = "";
			datepart = fulldatettime.split(" ")[0];
			datetextbox.setText(datepart.split("/")[0]);
			monthtextbox.setText(datepart.split("/")[1]);
			int startPos = datepart.split("/")[2].length()-2;
			yeartextbox.setText(datepart.split("/")[2].substring(startPos));
			String timepart = fulldatettime.split(" ")[1];
			String ampm = "";

			if(!formObject.isEditable()){ //when do search (just view)
				String hour = timepart.split(":")[0];
				int realHour = Integer.parseInt(hour);
				if(realHour > 12 && realHour <= 23){ //PM Selection
					ampm = "PM";
					hourstextbox.setText(String.valueOf(realHour - 12));
				}
				else{ //AM Selection
					ampm = "AM";
					if(realHour == 0){
						hourstextbox.setText("12");
					}
					else{
						hourstextbox.setText(String.valueOf(realHour));
					}
				}
				if(ampm.toUpperCase().equals("AM")){	timePeriodSpinner.setSelection(0);	}
				else { timePeriodSpinner.setSelection(1); }
			}
			else{ // On The SA Form
				ampm = fulldatettime.split(" ")[2]; // "PM"; //timepart.split(" ")[2];

				if(timepart.split(":")[0].equals("00")){
					hourstextbox.setText("12");
				}else { hourstextbox.setText(timepart.split(":")[0]); }

				if(ampm.toUpperCase().equals("AM")){	timePeriodSpinner.setSelection(0);	}
				else { timePeriodSpinner.setSelection(1); }
			}
			//txtnote

			minutestextbox.setText(timepart.split(":")[1]);

			String fulldatettimeA = formObject.getDateandTimeofAccident();
			//String StartDateTimeampmA = formatter.format(fulldatettimeA);   //use to het the ampm part using 'a' in the formatter
			String datepartA = fulldatettimeA.split(" ")[0];
			String timepartA = fulldatettimeA.split(" ")[1];
			String ampmA = ""; 		//StartDateTimeampmA.split(" ")[2]; //"AM";  //fulldatettimeA.split(" ")[2];

			datetextboxA.setText(datepartA.split("/")[0]);
			monthtextboxA.setText(datepartA.split("/")[1]);
			int startPosA = datepartA.split("/")[2].length()-2;
			//yeartextboxA.setText(datepartA.split("/")[2]);
			yeartextboxA.setText(datepartA.split("/")[2].substring(startPosA));

			//hourstextboxA.setText(timepartA.split(":")[0]);
			/*if(timepartA.split(":")[0].equals("00")){
				hourstextboxA.setText("12");
			}else { hourstextboxA.setText(timepartA.split(":")[0]); }*/
			//-----------------------------------------------------------------------------------------------------
			if(!formObject.isEditable()){ //when do search (just view)

				String hourA = timepartA.split(":")[0];
				int realHourA = Integer.parseInt(hourA);

				if(realHourA > 12 && realHourA <= 23){ //PM Selection
					ampmA = "PM";
					realHourA = realHourA - 12;
					hourstextboxA.setText(String.valueOf(realHourA));
				}
				else{ //AM Selection
					ampmA = "AM";
					if(realHourA == 0){
						hourstextboxA.setText("12");
					}
					else{
						hourstextboxA.setText(String.valueOf(realHourA));
					}
				}
				if(ampmA.toUpperCase().equals("AM")){	timePeriodSpinnerA.setSelection(0);	}
				else { timePeriodSpinnerA.setSelection(1); }
			}
			else{ // On The SA Form
				ampmA = fulldatettimeA.split(" ")[2];	//"AM"; //timepartA.split(" ")[2];

				if(timepartA.split(":")[0].equals("00")){
					hourstextboxA.setText("12");
				}else { hourstextboxA.setText(timepartA.split(":")[0]); }

				if(ampmA.toUpperCase().equals("AM")){	timePeriodSpinnerA.setSelection(0);	}
				else { timePeriodSpinnerA.setSelection(1); }
			}
			//-----------------------------------------------------------------------------------------------------
			minutestextboxA.setText(timepartA.split(":")[1]);

			// Contact No:
			contactNo.setText(formObject.getContactNo());
			// Name of Caller:
			nameofCaller.setText(formObject.getNameofCaller());
			// Vehicle No:

			vehicleNo.setText(formObject.getVehicleNo());

			// Vehicle Type And Color:

			vehicleTypeandColor.setText(formObject.getVehicleTypeandColor());

			// Date & Time of Accident:
			// timeofAccident.setText(formObject.getDateandTimeofAccident());

			// Location of Accident/Inspection:
			locationofAccident.setText(formObject.getLocationofAccident());
		} catch (NumberFormatException e) {
			LOG.error("onLoad");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("onLoad");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onSave() {
		try {
			// Job No:
			formObject.setJobNo(jobNo.getText().toString().trim());

			//get the selected AM PM value
			int pos = timePeriodSpinner.getSelectedItemPosition();
			int posA = timePeriodSpinnerA.getSelectedItemPosition();
			String period = "";
			String periodA = "";

			if(pos == 0){ period = "AM"; }
			else period = "PM";

			if(posA == 0){ periodA = "AM"; }
			else periodA = "PM";

			// Time Reported:
			formObject.setTimeReported(datetextbox.getText().toString().trim() + "/" + monthtextbox.getText().toString().trim() + "/" +
					yeartextbox.getText().toString().trim() + " " + hourstextbox.getText().toString().trim() + ":" + 
					minutestextbox.getText().toString().trim() + " " + period);

			// Contact No:
			formObject.setContactNo(contactNo.getText().toString().trim());
			// Name of Caller:
			formObject.setNameofCaller(nameofCaller.getText().toString().trim());
			// Vehicle No:
			formObject.setVehicleNo(vehicleNo.getText().toString().trim());
			// Vehicle Type And Color:
			formObject.setVehicleTypeandColor(vehicleTypeandColor.getText()
					.toString().trim());
			// Date & Time of Accident:
			formObject.setDateandTimeofAccident(datetextboxA.getText().toString().trim() + "/" + monthtextboxA.getText().toString().trim() + "/" +
					yeartextboxA.getText().toString().trim() + " " + hourstextboxA.getText().toString().trim() + ":" + 
					minutestextboxA.getText().toString().trim() + " " + periodA);

			// Location of Accident/Inspection:
			formObject.setLocationofAccident(locationofAccident.getText().toString().trim());

			/*		if(!formObject.getisSEARCH()){
				FileOperations.draftRenamer(formObject);
				if(!FileOperations.draftsMaxReached()){
				FormObjSerializer.serializeFormData();
				}else{
					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							AccidentDetailsActivity.this);
					alertbox.setTitle("Draft Alert:");
					alertbox.setMessage("Max no of drafts(10) has been reached. Please deal with some drafts to continue.");
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
								}
							});
					alertbox.show();
				}
			}*/
			if(!formObject.getisSEARCH()){
				FileOperations.draftRenamer(formObject);
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public class MyOnItemSelectedListenertimePeriodSelector implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
				
				for (TimePeriods tp : TimePeriods.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(tp.getString())) {
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

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	public void sendSMS(String phoneNumber, String message)
	{
		try{

			SMSProcessor.sendSMS(phoneNumber, message, AccidentDetailsActivity.this, getBaseContext());

			/*String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(AccidentDetailsActivity.this, "SMS sent", 
                                Toast.LENGTH_LONG).show();
                        LogFile.d("INFO", TAG + "SMS reply status: SMS sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(AccidentDetailsActivity.this, "Generic failure", 
                                Toast.LENGTH_LONG).show();
                        LogFile.d("INFO ", TAG + "SMS reply status: Generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(AccidentDetailsActivity.this, "No service", 
                                Toast.LENGTH_LONG).show();
                        LogFile.d("INFO ", TAG + "SMS reply status: No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(AccidentDetailsActivity.this, "Null PDU", 
                                Toast.LENGTH_LONG).show();
                        LogFile.d("INFO ", TAG + "SMS reply status: Null PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(AccidentDetailsActivity.this, "Radio off", 
                                Toast.LENGTH_LONG).show();
                        LogFile.d("INFO ", TAG + "SMS reply status: Radio off");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
        	@Override
        	public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(AccidentDetailsActivity.this, "SMS is delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(AccidentDetailsActivity.this, "SMS is not delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);*/

		}catch (Exception e) {
			LOG.error("sendSMS");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}}
}
