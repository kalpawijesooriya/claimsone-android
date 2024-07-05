package com.irononetech.android.searchcomponent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.irononetech.android.Webservice.WebServiceObject;

public class SearchWebServiceObject extends WebServiceObject {
	private String vehicleNoOrJobNo;
	private ArrayList<String> jobsByVehicleNoOrJobNo;
	//List<NameValuePair> searchedJobsWithVisitId = new ArrayList<NameValuePair>();
	//nvps.add(new BasicNameValuePair("RefNo", vehicleNo));
	
	public ArrayList<String> getJobsByVehicleNo() {
		return jobsByVehicleNoOrJobNo;
	}

	public void setJobsByVehicleNoOrJobNo(ArrayList<String> jobsByVehicleNoOrJobNo) {
		this.jobsByVehicleNoOrJobNo = jobsByVehicleNoOrJobNo;
	}

	public String getVehicleNoOrJobNo() {
		return vehicleNoOrJobNo;
	}

	public void setVehicleNoOrJobNo(String vehicleNoOrJobNo) {
		this.vehicleNoOrJobNo = vehicleNoOrJobNo;
	}
	
	/*public List<NameValuePair> getJobOrVehicleNoWithVisitId() {
		return searchedJobsWithVisitId;
	}
	
	public void setSearchedJobsWithVisitId(List<NameValuePair> searchedJobsWithVisitId) {
		this.searchedJobsWithVisitId = searchedJobsWithVisitId;
	}*/
}
