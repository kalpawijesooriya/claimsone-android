package com.irononetech.android.imageuploadcomponent;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ListenerWeb;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.database.DBService;
import com.irononetech.android.template.Controller;
import com.irononetech.android.template.DataObject;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.EventParcel;

public class ImageUploadController extends Controller implements ListenerWeb {
	Logger LOG = LoggerFactory.getLogger(ImageUploadController.class);

	@Override
	public void dataValidation(DataObject dataObject) throws GenException {
	}

	@Override
	public void doAction(EventParcel eventParcel) throws Exception,
			GenException {

		ArrayList<String> pendingJobsList = DBService.getPendingJobNoList();
		for (int j = 0; j < pendingJobsList.size(); j++) {
			ArrayList<ArrayList<String>> jobImages = DBService
					.getPendingImagesForJobNo(pendingJobsList.get(j));

			for (int i = 0; i < jobImages.size(); i++) {
				//LogFile.d("wowowowowowow",""+jobImages.size()+"ioioio");
				ImageUploadDataWebServiceObject imageUploadDataWebServiceObject = new ImageUploadDataWebServiceObject();
				imageUploadDataWebServiceObject.setImageData(jobImages.get(i));
				//LogFile.d("wowowowowowow",""+jobImages.size()+jobImages.get(i)+"xoxoxo");
				imageUploadDataWebServiceObject.setUploadCount(0);
				ServiceHandler imageUploadDataServiceHandler = new ImageUploadDataServiceHandler(
						imageUploadDataWebServiceObject);
				imageUploadDataServiceHandler.addListener(this,
						WebEvent.IMAGE_UPLOAD);
				imageUploadDataServiceHandler.runThread();
			}
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) throws Exception {

		if (webServiceObject.getXmlText() == null) {
		} else {
			try {
				XMLHandler.getResponse(webServiceObject);
			} catch (Exception e) {
				 LOG.error("listenerCallbackWeb");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			String status = null;
			try {
				status = webServiceObject.getCode();
			} catch (Exception e1) {
				 LOG.error("listenerCallbackWeb");
				   if(e1 != null){
					LOG.error("Message: " + e1.getMessage());
					LOG.error("StackTrace: " + e1.getStackTrace());
				  }
			}

			try {
				if (status.equalsIgnoreCase("0")) {
					ArrayList<String> imageData = ((ImageUploadDataWebServiceObject) webServiceObject)
							.getImageData();
					String jobNo = imageData.get(0);
					String field = imageData.get(1);
					String imagePath = imageData.get(2);
					//String resubmit = imageData.get(3);  //no need
					//String refNo = imageData.get(4);
					String imgStatus = "pending";
					String[] imageDetails = new String[4];
					imageDetails[0] = jobNo;
					imageDetails[1] = field;
					imageDetails[2] = imagePath;
					imageDetails[3] = imgStatus;
					//imageDetails[4] = resubmit;
					//imageDetails[5] = refNo;
					DBService.updateImageGal(imageDetails);  //deletes the image data from the db
				} else if (((ImageUploadDataWebServiceObject) webServiceObject)
						.getUploadCount() < 3) {
					int upcount = ((ImageUploadDataWebServiceObject) (webServiceObject))
							.getUploadCount();
					((ImageUploadDataWebServiceObject) (webServiceObject))
							.setUploadCount(upcount++);
					ServiceHandler imageUploadDataServiceHandler = new ImageUploadDataServiceHandler(
							((ImageUploadDataWebServiceObject) webServiceObject));
					imageUploadDataServiceHandler.addListener(this,
							WebEvent.IMAGE_UPLOAD);
					imageUploadDataServiceHandler.runThread();
				} else {
				}
			} catch (Exception e) {
				 LOG.error("listenerCallbackWeb");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		// Include each image upload response work.
		// Return webserviceobject with jobno:, field: , Imagepath: and upload
		// status.
		// Check the upload status and upload the status in db of that image. ,
	}
}
