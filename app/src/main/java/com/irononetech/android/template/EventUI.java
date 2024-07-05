package com.irononetech.android.template;

import java.util.ArrayList;
import java.util.Iterator;


public class EventUI {
	Object _sender;
	ArrayList<ListenerUI> _listeners;
	public EventUI(Object sender) {
		this._sender = sender;
		this._listeners = new ArrayList<ListenerUI>();
	}
	
	public void attachEventUI(ListenerUI listener) {
		this._listeners.add(listener);
	}
	
	public void notifyEventUI(EventParcel eventParcel) {
		Iterator<ListenerUI> itr = _listeners.iterator();
		while(itr.hasNext()){
			itr.next().listenerUICallbackUI(eventParcel);
		}
	}
}
