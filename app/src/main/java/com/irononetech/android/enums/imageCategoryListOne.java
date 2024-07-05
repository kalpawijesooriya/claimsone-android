package com.irononetech.android.enums;

public enum imageCategoryListOne {
	DLStatement("Driver Statement / DL / NIC", 0),
	TechnicalOfficerComments("Technical Officer Comments", 1),
	ClaimFormImage("Claim Form Image", 2),;
	
	private final String a;
	//private final int b;
	
	imageCategoryListOne(String a, int b){
		this.a = a;
		//this.b = b;
	}
	
	public String getString(){
		return this.a;
	}
}