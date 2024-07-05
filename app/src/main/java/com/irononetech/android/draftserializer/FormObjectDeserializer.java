package com.irononetech.android.draftserializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import com.irononetech.android.Application.Application;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.FormObjectDraft;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.VisitObjectDraft;

public class FormObjectDeserializer {

	static Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);

	// SA Form
	public static Boolean deserializeFormData(final String fileindex) {
		try {
			FormObject fomObj = Application.createFormObjectInstance();
			if (!deserialize(fomObj, fileindex)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			LOG.error("deserializeFormData:10102");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }return false;
		}
	}

	private static Boolean deserialize(FormObject fo, String fileindex) {
		try {
			int indx = Integer.parseInt(fileindex);

			FileInputStream f2;
			String fname = FileOperations.getFileNameFromIndex(indx);
			f2 = new FileInputStream(fname + ".ssm");

			ObjectInputStream in = new ObjectInputStream(f2);
			FormObjectDraft fod = (FormObjectDraft) in.readObject();

			if (assignToObject(fo, fod)) {
				// LogFile.d("INFO ", TAG + "Deserialize:10103");
			}

			f2.close();
			in.close();
			return true;
		} catch (Exception e) {
			//LogFile.d("EXCEPTION ", TAG + "deserialize:10103" + ex.getMessage());
			LOG.error("deserialize:10103");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	private static void setStringToBool(String csvString, FormObject fo)
			throws ParserConfigurationException, SAXException, IOException {
		try {

			int p1 = Application.get4DArrSizeSec1();
			int p2 = Application.get4DArrSizeSec2();
			int p3 = Application.get4DArrSizeSec3();
			int p4 = Application.get4DArrSizeSec4();
			boolean[][][][] damagedItemsArray = new boolean[p1][p2][p3][p4];
			/*
			 * for (int i=0;i<p1;i++){ for (int j=0; j<p2;j++){ for(int
			 * k=0;k<p3;k++){ damagedItemsArray[i][j][k][0]= false;
			 * damagedItemsArray[i][j][k][1]= false; } } }
			 */

			if (csvString != null && !csvString.isEmpty()) {
				String[] noHashArr = csvString.split("#");
				String[] listItems;

				for (int i = 0; i < noHashArr.length; i++) {
					if (!noHashArr[i].isEmpty()) {
						listItems = noHashArr[i].split(",");
						if (listItems.length > 0) {
							damagedItemsArray[Integer.parseInt(listItems[0].trim())][Integer.parseInt(listItems[1]
									.trim())][Integer.parseInt(listItems[2].trim())][Integer.parseInt(listItems[3].trim())] = true;
						}
					}
				}
			}

			if (fo.getvehicleType() == 1) {
				fo.setBooleanlistBus(damagedItemsArray);
			} else if (fo.getvehicleType() == 2) {
				fo.setBooleanlistCar(damagedItemsArray);
			} else if (fo.getvehicleType() == 3) {
				fo.setBooleanlistLorry(damagedItemsArray);
			} else if (fo.getvehicleType() == 4) {
				fo.setBooleanlistVan(damagedItemsArray);
			} else if (fo.getvehicleType() == 5) {
				fo.setBooleanlistThreeWheel(damagedItemsArray);
			} else if (fo.getvehicleType() == 6) {
				fo.setBooleanlistMotorcycle(damagedItemsArray);
			} else if (fo.getvehicleType() == 7) {
				fo.setBooleanlistTractor4WD(damagedItemsArray);
			} else if (fo.getvehicleType() == 8) {
				fo.setBooleanlistHandTractor(damagedItemsArray);
			}
		} catch (Exception e) {
			LOG.error("setStringToBool:10104");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static void setPreStringToBool(String csvString, FormObject fo)
			throws ParserConfigurationException, SAXException, IOException {
		try {
			int p1 = Application.get4DArrSizeSec1();
			int p2 = Application.get4DArrSizeSec2();
			int p3 = Application.get4DArrSizeSec3();
			int p4 = Application.get4DArrSizeSec4();
			boolean[][][][] predamagedItemsArray = new boolean[p1][p2][p3][p4];
			/*
			 * for (int i=0;i<p1;i++){ for (int j=0;j<p2;j++){ for(int
			 * k=0;k<p3;k++){ predamagedItemsArray[i][j][k][0]= false;
			 * predamagedItemsArray[i][j][k][1]= false; } } }
			 */

			if (csvString != null && !csvString.isEmpty()) {

				String[] noHashArr = csvString.split("#");
				String[] listItems;

				for (int i = 0; i < noHashArr.length; i++) {
					if (!noHashArr[i].isEmpty()) {

						listItems = noHashArr[i].split(",");
						if (listItems.length > 0) {
							predamagedItemsArray[Integer.parseInt(listItems[0].trim())][Integer.parseInt(listItems[1]
									.trim())][Integer.parseInt(listItems[2].trim())][Integer.parseInt(listItems[3].trim())] = true;
						}
					}
				}
			}

			if (fo.getvehicleType() == 1) {
				fo.setBooleanprelistCar(predamagedItemsArray);
			} else if (fo.getvehicleType() == 2) {
				fo.setBooleanprelistCar(predamagedItemsArray);
			} else if (fo.getvehicleType() == 3) {
				fo.setBooleanprelistLorry(predamagedItemsArray);
			} else if (fo.getvehicleType() == 4) {
				fo.setBooleanprelistVan(predamagedItemsArray);
			} else if (fo.getvehicleType() == 5) {
				fo.setBooleanprelistThreeWheel(predamagedItemsArray);
			} else if (fo.getvehicleType() == 6) {
				fo.setBooleanprelistMotorcycle(predamagedItemsArray);
			} else if (fo.getvehicleType() == 7) {
				fo.setBooleanprelistTractor4WD(predamagedItemsArray);
			} else if (fo.getvehicleType() == 8) {
				fo.setBooleanprelistHandTractor(predamagedItemsArray);
			}
		} catch (Exception e) {
			LOG.error("setPreStringToBool:10105");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static boolean assignToObject(FormObject fo, FormObjectDraft fod) {
		try {
			//if(fo.getTimeReported() == null || fo.getTimeReported().isEmpty())
				//LOG.info("Deserializing a null job object");
			
			//if(fod.getOrgTimeReported() == null || fod.getOrgTimeReported().isEmpty())
				//LOG.error("Class=FormObjectDeserializer - Func=assignToObject - OrgTimeReported is null");

			// fo = fod
			fo.setOrgTimeReported(fod.getOrgTimeReported());
			fo.setTimeVisited(fod.getTimeVisited());
			fo.setDateandTimeofAccident(fod.getDateandTimeofAccident());
			
			fo.setPolicyCoverNotePeriodFrom(fod.getPolicyCoverNotePeriodFrom());
			fo.setPolicyCoverNotePeriodTo(fod.getPolicyCoverNotePeriodTo());
			fo.setExpiryDateOfLicence(fod.getExpiryDateOfLicence());
			fo.setvehicleType(fod.getvehicleType());

			if (fo.getvehicleType() == 1) {
				setStringToBool(fod.getBooleanlistBus(), fo);
				setPreStringToBool(fod.getBooleanprelistBus(), fo);
			} else if (fo.getvehicleType() == 2) {
				setStringToBool(fod.getBooleanlistCar(), fo);
				setPreStringToBool(fod.getBooleanprelistCar(), fo);
			} else if (fo.getvehicleType() == 3) {
				setStringToBool(fod.getBooleanlistLorry(), fo);
				setPreStringToBool(fod.getBooleanprelistLorry(), fo);
			} else if (fo.getvehicleType() == 4) {
				setStringToBool(fod.getBooleanlistVan(), fo);
				setPreStringToBool(fod.getBooleanprelistVan(), fo);
			} else if (fo.getvehicleType() == 5) {
				setStringToBool(fod.getBooleanlistThreeWheel(), fo);
				setPreStringToBool(fod.getBooleanprelistThreeWheel(), fo);
			} else if (fo.getvehicleType() == 6) {
				setStringToBool(fod.getBooleanlistMotorcycle(), fo);
				setPreStringToBool(fod.getBooleanprelistMotorcycle(), fo);
			} else if (fo.getvehicleType() == 7) {
				setStringToBool(fod.getBooleanlistTractor4WD(), fo);
				setPreStringToBool(fod.getBooleanprelistTractor4WD(), fo);
			} else if (fo.getvehicleType() == 8) {
				setStringToBool(fod.getBooleanlistHandTractor(), fo);
				setPreStringToBool(fod.getBooleanprelistHandTractor(), fo);
			}

			fo.setisSMS(fod.getisSMS());
			fo.setisDRAFT(fod.getisDRAFT());
			fo.setisSEARCH(fod.getisSEARCH());

			// fo.setPointOfImpactAdded(fod.isPointOfImpactAdded());
			fo.setActivityNotificationDialogStr(fod.getActivityNotificationDialogStr());
			fo.setDataXML(fod.getDataXML());
			fo.setRelationshipTimePeriod(fod.getRelationshipTimePeriod());
			fo.setLocationofAccident(fod.getLocationofAccident());
			fo.setVehicleTypeandColor(fod.getVehicleTypeandColor());
			fo.setContactNo(fod.getContactNo());
			fo.setNameofCaller(fod.getNameofCaller());
			fo.setJobStatus(fod.getJobStatus());
			fo.setJobNo(fod.getJobNo());
			fo.setTimeReported(fod.getTimeReported());
			fo.setVehicleNo(fod.getVehicleNo());
			fo.setPolicyCoverNoteNo(fod.getPolicyCoverNoteNo());
			fo.setPolicyCoverNotePeriodFrom(fod.getPolicyCoverNotePeriodFrom());
			fo.setPolicyCoverNotePeriodTo(fod.getPolicyCoverNotePeriodTo());
			fo.setPolicyCoverNoteSerialNo(fod.getPolicyCoverNoteSerialNo());
			fo.setCoverNoteIssuedBy(fod.getCoverNoteIssuedBy());
			fo.setReasonsforIssuingCoverNote(fod.getReasonsforIssuingCoverNote());
			fo.setNameofInsured(fod.getNameofInsured());
			fo.setContactNooftheInsured(fod.getContactNooftheInsured());
			fo.setAccidentImageList(fod.getAccidentImageList());
			fo.setPointOfImpactList(fod.getPointOfImpactList());
			fo.setDriversName(fod.getDriversName());
			fo.setRelationshipBetweenDriverAndInsured(fod.getRelationshipBetweenDriverAndInsured());
			fo.setNICNoOfDriver(fod.getNICNoOfDriver());
			
			fo.setTypeOfNICNo(fod.getTypeOfNICNo()); // Thisaru Guruge | 01/02/2016 | To add new NIC type.
			
			fo.setDrivingLicenceNo(fod.getDrivingLicenceNo());
			fo.setExpiryDateOfLicence(fod.getExpiryDateOfLicence());
			fo.setTypeOfDrivingLicence(fod.getTypeOfDrivingLicence());
			fo.setSelectDL_NewOld(fod.getSelectDL_NewOld());
			fo.setCompetence(fod.getCompetence());
			fo.setVehicleClass(fod.getVehicleClass());
			fo.setselectTirecondition(fod.getselectTirecondition());
			fo.setChasisNo(fod.getChasisNo());
			fo.setEngineNo(fod.getEngineNo());
			fo.setMeterReading(fod.getMeterReading());
			fo.setDamagedItems(fod.getDamagedItems());
			// Draft compatibility has to check here too. DR list loading encounter error otherwise.
			fo.setPossibleDR(fod.getPossibleDR());
			fo.setAreTheyContributory(fod.getAreTheyContributory());
			fo.setIsFurtherReviewNeeded(fod.getIsFurtherReviewNeeded());
			fo.setOnSiteEstimation(fod.getOnSiteEstimation());
			fo.setAppCost(fod.getAppCost());
			fo.setImageCount(fod.getImageCount());
			fo.setTypeOfGoodsCarried(fod.getTypeOfGoodsCarried());
			fo.setWeightOfGoodsCarried(fod.getWeightOfGoodsCarried());
			fo.setDamagesToTheGoods(fod.getDamagesToTheGoods());
			fo.setOverLoaded(fod.getOverLoaded());
			fo.setOverWeightContributory(fod.getOverWeightContributory());
			fo.setOtherVehiclesInvolved(fod.getOtherVehiclesInvolved());
			fo.setThirdPartyDamagesProperty(fod.getThirdPartyDamagesProperty());
			fo.setInjuries_InsuredAnd3rdParty(fod.getInjuries_InsuredAnd3rdParty());
			fo.setPurposeOfJourney(fod.getPurposeOfJourney());
			fo.setNearestPoliceStation(fod.getNearestPoliceStation());
			fo.setComments(fod.getComments());
			fo.setPAVValue(fod.getPAVValue());
			// fo.setClaimProcessBranch(fod.getClaimProcessBranch());
			fo.setClaimProcessingBranch(fod.getClaimProcessingBranch());
			fo.setConsistancyByCSR(fod.getConsistancyByCSR());
			fo.setNameOfCSR(fod.getNameOfCSR());
			fo.setCSRCode(fod.getCSRCode());
			
			fo.setselectVehicleClassForOld(fod.getselectVehicleClassForOld());
			fo.setselectVehicleClassForNew(fod.getselectVehicleClassForNew());
			fo.setselectPossibleDR(fod.getselectPossibleDR());
			// fo.setvehicleType(fod.getvehicleType());
			fo.setisVehicleShow(fod.getisVehicleShow());
			fo.setisPreSelected(fod.getisPreSelected());
			fo.setCSRUserName(fod.getCSRUserName());
			fo.setdamagedItemsOtherField(fod.getdamagedItemsOtherField());
			fo.setpredamagedItemsOtherField(fod.getpredamagedItemsOtherField());
			fo.setpossibleDR_Other(fod.getpossibleDR_Other());
			fo.setdrivername_reason_selector(fod.getdrivername_reason_selector());
			fo.setDLStatementImageList(fod.getDLStatementImageList());
			fo.setTechnicalOfficerCommentsImageList(fod.getTechnicalOfficerCommentsImageList());
			fo.setClaimFormImageImageList(fod.getClaimFormImageImageList());
			fo.setARIImageList(fod.getARIImageList());
			fo.setDRImageList(fod.getDRImageList());
			fo.setSeenVisitImageList(fod.getSeenVisitImageList());
			fo.setSpecialReport1ImageList(fod.getSpecialReport1ImageList());
			fo.setSpecialReport2ImageList(fod.getSpecialReport2ImageList());
			fo.setSpecialReport3ImageList(fod.getSpecialReport3ImageList());
			fo.setSupplementary1ImageList(fod.getSupplementary1ImageList());
			fo.setSupplementary2ImageList(fod.getSupplementary2ImageList());
			fo.setSupplementary3ImageList(fod.getSupplementary3ImageList());
			fo.setSupplementary4ImageList(fod.getSupplementary4ImageList());
			fo.setAcknowledgmentImageList(fod.getAcknowledgmentImageList());
			fo.setSalvageReportImageList(fod.getSalvageReportImageList());
			fo.setDraftFileName(fod.getDraftFileName());
			
			fo.setselectedAccidentImages(fod.getselectedAccidentImages());
			fo.setselectedDLStatementImages(fod.getselectedDLStatementImages());
			fo.setselectedTechnicalOfficerCommentsImages(fod.getselectedTechnicalOfficerCommentsImages());
			fo.setselectedClaimFormImageImages(fod.getselectedClaimFormImageImages());
			fo.setselectedARIImages(fod.getselectedARIImages());
			fo.setselectedDRImages(fod.getselectedDRImages());
			fo.setselectedSeenVisitImages(fod.getselectedSeenVisitImages());
			fo.setselectedSpecialReport1Images(fod.getselectedSpecialReport1Images());
			fo.setselectedSpecialReport2Images(fod.getselectedSpecialReport2Images());
			fo.setselectedSpecialReport3Images(fod.getselectedSpecialReport3Images());
			fo.setselectedSupplementary1Images(fod.getselectedSupplementary1Images());
			fo.setselectedSupplementary2Images(fod.getselectedSupplementary2Images());
			fo.setselectedSupplementary3Images(fod.getselectedSupplementary3Images());
			fo.setselectedSupplementary4Images(fod.getselectedSupplementary4Images());
			fo.setselectedAcknowledgmentImages(fod.getselectedAcknowledgmentImages());
			fo.setselectedSalvageReportImages(fod.getselectedSalvageReportImages());

			return true;
		} catch (Exception e) {
			LOG.error("assignToObject:10106");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}


	// Visits
	public static Boolean deserializeFormDataForVisits(final String fileindex) {
		try {
			VisitObject vObj = Application.createVisitObjectInstance();
			if (!deserializeForVisits(vObj, fileindex)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			LOG.error("deserializeFormDataForVisits:11102");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	private static Boolean deserializeForVisits(VisitObject vo, String fileindex) {
		try {
			int indx = Integer.parseInt(fileindex);

			FileInputStream f2;
			String fname = FileOperations.getFileNameFromIndexForVisits(indx);
			f2 = new FileInputStream(fname + ".ssmv");

			ObjectInputStream in = new ObjectInputStream(f2);
			VisitObjectDraft fod = (VisitObjectDraft) in.readObject();

			if (assignToObjectForVisits(vo, fod)) {
				// LogFile.d("INFO ", TAG + "Deserialize:10103");
			}

			f2.close();
			in.close();
			return true;
		} catch (Exception e) {
			LOG.error("deserializeForVisits:11103");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	private static boolean assignToObjectForVisits(VisitObject vo, VisitObjectDraft vod) {
		try {

			if(vo.getInspectionDate() == null || vo.getInspectionDate().isEmpty())
				LOG.info("Deserializing a null visit object");

			// vo = vod
			vo.setVisitId((vod.getVisitId()));
			vo.setJobNo((vod.getJobNo()));
			vo.setVehicleNo((vod.getVehicleNo()));

			vo.setInspectionDate((vod.getInspectionDate()));
			vo.setChassisNo((vod.getChassisNo()));
			vo.setEngineNo((vod.getEngineNo()));

			vo.setPreviousComments((vod.getPreviousComments()));
			vo.setComments((vod.getComments()));

			vo.setInspectionType((vod.getInspectionType()));
			vo.setInspectionTypeArrIndex((vod.getInspectionTypeArrIndex()));
			vo.setInspectionTypeInText((vod.getInspectionTypeInText()));
			vo.setDraftFileName((vod.getDraftFileName()));

			vo.setisSMS((vod.getisSMS()));
			vo.setisDRAFT((vod.getisDRAFT()));
			vo.setisSEARCH((vod.getisSEARCH()));

			vo.setVisitFolderName((vod.getVisitFolderName()));

			return true;
		} catch (Exception e) {
			LOG.error("assignToObjectForVisits:11106");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}
}
