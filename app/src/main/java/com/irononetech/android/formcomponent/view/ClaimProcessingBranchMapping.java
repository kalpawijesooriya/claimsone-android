package com.irononetech.android.formcomponent.view;

public enum ClaimProcessingBranchMapping {
	Aluthgama	("Aluthgama",	72),
	Ambalangoda	("Ambalangoda",	13),
	Ampara	("Ampara",36),
	Anuradhapura	("Anuradhapura",	7),
	Avissawella	("Avissawella",	25),
	Badulla	("Badulla",	10),
	Bandarawela	("Bandarawela",	29),
	Batticaloa	("Batticaloa",	94),
	Chilaw	("Chilaw",	20),
	Dambulla	("Dambulla",	26),
	Embilipitiya 	("Embilipitiya", 	31),
	FBDKandy	("FBD Kandy",	129),
	Galle	("Galle",	3),
	Gampaha 	("Gampaha", 	27),
	HeadOffice	("Head Office",	55),
	Horana	("Horana",	74),
	JaEla	("Ja Ela",	12),
	Jaffna	("Jaffna",	41),
	Kadawatha	("Kadawatha",	80),
	Kaduwela	("Kaduwela",	44),
	Kalutara	("Kalutara",	1),
	Kandy	("Kandy",	5),
	Kegalle	("Kegalle",	6),
	Kilinochchi	("Kilinochchi",	101),
	Kiribathgoda	("Kiribathgoda",	45),
	Kuliyapitiya 	("Kuliyapitiya", 	21),
	Kurunegala 	("Kurunegala", 	9),
	KurunegalaCity	("Kurunegala City",	122),
	Matale 	("Matale", 	17),
	Matara	("Matara",	4),
	Matugama	("Matugama",	71),
	MELSIRIPURA	("MELSIRIPURA",	106),
	Narammala	("Narammala",	120),
	Nawalapitiya	("Nawalapitiya",	66),
	Negombo	("Negombo",	8),
	Nikaweratiya	("Nikaweratiya",	49),
	Nugegoda	("Nugegoda",51),
	Pilimathalawa	("Pilimathalawa",	65),
	Puttalam	("Puttalam",	22),
	Ratmalana	("Ratmalana",	30),
	Ratnapura	("Ratnapura",	2),
	Vavuniya	("Vavuniya",	52),
	Wariyapola	("Wariyapola",	105),;
	private final String a;
	private final int b;
	
	ClaimProcessingBranchMapping(String a, int b){
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
