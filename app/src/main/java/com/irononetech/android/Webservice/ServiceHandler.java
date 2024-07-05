package com.irononetech.android.Webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ServiceHandler implements Runnable, ListenerWeb {

	Logger LOG = LoggerFactory.getLogger(ServiceHandler.class);
	public EventWeb zEvent;
	WebEvent webEvent;
	private Thread thread = null;
	WebServiceObject webServiceObject;

	public ServiceHandler(WebServiceObject webServiceObject) {
		this.webServiceObject = webServiceObject;
	}

	public void addListener(ListenerWeb listener, WebEvent webEvent) {
		this.webEvent = webEvent;
		this.zEvent = null;
		this.zEvent = new EventWeb(this);
		this.zEvent.attachEvent(listener, webEvent);
	}

	public void stopThread() {
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
			thread = null;
		} catch (Exception e) {
			LOG.error("stopThread:10405");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	/**
	 * void runThread() start thread
	 */
	public void runThread() {
		try {
			runThread(0);
		} catch (Exception e) {
			LOG.error("runThread:10406");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	/**
	 * void runThread() start thread
	 */
	public void runThread(int sleep) {
		try {
			thread = new Thread(this);
			try {
				if (sleep > 0) {
					Thread.sleep(sleep);
				}
			} catch (InterruptedException e) {
				LOG.error("runThread:10407");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			thread.start();
		} catch (Exception e) {
			LOG.error("runThread:11408");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	/**
	 * @param cancelByUser
	 *            void callBack(boolean cancelByUser)
	 * @throws Exception
	 */
	private void callBack() throws Exception {
		synchronized (zEvent) {
			zEvent.notifyEvent(webServiceObject);
		}
	}

	@Override
	public void run() {
		excecute();
		try {
			callBack();
		} catch (Exception e) {
			LOG.error("runThread:10409");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public abstract void excecute();

	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) throws Exception {
		this.webServiceObject = webServiceObject;
		;
	}
}