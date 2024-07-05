package com.irononetech.android.formcomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.formcomponent.view.ClaimProcessingBranchMapping;
import com.irononetech.android.formcomponent.view.DriverLicenceNoReasonMapping;
import com.irononetech.android.formcomponent.view.NewSelectVehicleClassMapping;
import com.irononetech.android.formcomponent.view.OldSelectVehicleClassMapping;
import com.irononetech.android.formcomponent.view.PossibleDRMapping;
import com.irononetech.android.formcomponent.view.NICTypeMapping;

public class FormXMLCreator {
	
	static Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	
	
	public String getDamagedItemList(FormObject formObject) {
		boolean[][][][] booleanDamageItemList;

		int vehicleType = formObject.getvehicleType();
		if (vehicleType == 1) {
			//vehicleTypeName= "Bus";
			booleanDamageItemList = formObject.getBooleanlistBus(); 
		} else if (vehicleType == 2) {
			//vehicleTypeName= "Car";
			booleanDamageItemList = formObject.getBooleanlistCar();
		}
		else if (vehicleType == 3) {
			//vehicleTypeName= "Lorry";
			booleanDamageItemList = formObject.getBooleanlistLorry();
		}
		else if (vehicleType == 4) {
			//vehicleTypeName="Van";
			booleanDamageItemList = formObject.getBooleanlistVan();
		}
		else if (vehicleType == 5) {
			//vehicleTypeName="ThreeWheel";
			booleanDamageItemList = formObject.getBooleanlistThreeWheel();
		}
		else if (vehicleType == 6) {
			//vehicleTypeName="Motorcycle";
			booleanDamageItemList = formObject.getBooleanlistMotorcycle();
		}
		else if (vehicleType == 7) {
			//vehicleTypeName="Tractor4WD";
			booleanDamageItemList = formObject.getBooleanlistTractor4WD();
		}
		else if (vehicleType == 8) {
			//vehicleTypeName="HandTractor";
			booleanDamageItemList = formObject.getBooleanlistHandTractor();
		}
		else{
			booleanDamageItemList=null;
		}
		
		int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();
		
		//String xml = "";
		String data = "";
		if (booleanDamageItemList != null) {
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						if(booleanDamageItemList[i][j][k][0]){
							data += "<item>" + vehicleType + "/" + j + "/" + k + "</item>";
						}
					}
				}
			}

			//xml += "<Type>".concat(Integer.toString(vehicleType)).concat("</Type>");
			//xml += "<ItemColl><DamagedItemData>".concat(data).concat("</DamagedItemData></ItemColl>");
		}
		return data;
	}
	
	public String getpreDamagedItemList(FormObject formObject) {
		boolean[][][][] booleanpreDamageItemList;

		int vehicleType = formObject.getvehicleType();
		if (vehicleType == 1) {
			//vehicleTypeName= "Bus";
			booleanpreDamageItemList = formObject.getBooleanprelistBus(); 
		} else if (vehicleType == 2) {
			//vehicleTypeName= "Car";
			booleanpreDamageItemList = formObject.getBooleanprelistCar();
		}
		else if (vehicleType == 3) {
			//vehicleTypeName= "Lorry";
			booleanpreDamageItemList = formObject.getBooleanprelistLorry();
		}
		else if (vehicleType == 4) {
			//vehicleTypeName="Van";
			booleanpreDamageItemList = formObject.getBooleanprelistVan();
		}
		else if (vehicleType == 5) {
			//vehicleTypeName="ThreeWheel";
			booleanpreDamageItemList = formObject.getBooleanprelistThreeWheel();
		}
		else if (vehicleType == 6) {
			//vehicleTypeName="Motorcycle";
			booleanpreDamageItemList = formObject.getBooleanprelistMotorcycle();
		}
		else if (vehicleType == 7) {
			//vehicleTypeName="Tractor4WD";
			booleanpreDamageItemList = formObject.getBooleanprelistTractor4WD();
		}
		else if (vehicleType == 8) {
			//vehicleTypeName="HandTractor";
			booleanpreDamageItemList = formObject.getBooleanprelistHandTractor();
		}
		else{
			booleanpreDamageItemList=null;
		}

		int p1 = Application.get4DArrSizeSec1();
		int p2 = Application.get4DArrSizeSec2();
		int p3 = Application.get4DArrSizeSec3();

		//--------------------PRE---------------------------
		//String xml = "";
		String data = "";
		if (booleanpreDamageItemList != null) {
			for (int i = 0; i < p1; i++) {
				for (int j = 0; j < p2; j++) {
					for (int k = 0; k < p3; k++) {
						if(booleanpreDamageItemList[i][j][k][0]){
							data += "<item>" + vehicleType + "/" + j + "/" + k + "</item>";
						}
					}
				}
			}

			//xml += "<Type>".concat(Integer.toString(vehicleType)).concat("</Type>");
			//xml += "<ItemColl><preDamagedItemData>".concat(data).concat("</preDamagedItemData></ItemColl>");
		}
		return data;
	}
	
	
	public String getPossibleDRList(FormObject formObject) {

		//Suren: 2013-06-26 enable this code to check draft compatibility. 
		//Draft compatibility check
		/*boolean[] booleanpossibleDRList;
		if (formObject.getselectPossibleDR().length != PossibleDRMapping.values().length){ //NOT Compatible
			booleanpossibleDRList = new boolean[PossibleDRMapping.values().length];
			boolean[] oldDRList = formObject.getselectPossibleDR();
			for (int i = 0; i < oldDRList.length; i++) {
				booleanpossibleDRList[i] = oldDRList[i];
			}
		}else { //Compatible
			booleanpossibleDRList = formObject.getselectPossibleDR();
		}*/
		
		boolean[] booleanpossibleDRList = formObject.getselectPossibleDR();
		
		int count = 0;
		for (int i = 0; i < booleanpossibleDRList.length; i++) {
			if (booleanpossibleDRList[i]) {
				count++;
			}
		}
		String possibleDRList[] = new String[count];

		int newCount = 0;
		int position = 0;
		for (PossibleDRMapping fm : PossibleDRMapping.values()) {

			if (booleanpossibleDRList[newCount]) {
				possibleDRList[position] = fm.getString();
				position++;
			}
			newCount++;
		}

		String xml = "";
		if (possibleDRList != null) {
			for (int i = 0; i < possibleDRList.length; i++) {
				xml += "<item>".concat(possibleDRList[i]).concat("</item>");
			}
		}
		return xml;
	}
	
	public String getVehicleClassList(FormObject formObject) {
		boolean[] booleanvehicleClassList;
		String vehicleClassList[];

		if (formObject.getSelectDL_NewOld().equals("1")) {
			booleanvehicleClassList = formObject.getselectVehicleClassForNew();
			int count = 0;
			for (int i = 0; i < booleanvehicleClassList.length; i++) {
				if (booleanvehicleClassList[i]) {
					count++;
				}
			}

			vehicleClassList = new String[count];
			int newCount = 0;
			int position = 0;
			for (NewSelectVehicleClassMapping fm : NewSelectVehicleClassMapping
					.values()) {

				if (booleanvehicleClassList[newCount]) {
					vehicleClassList[position] = Integer.toString(fm.getInt());
					position++;
				}
				newCount++;
			}

		} else {
			booleanvehicleClassList = formObject.getselectVehicleClassForOld();
			int count = 0;
			for (int i = 0; i < booleanvehicleClassList.length; i++) {
				if (booleanvehicleClassList[i]) {
					count++;
				}
			}

			vehicleClassList = new String[count];
			int newCount = 0;
			int position = 0;
			for (OldSelectVehicleClassMapping fm : OldSelectVehicleClassMapping
					.values()) {

				if (booleanvehicleClassList[newCount]) {
					vehicleClassList[position] = Integer.toString(fm.getInt());
					position++;
				}
				newCount++;
			}

		}

		String xml = "";
		if (vehicleClassList != null) {
			for (int i = 0; i < vehicleClassList.length; i++) {
				xml += "<class>".concat(vehicleClassList[i]).concat("</class>");
			}
		}
		return xml;
	}
	
	public String getTyreConditionList(FormObject formObject) {
		boolean[] booleantyreConditionList = formObject.getselectTirecondition();
		int[] tyreConditionList = new int[6];
		int countforTruearray = 0;
		for (int i = 0; i < Application.getTyreConditionArrSize(); i++) {
			if (booleantyreConditionList[i]) {
				tyreConditionList[countforTruearray] = (i % 4) + 1;
				countforTruearray++;
			}
		}

		String xml = "";
		if (tyreConditionList != null) {
			xml = "<FR>"  + Integer.toString(tyreConditionList[0]) + "</FR>"  +
				  "<FL>"  + Integer.toString(tyreConditionList[1]) + "</FL>"  +
				  "<RRL>" + Integer.toString(tyreConditionList[2]) + "</RRL>" +
				  "<RLR>" + Integer.toString(tyreConditionList[3]) + "</RLR>" +
				  "<RLL>" + Integer.toString(tyreConditionList[4]) + "</RLL>" +
				  "<RRR>" + Integer.toString(tyreConditionList[5]) + "</RRR>";
		}
		return xml;
	}


	public String getSAFormXML(FormObject formObject) {
		//getTyreConditionList(formObject);

		//Because claim processing branch id need to be sent. not the text
		String cpBranchId = "";
		for (ClaimProcessingBranchMapping fm : ClaimProcessingBranchMapping .values()) {
			if(fm.getString().toLowerCase().equals(formObject.getClaimProcessingBranch().trim().toLowerCase())) {
				cpBranchId = Integer.toString(fm.getInt()); 
				break;
			}
		}

		/*
		 * Thisaru Guruge
		 * 01 / 02 / 2016
		 * NIC type has to be sent. The type Number should be sent instead of the string.
		 */
		String typeOfNICNo = "";
		
		for (NICTypeMapping fm : NICTypeMapping.values()) {
			if (Integer.toString(fm.getInt()).trim().equals(formObject.getTypeOfNICNo().trim())) {
				typeOfNICNo = Integer.toString(fm.getInt());
			}
		}
		
		String driverCompetence = formObject.getCompetence();
		String vehicleClass = this.getVehicleClassList(formObject);
		String licenceExpiryDate = formObject.getExpiryDateOfLicence();
		String licenceType = formObject.getTypeOfDrivingLicence();
		String licenseNewOld_IsNew = formObject.getSelectDL_NewOld();
		
		//If reason file get selected following fields are disabled.
		//So send server  empty tags.
		String licNo = formObject.getDrivingLicenceNo().trim();
		for (DriverLicenceNoReasonMapping fm : DriverLicenceNoReasonMapping.values()) {
			if(licNo.equalsIgnoreCase(fm.getString())){
				driverCompetence = "";
				vehicleClass = "";
				licenceExpiryDate = "";
				licenceType = "";
				licenseNewOld_IsNew = "";
				break;
			}
		}
		
		/*String orgTR = "";
		if(formObject.getOrgTimeReported() == null || formObject.getOrgTimeReported().isEmpty())
			orgTR = formObject.getTimeReported();
		else
			orgTR = formObject.getOrgTimeReported();*/
			
		String formXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
				+ "<Request>" + "<Data>"
				+ "<Job action=\"Add\">"
				+ "<FieldList>" + "<JobNo>"
				+ formObject.getJobNo()
				+ "</JobNo>"
				+ "<TimeReported>"
				+ formObject.getTimeReported()
				+ "</TimeReported>"
				+ "<OriginalTimeReported>"
				+ formObject.getOrgTimeReported()
				+ "</OriginalTimeReported>"
				+ "<TimeVisited>"
				+ formObject.getTimeVisited()
				+ "</TimeVisited>"
				+ "<VehicleNo>"
				+ formObject.getVehicleNo()
				+ "</VehicleNo>"
				+ "<CallerName>"
				+ formObject.getNameofCaller()
				+ "</CallerName>"
				+ "<CallerContactNo>"
				+ formObject.getContactNo()
				+ "</CallerContactNo>"
				+ "<InsuredName>"
				+ formObject.getNameofInsured()
				+ "</InsuredName>"
				+ "<InsuredContactNo>"
				+ formObject.getContactNooftheInsured()
				+ "</InsuredContactNo>"
				+ "<VehicleTypeColor>"
				+ formObject.getVehicleTypeandColor()
				+ "</VehicleTypeColor>"
				+ "<AccidentTime>"
				+ formObject.getDateandTimeofAccident()
				+ "</AccidentTime>"
				+ "<AccidentLocation>"
				+ formObject.getLocationofAccident()
				+ "</AccidentLocation>"
				+ "<Policy_CoverNoteNo>"
				+ formObject.getPolicyCoverNoteNo()
				+ "</Policy_CoverNoteNo>"
				+ "<Policy_CoverNotePeriod>"
				+ "<from>"
				+ formObject.getPolicyCoverNotePeriodFrom()
				+ "</from>"
				+ "<to>"
				+ formObject.getPolicyCoverNotePeriodTo()
				+ "</to>"
				+ "</Policy_CoverNotePeriod>"
				+ "<Policy_CoverNoteSerialNo>"
				+ formObject.getPolicyCoverNoteSerialNo()
				+ "</Policy_CoverNoteSerialNo>"
				+ "<CoverNoteIssuedBy>"
				+ formObject.getCoverNoteIssuedBy()
				+ "</CoverNoteIssuedBy>"
				+ "<CoverNoteReasons>"
				+ formObject.getReasonsforIssuingCoverNote()
				+ "</CoverNoteReasons>"
				+ "<ChassisNo>"
				+ formObject.getChasisNo()
				+ "</ChassisNo>"
				+ "<EngineNo>"
				+ formObject.getEngineNo()
				+ "</EngineNo>"
				+ "<DriverName>"
				+ formObject.getDriversName()
				+ "</DriverName>"
				+ "<LicenceNo>"
				+ formObject.getDrivingLicenceNo()
				+ "</LicenceNo>"
				+ "<LicenceExpiryDate>"
				+ licenceExpiryDate
				+ "</LicenceExpiryDate>"
				+ "<LicenceType>"
				+ licenceType
				+ "</LicenceType>"
				+ "<!-- Other=0, MTA32=1, MTA39=2,CMT49=3, INTLIC=4 -->"
				+ "<LicenseNewOld>"
				+ "<IsNew>"
				+ licenseNewOld_IsNew
				+ "</IsNew>"
				+ "<!-- Old=0, New=1 -->"
				+ "</LicenseNewOld>"
				
				/*
				 * Thisaru Guruge
				 * 01 / 02 / 2016
				 * To add NIC types, we have to save NIC type in a separate variable. To do so, add it.
				 */
				+ "<NICType>"
				+ typeOfNICNo
				+ "</NICType>"
				
				+ "<DriverNIC>"
				+ formObject.getNICNoOfDriver()
				+ "</DriverNIC>"
				+ "<DriverCompetence>"
				+ driverCompetence
				+ "</DriverCompetence>"
				+ "<!-- Yes=1, No=0 -->"
				+ "<VehicleClass>"
				+ "<!-- C=1, C1=2, D=3 (Multiple Selection) -->"
				+ vehicleClass
				+ "</VehicleClass>"
				+ "<MeterReading>"
				+ formObject.getMeterReading()
				+ "</MeterReading>"
				+ "<DamagedItems>"
				+ getDamagedItemList(formObject)
				+ "</DamagedItems>"
				+ "<PossibleDR>"
				+ "<!-- PossibleDR Store as a XML -->"
				+ this.getPossibleDRList(formObject)
				+ "</PossibleDR>"
				+ "<TyreCondition>"
				+ "<!-- Fair=1, Good=2, Bold=3, Not Applicable=4 -->"
				+ getTyreConditionList(formObject)
				+ "</TyreCondition>"
				+ "<TyreContributory>"
				+ formObject.getAreTheyContributory()
				+ "</TyreContributory>"
				+ "<!-- Yes=1, No=0 -->"
				+ "<GoodsType>"
				+ formObject.getTypeOfGoodsCarried()
				+ "</GoodsType>"
				+ "<GoodsWeight>"
				+ formObject.getWeightOfGoodsCarried()
				+ "</GoodsWeight>"
				+ "<!-- In Kgs -->"
				+ "<GoodsDamages>"
				+ formObject.getDamagesToTheGoods()
				+ "</GoodsDamages>"
				+ "<OverLoaded>"
				+ formObject.getOverLoaded()
				+ "</OverLoaded>"
				+ "<!-- Yes=1, No=0 -->"
				+ "<OverWeightContributory>"
				+ formObject.getOverWeightContributory()
				+ "</OverWeightContributory>"
				+ "<!-- Yes=1, No=0 -->"
				+ "<OtherVehiclesInvolved>"
				+ formObject.getOtherVehiclesInvolved()
				+ "</OtherVehiclesInvolved>"
				+ "<ThirdPartyDamages>"
				+ formObject.getThirdPartyDamagesProperty()
				+ "</ThirdPartyDamages>"
				+ "<Injuries>"
				+ formObject.getInjuries_InsuredAnd3rdParty()
				+ "</Injuries>"
				+ "<NearestPoliceStation>"
				+ formObject.getNearestPoliceStation()
				+ "</NearestPoliceStation>"
				+ "<JourneyPurpose>"
				+ formObject.getPurposeOfJourney()
				+ "</JourneyPurpose>"
				+ "<!-- Other=0, Work=1, Trip=2 -->"
				+ "<DriverRelationship>"
				+ formObject.getRelationshipBetweenDriverAndInsured()
				+ "</DriverRelationship>"
				+ "<!-- Other=0, Employee=1, Father=2, Son=3, Daughter=4, Nephew=5, Uncle=6 -->"
				+ "<PavValue>"
				+ formObject.getPAVValue()
				+ "</PavValue>"
				+ "<ClaimProcessBranch>"
				+ cpBranchId
				+ "</ClaimProcessBranch>"
				+ "<CSRConsistency>"
				+ formObject.getConsistancyByCSR()
				+ "</CSRConsistency>"
				+ "<CSRUserName>"
				+ Application.getCurrentUser()
				+ "</CSRUserName>"
				+ "<Comment>"
				+ formObject.getComments()
				+ "</Comment>"
				+ "<ApproxRepairCost>"
				+ formObject.getAppCost()
				+ "</ApproxRepairCost>"
				+ "<SiteEstimation>"
				+ formObject.getOnSiteEstimation()
				+ "</SiteEstimation>"
				+ "<ImageCount>"
				+ formObject.getImageCount()
				+ "</ImageCount>"

				+ "<DriverNameReason>"
				+ "</DriverNameReason>" 
				+ "<ChassisNoReason>"
				+ "</ChassisNoReason>" 
				+ "<EngineNoReason>"
				+ "</EngineNoReason>"
				+ "<LicenceNoReason>"
				+ "</LicenceNoReason>"
				+ "<LicenceExpiryDateReason>"
				+ "</LicenceExpiryDateReason>" 
				+ "<LicenceTypeReason>"
				+ "</LicenceTypeReason>"
				+ "<VehicleClassReason>"
				+ "</VehicleClassReason>"
				+ "<DriverCompetenceReason>"
				+ "</DriverCompetenceReason>" 
				+ "<DriverNICReason>"
				+ "</DriverNICReason>"

				+ "<OtherCoverNoteReasons>"
				+ "</OtherCoverNoteReasons>" 
				+ "<OtherPossibleDR>"
				+ formObject.getpossibleDR_Other()
				+ "</OtherPossibleDR>"
				+ "<OtherDamagedItems>"
				+ formObject.getdamagedItemsOtherField()
				+ "</OtherDamagedItems>"
				+ "<OtherPreAccidentDamages>"
				+ formObject.getpredamagedItemsOtherField()
				+ "</OtherPreAccidentDamages>"

				+ "<PreAccidentDamages>"
				+ getpreDamagedItemList(formObject)
				+ "</PreAccidentDamages>"
								
				/*ImageUploadEnums has ImageTypeIds*/
				+ "<ImageCountList>"
				+ "<ImageType>"
				+ "<TypeId>3</TypeId>" /*Accident Images*/
				+ "<ImageCount>" + formObject.getAccidentImageListCount() + "</ImageCount>"
				+ "</ImageType>"

				+ "<ImageType>"
				+ "<TypeId>4</TypeId>" /*Driver Statement / DL / NIC*/
				+ "<ImageCount>" + formObject.getDLStatementImageListCount() + "</ImageCount>"
				+ "</ImageType>"
				
				+ "<ImageType>"
				+ "<TypeId>5</TypeId>" /*Technical Officer Comments*/
				+ "<ImageCount>" + formObject.getTechnicalOfficerCommentsImageListCount() + "</ImageCount>"
				+ "</ImageType>"
				
				+ "<ImageType>"
				+ "<TypeId>6</TypeId>" /*Claim Form Image*/
				+ "<ImageCount>" + formObject.getClaimFormImageImageListCount() + "</ImageCount>"
				+ "</ImageType>"
				+ "</ImageCountList>"

				//further_review_new
				+ "<FurtherReview>" + formObject.getIsFurtherReviewNeeded() + "</FurtherReview>"

				+ "</FieldList>"
				+ "</Job>"
				
				+ "<DateTime></DateTime>" + "</Data>" + "</Request>";

		LOG.info("SAFORM XML: " + formXML);
		return formXML;
	}

	public String getVisitFormXML(VisitObject vObj) {
		String formXML = "";
		try {
			LOG.debug("ENTRY ","getVisitFormXML");

			formXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + 
					"<Request>" +
					"<Data>" +
					"<Job action=\"visit\">" +
					"<FieldList>" 	+
					"<JobNo>" 		+ vObj.getJobNo() + "</JobNo>" +
					"<VisitType>" 	+ vObj.getInspectionType() + "</VisitType>" +
					"<RefNo>" 		+ "30" + "</RefNo>" +
					"<EngineNo>" 	+ vObj.getEngineNo() + "</EngineNo>" +
					"<ChassisNo>" 	+ vObj.getChassisNo() + "</ChassisNo>" +
					"<CSRUserName>" + Application.getCurrentUser() + "</CSRUserName>" +
					"<Comment>" 	+ vObj.getComments() + "</Comment>" +
					"<ImageCount>" 	+ vObj.getImageCount() + "</ImageCount>" +
					"<TimeVisited>" + vObj.getInspectionDate() + "</TimeVisited>" 
					
					/*ImageUploadEnums has ImageTypeIds*/
					+ "<ImageCountList>"
					+ "<ImageType>"
					+ "<TypeId>20</TypeId>" /*Inspection Photos / Seen Visits / Any Other*/
					+ "<ImageCount>" + vObj.getInspectionPhotosSeenVisitsAnyOtherImageListCount() + "</ImageCount>"
					+ "</ImageType>"

					+ "<ImageType>"
					+ "<TypeId>21</TypeId>" /*Estimate / Any Other Comments*/
					+ "<ImageCount>" + vObj.getEstimateAnyotherCommentsImageListCount() + "</ImageCount>"
					+ "</ImageType>"

					+ "<ImageType>"
					+ "<TypeId>5</TypeId>" /*Technical Officer Comments*/
					+ "<ImageCount>" + vObj.getTechnicalOfficerCommentsImageListCount() + "</ImageCount>"
					+ "</ImageType>" +
					
					"</ImageCountList>" +
					"</FieldList>" 	+
					"</Job>" +
					"<DateTime></DateTime>" +
					"</Data>" +
					"</Request>";

			LOG.debug("SUCCESS ","getVisitFormXML");
		} catch (Exception e) {
			LOG.error("getVisitFormXML:12148");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		LOG.info("VISIT XML: " + formXML);
		return formXML;
	}
}