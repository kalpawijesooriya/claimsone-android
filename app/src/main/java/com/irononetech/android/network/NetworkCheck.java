package com.irononetech.android.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
		
	static Logger LOG = LoggerFactory.getLogger(NetworkCheck.class);
	
	public static final int TYPE_NONE = -1;
	public static final int TYPE_MOBILE = 0;
	public static final int TYPE_WI_FI = 1;
	
	public static boolean isNetworkAvailable(Context context) {
		try {
			boolean value = false;
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				value = true;
			}
			return value;
		} catch (Exception e) {
			LOG.error("isNetworkAvailable:10362");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}
	
	public static int getNetworkType(Context context) {
		 try {
			int type = TYPE_NONE;
			 ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			 NetworkInfo info = manager.getActiveNetworkInfo();
			 if (info != null && info.isAvailable()) {
			     int nType = info.getType();
			     if(nType == ConnectivityManager.TYPE_MOBILE) type = TYPE_MOBILE;
			     else if(nType == ConnectivityManager.TYPE_WIFI) type = TYPE_WI_FI;
			 }
			 return type;
		} catch (Exception e) {
			LOG.error("getNetworkType:10363");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return -1;
		}
	}
}
