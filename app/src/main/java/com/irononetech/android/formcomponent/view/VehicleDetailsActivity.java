package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.MultilevelListView.ExpList3_Bus;
import com.irononetech.android.MultilevelListView.ExpList3_Car;
import com.irononetech.android.MultilevelListView.ExpList3_HandTractor;
import com.irononetech.android.MultilevelListView.ExpList3_Lorry;
import com.irononetech.android.MultilevelListView.ExpList3_Motorcycle;
import com.irononetech.android.MultilevelListView.ExpList3_ThreeWheel;
import com.irononetech.android.MultilevelListView.ExpList3_Tractor4WD;
import com.irononetech.android.MultilevelListView.ExpList3_Van;
import com.irononetech.android.MultilevelListView.RadioButtonListView;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.imagedownloadcomponent.BulkImageDownloadController;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class VehicleDetailsActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	FormObject formObject;
	EditText chasisNumber;
	EditText engineNo;
	EditText meterReading;
	Spinner contributory_spinner;
	Button pointofimpactAddButton;
	Button pointofimpactViewButton;
	Button damagedItemsButton;
	Button predamagedItemsButton;
	Button possibledrButton;
	Button tyreConditionButton;
	ProgressDialog dialog;
	Button chassisno_reason_btn;
	Button engineno_reason_btn;

	TextView possibledr_edittxt;
	InputMethodManager imm;

	private static final int DIALOG_MULTIPLE_CHOICE_VEHICLECLASS = 0;
	private static final int DIALOG_SINGLE_CHOICE_VEHICLECLASS = 1;
	private static final int DIALOG_POSSIBLE_DR_UNEDITABLE = 2;

	boolean[] booleanselectedpossibledr; // this array is for keep the state of
										 // the multiple select items
	String[] dataforselectedpossibledr;  // this is for read the data from array
										 // class according to competence
	String[] trueselectedpossibledr;
	int vehicleType;
	String[] dataforVehicleType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			setContentView(R.layout.vehicleform);
			
			// Get Form Object
			formObject = Application.getFormObjectInstance();
			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			chasisNumber = (EditText) findViewById(R.id.chasisnotext);
			engineNo = (EditText) findViewById(R.id.enginenotext);
			meterReading = (EditText) findViewById(R.id.meterreadingtext);
			pointofimpactAddButton = (Button) findViewById(R.id.pointofimpactaddbutton);		
			pointofimpactViewButton = (Button) findViewById(R.id.pointofimpactviewbutton);
			damagedItemsButton = (Button) findViewById(R.id.damageditemsbutton);
			predamagedItemsButton = (Button) findViewById(R.id.predamageditemsbutton);
			possibledrButton = (Button) findViewById(R.id.possibledrbutton);
			tyreConditionButton = (Button) findViewById(R.id.button1);

			chassisno_reason_btn = (Button) findViewById(R.id.chassisno_reason_btn);
			engineno_reason_btn = (Button) findViewById(R.id.engineno_reason_btn);

			contributory_spinner = (Spinner) findViewById(R.id.contributory_spinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					this, R.array.competence, R.layout.textviewforspinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			contributory_spinner.setAdapter(adapter);
			contributory_spinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerContributoryInsured());
			contributory_spinner.setSelection(Integer.parseInt(formObject.getAreTheyContributory().trim()));
			
			// Currently onLoad() is implemented only for Accident Details Activity.
			// i.e.: First Activity
			// And need to check whether came from search and view a existing job.
			// otherwise no need of this.

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10532");
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
			LOG.debug("ENTRY onResume");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);

			formObject = Application.getFormObjectInstance();
			formComponentControler();
			onLoad();
			
			booleanselectedpossibledr = formObject.getselectPossibleDR();
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10533");
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
			LOG.debug("ENTRY onPause");
			System.gc();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onPause:10534");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void formComponentControler(){
		try {
			if(formObject.getisDRAFT() || formObject.getisSMS()){/*
				chasisNumber.setFocusable(true);
				chasisNumber.setFocusableInTouchMode(true); 
				chasisNumber.setClickable(true);

				engineNo.setFocusable(true);
				engineNo.setFocusableInTouchMode(true); 
				engineNo.setClickable(true);

				meterReading.setFocusable(true);
				meterReading.setFocusableInTouchMode(true); 
				meterReading.setClickable(true);

				contributory_spinner.setEnabled(true);
				pointofimpactAddButton.setEnabled(true);
				pointofimpactViewButton.setEnabled(true);

				chassisno_reason_btn.setEnabled(true);
				engineno_reason_btn.setEnabled(true);

				pointofimpactAddButton.setEnabled(true);
			*/}
			if(formObject.getisSEARCH()){
				chasisNumber.setTextColor(Color.GRAY);
				chasisNumber.setFocusable(false);
				chasisNumber.setFocusableInTouchMode(false); 
				chasisNumber.setClickable(false);

				engineNo.setTextColor(Color.GRAY);
				engineNo.setFocusable(false);
				engineNo.setFocusableInTouchMode(false); 
				engineNo.setClickable(false);

				meterReading.setTextColor(Color.GRAY);
				meterReading.setFocusable(false);
				meterReading.setFocusableInTouchMode(false); 
				meterReading.setClickable(false);
				
				contributory_spinner.setEnabled(false);	
				
				chassisno_reason_btn.setVisibility(View.GONE);
				engineno_reason_btn.setVisibility(View.GONE);

				pointofimpactAddButton.setVisibility(View.GONE);
			}

			if (!formObject.isEditable()) {
				//pointofimpactAddButton.setBackgroundDrawable(getResources()
						//.getDrawable(R.drawable.motorclaims_chasisform_add_button_active));

				pointofimpactViewButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

				damagedItemsButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

				predamagedItemsButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

				possibledrButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

				tyreConditionButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

			} else {
				if(!isPointOfImpactImgAvailable()){ // If POI Image not available
					//pointofimpactAddButton.setBackgroundDrawable(getResources()
							//.getDrawable(R.drawable.add_button_xml));
					pointofimpactAddButton.setVisibility(View.VISIBLE);
				}else{
					//pointofimpactAddButton.setBackgroundDrawable(getResources()
							//.getDrawable(R.drawable.motorclaims_chasisform_add_button_active));
					//pointofimpactAddButton.setEnabled(false);
					pointofimpactAddButton.setVisibility(View.GONE);
				}

				/*pointofimpactViewButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.view_button_xml));

				damagedItemsButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.add_button_xml));

				predamagedItemsButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.add_button_xml));

				possibledrButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.add_button_xml));

				tyreConditionButton.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.add_button_xml));*/
			}
		} catch (Exception e) {
			LOG.error("formComponentControler:10535");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private boolean isPointOfImpactImgAvailable(){
		try {
			File imgFile = new  File(URL.getSLIC_JOBS() + formObject.getJobNo() + "/PointsOfImpact/"
					+ formObject.getJobNo() + "_PointsOfImpact.jpg");
			if(imgFile.exists()){
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void onLoad() {
		try {
			// meterReading:
			meterReading.setText(formObject.getMeterReading());

			contributory_spinner.setSelection(Integer.parseInt(formObject.getAreTheyContributory().trim()));

			if(!(Application.getVisitObjectInstance() != null && Application.getVisitObjectInstance().getisDRAFT() && formObject.getisSEARCH())){
				// chasisNumber:
				chasisNumber.setText(formObject.getChasisNo());

				// engineNo:
				engineNo.setText(formObject.getEngineNo());
			}
			
			if (!formObject.getisSEARCH()) {
				pointofimpactViewButton.setVisibility(View.GONE);
				String destinationFolder = formObject.getJobNo();
				File imgFile = new File(URL.getSLIC_JOBS() + destinationFolder
						+ "/PointsOfImpact/" + destinationFolder
						+ "_PointsOfImpact" + ".jpg");
				if (imgFile.exists()) {
					pointofimpactViewButton.setVisibility(View.VISIBLE);
				}
			}
			
		} catch (NumberFormatException e) {
			LOG.error("onLoad:10536");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onSave() {
		try {
			// meterReading:
			formObject.setMeterReading((meterReading.getText().toString().trim()));

			int pos = contributory_spinner.getSelectedItemPosition();
			for (Confirmation fm : Confirmation.values()) {
				if (contributory_spinner.getItemAtPosition(pos).toString()
						.equals(fm.getString())) {
					formObject.setAreTheyContributory(Integer.toString(fm
							.getInt()));
					break;
				}
			}

			if(!(Application.getVisitObjectInstance() != null && Application.getVisitObjectInstance().getisDRAFT() && formObject.getisSEARCH())){
				// chasisNumber:
				formObject.setChasisNo((chasisNumber.getText().toString().trim()));
				// engineNo:
				formObject.setEngineNo((engineNo.getText().toString().trim()));
			}

			if(!formObject.getisSEARCH()){
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave:10537");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			//Application.cancelForm(this);
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.CANCEL_FORM, this, null));
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick_keyboardClose(View v){
		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
	}

	String[] dataforchassisno_reason;
	int preSelected = 0;

	public void on_click_chassisno_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_chassisno_reason_btn");
			dataforchassisno_reason = new String[ChassisNoReasonMapping.values().length];
			int i = 0;
			for (ChassisNoReasonMapping fm : ChassisNoReasonMapping.values()) {
				dataforchassisno_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(VehicleDetailsActivity.this)
			.setTitle("Reasons")
			.setSingleChoiceItems(dataforchassisno_reason, preSelected, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					chasisNumber.setText(""+dataforchassisno_reason[which]);
					preSelected = which;
					dialog.dismiss();
				}
			})
			/*.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									chasisNumber.setText(dataforchassisno_reason[0]);
									removeDialog(0);
								}
							})*/
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					/* User clicked No so do some stuff */
					preSelected = 0;
					chasisNumber.setText("");
					removeDialog(0);
				}
			});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			LOG.debug("SUCCESS ", "on_click_chassisno_reason_btn");
		} catch (Exception e) {
			LOG.error("on_click_chassisno_reason_btn:10538");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	String[] dataforengineno_reason;
	int preSelected1 = 0;

	public void on_click_engineno_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ","on_click_engineno_reason_btn");
			dataforengineno_reason = new String[EngineNoReasonMapping.values().length];
			int i = 0;
			for (EngineNoReasonMapping fm : EngineNoReasonMapping.values()) {
				dataforengineno_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(VehicleDetailsActivity.this)
			.setTitle("Reasons")
			.setSingleChoiceItems(dataforengineno_reason, preSelected1, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					engineNo.setText(""+dataforengineno_reason[which]);
					preSelected1 = which;
					dialog.dismiss();
				}
			})
			/*.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									engineNo.setText(dataforengineno_reason[0]);
									removeDialog(0);
								}
							})*/
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					/* User clicked No so do some stuff */
					preSelected1 = 0;
					engineNo.setText("");
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

	
	public void on_click_back_button(View v) {
		try {			
			LOG.debug("ENTRY ", "on_click_back_button");
			Application.goForward = false;
			Application.goBackward = true;
			onSave();
			Application.getInstance().doActionOnEvent(
					new EventParcel(UIEvent.VEHICLEDETAILS_BACK_BUTTON_CLICK,
							this, formObject));
			LOG.debug("SUCCESS ","on_click_back_button");
		} catch (Exception e) {
			LOG.error("on_click_back_button:10541");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_next_button(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_next_button");
			
			onSave();
			String chassiNoError = "";
			String engineNoError = "";

			if (chasisNumber.getText().toString().trim().equals("") && !formObject.getisSEARCH()) {
				chassiNoError = "Please Enter 'Chassis No'.\r\n";
			}
			if (engineNo.getText().toString().trim().equals("") && !formObject.getisSEARCH()) {
				engineNoError = "Please Enter 'Engine No'.\r\n";
			}
			if (!formObject.getisSEARCH() && (!chassiNoError.isEmpty() || !engineNoError.isEmpty())) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						VehicleDetailsActivity.this);
				alertbox.setTitle(R.string.saform);
				alertbox.setMessage(chassiNoError + engineNoError);
				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
			} else {
				try {
					Application.goForward = true;
					Application.goBackward = false;
					Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.VEHICLEDETAILS_NEXT_BUTTON_CLICK, this,	formObject));
				} catch (Exception e) {
					LOG.error("on_click_next_button:11540");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
			}
			LOG.debug("SUCCESS ",  "on_click_next_button");
		} catch (Exception e) {
			LOG.error("on_click_next_button:10540");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	
	public void on_click_point_of_impact(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_point_of_impact");
			onSave();

			Application.goForward = true;
			Application.goBackward = false;
			Intent intent = new Intent(getApplicationContext(), ListViewForVehicleDetails.class);
			intent.putExtra("JOBNO", formObject.getJobNo());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			//startActivity(new Intent(getApplicationContext(),	ListViewForVehicleDetails.class));
			LOG.debug("SUCCESS ",  "on_click_point_of_impact");
		} catch (Exception e) {
			LOG.error("on_click_point_of_impact:10542");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_pointofimpact_view_button(View v) {
		/*onSave();
		startActivity(new Intent(getApplicationContext(),
				PointOfImpactViewActivity.class));*/
		try {
			LOG.debug("ENTRY ",  "on_click_pointofimpact_view_button");
			File imgFile = null;
			String destinationFolder = formObject.getJobNo();

			if(formObject.getisSEARCH()){
				if(formObject.getPointOfImpactList() != null && formObject.getPointOfImpactList().size() > 0)
					imgFile = new  File(formObject.getPointOfImpactList().get(0));
				
				if(formObject.getPointOfImpactList() == null || formObject.getPointOfImpactList().size() <= 0) {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(VehicleDetailsActivity.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage(R.string._point_of_impact_image_is_not_available_);
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertbox.show();
				}else{
					if(imgFile != null && !imgFile.exists()){
						//download it
						String filepathImpact = URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact/";
						String targetFileName = imgFile.getName();
						
						dialog = ProgressDialog.show(VehicleDetailsActivity.this, "", "Downloading...", true);

						Intent intent = new Intent(getBaseContext(), BulkImageDownloadController.class);
						ArrayList<String> toBeDownloaded = new ArrayList<String>();
						toBeDownloaded.add(targetFileName);

						intent.putExtra("MEDIA_SAVE_PATH", filepathImpact);
						intent.putStringArrayListExtra("IMG_LIST", toBeDownloaded);
						bgImageDownloder(intent);

					}else{
						startActivity(new Intent(getApplicationContext(), PointOfImpactViewActivity.class));
					}
				}

			}else{
				onSave();
				imgFile = new  File(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact/" + destinationFolder + "_PointsOfImpact" + ".jpg");
				if(!imgFile.exists()){
					//MSG
					AlertDialog.Builder alertbox = new AlertDialog.Builder(VehicleDetailsActivity.this);  
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage(R.string._point_of_impact_image_is_not_available_);
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertbox.show();
				}else{
					startActivity(new Intent(getApplicationContext(), PointOfImpactViewActivity.class));
				}
			}
			LOG.debug("SUCCESS ",  "on_click_pointofimpact_view_button");
		}catch (Exception e) {
			LOG.error("on_click_pointofimpact_view_button:10543");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private Handler mHandler = new Handler();
	protected void bgImageDownloder(final Intent ii) {
		try {
			// Start lengthy operation in a background thread
			new Thread(new Runnable() {
				public void run() {

					BulkImageDownloadController b = new BulkImageDownloadController();
					b.bulkImageDownloder(ii);

					mHandler.post(new Runnable() {
						public void run() {
							startActivity(new Intent(getApplicationContext(), PointOfImpactViewActivity.class));
							handler.sendEmptyMessage(0);
						}
					});
				}
			}).start();
		} catch (Exception e) {
			LOG.error("bgImageDownloder:10544");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			handler.sendEmptyMessage(0);
		}
	}

	//@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
		}
	};

	public void tyrecondition_button_click(View v) {
		try {
			LOG.debug("ENTRY ",  "tyrecondition_button_click");
			onSave();
			Intent mIntent = new Intent(VehicleDetailsActivity.this,
					RadioButtonListView.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			LOG.debug("SUCCESS ", "tyrecondition_button_click");
		} catch (Exception e) {
			LOG.error("tyrecondition_button_click:10545");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	public void damageditems_button_click(View v) {
		try{
			LOG.debug("ENTRY ", "damageditems_button_click");
			formObject.setisPreSelected(false);  // used to identify weather click on pre damages or org. damages add btn
			onSave();

			if (formObject.getisVehicleShow()) {
				showDialog(DIALOG_SINGLE_CHOICE_VEHICLECLASS);
			} else {
				if (formObject.getvehicleType() == 1) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				} else if (formObject.getvehicleType() == 2) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Car.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 3) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Lorry.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 4) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Van.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 5) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_ThreeWheel.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 6) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Motorcycle.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 7) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Tractor4WD.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 8) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_HandTractor.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else{ /*if select nothing BUS will be the default*/
					/*Intent mIntent = new Intent(
						VehicleDetailsActivity.this,
						ExpList3_Bus.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(mIntent);*/

					if(formObject.isEditable()){
						formObject.setvehicleType(1);
						Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(mIntent);
					}
					else{
						// if nothing selected, vehi type change to no 6 on the XMLHandler.java file
						AlertDialog.Builder alertbox = new AlertDialog.Builder(VehicleDetailsActivity.this);
						alertbox.setTitle(R.string.saform);
						alertbox.setMessage("No Item(s) was selected.");
						alertbox.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
						alertbox.show();
					}
				}
			}
			LOG.debug("SUCCESS ",  "damageditems_button_click");
		}catch(Exception e)	{
			LOG.error("damageditems_button_click:10546");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void pre_damageditems_button_click(View v) {
		try{
			LOG.debug("ENTRY ", "pre_damageditems_button_click");
			formObject.setisPreSelected(true);   // used to identify weather click on pre dameges or org. damages add btn
			onSave();

			if (formObject.getisVehicleShow()) {
				showDialog(DIALOG_SINGLE_CHOICE_VEHICLECLASS);
			} else {
				if (formObject.getvehicleType() == 1) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				} else if (formObject.getvehicleType() == 2) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Car.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 3) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Lorry.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 4) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Van.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 5) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_ThreeWheel.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 6) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Motorcycle.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 7) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Tractor4WD.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else if (formObject.getvehicleType() == 8) {
					Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_HandTractor.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(mIntent);
				}
				else{ /*if select nothing BUS will be the default*/
					/*Intent mIntent = new Intent(
						VehicleDetailsActivity.this,
						ExpList3_Bus.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(mIntent);*/

					if(formObject.isEditable()){
						formObject.setvehicleType(1);
						Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(mIntent);
					}
					else{
						// if nothing selected, vehi type change to no 6 on the XMLHandler.java file
						AlertDialog.Builder alertbox = new AlertDialog.Builder(VehicleDetailsActivity.this);
						alertbox.setTitle(R.string.saform);
						alertbox.setMessage("No Item(s) was selected.");
						alertbox.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
						alertbox.show();
					}
				}
			}
			LOG.debug("SUCCESS ", "pre_damageditems_button_click");
		}catch(Exception e){
			LOG.error("pre_damageditems_button_click:10547");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void possibledr_button_click(View v) {
		try {
			LOG.debug("ENTRY ",  "possibledr_button_click");
			if(formObject.isEditable())	{
				customDialog();
			} else {
				showDialog(DIALOG_POSSIBLE_DR_UNEDITABLE);
			}
			LOG.debug("SUCCESS ",  "possibledr_button_click");
		} catch (Exception e) {
			LOG.error("possibledr_button_click:10548");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private void customDialog(){
		try {
			booleanselectedpossibledr = formObject.getselectPossibleDR();
			dataforselectedpossibledr = new String[PossibleDRMapping.values().length];
			int ii = 0;
			for (PossibleDRMapping fm : PossibleDRMapping.values()) {
				dataforselectedpossibledr[ii] = fm.getString();
				ii++;
			}

			AlertDialog.Builder customDialog = new 
					AlertDialog.Builder(VehicleDetailsActivity.this);
			customDialog.setTitle("Possible D/R");

			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View view = layoutInflater.inflate(R.layout.possible_dr_custom_dialog,null);

			possibledr_edittxt = (TextView)view.findViewById(R.id.possibledr_editText);
			possibledr_edittxt.setText(formObject.getpossibleDR_Other());

			customDialog.setMultiChoiceItems(dataforselectedpossibledr,
					booleanselectedpossibledr,
					new DialogInterface.OnMultiChoiceClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton, boolean isChecked) {
					/* User clicked on a check box do some stuff */
				}
			});

			customDialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					TextView possibledr_edittxt = (TextView)view.findViewById(R.id.possibledr_editText);

					formObject
					.setselectPossibleDR(booleanselectedpossibledr);
					formObject.setpossibleDR_Other(possibledr_edittxt.getText().toString().trim());
					removeDialog(0);
				}});

			customDialog.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					boolean[] a = new boolean[PossibleDRMapping.values().length]; 
					booleanselectedpossibledr = a;
					formObject.setselectPossibleDR(a);
					formObject.setpossibleDR_Other("");
					removeDialog(0);
				}
			});

			customDialog.setView(view);
			customDialog.show();
		} catch (Exception e) {
			LOG.error("customDialog:10549");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public class MyOnItemSelectedListenerContributoryInsured implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));
				
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
				
				for (Confirmation fm : Confirmation.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setAreTheyContributory(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10550");
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

	protected Dialog onCreateDialog(int id) {
		try{
			switch (id) {
			case DIALOG_MULTIPLE_CHOICE_VEHICLECLASS:  // NOT USED ANYMORE

				dataforselectedpossibledr = new String[PossibleDRMapping.values().length];
				int i = 0;
				for (PossibleDRMapping fm : PossibleDRMapping.values()) {
					dataforselectedpossibledr[i] = fm.getString();
					i++;
				}

				Dialog dialog = null;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						VehicleDetailsActivity.this)
				//	.setIcon(R.drawable.ic_popup_reminder)
				.setTitle("Possible D/R")
				.setMultiChoiceItems(dataforselectedpossibledr, booleanselectedpossibledr,
						new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton, boolean isChecked) {

						/* User clicked on a check box do some stuff */
					}
				})
				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int whichButton) {
						formObject.setselectPossibleDR(booleanselectedpossibledr);
						removeDialog(0);
					}
				})
				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						/* User clicked No so do some stuff */
						removeDialog(0);
					}
				});
				dialog = builder.create();
				if(!formObject.isEditable()){
					((AlertDialog) dialog).getListView().setClickable(false);
				}
				return dialog;

			case DIALOG_SINGLE_CHOICE_VEHICLECLASS:

				dataforVehicleType = new String[VehicleTypeMapping.values().length];
				int j = 0;
				for (VehicleTypeMapping fm : VehicleTypeMapping.values()) {
					dataforVehicleType[j] = fm.getString();
					j++;
				}

				Dialog dialog1 = null;

				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setTitle("Select Vehicle Type");
				builder1.setSingleChoiceItems(dataforVehicleType, 0, /*-1 was here, and i changed it to 0 - Suren*/
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						formObject.setvehicleType(item+1);

						formObject.setisVehicleShow(false);
						dialog.dismiss();

						if (formObject.getvehicleType() == 1) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						} else if (formObject.getvehicleType() == 2) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Car.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 3) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Lorry.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 4) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Van.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 5) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_ThreeWheel.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 6) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Motorcycle.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 7) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Tractor4WD.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else if (formObject.getvehicleType() == 8) {
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_HandTractor.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
						else {
							formObject.setvehicleType(1);
							Intent mIntent = new Intent(VehicleDetailsActivity.this, ExpList3_Bus.class);
							mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(mIntent);
						}
					}
				})

				/*.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									formObject.setisVehicleShow(false);
									removeDialog(0);

									//---------------------------------------------------------------------------
									//if(formObject.getisPreSelected()) {  //PRE
									//	formObject.setprevehicleType(formObject.getvehicleType());
									//	LogFile.d("111", "pre");
									//}
									//else {
									//	formObject.setprevehicleType(formObject.getvehicleType());
									//	formObject.setpostvehicleType(formObject.getvehicleType());
									//	LogFile.d("111", "post");
									//}
									//-----------------------------------------------------------------------------

									if (formObject.getvehicleType() == 1) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Bus.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									} else if (formObject.getvehicleType() == 2) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Car.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
									else if (formObject.getvehicleType() == 3) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Lorry.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
									else if (formObject.getvehicleType() == 4) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Van.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
									else if (formObject.getvehicleType() == 5) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_ThreeWheel.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
									else if (formObject.getvehicleType() == 6) {
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Motorcycle.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
									else {
										formObject.setvehicleType(1);
										Intent mIntent = new Intent(
												VehicleDetailsActivity.this,
												ExpList3_Bus.class);
										mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(mIntent);
									}
								}
							})*/

				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {

						/* User clicked No so do some stuff */
						removeDialog(0);
					}
				});
				dialog1 = builder1.create();
				dialog1.setCanceledOnTouchOutside(true);
				if(!formObject.isEditable()){
					((AlertDialog) dialog1).getListView().setEnabled(false);
				}
				return dialog1;

			case DIALOG_POSSIBLE_DR_UNEDITABLE:

				String[] sortedPosDR = new String[PossibleDRMapping.values().length];
				int oi = 0;
				for (PossibleDRMapping fm : PossibleDRMapping.values()) {
					sortedPosDR[oi] = fm.getString();
					oi++;
				}

				ArrayList<String> displayItems = new ArrayList<String>();
				for (int k = 0; k < formObject.getselectPossibleDR().length; k++) {
					//for (PossibleDRMapping fm : PossibleDRMapping.values()) {
					if(formObject.getselectPossibleDR()[k]){
						displayItems.add(sortedPosDR[k]);
						//break;
						//}
					}
				}
				String[] display;
				ArrayList<String> arr;

				if (displayItems.size() >= 1) {
					display = new String[(displayItems.size())];
					for (int k = 0; k < (displayItems.size()); k++) {
						display[k] = displayItems.get(k);
					}

					//java.util.Arrays.sort(display);

					if(formObject.getpossibleDR_Other() != "") {
						arr = new ArrayList<String>(display.length+1);

						for (String string : display) {
							arr.add(string);
						}
						arr.add(display.length, ">>> OTHER ITEMS: " + formObject.getpossibleDR_Other());
					}
					else{
						arr = new ArrayList<String>(display.length);
						for (String string : display) {
							arr.add(string);
						}
					}
				}
				else {
					arr = new ArrayList<String>(1);
					arr.add("No Item(s) was Selected.");
				}

				String[] disArr = new String[arr.size()];

				for (int k = 0; k < arr.size(); k++) {
					disArr[k] = arr.get(k);
				}


				Dialog dialog3 = null;
				AlertDialog.Builder builder3 = new AlertDialog.Builder(
						VehicleDetailsActivity.this)
				//	.setIcon(R.drawable.ic_popup_reminder)
				.setTitle("Selected Possible D/R")

				.setItems(disArr, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}	})

					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							removeDialog(0);
						}
					});
				/*.setNegativeButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								// User clicked No so do some stuff
								removeDialog(0);
							}
						});*/

				dialog3 = builder3.create();
				if(!formObject.isEditable()){
					((AlertDialog) dialog3).getListView().setEnabled(false);
				}
				return dialog3;
			}
			return null;
		}
		catch (Exception e) {
			LOG.error("onCreateDialog:10551");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
}