package com.irononetech.android.formcomponent.view;

public enum TimePeriods {

		AM("AM",1),
        PM("PM",2);
               
		private final String a;
		private final int b;
		TimePeriods(String a, int b){
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
