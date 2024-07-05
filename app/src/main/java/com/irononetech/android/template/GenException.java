package com.irononetech.android.template;

public class GenException extends Exception {
	
	String TAG ="";
	public String getTAG() {
		return TAG;
	}
	
	public GenException(String TAG, String err) {
		super(err);
		this.TAG = TAG;	
	}
}
