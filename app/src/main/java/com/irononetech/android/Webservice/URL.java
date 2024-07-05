package com.irononetech.android.Webservice;

import android.os.Environment;

public class URL {
	//NOTE: Ending '/' is required
	private static final String BASE_URL = "http://motortab.srilankainsurance.com/";
    //private static final String BASE_URL = "http://122.255.4.180:81/";  //SLIC UAT
    //private static final String BASE_URL = "http://122.255.4.187:89/";  //SLIC UAT_NEW
	//private static final String BASE_URL = "http://192.168.1.114:5001/";  //Local
	//private static final String BASE_URL = "http://172.21.21.21:5001/";
	//private static final String BASE_URL = "http://172.21.0.36:5001/";
	//private static final String BASE_URL = "http://172.24.60.22:81";
	//private static final String BASE_URL = "http://qa.boardpaconline.com:5001/";
	//private static final String BASE_URL = "http://192.168.103.10/";

	//URLs
	private static final String LOGIN_URL_REMAINDER = "LogOn/LogOn?fmt=xml";
	private static final String LOGOUT_URL_REMAINDER = "LogOn/LogOff?fmt=xmlLogOn/LogOff?fmt=xml";
	
	private static final String SA_FORM_UPLOAD_URL_REMAINDER = "Job/AddJob?fmt=xml";
	private static final String VISIT_FORM_UPLOAD_URL_REMAINDER = "Job/AddVisit?fmt=xml";
	 
	private static final String IMAGE_UPLOAD_URL_REMAINDER = "Job/UploadImage?fmt=xml";
	private static final String   IMAGE_DOWNLOAD_URL_REMAINDER = "Job/GetImageById?fmt=xml&imageID=";
	 
	private static final String SEARCH_BY_VISIT_ID_URL_REMAINDER = "/Job/SearchByVehicleNo?fmt=XML&searchText=";
	private static final String GET_JOB_DATA_USING_VISIT_ID_URL_REMAINDER = "/Job/GetJobDetailsAjaxHandler?fmt=XML&getImages=true";
	private static final String GET_VISIT_DATA_USING_VISIT_ID_URL_REMAINDER = "/Job/GetVisitDetailsAjaxHandler?fmt=XML&getImages=true";
	private static final String GET_ALL_COMMENTS_USING_JOB_NO_URL_REMAINDER = "Job/GetCommentsByJobNo?fmt=XML";
	private static final String GET_ALL_VISIT_STATUSES_URL_REMAINDER = "  ?fmt=XML";
	//private static final String SEARCH_BY_JOB_NUMBER_URL_REMAINDER = "Job/GetJobByJobNo?fmt=xml";
	private static final String FORM_REUPLOAD_URL_REMAINDER = "Job/ReSubmitJob?fmt=xml";
	
	
	//SLIC WS Info
	private static final String SLIC_WEB_SRV_URL = "http://122.255.4.186:10452/MotorTabWS2/MotorTabService";
	private static final String SLIC_WEB_SRV_NAMESPACE = "http://tab.motor.lk/";
	private static final String SLIC_WEB_SRV_SOAP_ACTION = "http://tab.motor.lk/getPolicyInfo";
	private static final String SLIC_WEB_SRV_METHOD_NAME = "getPolicyInfo";
	private static final int SLIC_WEB_SRV_TIMEOUT_IN_SEC = 30;


	
	//File Paths
	private static final String SLIC_JOBS = Environment.getExternalStorageDirectory() + "/SLIC/Jobs/";
	private static final String SLIC_VISITS = Environment.getExternalStorageDirectory() + "/SLIC/Visits/";

	private static final String SLIC_DRAFTS_JOBS = Environment.getExternalStorageDirectory() + "/SLIC/Drafts/Jobs/";
	private static final String SLIC_DRAFTS_VISITS = Environment.getExternalStorageDirectory() + "/SLIC/Drafts/Visits/";
		
	private static final String SLIC_COPIED = Environment.getExternalStorageDirectory() + "/SLIC/Copied";
	private static final String SLIC_DOCS = Environment.getExternalStorageDirectory() + "/SLIC/Document";
	private static final String SLIC_ACC = Environment.getExternalStorageDirectory() + "/SLIC/Accident";
	private static final String SLIC_USB = Environment.getExternalStorageDirectory() + "/Storages/usb/sda";
	private static final String SLIC_DOCUMENT = Environment.getExternalStorageDirectory() + "/SLIC/Document/";
	// private static final String SLIC_DOCUMENT = "/sdcard/SLIC/Document/";
	private static final String SLIC_ACCIDENT = Environment.getExternalStorageDirectory() + "/SLIC/Accident/";



	private static final String SLIC_LOGS = Environment.getExternalStorageDirectory() + "/SLIC/Logs/";
	
	
	//draft count get count
	private static final String GET_MAXIMUM_DRAFT_COUNT_URL = "admin/GetMaximumAllowedDraftsCount?fmt=XML";

	//Constants
	private static final int MAXREACHED = 10;
	private static final int MAXREACHEDWARNING = 9;
	private static final int WARNING = 8;
		
	
	
	
	//URLs
	public static String getBaseUrl() {
		return BASE_URL;
	}
	public static String getLoginUrlRemainder() {
		return LOGIN_URL_REMAINDER;
	}
	public static String getSAFormUploadUrlRemainder() {
		return SA_FORM_UPLOAD_URL_REMAINDER;
	}
	public static String getVisitFormUploadUrlRemainder() {
		return VISIT_FORM_UPLOAD_URL_REMAINDER;
	}
	public static String getImageUploadUrlRemainder() {
		return IMAGE_UPLOAD_URL_REMAINDER;
	}
	public static String getSearchByVisitIdUrlRemainder() {
		return SEARCH_BY_VISIT_ID_URL_REMAINDER;
	}
	public static String getAllCommentsUsingJobNoUrlRemainder() {
		return GET_ALL_COMMENTS_USING_JOB_NO_URL_REMAINDER;
	}
	public static String getAllVisitStatusesUrlRemainder() {
		return GET_ALL_VISIT_STATUSES_URL_REMAINDER;
	}
	public static String getLogoutURLRemainder() {
		return LOGOUT_URL_REMAINDER;
	}
	public static String getImageDownloadUrlRemainder() {
		return IMAGE_DOWNLOAD_URL_REMAINDER;
	}
	public static String getFormReUploadUrlRemainder() {
		return FORM_REUPLOAD_URL_REMAINDER;
	}
	public static String getJobDataUsingVisitIdUrlRemainder() {
		return GET_JOB_DATA_USING_VISIT_ID_URL_REMAINDER;
	}
	public static String getVisitDataUsingVisitIdUrlRemainder() {
		return GET_VISIT_DATA_USING_VISIT_ID_URL_REMAINDER;
	}

	public static String getGetMaximumDraftCountUrl() {
		return GET_MAXIMUM_DRAFT_COUNT_URL;
	}

	//SLIC WS Info
	public static String getSLICWebServiceURL() {
		return SLIC_WEB_SRV_URL;
	}
	public static String getSLICWebServiceNameSpace() {
		return SLIC_WEB_SRV_NAMESPACE;
	}
	public static String getSLICWebServiceSOAPAction() {
		return SLIC_WEB_SRV_SOAP_ACTION;
	}
	public static String getSLICWebServiceMethodName() {
		return SLIC_WEB_SRV_METHOD_NAME;
	}
	public static int getSLICWebServiceTimeoutInSec() {
		return SLIC_WEB_SRV_TIMEOUT_IN_SEC * 1000;
	}
	
	public static int getMAXREACHED() {
		return MAXREACHED;
	}
	
	public static int getMAXREACHEDWARNING() {
		return MAXREACHEDWARNING;
	}
	
	public static int getWARNING() {
		return WARNING;
	}
		
	
	//File Paths
	public static String getSLIC_DRAFTS_VISITS() {
		return SLIC_DRAFTS_VISITS;
	}
	
	public static String getSLIC_VISITS() {
		return SLIC_VISITS;
	}
	
	public static String getSLIC_JOBS() {
		return SLIC_JOBS;
	}
	
	public static String getSLIC_DRAFTS_JOBS() {
		return SLIC_DRAFTS_JOBS;
	}
	
	public static String getSLIC_COPIED() {
		return SLIC_COPIED;
	}
	public static String getSLIC_DOC() {
		return SLIC_DOCS;
	}
	public static String getSLIC_ACC() {
		return SLIC_ACC;
	}


	public static String getSLIC_DOCUMENTS() {
		return SLIC_DOCUMENT;
	}

	public static String getSLIC_ACCIDENT() {
		return SLIC_ACCIDENT;
	}
	public static String getSLIC_USB() {
		return SLIC_USB;
	}
	
	public static String getSLIC_LOGS() {
		return SLIC_LOGS;
	}

	private static final boolean [] CHECKSTATUSTYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};

}
