package com.irononetech.android.enums;

public enum imageCategoryListThree {
	TechnicalOfficerComments("Technical Officer Comments", 0),
	EstimateAnyotherComments("Estimate / Any Other Comments", 1),
	InspectionPhotosSeenVisitsAnyOther("Inspection Photos / Seen Visits / Any Other", 2);

	private final String a;
	private final int b;

	imageCategoryListThree(String a, int b) {
		this.a = a;
		this.b = b;
	}

	public String getString() {
		return this.a;
	}
}
