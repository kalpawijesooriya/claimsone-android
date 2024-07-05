package com.irononetech.android.Webservice;

import java.util.ArrayList;
import java.util.Iterator;

public class EventWeb {
	Object _sender;
	ArrayList<ListenerWeb> _listeners;
	WebEvent _webEvent;

	public EventWeb(Object sender) {
		this._sender = sender;
		this._listeners = new ArrayList<ListenerWeb>();
	}

	public void attachEvent(ListenerWeb listener, WebEvent webEvent) {
		_webEvent = webEvent;
		this._listeners.add(listener);
	}

	public void notifyEvent(WebServiceObject webServiceObject) throws Exception {
		Iterator<ListenerWeb> itr = _listeners.iterator();
		while (itr.hasNext()) {
			itr.next().listenerCallbackWeb(webServiceObject, _webEvent);
		}
	}
}
