package com.irononetech.android.formcomponent.view;

public enum OldSelectVehicleClassMapping {

	
		A1("A1",1),
		A("A",2),
        B1("B1",3),
        B("B",4),
        C1("C1",5),
        C("C",6),
        D("D",7),
		E("E",8),
        F("F",9),
        G1("G1",10),
        G("G",11),
        H("H",12);
		private final String a;
		private final int b;
		OldSelectVehicleClassMapping(String a, int b){
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
	
