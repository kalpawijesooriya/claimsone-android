package com.irononetech.android.formcomponent.view;

public enum TypeofDrivingLicenseMapping {
	OTHER("OTHER",0),
	MTA32("MTA 32",1),
    MTA39("MTA 39",2),
    CMT49("CMT 49",3),
    INTLIC("INTLIC",4),
    MTA37("MTA 37", 5); // Thisaru Guruge | 26/01/2016 | Added new Type of Driving License "MTA 37" with ID 5
  
	private final String a;
	private final int b;
	TypeofDrivingLicenseMapping(String a, int b){
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
