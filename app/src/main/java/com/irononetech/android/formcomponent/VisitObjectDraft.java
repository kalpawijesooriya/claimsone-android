package com.irononetech.android.formcomponent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VisitObjectDraft implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Visit Details
	String jobNo = "";
	String vehicleNo = "";
	String inspectionDate = "";
	String chassisNo = "";
	String engineNo = "";
	String previousComments = "";
	String comments = "";

	String inspectionTypeInText = "";

	int inspectionType = 1;
	int inspectionTypeArrindex = 0;

	String DraftFileName = "";
	String visitFolderName = "";
	String visitId = "";
	boolean selectedTechnicalOfficerCommentsImages[] = new boolean[400];
	boolean estimateAnyotherComments[] = new boolean[400];
	boolean inspectionPhotosSeenVisitsAnyOther[] = new boolean[400];
	
	String ImageCount = "0";
	
	public VisitObjectDraft() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");// "MMMMM d, yyyy h:mm a");
		String inspectionDate = formatter.format(Calendar.getInstance()
				.getTime());

		setInspectionDate(inspectionDate);
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jNo) {
		jobNo = jNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vNo) {
		vehicleNo = vNo;
	}

	public String getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(String iDate) {
		inspectionDate = iDate;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String eNo) {
		engineNo = eNo;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String cNo) {
		chassisNo = cNo;
	}
	
	public String getPreviousComments() {
		return previousComments;
	}

	public void setPreviousComments(String pComments) {
		previousComments = pComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String com) {
		comments = com;
	}

	public int getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(int iType) {
		inspectionType = iType;
	}

	public int getInspectionTypeArrIndex() {
		return inspectionTypeArrindex;
	}

	public void setInspectionTypeArrIndex(int pos) {
		inspectionTypeArrindex = pos;
	}

	public String getInspectionTypeInText() {
		return inspectionTypeInText;
	}

	public void setInspectionTypeInText(String inspectionType) {
		inspectionTypeInText = inspectionType;
	}
	
	public String getDraftFileName() {
		return DraftFileName;
	}

	public void setDraftFileName(String name) {
		DraftFileName = name;
	}

	public String getVisitFolderName() {
		return visitFolderName;
	}

	public void setVisitFolderName(String vNo) {
		visitFolderName = vNo;
	}
	
	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String vId) {
		visitId = vId;
	}
	
	// --------3 States of the App----------------------------------------
	boolean isSMS = false;
	boolean isDRAFT = false;
	boolean isSEARCH = false;

	public boolean getisSMS() {
		return isSMS;
	}

	public void setisSMS(boolean sms) {
		isSMS = sms;
	}

	public boolean getisDRAFT() {
		return isDRAFT;
	}

	public void setisDRAFT(boolean draft) {
		isDRAFT = draft;
	}

	public boolean getisSEARCH() {
		return isSEARCH;
	}

	public void setisSEARCH(boolean search) {
		isSEARCH = search;
	}

	// -------------------------------------------------------------------

	//--------Image Containers------------
	
	public boolean[] getselectedTechnicalOfficerCommentsImages() {
		return selectedTechnicalOfficerCommentsImages;
	}

	public void setselectedTechnicalOfficerCommentsImages(boolean[] arr) {
		selectedTechnicalOfficerCommentsImages = arr;
	}
	
	public boolean[] getselectedEstimateAnyotherComments() {
		return estimateAnyotherComments;
	}

	public void setselectedEstimateAnyotherComments(boolean[] arr) {
		estimateAnyotherComments = arr;
	}
	
	public boolean[] getselectedInspectionPhotosSeenVisitsAnyOther() {
		return inspectionPhotosSeenVisitsAnyOther;
	}

	public void setselectedInspectionPhotosSeenVisitsAnyOther(boolean[] arr) {
		inspectionPhotosSeenVisitsAnyOther = arr;
	}

	// -------------------------------------------------------------------

	public void setImageCount(String img){
		ImageCount = img;
	}
	
	public String getImageCount(){
		return ImageCount;
	}
}
