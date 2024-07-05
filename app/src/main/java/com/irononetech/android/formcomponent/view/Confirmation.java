package com.irononetech.android.formcomponent.view;

public enum Confirmation {
	Yes("Yes",1),
	No("No",0),;
  
	private final String a;
	private final int b;
	Confirmation(String a, int b){
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
