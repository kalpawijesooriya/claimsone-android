package com.irononetech.android.dataretrievecomponent;

import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;

public class DataRetrieveWebServiceObject extends WebServiceObject {
	private String visitId;
	private FormObject formObject;
	private VisitObject visitObject;
	
	public String getVisitId() {
		return visitId;
	}
	public void setVisitId(String vNo) {
		this.visitId = vNo;
	}
	
	//SA Form
	public FormObject getFormObject() {
		return formObject;
	}
	public void setFormObject(FormObject formObject) {
		this.formObject = formObject;
	}
	
	//Visit
	public VisitObject getVisitObject() {
		return visitObject;
	}
	public void setVisitObject(VisitObject visitObject) {
		this.visitObject = visitObject;
	}
}
