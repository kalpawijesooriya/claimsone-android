package com.irononetech.android.logincomponent.model;

import com.irononetech.android.Webservice.WebServiceObject;

public class LoginWebServiceObject extends WebServiceObject {
private String username;
private String password;

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}
}