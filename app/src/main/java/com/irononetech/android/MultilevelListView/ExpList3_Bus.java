package com.irononetech.android.MultilevelListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.view.VehicleDetailsActivity;

public class ExpList3_Bus extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {
	Logger LOG = LoggerFactory.getLogger(ExpList3_Bus.class);
	
	private ColorExpListAdapterBus colorExpListAdapter;
	FormObject fo;
	static boolean booleanlistBus[][][][];
	static boolean booleanprelistBus[][][][];
	
	boolean booleanlistBusCopy[][][][];
	boolean booleanprelistBusCopy[][][][];
	int p1, p2, p3, p4;
	Button resetBtn;
	LinearLayout damageditems_header;

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<MainPart> mainPartsList = new ArrayList<MainPart>();
	
	EditText otherfield;

	private static final boolean [] CHECK_STATUS_TYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};
	static final String freqDamagedItemMapper [] = {"1/0", "1/13", "1/14", "1/16", "1/17", "1/18", "1/19", "1/20", "1/21", "1/22",
													"1/23", "1/25", "1/31", "1/32", "4/0", "4/19", "4/20", "4/21", "4/22", "4/23", "4/24"};
	static final String listdesc[][][][] = { { // grey
		 {
				 { "Bus", "Frequently damaged items" }, { "Front buffer", "" }, { "L/H/S front buffer end ", "" },
				 { "R/H/S front buffer end", "" },{ "R/H/S head lamp", "" }, { "L/H/S head lamp", "" },
				 { "R/H/S head lamp frame", "" }, { "L/H/S head lamp frame", "" }, { "R/H/S front signal lamp", "" },
				 { "L/H/S front signal lamp", "" }, { "R/H/S front parking lamp", "" }, { "L/H/S front parking lamp", "" },
				 { "Front name board light", "" }, { "Front number plate", "" }, { "Front number plate lamp", "" },
				 { "Rear buffer", "" }, { "R/H/S Rear tail light", "" }, { "L/H/S Rear tail light", "" }, { "R/H/S rear parking lamp", "" },
				 { "L/H/S rear parking lamp", "" }, { "R/H/S rear buffer end", "" }, { "L/H/S rear buffer end", "" }},
		{ // lightgray
		{ "Bus", "Front face panel" },
				{ "Front buffer", "" },//0
				{ "Front windscreen", "" }, { "Front grill", "" },
				{ "Front badge", "" }, { "Dash board", "" },
				{ "A/C blower", "" }, { "A/C cooler", "" },
				{ "R/H/S front windscreen", "" },
				{ "L/H/S front windscreen", "" },
				{ "R/H/S front windscreen beading", "" },
				{ "L/H/S front windscreen beading", "" },
				{ "Front windscreen (single glass)", "" },
				{ "Front single windscreen beading", "" },
				{ "L/H/S front buffer end ", "" },
				{ "R/H/S front buffer end", "" },
				{ "Front face aluminum molding", "" },
				{ "R/H/S head lamp", "" }, { "L/H/S head lamp", "" },
				{ "R/H/S head lamp frame", "" },
				{ "L/H/S head lamp frame", "" },
				{ "R/H/S front signal lamp", "" },
				{ "L/H/S front signal lamp", "" },
				{ "R/H/S front parking lamp", "" },
				{ "L/H/S front parking lamp", "" },
				{ "Front name board glass", "" },
				{ "Front name board light", "" },
				{ "Front name board glass beading", "" },
				{ "A/C Condenser", "" }, { "A/C Filter", "" },
				{ "Horn", "" }, { "Air horn", "" },
				{ "Front number plate", "" },//31
				{ "Front number plate lamp", "" } },
		{ // darkgray
		{ "Bus", "Right body panel" }, { "R/H/S driver door", "" },
				{ "R/H/S cut glass", "" }, { "R/H/S fit glass", "" },
				{ "R/H/S rain gutter", "" }, { "R/H/S body molding", "" },
				{ "R/H/S door hinges", "" }, { "R/H/S front tire", "" },
				{ "R/H/S Driver door glass", "" },
				{ "R/H/S Driver door glass beading", "" },
				{ "R/H/S Door hinges", "" },
				{ "R/H/S Cut glass frames", "" },
				{ "R/H/S Fit glass frames", "" },
				{ "R/H/S Cut glass beading", "" },
				{ "R/H/S Fit glass beading", "" },
				{ "R/H/S Body aluminum molding", "" },
				{ "R/H/S Emergency door", "" },
				{ "R/H/S Emergency door glass", "" },
				{ "R/H/S Emergency door glass beading", "" },
				{ "R/H/S Front rim", "" }, { "R/H/S Rear tire outer", "" },
				{ "R/H/S Rear tire outer rim", "" },
				{ "R/H/S Rear tire inner", "" },
				{ "R/H/S Rear tire inner rim", "" },
				{ "R/H/S Mirror", "" },
				{ "R/H/S Front tire Wheel nuts", "" },
				{ "R/H/S Rear tire outer Wheel nuts", "" },
				{ "R/H/S Rear tire inner Wheel nuts", "" },
				{ "Rim cups (if available)", "" }, { "Side lights", "" },
				{ "Body beading aluminum", "" },
				{ "Body beading rubber", "" } },
		{ // darkgray
		{ "Bus", "Left body panel" }, { "L/H/S front door", "" },
				{ "L/H/S front board", "" }, { "L/H/S cut glasses", "" },
				{ "L/H/S foot board", "" }, { "L/H/S fit glasses", "" },
				{ "L/H/S body rubber", "" },
				{ "L/H/S rear tire inner", "" },
				{ "L/H/S Front door glass", "" },
				{ "L/H/S Front door glass beading", "" },
				{ "L/H/S Front door rubber flap", "" },
				{ "L/H/S Front door winder", "" },
				{ "L/H/S Foot board lamp", "" },
				{ "L/H/S Front rain gutter", "" },
				{ "L/H/S Body aluminum molding", "" },
				{ "L/H/S Body rubber molding", "" },
				{ "L/H/S Cut glasses frames", "" },
				{ "L/H/S Fit glasses frames", "" },
				{ "L/H/S Cut glass beading", "" },
				{ "L/H/S Fit glasses beading", "" },
				{ "L/H/S Rear foot board", "" },
				{ "L/H/S Rear foot board lamp", "" },
				{ "L/H/S Rear foot board door", "" },
				{ "L/H/S Rear foot board door glass", "" },
				{ "L/H/S Rear foot board door beading", "" },
				{ "L/H/S Rear foot board door rubber flap", "" },
				{ "L/H/S Rear rain gutter", "" },
				{ "L/H/S Front tire", "" }, { "L/H/S Front tire rim", "" },
				{ "L/H/S Rear tire outer", "" },
				{ "L/H/S Rear tire outer rim", "" },
				{ "L/H/S Rear tire inner", "" },
				{ "L/H/S Rear tire inner rim", "" },
				{ "L/H/S Front tire Wheel nuts", "" },
				{ "L/H/S Rear tire outer Wheel nuts", "" },
				{ "L/H/S Rear tire inner Wheel nuts", "" },
				{ "L/H/S Rim cups (if available)", "" },
				{ "Side lights", "" }, { "Body beading aluminum", "" },
				{ "Body beading rubber", "" } },
		{ // darkgray
		{ "Bus", "Rear side" },
				{ "Rear buffer", "" },
				{ "Dickey door", "" }, { "Dickey door lights", "" },
				{ "Dickey door ladder", "" }, { "Rear number plate", "" },
				{ "Dickey door locks", "" }, { "Dickey door garnish", "" },
				{ "Dickey door handles", "" },
				{ "Dickey door beadings", "" }, { "Rear windscreen", "" },
				{ "R/H/S Rear windscreen", "" },
				{ "L/H/S Rear windscreen", "" },
				{ "R/H/S Rear windscreen beading", "" },
				{ "R/H/S Rear windscreen beading", "" },
				{ "Rear windscreen beading", "" },
				{ "Rear name board box glass", "" },
				{ "Rear name board box glass beading", "" },
				{ "Rear name board box glass lamp", "" },
				{ "L/H/S tail light rear", "" },
				{ "R/H/S Rear tail light", "" },
				{ "L/H/S Rear tail light", "" },
				{ "R/H/S rear parking lamp", "" },
				{ "L/H/S rear parking lamp", "" },
				{ "R/H/S rear buffer end", "" },
				{ "L/H/S rear buffer end", "" },//24
				{ "Rear number plate lamp", "" },
				{ "Back body aluminum molding", "" },
				{ "Back body rubber beading", "" },
				{ "Rear windscreen mirror", "" },
				{ "Body beading rubber", "" },
				{ "Body beading aluminum", "" } },
		{ { "Bus", "Engine compartment and components" },
				{ "Diesel, petrol engine (gasoline engine)", "" },
				{ "Accessory belt", "" }, { "Air duct", "" },
				{ "Air intake housing", "" },
				{ "Air intake manifold", "" }, { "Camshaft", "" },
				{ "Camshaft bearing", "" }, { "Camshaft fastener", "" },
				{ "Camshaft follower", "" },
				{ "Camshaft locking plate", "" },
				{ "Camshaft pushrod", "" }, { "Camshaft spacer ring", "" },
				{ "Connecting rod", "" }, { "Connecting rod bearing", "" },
				{ "Connecting rod bolt", "" },
				{ "Connecting rod washer", "" }, { "Crank case", "" },
				{ "Crank pulley", "" }, { "Crankshaft", "" },
				{ "Crankshaft oil seal", "" }, { "Cylinder head", "" },
				{ "Cylinder head cover", "" },
				{ "Other cylinder head cover parts", "" },
				{ "Cylinder head gasket", "" }, { "Distributor", "" },
				{ "Distributor cap", "" }, { "Drive belt", "" },
				{ "Engine block", "" }, { "Engine block parts", "" },
				{ "Engine cradle", "" },
				{ "Engine shake damper and vibration absorber", "" },
				{ "Engine valve", "" }, { "Fan belt", "" },
				{ "Gudgeon pin (wrist pin)", "" },
				{ "Harmonic balancer", "" }, { "Heater", "" },
				{ "Mounting bolt", "" },
				{ "Piston pin and crank pin", "" },
				{ "Piston pin bush", "" },
				{ "Piston ring and circlip", "" }, { "Piston valve", "" },
				{ "Poppet valve", "" },
				{ "PCV valve (positive crankcase ventilation valve)", "" },
				{ "Pulley part", "" }, { "Rocker arm", "" },
				{ "Rocker cover Starter motor", "" },
				{ "Rocker cover Starter pinion", "" },
				{ "Rocker cover Starter ring", "" },
				{ "Turbocharger and supercharger", "" }, { "Tappet", "" },
				{ "Timing tape ", "" }, { "Valve cover", "" },
				{ "Valve housing", "" }, { "Valve spring", "" },
				{ "Valve stem seal", "" }, { "Water pump pulley", "" } },
		{ { "Bus", "Engine cooling system" }, { "Air blower", "" },
				{ "Coolant hose cooling fan", "" }, { "Fan blade", "" },
				{ "Fan clutch", "" }, { "Radiator bolt", "" },
				{ "Radiator shroud", "" }, { "Radiator gasket", "" },
				{ "Radiator pressure cap", "" }, { "Water neck", "" },
				{ "Water neck o-ring", "" }, { "Water pipe", "" },
				{ "Water pump", "" }, { "Water pump gasket", "" },
				{ "Water tank", "" }, { "Thermostat", "" } },
		{ { "Bus", "Engine oil system" }, { "Oil filter", "" },
				{ "Oil gasket", "" }, { "Oil pan", "" },
				{ "Oil pipe", "" }, { "Oil pump", "" },
				{ "Oil strainer", "" } },
		{ { "Bus", "Exhaust system" }, { "Catalytic converter", "" },
				{ "Exhaust clamp and bracket", "" },
				{ "Exhaust flange gasket", "" }, { "Exhaust gasket", "" },
				{ "Exhaust manifold", "" },
				{ "Exhaust manifold gasket", "" }, { "Exhaust pipe", "" },
				{ "Heat shield", "" }, { "Heat sleeving and tape", "" },
				{ "Muffler (silencer)", "" } },
		{ { "Bus", "Fuel supply system" }, { "Air filter", "" },
				{ "Carburetor", "" }, { "Carburetor parts", "" },
				{ "Choke cable", "" }, { "EGR valve", "" },
				{ "Fuel cap", "" }, { "Fuel cell", "" },
				{ "Fuel cell component", "" }, { "Fuel cooler", "" },
				{ "Fuel distributor", "" }, { "Fuel filter", "" },
				{ "Fuel filter seal", "" }, { "Fuel injector", "" },
				{ "Fuel injector nozzle", "" }, { "Fuel pump", "" },
				{ "Fuel pump gasket", "" },
				{ "Fuel pressure regulator", "" }, { "Fuel rail", "" },
				{ "Fuel tank", "" }, { "Fuel tank cover", "" },
				{ "Fuel water separator", "" }, { "Intake manifold", "" },
				{ "Intake manifold gasket", "" },
				{ "LPG (Liquefied petroleum gas) system assembly", "" },
				{ "Throttle body", "" }, { "Universal joint", "" } },
		{ { "Bus", "Other miscellaneous parts" },
				{ "Adhesive tape and foil", "" }, { "Bolt cap", "" },
				{ "License plate bracket", "" },
				{ "Speedometer cable", "" }, { "Cotter pin", "" },
				{ "Decal", "" }, { "Dashboard", "" },
				{ "Center console", "" }, { "Glove compartment", "" },
				{ "Drag link", "" }, { "Dynamic seal", "" },
				{ "Fastener", "" },
				{ "Gasket: Flat, molded, profiled", "" },
				{ "Hood and trunk release cable", "" },
				{ "Horn and trumpet horn", "" },
				{ "Injection-molded parts", "" },
				{ "Instrument cluster", "" }, { "Label", "" },
				{ "Mirror", "" }, { "Mount and mounting", "" },
				{ "Name plate", "" }, { "Flange nut", "" },
				{ "Hex nut", "" }, { "O-ring", "" }, { "Paint", "" },
				{ "Rivet", "" }, { "Rubber (extruded and molded)", "" },
				{ "Screw", "" }, { "Shim", "" }, { "Sun visor", "" } }, } };

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.multilevel_expandable_list_main);

			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			search = (SearchView) findViewById(R.id.search_damaged_items);
			search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			search.setIconifiedByDefault(false);
			search.setOnQueryTextListener(this);
			search.setOnCloseListener(this);

			myList = (ExpandableListView) findViewById(R.id.expandableListView);

			fo = Application.getFormObjectInstance();
			otherfield = (EditText) findViewById(R.id.damagedItemsOtherField);

			resetBtn = (Button) findViewById(R.id.resetButton);
			damageditems_header = (LinearLayout) findViewById(R.id.damageditems_header);
			
			p1 = Application.get4DArrSizeSec1();
			p2 = Application.get4DArrSizeSec2();
			p3 = Application.get4DArrSizeSec3();
			p4 = Application.get4DArrSizeSec4();
			
			if (fo.getisPreSelected()){ // PRE
				otherfield.setText(fo.getpredamagedItemsOtherField());
				booleanprelistBus = Arrays.copyOf(fo.getBooleanprelistBus(), fo.getBooleanprelistBus().length);
				
				booleanprelistBusCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanprelistBusCopy[i][j][k][0] = booleanprelistBus[i][j][k][0];
							booleanprelistBusCopy[i][j][k][1] = booleanprelistBus[i][j][k][1];

							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanprelistBus[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanprelistBus[i][j-1][k][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							}
						}
						MainPart mainPart = new MainPart(2+"/"+j+"0", listdesc[i][j][0][1],subParts);
						mainPartsList.add(mainPart);
					}
				}
				damageditems_header.setBackgroundDrawable(getResources().getDrawable(R.drawable.predamageditems_header));
			} else {
				otherfield.setText(fo.getdamagedItemsOtherField());
				booleanlistBus = Arrays.copyOf(fo.getBooleanlistBus(), fo.getBooleanlistBus().length);
				
				booleanlistBusCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanlistBusCopy[i][j][k][0] = booleanlistBus[i][j][k][0];
							booleanlistBusCopy[i][j][k][1] = booleanlistBus[i][j][k][1];
							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanlistBus[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanlistBus[i][j-1][k][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							}
						}

						MainPart mainPart = new MainPart(2+"/"+j, listdesc[i][j][0][1],subParts);
						mainPartsList.add(mainPart);
					}
				}
				damageditems_header.setBackgroundDrawable(getResources().getDrawable(R.drawable.damageditems_header));
			}
			
			if (!fo.isEditable()) {
				editDisable();
			}

			displayList();

			myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					try {
						FormObject formObject = Application.getFormObjectInstance();
						if (!formObject.isEditable()) {

						} else {
							CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox1);

							if(formObject.getisPreSelected()) {
								if (checkBox.isChecked()) { //if is already checked
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Bus.booleanprelistBus[0][i-1][j-1][0] = false;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, false);
								} else { //if user want to mark it damaged
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Bus.booleanprelistBus[0][i-1][j-1][0] = true;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, true);
								}
							} else { //if it is not pre selected
								if (checkBox.isChecked()) { //if is already checked
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Bus.booleanlistBus[0][i-1][j-1][0] = false;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, false);
								} else { //if user want to mark it damaged
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Bus.booleanlistBus[0][i-1][j-1][0] = true;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, true);
								}
							}
						}
					} catch (Exception e) {
						LOG.error("onChildClick:10218");
						if(e != null){
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}
					}
					return false;
				}
			});
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10296");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void editDisable() {
		try {
			otherfield.setFocusable(false);
			resetBtn.setVisibility(View.GONE);
			
			/*
			 * if(fo.getisPreSelected()) //PRE {
			 * back.setBackgroundDrawable(getResources
			 * ().getDrawable(R.drawable.back_button_xml)); } else{
			 * back.setBackgroundDrawable
			 * (getResources().getDrawable(R.drawable.back_button_xml)); }
			 */
		} catch (Exception e) {
			LOG.error("editDisable:10298");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	/*public void editEnable() {
		try {
			otherfield.setFocusable(true);
						
			/*
			 * if(fo.getisPreSelected()) //PRE {
			 * back.setBackgroundDrawable(getResources
			 * ().getDrawable(R.drawable.back_button_xml)); } else{
			 * back.setBackgroundDrawable
			 * (getResources().getDrawable(R.drawable.cancel_button_xml)); }
			 *
		} catch (Exception e) {
			LOG.error("editEnable:10300");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}*/

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void multilevel_ok_click(View v) {
		okCommon();
	}

	private void okCommon() {
		try {
			if (fo.getisPreSelected()) // PRE
			{
				fo.setBooleanprelistBus(booleanprelistBus);
				fo.setpredamagedItemsOtherField(otherfield.getText().toString().trim());
			} else {
				fo.setBooleanlistBus(booleanlistBus);
				fo.setdamagedItemsOtherField(otherfield.getText().toString().trim());
			}
			Application.setFormObjectInstance(fo);
			
			Intent mIntent = new Intent(ExpList3_Bus.this, VehicleDetailsActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			finish();
		} catch (Exception e) {
			LOG.error("okCommon:10301");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private void backCommon() {
		try {
			if (fo.getisPreSelected()) // PRE
			{
				fo.setpredamagedItemsOtherField(fo.getpredamagedItemsOtherField());
				fo.setBooleanprelistBus(booleanprelistBusCopy);
			} else {
				fo.setdamagedItemsOtherField(fo.getdamagedItemsOtherField());
				fo.setBooleanlistBus(booleanlistBusCopy);
			}
			Application.setFormObjectInstance(fo);
		} catch (Exception e) {
			LOG.error("backCommon:13388");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void multilevel_back_button(View v) {
		try {
			if (fo.isEditable()) {
				/*
				 * int p1 = Application.get4DArrSizeSec1(); int p2 =
				 * Application.get4DArrSizeSec2(); int p3 =
				 * Application.get4DArrSizeSec3(); int p4 =
				 * Application.get4DArrSizeSec4(); boolean[][][][] a = new
				 * boolean[p1][p2][p3][p4];
				 */

				boolean isSameList = true;
				boolean isSameText = false;
				
				if (fo.getisPreSelected()) // PRE
				{
					//isSameList = Arrays.equals(booleanprelistBus, booleanprelistBusCopy);
					for (int i = 0; i < p1; i++) {
						for (int j = 0; j < p2; j++) {
							for (int k = 0; k < p3; k++) {
								if (booleanprelistBusCopy[i][j][k][0] != booleanprelistBus[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanprelistBusCopy[i][j][k][1] != booleanprelistBus[i][j][k][1]) {
									isSameList = false;
									break;
								}
							}
						}
					}
					
					if (otherfield.getText().toString().toLowerCase().equals((fo.getpredamagedItemsOtherField().toLowerCase())))
						isSameText = true;
				} else {
					// isSameList = Arrays.equals(booleanlistBus, booleanlistBusCopy);
					for (int i = 0; i < p1; i++) {
						for (int j = 0; j < p2; j++) {
							for (int k = 0; k < p3; k++) {
								if (booleanlistBusCopy[i][j][k][0] != booleanlistBus[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanlistBusCopy[i][j][k][1] != booleanlistBus[i][j][k][1]) {
									isSameList = false;
									break;
								}
							}
						}
					}

					if (otherfield.getText().toString().toLowerCase().equals((fo.getdamagedItemsOtherField().toLowerCase())))
						isSameText = true;
				}

				if (isSameList && isSameText) {
					finish();
					return;
				} else {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Bus.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setCancelable(false);
					alertbox.setMessage("Do you want to save changes?");
					alertbox.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									okCommon();
									removeDialog(arg1);
									finish();
								}
							});
					alertbox.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									backCommon();
									removeDialog(arg1);
									finish();
								}
							});
					alertbox.show();
					return;
				}

				// Damaged Items
				/*
				 * if(!fo.getisPreSelected()) { //fo.setisVehicleShow(true);
				 * //fo.setdamagedItemsOtherField("");
				 * //fo.setpredamagedItemsOtherField("");
				 * //fo.setBooleanlistBus(a); //fo.setBooleanprelistBus(a); }
				 * else{ //Pre Damaged Items fo.setBooleanprelistBus(a);
				 * fo.setpredamagedItemsOtherField
				 * (otherfield.getText().toString().trim()); }
				 */
			}
			
			finish();
		} catch (Exception e) {
			LOG.error("multilevel_back_button:10302");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void on_click_reset_button(View v) {
		try {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Bus.this);
			alertbox.setTitle(R.string.saform);
			alertbox.setCancelable(false);
			alertbox.setMessage("'Point of Impact', 'Damaged Items', 'Tyre Conditions' and 'PAD Items' will be reset. Are you sure?");
			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							fo.setisVehicleShow(true);
							fo.setdamagedItemsOtherField("");
							fo.setpredamagedItemsOtherField("");
							
							fo.setBooleanlistBus(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistCar(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistLorry(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistVan(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistThreeWheel(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistMotorcycle(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistTractor4WD(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistHandTractor(new boolean[p1][p2][p3][p4]);
							
							fo.setBooleanprelistBus(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistCar(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistLorry(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistVan(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistThreeWheel(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistMotorcycle(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistTractor4WD(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistHandTractor(new boolean[p1][p2][p3][p4]);
							File imgFile = new  File(URL.getSLIC_JOBS() + fo.getJobNo() + "/PointsOfImpact/"
									+ fo.getJobNo() + "_PointsOfImpact.jpg");
							if(imgFile.exists()){
								imgFile.delete();
							}
							fo.setPointOfImpactList(null);
							fo.setselectTirecondition(CHECK_STATUS_TYRE);
							fo.setvehicleType(0);

							Application.setFormObjectInstance(fo);
							removeDialog(arg1);
							finish();
						}
					});
			alertbox.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							removeDialog(arg1);
						}
					});
			alertbox.show();
		} catch (Exception e) {
			LOG.error("on_click_reset_button:10772");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	//method to expand all groups
	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++){
			myList.expandGroup(i);
		}
	}

	//method to expand all groups
	private void displayList() {
		//get reference to the ExpandableListView
		//create the adapter by passing your ArrayList data
		listAdapter = new MyListAdapter(ExpList3_Bus.this, mainPartsList);
		//attach the adapter to the list
		myList.setAdapter(listAdapter);

	}

	@Override
	public boolean onClose() {
		listAdapter.filterData("");
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listAdapter.filterData(query);
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String query) {
        if(query.equals("")){
            listAdapter.filterData(query);
            expandAll();
        }
		return false;
	}
}
