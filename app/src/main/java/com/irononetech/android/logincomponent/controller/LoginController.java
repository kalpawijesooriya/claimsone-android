package com.irononetech.android.logincomponent.controller;

import com.irononetech.android.Application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.logincomponent.model.LoginServiceHandler;
import com.irononetech.android.logincomponent.model.LoginWebServiceObject;
import com.irononetech.android.logincomponent.view.UserObject;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;

public class LoginController extends Controller implements ListenerWeb {

	Logger LOG = LoggerFactory.getLogger(LoginController.class);

	EventParcel eventParcel;

	public LoginController() {
		super();
	}

	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
		try {
			if (((UserObject) dataObject).getUsername() != null
					&& !((UserObject) dataObject).getUsername().trim().equalsIgnoreCase("")) {
			} else {
				// Get from resource files
				throw new GenException("", "Please Enter Username");
			}
			if (((UserObject) dataObject).getPassword() != null
					&& !((UserObject) dataObject).getPassword().trim().equalsIgnoreCase("")) {

			} else {
				// Get from resource files
				// LogFile.d("INFO ", TAG + "dataValidation:10182");
				throw new GenException("", "Please Enter Password");
			}
		} catch (GenException e) {
			LOG.error("readXMLResponse:10180");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			throw e;
		} catch (Exception e) {
			LOG.error("readXMLResponse:10181");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

			throw new GenException("", e.getMessage());
		}
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception, GenException {
		try {
			if (NetworkCheck.isNetworkAvailable(eventParcel.getSourceActivity())) {
				this.eventParcel = eventParcel;

				// Validate username and password.
				this.dataValidation(eventParcel.getDataObject());

				String username = ((UserObject) (eventParcel.getDataObject())).getUsername();
				String password = ((UserObject) (eventParcel.getDataObject())).getPassword();

				// Set current user
				Application.setCurrentUser(username);

				LoginWebServiceObject webServiceObject = new LoginWebServiceObject();
				webServiceObject.setUsername(username);
				webServiceObject.setPassword(password);
				ServiceHandler handler = new LoginServiceHandler(webServiceObject);
				handler.addListener(this, WebEvent.LOGIN_WEB_SERVICE);
				handler.runThread();
			} else {
				LOG.info("doAction:10183");
				throw new GenException("", (ResourceParser.getResourceString(eventParcel.getSourceActivity(),
						R.string.networkunavailable)));
			}
		} catch (GenException e) {
			LOG.error("doAction:10184");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			throw e;
		} catch (Exception e) {
			LOG.error("doAction:10185");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			throw new GenException("", e.getMessage());
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject, WebEvent webEvent) {

		try {
			if (!webServiceObject.isSuccessful()) {
				throw new GenException("", webServiceObject.getError());

			} else {
				XMLHandler
						.getResponse(webServiceObject);

				String status = webServiceObject.getCode();
				String description = webServiceObject.getDescription();

				(eventParcel.getDataObject()).setStatus(status);
				(eventParcel.getDataObject()).setAlertDescription(description);
				// 2016-12-09
				// Suren Manawatta
				// Used to get all the app version info to check for updates
				(eventParcel.getDataObject()).setWebServiceObject(webServiceObject);

				super.listenerCallback(eventParcel);
			}
		} catch (GenException e) {
			LOG.error("listenerCallbackWeb:10186");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());
			super.listenerCallback(eventParcel);
		} catch (Exception e) {
			LOG.error("listenerCallbackWeb:10187");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());
			super.listenerCallback(eventParcel);
		}
	}
}
