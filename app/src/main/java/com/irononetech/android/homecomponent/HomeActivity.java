package com.irononetech.android.homecomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.EntityResponse;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebService;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.enums.imageUploadCategory;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.view.DraftActivity;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.searchcomponent.SearchUIobject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.enums.uploadImageSelectionTwo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends Activity {
	Logger LOG = LoggerFactory.getLogger(HomeActivity.class);
	public ProgressDialog dialog;

	String[] uploadCategoryList;
	public static int preSelected = 0;
	public static int preSelectedImageType = 0;
	private String SD_CARD_TEMP_DIR;
	private File storageDir;

	public synchronized ProgressDialog getDialog() {
		return dialog;
	}

	public synchronized void setDialog(ProgressDialog pd) {
		dialog = pd;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOG.debug("ENTRY onCreate");
		try {
			super.onCreate(savedInstanceState);

			// overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			setContentView(R.layout.home);
			LOG.debug("SUCCESS onCreate");

			new GetMaximumDraftCount().execute();

		} catch (Exception e) {
			LOG.error("onCreate:10151");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	@Override
	protected void onPause() {
		try {
			LOG.debug("ENTRY onPause");
			// overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			super.onPause();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onCreate:10152");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void on_motor_claim_form_button_click(View v) {
		try {
			LOG.debug("ENTRY on_motor_claim_form_button_click");
			Application.setIsInVisitSearchPage(false);
			if (FileOperations.draftsMaxReached(this)) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle("Draft Alert:");
				alertbox.setMessage(getString(R.string.max_no_of_drafts_has_been_reached_please_deal_with_some_drafts_to_continue_));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(HomeActivity.this,
										DraftActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								HomeActivity.this.startActivity(intent);
								// finish();
							}
						});
				alertbox.show();
			//temporarly warning message is stopped as requested via phone on 10/7/2019
			/*} else if (FileOperations.draftsMaxReachedWarning(this)) { // 1 more to reach
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle(R.string.draft_exceed_warning_);
				alertbox.setMessage(getString(R.string.draft_warn_msg2));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Application.getInstance().doActionOnEvent(
										new EventParcel(
												UIEvent.B_X_BUTTON_CLICK,
												HomeActivity.this, null));
							}
						});
				alertbox.show();
			} else if (FileOperations.draftsWarningMsg(this)) { // 2 more to reach
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle(R.string.draft_exceed_warning_);
				alertbox.setMessage(getString(R.string.draft_warn_msg));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Application.getInstance().doActionOnEvent(
										new EventParcel(
												UIEvent.B_X_BUTTON_CLICK,
												HomeActivity.this, null));
							}
						});
				alertbox.show();*/
			} else {
				Application.getInstance().doActionOnEvent(
						new EventParcel(UIEvent.B_X_BUTTON_CLICK, this, null));
			}
			LOG.debug("SUCCESS on_motor_claim_form_button_click");
		} catch (Exception e) {
			LOG.error("on_motor_claim_form_button_click:10153");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_visit_button(View V) {
		try {
			LOG.debug("ENTRY on_click_visit_button");
			if (FileOperations.draftsMaxReachedForVisits(this)) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle("Draft Alert:");
				alertbox.setMessage(getString(R.string.max_no_of_drafts_has_been_reached_please_deal_with_some_drafts_to_continue_));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(HomeActivity.this,
										DraftActivity.class);
								intent.putExtra("TAB", "1");
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								HomeActivity.this.startActivity(intent);
								// finish();
							}
						});
				alertbox.show();
			//draft count warning message is temporarily stopped as requested via phone on 10/7/2019
			/*} else if (FileOperations.draftsMaxReachedWarningForVisits(this)) { // 2 more to go
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle(R.string.draft_exceed_warning_);
				alertbox.setMessage(getString(R.string.draft_warn_msg2));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// Application.getInstance().doActionOnEvent(new
								// EventParcel(UIEvent.B_X_BUTTON_CLICK,
								// HomeActivity.this, null));
								commonVisitViewCode();
							}
						});
				alertbox.show();
			} else if (FileOperations.draftsWarningMsgForVisits(this)) { // 1 more to go
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						HomeActivity.this);
				alertbox.setTitle(R.string.draft_exceed_warning_);
				alertbox.setMessage(getString(R.string.draft_warn_msg));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// Application.getInstance().doActionOnEvent(new
								// EventParcel(UIEvent.B_X_BUTTON_CLICK,
								// HomeActivity.this, null));
								commonVisitViewCode();
							}
						});
				alertbox.show();*/
			} else {
				commonVisitViewCode();
				// Application.getInstance().doActionOnEvent(new
				// EventParcel(UIEvent.B_X_BUTTON_CLICK, this, null));
			}

			LOG.debug("SUCCESS  on_click_visit_button");

		} catch (Exception e) {
			 LOG.error("on_click_visit_button:11156");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

	}

	private void commonVisitViewCode() {
		SearchUIobject searchUIobject = new SearchUIobject();
		searchUIobject.setJobOrVehicleNo("Sample-VehNo");
		Application.setIsInVisitSearchPage(true);
		Application.getInstance().doActionOnEvent(
				new EventParcel(UIEvent.HOME_SEARCH_BUTTON_CLICK, this, null));
	}

	public void on_click_drafts_button(View V) {
		try {
			LOG.debug("ENTRY on_click_drafts_button");
			Application.setIsInVisitSearchPage(false);
			Application.getInstance().doActionOnEvent(
					new EventParcel(UIEvent.HOME_DRAFTS_BUTTON_CLICK, this,
							null));

			LOG.debug("SUCCESS on_click_drafts_button");
		} catch (Exception e) {
			 LOG.error("on_click_drafts_button:11155");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_search_button(View v) {
		try {
			LOG.debug("ENTRY on_click_search_button");
			SearchUIobject searchUIobject = new SearchUIobject();
			searchUIobject.setJobOrVehicleNo("Sample-VehNo");
			Application.setIsInVisitSearchPage(false);
			Application.setIsVisit(false);
			Application.getInstance().doActionOnEvent(
					new EventParcel(UIEvent.HOME_SEARCH_BUTTON_CLICK, this,
							null));
			LOG.debug("SUCCESS on_click_search_button");
		} catch (Exception e) {
			 LOG.error("on_click_search_button:10156");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_copy_images_button(final View v) {
		try{
			Dialog dialog = null;

			LOG.debug("ENTRY on_click_copy_capture_images_button");
			AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(R.array.uploadselectiontype, preSelected,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelected = which;

									uploadImageSelectionTwo fm = uploadImageSelectionTwo.values()[which];
									String selectionCategory = fm.toString();

									on_image_upload(which, v);
									dialog.dismiss();
								}
							});

			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();

		} catch(Exception e){
			LOG.error("on_click_copy_capture_images_button");
		}
	}

	public void on_image_upload(final int mode, final View v){
		try{
			Dialog dialog = null;

			LOG.debug("ENTRY on_click_copy_capture_images_button");
			AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(R.array.uploadImageType, preSelectedImageType,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelectedImageType = which;

									imageUploadCategory fm = imageUploadCategory.values()[which];
									String typeCategory = fm.toString();

									if(mode == 0){
										LOG.debug("ENTRY on_click_capture_images_button");
										if(which == 0){
											SD_CARD_TEMP_DIR = URL.getSLIC_ACCIDENT(); // Get File Path
										}else if(which == 1){
											SD_CARD_TEMP_DIR = URL.getSLIC_DOCUMENTS(); // Get File Path
										}else{
											SD_CARD_TEMP_DIR = URL.getSLIC_COPIED(); // Get File Path
										}
										dispatchTakePictureIntent();

									}else if(mode == 1){
										LOG.debug("ENTRY on_click_copy_images_button");
										Application.setIsInVisitSearchPage(false);
										Application.copyImageCommon(HomeActivity.this, v, which);
										LOG.debug("SUCCESS on_click_copy_images_button");
									}
									dialog.dismiss();
								}
							});

			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();

		} catch(Exception e){
			LOG.error("on_click_copy_capture_images_button");
		}
	}

	public void on_click_logout_button(View v) {
		try {
			final EventParcel ep = new EventParcel(UIEvent.LOGOUT_BUTTON_CLICK,
					this, null);

			AlertDialog.Builder alertbox = new AlertDialog.Builder(
					HomeActivity.this);
			alertbox.setTitle(R.string.logout_confirm_title);
			alertbox.setMessage(getString(R.string.logout_confirm));
			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							LOG.debug("ENTRY on_click_logout_button");
							Application.getInstance().doActionOnEvent(ep);
							Application.stopImgUploder();
							LOG.debug("SUCCESS on_click_logout_button");
						}
					});
			alertbox.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox.show();
		} catch (Exception e) {
			 LOG.error("on_click_logout_button:10157");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	// can remove
	public void on_click_pending_jobs(View V) {
		try {
			LOG.debug("ENTRY on_click_pending_jobs");
			Application.getInstance().doActionOnEvent(
					new EventParcel(UIEvent.HOME_PENDINGJOBS_BUTTON_CLICK,
							this, null));
			LOG.debug("SUCCESS on_click_pending_jobs");
		} catch (Exception e) {
			 LOG.error("on_click_pending_jobs:10155");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
			try{
				Bitmap bitmap = null;

				// Get the dimensions of the bitmap
				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

				// Decode the image file into a Bitmap sized to fill the View
				bmOptions.inJustDecodeBounds = false;
				bmOptions.inPurgeable = true;

				bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

				dispatchTakePictureIntent();
			}
			catch(Exception e){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		} else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_CANCELED){
			File file = new File(currentPhotoPath);
			if(file.exists()){
				file.delete();
			}
		}
	}

	public void saveToSharedPreferenceValue(int draftMaxCount){
		Context context = getApplicationContext();
		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.draftMaxReachedCount), draftMaxCount);
		editor.commit();

	}

	static final int REQUEST_TAKE_PHOTO = 1;
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				LOG.error("Message: " + ex.getMessage());
				LOG.error("StackTrace: " + ex.getStackTrace());
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this,
						BuildConfig.APPLICATION_ID + ".provider",
						photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	//create a file to save the photo taken
	String currentPhotoPath;
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = new File(SD_CARD_TEMP_DIR);

		//create the directory if it is not exist in the path
		if(!storageDir.exists()) storageDir.mkdir();
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = image.getAbsolutePath();
		return image;
	}

	/**
	 *
	 * @author Vimo sanan
	 */
	class GetMaximumDraftCount extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				//TODO
				if(NetworkCheck.isNetworkAvailable(HomeActivity.this)){
					String resultString = WebService.getDraftMaxReached();
					Gson gson = new Gson();
					EntityResponse res = gson.fromJson(resultString, EntityResponse.class);

					String draftCountString = res.getResult().getDraftsCount();
					int draftCount = Integer.parseInt(draftCountString);
					saveToSharedPreferenceValue(draftCount);

					return resultString;
				}else{
					return "";
				}
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String xmlText) {
			try{
				if(xmlText != null) {
//					//xml get response if 0 process and show the popup else show error msg
//					WebServiceObject ws = new WebServiceObject();
//					ws.setXmlText(xmlText);
//					XMLHandler.getResponse(ws);
//					if(ws.getCode().equalsIgnoreCase("0")) {
//						//TODO
//						statList = XMLHandler.getVisitStatusRecords(ws);
//						displayHistory();
//					} else {
//						if(ws.getDescription() != null && !ws.getDescription().isEmpty())
//							displayErrMessage(ws.getDescription());
//						else
//							displayErrMessage(getString(R.string.network_failed_));
//					}
				}else {
//					displayErrMessage(getString(R.string.network_failed_));
				}
			} catch(Exception e){
//				displayErrMessage(getString(R.string.network_failed_));
			} finally{
//				searchBtn.setEnabled(true);
//				progressBar.setVisibility(View.GONE);
			}
		}
	}
}