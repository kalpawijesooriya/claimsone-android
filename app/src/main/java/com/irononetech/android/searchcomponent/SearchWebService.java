package com.irononetech.android.searchcomponent;

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
import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchWebService {

	static Logger LOG = LoggerFactory.getLogger(SearchWebService.class);


	public String searchByVehicleNo(String url, String vehicleNo) throws GenException,Exception {

		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
		post.setHeader("Host", "motortab.srilankainsurance.com");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("searchText", vehicleNo));

		String erMsg = "Network Failed!\n";
		
		AbstractHttpEntity ent;
		try {
			ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			ent.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			ent.setContentEncoding("UTF-8");
			post.setEntity(ent);
			post.setURI(new URI(url));
			
			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
			String responseBody = EntityUtils.toString(response.getEntity());

			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				return responseBody;
			}else{
				LOG.info("Service Response is null");
				throw new GenException("", "Service Response is null");
			}

		} catch (UnsupportedEncodingException e) {
			LOG.error("searchByVehicleNo:10392");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Error 116", erMsg + e.getMessage());

		} catch (URISyntaxException e) {
			LOG.error("searchByVehicleNo:10394");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Error 117", erMsg + e.getMessage());

		} catch (ClientProtocolException e) {
			LOG.error("searchByVehicleNo:10600");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Error 118", erMsg + e.getMessage());

		} catch (IOException e) {
			LOG.error("searchByVehicleNo:10601");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Error 119", erMsg + e.getMessage());

		}catch(Exception e){
			LOG.error("searchByVehicleNo:10397");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("" + "Error 120", erMsg + e.getMessage());
		}
	}
}