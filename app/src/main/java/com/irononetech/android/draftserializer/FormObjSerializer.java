package com.irononetech.android.draftserializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.Activity;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.FormObjectDraft;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.VisitObjectDraft;

public class FormObjSerializer implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger LOG = LoggerFactory.getLogger(FormObjSerializer.class);

	// Normal Form Data
	public static synchronized void serializeFormData(final FormObject fo) {
		try {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					// this will be run in a separate thread
					FormObjectDraft fod = Application.createFormObjectDraftInstance();
					if (!serialize(fo, fod)) {
						LOG.info("Job Autosave has been failed\n");
					} else {
						LOG.info("Job Autosave has been success\n");
						if(fo.getIsExit()){
							Application.deleteFormObjectInstance();
							Application.deleteFormObjectDraftInstance();
						}
					}
				}
			});

			// start the thread
			thread.start();
		} catch (Exception e) {
			LOG.error("serializeFormData:10107");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static synchronized Boolean serialize(FormObject fo, FormObjectDraft fod) {
		try {
			if(fod.getTimeReported() == null || fod.getTimeReported().isEmpty()){
				LOG.info("Serializing a null form object " + fod.getJobNo());
			}

			if (fo.getDraftFileName().isEmpty()) {
				// used to generate a filename for this document's draft
				generateaFileName(fo, fo.getJobNo(), fo.getVehicleNo());
			}

			// Suren Manawatta
			// 2012-08-28
			// Used to freeze the JonNo field if it's a SMS
			// Even SMS saved as a draft and retrieve back, still treat it like
			// a SMS
			if (fo.getisSMS()) {
				fod.setisDRAFT(false);
				fod.setisSMS(true);
				fod.setisSEARCH(false);
			} else {
				fod.setisDRAFT(true);
				fod.setisSMS(false);
				fod.setisSEARCH(false);
			}

			if(fod.getTimeReported() != null && !fod.getTimeReported().isEmpty()){
				String filename = URL.getSLIC_DRAFTS_JOBS();
				
				File myDirectory = new File(filename);
				if (!myDirectory.exists()) {
					myDirectory.mkdirs();
				}
				
				filename = filename + fo.getDraftFileName();
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(fod);
				out.close();
			}

			return true;
		} catch (IOException ex) {
			LOG.error("serialize:10108");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		} catch (Exception ex) {
			LOG.error("serialize:10109");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		}
	}

	private static synchronized void generateaFileName(FormObject fo, String JobNo, String VehicleNo) {
		try {
			File myDirectory = new File(URL.getSLIC_DRAFTS_JOBS());
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yy-MM-dd HH.mm.ss a");
			String date = formatter.format(Calendar.getInstance().getTime());

			String filename = JobNo + "_" + VehicleNo + " (" + date + ")"
					+ ".ssm";

			// FormObject fo = Application.createFormObjectInstance();
			// FormObjectDraft fod =
			// Application.createFormObjectDraftInstance();
			// fod.setDraftFileName(filename);
			fo.setDraftFileName(filename);

		} catch (Exception e) {
			LOG.error("generateaFileName:10110");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	
	// SMS Data
	public static boolean[] saveSMSasaDraft(String MSG,	final Activity SMSActivity) {
		boolean[] errArr = new boolean[5];
		/*
		 * for (boolean boolean1 : errArr) { boolean1 = false; }
		 */
		try {
			if (MSG != null) {
				FormObjectDraft formObjDraft = new FormObjectDraft();
				formObjDraft.setEditable(true);
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yy KK:mm a");
				String date = formatter.format(Calendar.getInstance().getTime());
				formObjDraft.setTimeVisited(date);
				formObjDraft.setDateandTimeofAccident(date);

				// split it and assign it to the formObject
				String[] sms = MSG.split("#");

				if (sms.length == 18) {//for new sms format
					formObjDraft.setJobNo(sms[2]);            // ok
					formObjDraft.setVehicleNo(sms[3]);        // ok
					formObjDraft.setNameofInsured(sms[4]);    // ok
					formObjDraft.setDriversName(sms[5]);    // ok
					formObjDraft.setContactNo(sms[6]);        // ok
					formObjDraft.setPolicyCoverNoteNo(sms[7]); // ok

					String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
					Pattern pattern = Pattern.compile(expression);

					if (!sms[8].equals("")) {
						CharSequence inputStr = sms[8];
						Matcher matcher = pattern.matcher(inputStr);
						if (!matcher.matches()) {
							errArr[0] = true;
						} else {
							formObjDraft.setPolicyCoverNotePeriodFrom(sms[8]);
						}
					}

					if (!sms[9].equals("")) {
						CharSequence inputStr = sms[9];
						Matcher matcher = pattern.matcher(inputStr);
						if (!matcher.matches()) {
							errArr[1] = true;
						} else {
							formObjDraft.setPolicyCoverNotePeriodTo(sms[9]);
						}
					}

					formObjDraft.setNameofCaller(sms[10]);
					formObjDraft.setVehicleTypeandColor(sms[11]);

					//check for reported time from sms, if it is empty or invalid format set default and if it is invalid format send error message
					if (!sms[12].equals("")) {
						String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

						CharSequence inputStr1 = sms[12].toUpperCase();
						Pattern pattern1 = Pattern.compile(expression1);
						Matcher matcher1 = pattern1.matcher(inputStr1);
						if (!matcher1.matches()) {
							formObjDraft.setTimeReported(date);
							formObjDraft.setOrgTimeReported("");
							errArr[2] = true;
						} else {
							formObjDraft.setTimeReported(sms[12].toUpperCase());
							formObjDraft.setOrgTimeReported(sms[12].toUpperCase());
						}
					} else {
						formObjDraft.setTimeReported(date);
						formObjDraft.setOrgTimeReported("");
					}

					//check for Date and time of accident
					if (!sms[13].equals("")) {
						String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

						CharSequence inputStr2 = sms[13].toUpperCase();
						Pattern pattern1 = Pattern.compile(expression1);
						Matcher matcher1 = pattern1.matcher(inputStr2);
						if (!matcher1.matches()) {
							formObjDraft.setDateandTimeofAccident(date);
							errArr[5] = true;
						} else {
							formObjDraft.setDateandTimeofAccident(sms[13].toUpperCase());
						}
					} else {
						formObjDraft.setDateandTimeofAccident(date);
					}


					formObjDraft.setLocationofAccident(sms[14]);

					if (formObjDraft.getPolicyCoverNoteNo() == null || formObjDraft.getPolicyCoverNoteNo().trim().equals("")){
						formObjDraft.setPolicyCoverNoteNo(sms[15]);
					}

					formObjDraft.setNearestPoliceStation(sms[16]);

					formObjDraft.setisDRAFT(false);
					formObjDraft.setisSMS(true);
					formObjDraft.setisSEARCH(false);

					formObjDraft.setDraftFileName("");

					// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					if (!errArr[0] && !errArr[1]) {
						if (serializeSMS(formObjDraft)) {
							LOG.info("SMS serializeSMS was success.");
							Toast.makeText(SMSActivity, "SMS is saved as a draft.", Toast.LENGTH_LONG).show();
						} else {
							LOG.info("SMS serializeSMS was failed. Job never saved as a draft.");
							Toast.makeText(SMSActivity, "SMS is NOT saved as a draft.", Toast.LENGTH_LONG).show();
						}
					}
					// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				} else if(sms.length == 17){ //for old sms format
					formObjDraft.setJobNo(sms[2]);            // ok
					formObjDraft.setVehicleNo(sms[3]);        // ok
					formObjDraft.setNameofInsured(sms[4]);    // ok
					formObjDraft.setDriversName(sms[5]);    // ok
					formObjDraft.setContactNo(sms[6]);        // ok
					formObjDraft.setPolicyCoverNoteNo(sms[7]); // ok

					String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";
					Pattern pattern = Pattern.compile(expression);

					if (!sms[8].equals("")) {
						CharSequence inputStr = sms[8];
						Matcher matcher = pattern.matcher(inputStr);
						if (!matcher.matches()) {
							errArr[0] = true;
						} else {
							formObjDraft.setPolicyCoverNotePeriodFrom(sms[8]);
						}
					}
					if (!sms[9].equals("")) {
						CharSequence inputStr = sms[9];
						Matcher matcher = pattern.matcher(inputStr);
						if (!matcher.matches()) {
							errArr[1] = true;
						} else {
							formObjDraft.setPolicyCoverNotePeriodTo(sms[9]);
						}
					}

					formObjDraft.setNameofCaller(sms[10]);
					formObjDraft.setVehicleTypeandColor(sms[11]);

					if (!sms[12].equals("")) {
						String expression1 = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

						CharSequence inputStr1 = sms[12].toUpperCase();
						Pattern pattern1 = Pattern.compile(expression1);
						Matcher matcher1 = pattern1.matcher(inputStr1);
						if (!matcher1.matches()) {
							formObjDraft.setTimeReported(date);
							formObjDraft.setOrgTimeReported("");
							errArr[2] = true;
						} else {
							formObjDraft.setTimeReported(sms[12].toUpperCase());
							formObjDraft.setOrgTimeReported(sms[12].toUpperCase());
						}
					} else {
						formObjDraft.setTimeReported(date);
						formObjDraft.setOrgTimeReported("");
					}

					formObjDraft.setLocationofAccident(sms[13]);
					if (formObjDraft.getPolicyCoverNoteNo() == null ||
							formObjDraft.getPolicyCoverNoteNo().trim().equals("")) {
						formObjDraft.setPolicyCoverNoteNo(sms[14]);
					}

					formObjDraft.setNearestPoliceStation(sms[15]);

					formObjDraft.setisDRAFT(false);
					formObjDraft.setisSMS(true);
					formObjDraft.setisSEARCH(false);

					formObjDraft.setDraftFileName("");

					// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					if (!errArr[0] && !errArr[1]) {
						if (serializeSMS(formObjDraft)) {
							LOG.info("SMS serializeSMS was success.");
							Toast.makeText(SMSActivity, "SMS is saved as a draft.", Toast.LENGTH_LONG).show();
						} else {
							LOG.info("SMS serializeSMS was failed. Job never saved as a draft.");
							Toast.makeText(SMSActivity, "SMS is NOT saved as a draft.", Toast.LENGTH_LONG).show();
						}
					}
					// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				}else {
					errArr[3] = true;
				}
				// Delete the object
				formObjDraft = null;
			}
		} catch (Exception e) {
			LOG.error("saveSMSasaDraft:10111");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			errArr[4] = true;
		}
		return errArr;
	}

	private static Boolean serializeSMS(FormObjectDraft fod) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yy-MM-dd HH.mm.ss a");
			String date = formatter.format(Calendar.getInstance().getTime());
			String draftfilename = fod.getJobNo() + "_" + fod.getVehicleNo()
					+ " (" + date + ")" + ".ssm";
			fod.setDraftFileName(draftfilename);

			String filename = URL.getSLIC_DRAFTS_JOBS();
			File myDirectory = new File(filename);
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}
			
			filename = filename + fod.getDraftFileName();

			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(fod);
			out.close();

			return true;
		} catch (IOException ex) {
			LOG.error("serializeSMS:10112");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		} catch (Exception ex) {
			LOG.error("serializeSMS:10113");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		}
	}

	public static String getSMSJobno(String MSG) {
		try {
			if (MSG != null) {
				// split it and assign it to the formObject
				String[] sms = MSG.toString().split("#");
				if (sms.length == 18 || sms.length == 17) { //support for old and new sms
					return sms[2].trim();
				}
			}
			return "";
		} catch (Exception e) {
			LOG.error("getSMSJobno:10560");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	
	// Visit Data
	public static synchronized void serializeFormDataForVisits(final VisitObject vo) {
		try {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					// this will be run in a separate thread
					VisitObjectDraft vod = Application.createVisitObjectDraftInstance();
					if (!serializeForVisits(vo, vod)) {
						LOG.info("Visit Autosave has been failed\n");
					} else {
						LOG.info("Visit Autosave has been success\n");
						if(vo.getIsExit()){
							Application.deleteVisitObjectInstance();
							Application.deleteVisitObjectDraftInstance();
						}
					}
				}
			});

			// start the thread
			thread.start();
		} catch (Exception e) {
			LOG.error("serializeFormDataForVisits:11107");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static synchronized Boolean serializeForVisits(VisitObject vo, VisitObjectDraft vod) {
		try {
			if(vod.getJobNo() == null || vod.getJobNo().isEmpty())
				LOG.info("Serializing a null visit object");
			
			if (vo.getDraftFileName().isEmpty()) {
				// used to generate a filename for this document's draft
				generateaFileNameForVisits(vo);
			}

			// Suren Manawatta
			// 2012-08-28
			// Used to freeze the JobNo field if it's a SMS
			// Even SMS saved as a draft and retrieve back, still treat it like
			// a SMS
			if (vo.getisSMS()) {
				vod.setisDRAFT(false);
				vod.setisSMS(true);
				vod.setisSEARCH(false);
			} else {
				vod.setisDRAFT(true);
				vod.setisSMS(false);
				vod.setisSEARCH(false);
			}

			if(vod.getJobNo() != null && !vod.getJobNo().isEmpty()){
				String filename = URL.getSLIC_DRAFTS_VISITS();
				File myDirectory = new File(filename);
				if (!myDirectory.exists()) {
					myDirectory.mkdirs();
				}
				
				filename = filename + vo.getDraftFileName();
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(vod);
				out.close();
			}
			return true;
		} catch (IOException ex) {
			LOG.error("serializeForVisits:11108");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		} catch (Exception ex) {
			LOG.error("serializeForVisits:11109");
			   if(ex != null){
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			  }
			return false;
		}
	}
	
	private static synchronized void generateaFileNameForVisits(VisitObject vo) {
		try {
			File myDirectory = new File(URL.getSLIC_DRAFTS_VISITS()); 
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yy-MM-dd HH.mm.ss a");
			String date = formatter.format(Calendar.getInstance().getTime());

			String filename = vo.getJobNo() + "_" + vo.getVehicleNo() + "_" + vo.getInspectionTypeInText() + " (" + date + ")" + ".ssmv";
			vo.setDraftFileName(filename);

		} catch (Exception e) {
			LOG.error("generateaFileName:10110");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}
}