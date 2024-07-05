package com.irononetech.android.Webservice;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.irononetech.android.httpclient.DefaultHTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebService {

	static Logger LOG = LoggerFactory.getLogger(WebService.class);


	public static String[] callWebService(String jobno, int timeOutVal)
	{
		try {
			/*final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://172.21.3.7:8090/WebService.asmx";
		final String SOAP_ACTION = "http://tempuri.org/GetJobInfo";
		final String METHOD_NAME = "GetJobInfo";*/

			//"http://tab.motor.lk/";
			final String NAMESPACE = com.irononetech.android.Webservice.URL.getSLICWebServiceNameSpace();
			//"http://122.255.4.186:10452/MotorTabWS2/MotorTabService";
			final String URL = com.irononetech.android.Webservice.URL.getSLICWebServiceURL();
			//"http://tab.motor.lk/getPolicyInfo";
			final String SOAP_ACTION = com.irononetech.android.Webservice.URL.getSLICWebServiceSOAPAction();
			//"getPolicyInfo";
			final String METHOD_NAME = com.irononetech.android.Webservice.URL.getSLICWebServiceMethodName();

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			//request.addProperty("jobno", jobno);
			request.addProperty("arg0", jobno);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

			//envelope.dotNet = true;

			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, timeOutVal);

			int inc = 0;
			while (inc < 3) {  // Retry 3 times
				try {
					inc++;
					//to get the XML response uncomment this line
					//androidHttpTransport.debug=true;
					androidHttpTransport.call(SOAP_ACTION, envelope);
					SoapObject response = (SoapObject) envelope.getResponse();

					LOG.info("callWebService - WSResponse: " + response.toString());

					// If you interested in XML parser, use this code to get the XML response
					//String xml = androidHttpTransport.responseDump;
					//Object response = envelope.getResponse();

					int elementCount = response.getPropertyCount();
					if (elementCount > 0) {

						//for(int i = 0;i<elementCount;i++){
						//element = response.getProperty(i).toString();
						//}
						String[] arr = {
								response.getPropertyAsString("callerName").replace("anyType{}", ""),
								response.getPropertyAsString("insuredName").replace("anyType{}", ""),
								response.getPropertyAsString("jobNo").replace("anyType{}", ""),
								response.getPropertyAsString("location").replace("anyType{}", ""),
								response.getPropertyAsString("policeStation").replace("anyType{}", ""),
								response.getPropertyAsString("policycoverNoteNo").replace("anyType{}", ""),
								response.getPropertyAsString("timeReported").replace("anyType{}", "")};
						return arr;
					}
				} catch (Exception e) {
					LOG.error("callWebService:10582");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }	continue;
				}
				break;
			}
		}catch (Exception e) {
			LOG.error("callWebService:10583");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return null;
	}

	//This connected to our web not SLIC web service. 
	public static String getAllComments(String jobNo) {
		try {
			String url = URL.getBaseUrl() + URL.getAllCommentsUsingJobNoUrlRemainder();
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("jobNo", jobNo));

			AbstractHttpEntity ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			ent.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			ent.setContentEncoding("UTF-8");
			post.setEntity(ent);
			post.setURI(new URI(url));

			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(post);
			String responseBody = EntityUtils.toString(response.getEntity());
			
			//LogFile.d("\nINFO ", "GetAllComments: " + responseBody + "\n");
			
			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				return responseBody;
			}else{
				LOG.info("Service Response is null");
				return "";
			}

		} catch (UnsupportedEncodingException e) {
			LOG.error("getAllComments:10392");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (URISyntaxException e) {
			LOG.error("getAllComments:10394");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (ClientProtocolException e) {
			LOG.error("getAllComments:10600");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (IOException e) {
			LOG.error("getAllComments:10601");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}catch(Exception e){
			LOG.error("getAllComments:10397");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return "";
	}

	//This connected to our web not SLIC web service.
	public static String getAllVisitStatus(String usrname, String date, String stat) {
		try {
			String url = URL.getBaseUrl() + URL.getAllVisitStatusesUrlRemainder();
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("User-Agent", "Apache-HttpClient/4.1 (java 1.5)");
			post.setHeader("Host", "motortab.srilankainsurance.com");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Username", usrname));
			nvps.add(new BasicNameValuePair("Date", date));
			nvps.add(new BasicNameValuePair("Status", stat));

			AbstractHttpEntity ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
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
				return "";
			}
		} catch (Exception e) {
			LOG.error("getAllVisitStatus:11397");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return "";
	}

	//This connected to our web not SLIC web service.
	public static String getDraftMaxReached() {
		try {
			String url = URL.getBaseUrl() + URL.getGetMaximumDraftCountUrl();
			HttpGet request = new HttpGet(url);

			request.setURI(new URI(url));

			HttpResponse response = DefaultHTTPClient.getHTTPCLIENTInstance().execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());

			if(responseBody != null && !responseBody.trim().equalsIgnoreCase("")){
				return responseBody;
			}else{
				LOG.info("Service Response is null");
				return "";
			}
		} catch (Exception e) {
			LOG.error("getDraftMaxReached:");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
		return "";
	}


}
