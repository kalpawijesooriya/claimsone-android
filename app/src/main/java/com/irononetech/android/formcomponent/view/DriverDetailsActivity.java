package com.irononetech.android.formcomponent.view;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class DriverDetailsActivity extends Activity {

	private static final int DIALOG_MULTIPLE_CHOICE_VEHICLECLASS = 0;

	FormObject formObject;
	InputMethodManager imm;
	EditText driversName;
	EditText nicOfTheDriver;
	EditText drivingLicenseNumber;

	TextView dateseparatorDF;
	TextView dateseparatorDF1;

	EditText datetextbox;
	EditText monthtextbox;
	EditText yeartextbox;

	//LinearLayout driverform_body;
	LinearLayout exp_date_of_license_layout;
	LinearLayout type_of_dl_layout;
	LinearLayout dl_category_layout;
	LinearLayout competense_layout;

	Spinner relationshipSpinner;
	Spinner typeOfDrivingLicenseSpinner;
	Spinner oldnewDrivingLicenseSpinner;
	Spinner competenceSpinner;
	Spinner typeOfNICSpinner;

	Button drivername_reason_btn;
	Button drivinglicenceno_reason_btn;
	String[] datafordrivername_reason;
	Button vehicleClass_btn;

	String[] datafordriverlicenceno_reason;
	int preSelected1 = 0;

	final static int EXPIRYDATE = 1;

	Logger LOG = LoggerFactory.getLogger(DriverDetailsActivity.class);
	boolean[] booleanSelectVehecleClass; 	// this array is for keep the state of
	// the multiple select items
	String[] dataforSelectVehicleClass; 	// this is for read the data from array
	// class according to competence
	String[] trueSelectedValuesVehicleClass;
	boolean isMobile = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);

			setContentView(R.layout.driverform);

			formObject = Application.getFormObjectInstance();
			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			driversName = (EditText) findViewById(R.id.driversnametext);
			//driverform_body = (LinearLayout) findViewById(R.id.driverform_body);
			exp_date_of_license_layout = (LinearLayout)findViewById(R.id.driverform_body_LinearLayout5);
			type_of_dl_layout = (LinearLayout)findViewById(R.id.driverform_body_LinearLayout6);
			dl_category_layout = (LinearLayout)findViewById(R.id.driverform_body_LinearLayout7);
			competense_layout = (LinearLayout)findViewById(R.id.driverform_body_LinearLayout8);

			drivername_reason_btn = (Button) findViewById(R.id.drivername_reason_btn);
			drivinglicenceno_reason_btn = (Button) findViewById(R.id.drivinglicenceno_reason_btn);

			nicOfTheDriver = (EditText) findViewById(R.id.nicnoofdrivertext);
			drivingLicenseNumber = (EditText) findViewById(R.id.drivinglicensenotext);

			vehicleClass_btn = (Button) findViewById(R.id.vehicleclassbutton);

			dateseparatorDF =  (TextView) findViewById(R.id.dateseparatorDF);
			dateseparatorDF1 =  (TextView) findViewById(R.id.dateseparatorDF1);

			datetextbox = (EditText) findViewById(R.id.datetextboxDF);
			monthtextbox = (EditText) findViewById(R.id.monthtextboxDF);
			yeartextbox = (EditText) findViewById(R.id.yeartextboxDF);

			relationshipSpinner = (Spinner) findViewById(R.id.relationship_spinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(this,
							R.array.driverinsuredrelationship,
							R.layout.textviewforspinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			relationshipSpinner.setAdapter(adapter);
			relationshipSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerRelationshipDriverInsured());
			relationshipSpinner.setSelection(Integer.parseInt(formObject
					.getRelationshipBetweenDriverAndInsured().trim()) - 1);

			typeOfDrivingLicenseSpinner = (Spinner) findViewById(R.id.typeof_drivinglicense_spinner);
			ArrayAdapter<CharSequence> typeofDrivingadapter = ArrayAdapter
					.createFromResource(this, R.array.typeofdrivinglicense,
							R.layout.textviewforspinner);
			typeofDrivingadapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			typeOfDrivingLicenseSpinner.setAdapter(typeofDrivingadapter);
			typeOfDrivingLicenseSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerTypeOfdrivingLicense());
			typeOfDrivingLicenseSpinner.setSelection(Integer
					.parseInt(formObject.getTypeOfDrivingLicence().trim()));

			oldnewDrivingLicenseSpinner = (Spinner) findViewById(R.id.oldnew_drivinglicensespinner);
			ArrayAdapter<CharSequence> oldnewDrivingadapter = ArrayAdapter
					.createFromResource(this, R.array.drivingLicense,
							R.layout.textviewforspinner);
			oldnewDrivingadapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			oldnewDrivingLicenseSpinner.setAdapter(oldnewDrivingadapter);
			oldnewDrivingLicenseSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerOldNewdrivingLicense());
			oldnewDrivingLicenseSpinner.setSelection(Integer
					.parseInt(formObject.getSelectDL_NewOld().trim()));

			competenceSpinner = (Spinner) findViewById(R.id.competence_spinner);
			ArrayAdapter<CharSequence> competenceadapter = ArrayAdapter
					.createFromResource(this, R.array.competence,
							R.layout.textviewforspinner_small);
			competenceadapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			competenceSpinner.setAdapter(competenceadapter);
			competenceSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerCompetencedrivingLicense());
			competenceSpinner.setSelection(Integer.parseInt(formObject
					.getCompetence().trim()));
			
			/*
			 * Created By: Thisaru Guruge
			 * Date: 29 / 01 / 2016
			 * Adding the spinner to decide the type of the NIC number of the Driver
			 */
			typeOfNICSpinner = (Spinner) findViewById(R.id.typeOfNIC_spinner);
			ArrayAdapter<CharSequence> nictypeadapter = ArrayAdapter
					.createFromResource(this, R.array.nicNoType,
							R.layout.textviewforspinner);
			
			nictypeadapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			typeOfNICSpinner.setAdapter(nictypeadapter);
			typeOfNICSpinner
			.setOnItemSelectedListener(new MyOnItemSelectedListenerNICType());
			
			String typeOfNICNo = formObject.getTypeOfNICNo();
			
			if (typeOfNICNo == null || typeOfNICNo.equals("")) {
				typeOfNICSpinner.setSelection(0);
				//String selItem = (String)typeOfNICSpinner.getSelectedItem();
			} else {
				typeOfNICSpinner.setSelection(Integer.parseInt(formObject
						.getTypeOfNICNo()));
			}

			drivingLicenseNumber.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					//if(!formObject.getisSEARCH())
					freezeControls();
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					//if(!formObject.getisSEARCH())
					freezeControls();
				}

				@Override
				public void afterTextChanged(Editable s) {
					//if(!formObject.getisSEARCH())
					freezeControls();
				}
			});			

			onLoad();
			formComponentControler();

			if (formObject.getSelectDL_NewOld().equals("1")) {
				booleanSelectVehecleClass = formObject.getselectVehicleClassForNew();
			} else if (formObject.getSelectDL_NewOld().equals("0")) {
				booleanSelectVehecleClass = formObject.getselectVehicleClassForOld();
			}

			if(formObject.getisSEARCH())
				freezeControls();

			LOG.debug("SUCCESS onCreate");
		} catch (NumberFormatException e) {
			LOG.error("onCreate:10458");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		} catch (Exception e) {
			LOG.error("onCreate:10459");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private void freezeControls() {
		try {
			boolean goFreeze = false;
			String licNo = drivingLicenseNumber.getText().toString().trim();
			for (DriverLicenceNoReasonMapping fm : DriverLicenceNoReasonMapping.values()) {
				if(licNo.equalsIgnoreCase(fm.getString())){
					goFreeze = true;
					break;
				}
			}

			if (goFreeze) {
				dateseparatorDF.setVisibility(View.GONE);
				dateseparatorDF1.setVisibility(View.GONE);

				datetextbox.setVisibility(View.GONE);
				monthtextbox.setVisibility(View.GONE);
				yeartextbox.setVisibility(View.GONE);

				datetextbox.setText("");
				monthtextbox.setText("");
				yeartextbox.setText("");
				formObject.setExpiryDateOfLicence("");

				typeOfDrivingLicenseSpinner.setVisibility(View.GONE);
				typeOfDrivingLicenseSpinner.setEnabled(false);

				oldnewDrivingLicenseSpinner.setVisibility(View.GONE);
				oldnewDrivingLicenseSpinner.setEnabled(false);

				competenceSpinner.setVisibility(View.GONE);
				competenceSpinner.setEnabled(false);
				vehicleClass_btn.setEnabled(false);
				if (isTablet( this)) {
					exp_date_of_license_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body_diable5));
					type_of_dl_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body_diable6));
					dl_category_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body_diable7));
					competense_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body_diable8));

					vehicleClass_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_driver_select_vehicleclass_deactive));
				}
				else{
					exp_date_of_license_layout.setVisibility(View.GONE);
					type_of_dl_layout.setVisibility(View.GONE);
					dl_category_layout.setVisibility(View.GONE);
					competense_layout.setVisibility(View.GONE);
				}
			}else {
				dateseparatorDF.setVisibility(View.VISIBLE);
				dateseparatorDF1.setVisibility(View.VISIBLE);

				datetextbox.setVisibility(View.VISIBLE);
				monthtextbox.setVisibility(View.VISIBLE);
				yeartextbox.setVisibility(View.VISIBLE);

				typeOfDrivingLicenseSpinner.setVisibility(View.VISIBLE);
				typeOfDrivingLicenseSpinner.setEnabled(true);

				oldnewDrivingLicenseSpinner.setVisibility(View.VISIBLE);
				oldnewDrivingLicenseSpinner.setEnabled(true);

				competenceSpinner.setVisibility(View.VISIBLE);
				competenceSpinner.setEnabled(true);

				vehicleClass_btn.setEnabled(true);
				if (isTablet( this)) {
				exp_date_of_license_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body5));
				type_of_dl_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body6));
				dl_category_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body7));
				competense_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.driverform_body8));

				vehicleClass_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selectvehicle_class_button_xml));
				}
				else{
					exp_date_of_license_layout.setVisibility(View.VISIBLE);
					type_of_dl_layout.setVisibility(View.VISIBLE);
					dl_category_layout.setVisibility(View.VISIBLE);
					competense_layout.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			LOG.error("freezeControls:10466");
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

			if (formObject.getSelectDL_NewOld().equals("1")) {
				booleanSelectVehecleClass = formObject.getselectVehicleClassForNew();
			} else if (formObject.getSelectDL_NewOld().equals("0")) {
				booleanSelectVehecleClass = formObject.getselectVehicleClassForOld();
			}
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10460");
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
			LOG.error("onPause:10461");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private static boolean isTablet(Context context) {
		return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
	}

	public void formComponentControler() {
		try {
			if (formObject.getisDRAFT() || formObject.getisSMS()) {/*
				driversName.setFocusable(true);
				driversName.setFocusableInTouchMode(true);
				driversName.setClickable(true);

				drivername_reason_btn.setEnabled(true);
				drivinglicenceno_reason_btn.setEnabled(true);

				nicOfTheDriver.setFocusable(true);
				nicOfTheDriver.setFocusableInTouchMode(true);
				nicOfTheDriver.setClickable(true);

				drivingLicenseNumber.setFocusable(true);
				drivingLicenseNumber.setFocusableInTouchMode(true);
				drivingLicenseNumber.setClickable(true);

				datetextbox.setFocusable(true);
				datetextbox.setFocusableInTouchMode(true);
				datetextbox.setClickable(true);

				monthtextbox.setFocusable(true);
				monthtextbox.setFocusableInTouchMode(true);
				monthtextbox.setClickable(true);

				yeartextbox.setFocusable(true);
				yeartextbox.setFocusableInTouchMode(true);
				yeartextbox.setClickable(true);

				relationshipSpinner.setEnabled(true);
				typeOfDrivingLicenseSpinner.setEnabled(true);
				oldnewDrivingLicenseSpinner.setEnabled(true);
				competenceSpinner.setEnabled(true);
			 */}
			if (formObject.getisSEARCH()) {
				driversName.setTextColor(Color.GRAY);
				driversName.setFocusable(false);
				driversName.setFocusableInTouchMode(false);
				driversName.setClickable(false);

				drivername_reason_btn.setVisibility(View.GONE);
				drivinglicenceno_reason_btn.setVisibility(View.GONE);

				nicOfTheDriver.setTextColor(Color.GRAY);
				nicOfTheDriver.setFocusable(false);
				nicOfTheDriver.setFocusableInTouchMode(false);
				nicOfTheDriver.setClickable(false);

				drivingLicenseNumber.setTextColor(Color.GRAY);
				drivingLicenseNumber.setFocusable(false);
				drivingLicenseNumber.setFocusableInTouchMode(false);
				drivingLicenseNumber.setClickable(false);

				dateseparatorDF.setTextColor(Color.GRAY);
				dateseparatorDF1.setTextColor(Color.GRAY);

				datetextbox.setTextColor(Color.GRAY);
				datetextbox.setEnabled(false);
				//datetextbox.setFocusable(false);
				//datetextbox.setFocusableInTouchMode(false);
				//datetextbox.setClickable(false);

				monthtextbox.setTextColor(Color.GRAY);
				monthtextbox.setEnabled(false);
				//monthtextbox.setFocusable(false);
				//monthtextbox.setFocusableInTouchMode(false);
				//monthtextbox.setClickable(false);

				yeartextbox.setTextColor(Color.GRAY);
				yeartextbox.setEnabled(false);
				//yeartextbox.setFocusable(false);
				//yeartextbox.setFocusableInTouchMode(false);
				//yeartextbox.setClickable(false);

				relationshipSpinner.setEnabled(false);
				typeOfDrivingLicenseSpinner.setEnabled(false);
				typeOfNICSpinner.setEnabled(false);
				oldnewDrivingLicenseSpinner.setEnabled(false);
				competenceSpinner.setEnabled(false);
			}
		} catch (Exception e) {
			LOG.error("formComponentControler:10462");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void onLoad() {
		try {
			// Driver's Name:
			driversName.setText(formObject.getDriversName());

			// NIC of the Driver:
			nicOfTheDriver.setText(formObject.getNICNoOfDriver());

			// Driving License No:
			drivingLicenseNumber.setText(formObject.getDrivingLicenceNo());

			// Expire Date Of License:
			String fulldatettime = formObject.getExpiryDateOfLicence();

			if (fulldatettime != null && !(fulldatettime.equals(""))) {
				datetextbox.setText(fulldatettime.split("/")[0]);
				monthtextbox.setText(fulldatettime.split("/")[1]);

				int startPos = fulldatettime.split("/")[2].length() - 2;
				yeartextbox.setText(fulldatettime.split("/")[2]
						.substring(startPos));
			}

			relationshipSpinner.setSelection(Integer.parseInt(formObject
					.getRelationshipBetweenDriverAndInsured().trim()) - 1);

			typeOfDrivingLicenseSpinner.setSelection(Integer
					.parseInt(formObject.getTypeOfDrivingLicence().trim()));

			oldnewDrivingLicenseSpinner.setSelection(Integer
					.parseInt(formObject.getSelectDL_NewOld().trim()));

			competenceSpinner.setSelection(Integer.parseInt(formObject
					.getCompetence().trim()));
			
			typeOfNICSpinner.setSelection(Integer.parseInt(formObject
					.getTypeOfNICNo().trim()));

			if(!formObject.getisSEARCH())
				freezeControls();

		} catch (NumberFormatException e) {
			LOG.error("onLoad:10463");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void onSave() {
		try {
			// Driver's Name:
			formObject.setDriversName(driversName.getText().toString().trim());
			// NIC of the Driver:
			formObject.setNICNoOfDriver(nicOfTheDriver.getText().toString().trim());
			// Driving License No:
			formObject.setDrivingLicenceNo(drivingLicenseNumber.getText().toString().trim());

			// Expire Date Of License:
			// formObject.setExpiryDateOfLicence(drivingLicenseExpiryDate.getText().toString());

			if (datetextbox.getText().toString().trim().equals("")
					|| monthtextbox.getText().toString().trim().equals("")
					|| yeartextbox.getText().toString().trim().equals("")) {
				formObject.setExpiryDateOfLicence("");
			} else {
				formObject.setExpiryDateOfLicence(datetextbox.getText()
						.toString().trim()
						+ "/"
						+ monthtextbox.getText().toString().trim()
						+ "/"
						+ yeartextbox.getText().toString().trim());
			}

			int pos = relationshipSpinner.getSelectedItemPosition();
			// LogFile.d(TAG + "onSave" + "relationshipSpinner",
			// Integer.toString(relationshipSpinner.getSelectedItemPosition()));
			// LogFile.d(TAG + "onSave" + "relationshipSpinner",
			// relationshipSpinner.getItemAtPosition(pos).toString());

			for (RelationshipMapping fm : RelationshipMapping.values()) {
				int s= fm.getInt();
				if (pos== (fm.getInt()-1)) {
					formObject.setRelationshipBetweenDriverAndInsured(Integer
							.toString(fm.getInt()));
					break;
				}
			}

			int pos1 = typeOfDrivingLicenseSpinner.getSelectedItemPosition();
			// LogFile.d(TAG + "onSave" + "typeOfDrivingLicenseSpinner",
			// Integer.toString(typeOfDrivingLicenseSpinner.getSelectedItemPosition()));
			// LogFile.d(TAG + "onSave" + "typeOfDrivingLicenseSpinner",
			// typeOfDrivingLicenseSpinner.getItemAtPosition(pos1).toString());

			for (TypeofDrivingLicenseMapping fm : TypeofDrivingLicenseMapping.values()) {
				if (typeOfDrivingLicenseSpinner.getItemAtPosition(pos1).toString().equals(fm.getString())) {
					formObject.setTypeOfDrivingLicence((Integer.toString(fm.getInt())));
					break;
				}
			}

			int pos2 = oldnewDrivingLicenseSpinner.getSelectedItemPosition();
			// LogFile.d(TAG + "onSave" + "oldnewDrivingLicenseSpinner",
			// Integer.toString(oldnewDrivingLicenseSpinner.getSelectedItemPosition()));
			// LogFile.d(TAG + "onSave" + "oldnewDrivingLicenseSpinner",
			// oldnewDrivingLicenseSpinner.getItemAtPosition(pos2).toString());

			for (OldNewLicenseMapping fm : OldNewLicenseMapping.values()) {
				if (oldnewDrivingLicenseSpinner.getItemAtPosition(pos2).toString().equals(fm.getString())) {
					formObject.setSelectDL_NewOld(Integer.toString(fm.getInt()));
					break;
				}
			}

			int pos3 = competenceSpinner.getSelectedItemPosition();
			// LogFile.d(TAG + "onSave" + "competenceSpinner",
			// Integer.toString(competenceSpinner.getSelectedItemPosition()));
			// LogFile.d(TAG + "onSave" + "competenceSpinner",
			// competenceSpinner.getItemAtPosition(pos3).toString());

			for (Confirmation fm : Confirmation.values()) {
				if (competenceSpinner.getItemAtPosition(pos3).toString().equals(fm.getString())) {
					formObject.setCompetence(Integer.toString(fm.getInt()));
					break;
				}
			}
			
			int pos4 = typeOfNICSpinner.getSelectedItemPosition(); // Get Current Position.
			
			for (NICTypeMapping fm : NICTypeMapping.values()) {
				if (pos4 == fm.getInt()) {
					formObject.setTypeOfNICNo(Integer.toString(fm.getInt()));
					break;
				}
			}

			if (!formObject.getisSEARCH()) {
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave:10464");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
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

	public void on_click_drivername_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_drivername_reason_btn");

			int preSelect = formObject.getdrivername_reason_selector();
			datafordrivername_reason = new String[DriverNameReasonMapping.values().length];
			int i = 0;
			for (DriverNameReasonMapping fm : DriverNameReasonMapping.values()) {
				datafordrivername_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(DriverDetailsActivity.this)
			.setTitle("Reasons")
			.setSingleChoiceItems(datafordrivername_reason, preSelect,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,	int which) {
					driversName.setText("" + datafordrivername_reason[which]);
					formObject.setdrivername_reason_selector(which);
					dialog.dismiss();
				}
			})

			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					/* User clicked No so do some stuff */
					formObject.setdrivername_reason_selector(0);
					driversName.setText("");
					removeDialog(0);
				}
			});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			LOG.debug("SUCCESS ", "on_click_drivername_reason_btn");
		} catch (Exception e) {
			LOG.error("on_click_drivername_reason_btn");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_drivinglicenceno_reason_btn(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_drivinglicenceno_reason_btn");
			datafordriverlicenceno_reason = new String[DriverLicenceNoReasonMapping.values().length];
			int i = 0;
			for (DriverLicenceNoReasonMapping fm : DriverLicenceNoReasonMapping.values()) {
				datafordriverlicenceno_reason[i] = fm.getString();
				i++;
			}

			Dialog dialog = null;
			AlertDialog.Builder builder = new AlertDialog.Builder(DriverDetailsActivity.this)
			.setTitle("Reasons")
			.setSingleChoiceItems(datafordriverlicenceno_reason,
					preSelected1,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					drivingLicenseNumber.setText(""	+ datafordriverlicenceno_reason[which]);
					preSelected1 = which;
					dialog.dismiss();
				}
			})
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					preSelected1 = 0;
					drivingLicenseNumber.setText("");
					removeDialog(0);
				}
			});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			LOG.debug("SUCCESS ",  "on_click_drivinglicenceno_reason_btn");
		} catch (Exception e) {
			LOG.error("on_click_drivinglicenceno_reason_btn:10466");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	public void on_click_back_button(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_back_button");
			Application.goForward = false;
			Application.goBackward = true;
			onSave();
			Application.getInstance().doActionOnEvent(
					new EventParcel(UIEvent.DRIVERDETAILS_BACK_BUTTON_CLICK,
							this, formObject));
			LOG.debug("SUCCESS ", "on_click_back_button");
		} catch (Exception e) {
			LOG.error("on_click_back_button:10471");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_next_button(View v) {
		try {
			LOG.debug("ENTRY ", "on_click_next_button");

			onSave();

			String driverNameError = "";
			if (driversName.getText().toString().trim().equals("")) {
				driverNameError = "Please Enter 'Driver's Name'.\r\n";
			}
			String driverLicenseError = "";
			if (drivingLicenseNumber.getText().toString().trim().equals("")) {
				driverLicenseError = "Please Enter 'Driving License Number'.";
			}

			String expressionnicOld = "^[0-9]{9}[vVxX]$"; //Old NIC Format (9 digits with a letter following)
			String expressionnicnew = "^[0-9]{12}$"; //New NIC Format (12 digits, without letters)
			//String expressionpassport = "^([mMnNdD]|[oO][lL])[0-9]{7}$"; // Passport Format (1 letter and 7 digits)
			String nicval = "";

			if (formObject.isEditable()) {
				if (driversName.getText().toString().trim().equals("")
						|| drivingLicenseNumber.getText().toString().trim()
						.equals("")
						/*
						 * || datetextbox.getText().toString().equals("") ||
						 * monthtextbox.getText().toString().equals("") ||
						 * yeartextbox.getText().toString().equals("")
						 */) {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(
							DriverDetailsActivity.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setMessage(driverNameError + driverLicenseError);
					alertbox.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1) {
						}
					});
					alertbox.show();
				} else {
					if (!datetextbox.getText().toString().trim().equals("")
							|| !monthtextbox.getText().toString().trim()
							.equals("")
							|| !yeartextbox.getText().toString().trim()
							.equals("")) {

						// Original Exp:
						// ^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$
						String expression = "^((((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\\d)?\\d{2}))|((0[1-9]|1\\d|2[0-8])02((1[6-9]|[2-9]\\d)?\\d{2}))|(2902((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$";

						CharSequence inputStr = formObject.getExpiryDateOfLicence();
						Pattern pattern = Pattern.compile(expression);
						Matcher matcher = pattern.matcher(inputStr);
						String totalDateTextboxError = "";

						if (!matcher.matches()) {
							totalDateTextboxError = "Please Enter a valid 'Expiry Date of License'.";
							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									DriverDetailsActivity.this);
							alertbox.setTitle(R.string.saform);
							alertbox.setMessage(totalDateTextboxError);
							alertbox.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface arg0, int arg1) {
								}
							});
							alertbox.show();
						} else { 
							// Date fields are perfectly filled n checked
							// If ok goto next page
							if (!nicOfTheDriver.getText().toString().trim().equals("")) {								
								int typeOfNICNo = Integer.parseInt(formObject.getTypeOfNICNo());
								
								CharSequence inputStrnic = formObject.getNICNoOfDriver();
								Pattern patternnic = null;
								
								switch (typeOfNICNo) { // Different matchers for different types of NICs.
									case 0:
										patternnic = Pattern.compile(expressionnicOld);
										break;
									case 1:
										patternnic = Pattern.compile(expressionnicnew);
										break;
									case 2:
										//Not used
										patternnic = Pattern.compile(expressionnicOld);
										break;
								}								
								Matcher matchernic = patternnic.matcher(inputStrnic);								

								if (!matchernic.matches()) {
									
									if (typeOfNICNo == 2) {
										try {
											Application.goForward = true;
											Application.goBackward = false;
											Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
										} catch (Exception e) {
											Toast msg = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
											msg.show();
										}
										return;
									}
									
									nicval = "'ID No of Driver' is in incorrect format.\r\n";
									AlertDialog.Builder alertbox = new AlertDialog.Builder(
											DriverDetailsActivity.this);
									alertbox.setTitle(R.string.saform);
									alertbox.setMessage(nicval);
									alertbox.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface arg0,
														int arg1) {
												}
											});
									alertbox.show();
								} else {									
									try {
										Application.goForward = true;
										Application.goBackward = false;
										Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
									} catch (Exception e) {
										Toast msg = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
										msg.show();
									}
								}
							} else {
								try {
									Application.goForward = true;
									Application.goBackward = false;
									Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
								} catch (Exception e) {
									Toast msg = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
									msg.show();
								}
							}
						}
					}
					if (datetextbox.getText().toString().trim().equals("")
							&& monthtextbox.getText().toString().trim().equals("") // if date is totally null? it's
							&& yeartextbox.getText().toString().trim().equals("")) {

						if (!nicOfTheDriver.getText().toString().trim().equals("")) {
							int typeOfNICNo = Integer.parseInt(formObject.getTypeOfNICNo()); // Get the type Of the NIC from FormObject
							CharSequence inputStrnic = formObject.getNICNoOfDriver(); // Get NIC Number From the input
							Pattern patternnic = null;
							
							switch (typeOfNICNo) {
								case 0: // NIC (Old)
									patternnic = Pattern.compile(expressionnicOld);
									break;
								case 1: // NIC (New)
									patternnic = Pattern.compile(expressionnicnew);
									break;
								case 2: // Passport
									//Not used
									patternnic = Pattern.compile(expressionnicOld);
							}
							Matcher matchernic = patternnic.matcher(inputStrnic);
	
							if (!matchernic.matches()) {
								
								if (typeOfNICNo == 2) {
									try {
										Application.goForward = true;
										Application.goBackward = false;
										Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
									} catch (Exception e) {
										Toast msg = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
										msg.show();
									}
									return;
								}
								
								nicval = "'ID No of Driver' is in incorrect format.\r\n";
								AlertDialog.Builder alertbox = new AlertDialog.Builder(
										DriverDetailsActivity.this);
								alertbox.setTitle(R.string.saform);
								alertbox.setMessage(nicval);
								alertbox.setPositiveButton("Ok",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface arg0,
											int arg1) {
									}
								});
								alertbox.show();
							} else {
								try {
									Application.goForward = true;
									Application.goBackward = false;
									Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
								} catch (Exception e) {
									LOG.error("on_click_next_button:10470");
									if(e != null){
										LOG.error("Message: " + e.getMessage());
										LOG.error("StackTrace: " + e.getStackTrace());
									}
								}
							}
						} else {
							try {
								Application.goForward = true;
								Application.goBackward = false;
								Application.getInstance().doActionOnEvent(new EventParcel(
										UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK,
										this, formObject));
							} catch (Exception e) {
								LOG.error("on_click_next_button:10469");
								if(e != null){
									LOG.error("Message: " + e.getMessage());
									LOG.error("StackTrace: " + e.getStackTrace());
								}
							}
						}
					}
				}
			} else {
				try {
					Application.goForward = true;
					Application.goBackward = false;
					Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.DRIVERDETAILS_NEXT_BUTTON_CLICK, this, formObject));
				} catch (Exception e) {
					LOG.error("on_click_next_button:10468");
					if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					}
				}
			}
			LOG.debug("SUCCESS ", "on_click_next_button");
		} catch (Exception e) {
			LOG.error("on_click_next_button:10467");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	public void on_click_vehicle_class(View v) {
		try {
			LOG.debug("ENTRY ",  "on_click_vehicle_class");
			showDialog(DIALOG_MULTIPLE_CHOICE_VEHICLECLASS);
			LOG.debug("SUCCESS ","on_click_vehicle_class");
		} catch (Exception e) {
			LOG.error("on_click_vehicle_class:10554");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		try {
			final Calendar c = Calendar.getInstance();

			switch (id) {
			// case EXPIRYDATE:
			// return new DefaultDateSlider(this,
			// drivingLicenseExpiryDateDateTimeSetListener, c);

			case DIALOG_MULTIPLE_CHOICE_VEHICLECLASS:

				if (formObject.getSelectDL_NewOld().equals("1")) {
					booleanSelectVehecleClass = formObject
							.getselectVehicleClassForNew();
					dataforSelectVehicleClass = new String[NewSelectVehicleClassMapping
					                                       .values().length];
					int i = 0;
					for (NewSelectVehicleClassMapping fm : NewSelectVehicleClassMapping
							.values()) {
						dataforSelectVehicleClass[i] = fm.getString();
						i++;
					}

				} else if (formObject.getSelectDL_NewOld().equals("0")) {
					booleanSelectVehecleClass = formObject
							.getselectVehicleClassForOld();
					dataforSelectVehicleClass = new String[OldSelectVehicleClassMapping
					                                       .values().length];
					int i = 0;
					for (OldSelectVehicleClassMapping fm : OldSelectVehicleClassMapping
							.values()) {
						dataforSelectVehicleClass[i] = fm.getString();
						i++;
					}
				}

				Dialog dialog = null;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						DriverDetailsActivity.this)
				.setTitle(
						R.string.vehicle_class_new_alert_dialog_multi_choice)
						.setMultiChoiceItems(
								dataforSelectVehicleClass,
								booleanSelectVehecleClass,
								new DialogInterface.OnMultiChoiceClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton, boolean isChecked) {

										/*
										 * User clicked on a check box do some
										 * stuff
										 */
									}
								})
								.setPositiveButton(R.string.alert_dialog_ok,
										new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

										if (booleanSelectVehecleClass.length == 12) {
											formObject
											.setselectVehicleClassForOld(booleanSelectVehecleClass);
										}

										else if (booleanSelectVehecleClass.length == 13) {
											formObject
											.setselectVehicleClassForNew(booleanSelectVehecleClass);
										}
										removeDialog(0);
									}
								});

				if (!formObject.getisSEARCH()) {
					builder.setNegativeButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							/* User clicked No so do some stuff */

							boolean[] a = new boolean[12];
							boolean[] b = new boolean[13];
							formObject.setselectVehicleClassForOld(a);
							formObject.setselectVehicleClassForNew(b);
							removeDialog(0);
						}
					});
				}
				dialog = builder.create();

				if (!formObject.isEditable()) {
					((AlertDialog) dialog).getListView().setEnabled(false);
				}
				return dialog;
			}
			return null;
		} catch (Exception e) {
			LOG.error("onCreateDialog:10472");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
			return null;
		}
	}

	public class MyOnItemSelectedListenerRelationshipDriverInsured implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (RelationshipMapping fm : RelationshipMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setRelationshipBetweenDriverAndInsured(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10473");
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

	public class MyOnItemSelectedListenerTypeOfdrivingLicense implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (TypeofDrivingLicenseMapping fm : TypeofDrivingLicenseMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setTypeOfDrivingLicence((Integer.toString(fm.getInt())));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10474");
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

	public class MyOnItemSelectedListenerOldNewdrivingLicense implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (OldNewLicenseMapping fm : OldNewLicenseMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setSelectDL_NewOld(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10475");
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

	public class MyOnItemSelectedListenerCompetencedrivingLicense implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (Confirmation fm : Confirmation.values()) {
					if (parent.getItemAtPosition(pos).toString().equals(fm.getString())) {
						formObject.setCompetence(Integer.toString(fm.getInt()));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10476");
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
	
	/*
	 * Thisaru Guruge
	 * 01 / 02 / 2016
	 * This "OnOtemSelectedListener" class was created to listen to the NIC type Spinner
	 */
	public class MyOnItemSelectedListenerNICType implements
	OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				//Thisaru Guruge | 08/02/2016 | Added background for Spinner Item
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));

				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

				for (NICTypeMapping fm : NICTypeMapping.values()) {
					if (parent.getItemAtPosition(pos).toString().trim().toLowerCase().equals(fm.getString().trim().toLowerCase())) {
						formObject.setTypeOfNICNo((Integer.toString(fm.getInt())));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:1047");
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
}