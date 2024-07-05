package com.irononetech.android.searchcomponent;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;

public class SearchController extends Controller implements ListenerWeb{
	Logger LOG = LoggerFactory.getLogger(SearchController.class);

	EventParcel eventParcel;

	@Override
	public void dataValidation(DataObject dataObject)
			throws GenException {
		try {
			if (((SearchUIobject) dataObject).getJobOrVehicleNo() != null && !((SearchUIobject) dataObject).getJobOrVehicleNo().trim().equalsIgnoreCase("")) {
			}
		}
		catch (Exception e) {
			LOG.error("dataValidation:10385");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", "Error Gen1" + e.getMessage());
		}
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,
			GenException {
		try {
			if (NetworkCheck.isNetworkAvailable(eventParcel.getSourceActivity())) {
								
				this.eventParcel = eventParcel;
				this.dataValidation(eventParcel.getDataObject());
				// this.dataValidation(eventParcel.getDataObject());

				String vehicleNo = ((SearchUIobject) (eventParcel
						.getDataObject())).getJobOrVehicleNo();

				SearchWebServiceObject webServiceObject = new SearchWebServiceObject();
				
				webServiceObject.setVehicleNoOrJobNo(vehicleNo);
				ServiceHandler handler = new SearchWebServiceHandler(
						webServiceObject);
				handler.addListener(this, WebEvent.SEARCH_WEB_SERVICE);
				handler.runThread();
			} else {
				
				throw new GenException("", (ResourceParser.getResourceString(
						eventParcel.getSourceActivity(), R.string.networkunavailable)));
			}
		} catch (GenException e) {
			LOG.error("doAction:10386");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw e;
		} catch (Exception e) {
			LOG.error("doAction:10387");
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
				throw new GenException("", webServiceObject.getError());
			} else {
				XMLHandler.getResponse(webServiceObject);
				XMLHandler.getSearchResponseByVehicleNoOrJobNo(webServiceObject);
									
				String status = webServiceObject.getCode();
				String description = webServiceObject.getDescription();
				ArrayList<String> jobListbyVehicleNo = ((SearchWebServiceObject)webServiceObject).getJobsByVehicleNo();
						
				((SearchUIobject) (eventParcel.getDataObject())).setStatus(status);
				((SearchUIobject) (eventParcel.getDataObject())).setAlertDescription(description);
				((SearchUIobject) (eventParcel.getDataObject())).setSearchedJobs(jobListbyVehicleNo);
								
				super.listenerCallback(eventParcel);
			}
		} catch (GenException e) {
			LOG.error("listenerCallbackWeb:10388");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());			
			super.listenerCallback(eventParcel);			
		} catch (Exception e) {
			LOG.error("listenerCallbackWeb:10389");
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