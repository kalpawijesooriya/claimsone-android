package com.irononetech.android.formcomponent.view;

public enum VisitTypeMapping {
	
	SAForm("SA Form", 0),  //will not use in tab but used in web
	ARI("ARI", 1),
	DR("DR", 2),
	SeenVisit("Seen Visit", 3),
	SpecialReport1("Special Report 1", 4),
	SpecialReport2("Special Report 2", 5),
	SpecialReport3("Special Report 3", 6),
	SpecialReport4("Special Report 4", 7),
	Supplementary1("Supplementary 1", 8),
	Supplementary2("Supplementary 2", 9),
	Supplementary3("Supplementary 3", 10),
	Supplementary4("Supplementary 4", 11),
	SalvageReport("Salvage Report", 12),
	GarageInspection1("Garage Inspection 1", 13),
	GarageInspection2("Garage Inspection 2", 14),
	GarageInspection3("Garage Inspection 3", 15);

	private final String a;
	private final int b;
	
	VisitTypeMapping(String a, int b){
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
