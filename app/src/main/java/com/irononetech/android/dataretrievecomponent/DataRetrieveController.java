package com.irononetech.android.dataretrievecomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;

public class DataRetrieveController extends Controller implements ListenerWeb {
	Logger LOG = LoggerFactory.getLogger(DataRetrieveController.class);
	EventParcel eventParcel;
	
	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,
			GenException {
		try {
			if (NetworkCheck.isNetworkAvailable(eventParcel.getSourceActivity())) {
				this.eventParcel = eventParcel;
				String vNo = ((DataRetrieveUIObject) eventParcel.getDataObject()).getVisitId();

				DataRetrieveWebServiceObject webServiceObject = new DataRetrieveWebServiceObject();
				webServiceObject.setVisitId(vNo);
				ServiceHandler handler = new DataRetrieveServiceHandler(webServiceObject);
				handler.addListener(this, WebEvent.DATA_RETRIEVE_WEB_SERVICE);
				handler.runThread();
			} else {
				LOG.info("doAction:10084");
				throw new GenException("", (ResourceParser.getResourceString(eventParcel.getSourceActivity(),
						R.string.networkunavailable)));
			}
		} catch (GenException e) {
			LOG.error("doAction:10079");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw e;
		} catch (Exception e) {
			LOG.error("doAction:10080");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) throws Exception {

		try {
			if (!webServiceObject.isSuccessful()) {
				LOG.info("EXCEPTION listenerCallbackWeb:10081");
				throw new GenException("", webServiceObject.getError());
			} else {
				XMLHandler.getResponse(webServiceObject);
				
				String status = webServiceObject.getCode();
				String description = webServiceObject.getDescription();
				((DataRetrieveUIObject) (eventParcel.getDataObject())).setStatus(status);
				((DataRetrieveUIObject) (eventParcel.getDataObject())).setAlertDescription(description);
				

				if(Application.isVisit){
					XMLHandler.getVisitDetailsByVisitId(webServiceObject);
					VisitObject vObj = ((DataRetrieveWebServiceObject) webServiceObject).getVisitObject();
					((DataRetrieveUIObject) (eventParcel.getDataObject())).setVisitObject(vObj);
				}else{
					XMLHandler.getJobDetailsByVisitId(webServiceObject);
					FormObject formobj = ((DataRetrieveWebServiceObject) webServiceObject).getFormObject();
					((DataRetrieveUIObject) (eventParcel.getDataObject())).setFormObject(formobj);
				}

				super.listenerCallback(eventParcel);
			}
		} catch (GenException e) {
			LOG.error("listenerCallbackWeb:10082");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());
			super.listenerCallback(eventParcel);
		} catch (Exception e) {
			LOG.error("listenerCallbackWeb:10083");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());
			super.listenerCallback(eventParcel);
		}
	}
}
