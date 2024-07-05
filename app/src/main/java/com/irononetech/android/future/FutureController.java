package com.irononetech.android.future;

import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;

public class FutureController extends Controller implements ListenerWeb {

	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,
			GenException {

		ServiceHandler handler = new FutureServiceHandler(null);
		handler.addListener(this, WebEvent.LOGIN_WEB_SERVICE);
		handler.runThread();
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject eventParcel,
			WebEvent webEvent) throws Exception {


	}

}
