package com.irononetech.android.template;

public abstract class Controller implements Listener{
	public static Controller controller;
	public EventUI yEvent;
	public Controller() {
	}
	
	public abstract void doAction(EventParcel eventParcel) throws Exception, GenException;
	
	public abstract void dataValidation(DataObject dataObject) throws GenException;
	
	@Override
	public void listenerCallback(EventParcel eventParcel){
		this.yEvent.notifyEventUI(eventParcel);
	}
	
	public void addListener(ListenerUI listener) {
		this.yEvent = null;
		this.yEvent = new EventUI(this);		
		this.yEvent.attachEventUI(listener);
	}
}
