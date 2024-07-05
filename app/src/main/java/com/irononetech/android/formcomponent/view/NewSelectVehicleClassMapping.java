package com.irononetech.android.formcomponent.view;

public enum NewSelectVehicleClassMapping {

	
		A1("A1",21),
		A("A",22),
        B1("B1",23),
        B("B",24),
        C1("C1",25),
        C("C",26),
        CE("CE",27),
        D1("D1",28),
        D("D",29),
		DE("DE",30),
        G1("G1",31),
        G("G",32),
        J("J",33);
		private final String a;
		private final int b;
		NewSelectVehicleClassMapping(String a, int b){
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
	
