package com.irononetech.android.formcomponent.view;

import java.util.Calendar;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class InsuranceDetailsActivity extends Activity{
	FormObject formObject;
	EditText nameofInsured;
	EditText contactNoofInsured;
	EditText policyCoverNoteNo;
	
	TextView dateseparator1IF;
	TextView dateseparator2IF;
	TextView dateseparator1IF2;
	TextView dateseparator2IF2;
	
	EditText datetextbox;
	EditText monthtextbox;
	EditText yeartextbox;

	EditText datetextboxA;
	EditText monthtextboxA;
	EditText yeartextboxA;
	
	EditText policyCoverNoteSerialNo;
	EditText coverNoteIssuedBy;
	EditText reasonsforIssuingCoverNote;

	InputMethodManager imm;
	final static int FROM_DATEPICKER = 0;
	final static int TO_DATEPICKER = 1;
	static Logger LOG = LoggerFactory.getLogger(InsuranceDetailsActivity.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			LOG.debug("ENTRY onCreate");
			super.onCreate(savedInstanceState);
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			
			setContentView(R.layout.insuranceform);
			formObject = Application.getFormObjectInstance();
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			
			nameofInsured = (EditText) findViewById(R.id.nameofinsuredtext);
			contactNoofInsured = (EditText) findViewById(R.id.contactnoofinsuredtext);
			policyCoverNoteNo = (EditText) findViewById(R.id.policycovernotenotext);

			dateseparator1IF = (TextView) findViewById(R.id.dateseparator1IF);
			dateseparator2IF = (TextView) findViewById(R.id.dateseparator2IF);
			dateseparator1IF2 = (TextView) findViewById(R.id.dateseparator1IF2);
			dateseparator2IF2 = (TextView) findViewById(R.id.dateseparator2IF2);
			
			datetextbox = (EditText) findViewById(R.id.datetextboxIF);
			monthtextbox = (EditText) findViewById(R.id.monthtextboxIF);
			yeartextbox = (EditText) findViewById(R.id.yeartextboxIF);

			datetextboxA = (EditText) findViewById(R.id.datetextboxIF2);
			monthtextboxA = (EditText) findViewById(R.id.monthtextboxIF2);
			yeartextboxA = (EditText) findViewById(R.id.yeartextboxIF2);
						
			policyCoverNoteSerialNo = (EditText) findViewById(R.id.policycovernoteserialnotext);
			coverNoteIssuedBy = (EditText) findViewById(R.id.covernoteissuedbytext);
			reasonsforIssuingCoverNote = (EditText) findViewById(R.id.reasonsforissuingcovernotetext);
			//Currently onLoad() is implemented only for Accident Details Activity. i.e.: First Activity
			//And need to check whether came from search and view a existing job. otherwise no need of this.
			
			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10477");
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
			onLoad();
			LOG.debug("SUCCESS onResume");
			
		} catch (Exception e) {
			LOG.error("onResume:10478");
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
			LOG.debug("ENTRY onPause");
			System.gc();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onPause:10479");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void formComponentControler(){
		try {
			if(formObject.getisSEARCH()){
				nameofInsured.setTextColor(Color.GRAY);
				nameofInsured.setEnabled(false);

				contactNoofInsured.setTextColor(Color.GRAY);
				contactNoofInsured.setEnabled(false);
				contactNoofInsured.setFocusable(false);
				contactNoofInsured.setFocusableInTouchMode(false); 
				contactNoofInsured.setClickable(false);

				policyCoverNoteNo.setTextColor(Color.GRAY);
				policyCoverNoteNo.setFocusable(false);
				policyCoverNoteNo.setFocusableInTouchMode(false); 
				policyCoverNoteNo.setClickable(false);

				dateseparator1IF.setVisibility(View.GONE);
				dateseparator2IF.setVisibility(View.GONE);
				dateseparator1IF2.setVisibility(View.GONE);
				dateseparator2IF2.setVisibility(View.GONE);
				
				datetextbox.setTextColor(Color.GRAY);
				datetextbox.setHint("");
				datetextbox.setEnabled(false);

				monthtextbox.setTextColor(Color.GRAY);
				monthtextbox.setHint("");
				monthtextbox.setEnabled(false);

				yeartextbox.setTextColor(Color.GRAY);
				yeartextbox.setHint("");
				yeartextbox.setEnabled(false);

				datetextboxA.setTextColor(Color.GRAY);
				datetextboxA.setHint("");
				datetextboxA.setEnabled(false);

				monthtextboxA.setTextColor(Color.GRAY);
				monthtextboxA.setHint("");
				monthtextboxA.setEnabled(false);

				yeartextboxA.setTextColor(Color.GRAY);
				yeartextboxA.setHint("");
				yeartextboxA.setEnabled(false);

				policyCoverNoteSerialNo.setTextColor(Color.GRAY);
				policyCoverNoteSerialNo.setFocusable(false);
				policyCoverNoteSerialNo.setFocusableInTouchMode(false); 
				policyCoverNoteSerialNo.setClickable(false);

				coverNoteIssuedBy.setTextColor(Color.GRAY);
				coverNoteIssuedBy.setFocusable(false);
				coverNoteIssuedBy.setFocusableInTouchMode(false); 
				coverNoteIssuedBy.setClickable(false);

				reasonsforIssuingCoverNote.setTextColor(Color.GRAY);
				reasonsforIssuingCoverNote.setFocusable(false);
				reasonsforIssuingCoverNote.setFocusableInTouchMode(false); 
				reasonsforIssuingCoverNote.setClickable(false);
			}
		} catch (Exception e) {
			LOG.error("formComponentControler:10480");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
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
	
	public Boolean dataValidation() throws GenException {
		boolean val = false;
		try {
			String fromS = formObject.getPolicyCoverNotePeriodFrom();	//policyCoverNotePeriodFrom.getText().toString();
			String toS = formObject.getPolicyCoverNotePeriodTo();		//policyCoverNotePeriodTo.getText().toString();
			if (fromS != null && !fromS.trim().equalsIgnoreCase("")	&& toS != null && !toS.trim().equalsIgnoreCase("")) {
				String[] ff = fromS.split("/");
				String[] tt = toS.split("/");
				Calendar cFrom = Calendar.getInstance();
				Calendar cTo = Calendar.getInstance();
				cFrom.set(Integer.parseInt(ff[2]), Integer.parseInt(ff[1]),	Integer.parseInt(ff[0]));
				cTo.set(Integer.parseInt(tt[2]), Integer.parseInt(tt[1]), Integer.parseInt(tt[0]));
				if (cTo.before(cFrom) || cTo.equals(cFrom)) {
					val = false;
					AlertDialog.Builder alertbox = new AlertDialog.Builder(InsuranceDetailsActivity.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage("'Policy Cover Note Period To' contains an earlier date than 'Policy Cover Note Period From'");
					alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertbox.show();
				} else{
					val = true;
				}
			}
			
		} catch (Exception e) {
			LOG.error("dataValidation:10482");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			val = false;
		}
		return val;
	}
	
	
	public void on_click_back_button(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_back_button");
			Application.goForward = false;
			Application.goBackward = true;

			onSave();
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.INSURANCEDETAILS_BACK_BUTTON_CLICK, this, formObject));
			} catch (Exception e) {
				LOG.error("on_click_back_button:10486");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
	}
	
	public void on_click_next_button(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_next_button");
			onSave();
			
			//Original Exp: ^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$
			String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
			Pattern pattern = Pattern.compile(expression);
			String totalDateTextboxError = "";
			String totalDateTextboxErrorA = "";
			String msg = "";
			String msg2 = "";
			
			CharSequence inputStr = formObject.getPolicyCoverNotePeriodFrom();
			CharSequence inputStrA = formObject.getPolicyCoverNotePeriodTo();
			
			Matcher matcher = pattern.matcher(inputStr);
			Matcher matcherA = pattern.matcher(inputStrA);
			//Validate From
			if(!datetextbox.getText().toString().trim().equals("")
					|| !monthtextbox.getText().toString().trim().equals("")
					|| !yeartextbox.getText().toString().trim().equals("")){
					
			if(!matcher.matches()){
			totalDateTextboxError = "Please enter a valid 'Policy Cover Note Period From'.\r\n";
			}
			else{ //matched
				if(datetextboxA.getText().toString().trim().equals("") || monthtextboxA.getText().toString().trim().equals("") || yeartextboxA.getText().toString().trim().equals("")	
				){
					msg = "'Policy Cover Note Period To' can't be empty.\r\n";
				}
			}
			}
			
			if(!datetextboxA.getText().toString().trim().equals("")
					|| !monthtextboxA.getText().toString().trim().equals("")
					|| !yeartextboxA.getText().toString().trim().equals("")){
				
			if(!matcherA.matches()){
			totalDateTextboxErrorA = "Please enter a valid 'Policy Cover Note Period To'.";
			}
			else{ //matched
				if(datetextbox.getText().toString().trim().equals("") && monthtextbox.getText().toString().trim().equals("") && yeartextbox.getText().toString().trim().equals("")){
					msg2 = "'Policy Cover Note Period From' can't be empty.";
				}
			}
			}

			//if(!matcher.matches() || !matcherA.matches()){
			if(!totalDateTextboxError.equals("") || !totalDateTextboxErrorA.equals("") || !msg.equals("") || !msg2.equals("")){
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						InsuranceDetailsActivity.this);
				alertbox.setTitle(R.string.saform);
				alertbox.setMessage(totalDateTextboxError + totalDateTextboxErrorA + msg + msg2);
				alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {}
				});
				alertbox.show();
			}

			// if dates are 
			if(!datetextbox.getText().toString().trim().equals("") && !monthtextbox.getText().toString().trim().equals("") && !yeartextbox.getText().toString().trim().equals("") &&
					!datetextboxA.getText().toString().trim().equals("") && !monthtextboxA.getText().toString().trim().equals("") && !yeartextboxA.getText().toString().trim().equals("") &&
					matcher.matches() && matcherA.matches()) {  // all txtboxes are not null and pattern also ok, then do the below
				
				try {
					boolean proceed = true;
					if(formObject.isEditable()){
						proceed = dataValidation();
					}
					onSave();
					if(proceed) {
						Application.goForward = true;
						Application.goBackward = false;
						Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.INSURANCEDETAILS_NEXT_BUTTON_CLICK,	this, formObject));
					}
					
				} catch (Exception e) {
					LOG.error("on_click_next_button:10485");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
			}
			if(datetextbox.getText().toString().trim().equals("") && monthtextbox.getText().toString().trim().equals("") && yeartextbox.getText().toString().trim().equals("") &&
					datetextboxA.getText().toString().trim().equals("") && monthtextboxA.getText().toString().trim().equals("") && yeartextboxA.getText().toString().trim().equals("")) {
			
				try {
					onSave();
					Application.goForward = true;
					Application.goBackward = false;
					Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.INSURANCEDETAILS_NEXT_BUTTON_CLICK,	this, formObject));
				} catch (Exception e) {
					LOG.error("on_click_next_button:10484");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
			}
			LOG.debug("SUCCESS ", "on_click_next_button");
		} catch (Exception e) {
			LOG.error("on_click_next_button:10483");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	
	public void onSave(){
		try {
			//Name of Insured:
			formObject.setNameofInsured(nameofInsured.getText().toString().trim());
			//Contact No of Insured:
			formObject.setContactNooftheInsured(contactNoofInsured.getText().toString().trim());
			//Policy Cover Note No:
			formObject.setPolicyCoverNoteNo(policyCoverNoteNo.getText().toString().trim());
			
			
			//Policy Cover Note Period From:
			//formObject.setPolicyCoverNotePeriodFrom(policyCoverNotePeriodFrom.getText().toString());
			//Policy Cover Note Period To:
			//formObject.setPolicyCoverNotePeriodTo(policyCoverNotePeriodTo.getText().toString());
			//From
			if(datetextbox.getText().toString().trim().equals("") || monthtextbox.getText().toString().trim().equals("") || yeartextbox.getText().toString().trim().equals("")){
				formObject.setPolicyCoverNotePeriodFrom("");
			}
			else{
				formObject.setPolicyCoverNotePeriodFrom(datetextbox.getText().toString().trim() + "/" + 
						monthtextbox.getText().toString().trim() + "/" + yeartextbox.getText().toString().trim());
			}
			
			//To
			if(datetextboxA.getText().toString().trim().equals("") || monthtextboxA.getText().toString().trim().equals("") || yeartextboxA.getText().toString().trim().equals("")){
				formObject.setPolicyCoverNotePeriodTo("");
			}
			else{
				formObject.setPolicyCoverNotePeriodTo(datetextboxA.getText().toString().trim() + "/" + 
						monthtextboxA.getText().toString().trim() + "/" + yeartextboxA.getText().toString().trim());
			}
			
			//Policy Cover Note Serial No:
			formObject.setPolicyCoverNoteSerialNo(policyCoverNoteSerialNo.getText().toString().trim());
			//Cover Note Issued By:
			formObject.setCoverNoteIssuedBy(coverNoteIssuedBy.getText().toString().trim());
			//Reasons for Issuing Cover Note:
			formObject.setReasonsforIssuingCoverNote(reasonsforIssuingCoverNote.getText().toString().trim());
			
			if(!formObject.getisSEARCH()){
				FormObjSerializer.serializeFormData(formObject);
				}
		} catch (Exception e) {
			LOG.error("onSave:10487");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void onLoad(){
		try {
			formObject = Application.getFormObjectInstance();
			
			//Name of Insured:
			nameofInsured.setText(formObject.getNameofInsured());
			
			//Contact No of Insured:
			contactNoofInsured.setText(formObject.getContactNooftheInsured());
			
			//Policy Cover Note No:
			policyCoverNoteNo.setText(formObject.getPolicyCoverNoteNo());
			
			//Policy Cover Note Period From:
			//policyCoverNotePeriodFrom.setText(formObject.getPolicyCoverNotePeriodFrom());
			//Policy Cover Note Period To:
			//policyCoverNotePeriodTo.setText(formObject.getPolicyCoverNotePeriodTo());
			String fulldatettime = formObject.getPolicyCoverNotePeriodFrom();
			
			if((fulldatettime != null) && !(fulldatettime.equals(""))){
			datetextbox.setText(fulldatettime.split("/")[0]);
			monthtextbox.setText(fulldatettime.split("/")[1]);
			
			int startPos = fulldatettime.split("/")[2].length()-2;
			yeartextbox.setText(fulldatettime.split("/")[2].substring(startPos));
			}
			
			String fulldatettimeA = formObject.getPolicyCoverNotePeriodTo();
			
			if((fulldatettimeA != null) && !(fulldatettimeA.equals(""))){
			datetextboxA.setText(fulldatettimeA.split("/")[0]);
			monthtextboxA.setText(fulldatettimeA.split("/")[1]);
			
			int startPosA = fulldatettimeA.split("/")[2].length()-2;
			yeartextboxA.setText(fulldatettimeA.split("/")[2].substring(startPosA));
			}
			
			//Policy Cover Note Serial No:
			policyCoverNoteSerialNo.setText(formObject.getPolicyCoverNoteSerialNo());
			
			//Cover Note Issued By:
			coverNoteIssuedBy.setText(formObject.getCoverNoteIssuedBy());
			
			//Reasons for Issuing Cover Note:
			reasonsforIssuingCoverNote.setText(formObject.getReasonsforIssuingCoverNote());
		} catch (Exception e) {
			LOG.error("onSave:10488");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
}
