package com.irononetech.android.template;

import android.app.Activity;

public class EventParcel {
	UIEvent uIEvent;
	DataObject dataObject;
	Activity sourceActivity;
	
	public UIEvent getuIEvent() {
		return uIEvent;
	}


	public void setuIEvent(UIEvent uIEvent) {
		this.uIEvent = uIEvent;
	}


	public DataObject getDataObject() {
		return dataObject;
	}


	public void setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
	}


	public Activity getSourceActivity() {
		return sourceActivity;
	}

	
	public EventParcel(UIEvent uIEvent, Activity sourceActivity, DataObject dataObject) {
		this.uIEvent = uIEvent;
		this.sourceActivity = sourceActivity;
		this.dataObject = dataObject;
	}


	@Override
	public String toString() {
		return "EventParcel [dataObject=" + dataObject + ", sourceActivity="
				+ sourceActivity + ", uIEvent=" + uIEvent + "]";
	}
	
}
