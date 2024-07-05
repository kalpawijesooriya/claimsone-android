package com.irononetech.android.template;

import com.irononetech.android.Webservice.WebServiceObject;

public class DataObject{
	//status = 800 ; general error
	String status = "";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	String AlertDescription = "";

	public String getAlertDescription() {
		return AlertDescription;
	}

	public void setAlertDescription(String errorMessage) {
		AlertDescription = errorMessage;
		
		if(errorMessage == null || errorMessage.isEmpty()){
			AlertDescription = "Network Failed!";
		}
	}

	WebServiceObject webServiceObject;	
	public WebServiceObject getWebServiceObject() {
		return webServiceObject;
	}
	public void setWebServiceObject(WebServiceObject objc) {
		webServiceObject = objc;
	}
	
	//used to determine does search result contain one result or not
	/*boolean oneResult =false;
	public boolean getOneResult() {
		return oneResult;
	}

	public void setOneResult(boolean val) {
		oneResult = val;
	}*/
}
