package com.irononetech.android.formcomponent.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.draftserializer.FormObjSerializer;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.template.EventParcel;
import com.irononetech.android.template.UIEvent;
import com.irononetech.android.claimsone.R;

public class OtheritemsDetailsActivity extends Activity{
	Logger LOG = LoggerFactory.getLogger(OtheritemsDetailsActivity.class);
	FormObject formObject;

	EditText typeOfGoodsCarried;
	EditText weightOfGoodsCarried;
	EditText damagesToTheGoods;
	EditText otherVehiclesInvolved;
	EditText thirdPartyDamagesProperty;
	EditText injuries;
	Spinner spinneroverloaded;
	Spinner spinneroverweightcontributory;

	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");


			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			setContentView(R.layout.otheritems);

			imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			formObject = Application.getFormObjectInstance();

			typeOfGoodsCarried = (EditText) findViewById(R.id.typeofgoodscarriedtext);

			weightOfGoodsCarried = (EditText) findViewById(R.id.weightofgoodscarriedtext);

			damagesToTheGoods = (EditText) findViewById(R.id.damagestothegoodstext);

			otherVehiclesInvolved = (EditText) findViewById(R.id.othervehiclesinvolvedtext);

			thirdPartyDamagesProperty = (EditText) findViewById(R.id.thirdpartydamagespropertytext);

			injuries = (EditText) findViewById(R.id.injuriestext);


			spinneroverloaded = (Spinner) findViewById(R.id.overloaded_spinner);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					this, R.array.competence,
					R.layout.textviewforspinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinneroverloaded.setAdapter(adapter);
			spinneroverloaded.setOnItemSelectedListener(new MyOnItemSelectedListeneroverloaded());
			spinneroverloaded.setSelection(Integer.parseInt(formObject.getOverLoaded().trim()));


			spinneroverweightcontributory = (Spinner) findViewById(R.id.is_overweight_contributory_spinner);
			ArrayAdapter<CharSequence> overweightcontributoryadapter = ArrayAdapter.createFromResource(
					this, R.array.competence,
					R.layout.textviewforspinner);
			overweightcontributoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinneroverweightcontributory.setAdapter(overweightcontributoryadapter);
			spinneroverweightcontributory.setOnItemSelectedListener(new MyOnItemSelectedListeneroverweightcontributory());
			spinneroverweightcontributory.setSelection(Integer.parseInt(formObject.getOverWeightContributory().trim()));

			formComponentControler();
			onLoad();
			LOG.debug("SUCCESS onCreate");
		} catch (NumberFormatException e) {
			LOG.error("onCreate:10493");
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
			LOG.debug("SUCCESS onResume");
		} catch (Exception e) {
			LOG.error("onResume:10494");
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
			LOG.debug("ENTRY onPause");
			System.gc();
			LOG.debug("SUCCESS onPause");
		} catch (Exception e) {
			LOG.error("onPause:10495");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void formComponentControler(){
		try {
			if(formObject.getisDRAFT()){/*
				typeOfGoodsCarried.setFocusable(true);
				typeOfGoodsCarried.setFocusableInTouchMode(true); 
				typeOfGoodsCarried.setClickable(true);

				weightOfGoodsCarried.setFocusable(true);
				weightOfGoodsCarried.setFocusableInTouchMode(true); 
				weightOfGoodsCarried.setClickable(true);

				damagesToTheGoods.setFocusable(true);
				damagesToTheGoods.setFocusableInTouchMode(true); 
				damagesToTheGoods.setClickable(true);

				otherVehiclesInvolved.setFocusable(true);
				otherVehiclesInvolved.setFocusableInTouchMode(true); 
				otherVehiclesInvolved.setClickable(true);

				thirdPartyDamagesProperty.setFocusable(true);
				thirdPartyDamagesProperty.setFocusableInTouchMode(true); 
				thirdPartyDamagesProperty.setClickable(true);

				injuries.setFocusable(true);
				injuries.setFocusableInTouchMode(true); 
				injuries.setClickable(true);

				spinneroverloaded.setEnabled(true);	
				spinneroverweightcontributory.setEnabled(true);
			*/}
			if(formObject.getisSMS()){/*
				typeOfGoodsCarried.setFocusable(true);
				typeOfGoodsCarried.setFocusableInTouchMode(true); 
				typeOfGoodsCarried.setClickable(true);

				weightOfGoodsCarried.setFocusable(true);
				weightOfGoodsCarried.setFocusableInTouchMode(true); 
				weightOfGoodsCarried.setClickable(true);

				damagesToTheGoods.setFocusable(true);
				damagesToTheGoods.setFocusableInTouchMode(true); 
				damagesToTheGoods.setClickable(true);

				otherVehiclesInvolved.setFocusable(true);
				otherVehiclesInvolved.setFocusableInTouchMode(true); 
				otherVehiclesInvolved.setClickable(true);

				thirdPartyDamagesProperty.setFocusable(true);
				thirdPartyDamagesProperty.setFocusableInTouchMode(true); 
				thirdPartyDamagesProperty.setClickable(true);

				injuries.setFocusable(true);
				injuries.setFocusableInTouchMode(true); 
				injuries.setClickable(true);

				spinneroverloaded.setEnabled(true);	
				spinneroverweightcontributory.setEnabled(true);
			*/}
			if(formObject.getisSEARCH()){
				typeOfGoodsCarried.setTextColor(Color.GRAY);
				typeOfGoodsCarried.setFocusable(false);
				typeOfGoodsCarried.setFocusableInTouchMode(false); 
				typeOfGoodsCarried.setClickable(false);

				weightOfGoodsCarried.setTextColor(Color.GRAY);
				weightOfGoodsCarried.setFocusable(false);
				weightOfGoodsCarried.setFocusableInTouchMode(false); 
				weightOfGoodsCarried.setClickable(false);

				damagesToTheGoods.setTextColor(Color.GRAY);
				damagesToTheGoods.setFocusable(false);
				damagesToTheGoods.setFocusableInTouchMode(false); 
				damagesToTheGoods.setClickable(false);

				otherVehiclesInvolved.setTextColor(Color.GRAY);
				otherVehiclesInvolved.setFocusable(false);
				otherVehiclesInvolved.setFocusableInTouchMode(false); 
				otherVehiclesInvolved.setClickable(false);

				thirdPartyDamagesProperty.setTextColor(Color.GRAY);
				thirdPartyDamagesProperty.setFocusable(false);
				thirdPartyDamagesProperty.setFocusableInTouchMode(false); 
				thirdPartyDamagesProperty.setClickable(false);

				injuries.setTextColor(Color.GRAY);
				injuries.setFocusable(false);
				injuries.setFocusableInTouchMode(false); 
				injuries.setClickable(false);

				spinneroverloaded.setEnabled(false);
				spinneroverweightcontributory.setEnabled(false);
			}
		} catch (Exception e) {
			LOG.error("formComponentControler:10496");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onLoad() {
		try {
			// typeOfGoodsCarried:
			typeOfGoodsCarried.setText(formObject.getTypeOfGoodsCarried());

			// weightOfGoodsCarried:
			weightOfGoodsCarried.setText(formObject.getWeightOfGoodsCarried());

			// damagesToTheGoods:
			damagesToTheGoods.setText(formObject.getDamagesToTheGoods());

			// otherVehiclesInvolved:
			otherVehiclesInvolved.setText(formObject.getOtherVehiclesInvolved());

			// thirdPartyDamagesProperty:
			thirdPartyDamagesProperty.setText(formObject.getThirdPartyDamagesProperty());

			// injuries:
			injuries.setText(formObject.getInjuries_InsuredAnd3rdParty());

			spinneroverloaded.setSelection(Integer.parseInt(formObject.getOverLoaded().trim()));

			spinneroverweightcontributory.setSelection(Integer.parseInt(formObject.getOverWeightContributory().trim()));
		} catch (NumberFormatException e) {
			LOG.error("onLoad:10497");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("onLoad:10498");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void onSave(){
		try {
			//typeOfGoodsCarried:
			formObject.setTypeOfGoodsCarried((typeOfGoodsCarried.getText().toString().trim()));
			//weightOfGoodsCarried:
			formObject.setWeightOfGoodsCarried((weightOfGoodsCarried.getText().toString().trim()));
			//damagesToTheGoods:
			formObject.setDamagesToTheGoods((damagesToTheGoods.getText().toString().trim()));
			//otherVehiclesInvolved:
			formObject.setOtherVehiclesInvolved((otherVehiclesInvolved.getText().toString().trim()));
			//thirdPartyDamagesProperty:
			formObject.setThirdPartyDamagesProperty((thirdPartyDamagesProperty.getText().toString().trim()));
			//injuries:
			formObject.setInjuries_InsuredAnd3rdParty((injuries.getText().toString().trim()));

			int pos = spinneroverloaded.getSelectedItemPosition();
			for(Confirmation fm : Confirmation.values()){
				if(spinneroverloaded.getItemAtPosition(pos).toString().equals(fm.getString())){
					formObject.setOverLoaded((Integer.toString(fm.getInt())));
					break;
				}
			}

			int pos1 = spinneroverweightcontributory.getSelectedItemPosition();
			for(Confirmation fm : Confirmation.values()){
				if(spinneroverweightcontributory.getItemAtPosition(pos1).toString().equals(fm.getString())){
					formObject.setOverWeightContributory(((Integer.toString(fm.getInt()))));
					break;
				}
			}

			if(!formObject.getisSEARCH()){
				FormObjSerializer.serializeFormData(formObject);
			}
		} catch (Exception e) {
			LOG.error("onSave:10499");
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


	public void on_click_next_button(View v){
		try {
			LOG.debug("ENTRY ", "on_click_next_button");
			Application.goForward = true;
			Application.goBackward = false;
			onSave();
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.OTHERITEMS_NEXT_BUTTON_CLICK, this, formObject));
			LOG.debug("SUCCESS ",  "on_click_next_button");
		} catch (Exception e) {
			LOG.error("on_click_next_button:10500");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_back_button(View v){
		try {
			LOG.debug("ENTRY ", "on_button_click");
			Application.goForward = false;
			Application.goBackward = true;
			onSave();
			Application.getInstance().doActionOnEvent(new EventParcel(UIEvent.OTHERITEMS_BACK_BUTTON_CLICK, this, formObject));
			LOG.debug("SUCCESS ", "on_button_click");
		} catch (Exception e) {
			LOG.error("on_click_back_button:10501");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public class MyOnItemSelectedListeneroverloaded implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));
				
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
				
				for(Confirmation fm : Confirmation.values()){
					if(parent.getItemAtPosition(pos).toString().equals(fm.getString())){
						formObject.setOverLoaded((Integer.toString(fm.getInt())));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10502");
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

	public class MyOnItemSelectedListeneroverweightcontributory implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			try {
				((TextView) parent.getChildAt(0)).setBackgroundDrawable(getResources().getDrawable(R.drawable.motorclaims_spinneritem_bg));
				
				if(formObject.getisSEARCH())
					((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
				
				for(Confirmation fm : Confirmation.values()){
					if(parent.getItemAtPosition(pos).toString().equals(fm.getString())){
						formObject.setOverWeightContributory(((Integer.toString(fm.getInt()))));
						break;
					}
				}
			} catch (Exception e) {
				LOG.error("onItemSelected:10503");
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
