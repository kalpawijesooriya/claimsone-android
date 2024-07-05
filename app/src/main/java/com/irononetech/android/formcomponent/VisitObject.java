package com.irononetech.android.formcomponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.irononetech.android.Application.Application;
import com.irononetech.android.template.DataObject;

public class VisitObject extends DataObject {

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
	Random rn = new Random();
	int randNo = 0;
	
	String ImageCount = "0";
	
	boolean selectedTechnicalOfficerCommentsImages[] = new boolean[400];
	boolean estimateAnyotherComments[] = new boolean[400];
	boolean inspectionPhotosSeenVisitsAnyOther[] = new boolean[400];
	
	VisitObjectDraft draftObj;
	
	public VisitObject() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");// "MMMMM d, yyyy h:mm a");
		String inspectionDate = formatter.format(Calendar.getInstance()
				.getTime());
		
		randNo = rn.nextInt(10000);
		
		// Create a draft object for the data saving
		draftObj = Application.createVisitObjectDraftInstance();

		setInspectionDate(inspectionDate);
		draftObj.setInspectionDate(inspectionDate);
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jNo) {
		if(draftObj.getVisitFolderName().isEmpty())
		setVisitFolderName(jNo+"_"+randNo);
		jobNo = jNo;
		draftObj.setJobNo(jNo);
	}

	public String getVisitFolderName() {
		return visitFolderName;
	}

	public void setVisitFolderName(String vNo) {
		visitFolderName = vNo;
		draftObj.setVisitFolderName(vNo);
	}
	
	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String vId) {
		visitId = vId;
		draftObj.setVisitId(vId);
	}
	
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vNo) {
		vehicleNo = vNo;
		draftObj.setVehicleNo(vNo);
	}

	public String getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(String iDate) {
		inspectionDate = iDate;
		draftObj.setInspectionDate(iDate);
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String eNo) {
		engineNo = eNo;
		draftObj.setEngineNo(eNo);
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String cNo) {
		chassisNo = cNo;
		draftObj.setChassisNo(cNo);
	}

	public String getPreviousComments() {
		return previousComments;
	}

	public void setPreviousComments(String pComments) {
		previousComments = pComments;
		draftObj.setPreviousComments(pComments);
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String com) {
		comments = com;
		draftObj.setComments(com);
	}

	public int getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(int iType) {
		inspectionType = iType;
		draftObj.setInspectionType(iType);
	}

	public int getInspectionTypeArrIndex() {
		return inspectionTypeArrindex;
	}

	public void setInspectionTypeArrIndex(int pos) {
		inspectionTypeArrindex = pos;
		draftObj.setInspectionTypeArrIndex(pos);
	}

	public String getInspectionTypeInText() {
		return inspectionTypeInText;
	}

	public void setInspectionTypeInText(String inspectionType) {
		inspectionTypeInText = inspectionType;
		draftObj.setInspectionTypeInText(inspectionType);
	}
	
	public String getImageCount() {
		return ImageCount;
	}

	public void setImageCount(String img) {
		ImageCount = img;
		draftObj.setImageCount(img);
	}
	
	// -----Setting the drafts file name----------------------------------
	// contain .ssmv extension

	public String getDraftFileName() {
		return DraftFileName;
	}

	public void setDraftFileName(String name) {
		DraftFileName = name;
		draftObj.setDraftFileName(name);
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
		draftObj.setisSMS(sms);
	}

	public boolean getisDRAFT() {
		return isDRAFT;
	}

	public void setisDRAFT(boolean draft) {
		isDRAFT = draft;
		draftObj.setisSMS(draft);
	}

	public boolean getisSEARCH() {
		return isSEARCH;
	}

	public void setisSEARCH(boolean search) {
		isSEARCH = search;
		draftObj.setisSMS(search);
	}

	// -------------------------------------------------------------------
	
	//--------Image Containers------------
	
	public boolean[] getselectedTechnicalOfficerCommentsImages() {
		return selectedTechnicalOfficerCommentsImages;
	}

	public void setselectedTechnicalOfficerCommentsImages(boolean[] arr) {
		selectedTechnicalOfficerCommentsImages = arr;
		draftObj.setselectedTechnicalOfficerCommentsImages(arr);
	}
	
	public boolean[] getselectedEstimateAnyotherComments() {
		return estimateAnyotherComments;
	}

	public void setselectedEstimateAnyotherComments(boolean[] arr) {
		estimateAnyotherComments = arr;
		draftObj.setselectedEstimateAnyotherComments(arr);
	}
	
	public boolean[] getselectedInspectionPhotosSeenVisitsAnyOther() {
		return inspectionPhotosSeenVisitsAnyOther;
	}

	public void setselectedInspectionPhotosSeenVisitsAnyOther(boolean[] arr) {
		inspectionPhotosSeenVisitsAnyOther = arr;
		draftObj.setselectedInspectionPhotosSeenVisitsAnyOther(arr);
	}

	// -------------------------------------------------------------------

	

	//---------------------Image Categories--------------------------
	//---------------------No need to save on to a draft file--------
	ArrayList<String> fileListEstimateAnyotherComments;
	ArrayList<String> fileListTechnicalOfficerComments;
	ArrayList<String> fileListInspectionPhotosSeenVisitsAnyOther;
	
	public ArrayList<String> getEstimateAnyotherCommentsImageList() {
		return fileListEstimateAnyotherComments;
	}
		
	public void setEstimateAnyotherCommentsImageList(ArrayList<String> fileList) {
		fileListEstimateAnyotherComments = fileList;
	}
		
	public ArrayList<String> getTechnicalOfficerCommentsImageList() {
		return fileListTechnicalOfficerComments;
	}
		
	public void setTechnicalOfficerCommentsImageList(ArrayList<String> fileList) {
		fileListTechnicalOfficerComments = fileList;
	}
		
	public ArrayList<String> getInspectionPhotosSeenVisitsAnyOtherImageList() {
		return fileListInspectionPhotosSeenVisitsAnyOther;
	}
		
	public void setInspectionPhotosSeenVisitsAnyOtherImageList(ArrayList<String> fileList) {
		fileListInspectionPhotosSeenVisitsAnyOther = fileList;
	}
	//---------------------------------------------------------------

	String dataXML;

	public String getDataXML() {
		return dataXML;
	}

	public void setDataXML(String dataXML) {
		this.dataXML = dataXML;
	}

	//getIsExit and setIsExit not needed inside Draft Object
	boolean isExit = false;
	
	public boolean getIsExit() {
		return isExit;
	}
	
	public void setIsExit(boolean ie) {
		isExit = ie;
	}

	int estimateAnyotherCommentsImageListCount = 0;
	int technicalOfficerCommentsImageListCount = 0;
	int inspectionPhotosSeenVisitsAnyOtherImageListCount = 0;
	
	public int getEstimateAnyotherCommentsImageListCount() {
		return estimateAnyotherCommentsImageListCount;		
	}
	public void setEstimateAnyotherCommentsImageListCount(int size) {
		estimateAnyotherCommentsImageListCount = size;
	}
	
	public int getTechnicalOfficerCommentsImageListCount() {
		return technicalOfficerCommentsImageListCount;
	}
	public void setTechnicalOfficerCommentsImageListCount(int size) {
		technicalOfficerCommentsImageListCount = size;
	}

	public int getInspectionPhotosSeenVisitsAnyOtherImageListCount() {
		return inspectionPhotosSeenVisitsAnyOtherImageListCount;
	}
	public void setInspectionPhotosSeenVisitsAnyOtherImageListCount(int size) {
		inspectionPhotosSeenVisitsAnyOtherImageListCount = size;
	}
}