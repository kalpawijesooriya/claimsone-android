package com.irononetech.android.imageuploadcomponent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class ImageUploadWebService {
	Logger LOG = LoggerFactory.getLogger(ImageUploadWebService.class);

	public String imageUpload(String url, String imageDataXml, String imgPath,
			String imageName) throws GenException,Exception {
		
		InputStream in = null;

		try {
			 LOG.info("\n\nImageUpload: " + "Initiated" + "\n");
			File file = new File(imgPath);
			
 			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					imageDataXml.getBytes());
			
			/* -- Old code
			in = new BufferedInputStream(new FileInputStream(file));
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					imageDataXml.getBytes());
			SimpleMultipartEntity en = new SimpleMultipartEntity();					
			en.addPartWithBoundry("ImageData", "file.xml", byteArrayInputStream, "text/xml");
			en.addPartWithBoundry("Image", imageName, in, "form-data");
			*/
						
			MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
		
            reqEntity.addPart("ImageData", new InputStreamBody(byteArrayInputStream, "text/xml", "file.xml" ));
            reqEntity.addPart("Image",  new InputStreamBody(new FileInputStream(file), "image/jpeg", imageName));
            //reqEntity.addPart("Image", new FileBody(file));
            			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(reqEntity);

			HttpClient d = DefaultHTTPClient.getHTTPCLIENTInstance();
			HttpResponse response = d.execute(httpPost);
			int responseCode = response.getStatusLine().getStatusCode();

			String responseBody = EntityUtils.toString(response.getEntity());
			LOG.info("\n\nImageUploadResponseBody: "+ responseBody + "\n");
			
			String newFileName = readXMLResponse(responseBody);
			
			if(responseCode == 200){
				fileRenamer(imgPath, imageName, newFileName);
			}else{
				LOG.info("\n\nERRPRresponseCode: " + responseCode + ":: "+ responseBody);
			}
			return responseBody;

		} catch (IOException e) {
			 LOG.error("\n\nimageUploadIOEXCEPTION " + "9685 ");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Err234", e.getMessage());
			
		} catch (Exception e) {
			 LOG.error("\n\nimageUploadEXCEPTION " + "44578 ");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Err23dd4", e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					 LOG.error("\n\nImageUploadIOException2: "+ "98798 ");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					throw new GenException("" + "Err255", e.getMessage());
				}
				//LogFile.d("\n\nImageUploadResponseBody: ", "98798852 ");
			}
		}
	}
	
	//send the names with the extension
	private void fileRenamer(String oldnamewithpath, String oldName, String newName){
		try{
			File oldFile = new File(oldnamewithpath);
			
			String newFileWithPath = oldnamewithpath.replace(oldName, newName);
			File newFile = new File(newFileWithPath);
			
			oldFile.renameTo(newFile);
			LOG.info("\n\nImageResponseRename: "+ oldnamewithpath + " NEWNAME " + newName);
		}catch (Exception e) {
			 LOG.error("Err34r");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	//return image name with the extension
	private String readXMLResponse(String responseBody){
		String disValue = "";

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseBody));
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);		// this.getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("Status");	// ITEM);
		
			for (int i = 0; i < items.getLength(); i++) {
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
	
					if (name.equalsIgnoreCase("imageId")) {
						disValue = property.getFirstChild()
								.getNodeValue();
					}
				}
			}
			
			if(responseBody.contains("<code>0</code>")){
				disValue = disValue.concat(".jpg");
			}else{
				disValue = "";
			}
			return disValue;
			
		  } catch (Exception e) {
				 LOG.error("\nreadXMLResponseEXCEPTION");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				
				return "";
		  }
	}
}