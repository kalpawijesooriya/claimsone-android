package com.irononetech.android.dataretrievecomponent;

import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.template.DataObject;

public class DataRetrieveUIObject extends DataObject {

	String visitId="";
	FormObject formObject = null;
	VisitObject visitObject = null;
	
	public String getVisitId() {
		return visitId;
	}
		
	public void setVisitId(String vNo) {
		this.visitId = vNo;
	}
		
	public FormObject getFormObject() {
		return formObject;
	}

	public void setFormObject(FormObject formObject) {
		this.formObject = formObject;
	}

	public VisitObject getVisitObject() {
		return visitObject;
	}
	
	public void setVisitObject(VisitObject vObject) {
		this.visitObject = vObject;
	}
}
