package com.irononetech.android.httpclient;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class DefaultHTTPClient {
	private static HttpClient HTTPCLIENT;
	static Logger LOG = LoggerFactory.getLogger(DefaultHTTPClient.class);

	
	private DefaultHTTPClient() {
	}
	
	public static synchronized HttpClient getHTTPCLIENTInstance() {
		if(HTTPCLIENT==null){
			try {
				//Create HttpClient for the application
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
				//HttpParams httpParams = new BasicHttpParams();
				//HttpConnectionParams.setConnectionTimeout(httpParams, 19000);
				//HttpConnectionParams.setSoTimeout(httpParams, 20000);
				HttpParams parameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(parameters, 59000);
				HttpConnectionParams.setSoTimeout(parameters, 60000);
				ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(parameters, schemeRegistry);
				HTTPCLIENT = new DefaultHttpClient(connectionManager, parameters);
			} catch (Exception e) {
				 LOG.error("getHTTPCLIENTInstance:10158");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}		
		}
		return HTTPCLIENT;
	}

	public static void disposeHTTPCLIENT() {
		HTTPCLIENT = null;
	}
}
