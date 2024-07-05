package com.irononetech.android.enums;

//not using this enum anymore 
public enum imageCategoryListTwo {
	DLStatement("Driver Statement / DL / NIC", 0),
	TechnicalOfficerComments("Technical Officer Comments", 1),
	ClaimFormImage("Claim Form Image", 2),						//Deprecated
	ARI("ARI", 3),												//Deprecated
	DR("DR", 4),												//Deprecated
	SeenVisit("Seen Visit", 5),									//Deprecated
	SpecialReport1("Special Report  1", 6),						//Deprecated
	SpecialReport2("Special Report  2", 7),						//Deprecated
	SpecialReport3("Special Report  3", 8),						//Deprecated
	Supplementary1("Supplementary 1", 9),						//Deprecated
	Supplementary2("Supplementary 2", 10),						//Deprecated
	Supplementary3("Supplementary 3", 11),						//Deprecated
	Supplementary4("Supplementary 4", 12),						//Deprecated
	Acknowledgment("Acknowledgment", 13),						//Deprecated
	SalvageReport("Salvage Report", 14),;						//Deprecated
	
	private final String a;
	private final int b;
	
	imageCategoryListTwo(String a, int b){
		this.a = a;
		this.b = b;
	}
	
	public String getString(){
		return this.a;
	}
}
