package com.irononetech.android.formcomponent;

import java.io.Serializable;
import java.util.ArrayList;

import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.view.PossibleDRMapping;

public class FormObjectDraft implements Serializable{

	private static final long serialVersionUID = 1L;
	boolean editable = true;

	public boolean isEditable(){
		return editable;
	}
	public void setEditable(boolean editable){
		this.editable = editable;
	}

	//--------3 States of the App----------------------------------------
	boolean isSMS = false;
	boolean isDRAFT = false;
	boolean isSEARCH = false;
	
	public boolean getisSMS(){
		return isSMS;
	}
	
	public void setisSMS(boolean sms){
		isSMS = sms;
	}
	
	public boolean getisDRAFT(){
		return isDRAFT;
	}
	
	public void setisDRAFT(boolean draft){
		isDRAFT = draft;
	}
	
	public boolean getisSEARCH(){
		return isSEARCH;
	}
	
	public void setisSEARCH(boolean search){
		isSEARCH = search;
	}
	//-------------------------------------------------------------------
	
/*	boolean pointOfImpactAdded = false;
	public boolean isPointOfImpactAdded(){
		return pointOfImpactAdded;
	}
	public void setPointOfImpactAdded(boolean pointOfImpactAdded){
		this.pointOfImpactAdded = pointOfImpactAdded;
	}*/

	String activityNotificationDialogStr = "";
	
	public String getActivityNotificationDialogStr(){
		return activityNotificationDialogStr;
	}

	public void setActivityNotificationDialogStr(
			String activityNotificationDialogStr){
		this.activityNotificationDialogStr = activityNotificationDialogStr;
	}

	String dataXML;
	public String getDataXML(){
		return dataXML;
	}

	public void setDataXML(String dataXML){
		this.dataXML = dataXML;
	}

	// Accident Details
	String JobStatus = "PENDING";
	String JobNo = "";
	String TimeReported = "";
	String OrgTimeReported = "";
	String Periodampm = "0";
	String ContactNo = "";
	String NameofCaller = "";
	String VehicleNo = "";
	String VehicleTypeandColor = "";
	String DateandTimeofAccident = "";
	String LocationofAccident = "";
	String selectVehicleClassType;
	boolean selectVehicleClassForOld[];
	boolean selectVehicleClassForNew[];
	boolean selectPossibleDR[];
	boolean selectTirecondition[];
		
	int vehicleType = 1;
	boolean isVehicleShow;
	boolean preSelected = false;
	
	String booleanlistBus;
	String booleanlistLorry;
	String booleanlistVan;
	String booleanlistCar;
	String booleanlistThreeWheel;
	String booleanlistMotorcycle;
	String booleanlistTractor4WD;
	String booleanlistHandTractor;
	
	String booleanprelistBus;
	String booleanprelistLorry;
	String booleanprelistVan;
	String booleanprelistCar;
	String booleanprelistThreeWheel;
	String booleanprelistMotorcycle;
	String booleanprelistTractor4WD;
	String booleanprelistHandTractor;
	
	public FormObjectDraft(){
		selectTirecondition= new boolean[Application.getTyreConditionArrSize()];
		for (int i=0; i < selectTirecondition.length; i++){
        	if(i % 4 == 0){
        		selectTirecondition[i + 3] = true;
        	}
        	//else
        		//selectTirecondition[i]= false;
        }
		
		selectPossibleDR= new boolean[PossibleDRMapping.values().length];
		for (int i = 0; i < selectPossibleDR.length; i++){
			selectPossibleDR[i] = false;
		}
		
		selectVehicleClassForOld= new boolean[12];
		for (int i = 0; i < selectVehicleClassForOld.length; i++){
			selectVehicleClassForOld[i] = false;
		}
		
		selectVehicleClassForNew= new boolean[13];
		for (int i = 0; i < selectVehicleClassForNew.length; i++){
			selectVehicleClassForNew[i] = false;
		}
		
		SelectDL_NewOld="1"; // 1 is new
		vehicleType=0;
		isVehicleShow = true;
		
		
		/*int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();
		int p4 = Application.get4DArrSizeSec4();*/
		
		/*booleanlistBus = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistBus[i][j][k][0]= false;
					booleanlistBus[i][j][k][1]= false;
				}
			}
		}*/

		/*booleanlistCar = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistCar[i][j][k][0]= false;
					booleanlistCar[i][j][k][1]= false;
				}
			}
		}
		
		booleanlistThreeWheel = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistThreeWheel[i][j][k][0]= false;
					booleanlistThreeWheel[i][j][k][1]= false;
				}
			}
		}*/
		
		/*booleanlistMotorcycle = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistMotorcycle[i][j][k][0]= false;
					booleanlistMotorcycle[i][j][k][1]= false;
				}
			}
		}*/
		
		/*booleanlistLorry = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistLorry[i][j][k][0]= false;
					booleanlistLorry[i][j][k][1]= false;
				}
			}
		}*/
		
		/*booleanlistVan  = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanlistVan[i][j][k][0]= false;
					booleanlistVan[i][j][k][1]= false;
				}
			}
		}*/
		
		//------------------PRE----------------------------------
		
/*		booleanprelistBus = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistBus[i][j][k][0]= false;
					booleanprelistBus[i][j][k][1]= false;
				}
			}
		}

		booleanprelistCar = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistCar[i][j][k][0]= false;
					booleanprelistCar[i][j][k][1]= false;
				}
			}
		}
		
		booleanprelistThreeWheel = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistThreeWheel[i][j][k][0]= false;
					booleanprelistThreeWheel[i][j][k][1]= false;
				}
			}
		}
		
		booleanprelistMotorcycle = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistMotorcycle[i][j][k][0]= false;
					booleanprelistMotorcycle[i][j][k][1]= false;
				}
			}
		}
		
		booleanprelistLorry = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistLorry[i][j][k][0]= false;
					booleanprelistLorry[i][j][k][1]= false;
				}
			}
		}
		
		booleanprelistVan  = new boolean[p1][p2][p3][p4];
		for (int i=0;i<p1;i++){
			for (int j=0; j<p2;j++){
				for(int k=0;k<p3;k++){
					booleanprelistVan[i][j][k][0]= false;
					booleanprelistVan[i][j][k][1]= false;
				}
			}
		}*/

	}
	
	public String getBooleanlistBus(){
		return booleanlistBus;
	}

	public void setBooleanlistBus(String listBus){
		this.booleanlistBus = listBus;
	}

	public String getBooleanlistLorry(){
		return booleanlistLorry;
	}

	public void setBooleanlistLorry(String listLorry){
		this.booleanlistLorry = listLorry;
	}

	public String getBooleanlistVan(){
		return booleanlistVan;
	}

	public void setBooleanlistVan(String listVan){
		this.booleanlistVan = listVan;
	}

	public String getBooleanlistCar(){
		return booleanlistCar;
	}
	
	public void setBooleanlistCar(String listCar){
		this.booleanlistCar = listCar;
	}
	
	public String getBooleanlistThreeWheel(){
		return booleanlistThreeWheel;
	}

	public void setBooleanlistThreeWheel(String listThreeWheel){
		this.booleanlistThreeWheel = listThreeWheel;
	}

	public String getBooleanlistMotorcycle(){
		return booleanlistMotorcycle;
	}

	public void setBooleanlistMotorcycle(String listMotorcycle){
		this.booleanlistMotorcycle = listMotorcycle;
	}
	
	public String getBooleanlistTractor4WD(){
		return booleanlistTractor4WD;
	}

	public void setBooleanlistTractor4WD(String listTractor4WD){
		this.booleanlistTractor4WD = listTractor4WD;
	}
	
	public String getBooleanlistHandTractor(){
		return booleanlistHandTractor;
	}

	public void setBooleanlistHandTractor(String listHandTractor){
		this.booleanlistHandTractor = listHandTractor;
	}
	
	//----------------------PRE------------------------------------
	public String getBooleanprelistBus(){
		return booleanprelistBus;
	}

	public void setBooleanprelistBus(String prelistBus){
		this.booleanprelistBus = prelistBus;
	}

	public String getBooleanprelistLorry(){
		return booleanprelistLorry;
	}

	public void setBooleanprelistLorry(String prelistLorry){
		this.booleanprelistLorry = prelistLorry;
	}

	public String getBooleanprelistVan(){
		return booleanprelistVan;
	}

	public void setBooleanprelistVan(String prelistVan){
		this.booleanprelistVan = prelistVan;
	}

	public String getBooleanprelistCar(){
		return booleanprelistCar;
	}
	
	public void setBooleanprelistCar(String prelistCar){
		this.booleanprelistCar = prelistCar;
	}

	public String getBooleanprelistThreeWheel(){
		return booleanlistThreeWheel;
	}

	public void setBooleanprelistThreeWheel(String prelistThreeWheel){
		this.booleanprelistThreeWheel = prelistThreeWheel;
	}
	
	public String getBooleanprelistMotorcycle(){
		return booleanprelistMotorcycle;
	}

	public void setBooleanprelistMotorcycle(String prelistMotorcycle){
		this.booleanprelistMotorcycle = prelistMotorcycle;
	}
	
	public String getBooleanprelistTractor4WD(){
		return booleanprelistTractor4WD;
	}

	public void setBooleanprelistTractor4WD(String prelistTractor4WD){
		this.booleanprelistTractor4WD = prelistTractor4WD;
	}
	
	public String getBooleanprelistHandTractor(){
		return booleanprelistHandTractor;
	}

	public void setBooleanprelistHandTractor(String prelistHandTractor){
		this.booleanprelistHandTractor = prelistHandTractor;
	}
	
	public String getTimePeriods(){
		return Periodampm;
	}

	public String getRelationshipTimePeriod(){
		return Periodampm;
	}
	
	public void setRelationshipTimePeriod(String Period){
		if(Period==null){
			Period = "1";
		}
		Periodampm = Period;
	}
	
	public String getDateandTimeofAccident(){
		return DateandTimeofAccident;
	}

	public void setDateandTimeofAccident(String dateandTimeofAccident){
		DateandTimeofAccident = dateandTimeofAccident;
	}

	public String getLocationofAccident(){
		return LocationofAccident;
	}

	public void setLocationofAccident(String locationofAccident){
		LocationofAccident = locationofAccident;
	}

	public String getVehicleTypeandColor(){
		return VehicleTypeandColor;
	}

	public void setVehicleTypeandColor(String vehicleTypeandColor){
		VehicleTypeandColor = vehicleTypeandColor;
	}

	public String getContactNo(){
		return ContactNo;
	}

	public void setContactNo(String contactNo){
		ContactNo = contactNo;
	}

	public String getNameofCaller(){
		return NameofCaller;
	}

	public void setNameofCaller(String nameofCaller){
		NameofCaller = nameofCaller;
	}

	public String getJobStatus(){
		return JobStatus;
	}

	public void setJobStatus(String jobStatus){
		JobStatus = jobStatus;
	}

	public String getJobNo(){
		return JobNo;
	}

	public void setJobNo(String jobNo){
		JobNo = jobNo;
	}
	
	public String getTimeReported(){
		return TimeReported;
	}

	public void setTimeReported(String timeReported){
		TimeReported = timeReported;
	}

	public String getVehicleNo(){
		return VehicleNo;
	}

	public void setVehicleNo(String vehicleNo){
		VehicleNo = vehicleNo;
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

	public String getPolicyCoverNoteNo(){
		return policyCoverNoteNo;
	}

	public void setPolicyCoverNoteNo(String policyCoverNoteNo){
		this.policyCoverNoteNo = policyCoverNoteNo;
	}

	public String getPolicyCoverNotePeriodFrom(){
		return policyCoverNotePeriodFrom;
	}

	public void setPolicyCoverNotePeriodFrom(String policyCoverNotePeriodFrom){
		this.policyCoverNotePeriodFrom = policyCoverNotePeriodFrom;
	}

	public String getPolicyCoverNotePeriodTo(){
		return policyCoverNotePeriodTo;
	}

	public void setPolicyCoverNotePeriodTo(String policyCoverNotePeriodTo){
		this.policyCoverNotePeriodTo = policyCoverNotePeriodTo;
	}

	public String getPolicyCoverNoteSerialNo(){
		return policyCoverNoteSerialNo;
	}

	public void setPolicyCoverNoteSerialNo(String policyCoverNoteSerialNo){
		this.policyCoverNoteSerialNo = policyCoverNoteSerialNo;
	}

	public String getCoverNoteIssuedBy(){
		return coverNoteIssuedBy;
	}

	public void setCoverNoteIssuedBy(String coverNoteIssuedBy){
		this.coverNoteIssuedBy = coverNoteIssuedBy;
	}

	public String getReasonsforIssuingCoverNote(){
		return reasonsforIssuingCoverNote;
	}

	public void setReasonsforIssuingCoverNote(String reasonsforIssuingCoverNote){
		this.reasonsforIssuingCoverNote = reasonsforIssuingCoverNote;
	}

	public String getNameofInsured(){
		return NameofInsured;
	}

	public void setNameofInsured(String nameofInsured){
		NameofInsured = nameofInsured;
	}

	public String getContactNooftheInsured(){
		return ContactNooftheInsured;
	}

	public void setContactNooftheInsured(String contactNooftheInsured){
		ContactNooftheInsured = contactNooftheInsured;
	}

	ArrayList<String> AccidentImageList;
	public ArrayList<String> getAccidentImageList(){
		return AccidentImageList;
	}

	public void setAccidentImageList(ArrayList<String> accidentImageList){
		AccidentImageList = accidentImageList;
	}
	
	ArrayList<String> PointOfImpactList;
	public ArrayList<String> getPointOfImpactList(){
		return PointOfImpactList;
	}

	public void setPointOfImpactList(ArrayList<String> pointOfImpactList){
		PointOfImpactList = pointOfImpactList;
	}

	//DRIVER DETAILS
	String DriversName = "";
	String RelationshipBetweenDriverAndInsured = "0";
	String TypeOfNICNo = ""; // Thisaru Guruge | 29/01/2016 | This is to set the NIC No. type of the drivers NIC
	String NICNoOfDriver = "";
	String DrivingLicenceNo = "";
	String ExpiryDateOfLicence = "";
	String TypeOfDrivingLicence = "0";
	String SelectDL_NewOld = "0";
	String Competence = "1";
	String VehicleClass = "";
	
	
	public String getDriversName(){
		return DriversName;
	}

	public void setDriversName(String driversName){
		DriversName = driversName;
	}

	public String getRelationshipBetweenDriverAndInsured(){
		return RelationshipBetweenDriverAndInsured;
	}

	public void setRelationshipBetweenDriverAndInsured(
			String relationshipBetweenDriverAndInsured){
		if(relationshipBetweenDriverAndInsured==null){
			relationshipBetweenDriverAndInsured = "1";
		}
		RelationshipBetweenDriverAndInsured = relationshipBetweenDriverAndInsured;
	}
	
	/*
	 * Created By: Thisaru Guruge
	 * Date: 29 / 01 / 2016
	 * To add the new NIC types, have to add getter and setter to the DraftObject
	 */
	
	public String getTypeOfNICNo() {
		return TypeOfNICNo;
	}
	
	public void setTypeOfNICNo (String typeOfNICNo) {
		TypeOfNICNo = typeOfNICNo;
	}

	public String getNICNoOfDriver(){
		return NICNoOfDriver;
	}

	public void setNICNoOfDriver(String nICNoOfDriver){
		NICNoOfDriver = nICNoOfDriver;
	}

	public String getDrivingLicenceNo(){
		return DrivingLicenceNo;
	}

	public void setDrivingLicenceNo(String drivingLicenceNo){
		DrivingLicenceNo = drivingLicenceNo;
	}

	public String getExpiryDateOfLicence(){
		return ExpiryDateOfLicence;
	}

	public void setExpiryDateOfLicence(String expiryDateOfLicence){
		ExpiryDateOfLicence = expiryDateOfLicence;
	}

	public String getTypeOfDrivingLicence(){
		return TypeOfDrivingLicence;
	}

	public void setTypeOfDrivingLicence(String typeOfDrivingLicence){
		TypeOfDrivingLicence = typeOfDrivingLicence;
	}

	public String getSelectDL_NewOld(){
		return SelectDL_NewOld;
	}

	public void setSelectDL_NewOld(String selectDLNewOld){
		SelectDL_NewOld = selectDLNewOld;
	}

	public String getCompetence(){
		return Competence;
	}

	public void setCompetence(String competence){
		Competence = competence;
	}

	public String getVehicleClass(){
		return VehicleClass;
	}

	public void setVehicleClass(String vehicleClass){
		VehicleClass = vehicleClass;
	}
	
	//VEHICLE DETAILS
	String ChasisNo = "";
	String EngineNo = "";
	String MeterReading = "";
	String DamagedItems = "";
	String PossibleDR = "";
	String AreTheyContributory = "0";
	String IsFurtherReviewNeeded = "0";



	public void setselectTirecondition(boolean[] selectTirecondition){
		this.selectTirecondition = selectTirecondition;
	}

	public boolean[] getselectTirecondition(){
		return this.selectTirecondition;
	}

	public String getChasisNo(){
		return ChasisNo;
	}

	public void setChasisNo(String chasisNo){
		ChasisNo = chasisNo;
	}

	public String getEngineNo(){
		return EngineNo;
	}

	public void setEngineNo(String engineNo){
		EngineNo = engineNo;
	}

	public String getMeterReading(){
		return MeterReading;
	}

	public void setMeterReading(String meterReading){
		MeterReading = meterReading;
	}

	public String getDamagedItems(){
		return DamagedItems;
	}

	public void setDamagedItems(String damagedItems){
		DamagedItems = damagedItems;
	}

	public String getPossibleDR(){
		return PossibleDR;
	}

	public void setPossibleDR(String possibleDR){
		PossibleDR = possibleDR;
	}	

	public String getAreTheyContributory(){
		return AreTheyContributory;
	}

	public void setAreTheyContributory(String areTheyContributory){
		AreTheyContributory = areTheyContributory;
	}

	public String getIsFurtherReviewNeeded() {
		return IsFurtherReviewNeeded;
	}

	public void setIsFurtherReviewNeeded(String isFurtherReviewNeeded) {
		IsFurtherReviewNeeded = isFurtherReviewNeeded;
	}

	
	// COMMENT FORM
	String OnSiteEstimation = "1";
	String EstCost = "";
	String ImageCount = "0";	
	
	public String getOnSiteEstimation(){
		return OnSiteEstimation;
	}

	public void setOnSiteEstimation(String OnSiteEstimationVal){
		OnSiteEstimation = OnSiteEstimationVal;
	}
	
	public String getAppCost(){
		return EstCost;
	}
	
	public void setAppCost(String cost){
		EstCost = cost;
	}
	
	public void setImageCount(String img){
		ImageCount = img;
	}
	
	public String getImageCount(){
		return ImageCount;
	}
	
	//OTHER ITEMS
	String TypeOfGoodsCarried = "";
	String WeightOfGoodsCarried = "";
	String DamagesToTheGoods = "";
	String OverLoaded = "0";
	String OverWeightContributory = "0";
	String OtherVehiclesInvolved = "";
	String ThirdPartyDamagesProperty = "";
	String Injuries_InsuredAnd3rdParty = "";
	String PurposeOfJourney = "1";

	public String getTypeOfGoodsCarried(){
		return TypeOfGoodsCarried;
	}

	public void setTypeOfGoodsCarried(String typeOfGoodsCarried){
		TypeOfGoodsCarried = typeOfGoodsCarried;
	}

	public String getWeightOfGoodsCarried(){
		return WeightOfGoodsCarried;
	}

	public void setWeightOfGoodsCarried(String weightOfGoodsCarried){
		WeightOfGoodsCarried = weightOfGoodsCarried;
	}

	public String getDamagesToTheGoods(){
		return DamagesToTheGoods;
	}

	public void setDamagesToTheGoods(String damagesToTheGoods){
		DamagesToTheGoods = damagesToTheGoods;
	}

	public String getOverLoaded(){
		return OverLoaded;
	}

	public void setOverLoaded(String overLoaded){
		OverLoaded = overLoaded;
	}

	public String getOverWeightContributory(){
		return OverWeightContributory;
	}

	public void setOverWeightContributory(String overWeightContributory){
		OverWeightContributory = overWeightContributory;
	}

	public String getOtherVehiclesInvolved(){
		return OtherVehiclesInvolved;
	}

	public void setOtherVehiclesInvolved(String otherVehiclesInvolved){
		OtherVehiclesInvolved = otherVehiclesInvolved;
	}

	public String getThirdPartyDamagesProperty(){
		return ThirdPartyDamagesProperty;
	}

	public void setThirdPartyDamagesProperty(String thirdPartyDamagesProperty){
		ThirdPartyDamagesProperty = thirdPartyDamagesProperty;
	}

	public String getInjuries_InsuredAnd3rdParty(){
		return Injuries_InsuredAnd3rdParty;
	}

	public void setInjuries_InsuredAnd3rdParty(String injuriesInsuredAnd3rdParty){
		Injuries_InsuredAnd3rdParty = injuriesInsuredAnd3rdParty;
	}

	public String getPurposeOfJourney(){
		return PurposeOfJourney;
	}

	public void setPurposeOfJourney(String purposeOfJourney){
		if(purposeOfJourney==null){
			purposeOfJourney = "1";
		}
		PurposeOfJourney = purposeOfJourney;
	}

	//TECHNICAL REVIEW
	String NearestPoliceStation = "";
	String ClaimProcessingBranch = "";
	String PAVValue = "";
	String Comments = "";
	//String ClaimProcessBranch = "1";
	String ConsistancyByCSR = "1";

	public String getNearestPoliceStation(){
		return NearestPoliceStation;
	}

	public void setNearestPoliceStation(String nearestPoliceStation){
		NearestPoliceStation = nearestPoliceStation;
	}

	public String getClaimProcessingBranch(){
		return ClaimProcessingBranch;
	}
	public void setClaimProcessingBranch(String processingBranch){
		ClaimProcessingBranch = processingBranch;
	}
	
	public String getComments(){
		return Comments;
	}
	
	public void setComments(String Comm){
		Comments = Comm;
	}
	
	public String getPAVValue(){
		return PAVValue;
	}

	public void setPAVValue(String pAVValue){
		PAVValue = pAVValue;
	}
	
	/*public String getClaimProcessBranch(){
		return ClaimProcessBranch;
	}

	public void setClaimProcessBranch(String claimProcessBranch){
		if(claimProcessBranch==null){
			claimProcessBranch = "1";
		}
		ClaimProcessBranch = claimProcessBranch;
	}*/

	public String getConsistancyByCSR(){
		return ConsistancyByCSR;
	}

	public void setConsistancyByCSR(String consistancyByCSR){
		ConsistancyByCSR = consistancyByCSR;
	}

	//HIDDEN FIELDS
	String NameOfCSR = "rajith";
	String CSRCode = "";
	String TimeVisited = "";

	public String getNameOfCSR(){
		return NameOfCSR;
	}

	public void setNameOfCSR(String nameOfCSR){
		NameOfCSR = nameOfCSR;
	}

	public String getCSRCode(){
		return CSRCode;
	}

	public void setCSRCode(String cSRCode){
		CSRCode = cSRCode;
	}

	public String getTimeVisited(){
		return TimeVisited;
	}

	public void setTimeVisited(String timeVisited){
		TimeVisited = timeVisited;
	}
	
	public void setselectVehicleClassForOld(boolean[] selectVehicleClassForOld){
		this.selectVehicleClassForOld= selectVehicleClassForOld;
	}
	public boolean[] getselectVehicleClassForOld(){
		return this.selectVehicleClassForOld;
	}
	
	public void setselectVehicleClassForNew(boolean[] selectVehicleClassForNew){
		this.selectVehicleClassForNew= selectVehicleClassForNew;
	}
	public boolean[] getselectVehicleClassForNew(){
		return this.selectVehicleClassForNew;
	}
	
	

	public void setselectPossibleDR(boolean[] selectPossibleDR){
		this.selectPossibleDR= selectPossibleDR;
	}
	public boolean[] getselectPossibleDR(){
		return this.selectPossibleDR;
	}
	
	public void setvehicleType(int vehicleType){
		this.vehicleType = vehicleType;
	}
	public int getvehicleType(){
		return vehicleType;
	}
	
	public void setisVehicleShow(boolean isVehicleShow){
		this.isVehicleShow = isVehicleShow;
	}
	public boolean getisVehicleShow(){
		return isVehicleShow;
	}

	//--------------------PRE----------------------
	public boolean getisPreSelected(){
		return preSelected;
	}
	
	public void setisPreSelected(boolean pre){
		preSelected = pre;
	}

	String CSRUserName = "";
	public String getCSRUserName(){
		return CSRUserName;
	}

	public void setCSRUserName(String cSRUserName){
		CSRUserName = cSRUserName;
	}

	//-----------------OTHER-------------------------
	String damagedItemsOther = "";
	String predamagedItemsOther = "";
	
	public String getdamagedItemsOtherField(){
		return damagedItemsOther;
	}
	public void setdamagedItemsOtherField(String damagedItems){
		this.damagedItemsOther = damagedItems;
	}
	
	public String getpredamagedItemsOtherField(){
		return predamagedItemsOther;
	}
	public void setpredamagedItemsOtherField(String predamagedItems){
		this.predamagedItemsOther = predamagedItems;
	}
	
	//--------------------POSSIBLE DR OTHER------------------------------
	String possibleDR_Other = "";
	
	public String getpossibleDR_Other(){
		return possibleDR_Other;
	}
	public void setpossibleDR_Other(String possibleDR){
		this.possibleDR_Other = possibleDR;
	}
	
	//-----------------REASON SELECTORS-----------------------------------
	int drivername_reason_selector = 0;
	
	public int getdrivername_reason_selector(){
		return drivername_reason_selector;
	}
	public void setdrivername_reason_selector(int s){
		drivername_reason_selector = s;
	}

	//-------------------IMAGE CATEGORIES----------------------------------
	ArrayList<String> fileListDLStatement;
	public void setDLStatementImageList(ArrayList<String> fileList){
		fileListDLStatement = fileList;
	}
	public ArrayList<String> getDLStatementImageList(){
		return fileListDLStatement;
	}

	ArrayList<String> fileListTechnicalOfficerComments;
	public void setTechnicalOfficerCommentsImageList(ArrayList<String> fileList){
		fileListTechnicalOfficerComments = fileList;
	}
	public ArrayList<String> getTechnicalOfficerCommentsImageList(){
		return fileListTechnicalOfficerComments;
	}
	
	ArrayList<String> ClaimFormImage;
	public void setClaimFormImageImageList(ArrayList<String> fileList){
		ClaimFormImage = fileList;
	}
	public ArrayList<String> getClaimFormImageImageList(){
		return ClaimFormImage;
	}
	
	ArrayList<String> ARI;
	public void setARIImageList(ArrayList<String> fileList){
		ARI = fileList;
	}
	public ArrayList<String> getARIImageList(){
		return ARI;
	}
	
	ArrayList<String> DR;
	public void setDRImageList(ArrayList<String> fileList){
		DR = fileList;
	}
	public ArrayList<String> getDRImageList(){
		return DR;
	}
	
	ArrayList<String> SeenVisit;
	public void setSeenVisitImageList(ArrayList<String> fileList){
		SeenVisit = fileList;
	}
	public ArrayList<String> getSeenVisitImageList(){
		return SeenVisit;
	}
	
	ArrayList<String> SpecialReport1;
	public void setSpecialReport1ImageList(ArrayList<String> fileList){
		SpecialReport1 = fileList;
	}
	public ArrayList<String> getSpecialReport1ImageList(){
		return SpecialReport1;
	}
	
	ArrayList<String> SpecialReport2;
	public void setSpecialReport2ImageList(ArrayList<String> fileList){
		SpecialReport2 = fileList;
	}
	public ArrayList<String> getSpecialReport2ImageList(){
		return SpecialReport2;
	}
	
	ArrayList<String> SpecialReport3;
	public void setSpecialReport3ImageList(ArrayList<String> fileList){
		SpecialReport3 = fileList;
	}
	public ArrayList<String> getSpecialReport3ImageList(){
		return SpecialReport3;
	}
	
	ArrayList<String> Supplementary1;
	public void setSupplementary1ImageList(ArrayList<String> fileList){
		Supplementary1 = fileList;
	}
	public ArrayList<String> getSupplementary1ImageList(){
		return Supplementary1;
	}
	
	ArrayList<String> Supplementary2;
	public void setSupplementary2ImageList(ArrayList<String> fileList){
		Supplementary2 = fileList;
	}
	public ArrayList<String> getSupplementary2ImageList(){
		return Supplementary2;
	}
	
	ArrayList<String> Supplementary3;
	public void setSupplementary3ImageList(ArrayList<String> fileList){
		Supplementary3 = fileList;
	}
	public ArrayList<String> getSupplementary3ImageList(){
		return Supplementary3;
	}
	
	ArrayList<String> Supplementary4;
	public void setSupplementary4ImageList(ArrayList<String> fileList){
		Supplementary4 = fileList;
	}
	public ArrayList<String> getSupplementary4ImageList(){
		return Supplementary4;
	}
	
	ArrayList<String> Acknowledgment;
	public void setAcknowledgmentImageList(ArrayList<String> fileList){
		Acknowledgment = fileList;
	}
	public ArrayList<String> getAcknowledgmentImageList(){
		return Acknowledgment;
	}
	
	ArrayList<String> SalvageReport;
	public void setSalvageReportImageList(ArrayList<String> fileList){
		SalvageReport = fileList;
	}
	public ArrayList<String> getSalvageReportImageList(){
		return SalvageReport;
	}
	
	//---------------------Setting the drafts file name-------------------------------------
	String DraftFileName = "";
	public void setDraftFileName(String name){
		DraftFileName = name;
	}
	public String getDraftFileName(){
		return DraftFileName;
	}
	
	
	//used to track to original time reported sent by call center
	//if not available or manual entry in SA from this will be empty string
	public String getOrgTimeReported(){
		return OrgTimeReported;
	}
	public void setOrgTimeReported(String otimeReported){
		OrgTimeReported = otimeReported;
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
		
		public boolean[] getselectedDLStatementImages(){
			return selectedDLStatementImages;
		}
		public void setselectedDLStatementImages(boolean[] arr){
			selectedDLStatementImages = arr;
		}
		public boolean[] getselectedTechnicalOfficerCommentsImages(){
			return selectedTechnicalOfficerCommentsImages;
		}
		public void setselectedTechnicalOfficerCommentsImages(boolean[] arr){
			selectedTechnicalOfficerCommentsImages = arr;
		}
		public boolean[] getselectedClaimFormImageImages(){
			return selectedClaimFormImageImages;
		}
		public void setselectedClaimFormImageImages(boolean[] arr){
			selectedClaimFormImageImages = arr;
		}
		public boolean[] getselectedARIImages(){
			return selectedARIImages;
		}
		public void setselectedARIImages(boolean[] arr){
			selectedARIImages = arr;
		}
		public boolean[] getselectedDRImages(){
			return selectedDRImages;
		}
		public void setselectedDRImages(boolean[] arr){
			selectedDRImages = arr;
		}
		public boolean[] getselectedSeenVisitImages(){
			return selectedSeenVisitImages;
		}
		public void setselectedSeenVisitImages(boolean[] arr){
			selectedSeenVisitImages = arr;
		}
		public boolean[] getselectedSpecialReport1Images(){
			return selectedSpecialReport1Images;
		}
		public void setselectedSpecialReport1Images(boolean[] arr){
			selectedSpecialReport1Images = arr;
		}
		public boolean[] getselectedSpecialReport2Images(){
			return selectedSpecialReport2Images;
		}
		public void setselectedSpecialReport2Images(boolean[] arr){
			selectedSpecialReport2Images = arr;
		}
		public boolean[] getselectedSpecialReport3Images(){
			return selectedSpecialReport3Images;
		}
		public void setselectedSpecialReport3Images(boolean[] arr){
			selectedSpecialReport3Images = arr;
		}
		public boolean[] getselectedSupplementary1Images(){
			return selectedSupplementary1Images;
		}
		public void setselectedSupplementary1Images(boolean[] arr){
			selectedSupplementary1Images = arr;
		}
		public boolean[] getselectedSupplementary2Images(){
			return selectedSupplementary2Images;
		}
		public void setselectedSupplementary2Images(boolean[] arr){
			selectedSupplementary2Images = arr;
		}
		public boolean[] getselectedSupplementary3Images(){
			return selectedSupplementary3Images;
		}
		public void setselectedSupplementary3Images(boolean[] arr){
			selectedSupplementary3Images = arr;
		}
		public boolean[] getselectedSupplementary4Images(){
			return selectedSupplementary4Images;
		}
		public void setselectedSupplementary4Images(boolean[] arr){
			selectedSupplementary4Images = arr;
		}
		public boolean[] getselectedAcknowledgmentImages(){
			return selectedAcknowledgmentImages;
		}
		public void setselectedAcknowledgmentImages(boolean[] arr){
			selectedAcknowledgmentImages = arr;
		}
		public boolean[] getselectedSalvageReportImages(){
			return selectedSalvageReportImages;
		}
		public void setselectedSalvageReportImages(boolean[] arr){
			selectedSalvageReportImages = arr;
		}
		
		public boolean[] getselectedAccidentImages() {
			return selectedAccidentImages;
		}

		public void setselectedAccidentImages(boolean[] arr) {
			selectedAccidentImages = arr;
		}		
}