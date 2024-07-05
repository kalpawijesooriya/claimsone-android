package com.irononetech.android.searchcomponent;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchWebServiceHandler extends ServiceHandler {
	static Logger LOG = LoggerFactory.getLogger(SearchWebServiceHandler.class);

	WebServiceObject webServiceObject;

	public SearchWebServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	@Override
	public void excecute() {
		// String url = "http://172.21.0.87:5656/Job/SearchByVehicleNo?fmt=xml";
		try {
			String url = URL.getBaseUrl() + URL.getSearchByVisitIdUrlRemainder();
			String vehicleNoOrJobNo = ((SearchWebServiceObject) (webServiceObject)).getVehicleNoOrJobNo();

			boolean proceed = Application.automaticlogin();

			if (proceed) {
				SearchWebService ws = new SearchWebService();
				String response = ws.searchByVehicleNo(url, vehicleNoOrJobNo);
				webServiceObject.setXmlText(response);
				webServiceObject.setSuccessful(true);
			} else {
				webServiceObject.setSuccessful(false);
				webServiceObject.setError("Automatic login has been failed.");
			}
			// if(response!=null){
			// webServiceObject.setXmlText(response);
			// }else{
			// webServiceObject.setError("Response is null");
			// }
			// webServiceObject.setXmlText(xmlText);
		} catch (GenException e) {
			LOG.error("excecute:10395");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			LOG.error("excecute:10396");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		}
	}
}