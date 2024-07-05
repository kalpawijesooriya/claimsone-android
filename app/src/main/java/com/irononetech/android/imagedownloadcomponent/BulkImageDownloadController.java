package com.irononetech.android.imagedownloadcomponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import android.content.Intent;
import android.os.Bundle;

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.httpclient.DefaultHTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkImageDownloadController{

	public static String MEDIA_URL = "";
	Logger LOG = LoggerFactory.getLogger(BulkImageDownloadController.class);


	public void bulkImageDownloder(Intent intent) {
		try {
			MEDIA_URL = URL.getBaseUrl() + URL.getImageDownloadUrlRemainder();
			Bundle extras = intent.getExtras();

			String mediaSavePath = "";
			String targetFileName = "";
			ArrayList<String> imgList = null;
			String url = MEDIA_URL;
			HttpPost post;
			HttpResponse response;
			String PATH_op;
			FileOutputStream f;
			HttpEntity entity;
			InputStream instream;
			byte[] buffer;
			int len1 = 0;
			String[] imgPath;
			String finalUrl = "";

			if(extras != null){
				//Contains ArrayList<String> of urls of the images to be downloaded (image locations in the server not the android)
				imgList = (ArrayList<String>)intent.getStringArrayListExtra("IMG_LIST");
				//this defines where to save the downloaded image (in the android device)
				mediaSavePath = extras.getString("MEDIA_SAVE_PATH");
			}

			File dir = new File(mediaSavePath);
			if(!dir.exists()){
				dir.mkdirs();
			}

			if(imgList != null){
				for (int i = 0; i < imgList.size(); i++) {
					targetFileName = imgList.get(i);
					imgPath = targetFileName.split("/");
					targetFileName = imgPath[imgPath.length - 1];
					finalUrl = url + targetFileName.replace(".jpg", "");
					post = new HttpPost(finalUrl);
					post.setHeader("Accept", "application/json");

					post.setURI(new URI(finalUrl));
					response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);

					PATH_op = mediaSavePath + targetFileName;
					f = new FileOutputStream(new File(PATH_op));
					entity = response.getEntity();
					instream = entity.getContent();

					buffer = new byte[1024];
					len1 = 0;
					while ( (len1 = instream.read(buffer)) > 0 ) {
						f.write(buffer,0, len1);
					}

					f.flush();
					f.close();
					instream.close();
				}
			}
		}catch (UnsupportedEncodingException e) {
			 LOG.error("bulkImageDownloder:10159");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (URISyntaxException e) {
			 LOG.error("bulkImageDownloder:101620");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (ClientProtocolException e) {
			 LOG.error("bulkImageDownloder:10161");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (IOException e) {
			 LOG.error("bulkImageDownloder:10162");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}catch(Exception e){
			 LOG.error("bulkImageDownloder:10163");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
}