package com.irononetech.android.imageuploadcomponent;

import java.io.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.database.DBService;
import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class AsyncImgUploder implements Runnable {

	public static boolean continues = true;
	Logger LOG = LoggerFactory.getLogger(AsyncImgUploder.class);

	@Override
	public void run() {
		try {
			try {
				LOG.info("AsyncImgUploder Initiated OK.");
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				LOG.error("bulkImageDownload:10172");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

			while (continues) {
				ArrayList<String> pendingJobsList = DBService
						.getPendingJobNoList();

				if (pendingJobsList == null || pendingJobsList.size() <= 0) {
					// continues = false;
					// break;
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						 LOG.error("InterruptedEXCEPTION10173");
						   if(e != null){
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						  }
						}
					continue;
				}

				for (int j = 0; j < pendingJobsList.size(); j++) {
					ArrayList<ArrayList<String>> jobImages = DBService
							.getPendingImagesForJobNo(pendingJobsList.get(j));

					if (jobImages == null || jobImages.size() <= 0) {
						// continues = false;
						// break;
						continue;
					}

					for (int i = 0; i < jobImages.size(); i++) {
						excecute(jobImages.get(i));
					} // 4 jobImages
				} // 4 pendingJobsList

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					 LOG.error("InterruptedEXCEPTION10174");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}

			}// while(true)
		} catch (Exception e) {
			 LOG.error("Threadrun:10175");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}// run

	private synchronized void excecute(ArrayList<String> eData) {
		try {
			String url = URL.getBaseUrl() + URL.getImageUploadUrlRemainder();
			ArrayList<String> imageData = eData;
			String visitId = imageData.get(0);
			String imgType = imageData.get(1);
			String imgPath = imageData.get(2);
			String isResubmission = imageData.get(3);
			String refNo = imageData.get(4);

			String[] imgSplit = imgPath.split("/");
			String imgName = imgSplit[imgSplit.length - 1];
			int imgTypeId = ImageUploadEnums.valueOf(imgType).getInt(); // imageUploadEnums();

			String imgDataXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
					+ "<Request>" + "<Data>" + "<Image>" + "<FieldList>"
					+ "<visitId>" + visitId + "</visitId>" + "<imageName>"
					+ imgName + "</imageName>" + "<imageType>" + imgTypeId
					+ "</imageType>" + "<isResubmission>" + isResubmission
					+ "</isResubmission>" + "<refNo>" + refNo + "</refNo>"
					+ "</FieldList>" + "</Image>" + "<DateTime></DateTime>"
					+ "</Data></Request>";

			LOG.info("ImageXML: " + imgDataXml);

			String[] imageDetails = new String[4];
			imageDetails[0] = visitId;
			imageDetails[1] = imgType;
			imageDetails[2] = imgPath;
			imageDetails[3] = "pending"; // case sensitive

			//2015-08-04
			//Suren Manawatta
			//Check for the existence of the image. If image can't be found
			//it will be removed from the db anyway.
			File file = new File(imgPath);
			if(!file.exists()){
				LOG.error("excecute:10999 Image not exist, remove from db \n" + imgPath);
				DBService.updateImageGal(imageDetails);
				return;
			}
			
			// try to send the image for 3 times
			int x = 0;
			while (x < 3) {
				if (imageUpload(url, imgDataXml, imgPath, imgName)) {
					// If image uploaded successfully image record will remove
					// from the db
					DBService.updateImageGal(imageDetails);
					break;
				} else {
					x++;
					continue;
				}
			}
		} catch (GenException e) {
			 LOG.error("excecute:10176");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			 LOG.error("excecute:10555");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public synchronized boolean imageUpload(String url, String imageDataXml,
			String imgPath, String imageName) throws GenException, Exception {
		try {
			File file = new File(imgPath);

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					imageDataXml.getBytes());
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			reqEntity.addPart("ImageData", new InputStreamBody(
					byteArrayInputStream, "text/xml", "file.xml"));
			reqEntity.addPart("Image", new InputStreamBody(new FileInputStream(
					file), "image/jpeg", imageName));

			HttpPost httpPost = new HttpPost(url);

			// set authentication information here
			// httpPost.setHeader("Accept-Charset", "UTF-8");

			httpPost.setEntity(reqEntity);

			HttpClient d = DefaultHTTPClient.getHTTPCLIENTInstance();
			HttpResponse response = d.execute(httpPost);
			// int responseCode = response.getStatusLine().getStatusCode();

			// DefaultHttpClient d = new DefaultHttpClient();
			// HttpResponse response = d.execute(httpPost);
			// int responseCode = response.getStatusLine().getStatusCode();

			String responseBody = EntityUtils.toString(response.getEntity());
			LOG.info("Image Upload Response: " + responseBody);
			
			String newFileName = readXMLResponse(responseBody);

			if (!newFileName.isEmpty()) { // update db only img is success and
											// image name is fine
				fileRenamer(imgPath, imageName, newFileName);
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			 LOG.error("imageUpload:10177");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		} catch (Exception e) {
			 LOG.error("imageUpload:10178");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;

		}
		/*
		 * finally { if (in != null) { try { in.close(); } catch (IOException e)
		 * { LogFile.d("\n\nImageUploadException3: ", ""); return false;
		 * 
		 * } } }
		 */
	}

	// send the names with the extension
	private void fileRenamer(String oldnamewithpath, String oldName,
			String newName) {
		try {
			File oldFile = new File(oldnamewithpath);
			String newFileWithPath = oldnamewithpath.replace(oldName, newName);
			File newFile = new File(newFileWithPath);
			oldFile.renameTo(newFile);

		
			 LOG.info("ImageResponseRename: " + oldnamewithpath	+ " | NewName: " + newName);
		} catch (Exception e) {
			 LOG.error("fileRenamer:10178");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	// return image name with the extension
	private String readXMLResponse(String responseBody) {
		try {
			String statusCode = "";
			// String description = "";
			String imgNewName = "";

			String responseXML = responseBody;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("Status");

			for (int i = 0; i < items.getLength(); i++) {
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase("code")) {
						statusCode = property.getFirstChild().getNodeValue();
						// webServiceObject.setCode(codeValue);
					}
					/*
					 * else if (name.equalsIgnoreCase("description")) {
					 * description = property.getFirstChild().getNodeValue();
					 * //webServiceObject.setDescription(disValue); }
					 */
					if (name.equalsIgnoreCase("imageId")) {
						imgNewName = property.getFirstChild().getNodeValue();
					}
				}
			}

			if (statusCode.equals("0")) {
				imgNewName = imgNewName.concat(".jpg");
			} else {
				imgNewName = "";
			}
			return imgNewName;
		} catch (Exception e) {
			 LOG.error("readXMLResponse:11408");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}
} // class
