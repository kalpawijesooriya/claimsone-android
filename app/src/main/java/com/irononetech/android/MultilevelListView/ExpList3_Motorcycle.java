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

public class ExpList3_Motorcycle extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {

	Logger LOG = LoggerFactory.getLogger(ExpList3_Motorcycle.class);

	private ColorExpListAdapterMotorcycle colorExpListAdapter;
	FormObject fo;
	static boolean booleanlistMotorcycle[][][][];
	static boolean booleanprelistMotorcycle[][][][];

	boolean booleanlistMotorcycleCopy[][][][];
	boolean booleanprelistMotorcycleCopy[][][][];
	int p1, p2, p3, p4;
	Button resetBtn;
	LinearLayout damageditems_header;

	EditText otherfield;

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<MainPart> mainPartsList = new ArrayList<MainPart>();


	private static final boolean [] CHECK_STATUS_TYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};
	static final String freqDamagedItemMapper [] = { "1/1", "1/2", "1/3", "1/36","1/17", "1/22","1/99", "1/100",
			"1/5" ,"1/14", "1/9","1/7" ,"1/58", "1/101" ,"1/15", "1/55",
			"1/19" ,"1/102", "1/105" ,"1/51", "1/42","1/37", "1/106" ,"1/40", "1/103","1/59",
			"1/8" ,"1/6", "1/57", "1/104", "1/16" ,"1/53", "1/54", "1/41", "1/107"};
	static final String listdesc[][][][] = { {
			{
					{"Motorcycle",  "Frequently damaged items" },{ "Head Light", "" },
					{ "Head Light Cowling", "" }, { "Visor", "" }, { "Front Fender", "" },
					{ "Front Fork Tube", "" }, { "T Column", "" },
					{ "F/Wheel Rim", "" },{ "Speedo Meter", "" },
					{ "Handle", "" },{ "Ignition Switch", "" },
					{ "Front L/H Mirror", "" },{ "Front L/H Signal Light", "" },
					{ "Rear Left Signal Light", "" },{ "L/S Clutch Brake Liver", "" },
					{ "Handle Switch Left", "" }, { "Crash Bar", "" }, {"Petrol Tank", ""},
					{"Main Foot Rest", ""}, {"Left Side Rear Foot Rest", ""}, {"Sari Guard", ""},
					{"Left Side Cover", ""}, {"Seat", ""}, {"Sear Cowl(L/R)", ""}, {"Seat Grip", ""},
					{"Rear Fender", ""}, {"Brake Light", ""}, {"Front R/H Mirror", ""}, {"Front R/H Signal Light", ""},
					{"Rear Right Signal Light", ""}, {"R/S Brake Liver", ""}, {"Handle Switch Right", ""},
					{"Silencer", ""}, {"Silencer Guard", ""}, {"Right Side Cover", ""}, {"R/S Rear Foot Rest", ""}

			},
		{ { "Motorcycle", "All parts" },
			{ "Head Light Lh", "" }, { "Head Light", "" }, //1
			{ "Head Light Cowling", "" }, { "Visor", "" }, { "Meter", "" }, //4
			{ "Handle", "" }, { "Front R/H Signal Light", "" },//6
			{ "Front L/H Signal Light", "" }, { "Front R/H Mirror", "" },//8
			{ "Front L/H Mirror", "" }, { "Clutch Lever", "" },//10
			{ "Break Lever", "" }, { "Clutch cable", "" },//12
			{ "Break cable", "" }, { "Ignition Switch", "" },//14
			{ "Handle Switch Left", "" }, { "Handle Switch Right", "" },//16
			{ "Front Fork Tube", "" }, { "Front Fork Seal", "" },//18
			{ "Petrol Tank", "" }, { "Petrol Tank Lid", "" },//20
			{ "P/Tank cowling (L/R)", "" }, { "T Column", "" },//22
			{ "Top Column", "" }, { "Handle Bolt", "" },//24
			{ "Handle cup set upper", "" }, { "Handle cup set Lover", "" },//26
			{ "Speedometer", "" }, { "Speedometer cable", "" },//28
			{ "Front Hub", "" }, { "Front Disk", "" }, { "Wheel Rim", "" },//31
			{ "Alloy Wheel Rim", "" }, { "Liver set Front", "" },//33
			{ "Front Tire", "" }, { "Front Tire Tube", "" },//35
			{ "Front Fender", "" }, { "Seat", "" },//38
			{ "Seat Cowling (L/R)", "" }, { "Handle Balancer", "" },//39
			{ "Seat Grip", "" }, { "Right Side Cover", "" },//41
			{ "Left Side Cover", "" }, { "Kick Paddle", "" },//43
			{ "Break Paddle", "" }, { "Gear Lever", "" },//45
			{ "Right side Front Footrest", "" },//
			{ "Right side Rear Footrest", "" },//
			{ "Left side Front Footrest", "" },//48
			{ "Left side Rear Footrest", "" }, { "Foot-rest Rubber", "" },//50
			{ "Sari Guard", "" }, { "Swing Arm", "" }, { "Silencer", "" },//53
			{ "Silencer Guard", "" }, { "Crash Bar", "" },//56
			{ "Right Fender", "" }, { "Rear Right Signal Light", "" },//57
			{ "Rear Left Signal Light", "" }, { "Brake Light", "" },
			{ "Rear Wheel Rim", "" }, { "Right Alloy Wheel Rim", "" },//61
			{ "Rear Tire", "" }, { "Rear Tire Tube", "" }, { "Chain", "" },
			{ "Spoke", "" }, { "Chain Case", "" }, { "Main Stand", "" },//67
			{ "Side Stand", "" }, { "Battery", "" },
			{ "Footrest Holder RH", "" }, { "Footrest Holder LH", "" },//71
			{ "Tank Sticker set", "" }, { "Tail cover Sticker LH/RH", "" },
			{ "R Shock Absorber (LH/RH)", "" }, { "Front panel Top", "" },//75
			{ "Front panel Bottom", "" }, { "Rear RH Panel Bottom", "" },
			{ "Rear LH Panel Bottom", "" }, { "Rear RH Panel Top", "" },//79
			{ "Rear LH Panel Top", "" }, { "Break caliper", "" },
			{ "Fork Oil", "" }, { "Head Light Bracket", "" },//83
			{ "R Additional Cover", "" }, { "Starter Motor", "" },
			{ "Break servo Tank", "" }, { "Chock Cable", "" },//87
			{ "Number Plate", "" }, { "Side panels (for scooters)", "" },
			{ "Top panels (for scooters)", "" },//90
			{ "v-panels (for scooters)", "" },
			{ "Center panels (for scooters)", "" },//92
			{ "R/S bottom panels (for scooters)", "" },
			{ "L/S bottom pales (for scooters)", "" },//94
			{ "Front buckets (for scooters)", "" }, { "Back rests", "" },
			{ "Rear crash bars", "" }, { "CDI unit", "" },//98
				{"F/Wheel Rim", ""}, {"Speedo Meter", ""}, {"L/S Clutch Brake Liver", ""}, {"Main Foot Rest", ""},//102
				{"Rear Fender", ""}, {"R/S Brake Liver", ""}, //104
				{"Left Side Rear Foot Rest", ""}, {"Sear Cowl(L/R)", ""},{"R/S Rear Foot Rest", ""}
		}, } };

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
				booleanprelistMotorcycle = Arrays.copyOf(fo.getBooleanprelistMotorcycle(), fo.getBooleanprelistMotorcycle().length);

				booleanprelistMotorcycleCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanprelistMotorcycleCopy[i][j][k][0] = booleanprelistMotorcycle[i][j][k][0];
							booleanprelistMotorcycleCopy[i][j][k][1] = booleanprelistMotorcycle[i][j][k][1];

							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanprelistMotorcycle[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanprelistMotorcycle[i][j-1][k][0], listdesc[i][j][k+1][1]);
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
				booleanlistMotorcycle = Arrays.copyOf(fo.getBooleanlistMotorcycle(), fo.getBooleanlistMotorcycle().length);

				booleanlistMotorcycleCopy = new boolean[p1][p2][p3][p4];

				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanlistMotorcycleCopy[i][j][k][0] = booleanlistMotorcycle[i][j][k][0];
							booleanlistMotorcycleCopy[i][j][k][1] = booleanlistMotorcycle[i][j][k][1];
							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanlistMotorcycle[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanlistMotorcycle[i][j-1][k][0], listdesc[i][j][k+1][1]);
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

			//display the list
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
												if(i != 0 && j!=0) {ExpList3_Motorcycle.booleanprelistMotorcycle[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_Motorcycle.booleanprelistMotorcycle[0][i-1][j-1][0] = true;}
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
												if(i != 0 && j!=0) {ExpList3_Motorcycle.booleanlistMotorcycle[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_Motorcycle.booleanlistMotorcycle[0][i-1][j-1][0]= true; break;}
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
			LOG.error("editEnable:10320");
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
			
			/*if (fo.getisPreSelected()) // PRE
			{
				back.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.back_button_xml));
			} else {
				back.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.back_button_xml));
			}*/
		} catch (Exception e) {
			LOG.error("editEnable:10322");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	/*public void editEnable() {
		try {
			otherfield.setFocusable(true);

			if (fo.getisPreSelected()) // PRE
			{
				back.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.back_button_xml));
			} else {
				back.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.cancel_button_xml));
			}
		} catch (NotFoundException e) {
			LOG.error("editEnable:10323");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		} catch (Exception e) {
			LOG.error("editEnable:10324");
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
				fo.setBooleanprelistMotorcycle(booleanprelistMotorcycle);
				fo.setpredamagedItemsOtherField(otherfield.getText().toString().trim());
			} else {
				fo.setBooleanlistMotorcycle(booleanlistMotorcycle);
				fo.setdamagedItemsOtherField(otherfield.getText().toString().trim());
			}
			Application.setFormObjectInstance(fo);
			
			Intent mIntent = new Intent(ExpList3_Motorcycle.this, VehicleDetailsActivity.class);
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
				fo.setBooleanprelistMotorcycle(booleanprelistMotorcycleCopy);
			} else {
				fo.setdamagedItemsOtherField(fo.getdamagedItemsOtherField());
				fo.setBooleanlistMotorcycle(booleanlistMotorcycleCopy);
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
				
				boolean isSameList = true;
				boolean isSameText = false;
				
				if (fo.getisPreSelected()) // PRE
				{
					//isSameList = Arrays.equals(booleanprelistBus, booleanprelistBusCopy);
					for (int i = 0; i < p1; i++) {
						for (int j = 0; j < p2; j++) {
							for (int k = 0; k < p3; k++) {
								if (booleanprelistMotorcycleCopy[i][j][k][0] != booleanprelistMotorcycle[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanprelistMotorcycleCopy[i][j][k][1] != booleanprelistMotorcycle[i][j][k][1]) {
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
								if (booleanlistMotorcycleCopy[i][j][k][0] != booleanlistMotorcycle[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanlistMotorcycleCopy[i][j][k][1] != booleanlistMotorcycle[i][j][k][1]) {
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
					AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Motorcycle.this);
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
				
				/*boolean[][][][] a = new boolean[p1][p2][p3][p4];
				if (!fo.getisPreSelected()) {
					// Damaged Items
					fo.setisVehicleShow(true);
					fo.setdamagedItemsOtherField("");
					fo.setpredamagedItemsOtherField("");
					fo.setBooleanlistMotorcycle(a);
					fo.setBooleanprelistMotorcycle(a);
				} else {
					fo.setBooleanprelistMotorcycle(a);
					fo.setpredamagedItemsOtherField(otherfield.getText()
							.toString().trim());
				}*/
			}
			finish();
		} catch (Exception e) {
			LOG.error("multilevel_back_button:10326");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}
	
	public void on_click_reset_button(View v) {
		try {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Motorcycle.this);
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
		listAdapter = new MyListAdapter(ExpList3_Motorcycle.this, mainPartsList);
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
	public boolean onQueryTextChange(String query) {
		if(query.equals("")){
			listAdapter.filterData(query);
			expandAll();
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listAdapter.filterData(query);
		expandAll();
		return false;
	}
}