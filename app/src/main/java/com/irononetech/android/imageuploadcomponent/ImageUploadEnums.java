package com.irononetech.android.imageuploadcomponent;

public enum ImageUploadEnums {
	
	PointsOfImpact("Points Of Impact",1),
	//DriverStatement("Driver Statement", 2), 				/*will not use after version 3.0*/
	AccidentImages("Accident Images", 3),
	DLStatement("Driver Statement / DL / NIC", 4),
	TechnicalOfficerComments("Technical Officer Comments", 5),
	ClaimFormImage("Claim Form Image", 6),
	//ARI("ARI", 7),
	//DR("DR", 8),
	//SeenVisit("Seen Visit", 9),
	//SpecialReport1("Special Report 1", 10),
	//SpecialReport2("Special Report 2", 11),
	//SpecialReport3("Special Report 3", 12),
	//Supplementary1("Supplementary 1", 13),
	//Supplementary2("Supplementary 2", 14),
	//Supplementary3("Supplementary 3", 15),
	//Supplementary4("Supplementary 4", 16),
	//Acknowledgment("Acknowledgment", 17),
	//SalvageReport("Salvage Report", 18),
	//SpecialReport4("SpecialReport 4", 19),
	
	InspectionPhotosSeenVisitsAnyOther("Inspection Photos / Seen Visits / Any Other", 20),
	EstimateAnyotherComments("Estimate / Any Other Comments", 21);
	
	
	private final String a;
	private final int b;
	
	ImageUploadEnums(String a, int b){
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