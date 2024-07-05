package com.irononetech.android.formcomponent.view;

public enum OldNewLicenseMapping {
	Old("Old",0),
	New("New",1),;
  
	private final String a;
	private final int b;
	OldNewLicenseMapping(String a, int b){
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
