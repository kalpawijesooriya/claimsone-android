package com.irononetech.android.logincomponent.model;

import com.irononetech.android.Webservice.ServiceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;

public class LoginServiceHandler extends ServiceHandler {
	Logger LOG = LoggerFactory.getLogger(LoginServiceHandler.class);

	WebServiceObject webServiceObject;

	public LoginServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	@Override
	public void excecute() {
		try {
			String url = URL.getBaseUrl()+URL.getLoginUrlRemainder();
			String username = ((LoginWebServiceObject) (webServiceObject)).getUsername();
			String password = ((LoginWebServiceObject) (webServiceObject)).getPassword();
			LoginWebService ws = new LoginWebService();
			String xmlText = ws.sendRequestForLogin(url,username, password);
			webServiceObject.setXmlText(xmlText);
			webServiceObject.setSuccessful(true);
		} catch (GenException e) {
			 LOG.error("excecute:10188");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			LOG.error("excecute:10189");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setSuccessful(false);
			webServiceObject.setError(e.getMessage());
		}
	}
}
