package com.irononetech.android.datauploadcomponent;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.database.DBService;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;

public class DataUploadController extends Controller implements ListenerWeb{
	Logger LOG = LoggerFactory.getLogger(DataUploadController.class);
	EventParcel eventParcel;
	
	@Override
	public void dataValidation(DataObject dataObject)
			throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception, GenException {
		try {
			if(NetworkCheck.isNetworkAvailable(eventParcel.getSourceActivity())){
				this.eventParcel = eventParcel;

				if (!Application.getIsVisit()) {  //SA Form
					DataWebServiceObject dataWebServiceObject = new DataWebServiceObject();
					String xmlJob = ((FormObject) eventParcel.getDataObject()).getDataXML();
					dataWebServiceObject.setXmlToPass(xmlJob);
					ServiceHandler handler = new DataUploadServiceHandler(
							dataWebServiceObject);
					handler.addListener(this, WebEvent.DATAUPLOAD);
					handler.runThread();
				}else{		//Visit
					DataWebServiceObject dataWebServiceObject = new DataWebServiceObject();
					String xmlJob = ((VisitObject) eventParcel.getDataObject()).getDataXML();
					dataWebServiceObject.setXmlToPass(xmlJob);
					ServiceHandler handler = new DataUploadServiceHandler(
							dataWebServiceObject);
					handler.addListener(this, WebEvent.DATAUPLOAD);
					handler.runThread();
				}
								
			}else{
				LOG.info("EXCEPTION doAction:10090");				
				throw new GenException("", (ResourceParser.getResourceString(eventParcel.getSourceActivity(), R.string.networkunavailable)));
			}
		}catch (GenException e) {
			LOG.error("doAction:10091");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} 
		catch (Exception e) {
			LOG.error("doAction:10092");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject, WebEvent webEvent){
		try {
			if (!webServiceObject.isSuccessful()) {
				LOG.info("EXCEPTION doAction:10093");
				throw new GenException("", webServiceObject.getError());

			} else {
				XMLHandler.getResponse(webServiceObject);

				String status = webServiceObject.getCode();
				String description = webServiceObject.getDescription();
				String visitId = webServiceObject.getVisitId();

				(eventParcel.getDataObject()).setStatus(status);	
				(eventParcel.getDataObject()).setAlertDescription(description);
				if (status.equals("0")) {
					if(Application.getIsVisit()){  //Visit
						LOG.info("Visit data sent successfully.");
						//This event parcel has information about images. Otherwise this won't work.
						try {
							
							//Rename the visit folder from temp name to visit id
							//Inside this "DBService.saveVisitImageDetails(eventParcel, visitId);" function
							//visit object's visitfoldername property will get update
							File oldFolderName = new File(URL.getSLIC_VISITS() + ((VisitObject) eventParcel.getDataObject()).getVisitFolderName());
							File newFolderName = new File(URL.getSLIC_VISITS() + visitId);
							if (oldFolderName.exists()) {
								oldFolderName.renameTo(newFolderName);
							}
							
							if (((VisitObject) eventParcel.getDataObject()).getImageCount() != null 
									|| ((VisitObject) eventParcel.getDataObject()).getImageCount() != "") {
								int cnt = Integer.parseInt(((VisitObject) eventParcel.getDataObject()).getImageCount());
								if (cnt > 0 && !visitId.isEmpty()) {
									DBService.saveVisitImageDetails(eventParcel, visitId);
									LOG.info("Visit Image data saved successfully to the DB.");
								}
							}
						} catch (Exception e) {
							LOG.error("listenerCallbackWeb:10094");
							   if(e != null){
								LOG.error("Message: " + e.getMessage());
								LOG.error("StackTrace: " + e.getStackTrace());
							  }
						}
					}else{  //Job
						LOG.info("Job data sent successfully. ");
						//This event parcel has information about images. otherwise this won't work.
						try {
							if (((FormObject) eventParcel.getDataObject()).getImageCount() != null 
									|| ((FormObject) eventParcel.getDataObject()).getImageCount() != "") {
								int cnt = Integer.parseInt(((FormObject) eventParcel.getDataObject()).getImageCount());
								if (cnt > 0 && !visitId.isEmpty()) {
									DBService.saveSAImageDetails(eventParcel, visitId);
									LOG.info("Job Image data saved successfully to the DB.");
								}
							}
						} catch (Exception e) {
							LOG.error("listenerCallbackWeb:10094");
							   if(e != null){
								LOG.error("Message: " + e.getMessage());
								LOG.error("StackTrace: " + e.getStackTrace());
							  }
						}
					}
				} else {
					if(Application.getIsVisit())
						LOG.info("Visit data did NOT sent successfully.");
					else
						LOG.info("Job data did NOT sent successfully.");
				}
				super.listenerCallback(eventParcel);
			}
		} catch (GenException e) {
			LOG.error("listenerCallbackWeb:10095");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			(eventParcel.getDataObject()).setStatus("2000");
			(eventParcel.getDataObject()).setAlertDescription(e.getMessage());			
			super.listenerCallback(eventParcel);			
		} catch (Exception e) {
			LOG.error("listenerCallbackWeb:10096");
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

