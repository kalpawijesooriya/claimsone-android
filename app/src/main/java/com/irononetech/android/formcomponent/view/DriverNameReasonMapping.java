package com.irononetech.android.formcomponent.view;

public enum DriverNameReasonMapping {

	A("Driver hospitalize after the accident",4),
	B("Driver runs off after accident",3),
	C("Driver not available at the accident site",2),
	D("Refer special comment for details",1);
  
	private final String a;
	private final int b;
	DriverNameReasonMapping(String a, int b){
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
