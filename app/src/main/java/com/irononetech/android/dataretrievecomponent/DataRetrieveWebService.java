package com.irononetech.android.dataretrievecomponent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class DataRetrieveWebService {

	Logger LOG = LoggerFactory.getLogger(DataRetrieveWebService.class);

	public String getJobOrVisitDataByUsingVisitId(String url, String vId) throws GenException,Exception {
		try {
			HttpPost post = new HttpPost(url);	// "http://172.21.0.87:5656/Job/SearchByVehicleNo?fmt=xml");
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("visitId", vId));
			AbstractHttpEntity ent;
		
			ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			ent.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			ent.setContentEncoding("UTF-8");
			post.setEntity(ent);
			post.setURI(new URI(url));	// "http://172.21.0.87:5353/LogOn/LogOn?fmt=xml"));
			
			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
			
			String responseBody = EntityUtils.toString(response.getEntity());
			
			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				return responseBody;
			}else{
				LOG.info("EXCEPTION getJobOrVisitDataByUsingVisitId:10086");				
				throw new GenException("", "Service Response is null");
			}

		} catch (UnsupportedEncodingException e) {
			LOG.error("getJobOrVisitDataByUsingVisitId:10085");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (URISyntaxException e) {
			LOG.error("getJobOrVisitDataByUsingVisitId:10086");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("getJobOrVisitDataByUsingVisitId:10087");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (IOException e) {
			LOG.error("getJobOrVisitDataByUsingVisitId:10088");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}catch(Exception e){
			LOG.error("getJobOrVisitDataByUsingVisitId:10089");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}
}