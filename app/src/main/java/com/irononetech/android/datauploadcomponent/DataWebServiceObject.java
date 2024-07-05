package com.irononetech.android.datauploadcomponent;

import com.irononetech.android.Webservice.WebServiceObject;

public class DataWebServiceObject extends WebServiceObject {

	String xmlToPass;
	public String getXmlToPass() {
		return xmlToPass;
	}
	public void setXmlToPass(String xmlToPass) {
		this.xmlToPass = xmlToPass;
	}
	String response;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}