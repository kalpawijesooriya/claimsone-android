package com.irononetech.android.formcomponent.view;

public enum ConsistencyMapping {
	Yes("Yes",1),
	Pending("Pending",0);
  
	private final String a;
	private final int b;
	ConsistencyMapping(String a, int b){
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
