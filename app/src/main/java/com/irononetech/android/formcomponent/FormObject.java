package com.irononetech.android.formcomponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.view.PossibleDRMapping;
import com.irononetech.android.template.DataObject;

public class FormObject extends DataObject {

	boolean editable = true;

	// Accident Details
	String JobStatus = "PENDING";
	String JobNo = "";
	String VisitId = "";
	String TimeReported = "";
	String OrgTimeReported = "";
	String Periodampm = "0";
	String ContactNo = "";
	String NameofCaller = "";
	String VehicleNo = "";
	String VehicleTypeandColor = "";
	String DateandTimeofAccident = "";
	String LocationofAccident = "";
	String TimeVisited = "";
	String selectVehicleClassType;
	boolean selectVehicleClassForOld[];
	boolean selectVehicleClassForNew[];
	boolean selectPossibleDR[];
	boolean selectTirecondition[];

	int vehicleType = 1;
	boolean isVehicleShow;
	boolean preSelected = false;

	boolean booleanlistBus[][][][];
	boolean booleanlistLorry[][][][];
	boolean booleanlistVan[][][][];
	boolean booleanlistCar[][][][];
	boolean booleanlistThreeWheel[][][][];
	boolean booleanlistMotorcycle[][][][];
	boolean booleanlistTractor4WD[][][][];
	boolean booleanlistHandTractor[][][][];
	
	// boolean ispreVehicleShow;
	boolean booleanprelistBus[][][][];
	boolean booleanprelistLorry[][][][];
	boolean booleanprelistVan[][][][];
	boolean booleanprelistCar[][][][];
	boolean booleanprelistThreeWheel[][][][];
	boolean booleanprelistMotorcycle[][][][];
	boolean booleanprelistTractor4WD[][][][];
	boolean booleanprelistHandTractor[][][][];

	static FormObjectDraft draftObj;

	public FormObject() {
		// Create a draft object for the data saving
		draftObj = Application.createFormObjectDraftInstance();


		//get the current time and date of this formObject initiated
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");// "MMMMM d, yyyy h:mm a");
		String StartDateTime = formatter.format(Calendar.getInstance().getTime());

		setTimeReported(StartDateTime);
		draftObj.setTimeReported(StartDateTime);

		setDateandTimeofAccident(StartDateTime);
		draftObj.setDateandTimeofAccident(StartDateTime);

		setOrgTimeReported(StartDateTime);
		draftObj.setOrgTimeReported(StartDateTime);

		setTimeVisited(StartDateTime);
		draftObj.setTimeVisited(StartDateTime);

		/*
		 * setPolicyCoverNotePeriodFrom("");
		 * draftObj.setPolicyCoverNotePeriodFrom("");
		 * 
		 * setPolicyCoverNotePeriodTo("");
		 * draftObj.setPolicyCoverNotePeriodTo("");
		 * 
		 * setExpiryDateOfLicence(""); draftObj.setExpiryDateOfLicence("");
		 */

		selectTirecondition = new boolean[Application.getTyreConditionArrSize()];
		for (int i = 0; i < selectTirecondition.length; i++) {
			if (i % 4 == 0) {	//4 == no of items in each sub category
				selectTirecondition[i + 3] = true;  //3 == above no minus 1
				// selectTirecondition[i]=false;
			}
			// else
			// selectTirecondition[i]= false;
		}
		
		selectPossibleDR = new boolean[PossibleDRMapping.values().length];
		for (int i = 0; i < selectPossibleDR.length; i++) {
			selectPossibleDR[i] = false;
		}

		selectVehicleClassForOld = new boolean[12];
		for (int i = 0; i < selectVehicleClassForOld.length; i++) {
			selectVehicleClassForOld[i] = false;
		}

		selectVehicleClassForNew = new boolean[13];
		for (int i = 0; i < selectVehicleClassForNew.length; i++) {
			selectVehicleClassForNew[i] = false;
		}

		SelectDL_NewOld = "1"; // 1 is new
		vehicleType = 0;
		isVehicleShow = true;

		int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();
		int p4 = Application.get4DArrSizeSec4();

		booleanlistBus = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistBus[i][j][k][0]= false;
		 * booleanlistBus[i][j][k][1]= false; } } }
		 */

		booleanlistCar = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistCar[i][j][k][0]= false;
		 * booleanlistCar[i][j][k][1]= false; } } }
		 */

		booleanlistThreeWheel = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistThreeWheel[i][j][k][0]= false;
		 * booleanlistThreeWheel[i][j][k][1]= false; } } }
		 */

		booleanlistMotorcycle = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistMotorcycle[i][j][k][0]= false;
		 * booleanlistMotorcycle[i][j][k][1]= false; } } }
		 */

		booleanlistLorry = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistLorry[i][j][k][0]= false;
		 * booleanlistLorry[i][j][k][1]= false; } } }
		 */

		booleanlistVan = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanlistVan[i][j][k][0]= false;
		 * booleanlistVan[i][j][k][1]= false; } } }
		 */

		booleanlistTractor4WD = new boolean[p1][p2][p3][p4];
		booleanlistHandTractor = new boolean[p1][p2][p3][p4];
		
		// ------------------PRE----------------------------------

		booleanprelistBus = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistBus[i][j][k][0]= false;
		 * booleanprelistBus[i][j][k][1]= false; } } }
		 */

		booleanprelistCar = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistCar[i][j][k][0]= false;
		 * booleanprelistCar[i][j][k][1]= false; } } }
		 */

		booleanprelistThreeWheel = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistThreeWheel[i][j][k][0]= false;
		 * booleanprelistThreeWheel[i][j][k][1]= false; } } }
		 */

		booleanprelistMotorcycle = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistMotorcycle[i][j][k][0]= false;
		 * booleanprelistMotorcycle[i][j][k][1]= false; } } }
		 */

		booleanprelistLorry = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistLorry[i][j][k][0]= false;
		 * booleanprelistLorry[i][j][k][1]= false; } } }
		 */

		booleanprelistVan = new boolean[p1][p2][p3][p4];
		/*
		 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
		 * k=0;k<p3;k++){ booleanprelistVan[i][j][k][0]= false;
		 * booleanprelistVan[i][j][k][1]= false; } } }
		 */
		
		booleanprelistTractor4WD = new boolean[p1][p2][p3][p4];
		booleanprelistHandTractor = new boolean[p1][p2][p3][p4];
	}


	// Image Selection Save
	boolean selectedDLStatementImages[] = new boolean[400];
	boolean selectedTechnicalOfficerCommentsImages[] = new boolean[400];
	boolean selectedClaimFormImageImages[] = new boolean[400];
	boolean selectedARIImages[] = new boolean[400];
	boolean selectedDRImages[] = new boolean[400];
	boolean selectedSeenVisitImages[] = new boolean[400];
	boolean selectedSpecialReport1Images[] = new boolean[400];
	boolean selectedSpecialReport2Images[] = new boolean[400];
	boolean selectedSpecialReport3Images[] = new boolean[400];
	boolean selectedSupplementary1Images[] = new boolean[400];
	boolean selectedSupplementary2Images[] = new boolean[400];
	boolean selectedSupplementary3Images[] = new boolean[400];
	boolean selectedSupplementary4Images[] = new boolean[400];
	boolean selectedAcknowledgmentImages[] = new boolean[400];
	boolean selectedSalvageReportImages[] = new boolean[400];

	boolean selectedAccidentImages[] = new boolean[400];
	
	public boolean[] getselectedDLStatementImages() {
		return selectedDLStatementImages;
	}

	public void setselectedDLStatementImages(boolean[] arr) {
		selectedDLStatementImages = arr;
		draftObj.setselectedDLStatementImages(arr);
	}

	public boolean[] getselectedTechnicalOfficerCommentsImages() {
		return selectedTechnicalOfficerCommentsImages;
	}

	public void setselectedTechnicalOfficerCommentsImages(boolean[] arr) {
		selectedTechnicalOfficerCommentsImages = arr;
		draftObj.setselectedTechnicalOfficerCommentsImages(arr);
	}

	public boolean[] getselectedClaimFormImageImages() {
		return selectedClaimFormImageImages;
	}

	public void setselectedClaimFormImageImages(boolean[] arr) {
		selectedClaimFormImageImages = arr;
		draftObj.setselectedClaimFormImageImages(arr);
	}

	public boolean[] getselectedARIImages() {
		return selectedARIImages;
	}

	public void setselectedARIImages(boolean[] arr) {
		selectedARIImages = arr;
		draftObj.setselectedARIImages(arr);
	}

	public boolean[] getselectedDRImages() {
		return selectedDRImages;
	}

	public void setselectedDRImages(boolean[] arr) {
		selectedDRImages = arr;
		draftObj.setselectedDRImages(arr);
	}

	public boolean[] getselectedSeenVisitImages() {
		return selectedSeenVisitImages;
	}

	public void setselectedSeenVisitImages(boolean[] arr) {
		selectedSeenVisitImages = arr;
		draftObj.setselectedSeenVisitImages(arr);
	}

	public boolean[] getselectedSpecialReport1Images() {
		return selectedSpecialReport1Images;
	}

	public void setselectedSpecialReport1Images(boolean[] arr) {
		selectedSpecialReport1Images = arr;
		draftObj.setselectedSpecialReport1Images(arr);
	}

	public boolean[] getselectedSpecialReport2Images() {
		return selectedSpecialReport2Images;
	}

	public void setselectedSpecialReport2Images(boolean[] arr) {
		selectedSpecialReport2Images = arr;
		draftObj.setselectedSpecialReport2Images(arr);
	}

	public boolean[] getselectedSpecialReport3Images() {
		return selectedSpecialReport3Images;
	}

	public void setselectedSpecialReport3Images(boolean[] arr) {
		selectedSpecialReport3Images = arr;
		draftObj.setselectedSpecialReport3Images(arr);
	}

	public boolean[] getselectedSupplementary1Images() {
		return selectedSupplementary1Images;
	}

	public void setselectedSupplementary1Images(boolean[] arr) {
		selectedSupplementary1Images = arr;
		draftObj.setselectedSupplementary1Images(arr);
	}

	public boolean[] getselectedSupplementary2Images() {
		return selectedSupplementary2Images;
	}

	public void setselectedSupplementary2Images(boolean[] arr) {
		selectedSupplementary2Images = arr;
		draftObj.setselectedSupplementary2Images(arr);
	}

	public boolean[] getselectedSupplementary3Images() {
		return selectedSupplementary3Images;
	}

	public void setselectedSupplementary3Images(boolean[] arr) {
		selectedSupplementary3Images = arr;
		draftObj.setselectedSupplementary3Images(arr);
	}

	public boolean[] getselectedSupplementary4Images() {
		return selectedSupplementary4Images;
	}

	public void setselectedSupplementary4Images(boolean[] arr) {
		selectedSupplementary4Images = arr;
		draftObj.setselectedSupplementary4Images(arr);
	}

	public boolean[] getselectedAcknowledgmentImages() {
		return selectedAcknowledgmentImages;
	}

	public void setselectedAcknowledgmentImages(boolean[] arr) {
		selectedAcknowledgmentImages = arr;
		draftObj.setselectedAcknowledgmentImages(arr);
	}

	public boolean[] getselectedSalvageReportImages() {
		return selectedSalvageReportImages;
	}

	public void setselectedSalvageReportImages(boolean[] arr) {
		selectedSalvageReportImages = arr;
		draftObj.setselectedSalvageReportImages(arr);
	}

	public boolean[] getselectedAccidentImages() {
		return selectedAccidentImages;
	}

	public void setselectedAccidentImages(boolean[] arr) {
		selectedAccidentImages = arr;
		draftObj.setselectedAccidentImages(arr);
	}
	
	
	private String convertBooltoString(boolean parm[][][][]) {
		String data = "";
		int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();
		if (parm != null) {
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						if (parm[i][j][k][0]) {
							data += "#" + i + "," + j + "," + k + ",0";
						}
					}
				}
			}
		}

		return data;
	}

	private String convertPreBooltoString(boolean parm[][][][]) {
		String data = "";
		int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();
		if (parm != null) {
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						if (parm[i][j][k][0]) {
							// data += "<item>"+i+","+j+","+k+",0</item>";
							data += "#" + i + "," + j + "," + k + ",0";
						}
					}
				}
			}
		}
		return data;
	}

	public boolean[][][][] getBooleanlistBus() {
		return booleanlistBus;
	}

	public void setBooleanlistBus(boolean[][][][] booleanlistBus) {
		this.booleanlistBus = booleanlistBus;
		draftObj.setBooleanlistBus(convertBooltoString(booleanlistBus));
	}

	public boolean[][][][] getBooleanlistLorry() {
		return booleanlistLorry;
	}

	public void setBooleanlistLorry(boolean[][][][] booleanlistLorry) {
		this.booleanlistLorry = booleanlistLorry;
		draftObj.setBooleanlistLorry(convertBooltoString(booleanlistLorry));
	}

	public boolean[][][][] getBooleanlistVan() {
		return booleanlistVan;
	}

	public void setBooleanlistVan(boolean[][][][] booleanlistVan) {
		this.booleanlistVan = booleanlistVan;
		draftObj.setBooleanlistVan(convertBooltoString(booleanlistVan));
	}

	public boolean[][][][] getBooleanlistCar() {
		return booleanlistCar;
	}

	public void setBooleanlistCar(boolean[][][][] booleanlistCar) {
		this.booleanlistCar = booleanlistCar;
		draftObj.setBooleanlistCar(convertBooltoString(booleanlistCar));
	}

	public boolean[][][][] getBooleanlistThreeWheel() {
		return booleanlistThreeWheel;
	}

	public void setBooleanlistThreeWheel(boolean[][][][] booleanlistThreeWheel) {
		this.booleanlistThreeWheel = booleanlistThreeWheel;
		draftObj.setBooleanlistThreeWheel(convertBooltoString(booleanlistThreeWheel));
	}

	public boolean[][][][] getBooleanlistMotorcycle() {
		return booleanlistMotorcycle;
	}

	public void setBooleanlistMotorcycle(boolean[][][][] booleanlistMotorcycle) {
		this.booleanlistMotorcycle = booleanlistMotorcycle;
		draftObj.setBooleanlistMotorcycle(convertBooltoString(booleanlistMotorcycle));
	}

	public boolean[][][][] getBooleanlistTractor4WD() {
		return booleanlistTractor4WD;
	}

	public void setBooleanlistTractor4WD(boolean[][][][] booleanlistTractor4WD) {
		this.booleanlistTractor4WD = booleanlistTractor4WD;
		draftObj.setBooleanlistTractor4WD(convertBooltoString(booleanlistTractor4WD));
	}
	
	public boolean[][][][] getBooleanlistHandTractor() {
		return booleanlistHandTractor;
	}

	public void setBooleanlistHandTractor(boolean[][][][] booleanlistHandTractor) {
		this.booleanlistHandTractor = booleanlistHandTractor;
		draftObj.setBooleanlistHandTractor(convertBooltoString(booleanlistHandTractor));
	}
	
	// ----------------------PRE------------------------------------
	public boolean[][][][] getBooleanprelistBus() {
		return booleanprelistBus;
	}

	public void setBooleanprelistBus(boolean[][][][] booleanprelistBus) {
		this.booleanprelistBus = booleanprelistBus;
		draftObj.setBooleanprelistBus(convertPreBooltoString(booleanprelistBus));
	}

	public boolean[][][][] getBooleanprelistLorry() {
		return booleanprelistLorry;
	}

	public void setBooleanprelistLorry(boolean[][][][] booleanprelistLorry) {
		this.booleanprelistLorry = booleanprelistLorry;
		draftObj.setBooleanprelistLorry(convertPreBooltoString(booleanprelistLorry));
	}

	public boolean[][][][] getBooleanprelistVan() {
		return booleanprelistVan;
	}

	public void setBooleanprelistVan(boolean[][][][] booleanprelistVan) {
		this.booleanprelistVan = booleanprelistVan;
		draftObj.setBooleanprelistVan(convertPreBooltoString(booleanprelistVan));
	}

	public boolean[][][][] getBooleanprelistCar() {
		return booleanprelistCar;
	}

	public void setBooleanprelistCar(boolean[][][][] booleanprelistCar) {
		this.booleanprelistCar = booleanprelistCar;
		draftObj.setBooleanprelistCar(convertPreBooltoString(booleanprelistCar));
	}

	public boolean[][][][] getBooleanprelistThreeWheel() {
		return booleanprelistThreeWheel;
	}

	public void setBooleanprelistThreeWheel(
			boolean[][][][] booleanprelistThreeWheel) {
		this.booleanprelistThreeWheel = booleanprelistThreeWheel;
		draftObj.setBooleanprelistThreeWheel(convertBooltoString(booleanprelistThreeWheel));
	}

	public boolean[][][][] getBooleanprelistMotorcycle() {
		return booleanprelistMotorcycle;
	}

	public void setBooleanprelistMotorcycle(boolean[][][][] booleanprelistMotorcycle) {
		this.booleanprelistMotorcycle = booleanprelistMotorcycle;
		draftObj.setBooleanprelistMotorcycle(convertBooltoString(booleanprelistMotorcycle));
	}

	public boolean[][][][] getBooleanprelistTractor4WD() {
		return booleanprelistTractor4WD;
	}

	public void setBooleanprelistTractor4WD(boolean[][][][] booleanprelistTractor4WD) {
		this.booleanprelistTractor4WD = booleanprelistTractor4WD;
		draftObj.setBooleanprelistTractor4WD(convertBooltoString(booleanprelistTractor4WD));
	}
	
	public boolean[][][][] getBooleanprelistHandTractor() {
		return booleanprelistHandTractor;
	}

	public void setBooleanprelistHandTractor(boolean[][][][] booleanprelistHandTractor) {
		this.booleanprelistHandTractor = booleanprelistHandTractor;
		draftObj.setBooleanprelistHandTractor(convertBooltoString(booleanprelistHandTractor));
	}
	
	// Suren
	// there is an old way of tracking whether
	// its a search or not. this is expired, and getisSearch
	// getisDraft and getisSMS are used instead
	// but still old code is in use. So do not remove this.
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		draftObj.setEditable(editable);
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

	/*
	 * boolean pointOfImpactAdded = false; public boolean
	 * isPointOfImpactAdded(){ return pointOfImpactAdded; } public void
	 * setPointOfImpactAdded(boolean pointOfImpactAdded){
	 * this.pointOfImpactAdded = pointOfImpactAdded;
	 * draftObj.setPointOfImpactAdded(pointOfImpactAdded); }
	 */

	String activityNotificationDialogStr = "";

	public String getActivityNotificationDialogStr() {
		return activityNotificationDialogStr;
	}

	public void setActivityNotificationDialogStr(
			String activityNotificationDialogStr) {
		this.activityNotificationDialogStr = activityNotificationDialogStr;
		draftObj.setActivityNotificationDialogStr(activityNotificationDialogStr);
	}

	String dataXML;

	public String getDataXML() {
		return dataXML;
	}

	public void setDataXML(String dataXML) {
		this.dataXML = dataXML;
		draftObj.setDataXML(dataXML);
	}

	public String getTimePeriods() {
		return Periodampm;
	}

	public String getRelationshipTimePeriod() {
		return Periodampm;
	}

	public void setRelationshipTimePeriod(String Period) {
		if (Period == null) {
			Period = "1";
		}
		Periodampm = Period;
		draftObj.setRelationshipTimePeriod(Period);
	}

	public String getDateandTimeofAccident() {
		return DateandTimeofAccident;
	}

	public void setDateandTimeofAccident(String dateandTimeofAccident) {
		DateandTimeofAccident = dateandTimeofAccident;
		draftObj.setDateandTimeofAccident(dateandTimeofAccident);
	}

	public String getLocationofAccident() {
		return LocationofAccident;
	}

	public void setLocationofAccident(String locationofAccident) {
		LocationofAccident = locationofAccident;
		draftObj.setLocationofAccident(locationofAccident);
	}

	public String getVehicleTypeandColor() {
		return VehicleTypeandColor;
	}

	public void setVehicleTypeandColor(String vehicleTypeandColor) {
		VehicleTypeandColor = vehicleTypeandColor;
		draftObj.setVehicleTypeandColor(vehicleTypeandColor);
	}

	public String getContactNo() {
		return ContactNo;
	}

	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
		draftObj.setContactNo(contactNo);
	}

	public String getNameofCaller() {
		return NameofCaller;
	}

	public void setNameofCaller(String nameofCaller) {
		NameofCaller = nameofCaller;
		draftObj.setNameofCaller(nameofCaller);
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
		draftObj.setJobStatus(jobStatus);
	}

	public String getJobNo() {
		return JobNo;
	}

	public void setJobNo(String jobNo) {
		JobNo = jobNo;
		draftObj.setJobNo(jobNo);
	}

	//Use only for the search. No need to save it in the draft
	public String getVisitId() {
		return VisitId;
	}
	
	public void setVisitId(String id) {
		VisitId = id;
	}
	
	public String getTimeReported() {
		return TimeReported;
	}

	public void setTimeReported(String timeReported) {
		TimeReported = timeReported;
		draftObj.setTimeReported(timeReported);
	}

	// used to track to original time reported sent by call center
	// if not available or manual entry in SA from this will be empty string
	public String getOrgTimeReported() {
		return OrgTimeReported;
	}

	public void setOrgTimeReported(String otimeReported) {
		OrgTimeReported = otimeReported;
		draftObj.setOrgTimeReported(otimeReported);
	}

	public String getVehicleNo() {
		return VehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		VehicleNo = vehicleNo;
		draftObj.setVehicleNo(vehicleNo);
	}

	// Insurance Details
	String NameofInsured = "";
	String ContactNooftheInsured = "";
	String policyCoverNoteNo = "";
	String policyCoverNotePeriodFrom = "";
	String policyCoverNotePeriodTo = "";
	String policyCoverNoteSerialNo = "";
	String coverNoteIssuedBy = "";
	String reasonsforIssuingCoverNote = "";

	public String getPolicyCoverNoteNo() {
		return policyCoverNoteNo;
	}

	public void setPolicyCoverNoteNo(String policyCoverNoteNo) {
		this.policyCoverNoteNo = policyCoverNoteNo;
		draftObj.setPolicyCoverNoteNo(policyCoverNoteNo);
	}

	public String getPolicyCoverNotePeriodFrom() {
		return policyCoverNotePeriodFrom;
	}

	public void setPolicyCoverNotePeriodFrom(String policyCoverNotePeriodFrom) {
		this.policyCoverNotePeriodFrom = policyCoverNotePeriodFrom;
		draftObj.setPolicyCoverNotePeriodFrom(policyCoverNotePeriodFrom);
	}

	public String getPolicyCoverNotePeriodTo() {
		return policyCoverNotePeriodTo;
	}

	public void setPolicyCoverNotePeriodTo(String policyCoverNotePeriodTo) {
		this.policyCoverNotePeriodTo = policyCoverNotePeriodTo;
		draftObj.setPolicyCoverNotePeriodTo(policyCoverNotePeriodTo);
	}

	public String getPolicyCoverNoteSerialNo() {
		return policyCoverNoteSerialNo;
	}

	public void setPolicyCoverNoteSerialNo(String policyCoverNoteSerialNo) {
		this.policyCoverNoteSerialNo = policyCoverNoteSerialNo;
		draftObj.setPolicyCoverNoteSerialNo(policyCoverNoteSerialNo);
	}

	public String getCoverNoteIssuedBy() {
		return coverNoteIssuedBy;
	}

	public void setCoverNoteIssuedBy(String coverNoteIssuedBy) {
		this.coverNoteIssuedBy = coverNoteIssuedBy;
		draftObj.setCoverNoteIssuedBy(coverNoteIssuedBy);
	}

	public String getReasonsforIssuingCoverNote() {
		return reasonsforIssuingCoverNote;
	}

	public void setReasonsforIssuingCoverNote(String reasonsforIssuingCoverNote) {
		this.reasonsforIssuingCoverNote = reasonsforIssuingCoverNote;
		draftObj.setReasonsforIssuingCoverNote(reasonsforIssuingCoverNote);
	}

	public String getNameofInsured() {
		return NameofInsured;
	}

	public void setNameofInsured(String nameofInsured) {
		NameofInsured = nameofInsured;
		draftObj.setNameofInsured(nameofInsured);
	}

	public String getContactNooftheInsured() {
		return ContactNooftheInsured;
	}

	public void setContactNooftheInsured(String contactNooftheInsured) {
		ContactNooftheInsured = contactNooftheInsured;
		draftObj.setContactNooftheInsured(contactNooftheInsured);
	}

	ArrayList<String> AccidentImageList;

	public ArrayList<String> getAccidentImageList() {
		return AccidentImageList;
	}

	public void setAccidentImageList(ArrayList<String> accidentImageList) {
		AccidentImageList = accidentImageList;
		draftObj.setAccidentImageList(accidentImageList);
	}

	ArrayList<String> PointOfImpactList;

	public ArrayList<String> getPointOfImpactList() {
		return PointOfImpactList;
	}

	public void setPointOfImpactList(ArrayList<String> pointOfImpactList) {
		PointOfImpactList = pointOfImpactList;
		draftObj.setPointOfImpactList(pointOfImpactList);
	}

	// DRIVER DETAILS
	String DriversName = "";
	String RelationshipBetweenDriverAndInsured = "0";
	String TypeOfNICNo = "0"; // Thisaru Guruge | 29/01/2016 | This is to set the NIC No. type of the drivers NIC.
	// Default value of the "TypeOfNICNo" is 0 because the default selection should be Old NIC type.
	String NICNoOfDriver = "";
	String DrivingLicenceNo = "";
	String ExpiryDateOfLicence = "";
	String TypeOfDrivingLicence = "0";
	String SelectDL_NewOld = "0";
	String Competence = "1";
	String VehicleClass = "";

	public String getDriversName() {
		return DriversName;
	}

	public void setDriversName(String driversName) {
		DriversName = driversName;
		draftObj.setDriversName(driversName);
	}

	public String getRelationshipBetweenDriverAndInsured() {
		return RelationshipBetweenDriverAndInsured;
	}

	public void setRelationshipBetweenDriverAndInsured(
			String relationshipBetweenDriverAndInsured) {
		if (relationshipBetweenDriverAndInsured == null) {
			relationshipBetweenDriverAndInsured = "1";
		}
		RelationshipBetweenDriverAndInsured = relationshipBetweenDriverAndInsured;
		draftObj.setRelationshipBetweenDriverAndInsured(relationshipBetweenDriverAndInsured);
	}
	
	/*
	 * Created By: Thisaru Guruge
	 * Date: 29 / 01 / 2016
	 * To add the new NIC types, have to add getter and setter to the FormObject
	 */
	
	public String getTypeOfNICNo() {
		return TypeOfNICNo;
	}
	
	public void setTypeOfNICNo (String typeOfNICNo) {
		TypeOfNICNo = typeOfNICNo; // Thisaru Guruge | 02/02/2016 | For adding NIC Type
		draftObj.setTypeOfNICNo(typeOfNICNo);
	}

	public String getNICNoOfDriver() {
		return NICNoOfDriver;
	}

	public void setNICNoOfDriver(String nICNoOfDriver) {
		NICNoOfDriver = nICNoOfDriver;
		draftObj.setNICNoOfDriver(nICNoOfDriver);
	}

	public String getDrivingLicenceNo() {
		return DrivingLicenceNo;
	}

	public void setDrivingLicenceNo(String drivingLicenceNo) {
		DrivingLicenceNo = drivingLicenceNo;
		draftObj.setDrivingLicenceNo(drivingLicenceNo);
	}

	public String getExpiryDateOfLicence() {
		return ExpiryDateOfLicence;
	}

	public void setExpiryDateOfLicence(String expiryDateOfLicence) {
		ExpiryDateOfLicence = expiryDateOfLicence;
		draftObj.setExpiryDateOfLicence(expiryDateOfLicence);
	}

	public String getTypeOfDrivingLicence() {
		return TypeOfDrivingLicence;
	}

	public void setTypeOfDrivingLicence(String typeOfDrivingLicence) {
		TypeOfDrivingLicence = typeOfDrivingLicence;
		draftObj.setTypeOfDrivingLicence(typeOfDrivingLicence);
	}

	public String getSelectDL_NewOld() {
		return SelectDL_NewOld;
	}

	public void setSelectDL_NewOld(String selectDLNewOld) {
		SelectDL_NewOld = selectDLNewOld;
		draftObj.setSelectDL_NewOld(selectDLNewOld);
	}

	public String getCompetence() {
		return Competence;
	}

	public void setCompetence(String competence) {
		Competence = competence;
		draftObj.setCompetence(competence);
	}

	public String getVehicleClass() {
		return VehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		VehicleClass = vehicleClass;
		draftObj.setVehicleClass(vehicleClass);
	}

	// VEHICLE DETAILS
	String ChasisNo = "";
	String EngineNo = "";
	String MeterReading = "";
	String DamagedItems = "";
	String PossibleDR = "";
	String AreTheyContributory = "0";
	String IsFurtherReviewNeeded = "0";



	public boolean[] getselectTirecondition() {
		return this.selectTirecondition;
	}

	public void setselectTirecondition(boolean[] selectTirecondition) {
		this.selectTirecondition = selectTirecondition;
		draftObj.setselectTirecondition(selectTirecondition);
	}

	public String getChasisNo() {
		return ChasisNo;
	}

	public void setChasisNo(String chasisNo) {
		ChasisNo = chasisNo;
		draftObj.setChasisNo(chasisNo);
	}

	public String getEngineNo() {
		return EngineNo;
	}

	public void setEngineNo(String engineNo) {
		EngineNo = engineNo;
		draftObj.setEngineNo(engineNo);
	}

	public String getMeterReading() {
		return MeterReading;
	}

	public void setMeterReading(String meterReading) {
		MeterReading = meterReading;
		draftObj.setMeterReading(meterReading);
	}

	public String getDamagedItems() {
		return DamagedItems;
	}

	public void setDamagedItems(String damagedItems) {
		DamagedItems = damagedItems;
		draftObj.setDamagedItems(damagedItems);
	}

	public String getPossibleDR() {
		return PossibleDR;
	}

	public void setPossibleDR(String possibleDR) {
		PossibleDR = possibleDR;
		draftObj.setPossibleDR(possibleDR);
	}

	public String getAreTheyContributory() {
		return AreTheyContributory;
	}

	public void setAreTheyContributory(String areTheyContributory) {
		AreTheyContributory = areTheyContributory;
		draftObj.setAreTheyContributory(areTheyContributory);
	}

	public String getIsFurtherReviewNeeded() {
		return IsFurtherReviewNeeded;
	}

	public void setIsFurtherReviewNeeded(String isFurtherReviewNeeded) {
		IsFurtherReviewNeeded = isFurtherReviewNeeded;
		draftObj.setIsFurtherReviewNeeded(isFurtherReviewNeeded);
	}

	// COMMENT FORM
	String OnSiteEstimation = "1";
	String EstCost = "";
	String ImageCount = "0";

	public String getOnSiteEstimation() {
		return OnSiteEstimation;
	}

	public void setOnSiteEstimation(String OnSiteEstimationVal) {
		OnSiteEstimation = OnSiteEstimationVal;
		draftObj.setOnSiteEstimation(OnSiteEstimationVal);
	}

	public String getAppCost() {
		return EstCost;
	}

	public void setAppCost(String cost) {
		EstCost = cost;
		draftObj.setAppCost(cost);
	}

	public String getImageCount() {
		return ImageCount;
	}

	public void setImageCount(String img) {
		ImageCount = img;
		draftObj.setImageCount(img);
	}

	/*
	 * public String getResubmitImageCount() { int count = 0;
	 * if(fileListDLStatement != null){ count = count +
	 * fileListDLStatement.size(); } if(fileListTechnicalOfficerComments !=
	 * null){ count = count + fileListTechnicalOfficerComments.size(); }
	 * if(ClaimFormImage != null){ count = count + ClaimFormImage.size(); }
	 * if(ARI != null){ count = count + ARI.size(); } if(DR != null){ count =
	 * count + DR.size(); } if(SeenVisit != null){ count = count +
	 * SeenVisit.size(); } if(SpecialReport1 != null){ count = count +
	 * SpecialReport1.size(); } if(SpecialReport2 != null){ count = count +
	 * SpecialReport2.size(); } if(SpecialReport3 != null){ count = count +
	 * SpecialReport3.size(); } if(Supplementary1 != null){ count =
	 * Supplementary1.size(); } if(Supplementary2 != null){ count = count +
	 * Supplementary2.size(); } if(Supplementary3 != null){ count = count +
	 * Supplementary3.size(); } if(Supplementary4 != null){ count = count +
	 * Supplementary4.size(); } if(Acknowledgment != null){ count = count +
	 * Acknowledgment.size(); } if(SalvageReport != null){ count = count +
	 * SalvageReport.size(); } LogFile.d("\n\ngetResubmitImageCount: ",
	 * count+""); return count+""; }
	 */

	// OTHER ITEMS
	String TypeOfGoodsCarried = "";
	String WeightOfGoodsCarried = "";
	String DamagesToTheGoods = "";
	String OverLoaded = "0";
	String OverWeightContributory = "0";
	String OtherVehiclesInvolved = "";
	String ThirdPartyDamagesProperty = "";
	String Injuries_InsuredAnd3rdParty = "";
	String PurposeOfJourney = "1";

	public String getTypeOfGoodsCarried() {
		return TypeOfGoodsCarried;
	}

	public void setTypeOfGoodsCarried(String typeOfGoodsCarried) {
		TypeOfGoodsCarried = typeOfGoodsCarried;
		draftObj.setTypeOfGoodsCarried(typeOfGoodsCarried);
	}

	public String getWeightOfGoodsCarried() {
		return WeightOfGoodsCarried;
	}

	public void setWeightOfGoodsCarried(String weightOfGoodsCarried) {
		WeightOfGoodsCarried = weightOfGoodsCarried;
		draftObj.setWeightOfGoodsCarried(weightOfGoodsCarried);
	}

	public String getDamagesToTheGoods() {
		return DamagesToTheGoods;
	}

	public void setDamagesToTheGoods(String damagesToTheGoods) {
		DamagesToTheGoods = damagesToTheGoods;
		draftObj.setDamagesToTheGoods(damagesToTheGoods);
	}

	public String getOverLoaded() {
		return OverLoaded;
	}

	public void setOverLoaded(String overLoaded) {
		OverLoaded = overLoaded;
		draftObj.setOverLoaded(overLoaded);
	}

	public String getOverWeightContributory() {
		return OverWeightContributory;
	}

	public void setOverWeightContributory(String overWeightContributory) {
		OverWeightContributory = overWeightContributory;
		draftObj.setOverWeightContributory(overWeightContributory);
	}

	public String getOtherVehiclesInvolved() {
		return OtherVehiclesInvolved;
	}

	public void setOtherVehiclesInvolved(String otherVehiclesInvolved) {
		OtherVehiclesInvolved = otherVehiclesInvolved;
		draftObj.setOtherVehiclesInvolved(otherVehiclesInvolved);
	}

	public String getThirdPartyDamagesProperty() {
		return ThirdPartyDamagesProperty;
	}

	public void setThirdPartyDamagesProperty(String thirdPartyDamagesProperty) {
		ThirdPartyDamagesProperty = thirdPartyDamagesProperty;
		draftObj.setThirdPartyDamagesProperty(thirdPartyDamagesProperty);
	}

	public String getInjuries_InsuredAnd3rdParty() {
		return Injuries_InsuredAnd3rdParty;
	}

	public void setInjuries_InsuredAnd3rdParty(String injuriesInsuredAnd3rdParty) {
		Injuries_InsuredAnd3rdParty = injuriesInsuredAnd3rdParty;
		draftObj.setInjuries_InsuredAnd3rdParty(injuriesInsuredAnd3rdParty);
	}

	public String getPurposeOfJourney() {
		return PurposeOfJourney;
	}

	public void setPurposeOfJourney(String purposeOfJourney) {
		if (purposeOfJourney == null) {
			purposeOfJourney = "1";
		}
		PurposeOfJourney = purposeOfJourney;
		draftObj.setPurposeOfJourney(purposeOfJourney);
	}

	// TECHNICAL REVIEW
	String NearestPoliceStation = "";
	String ClaimProcessingBranch = "";
	String PAVValue = "";
	String Comments = "";
	// String ClaimProcessBranch = "1";
	String ConsistancyByCSR = "1";

	public String getNearestPoliceStation() {
		return NearestPoliceStation;
	}

	public void setNearestPoliceStation(String nearestPoliceStation) {
		NearestPoliceStation = nearestPoliceStation;
		draftObj.setNearestPoliceStation(nearestPoliceStation);
	}

	public String getClaimProcessingBranch() {
		return ClaimProcessingBranch;
	}

	public void setClaimProcessingBranch(String processingBranch) {
		ClaimProcessingBranch = processingBranch;
		draftObj.setClaimProcessingBranch(processingBranch);
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String Comm) {
		Comments = Comm;
		draftObj.setComments(Comm);
	}

	public String getPAVValue() {
		return PAVValue;
	}

	public void setPAVValue(String pAVValue) {
		PAVValue = pAVValue;
		draftObj.setPAVValue(pAVValue);
	}

	/*
	 * public String getClaimProcessBranch(){ return ClaimProcessBranch; }
	 * public void setClaimProcessBranch(String claimProcessBranch){
	 * if(claimProcessBranch==null){ claimProcessBranch = "1"; }
	 * ClaimProcessBranch = claimProcessBranch;
	 * draftObj.setClaimProcessBranch(claimProcessBranch); }
	 */

	public String getConsistancyByCSR() {
		return ConsistancyByCSR;
	}

	public void setConsistancyByCSR(String consistancyByCSR) {
		ConsistancyByCSR = consistancyByCSR;
		draftObj.setConsistancyByCSR(consistancyByCSR);
	}

	// HIDDEN FIELDS
	String NameOfCSR = "Suren";
	String CSRCode = "";


	public String getNameOfCSR() {
		return NameOfCSR;
	}

	public void setNameOfCSR(String nameOfCSR) {
		NameOfCSR = nameOfCSR;
		draftObj.setNameOfCSR(nameOfCSR);
	}

	public String getCSRCode() {
		return CSRCode;
	}

	public void setCSRCode(String cSRCode) {
		CSRCode = cSRCode;
		draftObj.setCSRCode(cSRCode);
	}

	public String getTimeVisited() {
		return TimeVisited;
	}

	public void setTimeVisited(String timeVisited) {
		TimeVisited = timeVisited;
		draftObj.setTimeVisited(timeVisited);
	}

	public boolean[] getselectVehicleClassForOld() {
		return this.selectVehicleClassForOld;
	}

	public void setselectVehicleClassForOld(boolean[] selectVehicleClassForOld) {
		this.selectVehicleClassForOld = selectVehicleClassForOld;
		draftObj.setselectVehicleClassForOld(selectVehicleClassForOld);
	}

	public boolean[] getselectVehicleClassForNew() {
		return this.selectVehicleClassForNew;
	}

	public void setselectVehicleClassForNew(boolean[] selectVehicleClassForNew) {
		this.selectVehicleClassForNew = selectVehicleClassForNew;
		draftObj.setselectVehicleClassForNew(selectVehicleClassForNew);
	}

	public boolean[] getselectPossibleDR() {
		return this.selectPossibleDR;
	}

	public void setselectPossibleDR(boolean[] selectPossibleDR) {
		this.selectPossibleDR = selectPossibleDR;
		draftObj.setselectPossibleDR(selectPossibleDR);
	}

	public int getvehicleType() {
		return vehicleType;
	}

	public void setvehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
		draftObj.setvehicleType(vehicleType);
	}

	public boolean getisVehicleShow() {
		return isVehicleShow;
	}

	public void setisVehicleShow(boolean isVehicleShow) {
		this.isVehicleShow = isVehicleShow;
		draftObj.setisVehicleShow(isVehicleShow);
	}

	// --------------------PRE----------------------
	public boolean getisPreSelected() {
		return preSelected;
	}

	public void setisPreSelected(boolean pre) {
		preSelected = pre;
		draftObj.setisPreSelected(pre);
	}

	String CSRUserName = "";

	public String getCSRUserName() {
		return CSRUserName;
	}

	public void setCSRUserName(String cSRUserName) {
		CSRUserName = cSRUserName;
		draftObj.setCSRUserName(cSRUserName);
	}

	// -----------------OTHER-------------------------
	String damagedItemsOther = "";
	String predamagedItemsOther = "";

	public String getdamagedItemsOtherField() {
		return damagedItemsOther;
	}

	public void setdamagedItemsOtherField(String damagedItems) {
		this.damagedItemsOther = damagedItems;
		draftObj.setdamagedItemsOtherField(damagedItems);
	}

	public String getpredamagedItemsOtherField() {
		return predamagedItemsOther;
	}

	public void setpredamagedItemsOtherField(String predamagedItems) {
		this.predamagedItemsOther = predamagedItems;
		draftObj.setpredamagedItemsOtherField(predamagedItems);
	}

	// --------------------POSSIBLE DR OTHER------------------------------
	String possibleDR_Other = "";

	public String getpossibleDR_Other() {
		return possibleDR_Other;
	}

	public void setpossibleDR_Other(String possibleDR) {
		this.possibleDR_Other = possibleDR;
		draftObj.setpossibleDR_Other(possibleDR);
	}

	// -----------------REASON SELECTORS-----------------------------------
	int drivername_reason_selector = 0;

	public int getdrivername_reason_selector() {
		return drivername_reason_selector;
	}

	public void setdrivername_reason_selector(int s) {
		drivername_reason_selector = s;
		draftObj.setdrivername_reason_selector(s);
	}

	// -------------------IMAGE CATEGORIES----------------------------------
	ArrayList<String> fileListDLStatement;

	public ArrayList<String> getDLStatementImageList() {
		return fileListDLStatement;
	}

	public void setDLStatementImageList(ArrayList<String> fileList) {
		fileListDLStatement = fileList;
		draftObj.setDLStatementImageList(fileList);
	}

	ArrayList<String> fileListTechnicalOfficerComments;

	public ArrayList<String> getTechnicalOfficerCommentsImageList() {
		return fileListTechnicalOfficerComments;
	}

	public void setTechnicalOfficerCommentsImageList(ArrayList<String> fileList) {
		fileListTechnicalOfficerComments = fileList;
		draftObj.setTechnicalOfficerCommentsImageList(fileList);
	}

	ArrayList<String> ClaimFormImage;

	public ArrayList<String> getClaimFormImageImageList() {
		return ClaimFormImage;
	}

	public void setClaimFormImageImageList(ArrayList<String> fileList) {
		ClaimFormImage = fileList;
		draftObj.setClaimFormImageImageList(fileList);
	}

	ArrayList<String> ARI;

	public ArrayList<String> getARIImageList() {
		return ARI;
	}

	public void setARIImageList(ArrayList<String> fileList) {
		ARI = fileList;
		draftObj.setARIImageList(fileList);
	}

	ArrayList<String> DR;

	public ArrayList<String> getDRImageList() {
		return DR;
	}

	public void setDRImageList(ArrayList<String> fileList) {
		DR = fileList;
		draftObj.setDRImageList(fileList);
	}

	ArrayList<String> SeenVisit;

	public ArrayList<String> getSeenVisitImageList() {
		return SeenVisit;
	}

	public void setSeenVisitImageList(ArrayList<String> fileList) {
		SeenVisit = fileList;
		draftObj.setSeenVisitImageList(fileList);
	}

	ArrayList<String> SpecialReport1;

	public ArrayList<String> getSpecialReport1ImageList() {
		return SpecialReport1;
	}

	public void setSpecialReport1ImageList(ArrayList<String> fileList) {
		SpecialReport1 = fileList;
		draftObj.setSpecialReport1ImageList(fileList);
	}

	ArrayList<String> SpecialReport2;

	public ArrayList<String> getSpecialReport2ImageList() {
		return SpecialReport2;
	}

	public void setSpecialReport2ImageList(ArrayList<String> fileList) {
		SpecialReport2 = fileList;
		draftObj.setSpecialReport2ImageList(fileList);
	}

	ArrayList<String> SpecialReport3;

	public ArrayList<String> getSpecialReport3ImageList() {
		return SpecialReport3;
	}

	public void setSpecialReport3ImageList(ArrayList<String> fileList) {
		SpecialReport3 = fileList;
		draftObj.setSpecialReport3ImageList(fileList);
	}

	ArrayList<String> Supplementary1;

	public ArrayList<String> getSupplementary1ImageList() {
		return Supplementary1;
	}

	public void setSupplementary1ImageList(ArrayList<String> fileList) {
		Supplementary1 = fileList;
		draftObj.setSupplementary1ImageList(fileList);
	}

	ArrayList<String> Supplementary2;

	public ArrayList<String> getSupplementary2ImageList() {
		return Supplementary2;
	}

	public void setSupplementary2ImageList(ArrayList<String> fileList) {
		Supplementary2 = fileList;
		draftObj.setSupplementary2ImageList(fileList);
	}

	ArrayList<String> Supplementary3;

	public ArrayList<String> getSupplementary3ImageList() {
		return Supplementary3;
	}

	public void setSupplementary3ImageList(ArrayList<String> fileList) {
		Supplementary3 = fileList;
		draftObj.setSupplementary3ImageList(fileList);
	}

	ArrayList<String> Supplementary4;

	public ArrayList<String> getSupplementary4ImageList() {
		return Supplementary4;
	}

	public void setSupplementary4ImageList(ArrayList<String> fileList) {
		Supplementary4 = fileList;
		draftObj.setSupplementary4ImageList(fileList);
	}

	ArrayList<String> Acknowledgment;

	public ArrayList<String> getAcknowledgmentImageList() {
		return Acknowledgment;
	}

	public void setAcknowledgmentImageList(ArrayList<String> fileList) {
		Acknowledgment = fileList;
		draftObj.setAcknowledgmentImageList(fileList);
	}

	ArrayList<String> SalvageReport;

	public ArrayList<String> getSalvageReportImageList() {
		return SalvageReport;
	}

	public void setSalvageReportImageList(ArrayList<String> fileList) {
		SalvageReport = fileList;
		draftObj.setSalvageReportImageList(fileList);
	}

	// ---------------------Setting the drafts file
	// name-------------------------------------
	// contain .ssm also
	String DraftFileName = "";

	public String getDraftFileName() {
		return DraftFileName;
	}

	public void setDraftFileName(String name) {
		DraftFileName = name;
		draftObj.setDraftFileName(name);
	}

	// ---------------------------------------Ref
	// No---------------------------------------
	// No need to assign to the duplicate obj FormObjectDraft.java
	// Coz this is auto generating when the submitting the data
	String refno = "";

	public String getRefNo() {
		return refno;
	}

	public void setRefNo(String name) {
		refno = name;
	}

	// ----------------------Holding what kind of img types/categories upload in
	// the re submission--------------------
	String resubmitTypes = "";

	public String getResubmissionType() {
		return resubmitTypes;
	}

	public void setResubmissionType(String submissionType) {
		resubmitTypes = submissionType;
	}

	//getIsExit and setIsExit not needed inside Draft Object
	boolean isExit = false;
	
	public boolean getIsExit() {
		return isExit;
	}
	
	public void setIsExit(boolean ie) {
		isExit = ie;
	}

	int dLStatementImageListCount = 0;
	public int getDLStatementImageListCount() {
		return dLStatementImageListCount;
	}
	public void setDLStatementImageListCount(int size) {
		dLStatementImageListCount = size;
	}

	int technicalOfficerCommentsImageListCount = 0;
	public int getTechnicalOfficerCommentsImageListCount() {
		return technicalOfficerCommentsImageListCount;
	}
	public void setTechnicalOfficerCommentsImageListCount(int size) {
		technicalOfficerCommentsImageListCount = size;
	}

	int claimFormImageImageListCount = 0;
	public int getClaimFormImageImageListCount() {
		return claimFormImageImageListCount;
	}
	public void setClaimFormImageImageListCount(int size) {
		claimFormImageImageListCount = size;
	}

	int aRIImageListCount = 0;
	public int getARIImageListCount() {
		return aRIImageListCount;		
	}
	public void setARIImageListCount(int size) {
		aRIImageListCount = size;
	}

	int dRImageListCount = 0;	
	public int getDRImageListCount() {
		return dRImageListCount;
	}
	public void setDRImageListCount(int size) {
		dRImageListCount = size;
	}

	int seenVisitImageListCount = 0;	
	public int getSeenVisitImageListCount() {
		return seenVisitImageListCount;
	}
	public void setSeenVisitImageListCount(int size) {
		seenVisitImageListCount = size;
	}

	int specialReport1ImageListCount = 0;	
	public int getSpecialReport1ImageListCount() {
		return specialReport1ImageListCount;
	}
	public void setSpecialReport1ImageListCount(int size) {
		specialReport1ImageListCount = size;
	}
	
	int specialReport2ImageListCount = 0;	
	public int getSpecialReport2ImageListCount() {
		return specialReport2ImageListCount;
	}
	public void setSpecialReport2ImageListCount(int size) {
		specialReport2ImageListCount = size;
	}
	
	int specialReport3ImageListCount = 0;	
	public int getSpecialReport3ImageListCount() {
		return specialReport3ImageListCount;
	}
	public void setSpecialReport3ImageListCount(int size) {
		specialReport3ImageListCount = size;
	}

	int supplementary1ImageListCount = 0;	
	public int getSupplementary1ImageListCount() {
		return supplementary1ImageListCount;
	}
	public void setSupplementary1ImageListCount(int size) {
		supplementary1ImageListCount = size;
	}
	
	int supplementary2ImageListCount = 0;	
	public int getSupplementary2ImageListCount() {
		return supplementary2ImageListCount;
	}
	public void setSupplementary2ImageListCount(int size) {
		supplementary2ImageListCount = size;
	}
	
	int supplementary3ImageListCount = 0;	
	public int getSupplementary3ImageListCount() {
		return supplementary3ImageListCount;
	}
	public void setSupplementary3ImageListCount(int size) {
		supplementary3ImageListCount = size;
	}
	
	int supplementary4ImageListCount = 0;	
	public int getSupplementary4ImageListCount() {
		return supplementary4ImageListCount;
	}
	public void setSupplementary4ImageListCount(int size) {
		supplementary4ImageListCount = size;
	}

	int acknowledgmentImageListCount = 0;	
	public int getAcknowledgmentImageListCount() {
		return acknowledgmentImageListCount;
	}
	public void setAcknowledgmentImageListCount(int size) {
		acknowledgmentImageListCount = size;
	}

	int salvageReportImageListCount = 0;	
	public int getSalvageReportImageListCount() {
		return salvageReportImageListCount;
	}
	public void setSalvageReportImageListCount(int size) {
		salvageReportImageListCount = size;
	}

	int accidentImageListCount = 0;	
	public int getAccidentImageListCount() {
		return accidentImageListCount;
	}
	public void setAccidentImageListCount(int size) {
		accidentImageListCount = size;
	}
}