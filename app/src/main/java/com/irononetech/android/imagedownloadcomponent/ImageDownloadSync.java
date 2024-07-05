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
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import com.irononetech.android.httpclient.DefaultHTTPClient;

public class ImageDownloadSync {
	
	static Logger LOG = LoggerFactory.getLogger(ImageDownloadSync.class);
											// path					name without .jpg
	public static boolean onHandleImageDownload(String MEDIA_SAVE_PATH, String imgName) {
		
		String MEDIA_URL = com.irononetech.android.Webservice.URL.getBaseUrl() + 
				com.irononetech.android.Webservice.URL.getImageDownloadUrlRemainder() + "&imageID=";
		//MEDIA_URL = "http://vsallaccess.victoriassecret.com/assets/main/iphone-home_rosie-662x640.jpg";
		
		String mediaSavePath = MEDIA_SAVE_PATH;
		File dir = new File(mediaSavePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		String targetFileName = imgName + ".jpg";
		
		try {
			String url = MEDIA_URL + imgName;
			
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
				
			return true;			            
			} catch (UnsupportedEncodingException e) {
				 LOG.error("onHandleImageDownload:10169");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
			
			} catch (URISyntaxException e) {
				 LOG.error("onHandleImageDownload:10170");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
				
			} catch (ClientProtocolException e) {
				 LOG.error("onHandleImageDownload:10171");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
				
			} catch (IOException e) {
				 LOG.error("onHandleImageDownload:10172");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
				
			}catch(Exception e){
				 LOG.error("onHandleImageDownload:10173");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
			}
	}

	
	// path					name without .jpg
	public static boolean bulkImageDownload(ArrayList<String> toBeDownloaded, String MEDIA_SAVE_PATH) {
		
		if(toBeDownloaded != null && toBeDownloaded.size() > 0){
			
			String MEDIA_URL = com.irononetech.android.Webservice.URL.getBaseUrl() + 
					com.irononetech.android.Webservice.URL.getImageDownloadUrlRemainder() + "&imageID=";
			
			String mediaSavePath = "";
			String targetFileName = "";
			String url = "";
			HttpResponse response;
			String PATH_op = "";
			FileOutputStream f;
			HttpEntity entity;
			InputStream instream;
			byte[] buffer;
			
			mediaSavePath = MEDIA_SAVE_PATH;

			File dir = new File(mediaSavePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			try {
			for (int i = 0; i < 1/*toBeDownloaded.size()*/; i++) {
				
				targetFileName = toBeDownloaded.get(i).split("/")[7];
				url = MEDIA_URL + targetFileName.replace(".jpg", "");
					
					HttpPost post = new HttpPost(url);
					post.setHeader("Accept", "application/json");
					post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
					post.setHeader("Host", "motortab.srilankainsurance.com");
					
					post.setURI(new URI(url));
						
						response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
						
						PATH_op = mediaSavePath + targetFileName;
						f = new FileOutputStream(new File(PATH_op));
						entity = response.getEntity();
						instream = entity.getContent();
						
						buffer = new byte[1024];
					    int len1 = 0;
					       while ( (len1 = instream.read(buffer)) > 0 ) {
					           f.write(buffer,0, len1);
					       }
					    f.close();
					}
					return true;
				
				} catch (UnsupportedEncodingException e) {
					 LOG.error("bulkImageDownload:10171");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					return false;
					
				} catch (URISyntaxException e) {
					 LOG.error("bulkImageDownload:10172");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					return false;
						
				} catch (ClientProtocolException e) {
					 LOG.error("bulkImageDownload:10173");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					return false;
					
				} catch (IOException e) {
					 LOG.error("bulkImageDownload:10174");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					return false;
						
				}catch(Exception e){
					 LOG.error("bulkImageDownload:10175");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
					return false;
				}
		}
	return true;
	}
}