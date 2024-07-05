package com.irononetech.android.logoutcomponent;

import com.irononetech.android.Webservice.ListenerWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.logincomponent.view.UserObject;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;

public class LogoutController extends Controller implements ListenerWeb{
	Logger LOG = LoggerFactory.getLogger(LogoutController.class);
	EventParcel eventParcel;
	
	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,
			GenException {

		try {
			if(NetworkCheck.isNetworkAvailable(eventParcel.getSourceActivity())){
				this.eventParcel = eventParcel;
							
				WebServiceObject webServiceObject = new WebServiceObject();
				
				ServiceHandler handler = new LogoutServiceHandler(webServiceObject);
				handler.addListener(this, WebEvent.LOGOUT_WEB_SERVICE);
				handler.runThread();
			}else{
				((UserObject) (eventParcel.getDataObject())).setAlertDescription(ResourceParser.getResourceString(eventParcel.getSourceActivity(), R.string.networkunavailable));
				super.listenerCallback(eventParcel);
			}
		} catch (Exception e) {
			 LOG.error("doAction:10200");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) {
		//PLease don't add callbak. If you are adding please don't call eventparcel.getsourceactivity(). Because the source
		// Activity has already deleted.
	}
}
