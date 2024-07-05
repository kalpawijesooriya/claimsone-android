package com.irononetech.android.template;

import java.util.ArrayList;
import java.util.Iterator;


public class Event {
	Object _sender;
	ArrayList<Listener> _listeners;
	public Event(Object sender) {
		this._sender = sender;
		this._listeners = new ArrayList<Listener>();
	}
	
	public void attachEvent(Listener listener) {
		this._listeners.add(listener);
	}
	
	public void notifyEvent(EventParcel eventParcel) {
		Iterator<Listener> itr = _listeners.iterator();
		while(itr.hasNext()){
			itr.next().listenerCallback(eventParcel);
		}
	}

	
}
