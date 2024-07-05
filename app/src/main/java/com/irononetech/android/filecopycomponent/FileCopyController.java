package com.irononetech.android.filecopycomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;

public class FileCopyController extends Controller implements ListenerWeb{
	EventParcel eventParcel;
	Logger LOG = LoggerFactory.getLogger(FileCopyController.class);

	@Override
	public void dataValidation(DataObject dataObject)
			throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,	GenException {
		try {
			this.eventParcel = eventParcel;

			ServiceHandler handler = new FileCopyServiceHandler(null);
			handler.addListener(this, WebEvent.FILE_COPY);
			handler.runThread();
		} catch (Exception e) {
			LOG.error("scanObjectsInStorage:10120");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) throws Exception {
		try {
			super.listenerCallback(eventParcel);
		} catch (Exception e) {
			LOG.error("listenerCallbackWeb:10121");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
}
