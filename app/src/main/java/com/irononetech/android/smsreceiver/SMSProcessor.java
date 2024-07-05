package com.irononetech.android.smsreceiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Suren Manawatta
 */
public class SMSProcessor {

	static Logger LOG = LoggerFactory.getLogger(SMSProcessor.class);

	
	/**
	 * 
	 * @author Suren Manawatta
	 */
	public static String composeNsendSMS(boolean[] arr, String CC_No){
		try {
			String errMSG = "";
			String tabMsg = "";
			if(arr[0]){
				errMSG = "'Policy Cover Note Period From' is in incorrect format.\r\n";
				tabMsg = tabMsg + errMSG;
			}
			if(arr[1]){
				errMSG = "'Policy Cover Note Period To' is in incorrect format.\r\n";
				tabMsg = tabMsg + errMSG;
			}
			
			//sendSMS(CC_No, smsTobesent);
			//smsTobesent = "Received SMS was incomplete.";
			
			if(arr[2]){
				errMSG = "'Time Reported' format is incorrect. Current date and time will be used.\r\n";
				tabMsg = tabMsg + errMSG;
			}
			if(arr[3]){
				errMSG = "Received SMS was incomplete.\r\n";
				tabMsg = tabMsg + errMSG;
			}
			if(arr[4]){
				errMSG = "Program failed to retrieve the SMS.";
				tabMsg = tabMsg + errMSG;
			}
			
			//sendSMS(CC_No, smsTobesent);
			LOG.info("Message: " + tabMsg);
			return tabMsg;
			
		} catch (Exception e) {
			LOG.error("composeNsendSMS:10558");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }			
			return "";
		}
	}
	
	/**
	 * 
	 * @author Suren Manawatta
	 */
	public static void sendSMS(String phoneNumber, String message, final Activity act, Context cont)
    {
		BroadcastReceiver sendBroadcastReceiver = null;
		BroadcastReceiver deliveryBroadcastReciever = null;
		try{

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(cont, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(cont, 0,
            new Intent(DELIVERED), 0);
        //---when the SMS has been sent---
        cont.registerReceiver(sendBroadcastReceiver = new BroadcastReceiver(){
        	
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(act, "SMS sent", 
                                Toast.LENGTH_LONG).show();
                        LOG.info("SMS reply status: SMS sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(act, "Generic failure", 
                                Toast.LENGTH_LONG).show();
                        LOG.info("SMS reply status: Generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(act, "No service", 
                                Toast.LENGTH_LONG).show();
                        LOG.info("SMS reply status: No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(act, "Null PDU", 
                                Toast.LENGTH_LONG).show();
                        LOG.info("SMS reply status: Null PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(act, "Radio off", 
                                Toast.LENGTH_LONG).show();
                        LOG.info("SMS reply status: Radio off");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        cont.registerReceiver(deliveryBroadcastReciever = new BroadcastReceiver(){
        	@Override
        	public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(act, "SMS is delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(act, "SMS is not delivered", 
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        
        // Need to unregister the registered receivers otherwise a leak will occur.
        // No exception will fire but, logcat will show it as a leak
        cont.unregisterReceiver(sendBroadcastReceiver);
    	cont.unregisterReceiver(deliveryBroadcastReciever);
    }catch (Exception e) {
    	LOG.error("sendSMS:10428");
    	   if(e != null){
    		LOG.error("Message: " + e.getMessage());
    		LOG.error("StackTrace: " + e.getStackTrace());
    	  }
    	cont.unregisterReceiver(sendBroadcastReceiver);
    	cont.unregisterReceiver(deliveryBroadcastReciever);
	}}
}
