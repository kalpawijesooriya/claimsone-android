package com.irononetech.android.dataretrievecomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;

public class DataRetrieveServiceHandler extends ServiceHandler {
	Logger LOG = LoggerFactory.getLogger(DataRetrieveServiceHandler.class);
	WebServiceObject webServiceObject;
	
	public DataRetrieveServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	@Override
	public void excecute() {
		try {
			String url = "";
			if(Application.getIsVisit())  //searching for a visit data
				url = URL.getBaseUrl() + URL.getVisitDataUsingVisitIdUrlRemainder();
			else  // searching for a job data
				url = URL.getBaseUrl() + URL.getJobDataUsingVisitIdUrlRemainder();

			String vNo = ((DataRetrieveWebServiceObject)(webServiceObject)).getVisitId();

			DataRetrieveWebService ws = new DataRetrieveWebService();
			String response = ws.getJobOrVisitDataByUsingVisitId(url, vNo);
			webServiceObject.setXmlText(response);
			webServiceObject.setSuccessful(true);

			//			if (response != null) {
			//				webServiceObject.setXmlText(response);
			//			} else {
			//				webServiceObject.setError("Response is null");
			//			}

		} catch (GenException e) {
			LOG.error("execute:10084");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			LOG.error("execute:10085");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		}
	}
}