package com.irononetech.android.formcomponent.view;

public enum RelationshipMapping {		
		Employee("Employee",1),
        Spouse("Spouse",2),
        Relations("Relations",3),
        Friend("Friend",4),
        NoRelation("No relation - Rented vehicle",5),
        Insured("Insured",6);
       
		private final String a;
		private final int b;
		RelationshipMapping(String a, int b){
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
