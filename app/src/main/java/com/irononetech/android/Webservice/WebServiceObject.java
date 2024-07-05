package com.irononetech.android.Webservice;

import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.R.string;
import android.net.Uri;

public class WebServiceObject {
	private URL url;
	private String visitId = "";
	private String code;
	private String xmlText;
	private String description="Description not available!";
	private String error;
	private boolean successful = false;
	static Logger LOG = LoggerFactory.getLogger(WebServiceObject.class);

	private String CurrentWebVersion;
	private String MinimumSupportedAppVersion;
	private String LatestAppVersioninGooglePlay;
	private String ForceLatestVersionToUsers;
	private String GooglePlayAppURL;
	
	
	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
		
		if(error == null || error.isEmpty()){
			error = "Network Failed!";
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getXmlText() {
		return xmlText;
	}

	public void setXmlText(String xmlText) {
		LOG.info("Server Responce: " + xmlText);
		this.xmlText = xmlText;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String vId) {
		visitId = vId;
	}

	
	public String getCurrentWebVersion() {
		return CurrentWebVersion;
	}
	public String getMinimumSupportedAppVersion() {
		return MinimumSupportedAppVersion;
	}
	public String getLatestAppVersioninGooglePlay() {
		return LatestAppVersioninGooglePlay;
	}
	public Boolean getForceLatestVersionToUsers() {
		return Boolean.parseBoolean(ForceLatestVersionToUsers);
	}
	public Uri getGooglePlayAppURL() {
		return Uri.parse(GooglePlayAppURL);
	}
	
	public void setCurrentWebVersion(String val) {
		CurrentWebVersion = val;		
	}
	public void setMinimumSupportedAppVersion(String val) {
		MinimumSupportedAppVersion = val;
	}
	public void setLatestAppVersioninGooglePlay(String val) {
		LatestAppVersioninGooglePlay = val;
	}
	public void setForceLatestVersionToUsers(String val) {
		ForceLatestVersionToUsers = val;
	}
	public void setGooglePlayAppURL(String val) {
		GooglePlayAppURL = val;
	}
}