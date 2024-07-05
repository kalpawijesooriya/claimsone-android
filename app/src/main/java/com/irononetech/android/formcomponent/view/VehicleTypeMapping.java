package com.irononetech.android.formcomponent.view;

public enum VehicleTypeMapping {
	BUS("Bus",1),
	CAR("Cars / Double cabs / Jeeps",2),
	LORRY("Lorry",3),
	VAN("Van",4),
	THREEWHEEL("Three Wheel", 5),
	MOTORCYCLE("Motorcycle", 6),
	TRACTOR4WD("Tractor 4WD", 7),
	HANDTRACTOR("Hand Tractor", 8);
  
	private final String a;
	private final int b;
	
	VehicleTypeMapping(String a, int b){
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
