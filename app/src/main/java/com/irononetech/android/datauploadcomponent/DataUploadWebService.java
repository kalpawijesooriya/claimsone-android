package com.irononetech.android.datauploadcomponent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class DataUploadWebService {
	Logger LOG = LoggerFactory.getLogger(DataUploadWebService.class);

	public String dataUpload(String url, String xmlJobToPass) throws Exception,
			GenException {

		try {
			String xmlToPass = xmlJobToPass.replace("&", "and");
			HttpPost httppost = new HttpPost(url);		// "http://172.21.0.87:5656/Job/AddJob?fmt=xml");
		
			StringEntity se = new StringEntity(xmlToPass, HTTP.UTF_8);
			se.setContentType("text/xml");
			httppost.setEntity(se);

			HttpResponse httpresponse = DefaultHTTPClient
					.getHTTPCLIENTInstance().execute(httppost);

			LOG.info("DataUploadExecuted");
			String responseBody = EntityUtils
					.toString(httpresponse.getEntity());

			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				//LogFile.d("\n\nINFO ", "ResBody!=NULL");
				return responseBody;
			}else{
				LOG.info("dataUpload:11097");
				throw new GenException("", "Service Response is null");
			}	

		} catch (UnsupportedEncodingException e) {
			LOG.error("dataUpload:11098");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("dataUpload:11099");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (IOException e) {
			LOG.error("dataUpload:11100");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}catch(Exception e){
			LOG.error("dataUpload:11101");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}
}
