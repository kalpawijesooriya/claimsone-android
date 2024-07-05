/*
 * Created By: Thisaru Guruge
 * Date: 29 / 01 / 2016
 * This ENUM is to map the NIC type of the driver
 * From the three types available
 * 		- Old - ID = 1
 * 		- New - ID = 2
 * 		- Passport - ID = 3
 */

package com.irononetech.android.formcomponent.view;

public enum NICTypeMapping {
	Old("Old", 0),
	New("New", 1),
	Passport("Passport", 2);
	
	private final String a;
	private final int b;
	
	NICTypeMapping (String a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public String getString () {
		return this.a;
	}
	
	public int getInt() {
		return this.b;
	}
}
