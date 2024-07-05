package com.irononetech.android.imageuploadcomponent;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebEvent;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.template.GenException;

public class ImageUploadDataServiceHandler extends ServiceHandler {

Logger LOG = LoggerFactory.getLogger(ImageUploadDataServiceHandler.class);

	WebServiceObject webServiceObject;

	public ImageUploadDataServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
		this.webServiceObject = webServiceObject;
	}

	// DEPRICATED USE ASYNCIMGUPLODER
	@Override
	public void excecute() {
		try {
			// String url = "http://172.21.0.87:5656/Image/UploadImage?fmt=xml";
			String url = URL.getBaseUrl() + URL.getImageUploadUrlRemainder();
			ArrayList<String> imageData = ((ImageUploadDataWebServiceObject) (webServiceObject))
					.getImageData();
			String vstId = imageData.get(0);
			String imgType = imageData.get(1);
			String imgPath = imageData.get(2);
			String isResubmission = imageData.get(3);
			String refNo = imageData.get(4);
			String[] split = imgPath.split("/");
			String imgName = split[split.length - 1];
			int imageTypeId = ImageUploadEnums.valueOf(imgType).getInt(); // .imageUploadEnums();

			// String resubmission =
			// Application.createFormResubmitObjectInstance().getisSEARCH() +
			// "";
			// String refNo =
			// Application.createFormResubmitObjectInstance().getRefNo();

			String imgDataXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
					+ "<Request>" + "<Data>" + "<Image>" + "<FieldList>"
					+ "<visitId>" + vstId + "</visitId>" + "<imageName>"
					+ imgName + "</imageName>" + "<imageType>" + imageTypeId
					+ "</imageType>" + "<isResubmission>" + isResubmission
					+ "</isResubmission>" + "<refNo>" + refNo + "</refNo>"
					+ "</FieldList>" + "</Image>" + "<DateTime></DateTime>"
					+ "</Data></Request>";

			/*
			 * String imgDataXml =
			 * "<?xml version=\"1.0\" encoding=\"utf-8\" ?><Request>" +
			 * "<Data><Image><FieldList><jobNo>" + jobNo + "</jobNo><imageName>"
			 * + imgName + "</imageName><fieldId>" + fieldID +
			 * "</fieldId></FieldList>" +
			 * "</Image><DateTime></DateTime></Data></Request>";
			 */

			 LOG.info("\n\nImageXML "+ imgDataXml);

			ImageUploadWebService imageUploadWebService = new ImageUploadWebService();
			String xmlText = imageUploadWebService.imageUpload(url, imgDataXml,
					imgPath, imgName);
			if (xmlText != null) {
				webServiceObject.setXmlText(xmlText);
			} else {
				webServiceObject.setError("Response is null");
			}
		} catch (GenException e) {
			 LOG.error("EXE()1 " + e.getMessage());
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setError(e.getMessage());
		} catch (Exception e) {
			 LOG.error("EXE()2 " + e.getMessage());
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			webServiceObject.setError(e.getMessage());
		}
	}

	@Override
	public void listenerCallbackWeb(WebServiceObject webServiceObject,
			WebEvent webEvent) throws Exception, GenException {
		super.listenerCallbackWeb(webServiceObject, webEvent);
	}
}