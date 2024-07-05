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

public class ExpList3_Tractor4WD extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {
	Logger LOG = LoggerFactory.getLogger(ExpList3_Tractor4WD.class);
	
	private ColorExpListAdapterTractor4WD colorExpListAdapter;
	FormObject fo;
	static boolean booleanlistTractor4WD[][][][];
	static boolean booleanprelistTractor4WD[][][][];
	
	boolean booleanlistTractor4WDCopy[][][][];
	boolean booleanprelistTractor4WDCopy[][][][];
	int p1, p2, p3, p4;
	Button resetBtn;
	LinearLayout damageditems_header;
	
	EditText otherfield;

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<MainPart> mainPartsList = new ArrayList<MainPart>();

	private static final boolean [] CHECK_STATUS_TYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};
	static final String freqDamagedItemMapper [] = {"1/0", "1/1", "1/3", "1/22", "1/23"};
	static final String listdesc[][][][] = { {
			{
					{ "Tractor 4WD",  "Frequently damaged items" },
					{ "Head Light Lh & Rh", "" },
					{ "Front Signal Light", "" },
					{ "Bonnet", "" },
					{ "Rear LH Wheel", "" }, { "Rear RH Wheel", "" }
			},
		{
			{ "Tractor 4WD", "All parts" }, { "Head Light Lh & Rh", "" },
			{ "Front Signal Light", "" }, { "Front Shell", "" },
			{ "Bonnet", "" }, { "Silancer", "" }, { "FR RH Axial", "" },
			{ "FR LH Axial", "" }, { "Center Bar", "" }, { "RH FR Wheel", "" },
			{ "LH FR Wheel", "" }, { "Dashboard", "" }, { "Meter Set", "" },
			{ "Starting Switch", "" }, { "Tie Rods", "" }, { "Radiator", "" },
			{ "Water Pump", "" }, { "Radiator Fan", "" },
			{ "Driver Seat", "" }, { "Rear RH Fender", "" },
			{ "Rear LH Fender", "" }, { "Rear RH & LH Combination Lamps", "" },
			{ "Hitch", "" }, { "Rear LH Wheel", "" }, { "Rear RH Wheel", "" }, }, } };

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
				booleanprelistTractor4WD = Arrays.copyOf(fo.getBooleanprelistTractor4WD(), fo.getBooleanprelistTractor4WD().length);

				booleanprelistTractor4WDCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanprelistTractor4WDCopy[i][j][k][0] = booleanprelistTractor4WD[i][j][k][0];
							booleanprelistTractor4WDCopy[i][j][k][1] = booleanprelistTractor4WD[i][j][k][1];

							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanprelistTractor4WD[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanprelistTractor4WD[i][j-1][k][0], listdesc[i][j][k+1][1]);
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
				booleanlistTractor4WD = Arrays.copyOf(fo.getBooleanlistTractor4WD(), fo.getBooleanlistTractor4WD().length);

				booleanlistTractor4WDCopy = new boolean[p1][p2][p3][p4];

				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanlistTractor4WDCopy[i][j][k][0] = booleanlistTractor4WD[i][j][k][0];
							booleanlistTractor4WDCopy[i][j][k][1] = booleanlistTractor4WD[i][j][k][1];
							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanlistTractor4WD[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanlistTractor4WD[i][j-1][k][0], listdesc[i][j][k+1][1]);
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
												if(i != 0 && j!=0) {ExpList3_Tractor4WD.booleanprelistTractor4WD[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_Tractor4WD.booleanprelistTractor4WD[0][i-1][j-1][0] = true;}
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
												if(i != 0 && j!=0) {ExpList3_Tractor4WD.booleanlistTractor4WD[0][i-1][j-1][0] = false;}
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
												if(i != 0 && j!=0) {ExpList3_Tractor4WD.booleanlistTractor4WD[0][i-1][j-1][0] = true;}
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
				fo.setBooleanprelistTractor4WD(booleanprelistTractor4WD);
				fo.setpredamagedItemsOtherField(otherfield.getText().toString().trim());
			} else {
				fo.setBooleanlistTractor4WD(booleanlistTractor4WD);
				fo.setdamagedItemsOtherField(otherfield.getText().toString().trim());
			}
			Application.setFormObjectInstance(fo);

			Intent mIntent = new Intent(ExpList3_Tractor4WD.this, VehicleDetailsActivity.class);
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
				fo.setBooleanprelistTractor4WD(booleanprelistTractor4WDCopy);
			} else {
				fo.setdamagedItemsOtherField(fo.getdamagedItemsOtherField());
				fo.setBooleanlistTractor4WD(booleanlistTractor4WDCopy);
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
								if (booleanprelistTractor4WDCopy[i][j][k][0] != booleanprelistTractor4WD[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanprelistTractor4WDCopy[i][j][k][1] != booleanprelistTractor4WD[i][j][k][1]) {
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
								if (booleanlistTractor4WDCopy[i][j][k][0] != booleanlistTractor4WD[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanlistTractor4WDCopy[i][j][k][1] != booleanlistTractor4WD[i][j][k][1]) {
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
					AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Tractor4WD.this);
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
					fo.setBooleanlistTractor4WD(a);
					fo.setBooleanprelistTractor4WD(a);
				} else {
					fo.setBooleanprelistTractor4WD(a);
					fo.setpredamagedItemsOtherField(otherfield.getText()
							.toString().trim());
				}*/
			}
			finish();
		} catch (Exception e) {
			LOG.error("multilevel_back_button:11326");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}
	
	public void on_click_reset_button(View v) {
		try {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Tractor4WD.this);
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
		listAdapter = new MyListAdapter(ExpList3_Tractor4WD.this, mainPartsList);
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