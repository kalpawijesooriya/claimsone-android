package com.irononetech.android.logoutcomponent;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class LogoutWebService {

Logger LOG = LoggerFactory.getLogger(LogoutWebService.class);


	public String sendRequestForLogout(String url) throws GenException,
			Exception {

		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");
		
			post.setURI(new URI(url));
			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance()
						.execute(post);

			String responseBody = EntityUtils.toString(response.getEntity());
			return responseBody;
		
		} catch (UnsupportedEncodingException e) {
			 LOG.error("doAction:10203");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());

		} catch (URISyntaxException e) {
			 LOG.error("doAction:10204");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());

		} catch (ClientProtocolException e) {
			 LOG.error("doAction:10205");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());

		} catch (IOException e) {
			 LOG.error("doAction:10206");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}
}