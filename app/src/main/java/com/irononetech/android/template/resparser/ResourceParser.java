package com.irononetech.android.template.resparser;

import android.content.Context;

	/**
 	* 
 	* @author Adeesha Wijayasiri this class is for return the string message of the
 	*         string id
 	* 
 	*/
	public class ResourceParser {
	/**
	 * 
	 * @param context
	 * @param id of the message
	 * @return string message
	 */
	public static String getResourceString(Context context, int id) {
		return context.getString(id);
	}
}
