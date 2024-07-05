package com.irononetech.android.logincomponent.model;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.httpclient.DefaultHTTPClient;
import com.irononetech.android.template.GenException;

public class LoginWebService {

	Logger LOG = LoggerFactory.getLogger(LoginWebService.class);

	public String URL = com.irononetech.android.Webservice.URL.getBaseUrl()+"LogOn/LogOn?fmt=xml";


	public String getURL(){
		return URL;
	}
	public void setURL(String URL){
		this.URL=URL;
	}
	
	public String sendRequestForLogin(String url,String username, String password) throws GenException,Exception{
		
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("UserName", username));
			nvps.add(new BasicNameValuePair("Password", password));
			AbstractHttpEntity ent;
		
			ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			ent.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			ent.setContentEncoding("UTF-8");
			post.setEntity(ent);
			post.setURI(new URI(url));

			DefaultHTTPClient.disposeHTTPCLIENT();
			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
			String responseBody = EntityUtils.toString(response.getEntity());

			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				return responseBody;
			}else{
				LOG.info("Service Response is null");
				throw new GenException("", "Service Response is null");
			}			
		} catch (UnsupportedEncodingException e) {
			 LOG.error("excecute:10190");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (URISyntaxException e) {
			LOG.error("excecute:10191");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("excecute:10192");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		} catch (IOException e) {
			LOG.error("excecute:10193");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}catch(Exception e){
			LOG.error("excecute:10194");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			throw new GenException("", e.getMessage());
		}
	}
}