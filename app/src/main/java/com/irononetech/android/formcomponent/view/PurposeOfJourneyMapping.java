package com.irononetech.android.formcomponent.view;

public enum PurposeOfJourneyMapping {
	Private("Private",1),
	Hiring("Hiring",2),
    RentACar("Rent a Car",3),
	Official("Official", 4); // Thisaru Guruge | 26/01/2016 | Added new Purpose of Journey "Official" with ID 4
    
	private final String a;
	private final int b;
	PurposeOfJourneyMapping(String a, int b){
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