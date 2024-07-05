package com.irononetech.android.template;

public abstract class IronDroidApplication implements ListenerUI{
	/**
	 * A method to invoke when an action occurs in a activity
	 * @param eventParcel
	 * @throws Exception
	 * @throws GenException
	 */
	public abstract void doActionOnEvent(EventParcel eventParcel) throws Exception,
	GenException;
}
