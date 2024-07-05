package com.irononetech.android.datauploadcomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;

public class DataUploadServiceHandler extends ServiceHandler {
	Logger LOG = LoggerFactory.getLogger(DataUploadServiceHandler.class);
	WebServiceObject webServiceObject;

	public DataUploadServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	@Override
	public void excecute(){
		try {
			String url = "";
			if(Application.getIsVisit()){  // is a visit
				url = URL.getBaseUrl() + URL.getVisitFormUploadUrlRemainder();
			}else{ // is a form
				url = URL.getBaseUrl() + URL.getSAFormUploadUrlRemainder();
			}
			
			String xmlJobToPass = ((DataWebServiceObject) webServiceObject).getXmlToPass();
			DataUploadWebService dataUploadWebService = new DataUploadWebService();
			String response = dataUploadWebService.dataUpload(url, xmlJobToPass);
			
			if(response == null){
				response = "";
				LOG.info("Data sent, but Response is null.");
				
			}
			webServiceObject.setXmlText(response);
			webServiceObject.setSuccessful(true);
		} catch (GenException e) {
			LOG.error("excecute:10097");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			LOG.error("excecute:10098");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		}
	}
}
