package com.irononetech.android.database;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ContentValues;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.logincomponent.view.UserObject;
import com.irononetech.android.template.EventParcel;

public class DBService {

	static DBHandler dbHandler = DBHandler.getInstance();
	static Logger LOG = LoggerFactory.getLogger(DBService.class);
	
	public static void saveSAFormDetails(EventParcel eventParcel) throws Exception {
		try {
			ContentValues values = new ContentValues();
			values.clear();
			values.put(dbHandler.JT_JOB_NO, ((FormObject)eventParcel.getDataObject()).getJobNo());
			// values.put(dbHandler.JT_JOBSTATUS, ((FormObject)eventParcel.getDataObject()).getJobStatus());
			values.put(dbHandler.JT_TIME_REPORTED, ((FormObject)eventParcel.getDataObject()).getTimeReported());
			values.put(dbHandler.JT_VEHICLE_NO, ((FormObject)eventParcel.getDataObject()).getVehicleNo());
			dbHandler.insertOrIgnore(values);
		} catch (Exception e) {
			LOG.error("saveSAFormDetails");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	
	public static void saveSAImageDetails(EventParcel eventParcel, String visitId) throws Exception {
		try {
			String jobNo = ((FormObject) eventParcel.getDataObject()).getJobNo();
			String vistId = visitId;
			String refno = ((FormObject) eventParcel.getDataObject()).getRefNo();
			String resubmit = ((FormObject) eventParcel.getDataObject()).getisSEARCH()+"";
			String status = "pending";
			ArrayList<String> dirs = null;
			String field = "";
			
			if(!((FormObject) eventParcel.getDataObject()).getisSEARCH()){
			ArrayList<String> accidentImagePathList = new ArrayList<String>();
			field = "AccidentImages";
			accidentImagePathList = ((FormObject) eventParcel.getDataObject())
					.getAccidentImageList();
			if(accidentImagePathList != null && accidentImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, accidentImagePathList, resubmit, refno);
			}
			}
			
			//other image list should be saved in the same manner
			/*ArrayList<String> documentsImagePathList = new ArrayList<String>();
			field = "DriverStatement";
			documentsImagePathList = ((FormObject) eventParcel.getDataObject())
					.getDocumentsImageList();
			saveImageDetailsToDB(jobNo, field, status, documentsImagePathList);*/
			
			
			//--------------------------------------------------------------------------
			String filepathDocs = URL.getSLIC_JOBS() + jobNo + "/DocImages/";
			File fil = new File(filepathDocs);
			if(fil.length() > 0){
			File[] files = fil.listFiles();
			dirs = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()){
					dirs.add(files[i].getName());
				}
			}
			
			
			//---------
			if (!dirs.isEmpty()){
				
			if(dirs.contains("DLStatement")){
			ArrayList<String> DLStatementImagePathList = new ArrayList<String>();
			field = "DLStatement";
			DLStatementImagePathList = ((FormObject) eventParcel.getDataObject())
					.getDLStatementImageList();
			if(DLStatementImagePathList != null && DLStatementImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, DLStatementImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("TechnicalOfficerComments")){
			ArrayList<String> TechnicalOfficerCommentsImagePathList = new ArrayList<String>();
			field = "TechnicalOfficerComments";
			TechnicalOfficerCommentsImagePathList = ((FormObject) eventParcel.getDataObject())
					.getTechnicalOfficerCommentsImageList();
			if(TechnicalOfficerCommentsImagePathList != null && TechnicalOfficerCommentsImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, TechnicalOfficerCommentsImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("ClaimFormImage")){
			ArrayList<String> ClaimFormImageImagePathList = new ArrayList<String>();
			field = "ClaimFormImage";
			ClaimFormImageImagePathList = ((FormObject) eventParcel.getDataObject())
					.getClaimFormImageImageList();
			if(ClaimFormImageImagePathList != null && ClaimFormImageImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, ClaimFormImageImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("ARI")){
			ArrayList<String> ARIImagePathList = new ArrayList<String>();
			field = "ARI";
			ARIImagePathList = ((FormObject) eventParcel.getDataObject())
					.getARIImageList();
			if(ARIImagePathList != null && ARIImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, ARIImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("DR")){
			ArrayList<String> DRImagePathList = new ArrayList<String>();
			field = "DR";
			DRImagePathList = ((FormObject) eventParcel.getDataObject())
					.getDRImageList();
			if(DRImagePathList != null && DRImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, DRImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("SeenVisit")){
			ArrayList<String> SeenVisitImagePathList = new ArrayList<String>();
			field = "SeenVisit";
			SeenVisitImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSeenVisitImageList();
			if(SeenVisitImagePathList != null && SeenVisitImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, SeenVisitImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("SpecialReport1")){
			ArrayList<String> SpecialReport1ImagePathList = new ArrayList<String>();
			field = "SpecialReport1";
			SpecialReport1ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSpecialReport1ImageList();
			if(SpecialReport1ImagePathList != null && SpecialReport1ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, SpecialReport1ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("SpecialReport2")){
			ArrayList<String> SpecialReport2ImagePathList = new ArrayList<String>();
			field = "SpecialReport2";
			SpecialReport2ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSpecialReport2ImageList();
			if(SpecialReport2ImagePathList != null && SpecialReport2ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, SpecialReport2ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("SpecialReport3")){
			ArrayList<String> SpecialReport3ImagePathList = new ArrayList<String>();
			field = "SpecialReport3";
			SpecialReport3ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSpecialReport3ImageList();
			if(SpecialReport3ImagePathList != null && SpecialReport3ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, SpecialReport3ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("Supplementary1")){
			ArrayList<String> Supplementary1ImagePathList = new ArrayList<String>();
			field = "Supplementary1";
			Supplementary1ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSupplementary1ImageList();
			if(Supplementary1ImagePathList != null && Supplementary1ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, Supplementary1ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("Supplementary2")){
			ArrayList<String> Supplementary2ImagePathList = new ArrayList<String>();
			field = "Supplementary2";
			Supplementary2ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSupplementary2ImageList();
			if(Supplementary2ImagePathList != null && Supplementary2ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, Supplementary2ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("Supplementary3")){
			ArrayList<String> Supplementary3ImagePathList = new ArrayList<String>();
			field = "Supplementary3";
			Supplementary3ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSupplementary3ImageList();
			if(Supplementary3ImagePathList != null && Supplementary3ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, Supplementary3ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("Supplementary4")){
			ArrayList<String> Supplementary4ImagePathList = new ArrayList<String>();
			field = "Supplementary4";
			Supplementary4ImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSupplementary4ImageList();
			if(Supplementary4ImagePathList != null && Supplementary4ImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, Supplementary4ImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("Acknowledgment")){
			ArrayList<String> AcknowledgmentImagePathList = new ArrayList<String>();
			field = "Acknowledgment";
			AcknowledgmentImagePathList = ((FormObject) eventParcel.getDataObject())
					.getAcknowledgmentImageList();
			if(AcknowledgmentImagePathList != null && AcknowledgmentImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, AcknowledgmentImagePathList, resubmit, refno);
			}
			}
			
			if(dirs.contains("SalvageReport")){
			ArrayList<String> SalvageReportImagePathList = new ArrayList<String>();
			field = "SalvageReport";
			SalvageReportImagePathList = ((FormObject) eventParcel.getDataObject())
					.getSalvageReportImageList();
			if(SalvageReportImagePathList != null && SalvageReportImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, SalvageReportImagePathList, resubmit, refno);
			}
			}
			}}
			
			//--------------------------------------------------------------------------
			
			if(!((FormObject) eventParcel.getDataObject()).getisSEARCH()){
			ArrayList<String> pointOfImpactImagePathList = new ArrayList<String>();
			field = "PointsOfImpact";
			pointOfImpactImagePathList = ((FormObject) eventParcel.getDataObject())
					.getPointOfImpactList();
			if(pointOfImpactImagePathList != null && pointOfImpactImagePathList.size() > 0){
				saveImageDetailsToDB(vistId, field, status, pointOfImpactImagePathList, resubmit, refno);
			}
			}
		} catch (Exception e) {
			LOG.error("saveImageDetails");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public static void saveVisitImageDetails(EventParcel eventParcel, String visitId) throws Exception {
		try {
			String vistId = visitId;
			String refno = "200";			//Deprecated  (server doesn't use this)
			String resubmit = "false";		//Deprecated  (server doesn't use this)
			String status = "pending";		//Deprecated  (server doesn't use this)
			ArrayList<String> dirs = null;
			String field = "";

			String filepathDocs = URL.getSLIC_VISITS() + vistId + "/DocImages/";
			File fil = new File(filepathDocs);
			if(fil.length() > 0){
				File[] files = fil.listFiles();
				dirs = new ArrayList<String>();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()){
						dirs.add(files[i].getName());
					}
				}
			}

			if (!dirs.isEmpty()){
				String oldVisitFoldername = ((VisitObject) eventParcel.getDataObject()).getVisitFolderName();

				field = "TechnicalOfficerComments";
				if(dirs.contains(field)){
					ArrayList<String> TechnicalOfficerCommentsImagePathList = ((VisitObject) eventParcel.getDataObject()).getTechnicalOfficerCommentsImageList();
					if(TechnicalOfficerCommentsImagePathList != null && TechnicalOfficerCommentsImagePathList.size() > 0){
						for (int i = 0; i < TechnicalOfficerCommentsImagePathList.size(); i++) {
							String tmp = TechnicalOfficerCommentsImagePathList.get(i);
							tmp = tmp.replace(oldVisitFoldername, visitId);
							TechnicalOfficerCommentsImagePathList.set(i, tmp);
						}
						saveImageDetailsToDB(vistId, field, status, TechnicalOfficerCommentsImagePathList, resubmit, refno);
					}
				}

				field = "InspectionPhotosSeenVisitsAnyOther";
				if(dirs.contains(field)){
					ArrayList<String> InspectionPhotosSeenVisitsAnyOtherImagePathList = ((VisitObject) eventParcel.getDataObject()).getInspectionPhotosSeenVisitsAnyOtherImageList();
					if(InspectionPhotosSeenVisitsAnyOtherImagePathList != null && InspectionPhotosSeenVisitsAnyOtherImagePathList.size() > 0){						
						for (int i = 0; i < InspectionPhotosSeenVisitsAnyOtherImagePathList.size(); i++) {
							String tmp = InspectionPhotosSeenVisitsAnyOtherImagePathList.get(i);
							tmp = tmp.replace(oldVisitFoldername, visitId);
							InspectionPhotosSeenVisitsAnyOtherImagePathList.set(i, tmp);
						}
						saveImageDetailsToDB(vistId, field, status, InspectionPhotosSeenVisitsAnyOtherImagePathList, resubmit, refno);
					}
				}

				field = "EstimateAnyotherComments";
				if(dirs.contains(field)){
					ArrayList<String> EstimateAnyotherCommentsImagePathList = ((VisitObject) eventParcel.getDataObject()).getEstimateAnyotherCommentsImageList();
					if(EstimateAnyotherCommentsImagePathList != null && EstimateAnyotherCommentsImagePathList.size() > 0){
						for (int i = 0; i < EstimateAnyotherCommentsImagePathList.size(); i++) {
							String tmp = EstimateAnyotherCommentsImagePathList.get(i);
							tmp = tmp.replace(oldVisitFoldername, visitId);
							EstimateAnyotherCommentsImagePathList.set(i, tmp);
						}
						saveImageDetailsToDB(vistId, field, status, EstimateAnyotherCommentsImagePathList, resubmit, refno);
					}
				}

				//image folder name gets renamed. It's new name is visitId.
				((VisitObject) eventParcel.getDataObject()).setVisitFolderName(visitId);
			}
		} catch (Exception e) {
			LOG.error("saveVisitImageDetails");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	private static void saveImageDetailsToDB(String jobNo, String imgType,
			String status, ArrayList<String> imagePathList, String resub, String refno) throws Exception {
		try {
			ContentValues values = new ContentValues();
			values.clear();
			values.put(dbHandler.JT_JOB_NO, jobNo);
			// values.put(dbHandler.JT_JOBSTATUS,
			// ((FormObject)eventParcel.getDataObject()).getJobStatus());
			values.put(dbHandler.JT_FIELD, imgType);
			values.put(dbHandler.JT_STATUS, status);
			values.put(dbHandler.JT_RESUBMIT, resub);
			values.put(dbHandler.JT_REFNO, refno);
			
			for (String path : imagePathList) {
				values.put(dbHandler.JT_IMAGE_PATH, path);
				dbHandler.insertOrIgnoreToImageGal(values);
			}  
		} catch (Exception e) {
			LOG.error("saveImageDetailsToDB");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public static synchronized void updateImageGal(String[] imageDetails) {
		try {
			dbHandler.updateImageGalTable(imageDetails);
		} catch (Exception e) {
			LOG.error("updateImageGal");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	
	public static void saveUserDetails(EventParcel eventParcel) throws Exception {
		try {
			ContentValues values = new ContentValues();
			values.put(dbHandler.JT_USERNAME, ((UserObject)eventParcel.getDataObject()).getUsername().toLowerCase().trim());
			values.put(dbHandler.JT_PASSWORD, ((UserObject)eventParcel.getDataObject()).getPassword().trim());
			
			int affRec = dbHandler.updateUsersTable(values);
			if (affRec <= 0) {  //no records to update mean user not available on the db, so add the user
				dbHandler.insertOrIgnoreToUsers(values);
			}
		} catch (Exception e) {
			LOG.error("saveUserDetails");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public static boolean checkUserAuthOffline(EventParcel eventParcel){
		try {
			String usrnam = ((UserObject)eventParcel.getDataObject()).getUsername().toLowerCase().trim();
			String pass = ((UserObject)eventParcel.getDataObject()).getPassword().trim();
			
			return dbHandler.checkAuthenticationOffline(usrnam, pass);
			
		} catch (Exception e) {
			LOG.error("checkUserAuthOffline");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}
	
	public static String getPasswordOfCurrentuserService(String usrnam){
		try {
			return dbHandler.getPasswordOfCurrentuser(usrnam);			
		} catch (Exception e) {
			LOG.error("getPasswordOfCurrentuserService");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	
	public static synchronized ArrayList<String> getPendingJobNoList() {
		try {
			ArrayList<String> pendingJobNo = new ArrayList<String>();
			pendingJobNo = dbHandler.getPendingJobNoList();
			return pendingJobNo;
		} catch (Exception e) {
			LOG.error("getPendingJobNoList");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
	
	public static synchronized ArrayList<ArrayList<String>> getPendingImagesForJobNo(String jobNo) {
		try {
			ArrayList<ArrayList<String>> pendingImages = new ArrayList<ArrayList<String>>();
			pendingImages = dbHandler.getPendingImagesForJobNo(jobNo);
			return pendingImages;
		} catch (Exception e) {
			LOG.error("getPendingImagesForJobNo");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}

	public static ArrayList<String> getPendingJobDraftsList() {
		try {
			ArrayList<String> PendingDrafts = new ArrayList<String>();
			PendingDrafts = dbHandler.getPendingJobDraftsList();
			return PendingDrafts;
		} catch (Exception e) {
			LOG.error("getPendingJobDraftsList");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
	
	public static ArrayList<String> getPendingVisitDraftsList() {
		try {
			ArrayList<String> PendingDrafts = new ArrayList<String>();
			PendingDrafts = dbHandler.getPendingVisitDraftsList();
			return PendingDrafts;
		} catch (Exception e) {
			LOG.error("getPendingVisitDraftsList");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
}
