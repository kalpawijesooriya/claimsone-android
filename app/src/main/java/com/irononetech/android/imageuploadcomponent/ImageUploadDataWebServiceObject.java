package com.irononetech.android.imageuploadcomponent;

import java.util.ArrayList;
import com.irononetech.android.Webservice.WebServiceObject;

public class ImageUploadDataWebServiceObject extends WebServiceObject {
	private ArrayList<String> imageData;
	private int uploadCount;

	public int getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(int uploadCount) {
		this.uploadCount = uploadCount;
	}

	public ArrayList<String> getImageData() {
		return imageData;
	}

	public void setImageData(ArrayList<String> imageData) {
		this.imageData = imageData;
	}
}
