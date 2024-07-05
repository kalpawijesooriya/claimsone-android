package com.irononetech.android.template;

public class Model {
	public Event xEvent;
	public Model() {
	}
	public void addListener(Listener listener, AppEvent appEvent) {		
		this.xEvent = null;
		this.xEvent = new Event(this);
		this.xEvent.attachEvent(listener);
	}	
	public void callback(EventParcel eventParcel) throws Exception {
		xEvent.notifyEvent(eventParcel);
	}
}
