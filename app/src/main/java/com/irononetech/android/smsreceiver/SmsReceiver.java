package com.irononetech.android.smsreceiver;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebService;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.formcomponent.view.SMSActivity;
import com.irononetech.android.claimsone.R;

/**
 * 
 * @author Suren Manawatta
 * @modified by Vimosanan
 */
public class SmsReceiver extends BroadcastReceiver
{
	public static List<String> smsMsg = new ArrayList<String>();
	SmsMessage[] msgs = null;
	static Logger LOG = LoggerFactory.getLogger(SmsReceiver.class);
	String completeSMS = "";
	public Context contextP;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			//---get the SMS message passed in---
			String START = context.getString(R.string.SMS_START);
			String SLIC_MSG1 = context.getString(R.string.SLIC_MSG1);
			String SLIC_MSG2 = context.getString(R.string.SLIC_MSG2);
			String END = context.getString(R.string.SMS_END);
			//String CC_NO = context.getString(R.string.CALL_CENTER_NO);

			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				//---retrieve the SMS message received---
				//String msgNo = "";		// sender phone number
				String msgBody = "";		// contain the msg
				String msgBodyUC = "";		// contain uppercase version of the MSG
				String actName = "";		//activity name
				Boolean isSLICMSGType = false;

				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];

				for (int i=0; i<msgs.length; i++){
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					//msgNo = msgs[i].getOriginatingAddress();

					//if(msgNo.equals(CC_NO)) {  // Setup the Call Center Number
					msgBody = msgs[i].getMessageBody().toString();
					msgBodyUC = msgs[i].getMessageBody().toString().toUpperCase();

					LOG.info("SMS: " + msgBody);

					if(msgBodyUC.startsWith(SLIC_MSG1) || msgBodyUC.startsWith(SLIC_MSG2) || isSLICMSGType){ 				// contain '#SLIC_MSG'
						msgBody = msgBody.replaceAll(SLIC_MSG1, "");		// remove SLIC_MSG from the sms coz it's no longer needed
						msgBody = msgBody.replaceAll(SLIC_MSG2, "");
						isSLICMSGType = true;
						if (msgBodyUC.indexOf(START) >= 0) {  				// contain 'START'
							smsMsg.clear();
							smsMsg.add(msgBody);
							//LogFile.d("\nInside the START:", "msgBody");
							//When the same SMS has the END part
							if (msgBodyUC.endsWith(END)) {  			// contains "END#"
								//LogFile.d("\nInside the END:", "msgBody");
								for (int j = 0; j < smsMsg.size(); j++) {
									completeSMS = completeSMS + smsMsg.get(j);
								}
								commonFunc(context, actName, completeSMS);
							}
						} else {
							if(!smsMsg.isEmpty()) {  						//not isEmpty()
								// Check start is in the List
								Boolean isStart = false;
								for (String object : smsMsg) {
									if(object.toString().toUpperCase().indexOf(START) >= 0){  //contain "START"
										isStart = true;
									}
								}
								if(isStart) {  							//if start is in the List proceed with other MSG
									smsMsg.add(msgBody);
									if (msgBodyUC.endsWith(END)) {  	// contains "END#"
										for (int j = 0; j < smsMsg.size(); j++) {
											completeSMS = completeSMS + smsMsg.get(j);
										}
										commonFunc(context, actName, completeSMS);	
									}
								}
								else {  			/* 'START' is not in the List */
									smsMsg.clear();
								}
							}
						}
					} else {				/* '#SLIC_MSG' is not in the List */
						smsMsg.clear();
					}
					//} // if(msgNo.equals(CC_NO))
				}
			}
			//this.abortBroadcast();  		// don't need it. i guess
		} catch (Exception e) {
			LOG.error("excecute:10397");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	/**
	 * 
	 * @author Suren Manawatta
	 */
	private void commonFunc(Context context, String actName, String completeSMS) {
		try {
			contextP = context;
			ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
			List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);  //(1): specify no of tasks you wanna store on the RunningTaskInfo List
			for (RunningTaskInfo info : taskInfo) {					 // from top most to bottom respectively from the BackStack
				actName = info.topActivity.getClassName();

				if (actName.endsWith("Launcher")) {

					List<RunningTaskInfo> allTaskInfo = am.getRunningTasks(30);  //(30): specify no of tasks you wanna store on the RunningTaskInfo List
					for (RunningTaskInfo inf : allTaskInfo) {					 // from top most to bottom respectively from the BackStack
						if(inf.topActivity.getClassName().endsWith("LoginActivity")){
							actName = "LoginActivity";
							break;
						}
					}
				}
			}

			if (actName != null) {  // If Activity name is not null
				if (!actName.endsWith("LoginActivity")) {  /*!actName.endsWith("Launcher") && */
					String[] arr = {actName , completeSMS};
					new SMSWSHandler().execute(arr);
				}
			}
		} catch (SecurityException e) {
			LOG.error("excecute:10398");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("excecute:10399");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	/**
	 * 
	 * @author Suren Manawatta
	 */
	class SMSWSHandler extends AsyncTask <String, Void, String[]> {

		public String actName;
		@Override
		protected String[] doInBackground(String... params) {
			try {
				actName = params[0];
				String jobno = FormObjSerializer.getSMSJobno(params[1]);
				return WebService.callWebService(jobno, URL.getSLICWebServiceTimeoutInSec());
			} catch (Exception e) {
				LOG.error("SMSWSHandler - AsyncTask:10580");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return null;
			}
		}

		@Override
		protected void onPostExecute(String[] result) {
			try{
				// Check it's in the outside of the SA form
				if ((actName.indexOf("HomeActivity") >= 0) || (actName.indexOf("SearchActivity") >= 0)
						|| (actName.indexOf("SearchListActivity") >= 0) || (actName.indexOf("DraftActivity") >= 0)){

					Intent smsintent = new Intent(contextP, SMSActivity.class);
					smsintent.putExtra("SA_FORM", "false");
					smsintent.putExtra("MSG", SMSManip(result, completeSMS));
					smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					contextP.startActivity(smsintent);
				}
				else { /*if inside the SA form*/
					Intent smsintent = new Intent(contextP, SMSActivity.class);
					smsintent.putExtra("SA_FORM", "true");
					smsintent.putExtra("MSG", SMSManip(result, completeSMS));
					smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					contextP.startActivity(smsintent);
				}
			} catch(Exception e){
				LOG.error("SMSWSHandler - onPostExecute:10581");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		private String SMSManip(String[] result, String sms){
			try {
				if (result == null || result.length <=0) {
					return sms;
				}

				String[] msg = sms.split("#");
				if(msg.length == 18){//for new format sms
					if(result[0] != ""){
						msg[10] = result[0]; // Name of Caller
					}
					if(result[1] != ""){
						msg[4] = result[1];  // Insured Name    result[2] == jobno
					}
					if(result[3] != ""){
						msg[14] = result[3]; // Location of Accident
					}
					/*if(result[4] != ""){
						try{
							int x = Integer.parseInt(result[4]); // Nearest Police Station
							String[] policeStationList = contextP.getResources().getStringArray(R.array.nearestPoliceStation);
							msg[16] = policeStationList[x];
						} catch (Exception e){
							msg[16] = result[4];
						}
					}*/
					if(result[5] != ""){
						//Cover note normally comes in SMS, So if SMS doesn't provide only I'm
						//assigning web service return value.
						if(msg[15] == null || msg[15].trim().equals(""))
							msg[15] = result[5]; // Policy Cover Note No
					}
					if(result[6] != ""){
						msg[12] = result[6]; // Time Reported
					}
				}else if(msg.length == 17){// for old format sms
					if(result[0] != ""){
						msg[10] = result[0]; // Name of Caller
					}
					if(result[1] != ""){
						msg[4] = result[1];  // Insured Name    result[2] == jobno
					}
					if(result[3] != ""){
						msg[13] = result[3]; // Location of Accident
					}
					if(result[5] != ""){
						//Cover note normally comes in SMS, So if SMS doesn't provide only I'm
						//assigning web service return value.
						if(msg[14] == null || msg[15].trim().equals(""))
							msg[14] = result[5]; // Policy Cover Note No
					}
					/*if(result[4] != ""){
						try{
							int x = Integer.parseInt(result[4]); // Nearest Police Station
							String[] policeStationList = contextP.getResources().getStringArray(R.array.nearestPoliceStation);
							msg[15] = policeStationList[x];
						} catch (Exception e){
							msg[15] = result[4];
						}
					}*/
					if(result[6] != "") {
						msg[12] = result[6]; // Time Reported
					}
				}

				String temp = "";
				for (int i = 0; i < msg.length; i++) {
					temp = temp + msg[i] + "#";
				}
				return temp;
			} catch (Exception e) {
				LOG.error("SMSManip:10583");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return sms;
			}
		}
	}
}