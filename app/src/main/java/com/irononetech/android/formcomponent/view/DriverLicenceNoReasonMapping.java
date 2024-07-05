package com.irononetech.android.formcomponent.view;

public enum DriverLicenceNoReasonMapping {

	A("Driving license not clear",3),
	B("Driving license not produce",2),
	C("Refer special comment for details",1),
	D("Not Applicable",4);
  
	private final String a;
	private final int b;
	
	DriverLicenceNoReasonMapping(String a, int b){
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
