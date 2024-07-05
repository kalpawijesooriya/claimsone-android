package com.irononetech.android.formcomponent.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.irononetech.android.Application.Application;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.smsreceiver.SMSProcessor;

public class SMSActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(SMSActivity.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			setContentView(R.layout.sms);
			final FormObject fo = Application.createFormObjectInstance();
			Bundle extras = this.getIntent().getExtras();
			Boolean SA_FORM;
			final String CC_No = this.getString(R.string.CALL_CENTER_NO);  //contain call center no
			
			if(extras != null)
			{
				SA_FORM = Boolean.valueOf(extras.getString("SA_FORM"));
				final String MSG = extras.getString("MSG").toString();

				if(SA_FORM){  //inside the SA form
					AlertDialog.Builder alertbox = new AlertDialog.Builder(SMSActivity.this);
					alertbox.setTitle("New Job Alert!");
					alertbox.setCancelable(false);
					alertbox.setMessage(getString(R.string.you_have_a_new_job_do_you_want_to_accept_));
					alertbox.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, final int arg1) {
									try {
										// this should not declare here. if it is the currently working on
										//SA form's job no will be freeze
										
										//save it as a draft
										//fo.setisSMS(true);
										//fo.setisDRAFT(false);
										//fo.setisSEARCH(false);
										
										if(!FileOperations.draftsMaxReachedCHeck9(SMSActivity.this)){ //not max reached  (<9)
											if(FileOperations.draftsWarningMsg(SMSActivity.this)){ // check draft count == 8  //but it shows 9's warning
																				   // sa form working now is not saved yet. so keep a slot for that.
											new AlertDialog.Builder(SMSActivity.this).setMessage(getString(R.string.drafts_Max_Reached_Warning))
											.setTitle(R.string.draft_exceed_warning_)
											.setPositiveButton(android.R.string.ok, 
													new DialogInterface.OnClickListener() { 
														public void onClick(DialogInterface dialog, int whichButton){
															boolean[] arr = FormObjSerializer.saveSMSasaDraft(MSG, SMSActivity.this);
															//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
															String jobno = FormObjSerializer.getSMSJobno(MSG);
															sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);
															composeNsendSMS(arr, CC_No, jobno);
															
															removeDialog(whichButton);
															removeDialog(arg1);
														}
												}).show();
											}else if(FileOperations.draftsMaxReachedWarning(SMSActivity.this)){ // check draft count == 9
												new AlertDialog.Builder(SMSActivity.this).setMessage(getString(R.string.drafts_Max_Reached_Warning))
												.setTitle(R.string.draft_exceed_warning_)
												.setPositiveButton(android.R.string.ok, 
														new DialogInterface.OnClickListener() { 
															public void onClick(DialogInterface dialog, int whichButton){
																boolean[] arr = FormObjSerializer.saveSMSasaDraft(MSG, SMSActivity.this);
																//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
																String jobno = FormObjSerializer.getSMSJobno(MSG);
																sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);
																composeNsendSMS(arr, CC_No, jobno);
																
																removeDialog(whichButton);
																removeDialog(arg1);
															}
													}).show();
											}else{
												boolean[] arr = FormObjSerializer.saveSMSasaDraft(MSG, SMSActivity.this);
												//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
												String jobno = FormObjSerializer.getSMSJobno(MSG);
												sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);
												composeNsendSMS(arr, CC_No, jobno);
												removeDialog(arg1);
											}
										} else{ //max reached
											//String jobno = FormObjSerializer.getSMSJobno(MSG);
											sendSMS(CC_No, getString(R.string.mtabexceed_) + Application.getCurrentUser());
											AlertDialog.Builder alertbox = new AlertDialog.Builder(SMSActivity.this);
											alertbox.setTitle("Draft Alert:");
											alertbox.setMessage(getString(R.string.max_no_of_drafts_has_been_reached_please_deal_with_some_drafts_to_continue_callcentre_willbe_informed));
											alertbox.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface arg0, int arg1) {
															Intent intent = new Intent(SMSActivity.this, DraftActivity.class);
															  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
															  SMSActivity.this.startActivity(intent);
															  SMSActivity.this.finish();
														}
													});
											alertbox.show();
											
											//sendSMS(CC_No, getString(R.string.detected_too_many_saved_drafts_sms_was_ignored_by_the_program_));
											removeDialog(arg1);
										}
									} catch (Exception e) {
										//sendSMS(CC_No, getString(R.string.application_was_crashed_please_resend_the_sms_));
										//Toast.makeText(SMSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
										LOG.error("onCreate:10557");
										   if(e != null){
												LOG.error("Message: " + e.getMessage());
												LOG.error("StackTrace: " + e.getStackTrace());
											  }
										removeDialog(arg1);
										finish();
									}
								}
							});
					 alertbox.setNegativeButton("Reject",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								//sendSMS(CC_No, getString(R.string.i_m_unable_to_accept_the_job_));
								String jobno = FormObjSerializer.getSMSJobno(MSG);
								sendSMS(CC_No, getString(R.string.mtabrejec_)+jobno);
								removeDialog(arg1);
								finish();
							}
						});
					alertbox.show();
				} else { // not in the SA form
					AlertDialog.Builder alertbox = new AlertDialog.Builder(SMSActivity.this);
					alertbox.setTitle("New Job Alert!");
					alertbox.setCancelable(false);
					alertbox.setMessage(getString(R.string.you_have_a_new_job_do_you_want_to_accept_));
					alertbox.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, final int arg1) {
									try {
										// open it on the SA form
										fo.setisSMS(true);
										fo.setisDRAFT(false);
										fo.setisSEARCH(false);

										if(!FileOperations.draftsMaxReached(SMSActivity.this)){ //not max reached
												/*if(FileOperations.draftsWarningMsg(SMSActivity.this)){ // check draft count
													new AlertDialog.Builder(SMSActivity.this).setMessage(getString(R.string.draft_warn_msg))
													.setTitle(R.string.draft_exceed_warning_)
													.setPositiveButton(android.R.string.ok,
															new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int whichButton){
																	Intent smsintent = new Intent(SMSActivity.this, AccidentDetailsActivity.class);
																	smsintent.putExtra("FROM_SMS", "true");
																	smsintent.putExtra("MSG", MSG);
																	smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																	SMSActivity.this.startActivity(smsintent);
																	//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
																	String jobno = FormObjSerializer.getSMSJobno(MSG);
																	sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);

																	removeDialog(whichButton);
																	removeDialog(arg1);
																	finish();
																}
														}).show();
												}else if(FileOperations.draftsMaxReachedWarning(SMSActivity.this)){ // check draft count == 9
													new AlertDialog.Builder(SMSActivity.this).setMessage(getString(R.string.drafts_Max_Reached_Warning))
													.setTitle(R.string.draft_exceed_warning_)
													.setPositiveButton(android.R.string.ok, 
															new DialogInterface.OnClickListener() { 
																public void onClick(DialogInterface dialog, int whichButton){
																	Intent smsintent = new Intent(SMSActivity.this, AccidentDetailsActivity.class);
																	smsintent.putExtra("FROM_SMS", "true");
																	smsintent.putExtra("MSG", MSG);
																	smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																	SMSActivity.this.startActivity(smsintent);
																	//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
																	String jobno = FormObjSerializer.getSMSJobno(MSG);
																	sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);

																	removeDialog(whichButton);
																	removeDialog(arg1);
																	finish();
																}
														}).show();
												}else{*/
													Intent smsintent = new Intent(SMSActivity.this, AccidentDetailsActivity.class);
													smsintent.putExtra("FROM_SMS", "true");
													smsintent.putExtra("MSG", MSG);
													smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													SMSActivity.this.startActivity(smsintent);
													//sendSMS(CC_No, getString(R.string.i_m_accepting_the_job_you_requested_));
													String jobno = FormObjSerializer.getSMSJobno(MSG);
													sendSMS(CC_No, getString(R.string.mtabaccpt_)+jobno);
													
													removeDialog(arg1);
													finish();
//												}
										} else{ //max reached
											AlertDialog.Builder alertbox = new AlertDialog.Builder(
													SMSActivity.this);
											alertbox.setTitle("Draft Alert:");
											alertbox.setMessage(getString(R.string.max_no_of_drafts_has_been_reached_please_deal_with_some_drafts_to_continue_));
											alertbox.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface arg0, int arg1) {
															Intent intent = new Intent(SMSActivity.this, DraftActivity.class);
															  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
															  SMSActivity.this.startActivity(intent);
															  //finish();
															  SMSActivity.this.finish();
														}
													});
											alertbox.show();
											
											//sendSMS(CC_No, getString(R.string.detected_too_many_saved_drafts_sms_was_ignored_by_the_program_));
											removeDialog(arg1);
										}
									} catch (Exception e) {
										//Toast.makeText(SMSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
										//sendSMS(CC_No, getString(R.string.application_was_crashed_please_resend_the_sms_));
										LOG.error("onCreate:10556");
										   if(e != null){
												LOG.error("Message: " + e.getMessage());
												LOG.error("StackTrace: " + e.getStackTrace());
											  }
										removeDialog(arg1);
										finish();
									}
								}
							});
					 alertbox.setNegativeButton("Reject",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								//sendSMS(CC_No, getString(R.string.i_m_unable_to_accept_the_job_));
								String jobno = FormObjSerializer.getSMSJobno(MSG);
								sendSMS(CC_No, getString(R.string.mtabrejec_)+jobno);
								removeDialog(arg1);
								finish();
							}
						});
					alertbox.show();
				}
			}
			else
			{
				finish();
			}
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:11504");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}
	
	//can only send <160 char
	private void composeNsendSMS(boolean[] arr, String CC_No, String jobno){
		try {
			String tabMsg = SMSProcessor.composeNsendSMS(arr, CC_No);
						
			if(!tabMsg.isEmpty()){
			//sendSMS(CC_No, "Received SMS was incomplete.");
			if(jobno.isEmpty()){
				sendSMS(CC_No, getString(R.string.mtabicomp_));
			}else{
				sendSMS(CC_No, getString(R.string.mtabicomp_)+jobno);
			}
			
			String title = "SMS Details:";
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setTitle(title);
			alertbox.setMessage(tabMsg);
			alertbox.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							finish();
						}
					});
			alertbox.show();
			}else{
				finish();
			}
		} catch (Exception e) {
			LOG.error("composeNsendSMS:10506");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}
	
	//can only send <160 char
	public void sendSMS(String phoneNumber, String message)
    {
		try{			
			SMSProcessor.sendSMS(phoneNumber, message, SMSActivity.this, getBaseContext());
			
			/*
        String SENT = "SMS_SENT";
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
                        Toast.makeText(SMSActivity.this, "SMS sent", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SMSActivity.this, "Generic failure", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SMSActivity.this, "No service", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SMSActivity.this, "Null PDU", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(SMSActivity.this, "Radio off", 
                                Toast.LENGTH_LONG).show();
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
                        Toast.makeText(SMSActivity.this, "SMS is delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SMSActivity.this, "SMS is not delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    
    */}catch (Exception e) {
    	LOG.error("sendSMS:10507");
		   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
	}}
}