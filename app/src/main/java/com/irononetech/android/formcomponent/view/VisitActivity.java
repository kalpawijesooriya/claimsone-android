package com.irononetech.android.formcomponent.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebService;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.XML.XMLHandler;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.dataretrievecomponent.DataRetrieveUIObject;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.enums.imageCategoryListThree;
import com.irononetech.android.enums.uploadImageSelectionTwo;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.network.NetworkCheck;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.utilities.JPGFileFilter;
public class VisitActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(VisitActivity.class);
	InputMethodManager imm;
	private static final int COPY_IMAGES_ID = Menu.FIRST;

	VisitObject visitObj;

	TextView jobNo;
	TextView vehicleNo;
	Spinner inspectionType_Spinner;

	TextView dateSeparator1;
	TextView dateSeparator2;

	EditText day;
	EditText month;
	EditText year;

	EditText chassisNo;
	Button chassisNoR;
	EditText engineNo;
	Button engineNoR;

	Button docImagesAdd;
	Button docImagesView;

	ProgressBar progressBar;
	EditText comments;

	Button prevComments;
	Button copyImages;
	Button submit;

	String[] dataforchassisno_reason;
	String[] dataforengineno_reason;
	int preSelected = 0;
	int preSelected1 = 0;
	String[] imageCategoryList;
	static List<NameValuePair> chtList = new ArrayList<NameValuePair>();

	public ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			setContentView(R.layout.activity_visit);

			// Initialize Visit Form
			visitObj = Application.createVisitObjectInstance();
			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			jobNo = (TextView) findViewById(R.id.jobNo);
			vehicleNo = (TextView) findViewById(R.id.vehicleNo);

			dateSeparator1 = (TextView) findViewById(R.id.dateseparator1);
			dateSeparator2 = (TextView) findViewById(R.id.dateseparator2);

			day = (EditText) findViewById(R.id.dateTextbox);
			month = (EditText) findViewById(R.id.monthTextbox);
			year = (EditText) findViewById(R.id.yearTextbox);

			chassisNo = (EditText) findViewById(R.id.chassisNo);
			engineNo = (EditText) findViewById(R.id.engineNo);

			chassisNoR = (Button) findViewById(R.id.chassisno_reason_btn);
			engineNoR = (Button) findViewById(R.id.engineno_reason_btn);

			docImagesAdd = (Button) findViewById(R.id.documentImages_button);
			docImagesView = (Button) findViewById(R.id.documentsImagesView_button);

			progressBar = (ProgressBar) findViewById(R.id.progressBar);
			comments = (EditText) findViewById(R.id.commentsTextbox);

			prevComments = (Button) findViewById(R.id.previousCommentsView_button);
			copyImages = (Button) findViewById(R.id.VisitFormCopyButton);
			submit = (Button) findViewById(R.id.visitActivitySubmitButton);

			progressBar.setVisibility(View.GONE);
			onLoad();
			formComponentControler();
			if (!visitObj.getisSEARCH()) {
				FileOperations.draftRenamerForVisits(visitObj);
				FormObjSerializer.serializeFormDataForVisits(visitObj);
			}

			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:1301");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return;
		}
	}

	@Override
	protected void onPause() {
		LOG.debug("ENTRY onPause");
		try {
			super.onPause();

			LOG.debug("SUCCESS onPause");
			// onSave();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		try {
			LOG.debug("ENTRY onResume");
			//This line needed to activate visit mode after viewing the SA form
			Application.setIsVisit(true);
			visitObj = Application.createVisitObjectInstance();
			formComponentControler();
			super.onResume();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onCreate:1300");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void formComponentControler() {
		try {
			if (visitObj.getisSEARCH()) {
				inspectionType_Spinner.setEnabled(false);
				day.setEnabled(false);
				day.setFocusable(false);
				day.setFocusableInTouchMode(false);
				month.setEnabled(false);
				month.setFocusable(false);
				month.setFocusableInTouchMode(false);
				year.setEnabled(false);
				year.setFocusable(false);
				year.setFocusableInTouchMode(false);

				chassisNo.setFocusable(false);
				chassisNo.setFocusableInTouchMode(false); 
				chassisNo.setClickable(false);
				chassisNoR.setEnabled(false);

				engineNo.setFocusable(false);
				engineNo.setFocusableInTouchMode(false); 
				engineNo.setClickable(false);
				engineNoR.setEnabled(false);

				docImagesAdd.setEnabled(false);

				dateSeparator1.setTextColor(Color.GRAY);
				dateSeparator2.setTextColor(Color.GRAY);

				day.setTextColor(Color.GRAY);
				month.setTextColor(Color.GRAY);
				year.setTextColor(Color.GRAY);
				chassisNo.setTextColor(Color.GRAY);
				chassisNoR.setVisibility(View.GONE);
				engineNo.setTextColor(Color.GRAY);
				engineNoR.setVisibility(View.GONE);
				docImagesAdd.setVisibility(View.GONE);

				comments.setFocusable(false);
				comments.setFocusableInTouchMode(false); 
				comments.setClickable(false);
				comments.setTextColor(Color.GRAY);
				comments.setTextSize(22);

				copyImages.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
			}else {
				int maxLength = 500;
				InputFilter[] fArray = new InputFilter[1];
				fArray[0] = new InputFilter.LengthFilter(maxLength);
				comments.setFilters(fArray);
			}
		} catch (NotFoundException e) {
			LOG.error("formComponentControler:11510");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("formComponentControler:11511");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onLoad() {
		try {
			jobNo.setText(visitObj.getJobNo());
			vehicleNo.setText(visitObj.getVehicleNo());
			// Inspection Type:
			inspectionType_Spinner = (Spinner) findViewById(R.id.inspectionType_spinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.inspectionTypes,
					R.layout.textviewforspinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			inspectionType_Spinner.setAdapter(adapter);
			inspectionType_Spinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerVisitTypeSelector());

			try {
				if(visitObj.getisSEARCH()){
					String inspTypeTxt = visitObj.getInspectionTypeInText();
					String[] inspTypeArr = getResources().getStringArray(R.array.inspectionTypes);
					for (int i = 0; i < inspTypeArr.length; i++) {
						if(inspTypeTxt.equals(inspTypeArr[i])){
							visitObj.setInspectionTypeArrIndex(i);
							inspectionType_Spinner.setSelection(i);
							break;
						}
					}
				}
				else{
					inspectionType_Spinner.setSelection(visitObj.getInspectionTypeArrIndex());
				}

			} catch (Exception e) {
				inspectionType_Spinner.setSelection(0);
			}

			// Inspection Date:
			String fullDate = visitObj.getInspectionDate();
			String datepart = "";
			if (!fullDate.isEmpty()) {
				datepart = fullDate.split(" ")[0];
				day.setText(datepart.split("/")[0]);
				month.setText(datepart.split("/")[1]);
				int startPos = datepart.split("/")[2].length() - 2;
				year.setText(datepart.split("/")[2].substring(startPos));
			}

			chassisNo.setText(visitObj.getChassisNo());
			engineNo.setText(visitObj.getEngineNo());

			comments.setText(visitObj.getComments());

		} catch (Exception e) {
			LOG.error("onLoad:11426");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onSave() {
		try {
			visitObj.setJobNo(jobNo.getText().toString());
			visitObj.setVehicleNo(vehicleNo.getText().toString());

			try {
				inspectionType_Spinner.setSelection(visitObj.getInspectionTypeArrIndex());
			} catch (Exception e) {
				inspectionType_Spinner.setSelection(0);
			}

			if(!(day.getText().toString().trim().isEmpty() && month.getText().toString().trim().isEmpty() && 
					year.getText().toString().trim().isEmpty()))
			{
				visitObj.setInspectionDate((day.getText().toString().trim() + "/"
						+ month.getText().toString().trim() + "/" + year.getText().toString().trim()));
			}else
				visitObj.setInspectionDate("");

			visitObj.setChassisNo(chassisNo.getText().toString().trim());
			visitObj.setEngineNo(engineNo.getText().toString().trim());

			visitObj.setComments(comments.getText().toString().trim());

			if (!visitObj.getisSEARCH()) {
				FileOperations.draftRenamerForVisits(visitObj);
				FormObjSerializer.serializeFormDataForVisits(visitObj);
			}
		} catch (Exception e) {
			LOG.error("onSave:11427");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_view_sa_form(View v){
		try {
			LOG.debug("ENTRY ", "on_click_view_sa_form");
			//If Offline
			//This "Application.getIsOnline() == 0", can't use coz it's always 1 when it comes to visit activity
			if (!NetworkCheck.isNetworkAvailable(this)) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setTitle(getString(R.string.inspec_info_form));
				alertbox.setMessage(getString(R.string.submit_no_network_msg));
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
				return;
			}
			
			Application.setIsVisit(false);
			String vId = visitObj.getVisitId();

			dialog = ProgressDialog.show(VisitActivity.this, "",
					this.getString(R.string.data_retrieving_message) , true);

			DataRetrieveUIObject dataRetrieveUIObject = new DataRetrieveUIObject();
			if(visitObj.getisSEARCH() || vId == null || vId.isEmpty()) {
				List<NameValuePair> visitIdofJob = Application.getJobOrVehicleNoWithVisitId();
				for (NameValuePair pair : visitIdofJob) {
					if(pair.getValue().contains(jobNo.getText().toString())){
						dataRetrieveUIObject.setVisitId(pair.getValue().split("_")[0]);
						break;
					}
				}
			}else {
				dataRetrieveUIObject.setVisitId(vId);
			}

			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DATA_RETRIEVE_BUTTON_CLICK, this, dataRetrieveUIObject));
			LOG.debug("SUCCESS ","on_click_view_sa_form");
		} catch (Exception e) {
			LOG.error("on_click_view_sa_form:11538");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			Application.setIsVisit(true);
			handler.sendEmptyMessage(0);
		}
	}

	public void on_click_chassisno_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_chassisno_reason_btn");
			dataforchassisno_reason = new String[ChassisNoReasonMappingVisits.values().length];
			int i = 0;
			for (ChassisNoReasonMappingVisits fm : ChassisNoReasonMappingVisits.values()) {
				dataforchassisno_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(VisitActivity.this)
			.setTitle("Chassis No Reasons")
			.setSingleChoiceItems(dataforchassisno_reason, preSelected,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					chassisNo.setText("" + dataforchassisno_reason[which]);
					preSelected = which;
					dialog.dismiss();
				}
			})
			.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					chassisNo.setText("");
					preSelected = 0;
					dialog.dismiss();
				}
			})
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,	int whichButton) {
					preSelected = 0;
					removeDialog(0);
				}
			});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			LOG.debug("SUCCESS ",  "on_click_chassisno_reason_btn");
		} catch (Exception e) {
			LOG.error("on_click_chassisno_reason_btn:10538");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_engineno_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_engineno_reason_btn");
			final String[] dataforengineno_reason = new String[EngineNoReasonMapping.values().length];
			int i = 0;
			for (EngineNoReasonMapping fm : EngineNoReasonMapping.values()) {
				dataforengineno_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(VisitActivity.this)
			.setTitle("Engine No Reasons")
			.setSingleChoiceItems(dataforengineno_reason, preSelected1,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,	int which) {
					engineNo.setText(""	+ dataforengineno_reason[which]);
					preSelected1 = which;
					dialog.dismiss();
				}
			})
			.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					engineNo.setText("");
					preSelected1 = 0;
					dialog.dismiss();
				}
			})
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					preSelected1 = 0;
					removeDialog(0);
				}
			});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			LOG.debug("SUCCESS ", "on_click_engineno_reason_btn");
		} catch (Exception e) {
			LOG.error("on_click_engineno_reason_btn:10539");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public void on_click_add_document_images(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_add_document_images");
			onSave();

			if(!Application.checkImageCount(this)) //Image count exceed 400
				return;

			// 1 == Doc Images Add; 2 == Doc Images View
			imageCategoryPopup(1);

			LOG.debug("SUCCESS ",  "on_click_add_document_images");
		} catch (Exception e) {
			LOG.error("on_click_add_document_images:11523");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

	}

	private void imageCategoryPopup(int addORview) {
		// addORview:    add-1, view-2
		try {
			String name;
			List<File> finalFileList;
			int count = 0;

			imageCategoryList = new String[imageCategoryListThree.values().length];
			int i = 0;

			for (imageCategoryListThree fm : imageCategoryListThree.values()) {
				name = getFolderName(i);
				finalFileList = imageList(name);
				count = finalFileList.size();

				//Images to be downloaded
				if(visitObj.getisSEARCH()) {
					switch (i) {
					case 0:
						if(visitObj.getTechnicalOfficerCommentsImageList() != null)
							count = visitObj.getTechnicalOfficerCommentsImageList().size();
						break;
					case 1:
						if(visitObj.getEstimateAnyotherCommentsImageList() != null)
							count = visitObj.getEstimateAnyotherCommentsImageList().size();
						break;
					case 2:
						if(visitObj.getInspectionPhotosSeenVisitsAnyOtherImageList() != null)
							count = visitObj.getInspectionPhotosSeenVisitsAnyOtherImageList().size();
						break;
					default:
						break;
					}
				}

				if(visitObj.getisSEARCH()) {
					imageCategoryList[i] = fm.getString() + "    (" + count	+ ")";
				}else {
					if (count > 0) {
						imageCategoryList[i] = fm.getString() + "    (" + count	+ ")";
					} else {
						imageCategoryList[i] = fm.getString();
					}
				}
				i++;
			}

			Dialog dialog = null;
			final int addView = addORview;

			AlertDialog.Builder builder = new AlertDialog.Builder(
					VisitActivity.this)
			.setTitle("Select Image Category:")
			.setSingleChoiceItems(imageCategoryList, preSelected,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					preSelected = which;

					String imgCategory = getFolderName(preSelected);
					// removeDialog(0);
					dialog.dismiss();

					if (addView == 1) { // doc img add btn
						Intent intent = new Intent(VisitActivity.this,	CustomGalleryJobDocsAdd.class);
						intent.putExtra("SELECTION_ID", imgCategory);
						//intent.putExtra("ISVISIT", true);
						startActivity(intent);
					} else { // doc img view btn
						Intent intent = new Intent(VisitActivity.this,	CustomGalleryJobDocsView.class);
						intent.putExtra("SELECTION_ID",	imgCategory);
						//intent.putExtra("ISVISIT", true);
						startActivity(intent);
					}
				}
			})

			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
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
			LOG.error("imageCategoryPopup:11525");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

	}

	private String getFolderName(int preSelected) {
		try {
			String imgCategory = "";
			switch (preSelected) {
			case 0:
				imgCategory = "TechnicalOfficerComments";
				break;
			case 1:
				imgCategory = "EstimateAnyotherComments";
				break;
			case 2:
				imgCategory = "InspectionPhotosSeenVisitsAnyOther";
				break;
			default:
				imgCategory = "Temp";
				break;
			}
			return imgCategory;
		} catch (Exception e) {
			LOG.error("getFolderName:11526");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	public List<File> imageList(String foldername) {
		VisitObject vObject = Application.getVisitObjectInstance();
		String destinationFolder = vObject.getVisitFolderName();

		File f = new File(URL.getSLIC_VISITS() + destinationFolder
				+ "/DocImages/" + foldername);
		List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(),
				true);
		return fileList;
	}


	public void on_click_view_document_images(View v) {
		try {
			LOG.debug("ENTRY ","on_click_view_document_images");
			onSave();

			// 1 == Doc Images Add; 2 == Doc Images View			
			imageCategoryPopup(2);

			LOG.debug("SUCCESS ", "on_click_view_document_images");
		} catch (Exception e) {
			LOG.error("on_click_view_document_images:11524");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public void on_click_view_PreviousComments(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_view_PreviousComments");

			v.setEnabled(false);
			progressBar.setVisibility(View.VISIBLE);

			new getAllCommentsHandler().execute(visitObj.getJobNo());

			LOG.debug("SUCCESS ", "on_click_view_PreviousComments");
		} catch (Exception e) {
			LOG.error("on_click_view_PreviousComments:12524");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }

			v.setEnabled(true);
			progressBar.setVisibility(View.GONE);
		}
	}


	//Example of broadcast receiver
	public void on_click_cancel_button(View v) {
		try {
			//Application.cancelForm(this);
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
		} catch (Exception e) {
		}
	}


	public synchronized ProgressDialog getDialog() {
		return dialog;
	}

	public void on_click_copy_images_button(final View v) {
		try{
			Dialog dialog = null;

			LOG.debug("ENTRY on_click_copy_capture_images_button");
			AlertDialog.Builder builder = new AlertDialog.Builder(VisitActivity.this)
					.setTitle("Select Image Category:")
					.setSingleChoiceItems(R.array.uploadselectiontype, preSelected,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									preSelected = which;

									uploadImageSelectionTwo fm = uploadImageSelectionTwo.values()[which];
									String selectionCategory = fm.toString();

									//call the function
									if(which == 0){
										LOG.debug("ENTRY on_click_capture_images_button");
										dispatchTakePictureIntent();

									}else if(which == 1){
										try {
											LOG.debug("ENTRY ", "on_click_copy_images_button");
											Application.copyImageCommon(VisitActivity.this, v, 1);
											LOG.debug("SUCCESS ", "on_click_copy_images_button");
										} catch (Exception e) {
											LOG.error("on_click_copy_images_button:11517");
											if(e != null){
												LOG.error("Message: " + e.getMessage());
												LOG.error("StackTrace: " + e.getStackTrace());
											}
										}
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


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		try {
			super.onCreateOptionsMenu(menu);
			if (!visitObj.getisSEARCH()) {
				menu.add(0, COPY_IMAGES_ID, 0, "Copy Images").setShortcut('3', 'c');
			}
			return true;
		} catch (Exception e) {
			LOG.error("onCreateOptionsMenu:11530");
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
			//on_click_copy_images_button(VisitActivity.this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void on_click_submit_button(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_submit_button");
			String errmsg = inspecDateCheck();
			if(!errmsg.isEmpty()) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(VisitActivity.this);
				alertbox.setTitle(getString(R.string.inspec_info_form));
				alertbox.setMessage(errmsg);
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						onSave();
					}});
				alertbox.show();
			}
			else if (!NetworkCheck.isNetworkAvailable(this)) { //If Offline   //Application.getIsOnline() == 0 can't use coz it's always 1 when it comes to visit activity
				AlertDialog.Builder alertbox = new AlertDialog.Builder(VisitActivity.this);
				alertbox.setTitle(getString(R.string.inspec_info_form));
				alertbox.setCancelable(false);
				alertbox.setMessage("Do the visit submission when network is available.");
				alertbox.setPositiveButton("Save & Goto Home",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						onSave();
						Intent mIntent = new Intent(VisitActivity.this, HomeActivity.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						VisitActivity.this.startActivity(mIntent);
						VisitActivity.this.finish();
					}});
				alertbox.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						removeDialog(arg1);
					}});
				alertbox.show();

			}else { //If Online
				File f = new File(URL.getSLIC_VISITS() + visitObj.getVisitFolderName());
				if(f.exists()){
					List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);
					visitObj.setImageCount(Integer.toString(fileList.size()));
				}

				AlertDialog.Builder alertbox = new AlertDialog.Builder(VisitActivity.this);
				alertbox.setTitle(getString(R.string.inspec_info_form));
				alertbox.setMessage("Are you sure you want to submit?");
				alertbox.setCancelable(false);
				alertbox.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						onSave();
						//Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.SUBMIT_BUTTON_CLICK, VisitActivity.this, visitObj));
						Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.VISIT_SUBMIT_BUTTON_CLICK, VisitActivity.this, visitObj));
					}
				});
				alertbox.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						removeDialog(arg1);
					}
				});
				alertbox.show();
			}
			LOG.debug("SUCCESS ",  "on_click_submit_button");
		} catch (Exception e) {
			LOG.error("on_click_submit_button:15624");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private String inspecDateCheck() {
		String errMsg = "";
		try {
			onSave();
			if (day.getText().toString().trim().isEmpty()) {
				errMsg = "Please Enter 'Date' in 'Inspection Date'.\r\n";
			}
			if (month.getText().toString().trim().isEmpty()) {
				errMsg = errMsg + "Please Enter 'Month' in 'Inspection Date'.\r\n";
			}
			if (year.getText().toString().trim().isEmpty()) {
				errMsg = errMsg + "Please Enter 'Year' in 'Inspection Date'.\r\n";
			}

			if(!day.getText().toString().trim().isEmpty() && !month.getText().toString().trim().isEmpty() && !year.getText().toString().trim().isEmpty()) {
				Date today = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

				try {
					today = (Date)dateFormat.parse(dateFormat.format(today));
					Date getInspectionDate = (Date)dateFormat.parse(visitObj.getInspectionDate().toString().trim());

					//check entered date is a future date time compared to today date time 
					if(today.before(getInspectionDate))
						errMsg = errMsg + "'Inspection Date' can't be a future date.\r\n";
				} catch (Exception e) {
					errMsg = errMsg + "'Inspection Date' not in a valid format.\r\n";
				}
			}

		} catch (Exception e) {
			LOG.error("inspecDateCheck:16624");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return errMsg;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				//Application.cancelForm(this);
				Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
			}
		} catch (Exception e) {
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

	public class MyOnItemSelectedListenerVisitTypeSelector implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(visitObj.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (VisitTypeMapping tp : VisitTypeMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(tp.getString())) {
						visitObj.setInspectionTypeInText(tp.name());
						visitObj.setInspectionType(tp.getInt());
						visitObj.setInspectionTypeArrIndex(pos);
						break;
					}
				}

			} catch (Exception e) {
				LOG.error("onItemSelected:10429");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private void displayMessage(String msg) {
		String title = "Previous Comments";
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle(title);
		alertbox.setMessage(msg);
		alertbox.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		alertbox.show();
	}

	private void displayChatWindow() {
		try {
			LOG.debug("SUCCESS ",  "displayChatWindow");

			AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.chat_activity, null);
			myDialog.setView(view);
			myDialog.setTitle("Previous Comments");

			ListView chatList = (ListView) view.findViewById(R.id.chat_listview);

			chatList.setAdapter(new ChatListAdapter(this));
			chatList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				}
			});

			myDialog.setPositiveButton("Close",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			myDialog.show();
			LOG.debug("SUCCESS ",  "displayChatWindow");
		} catch (Exception e) {
			LOG.error("displayChatWindow:12523");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static class ChatListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public ChatListAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return chtList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 1.
			View view = mInflater.inflate(R.layout.comment_custom_bubble_item, null);

			// 2.
			LinearLayout listLayout = (LinearLayout) view.findViewById(R.id.listlayoutview);
			LinearLayout topbar = (LinearLayout) view.findViewById(R.id.topbar);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView date = (TextView) view.findViewById(R.id.date);
			TextView msg = (TextView) view.findViewById(R.id.msg);

			// 3.
			//name:  name_date		| visittypeName_Date
			//value: msg			| -1
			NameValuePair itm = chtList.get(position);
			String[] arr = itm.getName().split("_");

			if(itm.getValue().equals("-1")) {
				listLayout.setBackgroundResource(R.drawable.pendingjobs_only_row_i);
				topbar.setVisibility(View.GONE);
				msg.setText(arr[0] + "  (" + arr[1] + ")");
			}else {
				topbar.setVisibility(View.VISIBLE);
				name.setText(arr[0]);
				date.setText(arr[1]);
				msg.setText(itm.getValue());
			}

			return view;
		}
	}	


	/**
	 * 
	 * @author Suren Manawatta
	 */
	class getAllCommentsHandler extends AsyncTask <String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				return WebService.getAllComments(params[0]);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String xmlText) {
			try{
				if(xmlText != null) {
					//xml get response if 0 process and show the popup else show error smg
					WebServiceObject ws = new WebServiceObject();
					ws.setXmlText(xmlText);
					XMLHandler.getResponse(ws);
					if(ws.getCode().equalsIgnoreCase("0")) {
						chtList = XMLHandler.getChatRecords(ws);
						displayChatWindow();
					} else {
						if(ws.getDescription() != null && !ws.getDescription().isEmpty())
							displayMessage(ws.getDescription());
						else
							displayMessage("Network Failed!");
					}
				}else {
					displayMessage("Network Failed!");
				}

				prevComments.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			} catch(Exception e){
				prevComments.setEnabled(true);
				progressBar.setVisibility(View.GONE);
				displayMessage("Network Failed!");
			}
		}
	}

	static final int REQUEST_TAKE_PHOTO = 234;
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
		File storageDir = new File(URL.getSLIC_DOCUMENTS());

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
}