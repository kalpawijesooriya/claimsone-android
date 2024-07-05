package com.irononetech.android.formcomponent.view;

public enum ItemSentStatus {
	All("All", 0),
	Completed("Completed",1),
	Deleted("Deleted", 2),
	Pending("Pending",3);

	private final String a;
	private final int b;
	ItemSentStatus(String a, int b){
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
