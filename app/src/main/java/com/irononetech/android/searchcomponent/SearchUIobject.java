package com.irononetech.android.searchcomponent;

import java.util.ArrayList;
import com.irononetech.android.template.DataObject;

public class SearchUIobject extends DataObject {

	private String jobOrVehicleNo;
	boolean isVisit = false;
	private ArrayList<String> searchedJobs;
	//List<NameValuePair> searchedJobsWithVisitId = new ArrayList<NameValuePair>();
	//nvps.add(new BasicNameValuePair("RefNo", vehicleNo));

	public ArrayList<String> getSearchedJobs() {
		return searchedJobs;
	}

	public void setSearchedJobs(ArrayList<String> searchedJobs) {
		this.searchedJobs = searchedJobs;
	}

	public String getJobOrVehicleNo() {
		return jobOrVehicleNo;
	}

	/*public List<NameValuePair> getJobOrVehicleNoWithVisitId() {
		return searchedJobsWithVisitId;
	}
	
	public void setSearchedJobsWithVisitId(List<NameValuePair> searchedJobsWithVisitId) {
		this.searchedJobsWithVisitId = searchedJobsWithVisitId;
	}*/
	
	public void setJobOrVehicleNo(String jobOrVehicleNo) {
		this.jobOrVehicleNo = jobOrVehicleNo;
	}
	
	/*public boolean getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(boolean It) {
		this.isVisit = It;
	}*/
}
