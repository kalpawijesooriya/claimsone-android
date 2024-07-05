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

public class ExpList3_ThreeWheel extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {
	Logger LOG = LoggerFactory.getLogger(ExpList3_ThreeWheel.class);
	
	private ColorExpListAdapterThreeWheel colorExpListAdapter;
	FormObject fo;
	static boolean booleanlistThreeWheel[][][][];
	static boolean booleanprelistThreeWheel[][][][];
	
	boolean booleanlistThreeWheelCopy[][][][];
	boolean booleanprelistThreeWheelCopy[][][][];
	int p1, p2, p3, p4;
	Button resetBtn;
	LinearLayout damageditems_header;
	
	EditText otherfield;

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<MainPart> mainPartsList = new ArrayList<MainPart>();

	private static final boolean [] CHECK_STATUS_TYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};
	static final String freqDamagedItemMapper [] = {"1/6", "1/5", "1/38", "1/53", "1/35", "1/117",
			"1/72", "1/73", "1/37", "1/61", "1/113", "1/15", "1/16", "1/14", "1/18", "1/114", "1/118", "1/71",
			"1/74", "1/36", "1/83", "1/78", "1/115", "1/95", "1/94", "1/80", "1/116"};
	static final String listdesc[][][][] = { {// grey
			{
					{ "Three Wheel", "Frequently damaged items" },
					{ "Windscreen", "" },
					{ "Windscreen Frame", "" }, { "Front Fork", "" },
					{ "Front Mud Guard", "" }, { "Horn Guard", "" },
					{ "Head Light LH", "" }, {"Head Light Cover LH", "" },
					{ "Front LH Signal Light", "" },
					{ "Side Mirror LH", "" }, { "Dash Board", "" },
					{ "Centre Post", "" }, { "Front Frame", "" },
					{ "Rear Frame Set", "" },
					{ "Hood Canopy", "" }, { "Rear LH Fender", "" },
					{ "LH Body", "" }, { "Head Light RH", "" },
					{ "Head Light Cover RH", "" },
					{ "Front RH Signal Light", "" }, { "Side Mirror RH", "" },
					{ "RH Door", "" }, { "R/RH Mud Guard", "" },
					{ "RH Body", "" },
					{ "R/RH Tail Light", "" }, { "R/LH Tail Light", "" },
					{ "R/Buffer", "" }, { "Back  Door", "" }

			},
			{
					{ "Three Wheel", "All parts" }, { "Headlight Lh", "" },//0
					{ "Headlight Rh", "" }, { "Headlight cowl", "" },//2
					{ "Signal light Rh", "" }, { "Signal Light Lh", "" },
					{ "Windscreen Frame", "" }, { "Windscreen", "" },//6
					{ "Windscreen Beading", "" }, { "Joint Beading", "" },
					{ "Front Fender", "" }, { "Fork", "" }, { "Pin Set", "" },//11
					{ "Cup Set", "" }, { "Ball Set", "" }, { "Hood Canopy", "" },//14
					{ "Front Frame", "" }, { "Rear Frame Set", "" },
					{ "Rear Rh Fender", "" }, { "Rear LH Fender", "" },//18
					{ "Rear Buffer", "" }, { "Rear Rh Combination Lamp", "" },
					{ "Rear Lh Combination Lamp", "" }, { "Rear Door", "" },//22
					{ "Side Door", "" }, { "Side Plastic LH", "" },
					{ "Side Plastic RH", "" }, { "Kick Pedal", "" },//26
					{ "Cabin Hole", "" }, { "Handle", "" }, { "Swing Arm", "" },
					{ "Engine Housing", "" }, { "Battery", "" }, { "Petrol Tank", "" },//32
					{ "Rear Deck", "" }, { "Cable set", "" }, { "Horn Guard", "" },
					{ "Side Mirror RH", "" }, { "Side Mirror LH", "" },//37
					{ "Front Fork", "" }, { "Front Hub", "" }, { "Front Axle", "" },//40
					{ "Fork Pin", "" }, { "Cup set Upper/Lower", "" },
					{ "Handle Ball", "" }, { "Meter", "" }, { "Meter Cable", "" },//45
					{ "Gear Cable Black", "" }, { "Gear Cable White", "" },
					{ "F shock absorber", "" }, { "Clutch cable", "" },//49
					{ "Chock Cable", "" }, { "Front Rim", "" }, { "Front Tire", "" },
					{ "Front Mud Guard", "" }, { "F/Windscreen", "" },//54
					{ "F/W/LH Plastic", "" }, { "F/W/RH Plastic", "" },
					{ "F/ Windscreen Beading", "" }, { "F/W/B Clip", "" },//58
					{ "RH Mirror", "" }, { "LH Mirror", "" }, { "Dash Board", "" },
					{ "F/Brake Hose", "" }, { "F/Brake Pipe", "" },//63
					{ "Brake Pump", "" }, { "Hood Cloth", "" }, { "Hood Pipe", "" },
					{ "Hood Frame", "" }, { "Front Seat", "" },//68
					{ "Front seat Frame", "" }, { "Head Light", "" },
					{ "Head Light Cover RH", "" }, { "Head Light Cover LH", "" },//72
					{ "Front LH Signal Light", "" }, { "Front RH Signal Light", "" },
					{ "Wiper Blade", "" }, { "Wiper Motor", "" },//76
					{ "R/LH Mud Guard", "" }, { "R/RH Mud Guard", "" },
					{ "R/Seat", "" }, { "R/Buffer", "" }, { "R/Door", "" },//81
					{ "Reverse Gear Cable", "" }, { "RH Door", "" },
					{ "R/RH Shock absorber", "" }, { "R/LH Shock absorber", "" },//85
					{ "R/RH Wheel Rim", "" }, { "R/LH Wheel Rim", "" },
					{ "RH Tire", "" }, { "LH Tire", "" }, { "RH Swing Arm", "" },//90
					{ "LH Swing Arm", "" }, { "Distance Piece", "" },
					{ "Silent Bush Swing Arm", "" }, { "R/LH Tail Light", "" },//94
					{ "R/RH Tail Light", "" }, { "Front Number Plate", "" },
					{ "Rear Number Plate", "" }, { "Silencer", "" },//98
					{ "Accelerator cable", "" }, { "Front wheel bracket", "" },
					{ "Break Oil tank", "" }, { "Hand break cable", "" },//102
					{ "Back rest support", "" }, { "Drive shaft", "" },
					{ "Mudguard beading", "" }, { "Front cabin", "" },//106
					{ "Rear cabin", "" }, { "s/s modifications", "" },
					{ "Sun visors", "" }, { "Ornaments", "" }, { "Arial", "" },//111
					{ "Radio/CD/Cassette player", "" }, {"Centre Post", ""}, {"LH Body", ""},//114
						{"RH Body", ""}, {"Back  Door", ""}, {"Head Light LH", ""}, {"Head Light RH", ""}} } };//118

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		try {
			LOG.debug("ENTRY onCreate");
			super.onCreate(icicle);
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
				booleanprelistThreeWheel = Arrays.copyOf(fo.getBooleanprelistThreeWheel(), fo.getBooleanprelistThreeWheel().length);

				booleanprelistThreeWheelCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanprelistThreeWheelCopy[i][j][k][0] = booleanprelistThreeWheel[i][j][k][0];
							booleanprelistThreeWheelCopy[i][j][k][1] = booleanprelistThreeWheel[i][j][k][1];

							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanprelistThreeWheel[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanprelistThreeWheel[i][j-1][k][0], listdesc[i][j][k+1][1]);
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
				booleanlistThreeWheel = Arrays.copyOf(fo.getBooleanlistThreeWheel(), fo.getBooleanlistThreeWheel().length);

				booleanlistThreeWheelCopy = new boolean[p1][p2][p3][p4];

				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanlistThreeWheelCopy[i][j][k][0] = booleanlistThreeWheel[i][j][k][0];
							booleanlistThreeWheelCopy[i][j][k][1] = booleanlistThreeWheel[i][j][k][1];
							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanlistThreeWheel[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanlistThreeWheel[i][j-1][k][0], listdesc[i][j][k+1][1]);
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
												if(i != 0 && j!=0) {ExpList3_ThreeWheel.booleanprelistThreeWheel[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_ThreeWheel.booleanprelistThreeWheel[0][i-1][j-1][0] = true;}
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
												if(i != 0 && j!=0) {ExpList3_ThreeWheel.booleanlistThreeWheel[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_ThreeWheel.booleanlistThreeWheel[0][i-1][j-1][0] = true;}
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
			LOG.error("onCreate:10320");
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
			LOG.error("editDisable:10322");
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
				fo.setBooleanprelistThreeWheel(booleanprelistThreeWheel);
				fo.setpredamagedItemsOtherField(otherfield.getText().toString().trim());
			} else {
				fo.setBooleanlistThreeWheel(booleanlistThreeWheel);
				fo.setdamagedItemsOtherField(otherfield.getText().toString().trim());
			}
			Application.setFormObjectInstance(fo);

			Intent mIntent = new Intent(ExpList3_ThreeWheel.this, VehicleDetailsActivity.class);
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
				fo.setBooleanprelistThreeWheel(booleanprelistThreeWheelCopy);
			} else {
				fo.setdamagedItemsOtherField(fo.getdamagedItemsOtherField());
				fo.setBooleanlistThreeWheel(booleanlistThreeWheelCopy);
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
								if (booleanprelistThreeWheelCopy[i][j][k][0] != booleanprelistThreeWheel[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanprelistThreeWheelCopy[i][j][k][1] != booleanprelistThreeWheel[i][j][k][1]) {
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
								if (booleanlistThreeWheelCopy[i][j][k][0] != booleanlistThreeWheel[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanlistThreeWheelCopy[i][j][k][1] != booleanlistThreeWheel[i][j][k][1]) {
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
					AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_ThreeWheel.this);
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
					fo.setBooleanlistThreeWheel(a);
					fo.setBooleanprelistThreeWheel(a);
				} else {
					fo.setBooleanprelistThreeWheel(a);
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
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_ThreeWheel.this);
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
		listAdapter = new MyListAdapter(ExpList3_ThreeWheel.this, mainPartsList);
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