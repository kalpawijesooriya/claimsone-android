package com.irononetech.android.formcomponent;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.datauploadcomponent.DataUploadController;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.ListenerUI;

public class VisitController extends Controller implements ListenerUI {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	EventParcel eventParcel;
	
	public VisitController() {
		super();
	}
		
	@Override
	public void doAction(EventParcel eventParcel) throws Exception, GenException {
		try {
			this.eventParcel = eventParcel;

			//---------------------------------------------------------------------------------------------------------
			String filepathDocs = URL.getSLIC_VISITS()
					+ ((VisitObject) eventParcel.getDataObject()).getVisitFolderName()
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
					if(dirs.contains("EstimateAnyotherComments")){
						ArrayList<String> fileListEstimateAnyotherComments
						= FileOperations.getFileList(filepathDocs + "EstimateAnyotherComments/");
						
						((VisitObject) eventParcel.getDataObject()).
						setEstimateAnyotherCommentsImageList(fileListEstimateAnyotherComments);
						((VisitObject) eventParcel.getDataObject()).
						setEstimateAnyotherCommentsImageListCount(fileListEstimateAnyotherComments.size());
					}
					
					if(dirs.contains("TechnicalOfficerComments")){
						ArrayList<String> fileListTechnicalOfficerComments
						= FileOperations.getFileList(filepathDocs + "TechnicalOfficerComments/");
						
						((VisitObject) eventParcel.getDataObject()).
						setTechnicalOfficerCommentsImageList(fileListTechnicalOfficerComments);
						((VisitObject) eventParcel.getDataObject()).
						setTechnicalOfficerCommentsImageListCount(fileListTechnicalOfficerComments.size());
					}
					
					if(dirs.contains("InspectionPhotosSeenVisitsAnyOther")){
						ArrayList<String> fileListInspectionPhotosSeenVisitsAnyOther
						= FileOperations.getFileList(filepathDocs + "InspectionPhotosSeenVisitsAnyOther/");
						
						((VisitObject) eventParcel.getDataObject()).
						setInspectionPhotosSeenVisitsAnyOtherImageList(fileListInspectionPhotosSeenVisitsAnyOther);
						((VisitObject) eventParcel.getDataObject()).
						setInspectionPhotosSeenVisitsAnyOtherImageListCount(fileListInspectionPhotosSeenVisitsAnyOther.size());
					}
				}
			} //if
			
			FormXMLCreator formXMLCreator = new FormXMLCreator();
			String formXML = formXMLCreator.getVisitFormXML((VisitObject)eventParcel.getDataObject());
			((VisitObject)eventParcel.getDataObject()).setDataXML(formXML);
			DataUploadController dataUploadController = new DataUploadController();
			dataUploadController.addListener(this);
			dataUploadController.doAction(eventParcel);
			
		} catch (Exception e) {
			LOG.error("doAction:11148");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }

			throw new GenException("", "Data uploading failure!");
		}
	}
	
	@Override
	public void listenerUICallbackUI(EventParcel eventParcel) {
		super.listenerCallback(eventParcel);
	}

	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
	}

}
