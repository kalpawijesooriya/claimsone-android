package com.irononetech.android.Application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
//import junit.runner.Version;

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.database.DBService;
import com.irononetech.android.dataretrievecomponent.DataRetrieveController;
import com.irononetech.android.dataretrievecomponent.DataRetrieveUIObject;
import com.irononetech.android.filecopycomponent.FileCopyController;
import com.irononetech.android.filecopycomponent.FileCopyServiceHandler;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormController;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.FormObjectDraft;
import com.irononetech.android.formcomponent.FormResubmitObject;
import com.irononetech.android.formcomponent.VisitController;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.VisitObjectDraft;
import com.irononetech.android.formcomponent.view.AccidentDetailsActivity;
import com.irononetech.android.formcomponent.view.CommentActivity;
import com.irononetech.android.formcomponent.view.DraftActivity;
import com.irononetech.android.formcomponent.view.DriverDetailsActivity;
import com.irononetech.android.formcomponent.view.InsuranceDetailsActivity;
import com.irononetech.android.formcomponent.view.OtheritemsDetailsActivity;
import com.irononetech.android.formcomponent.view.TechnicalReviewDetailsActivity;
import com.irononetech.android.formcomponent.view.VehicleDetailsActivity;
import com.irononetech.android.formcomponent.view.VisitActivity;
import com.irononetech.android.future.FutureController;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.imageuploadcomponent.AsyncImgUploder;
import com.irononetech.android.logincomponent.controller.LoginController;
import com.irononetech.android.logincomponent.model.LoginWebService;
import com.irononetech.android.logincomponent.view.LoginActivity;
import com.irononetech.android.logincomponent.view.UserObject;
import com.irononetech.android.logoutcomponent.LogoutController;
import com.irononetech.android.searchcomponent.SearchActivity;
import com.irononetech.android.searchcomponent.SearchController;
import com.irononetech.android.searchcomponent.SearchListActivity;
import com.irononetech.android.searchcomponent.SearchUIobject;
import com.irononetech.android.smsreceiver.SMSProcessor;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.GenException;
import com.irononetech.android.template.IronDroidApplication;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.template.resparser.ResourceParser;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.utilities.JPGFileFilter;

public class Application extends IronDroidApplication {
	// aaaaa
	static Logger LOG = LoggerFactory.getLogger(Application.class);
	static Application testApplication;
	static FormObject formObject;
	static FormObjectDraft formObjectDraft;
	static VisitObject visitObject;
	static VisitObjectDraft visitObjectDraft;
	static SearchUIobject searchUIobject;
	static String currentUser = "";
	static FormResubmitObject resubmitObject;

	// Global variables
	public static int status = 1; // 0 == offline | 1 == online
	public static boolean isInVisitPage = false; // True == Search For a Visit
													// Creation | False ==
													// Search jobs & Visits For
													// View
	public static boolean isVisit = false; // True == retrieving a Visit | False
											// == Retrieving a Job
	public static List<NameValuePair> searchedJobsWithVisitId;

	public static boolean goForward = false;
	public static boolean goBackward = false;

	private Application() {
	}

	public static Application getInstance() {
		if (testApplication == null) {
			testApplication = new Application();
		}
		return testApplication;
	}

	public static FormObject createFormObjectInstance() {
		// FormObject formObject = new FormObject();
		// return formObject;
		if (formObject == null) {
			formObject = new FormObject();
		}
		return formObject;
	}

	public static FormObjectDraft createFormObjectDraftInstance() {
		// FormObjectDraft formObjectDraft = new FormObjectDraft();
		// return formObjectDraft;
		if (formObjectDraft == null) {
			formObjectDraft = new FormObjectDraft();
		}
		return formObjectDraft;
	}

	public static VisitObject createVisitObjectInstance() {
		if (visitObject == null) {
			visitObject = new VisitObject();
		}
		return visitObject;
	}

	public static VisitObjectDraft createVisitObjectDraftInstance() {
		if (visitObjectDraft == null) {
			visitObjectDraft = new VisitObjectDraft();
		}
		return visitObjectDraft;
	}

	public static FormResubmitObject createFormResubmitObjectInstance() {
		// FormResubmitObject resubmitObject = new FormResubmitObject();
		// return resubmitObject;
		if (resubmitObject == null) {
			resubmitObject = new FormResubmitObject();
		}
		return resubmitObject;
	}

	public static void deleteFormResubmitObjectInstance() {
		resubmitObject = null;
	}

	public static void deleteFormObjectInstance() {
		formObject = null;
	}

	public static void deleteFormObjectDraftInstance() {
		formObjectDraft = null;
	}

	public static FormObject getFormObjectInstance() {
		return formObject;
	}

	public static VisitObject getVisitObjectInstance() {
		return visitObject;
	}

	public static void setFormObjectInstance(FormObject formObj) {
		formObject = formObj;
	}

	public static void setVisitObjectInstance(VisitObject vObj) {
		visitObject = vObj;
	}

	public static SearchUIobject createSearhUIobjectInstance() {
		searchUIobject = new SearchUIobject();
		return searchUIobject;
	}

	public static void deleteSearhUIobjectInstance() {
		searchUIobject = null;
	}

	public static SearchUIobject getSearhUIobjectInstance() {
		return searchUIobject;
	}

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Application.currentUser = currentUser;
	}

	public static void deleteVisitObjectInstance() {
		visitObject = null;
	}

	public static void deleteVisitObjectDraftInstance() {
		visitObjectDraft = null;
	}

	private static boolean checkCamAvail() {
		try {
			File f = new File(URL.getSLIC_USB());
			return f.isDirectory();
		} catch (Exception e) {
			LOG.error("checkCam");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
		return false;
	}

	// ---------Offline login status------------------
	public static int getIsOnline() {
		return status;
	}

	public static void setIsOnline(int stat) {
		status = stat;
	}

	// ----Check user in visit creation page or search page----
	public static boolean getIsInVisitPage() {
		return isInVisitPage;
	}

	public static void setIsInVisitSearchPage(boolean isVst) {
		isInVisitPage = isVst;
	}

	// ----Check current operation is a visit or not----
	public static boolean getIsVisit() {
		return isVisit;
	}

	public static void setIsVisit(boolean isVst) {
		isVisit = isVst;
	}

	public static List<NameValuePair> getJobOrVehicleNoWithVisitId() {
		return searchedJobsWithVisitId;
	}

	public static void setJobOrVehicleNoWithVisitId(List<NameValuePair> sJobsWithVisitId) {
		searchedJobsWithVisitId = sJobsWithVisitId;
	}

	// ---------Image upload thread-------------------
	static AsyncImgUploder asyncI;
	static Thread imgThread;

	public static void startImageUploder() {
		try {
			if (asyncI == null) {
				asyncI = new AsyncImgUploder();
			}

			if (imgThread == null) {
				imgThread = new Thread(asyncI);

				if (!imgThread.isAlive()) {
					new Thread(asyncI).start();
				}
			}
		} catch (Exception e) {
			LOG.error("startImageUpload");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public static void stopImgUploder() {
		try {
			if (imgThread != null)
				imgThread.interrupt();
		} catch (Exception e) {
			LOG.error("stopImgUpload");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	// -----------------------------------------------

	// ------4D array size definer--------------------

	// Defines : new boolean[10][x][x][x];
	public static int get4DArrSizeSec1() {
		return 1;
	}

	// Defines : new boolean[x][16][x][x];
	public static int get4DArrSizeSec2() {
		return 17;
	}

	// Defines : new boolean[x][x][113][x];
	public static int get4DArrSizeSec3() {
		return 120;
	}

	// Defines : new boolean[x][x][x][2];
	public static int get4DArrSizeSec4() {
		return 2;
	}

	public static int getTyreConditionArrSize() {
		return 24;
	}

	public void cancelForm(final EventParcel eventParcel) {
		final Activity act = eventParcel.getSourceActivity();
		final String actName = act.getClass().getSimpleName();
		try {
			LOG.debug("ENTRY cancelForm");
			String btnName = "Delete Draft";
			if (actName.equalsIgnoreCase("VisitActivity") && visitObject != null && visitObject.getisSEARCH()) {
				btnName = "Yes";
			}
			if (formObject != null && formObject.getisSEARCH()) {
				btnName = "Yes";
			}

			AlertDialog.Builder alertbox = new AlertDialog.Builder(new ContextThemeWrapper(act, R.style.MyTheme));
			alertbox.setTitle(R.string.saform);
			alertbox.setIcon(android.R.drawable.ic_dialog_alert);
			alertbox.setMessage("Are you sure you want to cancel?");
			alertbox.setPositiveButton(btnName, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					try {
						// Delete the relevant draft
						if (!actName.equalsIgnoreCase("VisitActivity"))
							FileOperations.draftFileDeleterUsingFilename(formObject.getDraftFileName());
						else
							FileOperations.draftFileDeleterUsingFilenameForVisits(visitObject.getDraftFileName());

						// Delete the relevant job/visit folder
						File f2;
						if (!actName.equalsIgnoreCase("VisitActivity")) {
							String jobname = URL.getSLIC_JOBS();
							f2 = new File(jobname + formObject.getJobNo());
						} else {
							String jobname = URL.getSLIC_VISITS();
							f2 = new File(jobname + visitObject.getJobNo());
						}

						// NOTE: Suren - 2015-05-15
						// This code fixed the bug of pending job's image folder
						// gets
						// Deleted, when user tries to search the same job just
						// after submitting.
//						ArrayList<String> pendingVisitIds = DBService.getPendingJobNoList();
//						if (!(pendingVisitIds != null && pendingVisitIds.size() > 0
//								&& pendingVisitIds.contains(formObject.getVisitId())))
//							FileOperations.DeleteRecursive(f2);

						if (!actName.equalsIgnoreCase("VisitActivity")) {
							deleteFormObjectInstance();
							deleteFormObjectDraftInstance();
						} else {
							deleteVisitObjectInstance();
							deleteVisitObjectDraftInstance();
						}
						listenerUICallbackUI(eventParcel);
					} catch (Exception e) {
						LOG.error("cancelForm");
						if (e != null) {
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}
					}
				}
			});
			// if (formObject != null && !formObject.getisSEARCH() &&
			// !Application.getIsVisit() || visitObject != null &&
			// !visitObject.getisSEARCH() && Application.getIsVisit()) {
			if (actName.equalsIgnoreCase("VisitActivity") && visitObject != null && !visitObject.getisSEARCH()
					|| formObject != null && !formObject.getisSEARCH()) {
				alertbox.setNeutralButton("Save & Quit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// CLICK_FRONT =false;
						// CLICK_BACK = true;
						try {
							boolean continu = false;

							// leave below commented two lines. if enable this
							// you can't save the 10th draft.
							if (actName.equalsIgnoreCase("VisitActivity")) {
								// continu =
								// FileOperations.draftsMaxReachedForVisits();
							} else {
								// continu = FileOperations.draftsMaxReached();
							}

							if (continu) {
								AlertDialog.Builder alertbox = new AlertDialog.Builder(act);
								alertbox.setTitle("Draft Alert:");
								alertbox.setMessage(act.getString(
										R.string.max_no_of_drafts_has_been_reached_please_deal_with_some_drafts_to_continue_));
								alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {
										Intent intent = new Intent(act, DraftActivity.class);

										if (actName.equalsIgnoreCase("VisitActivity"))
											intent.putExtra("TAB", "1");

										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										act.startActivity(intent);
										// finish();
									}
								});
								alertbox.show();
							} else {
								if (actName.equalsIgnoreCase("AccidentDetailsActivity")) {
									formObject.setIsExit(true);
									((AccidentDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("DriverDetailsActivity")) {
									formObject.setIsExit(true);
									((DriverDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("InsuranceDetailsActivity")) {
									formObject.setIsExit(true);
									((InsuranceDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("OtheritemsDetailsActivity")) {
									formObject.setIsExit(true);
									((OtheritemsDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("TechnicalReviewDetailsActivity")) {
									formObject.setIsExit(true);
									((TechnicalReviewDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("VehicleDetailsActivity")) {
									formObject.setIsExit(true);
									((VehicleDetailsActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("CommentActivity")) {
									formObject.setIsExit(true);
									((CommentActivity) act).onSave();
								}
								if (actName.equalsIgnoreCase("VisitActivity")) {
									visitObject.setIsExit(true);
									((VisitActivity) act).onSave();
									Application.setIsVisit(false);
								}

								listenerUICallbackUI(eventParcel);
								// Application.getInstance().doActionOnEvent(new
								// EventParcel(UIEvent.CANCEL_FORM, act, null));
							}
						} catch (Exception e) {
							LOG.error("cancelForm");
							if (e != null) {
								LOG.error("Message: " + e.getMessage());
								LOG.error("StackTrace: " + e.getStackTrace());
							}
						}
					}
				});
			}
			alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					act.removeDialog(arg1);
				}
			});
			alertbox.show();
			LOG.debug("SUCCESS cancelForm");
		} catch (Exception e) {
			LOG.error("cancelForm");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	// -----------------------------------------------

	public void alertShow(Activity activity, String message) {
		try {
			if (message == null || message.isEmpty())
				message = activity.getString(R.string.networkunavailable);
			final String errorMessage = message;
			final Activity activity2 = activity;
			activity.runOnUiThread(new Runnable() {
				public void run() {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity2);
					alertbox.setMessage(errorMessage);
					alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertbox.show();
				}
			});
		} catch (Exception e) {
			LOG.error("alertShow");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void alertShowYesNo(final Activity activity, String message, final Uri appUrl,
			final Boolean isNoBtnAvailable) {
		try {
			if (message == null || message.isEmpty())
				message = activity.getString(R.string.networkunavailable);
			final String errorMessage = message;
			final Activity activity2 = activity;
			activity.runOnUiThread(new Runnable() {
				public void run() {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity2);
					alertbox.setMessage(errorMessage);
					alertbox.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							final String appPackageName = activity.getPackageName();
							try {
								activity.startActivity(new Intent(Intent.ACTION_VIEW,
										Uri.parse("market://details?id=" + appPackageName)));
							} catch (android.content.ActivityNotFoundException anfe) {
								// use a try/catch block here because an
								// Exception will be thrown if
								// the Play Store is not installed on
								// the target device.
								activity.startActivity(new Intent(Intent.ACTION_VIEW, appUrl));
							}
						}
					});
					if (isNoBtnAvailable) {
						alertbox.setNegativeButton("Later", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent mIntent = new Intent(activity, HomeActivity.class);
								mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								activity.startActivity(mIntent);
								
								((LoginActivity) activity).getDialog().dismiss();
								((LoginActivity) activity).finish();
							}
						});
					}
					alertbox.show();
				}
			});
		} catch (Exception e) {
			LOG.error("alertShowYesNo");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public static void goOffline(final Activity act, UserObject uObj, final boolean isTabFailed) {
		try {
			// If Offline
			final LoginActivity loginAct = (LoginActivity) act;
			final UserObject userObject = uObj;
			final String usrnam = userObject.getUsername();
			final String title = "Application Offline";

			loginAct.runOnUiThread(new Runnable() {
				public void run() {

					AlertDialog.Builder alertbox = new AlertDialog.Builder(loginAct);
					alertbox.setTitle(title);
					alertbox.setCancelable(false);
					if (isTabFailed)
						alertbox.setMessage(act.getString(R.string.networkunavailable_gooffline_two)); // Old
																										// msg:
																										// "Network
																										// not
																										// available,
																										// Do
																										// you
																										// want
																										// to
																										// proceed
																										// in
																										// offline
																										// mode?");
					else
						alertbox.setMessage(act.getString(R.string.networkunavailable_gooffline_one));
					alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							if (DBService.checkUserAuthOffline(
									new EventParcel(UIEvent.LOGIN_BUTTON_CLICK, loginAct, userObject))) { // Login
																											// Status

								// Offline (Global selection)
								Application.setIsOnline(0);
								Application.setCurrentUser(usrnam);
								Intent mIntent = new Intent(loginAct, HomeActivity.class);
								mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								loginAct.startActivity(mIntent);
								loginAct.finish(); // finish login activity
							} else {
								// login failed or exception
								// Incorrect Username or Password

								AlertDialog.Builder alertbox = new AlertDialog.Builder(loginAct);
								alertbox.setTitle(R.string.app_name);
								alertbox.setMessage("Incorrect Username or Password");
								alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {
									}
								});
								alertbox.show();
							}
						}
					});
					alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							try {
								((LoginActivity) act).getDialog().dismiss();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					alertbox.show();
				}
			});
		} catch (Exception e) {
			LOG.error("goOffline");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	// Automatic Login
	public static boolean automaticlogin() {
		try {
			boolean proceed = true;
			if (Application.getIsOnline() == 0) {
				// Automatic Login
				String username = Application.getCurrentUser();
				String password = DBService.getPasswordOfCurrentuserService(username);
				String urlL = URL.getBaseUrl() + URL.getLoginUrlRemainder();

				LoginWebService lws = new LoginWebService();
				String xmlText = lws.sendRequestForLogin(urlL, username, password);

				WebServiceObject ws = new WebServiceObject();
				ws.setXmlText(xmlText);
				XMLHandler.getResponse(ws);
				if (!ws.getCode().equalsIgnoreCase("0"))
					proceed = false;
				else
					Application.setIsOnline(1);
			}
			return proceed;
		} catch (Exception e) {
			LOG.error("automaticlogin");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			return false;
		}
	}

	/*
	 * public static void copyImages(Activity act){ try { LogFile.d("ENTRY ",
	 * TAG + "copyImages");
	 * 
	 * File f = new File(URL.getSLIC_USB());
	 * 
	 * if (f.exists()) { //deviceList != null && deviceList.size() > 0
	 * List<File> filesList = FileCopyServiceHandler.getSrcImageList();
	 * 
	 * if (filesList.size() <= 0) { }
	 * 
	 * if (filesList.size() > 100) { }
	 * 
	 * String className = act.getClass().getSimpleName(); //ProgressDialog
	 * dialog = ProgressDialog.show(act, "", "Copying. Please wait...", true);
	 * 
	 * if(className.equalsIgnoreCase("HomeActivity")){ //((HomeActivity)
	 * act).setDialog(dialog); //((HomeActivity) act).getDialog().show(); }
	 * else{ //((TechnicalReviewDetailsActivity) act).setDialog(dialog);
	 * //((TechnicalReviewDetailsActivity) act).getDialog().show(); }
	 * 
	 * //dialog = ProgressDialog.show(act, "", "Copying. Please wait...", true);
	 * Application.getInstance().doActionOnEvent(new
	 * EventParcel(UIEvent.FILECOPY_BUTTON_CLICK, act, null)); LogFile.d(
	 * "SUCCESS ", TAG + "copyImages"); }else{ LayoutInflater inflater =
	 * act.getLayoutInflater(); View dialoglayout =
	 * inflater.inflate(R.layout.cameraconnectmsg, null); String title =
	 * "Please Connect a Camera!"; AlertDialog.Builder alertbox = new
	 * AlertDialog.Builder(act); alertbox.setTitle(title);
	 * alertbox.setView(dialoglayout); alertbox.setPositiveButton("Ok", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * arg0, int arg1) { } }); alertbox.show(); } } catch (Exception e) {
	 * LogFile.d("EXCEPTION ", TAG + "copyImages:11154"); } }
	 */

	static File f;

	static int orgDestinationImgCount = 0;
	static int portionCopied = 0;
	static int setTotalNoOfImgToCopy = 0;
	static int progressBarStatus = 0;
	static Thread t;
	static int preSelected = 0;

	public static void copyImageCommon(Activity act, View v, int mode) {
		try {
			if(mode == 0){
				f = new File(URL.getSLIC_ACCIDENT());
			}else if(mode == 1){
				 f = new File(URL.getSLIC_DOCUMENTS());
			}else{
				f = new File(URL.getSLIC_COPIED());
			}

			if (Application.checkCamAvail())
				Application.copyImagesFromMedia(act, v);
			else {
				LayoutInflater inflater = act.getLayoutInflater();
				View dialoglayout = inflater.inflate(R.layout.cameraconnectmsg, null);
				String title = "Please Connect a Camera!";
				AlertDialog.Builder alertbox = new AlertDialog.Builder(act);
				alertbox.setTitle(title);
				alertbox.setView(dialoglayout);
				alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
			}
		} catch (Exception e) {
			LOG.error("copyImageCommon");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private static void copyImagesFromMedia(Activity act, View v) {
		try {
			LOG.debug("ENTRY on_click_copy_images_button");

			final Handler progressBarHandler = new Handler();
			final ProgressDialog progressBar;

			// Initialize
			orgDestinationImgCount = 0;
			portionCopied = 0;
			setTotalNoOfImgToCopy = 0;
			progressBarStatus = 0;

			// dialog = ProgressDialog.show(this, "", "Copying. Please wait...",
			// true);
			// Application.copyImages(this);
			orgDestinationImgCount = FileOperations.listFiles(f, new JPGFileFilter(), true).size();

			List<File> srcFileList = FileCopyServiceHandler.getSrcImageList();
			List<File> destFileList = FileCopyServiceHandler.getDestImageList();

			final List<File> actualList = FileCopyServiceHandler.compareImageDir(srcFileList, destFileList);
			if (actualList != null && actualList.size() > 0) {

				setTotalNoOfImgToCopy = actualList.size();

				// prepare for a progress bar dialog
				progressBar = new ProgressDialog(v.getContext());
				progressBar.setCancelable(false);
				progressBar.setMessage("File Copying...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);

				progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressBarStatus = actualList.size();

						if (t.isAlive())
							t.interrupt();
						dialog.dismiss();
					}
				});

				progressBar.setMax(actualList.size());
				progressBar.show();

				new Thread(new Runnable() {
					public void run() {
						t = new Thread(new Runnable() {
							public void run() {
								FileOperations.copyFilesFromDirToDir(actualList, URL.getSLIC_COPIED());
							}
						});

						try {
							t.start();
						} catch (Exception e) {
						}

						while (progressBarStatus < actualList.size()) {
							// process some tasks
							progressBarStatus = findProgressOfFileCopy();

							// your computer is too fast, sleep 1 second
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							// Update the progress bar
							progressBarHandler.post(new Runnable() {
								public void run() {
									progressBar.setProgress(progressBarStatus);
								}
							});
						}

						// ok, files are copied.
						if (progressBarStatus >= actualList.size()) {
							// sleep 2 seconds, so that you can see the 100%
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							// close the progress bar dialog
							progressBar.dismiss();
						}
					}
				}).start();
			} else {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(act);
				alertbox.setTitle("Copy Image Complete");
				alertbox.setMessage("All the images are copied to the device successfully.");
				alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
			}
			LOG.debug("SUCCESS on_click_copy_images_button");
		} catch (Exception e) {
			LOG.error("on_click_copy_images_button");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}

	}

	private static int findProgressOfFileCopy() {
		try {
			// LogFile.d("ENTRY ", TAG + "findProgressOfFileCopy");

			int destinationCurrentImgCount = FileOperations.listFiles(f, new JPGFileFilter(), true).size();
			portionCopied = portionCopied + (destinationCurrentImgCount - orgDestinationImgCount);
			orgDestinationImgCount = destinationCurrentImgCount;

			// LogFile.d("SUCCESS ", TAG + "findProgressOfFileCopy");
			return portionCopied;
		} catch (Exception e) {
			LOG.error("findProgressOfFileCopy");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			return 0;
		}
	}

	public static Boolean checkImageCount(Activity act) {
		File f = new File(URL.getSLIC_COPIED());
		int count = FileOperations.listFiles(f, new JPGFileFilter(), true).size();
		if (count > 400) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(act);
			alertbox.setTitle("Copy Image Alert");
			alertbox.setMessage("Application can handle only 400 images. Please delete minimum of " + (count - 400)
					+ " images from 'SLIC/Copied' folder to proceed.");
			alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			alertbox.show();
			return false;
		}
		return true;
	}

	@Override
	public void doActionOnEvent(EventParcel eventParcel) {
		if (eventParcel.getuIEvent() == UIEvent.SUBMIT_BUTTON_CLICK) {
			try {
				try {
					FormController formController = new FormController();
					formController.addListener(this);
					formController.doAction(eventParcel);
				} catch (GenException e) {
					LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw new GenException("", e.getMessage());
				} // TechnicalReviewDetailsActivity --> CommentActivity
				((CommentActivity) eventParcel.getSourceActivity()).dialog = ProgressDialog
						.show(((CommentActivity) eventParcel.getSourceActivity()), "", ResourceParser.getResourceString(
								((CommentActivity) eventParcel.getSourceActivity()), R.string.data_uploading), true);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((CommentActivity) eventParcel.getSourceActivity()), e.getMessage());
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.VISIT_SUBMIT_BUTTON_CLICK) {
			try {
				try {
					VisitController visitController = new VisitController();
					visitController.addListener(this);
					visitController.doAction(eventParcel);
				} catch (GenException e) {
					LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw new GenException("", e.getMessage());
				}

				((VisitActivity) eventParcel.getSourceActivity()).dialog = ProgressDialog
						.show(((VisitActivity) eventParcel.getSourceActivity()), "", ResourceParser.getResourceString(
								((VisitActivity) eventParcel.getSourceActivity()), R.string.data_uploading), true);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((VisitActivity) eventParcel.getSourceActivity()), e.getMessage());
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

		} else if (eventParcel.getuIEvent() == UIEvent.RESUBMIT_BUTTON_CLICK) {
			try {
				try {
					FormController formController = new FormController();
					formController.addListener(this);
					formController.doAction(eventParcel);
				} catch (GenException e) {
					LOG.error("doActionOnEvent_RESUBMIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_RESUBMIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw new GenException("" + "Err0108", e.getMessage());
				} // TechnicalReviewDetailsActivity --> CommentActivity
				((CommentActivity) eventParcel.getSourceActivity()).dialog = ProgressDialog
						.show(((CommentActivity) eventParcel.getSourceActivity()), "", ResourceParser.getResourceString(
								((CommentActivity) eventParcel.getSourceActivity()), R.string.data_uploading), true);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_RESUBMIT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((CommentActivity) eventParcel.getSourceActivity()), e.getMessage());
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_RESUBMIT_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_RESUBMIT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.LOGIN_BUTTON_CLICK) {
			try {
				try {
					((LoginActivity) eventParcel.getSourceActivity()).dialog = ProgressDialog.show(
							((LoginActivity) eventParcel.getSourceActivity()), "", ResourceParser.getResourceString(
									((LoginActivity) eventParcel.getSourceActivity()), R.string.login_message),
							true);
					// ((LoginActivity)
					// eventParcel.getSourceActivity()).dialog.setCanceledOnTouchOutside(true);

					LoginController loginController = new LoginController();
					loginController.addListener(this);
					loginController.doAction(eventParcel);
				} catch (GenException e) {
					LOG.error("doActionOnEvent_LOGIN_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_LOGIN_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}

			} catch (GenException e) {
				LOG.error("doActionOnEvent_LOGIN_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((LoginActivity) eventParcel.getSourceActivity()), e.getMessage());
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_LOGIN_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}

			} catch (Exception e) {
				LOG.error("doActionOnEvent_LOGIN_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

		} else if (eventParcel.getuIEvent() == UIEvent.FILECOPY_BUTTON_CLICK) {
			try {
				FileCopyController fileCopyController = new FileCopyController();
				fileCopyController.addListener(this);
				fileCopyController.doAction(eventParcel);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_FILECOPY_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_FILECOPY_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_FILECOPY_BUTTON_CLICK) {
			try {
				FileCopyController fileCopyController = new FileCopyController();
				fileCopyController.addListener(this);
				fileCopyController.doAction(eventParcel);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_TECHNICALREVIEW_FILECOPY_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_TECHNICALREVIEW_FILECOPY_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.B_X_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.ACCIDENTDETAILS_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.INSURANCEDETAILS_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.INSURANCEDETAILS_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.DRIVERDETAILS_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.VEHICLEDETAILS_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.VEHICLEDETAILS_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.OTHERITEMS_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.OTHERITEMS_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_NEXT_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.COMMENTFORM_BACK_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.DATA_RETRIEVE_BUTTON_CLICK) {
			try {
				try {
					DataRetrieveController dataRetrieveController = new DataRetrieveController();
					dataRetrieveController.addListener(this);
					dataRetrieveController.doAction(eventParcel);

				} catch (GenException e) {
					LOG.error("doActionOnEvent_DATA_RETRIEVE_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_DATA_RETRIEVE_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					throw new GenException("", e.getMessage());
				}
				String cName = eventParcel.getSourceActivity().getClass().getSimpleName();
				if (cName.equalsIgnoreCase("SearchListActivity")) {
					((SearchListActivity) eventParcel.getSourceActivity()).progressDialog = ProgressDialog
							.show(((SearchListActivity) eventParcel.getSourceActivity()), "",
									ResourceParser.getResourceString(
											((SearchListActivity) eventParcel.getSourceActivity()),
											R.string.data_retrieving_message),
									true);
				} else {
					/*
					 * ((SearchActivity) eventParcel.getSourceActivity()).dialog
					 * = ProgressDialog .show(((SearchActivity)
					 * eventParcel.getSourceActivity()), "", ResourceParser
					 * .getResourceString(((SearchActivity)
					 * eventParcel.getSourceActivity()),
					 * R.string.data_retrieving_message), true);
					 */
				}
			} catch (GenException e) {
				LOG.error("doActionOnEvent_DATA_RETRIEVE_SUBMIT_BUTTON_CLICK : 1");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((SearchListActivity) eventParcel.getSourceActivity()), e.getMessage());
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_DATA_RETRIEVE_SUBMIT_BUTTON_CLICK : 2");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_DATA_RETRIEVE_SUBMIT_BUTTON_CLICK : 3");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

		} else if (eventParcel.getuIEvent() == UIEvent.SEARCH_BUTTON_CLICK) {

			try {
				try {
					((SearchActivity) eventParcel.getSourceActivity()).dialog = ProgressDialog.show(
							((SearchActivity) eventParcel.getSourceActivity()), "",
							ResourceParser.getResourceString(((SearchActivity) eventParcel.getSourceActivity()),
									R.string.searching_message),
							true, false);

					SearchController searchController = new SearchController();
					searchController.addListener(this);
					searchController.doAction(eventParcel);
				} catch (GenException e) {
					LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
					throw e;
				} catch (Exception e) {
					LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
					LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			} catch (GenException e) {
				LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				try {
					alertShow(((SearchActivity) eventParcel.getSourceActivity()), e.getMessage());
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				} catch (Exception e1) {
					LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
					if (e != null) {
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_SEARCH_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
			}

		} else if (eventParcel.getuIEvent() == UIEvent.HOME_SEARCH_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.LOGOUT_BUTTON_CLICK) {
			try {
				LogoutController logoutController = new LogoutController();
				logoutController.doAction(eventParcel);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_LOGOUT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_LOGOUT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.CANCEL_FORM) {
			cancelForm(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_PENDINGJOBS_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_VISITS_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_DRAFTS_BUTTON_CLICK) {
			this.listenerUICallbackUI(eventParcel);
		} else if (eventParcel.getuIEvent() == UIEvent.FUTURE_BUTTON_CLICK) {
			try {
				FutureController futureController = new FutureController();
				futureController.addListener(this);
				futureController.doAction(eventParcel);
			} catch (GenException e) {
				LOG.error("doActionOnEvent_FUTURE_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_FUTURE_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}
	}

	@Override
	public void listenerUICallbackUI(EventParcel eventParcel) {

		if (eventParcel.getuIEvent() == UIEvent.LOGIN_BUTTON_CLICK) {
			try {
				if (((UserObject) eventParcel.getDataObject()).getStatus().equals("0")) {
					try {
						// Suren Manawatta
						// Save user details to users table to use when app is
						// in offline mode
						DBService.saveUserDetails(eventParcel);

						try {
							WebServiceObject wObj = ((UserObject) eventParcel.getDataObject()).getWebServiceObject();
							if (wObj != null) {
								String appVer = eventParcel.getSourceActivity().getPackageManager().getPackageInfo(
										eventParcel.getSourceActivity().getPackageName(), 0).versionName;
								String miniVersion = wObj.getMinimumSupportedAppVersion();
								Uri appUrl = wObj.getGooglePlayAppURL();
								String storeVersion = wObj.getLatestAppVersioninGooglePlay();

								appVer = appVer.replace(".", "");
								int appVersion = Integer.parseInt(appVer);
								miniVersion = miniVersion.replace(".", "");
								int miniSupVersion = Integer.parseInt(miniVersion);
								storeVersion = storeVersion.replace(".", "");
								int playVersion = Integer.parseInt(storeVersion);

								String msg = "There is a newer version(" + wObj.getLatestAppVersioninGooglePlay()
										+ ") is available in Google Play. Downloading this version is mandatory.";

								if (appVersion < miniSupVersion) {
									// Force the user
									alertShowYesNo(((LoginActivity) eventParcel.getSourceActivity()), msg, appUrl,
											false);
									return;
								}

								if (appVersion >= miniSupVersion && appVersion < playVersion) {
									if (wObj.getForceLatestVersionToUsers()) {
										// Force the user
										alertShowYesNo(((LoginActivity) eventParcel.getSourceActivity()), msg, appUrl,
												false);
										return;
									} else {
										// Warn the user
										msg = "There is a newer version(" + wObj.getLatestAppVersioninGooglePlay()
												+ ") is available in Google Play. Would you like to download it now?";
										alertShowYesNo(((LoginActivity) eventParcel.getSourceActivity()), msg, appUrl,
												true);
										return;
									}
								}
							} // wObj != null
						} catch (Exception e1) {
							if (e1 != null) {
								LOG.error("Message: " + e1.getMessage());
								LOG.error("StackTrace: " + e1.getStackTrace());
							}
						} finally {
							((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
						}

						Intent mIntent = new Intent(eventParcel.getSourceActivity(), HomeActivity.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						eventParcel.getSourceActivity().startActivity(mIntent);

						((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
						// Finish the login activity after successful login.
						((LoginActivity) eventParcel.getSourceActivity()).finish();
						
					} catch (Exception e) {
						((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
						if (e != null) {
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}
					}finally {						
						int x = 0;
						while (x < 3) {
							try {
								Application.startImageUploder();
								break;
							} catch (Exception e) {
								x++;
								LOG.error("ApplicationImageUploder");
								if (e != null) {
									LOG.error("Message: " + e.getMessage());
									LOG.error("StackTrace: " + e.getStackTrace());
								}
								continue;
							}
						}
					}
				} else if (!((UserObject) eventParcel.getDataObject()).getStatus().equals("0")) {
					try {
						((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

						// 2013-05-14 Suren Manawatta
						String[] errCodes = eventParcel.getSourceActivity().getResources()
								.getStringArray(R.array.errCodes);

						if (Arrays.asList(errCodes).contains(((UserObject) eventParcel.getDataObject()).getStatus())) {
							goOffline(eventParcel.getSourceActivity(), (UserObject) eventParcel.getDataObject(), false);
						} else {
							final String errorMessage = ((UserObject) eventParcel.getDataObject())
									.getAlertDescription();
							alertShow(((LoginActivity) eventParcel.getSourceActivity()), errorMessage);
						}
						// goOffline(eventParcel.getSourceActivity(),
						// (UserObject)eventParcel.getDataObject(), false);
						eventParcel.setDataObject(null);
					} catch (Exception e) {
						((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
						goOffline(eventParcel.getSourceActivity(), (UserObject) eventParcel.getDataObject(), false);
						if (e != null) {
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}
					}
				}
			} catch (Exception e) {
				((LoginActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				goOffline(eventParcel.getSourceActivity(), (UserObject) eventParcel.getDataObject(), false);
				LOG.error("doActionOnEvent_LOGIN_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

			/* No longer use */
		} else if (eventParcel.getuIEvent() == UIEvent.FILECOPY_BUTTON_CLICK) {
			String className = "";
			try {
				className = eventParcel.getSourceActivity().getClass().getSimpleName();
				if (className.equalsIgnoreCase("HomeActivity"))
					((HomeActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				else
					((TechnicalReviewDetailsActivity) eventParcel.getSourceActivity()).dialog.dismiss();
			} catch (Exception e) {

				LOG.error("doActionOnEvent_FILECOPY_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				if (className.equalsIgnoreCase("HomeActivity"))
					((HomeActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				else
					((TechnicalReviewDetailsActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
			}
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_FILECOPY_BUTTON_CLICK) {
			try {
				((TechnicalReviewDetailsActivity) eventParcel.getSourceActivity()).dialog.dismiss();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_TECHNICALREVIEW_FILECOPY_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.B_X_BUTTON_CLICK) {
			try {
				/**
				 * This is the place where Form Object used for new motor claim
				 * form is created.
				 */
				FormObject formObject = Application.createFormObjectInstance();
				eventParcel.setDataObject(formObject);
				formObject.setEditable(true);

				formObject.setisDRAFT(true);
				formObject.setisSEARCH(false);
				formObject.setisSMS(false);

				formObject.setDraftFileName("");

				// set time visited;
				// SimpleDateFormat formatter;
				// formatter = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
				// String date =
				// formatter.format(Calendar.getInstance().getTime());

				Intent mIntent = new Intent(eventParcel.getSourceActivity(), AccidentDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
			} catch (Exception e) {
				LOG.error("doActionOnEvent_B_X_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.ACCIDENTDETAILS_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), InsuranceDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_ACCIDENTDETAILS_NEXT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.INSURANCEDETAILS_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), AccidentDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_INSURANCEDETAILS_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.INSURANCEDETAILS_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), DriverDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_INSURANCEDETAILS_NEXT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.DRIVERDETAILS_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), InsuranceDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_DRIVERDETAILS_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), VehicleDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_DRIVERDETAILS_NEXT_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.VEHICLEDETAILS_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), DriverDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_VEHICLEDETAILS_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.VEHICLEDETAILS_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), OtheritemsDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_VEHICLEDETAILS_NEXT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.OTHERITEMS_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), VehicleDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_OTHERITEMS_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.OTHERITEMS_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), TechnicalReviewDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_OTHERITEMS_NEXT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_PENDINGJOBS_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), DraftActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
			} catch (Exception e) {
				LOG.error("doActionOnEvent_HOME_PENDINGJOBS_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_VISITS_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), VisitActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
			} catch (Exception e) {
				LOG.error("doActionOnEvent_HOME_VISITS_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_DRAFTS_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), DraftActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
			} catch (Exception e) {
				LOG.error("doActionOnEvent_HOME_DRAFTS_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.LOGOUT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), LoginActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				// This closing activity should be a HomeActivity.
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_LOGOUT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), OtheritemsDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_TECHNICALREVIEW_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.TECHNICALREVIEW_NEXT_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), CommentActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_TECHNICALREVIEW_NEXT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.COMMENTFORM_BACK_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), TechnicalReviewDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_COMMENTFORM_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.FUTURE_BUTTON_CLICK) {
			try {
				Intent mIntent = new Intent(eventParcel.getSourceActivity(), VehicleDetailsActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
				eventParcel.getSourceActivity().finish();
			} catch (Exception e) {
				LOG.error("doActionOnEvent_FUTURE_BACK_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.DATA_RETRIEVE_BUTTON_CLICK) {
			// Add Logic for data retrieving
			String className = eventParcel.getSourceActivity().getClass().getSimpleName();
			try {
				if (className.equalsIgnoreCase("SearchListActivity"))
					((SearchListActivity) eventParcel.getSourceActivity()).getProgressDialog().dismiss();
				if (className.equalsIgnoreCase("SearchActivity"))
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				if (className.equalsIgnoreCase("VisitActivity"))
					((VisitActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

				if (eventParcel.getDataObject().getStatus().equals("0")) {

					if (!isVisit) { // SA Form
						Application.setFormObjectInstance(
								((DataRetrieveUIObject) eventParcel.getDataObject()).getFormObject());
						// Editable settings
						Application.getFormObjectInstance().setEditable(false);
						Application.getFormObjectInstance().setisVehicleShow(false);
						Application.getFormObjectInstance().setisSMS(false);
						Application.getFormObjectInstance().setisDRAFT(false);
						Application.getFormObjectInstance().setisSEARCH(true);
						Intent mIntent = new Intent(eventParcel.getSourceActivity(), AccidentDetailsActivity.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						eventParcel.getSourceActivity().startActivity(mIntent);
						// eventParcel.getSourceActivity().finish();
					} else {
						Application.setVisitObjectInstance(
								((DataRetrieveUIObject) eventParcel.getDataObject()).getVisitObject());
						// Editable settings
						Application.getVisitObjectInstance().setisSMS(false);
						Application.getVisitObjectInstance().setisDRAFT(false);
						Application.getVisitObjectInstance().setisSEARCH(true);
						Intent mIntent = new Intent(eventParcel.getSourceActivity(), VisitActivity.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						eventParcel.getSourceActivity().startActivity(mIntent);
						// eventParcel.getSourceActivity().finish();
					}

				} else if (!eventParcel.getDataObject().getStatus().equals("0")) {
					/*
					 * ((SearchListActivity) eventParcel.getSourceActivity())
					 * .getProgressDialog().dismiss();
					 */

					String errorMessage = ((DataRetrieveUIObject) eventParcel.getDataObject()).getAlertDescription();
					alertShow((eventParcel.getSourceActivity()), errorMessage);

					if (!isVisit) { // SA Form
						// Editable settings
						Application.getFormObjectInstance().setEditable(false);
						Application.getFormObjectInstance().setisVehicleShow(false);
						Application.getFormObjectInstance().setisSMS(false);
						Application.getFormObjectInstance().setisDRAFT(false);
						Application.getFormObjectInstance().setisSEARCH(true);
					} else {
						// Editable settings
						Application.getVisitObjectInstance().setisSMS(false);
						Application.getVisitObjectInstance().setisDRAFT(false);
						Application.getVisitObjectInstance().setisSEARCH(true);
					}

					// you can't do this shit! here.
					/*
					 * Intent mIntent = new
					 * Intent(eventParcel.getSourceActivity(),
					 * SearchActivity.class);
					 * mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					 * eventParcel.getSourceActivity().startActivity(mIntent);
					 * eventParcel.getSourceActivity().finish();
					 */
				}
			} catch (Exception e) {
				if (className.equalsIgnoreCase("SearchListActivity"))
					((SearchListActivity) eventParcel.getSourceActivity()).getProgressDialog().dismiss();
				if (className.equalsIgnoreCase("SearchActivity"))
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				if (className.equalsIgnoreCase("VisitActivity"))
					((VisitActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

				LOG.error("doActionOnEvent_DATA_RETRIEVE_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}
		// ON Cancel button click on final page (Comment Page), Go to home.
		else if (eventParcel.getuIEvent() == UIEvent.CANCEL_FORM) {
			try {
				// Below lines commented on 2013-03-25
				// Added to serializeFormData function on FormObjSerializer.java
				// final String actName =
				// eventParcel.getSourceActivity().getClass().getSimpleName();
				// if (actName.equalsIgnoreCase("VisitActivity")) {
				// Application.deleteVisitObjectInstance();
				// Application.deleteVisitObjectDraftInstance();
				eventParcel.setDataObject(null); // don't know what to do with
													// is on visit section
				// } else {
				// Below 2 lines commented on 2013-03-25
				// Added to serializeFormData function on FormObjSerializer.java
				/// Application.deleteFormObjectInstance();
				/// Application.deleteFormObjectDraftInstance();
				eventParcel.setDataObject(null);

				// Editable Settings
				// Application.createFormObjectInstance().setEditable(true);
				// Application.getFormObjectInstance().setisVehicleShow(true);
				// }
				/*
				 * Intent mIntent = new Intent(eventParcel.getSourceActivity(),
				 * HomeActivity.class);
				 * mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				 * eventParcel.getSourceActivity().startActivity(mIntent);
				 */
				eventParcel.getSourceActivity().finish();
				/*
				 * try { eventParcel.getSourceActivity().finish(); } catch
				 * (Exception e) { Intent mIntent = new
				 * Intent(eventParcel.getSourceActivity(), HomeActivity.class);
				 * mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				 * eventParcel.getSourceActivity().startActivity(mIntent);
				 * eventParcel.getSourceActivity().finish(); }
				 */

			} catch (Exception e) {
				LOG.error("doActionOnEvent_CANCEL_FORM");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.HOME_SEARCH_BUTTON_CLICK) {
			try {
				// Creating search UI object when click the search button from
				// home
				SearchUIobject searchUIobject = Application.createSearhUIobjectInstance();
				eventParcel.setDataObject(searchUIobject);

				Intent mIntent = new Intent(eventParcel.getSourceActivity(), SearchActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				eventParcel.getSourceActivity().startActivity(mIntent);
			} catch (Exception e) {
				LOG.error("doActionOnEvent_HOME_SEARCH_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.SEARCH_BUTTON_CLICK) {
			try {
				// ((SearchActivity)
				// eventParcel.getSourceActivity()).getDialog().dismiss();
				if (eventParcel.getDataObject().getStatus().equals("0")) {
					SearchUIobject searchUIobject = getSearhUIobjectInstance();
					ArrayList<String> DATA = searchUIobject.getSearchedJobs();

					DataRetrieveUIObject dataRetrieveUIObject = new DataRetrieveUIObject();

					if (DATA.size() == 0) {
						((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
						alertShow(eventParcel.getSourceActivity(),
								eventParcel.getSourceActivity().getString(R.string.no_records));
					} else {
						if (isInVisitPage) { // In Visit Creation page
							((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

							if (DATA.size() == 1) {
								setIsVisit(true);
								VisitObject vObj = createVisitObjectInstance();
								vObj.setJobNo(DATA.get(0).split("_")[0].trim());
								List<NameValuePair> tmpList = getJobOrVehicleNoWithVisitId();
								vObj.setVisitId(tmpList.get(0).getName());
								vObj.setVehicleNo(DATA.get(0).split("_")[1].trim());
								vObj.setisSMS(false);
								vObj.setisDRAFT(true);
								vObj.setisSEARCH(false);
								Intent mIntent = new Intent(eventParcel.getSourceActivity(), VisitActivity.class);

								mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								eventParcel.getSourceActivity().startActivity(mIntent);
								// eventParcel.getSourceActivity().finish();
							} else {
								Intent mIntent = new Intent(eventParcel.getSourceActivity(), SearchListActivity.class);

								mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								eventParcel.getSourceActivity().startActivity(mIntent);
								eventParcel.getSourceActivity().finish();
							}
						} else { // In search page
							if (DATA.size() == 2) {
								setIsVisit(false);
								List<NameValuePair> tmpList = getJobOrVehicleNoWithVisitId();
								dataRetrieveUIObject.setVisitId(tmpList.get(1).getName());
								doActionOnEvent(new EventParcel(UIEvent.DATA_RETRIEVE_BUTTON_CLICK,
										eventParcel.getSourceActivity(), dataRetrieveUIObject));
							} else {
								((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
								Intent mIntent = new Intent(eventParcel.getSourceActivity(), SearchListActivity.class);

								mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								eventParcel.getSourceActivity().startActivity(mIntent);
								eventParcel.getSourceActivity().finish();
							}
						}
					}
				} else if (!eventParcel.getDataObject().getStatus().equals("0")) {
					((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
					String errorMessage = ((SearchUIobject) eventParcel.getDataObject()).getAlertDescription();
					alertShow(((SearchActivity) eventParcel.getSourceActivity()), errorMessage);
				}
			} catch (Exception e) {
				((SearchActivity) eventParcel.getSourceActivity()).getDialog().dismiss();
				LOG.error("doActionOnEvent_SEARCH_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}

		} else if (eventParcel.getuIEvent() == UIEvent.SUBMIT_BUTTON_CLICK) {
			try { // TechnicalReviewDetailsActivity --> CommentActivity
				if (eventParcel.getDataObject().getStatus().equals("0")) {
					((CommentActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					// Send Submit SMS
					SMSProcessor.sendSMS(eventParcel.getSourceActivity().getString(R.string.CALL_CENTER_NO),
							eventParcel.getSourceActivity().getString(R.string.mtabsubmit_) + formObject.getJobNo(),
							(CommentActivity) eventParcel.getSourceActivity(),
							((CommentActivity) eventParcel.getSourceActivity()).getBaseContext());

					eventParcel.setDataObject(null);

					Intent mIntent = new Intent(eventParcel.getSourceActivity(), HomeActivity.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					eventParcel.getSourceActivity().startActivity(mIntent);
					eventParcel.getSourceActivity().finish();

					// Delete the relevant draft
					FileOperations
							.draftFileDeleterUsingFilename(Application.getFormObjectInstance().getDraftFileName());
					Application.deleteFormObjectInstance();
					Application.deleteFormObjectDraftInstance();
					LOG.info("ORGSUBMIT: Deleted FormObject and The Draft File.");

				} else if (!eventParcel.getDataObject().getStatus().equals("0")) {
					((CommentActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					String errorMessage = ((FormObject) eventParcel.getDataObject()).getAlertDescription();
					alertShow(((CommentActivity) eventParcel.getSourceActivity()), errorMessage);
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.VISIT_SUBMIT_BUTTON_CLICK) {
			try {
				if (eventParcel.getDataObject().getStatus().equals("0")) {
					((VisitActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					// Send Submit SMS
					/*
					 * SMSProcessor.sendSMS(eventParcel.getSourceActivity()
					 * .getString(R.string.CALL_CENTER_NO), eventParcel
					 * .getSourceActivity() .getString(R.string.mtabsubmit_) +
					 * formObject.getJobNo(), (CommentActivity)
					 * eventParcel.getSourceActivity(), ((CommentActivity)
					 * eventParcel.getSourceActivity()) .getBaseContext());
					 */

					eventParcel.setDataObject(null);

					Intent mIntent = new Intent(eventParcel.getSourceActivity(), HomeActivity.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					eventParcel.getSourceActivity().startActivity(mIntent);
					eventParcel.getSourceActivity().finish();

					// Delete the relevant draft
					FileOperations.draftFileDeleterUsingFilenameForVisits(
							Application.getVisitObjectInstance().getDraftFileName());
					Application.deleteVisitObjectInstance();
					Application.deleteVisitObjectDraftInstance();
					LOG.info("VISITSUBMIT: Deleted VisitObject and The Draft File.");

				} else if (!eventParcel.getDataObject().getStatus().equals("0")) {
					((VisitActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					String errorMessage = ((VisitObject) eventParcel.getDataObject()).getAlertDescription();
					alertShow(((VisitActivity) eventParcel.getSourceActivity()), errorMessage);
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_VISIT_SUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		} else if (eventParcel.getuIEvent() == UIEvent.RESUBMIT_BUTTON_CLICK) {
			try { // TechnicalReviewDetailsActivity --> CommentActivity
				if (eventParcel.getDataObject().getStatus().equals("0")) {
					((CommentActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					Intent mIntent = new Intent(eventParcel.getSourceActivity(), HomeActivity.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					eventParcel.getSourceActivity().startActivity(mIntent);
					eventParcel.getSourceActivity().finish();

					// delete the relevant draft
					FileOperations
							.draftFileDeleterUsingFilename(Application.getFormObjectInstance().getDraftFileName());
					Application.deleteFormObjectInstance();
					Application.deleteFormObjectDraftInstance();
					LOG.info("RESUBMIT: Deleted FormObject and The Draft File.");

				} else if (!eventParcel.getDataObject().getStatus().equals("0")) {
					((CommentActivity) eventParcel.getSourceActivity()).getDialog().dismiss();

					String errorMessage = ((FormObject) eventParcel.getDataObject()).getAlertDescription();
					alertShow(((CommentActivity) eventParcel.getSourceActivity()), errorMessage);
				}
			} catch (Exception e) {
				LOG.error("doActionOnEvent_RESUBMIT_BUTTON_CLICK");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}
	}
}