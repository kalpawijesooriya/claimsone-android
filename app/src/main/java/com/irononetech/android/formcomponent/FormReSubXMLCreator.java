package com.irononetech.android.formcomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;

public class FormReSubXMLCreator {

	Logger LOG = LoggerFactory.getLogger(FormReSubXMLCreator.class);
	
	public String getFormXML(FormObject formObject) {
		
	try {
		String formXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + 
			"<Request>" + 
			  "<Data>" + 
			    "<Job action=\"resubmission\">" + 
			      "<FieldList>" + 
			        "<JobNo>" + formObject.getJobNo() + "</JobNo>" + 
			        "<RefNo>" + formObject.getRefNo() + "</RefNo>" + 
			        "<CSRUserName>" +  Application.getCurrentUser() + "</CSRUserName>" + 
			        "<Comment>" + formObject.getComments() + "</Comment>" + 
			        "<ImageCount>" + formObject.getImageCount() + "</ImageCount>" +
			        "<TimeVisited>" + formObject.getTimeVisited() + "</TimeVisited>" +
			        "<SubmissionType>" + formObject.getResubmissionType()  + "</SubmissionType>" +
			      "</FieldList>" +			    
			    "</Job>" +
			    "<DateTime></DateTime>" +
			 "</Data>" + 
			"</Request>";
			
		LOG.info("RESUBMIT XML: " + formXML);
			return formXML;
	} catch (Exception e) {
		LOG.error("doAction:10150");
		   if(e != null){
			LOG.error("Message: " + e.getMessage());
			LOG.error("StackTrace: " + e.getStackTrace());
		  }
		return "";
	}
	}
}
