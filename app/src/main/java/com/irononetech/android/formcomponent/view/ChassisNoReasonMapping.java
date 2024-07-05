package com.irononetech.android.formcomponent.view;

public enum ChassisNoReasonMapping {

	A("Difficult to trace due to accident damage",2),
	B("Refer special comment for details",1);
	  
	private final String a;
	private final int b;
	
	ChassisNoReasonMapping(String a, int b){
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