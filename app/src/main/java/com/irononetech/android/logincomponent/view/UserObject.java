package com.irononetech.android.logincomponent.view;

import com.irononetech.android.template.DataObject;

public class UserObject extends DataObject {
	String username;
	String password;
	String result = "";


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "UserObject [password=" + password + ", username=" + username
				+ "]";
	}

}
