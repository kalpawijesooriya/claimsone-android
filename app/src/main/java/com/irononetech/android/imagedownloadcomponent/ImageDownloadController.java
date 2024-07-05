package com.irononetech.android.imagedownloadcomponent;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import com.irononetech.android.httpclient.DefaultHTTPClient;

public class ImageDownloadController extends IntentService {

	public static String MEDIA_URL = "";
	public static String MEDIA_SAVE_PATH = "";
	public static String MEDIA_SAVE_NAME = "";
	Logger LOG = LoggerFactory.getLogger(ImageDownloadController.class);

	
	public ImageDownloadController() {
		super("ImageDownloadController");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		MEDIA_URL = com.irononetech.android.Webservice.URL.getBaseUrl() + 
				com.irononetech.android.Webservice.URL.getImageDownloadUrlRemainder() + "&imageID=";
		//MEDIA_URL = "http://vsallaccess.victoriassecret.com/assets/main/iphone-home_rosie-662x640.jpg";
		Bundle extras = intent.getExtras();
		
		String mediaSavePath = "";
		String targetFileName = "";
		
		if(extras != null){
			mediaSavePath = extras.getString("MEDIA_SAVE_PATH");
			targetFileName = extras.getString("MEDIA_SAVE_NAME");  //name.jpg
		}
		
		//String mediaSavePath = intent.getStringExtra(MEDIA_SAVE_PATH);
		//String targetFileName = intent.getStringExtra(MEDIA_SAVE_NAME); //name.jpg

		File dir = new File(mediaSavePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		try {
			//2 other methods of doing this was commented on the bottom of the page
			String url = MEDIA_URL + targetFileName.replace(".jpg", "");
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");

				post.setURI(new URI(url));
				
				HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
				
					String PATH_op = mediaSavePath + targetFileName;
					FileOutputStream f = new FileOutputStream(new File(PATH_op));
					HttpEntity entity = response.getEntity();
				    InputStream instream = entity.getContent();
				
				     byte[] buffer = new byte[1024];
			            int len1 = 0;
			            while ( (len1 = instream.read(buffer)) > 0 ) {
			                f.write(buffer,0, len1);
			            }
			            f.close();
					
			} catch (UnsupportedEncodingException e) {
				 LOG.error("onHandleIntent:10164");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			} catch (URISyntaxException e) {
				 LOG.error("onHandleIntent:10165");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			} catch (ClientProtocolException e) {
				 LOG.error("onHandleIntent:10166");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			} catch (IOException e) {
				 LOG.error("onHandleIntent:10167");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}catch(Exception e){
				 LOG.error("onHandleIntent:10168");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
	}
}



