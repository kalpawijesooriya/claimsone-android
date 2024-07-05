package com.irononetech.android.formcomponent;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.database.DBService;
import com.irononetech.android.datauploadcomponent.DataUploadController;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.ListenerUI;

public class FormController extends Controller implements ListenerUI {
	Logger LOG = LoggerFactory.getLogger(FormController.class);
	EventParcel eventParcel;

	public FormController() {
		super();
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,	GenException {
		int netImageCount = 0;
		String SubmissionType = "";
		
		try {
			this.eventParcel = eventParcel;
			if(!((FormObject) eventParcel.getDataObject()).getisSEARCH()){
			String filepath = URL.getSLIC_JOBS()
					+ ((FormObject) eventParcel.getDataObject()).getJobNo()
					+ "/AccImages";
			ArrayList<String> fileList = FileOperations.getFileList(filepath);
			((FormObject) eventParcel.getDataObject())
					.setAccidentImageList(fileList);
			((FormObject) eventParcel.getDataObject())
			.setAccidentImageListCount(fileList.size());
			} else{
				((FormObject) eventParcel.getDataObject())
				.setAccidentImageList(null);
			}
			
			//---------------------------------------------------------------------------------------------------------
			String filepathDocs = URL.getSLIC_JOBS()
					+ ((FormObject) eventParcel.getDataObject()).getJobNo()
					+ "/DocImages/";
			ArrayList<String> dirs = null;

			//folder filter
			File fil = new File(filepathDocs);
			if(fil.length() > 0){
			File[] files = fil.listFiles();
			dirs = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()){
					dirs.add(files[i].getName());
				}
			}
			//---------------------------------------------------------------------------------------------------------

			if (!dirs.isEmpty()){
			if(dirs.contains("DLStatement")){
				ArrayList<String> fileListDLStatement;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListDLStatement = ((FormObject) eventParcel.getDataObject()).getDLStatementImageList();
				}else{
					fileListDLStatement = FileOperations.getFileList(filepathDocs+"DLStatement/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setDLStatementImageList(FileOperations.getListComparison(fileListDLStatement, Application.createFormResubmitObjectInstance().getDLStatementImageList()));
					if(((FormObject) eventParcel.getDataObject()).getDLStatementImageList() != null && ((FormObject) eventParcel.getDataObject()).getDLStatementImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getDLStatementImageList().size();
						SubmissionType = SubmissionType + "DLStatement, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setDLStatementImageList(fileListDLStatement);
					((FormObject) eventParcel.getDataObject())
					.setDLStatementImageListCount(fileListDLStatement.size());
				}
			}
			if(dirs.contains("TechnicalOfficerComments")){
				ArrayList<String> fileListTechnicalOfficerComments;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListTechnicalOfficerComments = ((FormObject) eventParcel.getDataObject()).getTechnicalOfficerCommentsImageList();
				}else{
					fileListTechnicalOfficerComments = FileOperations.getFileList(filepathDocs+"TechnicalOfficerComments/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setTechnicalOfficerCommentsImageList(FileOperations.getListComparison(fileListTechnicalOfficerComments, Application.createFormResubmitObjectInstance().getTechnicalOfficerCommentsImageList()));
					if(((FormObject) eventParcel.getDataObject()).getTechnicalOfficerCommentsImageList() != null && ((FormObject) eventParcel.getDataObject()).getTechnicalOfficerCommentsImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getTechnicalOfficerCommentsImageList().size();
						SubmissionType = SubmissionType + "TechnicalOfficerComments, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setTechnicalOfficerCommentsImageList(fileListTechnicalOfficerComments);
					((FormObject) eventParcel.getDataObject())
					.setTechnicalOfficerCommentsImageListCount(fileListTechnicalOfficerComments.size());
				}
			}
			if(dirs.contains("ClaimFormImage")){
				ArrayList<String> fileListClaimFormImage;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListClaimFormImage = ((FormObject) eventParcel.getDataObject()).getClaimFormImageImageList();
				}else{
					fileListClaimFormImage = FileOperations.getFileList(filepathDocs+"ClaimFormImage/");
				}

				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setClaimFormImageImageList(FileOperations.getListComparison(fileListClaimFormImage, Application.createFormResubmitObjectInstance().getClaimFormImageImageList()));
					if(((FormObject) eventParcel.getDataObject()).getClaimFormImageImageList() != null && ((FormObject) eventParcel.getDataObject()).getClaimFormImageImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getClaimFormImageImageList().size();
						SubmissionType = SubmissionType + "ClaimFormImage, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setClaimFormImageImageList(fileListClaimFormImage);
					((FormObject) eventParcel.getDataObject())
					.setClaimFormImageImageListCount(fileListClaimFormImage.size());
				}
			}
			if(dirs.contains("ARI")){
				ArrayList<String> fileListARI;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListARI = ((FormObject) eventParcel.getDataObject()).getARIImageList();
				}else{
					fileListARI = FileOperations.getFileList(filepathDocs+"ARI/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setARIImageList(FileOperations.getListComparison(fileListARI, Application.createFormResubmitObjectInstance().getARIImageList()));
					if(((FormObject) eventParcel.getDataObject()).getARIImageList() != null && ((FormObject) eventParcel.getDataObject()).getARIImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getARIImageList().size();
						SubmissionType = SubmissionType + "ARI, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setARIImageList(fileListARI);
					((FormObject) eventParcel.getDataObject())
					.setARIImageListCount(fileListARI.size());
				}
			}
			if(dirs.contains("DR")){
				ArrayList<String> fileListDR;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListDR = ((FormObject) eventParcel.getDataObject()).getDRImageList();
				}else{
					fileListDR = FileOperations.getFileList(filepathDocs+"DR/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setDRImageList(FileOperations.getListComparison(fileListDR, Application.createFormResubmitObjectInstance().getDRImageList()));
					if(((FormObject) eventParcel.getDataObject()).getDRImageList() != null && ((FormObject) eventParcel.getDataObject()).getDRImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getDRImageList().size();
						SubmissionType = SubmissionType + "DR, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setDRImageList(fileListDR);
					((FormObject) eventParcel.getDataObject())
					.setDRImageListCount(fileListDR.size());
				}
			}
			if(dirs.contains("SeenVisit")){
				ArrayList<String> fileListSeenVisit;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSeenVisit = ((FormObject) eventParcel.getDataObject()).getSeenVisitImageList();
				}else{
					fileListSeenVisit = FileOperations.getFileList(filepathDocs+"SeenVisit/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSeenVisitImageList(FileOperations.getListComparison(fileListSeenVisit, Application.createFormResubmitObjectInstance().getSeenVisitImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSeenVisitImageList() != null && ((FormObject) eventParcel.getDataObject()).getSeenVisitImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSeenVisitImageList().size();
						SubmissionType = SubmissionType + "SeenVisit, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSeenVisitImageList(fileListSeenVisit);
					((FormObject) eventParcel.getDataObject())
					.setSeenVisitImageListCount(fileListSeenVisit.size());
				}
			}
			if(dirs.contains("SpecialReport1")){
				ArrayList<String> fileListSpecialReport1;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSpecialReport1 = ((FormObject) eventParcel.getDataObject()).getSpecialReport1ImageList();
				}else{
					fileListSpecialReport1 = FileOperations.getFileList(filepathDocs+"SpecialReport1/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport1ImageList(FileOperations.getListComparison(fileListSpecialReport1, Application.createFormResubmitObjectInstance().getSpecialReport1ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSpecialReport1ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSpecialReport1ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSpecialReport1ImageList().size();
						SubmissionType = SubmissionType + "SpecialReport1, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport1ImageList(fileListSpecialReport1);
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport1ImageListCount(fileListSpecialReport1.size());
				}
			}
			if(dirs.contains("SpecialReport2")){
				ArrayList<String> fileListSpecialReport2;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSpecialReport2 = ((FormObject) eventParcel.getDataObject()).getSpecialReport2ImageList();
				}else{
					fileListSpecialReport2 = FileOperations.getFileList(filepathDocs+"SpecialReport2/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport2ImageList(FileOperations.getListComparison(fileListSpecialReport2, Application.createFormResubmitObjectInstance().getSpecialReport2ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSpecialReport2ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSpecialReport2ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSpecialReport2ImageList().size();
						SubmissionType = SubmissionType + "SpecialReport2, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport2ImageList(fileListSpecialReport2);
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport2ImageListCount(fileListSpecialReport2.size());
				}
			}
			if(dirs.contains("SpecialReport3")){
				ArrayList<String> fileListSpecialReport3;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSpecialReport3 = ((FormObject) eventParcel.getDataObject()).getSpecialReport3ImageList();
				}else{
					fileListSpecialReport3 = FileOperations.getFileList(filepathDocs+"SpecialReport3/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport3ImageList(FileOperations.getListComparison(fileListSpecialReport3, Application.createFormResubmitObjectInstance().getSpecialReport3ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSpecialReport3ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSpecialReport3ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSpecialReport3ImageList().size();
						SubmissionType = SubmissionType + "SpecialReport3, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport3ImageList(fileListSpecialReport3);
					((FormObject) eventParcel.getDataObject())
					.setSpecialReport3ImageListCount(fileListSpecialReport3.size());
				}
			}
			if(dirs.contains("Supplementary1")){
				ArrayList<String> fileListSupplementary1;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSupplementary1 = ((FormObject) eventParcel.getDataObject()).getSupplementary1ImageList();
				}else{
					fileListSupplementary1 = FileOperations.getFileList(filepathDocs+"Supplementary1/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSupplementary1ImageList(FileOperations.getListComparison(fileListSupplementary1, Application.createFormResubmitObjectInstance().getSupplementary1ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSupplementary1ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSupplementary1ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSupplementary1ImageList().size();
						SubmissionType = SubmissionType + "Supplementary1, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSupplementary1ImageList(fileListSupplementary1);
					((FormObject) eventParcel.getDataObject())
					.setSupplementary1ImageListCount(fileListSupplementary1.size());
				}
			}
			if(dirs.contains("Supplementary2")){
				ArrayList<String> fileListSupplementary2;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSupplementary2 = ((FormObject) eventParcel.getDataObject()).getSupplementary2ImageList();
				}else{
					fileListSupplementary2 = FileOperations.getFileList(filepathDocs+"Supplementary2/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSupplementary2ImageList(FileOperations.getListComparison(fileListSupplementary2, Application.createFormResubmitObjectInstance().getSupplementary2ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSupplementary2ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSupplementary2ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSupplementary2ImageList().size();
						SubmissionType = SubmissionType + "Supplementary2, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSupplementary2ImageList(fileListSupplementary2);
					((FormObject) eventParcel.getDataObject())
					.setSupplementary2ImageListCount(fileListSupplementary2.size());
				}
			}
			if(dirs.contains("Supplementary3")){
				ArrayList<String> fileListSupplementary3;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSupplementary3 = ((FormObject) eventParcel.getDataObject()).getSupplementary3ImageList();
				}else{
					fileListSupplementary3 = FileOperations.getFileList(filepathDocs+"Supplementary3/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSupplementary3ImageList(FileOperations.getListComparison(fileListSupplementary3, Application.createFormResubmitObjectInstance().getSupplementary3ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSupplementary3ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSupplementary3ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSupplementary3ImageList().size();
						SubmissionType = SubmissionType + "Supplementary3, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSupplementary3ImageList(fileListSupplementary3);
					((FormObject) eventParcel.getDataObject())
					.setSupplementary3ImageListCount(fileListSupplementary3.size());
				}
			}
			if(dirs.contains("Supplementary4")){
				ArrayList<String> fileListSupplementary4;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSupplementary4 = ((FormObject) eventParcel.getDataObject()).getSupplementary4ImageList();
				}else{
					fileListSupplementary4 = FileOperations.getFileList(filepathDocs+"Supplementary4/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSupplementary4ImageList(FileOperations.getListComparison(fileListSupplementary4, Application.createFormResubmitObjectInstance().getSupplementary4ImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSupplementary4ImageList() != null && ((FormObject) eventParcel.getDataObject()).getSupplementary4ImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSupplementary4ImageList().size();
						SubmissionType = SubmissionType + "Supplementary4, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSupplementary4ImageList(fileListSupplementary4);
					((FormObject) eventParcel.getDataObject())
					.setSupplementary4ImageListCount(fileListSupplementary4.size());
				}
			}
			if(dirs.contains("Acknowledgment")){
				ArrayList<String> fileListAcknowledgment;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListAcknowledgment = ((FormObject) eventParcel.getDataObject()).getAcknowledgmentImageList();
				}else{
					fileListAcknowledgment = FileOperations.getFileList(filepathDocs+"Acknowledgment/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setAcknowledgmentImageList(FileOperations.getListComparison(fileListAcknowledgment, Application.createFormResubmitObjectInstance().getAcknowledgmentImageList()));
					if(((FormObject) eventParcel.getDataObject()).getAcknowledgmentImageList() != null && ((FormObject) eventParcel.getDataObject()).getAcknowledgmentImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getAcknowledgmentImageList().size();
						SubmissionType = SubmissionType + "Acknowledgment, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setAcknowledgmentImageList(fileListAcknowledgment);
					((FormObject) eventParcel.getDataObject())
					.setAcknowledgmentImageListCount(fileListAcknowledgment.size());
				}
			}
			if(dirs.contains("SalvageReport")){
				ArrayList<String> fileListSalvageReport;
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					fileListSalvageReport = ((FormObject) eventParcel.getDataObject()).getSalvageReportImageList();
				}else{
					fileListSalvageReport = FileOperations.getFileList(filepathDocs+"SalvageReport/");
				}
				
				if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
					((FormObject) eventParcel.getDataObject())
					.setSalvageReportImageList(FileOperations.getListComparison(fileListSalvageReport, Application.createFormResubmitObjectInstance().getSalvageReportImageList()));
					if(((FormObject) eventParcel.getDataObject()).getSalvageReportImageList() != null && ((FormObject) eventParcel.getDataObject()).getSalvageReportImageList().size() > 0){
						netImageCount = netImageCount + ((FormObject) eventParcel.getDataObject()).getSalvageReportImageList().size();
						SubmissionType = SubmissionType + "SalvageReport, ";
					}
				}
				else{
					((FormObject) eventParcel.getDataObject())
					.setSalvageReportImageList(fileListSalvageReport);
					((FormObject) eventParcel.getDataObject())
					.setSalvageReportImageListCount(fileListSalvageReport.size());
				}
			}}
			}

			if(((FormObject) eventParcel.getDataObject()).getisSEARCH()){
				((FormObject) eventParcel.getDataObject()).setImageCount(netImageCount+"");
				((FormObject) eventParcel.getDataObject()).setResubmissionType(SubmissionType);
				LOG.info("\n\n RESUBMIT IMGCOUNT: " + netImageCount + "\nSubType: " + SubmissionType);
			}
//---------------------------------------------------------------------------------------------------------
			if(!((FormObject) eventParcel.getDataObject()).getisSEARCH()){
			
				String filepathImpact = URL.getSLIC_JOBS() + ((FormObject) eventParcel.getDataObject()).getJobNo() + "/PointsOfImpact";
				ArrayList<String> fileListImpact = FileOperations.getFileList(filepathImpact);
				((FormObject) eventParcel.getDataObject()).setPointOfImpactList(fileListImpact);
			} else{
				((FormObject) eventParcel.getDataObject()).setPointOfImpactList(null);
			}
			
			//LogFile.d("get a formobj ","serializeFormDataSTART " + 951753);
			
			FormObject formObject = Application.getFormObjectInstance();
			if(!formObject.getisSEARCH()){
				FormObjSerializer.serializeFormData(formObject);
				}
			//LogFile.d("get a formobj ","serializeFormDataEND " + 951753);
			
			//This is a useless function. 
			DBService.saveSAFormDetails(eventParcel);
			
			if(formObject.getisSEARCH()){
				FormReSubXMLCreator formXMLCreator = new FormReSubXMLCreator();
				String formXML = formXMLCreator.getFormXML((FormObject)eventParcel.getDataObject());
				((FormObject)eventParcel.getDataObject()).setDataXML(formXML);
				DataUploadController dataUploadController = new DataUploadController();
				dataUploadController.addListener(this);
				dataUploadController.doAction(eventParcel);
			} else{
				FormXMLCreator formXMLCreator = new FormXMLCreator();
				String formXML = formXMLCreator.getSAFormXML((FormObject)eventParcel.getDataObject());
				((FormObject)eventParcel.getDataObject()).setDataXML(formXML);
				DataUploadController dataUploadController = new DataUploadController();
				dataUploadController.addListener(this);
				dataUploadController.doAction(eventParcel);
			}
		}catch (GenException e) {
			LOG.error("doAction:10147");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw e;
		}
		catch (Exception e) {
			LOG.error("doAction:10148");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", "Data uploading failure!");
		}
	}

	@Override
	public void listenerUICallbackUI(EventParcel eventParcel) {
		/**
		 * on successful submission please delete current form object instance.
		 */
		// Please debug this. Since the eventparcel still has the
		// formobject. that might create issues.
		
		// Suren: yap, it's done. when i receive 0 as the response i'm setting the obj to null 
		// it's not done here it's on the Application class
		super.listenerCallback(eventParcel);
	}

	@Override
	public void dataValidation(DataObject dataObject)
			throws GenException {
	}
}
