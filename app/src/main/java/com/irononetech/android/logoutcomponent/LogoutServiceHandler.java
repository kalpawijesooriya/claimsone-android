package com.irononetech.android.logoutcomponent;

import com.irononetech.android.Webservice.ServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;

public class LogoutServiceHandler extends ServiceHandler {

	Logger LOG = LoggerFactory.getLogger(LogoutServiceHandler.class);

	WebServiceObject webServiceObject;
	
	public LogoutServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	@Override
	public void excecute() {
		try {
			String url = URL.getBaseUrl()+URL.getLogoutURLRemainder();
		
			LogoutWebService ws = new LogoutWebService();
			String xmlText = ws.sendRequestForLogout(url);
			if(xmlText!=null){
				webServiceObject.setXmlText(xmlText);
			}else{
				webServiceObject.setError("Response is null");
			}
		} catch (GenException e) {
			 LOG.error("doAction:10201");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			 LOG.error("doAction:10202");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setError(e.getMessage());
		}
	}
}