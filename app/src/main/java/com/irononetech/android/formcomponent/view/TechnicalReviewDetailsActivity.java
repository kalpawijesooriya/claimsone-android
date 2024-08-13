package com.irononetech.android.formcomponent.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.enums.imageCategoryListOne;
import com.irononetech.android.enums.imageCategoryListTwo;
import com.irononetech.android.enums.imageUploadCategory;
import com.irononetech.android.enums.uploadImageSelectionTwo;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.imagedownloadcomponent.BulkImageDownloadController;
import com.irononetech.android.permissions.RequestPermission;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.utilities.JPGFileFilter;

public class TechnicalReviewDetailsActivity extends Activity {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	private static final int COPY_IMAGES_ID = Menu.FIRST;
	private static final int CAMERA_TECHNICAL_VIEW = 125;

	FormObject formObject;
	EditText PAVValue;
	InputMethodManager imm;
	AutoCompleteTextView nearestpolicestation;
	AutoCompleteTextView claimProcessingBranch;
	Button documentsImageAddButton;
	Button documentsImageViewButton;
	Button accidentImageAddButton;
	Button accidentImageViewButton;
	Button backButton;
	Button nextButton;
	Button filecopy;
	Button btnCamera;
	Spinner spinnerpurposeofjourney;
	Spinner spinnerconsistencybycsr;

	//further_review_new
	Spinner spinnerFurtherReviewNeeded;

	String[] processingBranchList = null;
	String[] policeStationList = null;
	public ProgressDialog dialog;

	private int button_clicked = 0;

	String[] imageCategoryList;
	public static int preSelected = 0;
	public static int preSelected2 = 0;
	public static int preSelected3 = 0;

	private String SD_CARD_TEMP_DIR;
	private String TYPE_CATEGORY;

	private static Handler mHandler = new Handler();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			setContentView(R.layout.technicalreview);

			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			formObject = Application.getFormObjectInstance();

			PAVValue = (EditText) findViewById(R.id.pavvaluetext);
			documentsImageAddButton = (Button) findViewById(R.id.documentsimagesbutton);
			documentsImageViewButton = (Button) findViewById(R.id.documentsimagesviewbutton);
			accidentImageAddButton = (Button) findViewById(R.id.accidentimagesaddbutton);
			accidentImageViewButton = (Button) findViewById(R.id.accidentimagesviewbutton);
			backButton = (Button) findViewById(R.id.TechnicalReviewFormBackButton);
			nextButton = (Button) findViewById(R.id.TechnicalReviewFormNextButton);
			btnCamera = (Button) findViewById(R.id.btnCamera);

			//further_review_new
			spinnerFurtherReviewNeeded = (Spinner) findViewById(R.id.further_review_spinner);

			spinnerpurposeofjourney = (Spinner) findViewById(R.id.purpose_of_journey_spinner);
			ArrayAdapter<CharSequence> purposeofjourneyadapter = ArrayAdapter
					.createFromResource(this, R.array.purposeOfJourney,
							R.layout.textviewforspinner);
			purposeofjourneyadapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerpurposeofjourney.setAdapter(purposeofjourneyadapter);
			spinnerpurposeofjourney
					.setOnItemSelectedListener(new MyOnItemSelectedListenerpurposeofjourney());
			spinnerpurposeofjourney.setSelection(Integer.parseInt(formObject
					.getPurposeOfJourney().trim()) - 1);

			nearestpolicestation = (AutoCompleteTextView) findViewById(R.id.nearestpolicestation); // simple_dropdown_item_1line
			claimProcessingBranch = (AutoCompleteTextView) findViewById(R.id.claim_processing_branch);

			//further_review_new
			//spinner implementation for further review needed or not
			ArrayAdapter<CharSequence> adapterFurtherReview = ArrayAdapter.createFromResource(
					this, R.array.furtherReview, R.layout.textviewforspinner);
			adapterFurtherReview.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			//further_review_new
			spinnerFurtherReviewNeeded.setAdapter(adapterFurtherReview);
			spinnerFurtherReviewNeeded
					.setOnItemSelectedListener(new MyOnItemSelectedListenerFurtherReviewed());
			spinnerFurtherReviewNeeded.setSelection(Integer.parseInt(formObject.getIsFurtherReviewNeeded().trim()));

			if(!formObject.getisSEARCH()){
				//////////////////////////////////////
				policeStationList = getResources().getStringArray(R.array.nearestPoliceStation);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.select_dialog_singlechoice,
						policeStationList);

				nearestpolicestation.setAdapter(adapter); // police_station_style
				//////////////////////////////////////
				processingBranchList = getResources().getStringArray(R.array.claimProcessingBranch);

				ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(this,
						android.R.layout.select_dialog_singlechoice,
						processingBranchList);

				claimProcessingBranch.setAdapter(adapter_);
				//////////////////////////////////////
			}

			spinnerconsistencybycsr = (Spinner) findViewById(R.id.consistency_by_csr_spinner);
			ArrayAdapter<CharSequence> consistencybycsradapter = ArrayAdapter
					.createFromResource(this, R.array.consinstency,
							R.layout.textviewforspinner);
			consistencybycsradapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerconsistencybycsr.setAdapter(consistencybycsradapter);
			spinnerconsistencybycsr
					.setOnItemSelectedListener(new MyOnItemSelectedListenerconsistencybycsr());
			spinnerconsistencybycsr.setSelection(Integer.parseInt(formObject
					.getConsistancyByCSR().trim()));

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");

		} catch (NumberFormatException e) {
			LOG.error("onCreate:10508");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		} catch (NotFoundException e) {
			LOG.error("onCreate:10509");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

		} catch (Exception e) {
			LOG.error("onCreate:10599");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
			LOG.debug("ENTRY onPause");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);

			formObject = Application.getFormObjectInstance();
			onLoad();
			formComponentControler();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onResume:10512");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	@Override
	protected void onPause() {
		try {
			super.onPause();
			onSave();
			LOG.debug("ENTRY onResume");
			//onSave();
			System.gc();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onPause:10513");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void onLoad() {
		try {
			// PAVValue:
			PAVValue.setText(formObject.getPAVValue());

			// commentsTxtbox.setText(formObject.getComments());
			spinnerpurposeofjourney.setSelection(Integer.parseInt(formObject
					.getPurposeOfJourney().trim()) - 1);

			// spinnernearestpolicestation.setSelection(Integer.parseInt(formObject.getNearestPoliceStation().trim())-1);
			nearestpolicestation.setText(formObject.getNearestPoliceStation());
			claimProcessingBranch.setText(formObject.getClaimProcessingBranch());

			/*
			 * spinnerclaimprocessbranch.setSelection(Integer.parseInt(formObject
			 * .getClaimProcessBranch().trim()) - 1);
			 */

			spinnerconsistencybycsr.setSelection(Integer.parseInt(formObject
					.getConsistancyByCSR().trim()));

			//further_review_new
			spinnerFurtherReviewNeeded.setSelection(Integer.parseInt(formObject.getIsFurtherReviewNeeded().trim()));
		} catch (NumberFormatException e) {
			LOG.error("onLoad:10514");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void onSave() {
		try {
			// PAVValue:
			formObject.setPAVValue(((PAVValue.getText().toString().trim())));

			// formObject.setComments(commentsTxtbox.getText().toString().trim());

			int pos = spinnerpurposeofjourney.getSelectedItemPosition();
			for (PurposeOfJourneyMapping fm : PurposeOfJourneyMapping.values()) {
				if (spinnerpurposeofjourney.getItemAtPosition(pos).toString()
						.equals(fm.getString())) {
					formObject.setPurposeOfJourney((Integer.toString(fm
							.getInt())));
					break;
				}
			}

			//further_review_new
			int posFurtherReview = spinnerFurtherReviewNeeded.getSelectedItemPosition();
			for (Confirmation fm : Confirmation.values()) {
				if (spinnerFurtherReviewNeeded.getItemAtPosition(posFurtherReview).toString()
						.equals(fm.getString())) {
					formObject.setIsFurtherReviewNeeded(Integer.toString(fm
							.getInt()));
					break;
				}
			}

			// When data is sending id will be assigned and sent accordingly. code is in FormXMLCreator page
			// Applied on both of the following properties
			// 2013-03-19 (for now only setClaimProcessingBranch works like this but setNearestPoliceStation does not)
			formObject.setNearestPoliceStation(((nearestpolicestation.getText()
					.toString().trim())));
			formObject.setClaimProcessingBranch(((claimProcessingBranch
					.getText().toString().trim())));

			/*
			 * int pos2 = spinnerclaimprocessbranch.getSelectedItemPosition();
			 * for (ClaimProcessingBranchMapping fm :
			 * ClaimProcessingBranchMapping .values()) { if
			 * (spinnerclaimprocessbranch.getItemAtPosition(pos2)
			 * .toString().equals(fm.getString())) {
			 * formObject.setClaimProcessBranch((((Integer.toString(fm
			 * .getInt()))))); break; } }
			 */

			int pos3 = spinnerconsistencybycsr.getSelectedItemPosition();
			for (ConsistencyMapping fm : ConsistencyMapping.values()) {
				if (spinnerconsistencybycsr.getItemAtPosition(pos3).toString()
						.equals(fm.getString())) {
					formObject.setConsistancyByCSR(((Integer.toString(fm
							.getInt()))));
					break;
				}
			}

			if (!formObject.getisSEARCH()) {
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave:10515");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void formComponentControler() {
		try {
			if (formObject.getisDRAFT() || formObject.getisSMS()) {/*
				PAVValue.setFocusable(true);
				PAVValue.setFocusableInTouchMode(true);
				PAVValue.setClickable(true);

				spinnerpurposeofjourney.setEnabled(true);
				spinnerpurposeofjourney.setEnabled(true);
				nearestpolicestation.setFocusable(true);
				nearestpolicestation.setFocusableInTouchMode(true);
				nearestpolicestation.setClickable(true);

				claimProcessingBranch.setFocusable(true);
				claimProcessingBranch.setFocusableInTouchMode(true);
				claimProcessingBranch.setClickable(true);

				spinnerconsistencybycsr.setEnabled(true);
				documentsImageAddButton.setEnabled(true);
				documentsImageViewButton.setEnabled(true);
				accidentImageAddButton.setEnabled(true);
				accidentImageViewButton.setEnabled(true);
				filecopy.setVisibility(View.VISIBLE);

			*/}
			if (formObject.getisSEARCH()) {
				PAVValue.setTextColor(Color.GRAY);
				PAVValue.setFocusable(false);
				PAVValue.setFocusableInTouchMode(false);
				PAVValue.setClickable(false);

				spinnerpurposeofjourney.setEnabled(false);
				spinnerpurposeofjourney.setEnabled(false);

				nearestpolicestation.setTextColor(Color.GRAY);
				nearestpolicestation.setFocusable(false);
				nearestpolicestation.setFocusableInTouchMode(false);
				nearestpolicestation.setClickable(false);
				btnCamera.setVisibility(View.GONE);
				btnCamera.setClickable(false);

				claimProcessingBranch.setTextColor(Color.GRAY);
				claimProcessingBranch.setFocusable(false);
				claimProcessingBranch.setFocusableInTouchMode(false);
				claimProcessingBranch.setClickable(false);

				spinnerconsistencybycsr.setEnabled(false);

				//further_review_new
				spinnerFurtherReviewNeeded.setEnabled(false);

				documentsImageAddButton.setVisibility(View.GONE);
				accidentImageAddButton.setVisibility(View.GONE);
				filecopy.setVisibility(View.GONE);
			}

			/*if (!formObject.isEditable()) {
				//accidentImageAddButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_chasisform_add_button_active));
				//documentsImageAddButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_chasisform_add_button_active));
			} else {
				//documentsImageAddButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_button_xml));
			}*/
		} catch (NotFoundException e) {
			LOG.error("formComponentControler:10510");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		} catch (Exception e) {
			LOG.error("formComponentControler:10511");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public synchronized ProgressDialog getDialog() {
		return dialog;
	}

	public synchronized void setDialog(ProgressDialog pd) {
		dialog = pd;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// return false;
			//Application.cancelForm(this);
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick_keyboardClose(View v) {
		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
	}

	public void on_click_copy_images_button(int mode, View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_copy_images_button");
			Application.copyImageCommon(this, v, mode);
			LOG.debug("SUCCESS ", "on_click_copy_images_button");
		} catch (Exception e) {
			LOG.error("on_click_copy_images_button:10517");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
		}
	};


	public void on_click_back_button(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_back_button");
			Application.goForward = false;
			Application.goBackward = true;
			onSave();
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.TECHNICALREVIEW_BACK_BUTTON_CLICK, this, formObject));
			LOG.debug("SUCCESS ",  "on_click_back_button");
		} catch (Exception e) {
			LOG.error("on_click_back_button:10516");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_next_button(View v) {
		String errMsg = null;
		try {
			LOG.debug("ENTRY ", "on_click_next_button");

			if (!getVerified(nearestpolicestation.getText().toString().trim(), policeStationList)) {
				errMsg = "Please select a 'Nearest Police Station' from the list.";
			}
			if (claimProcessingBranch.getText().toString().trim().isEmpty() || !getVerified(claimProcessingBranch.getText().toString().trim(), processingBranchList)) {
				if(errMsg == null) errMsg = "Please select a 'Claim Processing Branch' from the list.";
				else errMsg = errMsg + "\nPlease select a 'Claim Processing Branch' from the list.";
			}

			if(errMsg != null){
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setTitle(R.string.saform);
				alertbox.setMessage(errMsg);
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
				alertbox.show();
			}
			else {
				Application.goForward = true;
				Application.goBackward = false;
				onSave();
				Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.TECHNICALREVIEW_NEXT_BUTTON_CLICK, this, formObject));
				LOG.debug("SUCCESS ",  "on_click_next_button");
			}
		} catch (Exception e) {
			LOG.error("on_click_next_button:10518");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	public void on_click_add_accident_images(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_add_accident_images");
			button_clicked = 3;
			if(RequestPermission.checkPermissionForExternalStorage(this)){
				add_accident_images();
			}
			LOG.debug("SUCCESS ",  "on_click_add_accident_images");
		} catch (Exception e) {
			LOG.error("on_click_add_accident_images:10519");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void add_accident_images() {
		try {
			LOG.debug("ENTRY ",  "add_accident_images");

			if(!Application.checkImageCount(this)) //Image count exceed 400
				return;

			dialog = ProgressDialog.show(TechnicalReviewDetailsActivity.this,"", "Please wait...", true);
			onSave();
			startActivity(new Intent(getApplicationContext(), CustomGalleryJobImagesAdd.class));
			handler.sendEmptyMessage(0);
			LOG.debug("SUCCESS ",  "add_accident_images");
		} catch (Exception e) {
			LOG.error("add_accident_images:10519");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_view_accident_images(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_view_accident_images");
			button_clicked = 4;
			if(RequestPermission.checkPermissionForExternalStorage(this)){
				view_accident_images();
			}
			LOG.debug("SUCCESS ", "on_click_view_accident_images");
		} catch (Exception e) {
			LOG.error("on_click_view_accident_images:10521");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void view_accident_images() {
		try {
			LOG.debug("ENTRY ", "view_accident_images");
			String destinationFolder = formObject.getJobNo();

			if (formObject.getisSEARCH()) {
				LOG.debug("accImage list",formObject.getAccidentImageList());
				if (formObject.getAccidentImageList() != null && formObject.getAccidentImageList().size() > 0) {
					File imgFile;
					ArrayList<String> svrCollection = formObject.getAccidentImageList();
					LOG.debug("returned accident image list",svrCollection);
					ArrayList<String> toBeDownloaded = new ArrayList<String>();
					int count = 0;

					if (svrCollection != null && svrCollection.size() > 0) {
						for (int i = 0; i < svrCollection.size(); i++) {
							imgFile = new File(svrCollection.get(i));

							if (!imgFile.exists()) {
								// Download It
								toBeDownloaded.add(svrCollection.get(i));
							} else {
								count++;
							}
						}
					}

					if (toBeDownloaded != null && toBeDownloaded.size() > 0) {
						dialog = ProgressDialog.show(TechnicalReviewDetailsActivity.this, "", "Downloading. Please wait...", true);

						String filepath = URL.getSLIC_JOBS() + destinationFolder + "/AccImages/";
						Intent intent = new Intent(getBaseContext(), BulkImageDownloadController.class);
						intent.putExtra("MEDIA_SAVE_PATH", filepath);
						intent.putStringArrayListExtra("IMG_LIST", toBeDownloaded);
						backgroundImageDownloder(intent, true);
					}
					if (count > 0 && toBeDownloaded.size() <= 0) {
						getLocalAccidentImagesAndDisplay(destinationFolder);
					}
				} else {
					getLocalAccidentImagesAndDisplay(destinationFolder);
				}
			} else {
				getLocalAccidentImagesAndDisplay(destinationFolder);
			}
			LOG.debug("SUCCESS ", "view_accident_images");
		} catch (Exception e) {
			LOG.error("view_accident_images:10521");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_capture_copy_images(final View v){
		try{
			Dialog dialog = null;

			LOG.debug("ENTRY on_click_copy_capture_images_button");
			AlertDialog.Builder builder = new AlertDialog.Builder(TechnicalReviewDetailsActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(R.array.uploadselectiontype, preSelected2,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelected2 = which;

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
			LOG.error("on_click_copy_capture_images_button_technical_review");
		}
	}

	public void on_image_upload(final int mode, final View v){
		try{
			Dialog dialog = null;

			LOG.debug("ENTRY on_click_copy_capture_images_button");
			AlertDialog.Builder builder = new AlertDialog.Builder(TechnicalReviewDetailsActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(R.array.uploadImageType, preSelected3,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelected3 = which;

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
										on_click_copy_images_button(which, v);
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

	public void on_click_add_document_images(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_add_document_images");
			button_clicked = 1;
			if(RequestPermission.checkPermissionForExternalStorage(this)){
				add_document_images();
			}
			LOG.debug("SUCCESS ",  "on_click_add_document_images");
		} catch (Exception e) {
			LOG.error("on_click_add_document_images:10523");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private void add_document_images() {
		try {
			LOG.debug("ENTRY ",  "add_document_images");
			if(!Application.checkImageCount(this)) {
				LOG.debug("imgCount","Image Count Exceeded");
				return;
			}//Image count exceed 400

			onSave();
			if (!formObject.getisSEARCH()) {
				imageCategoryPopup(1, 1);
			} else {
				imageCategoryPopup(2, 1);
			}
			LOG.debug("SUCCESS ",  "add_document_images");
		} catch (Exception e) {
			LOG.error("add_document_images:10523");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	public void on_click_view_document_images(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_view_document_images");
			button_clicked = 2;
			if(RequestPermission.checkPermissionForExternalStorage(this)){
				view_document_images();
			}
			LOG.debug("SUCCESS ",  "on_click_view_document_images");
		} catch (Exception e) {
			LOG.error("on_click_view_document_images:10524");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void view_document_images() {
		try {
			LOG.debug("ENTRY ",  "view_document_images");
			onSave();
			if (!formObject.getisSEARCH()) {
				imageCategoryPopup(1, 2);
			} else {							//Search
				imageCategoryPopup(1, 2);
				//imageCategoryPopup(2, 2);
			}
			LOG.debug("SUCCESS ",  "view_document_images");
		} catch (Exception e) {
			LOG.error("view_document_images:10524");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	private void imageCategoryPopup(int listno, int addORview) {
		// listno: Value == 1 or 2,  2 is deprecated
		// addORview: 1:Add, 2:View
		try {
			String folderName = null;
			List<File> finalFileList;
			int count = 0;

			if (listno == 1) {
				imageCategoryList = new String[imageCategoryListOne.values().length];
				int i = 0;

				if(formObject.getisSEARCH()) {
					for (imageCategoryListOne fm : imageCategoryListOne.values()) {
						folderName = fm.toString(); //getFolderName(i);
						count = 0;

						if(folderName.equals("DLStatement") && formObject.getDLStatementImageList() != null) {
							count = formObject.getDLStatementImageList().size();
						}
						if(folderName.equals("TechnicalOfficerComments") && formObject.getTechnicalOfficerCommentsImageList() != null) {
							count = formObject.getTechnicalOfficerCommentsImageList().size();
						}
						if(folderName.equals("ClaimFormImage") && formObject.getClaimFormImageImageList() != null) {
							count = formObject.getClaimFormImageImageList().size();
						}

						//if (count > 0) {
						imageCategoryList[i] = fm.getString() + "    (" + count	+ ")";
						//} else {
						//imageCategoryList[i] = fm.getString();
						//}
						i++;
					}
				}else {
					for (imageCategoryListOne fm : imageCategoryListOne.values()) {
						folderName = fm.toString(); //getFolderName(i);
						finalFileList = imageList(folderName);
						count = finalFileList.size();

						if (count > 0) {
							imageCategoryList[i] = fm.getString() + "    (" + count	+ ")";
						} else {
							imageCategoryList[i] = fm.getString();
						}
						i++;
					}
				}
			} else { // list2
				imageCategoryList = new String[15];
				int i = 0;

				for (imageCategoryListTwo fm : imageCategoryListTwo.values()) {
					folderName = fm.toString(); //getFolderName(i);
					finalFileList = imageList(folderName);
					count = finalFileList.size();

					if (count > 0) {
						imageCategoryList[i] = fm.getString() + "    (" + count	+ ")";
					} else {
						imageCategoryList[i] = fm.getString();
					}
					i++;
				}
			}

			Dialog dialog = null;
			final int addView = addORview;
			//String imgCategory = "";

			AlertDialog.Builder builder = new AlertDialog.Builder(TechnicalReviewDetailsActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(imageCategoryList, preSelected,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelected = which;

									imageCategoryListOne fm = imageCategoryListOne.values()[which];
									String imgCategory = fm.toString();
									dialog.dismiss();

									if (addView == 1) { 	// Document Images Add btn
										Intent intent = new Intent(TechnicalReviewDetailsActivity.this, CustomGalleryJobDocsAdd.class);
										intent.putExtra("SELECTION_ID",	imgCategory);
										startActivity(intent);
									} else { 				// Document Images View btn
										if(!imageCategoryList[which].contains("(0)")) {
											Intent intent = new Intent(TechnicalReviewDetailsActivity.this,	CustomGalleryJobDocsView.class);
											intent.putExtra("SELECTION_ID",	imgCategory);
											startActivity(intent);
										}else {
											Toast.makeText(TechnicalReviewDetailsActivity.this, "Selected image category contains no images to display.", Toast.LENGTH_SHORT).show();
										}
									}
								}
							})

					/*
					 * .setPositiveButton(R.string.alert_dialog_ok, new
					 * DialogInterface.OnClickListener() { public void
					 * onClick(DialogInterface dialog, int whichButton) {
					 *
					 * String imgCategory = getFolderName(preSelected);
					 * removeDialog(0);
					 *
					 * if (addView == 1) { // doc img add btn Intent intent =
					 * new Intent( TechnicalReviewDetailsActivity.this,
					 * CustomGalleryJobDocsAdd.class);
					 * intent.putExtra("SELECTION_ID", imgCategory);
					 * startActivity(intent); } else { // doc img view btn
					 * Intent intent = new Intent(
					 * TechnicalReviewDetailsActivity.this,
					 * CustomGalleryJobDocsView.class);
					 * intent.putExtra("SELECTION_ID", imgCategory);
					 * startActivity(intent); } } })
					 */

					.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
											int whichButton) {
							/* User clicked No so do some stuff */
							preSelected = 0;
							removeDialog(0);
						}
					});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		} catch (Exception e) {
			LOG.error("imageCategoryPopup:10525");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	/*private String getFolderName(int preSelected) {
		try {
			String imgCategory = "";
			switch (preSelected) {
			case 0:
				imgCategory = "DLStatement";
				break;
			case 1:
				imgCategory = "TechnicalOfficerComments";
				break;
			case 2:
				imgCategory = "ClaimFormImage";
				break;
			case 3:
				imgCategory = "ARI";
				break;
			case 4:
				imgCategory = "DR";
				break;
			case 5:
				imgCategory = "SeenVisit";
				break;
			case 6:
				imgCategory = "SpecialReport1";
				break;
			case 7:
				imgCategory = "SpecialReport2";
				break;
			case 8:
				imgCategory = "SpecialReport3";
				break;
			case 9:
				imgCategory = "Supplementary1";
				break;
			case 10:
				imgCategory = "Supplementary2";
				break;
			case 11:
				imgCategory = "Supplementary3";
				break;
			case 12:
				imgCategory = "Supplementary4";
				break;
			case 13:
				imgCategory = "Acknowledgment";
				break;
			case 14:
				imgCategory = "SalvageReport";
				break;
			default:
				imgCategory = "Temp";
				break;
			}
			return imgCategory;
		} catch (Exception e) {
			LogFile.d("EXCEPTION ", TAG + "imageCategoryPopup:10526");
			return "";
		}
	}*/

	public List<File> imageList(String foldername) {

		FormObject formObject = Application.getFormObjectInstance();
		String destinationFolder = formObject.getJobNo();

		File f = new File(URL.getSLIC_JOBS() + destinationFolder + "/DocImages/" + foldername);
		List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);

		// String fileNames = "";
		// for (int i = 0; i < fileList.size(); i++) {
		// fileNames += ">>" + fileList.get(i).getPath() + "\n";
		// }
		return fileList;
	}

	private void getLocalAccidentImagesAndDisplay(String destinationFolder) {
		try {
			String filepathImpact = URL.getSLIC_JOBS() + destinationFolder + "/AccImages/";
			ArrayList<String> arr = FileOperations.getFileList(filepathImpact);

			if (arr != null && arr.size() > 0) {
				onSave();
				startActivity(new Intent(getApplicationContext(),
						CustomGalleryJobImagesView.class));
			} else {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						TechnicalReviewDetailsActivity.this);
				alertbox.setTitle(R.string.saform);
				alertbox.setMessage(R.string.no_accident_images_available_);
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				alertbox.show();
			}
		} catch (Exception e) {
			LOG.error("checkLocalFolder:10522");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private boolean getVerified(String text, String[] validWords){
		try {
			LOG.debug("ENTRY ",  "getVerified");
			if(formObject.getisSEARCH()) return true;

			if(text == null || text.isEmpty()) return true;

			String[] lowervalidWords = new String[validWords.length];
			for (int i = 0; i < lowervalidWords.length; i++) {
				lowervalidWords[i] = validWords[i].trim().toLowerCase();
			}

			Arrays.sort(lowervalidWords);
			if (Arrays.binarySearch(lowervalidWords, text.trim().toLowerCase()) >= 0) {
				LOG.debug("SUCCESS ",  "getVerified");
				return true;
			}
			return false;
		} catch (Exception e) {
			LOG.error("getVerified:10518");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			return false;
		}
	}

	//further_review_new
	public class MyOnItemSelectedListenerFurtherReviewed implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (Confirmation fm : Confirmation.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setIsFurtherReviewNeeded(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:11500");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	protected void backgroundImageDownloder(final Intent ii, final boolean isAccidentImages) {
		try {
			// Start lengthy operation in a background thread
			new Thread(new Runnable() {
				public void run() {

					BulkImageDownloadController b = new BulkImageDownloadController();
					b.bulkImageDownloder(ii);

					mHandler.post(new Runnable() {
						public void run() {
							if(isAccidentImages)
								startActivity(new Intent(getApplicationContext(), CustomGalleryJobImagesView.class));
							else
								startActivity(new Intent(getApplicationContext(), CustomGalleryJobDocsView.class));
							handler.sendEmptyMessage(0);
						}
					});
				}
			}).start();
		} catch (Exception e) {
			LOG.error("bgImageDownloder:10520");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			handler.sendEmptyMessage(0);
		}
	}


	public class MyOnItemSelectedListenerpurposeofjourney implements
			OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (PurposeOfJourneyMapping fm : PurposeOfJourneyMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setPurposeOfJourney((Integer.toString(fm.getInt())));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10527");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	// This function is called when you come back to your activity after the intent has finished. Do read android documentation on Google. It will Help
	public void onActivityResult(int requestCode, int resultCode, Intent data){
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

	public class MyOnItemSelectedListenerconsistencybycsr implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (ConsistencyMapping fm : ConsistencyMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setConsistancyByCSR(((Integer.toString(fm.getInt()))));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10529");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case 74: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					if(button_clicked == 1){
						add_document_images();
					}else if(button_clicked == 2){
						view_document_images();
					}else if(button_clicked == 3){
						add_accident_images();
					}else if(button_clicked == 4){
						view_document_images();
					}else{
						//do nothing
					}
				} else {
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}
			// other 'case' lines to check for other
			// permissions this app might request.
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		try {
			super.onCreateOptionsMenu(menu);
			/*if (formObject.isEditable()) {
				menu.add(0, COPY_IMAGES_ID, 0, "Copy Images").setShortcut('3','c');
			}*/
			return true;
		} catch (Exception e) {
			LOG.error("onCreateOptionsMenu:10530");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			return false;
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case COPY_IMAGES_ID:
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}