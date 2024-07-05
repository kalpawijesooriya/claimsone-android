package com.irononetech.android.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.Application;
import android.widget.Toast;
import com.irononetech.android.database.DBHandler;

public class AndroidApplication extends Application {
	
	Logger LOG = LoggerFactory.getLogger(AndroidApplication.class);
	
	@Override
	public void onCreate() { 
		
		try { 
			super.onCreate();
			LOG.debug("ENTRY onCreate");			
			//Initialize the DB
			DBHandler.createInstance(getApplicationContext());
			
			// Get Our Application Class Instance
			com.irononetech.android.Application.Application.getInstance();
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			//LogFile.d("EXCEPTION ", TAG + "onCreate:10078");
			LOG.error("onCreate");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void showToast(){
		Toast msg = Toast.makeText(this, "SLIC App", Toast.LENGTH_LONG);
		msg.show();
	}
	
	
//	public static void finishApplication() {
//		android.os.Process.killProcess(android.os.Process.myPid());
//	}

}
