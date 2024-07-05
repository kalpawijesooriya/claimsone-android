package com.irononetech.android.formcomponent.view;

public enum CommentOpt {
	Available("Available", 1),
	NotAvailable("Not Available", 0),;
  
	private final String a;
	private final int b;
	
	CommentOpt(String a, int b){
		this.a = a;
		this.b = b;
	}
	
	public String getString(){
		return this.a;
	}

	public int getInt() {
		return this.b;
	}
}
