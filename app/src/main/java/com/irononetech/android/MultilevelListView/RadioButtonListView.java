package com.irononetech.android.MultilevelListView;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.view.VehicleDetailsActivity;
import com.irononetech.android.claimsone.R;

public class RadioButtonListView extends Activity {

	Logger LOG = LoggerFactory.getLogger(RadioButtonListView.class);

	GroupClass[] buttonarraylist;
	boolean[] checkStatus;
	Button backCancelBtn;
	ExpandableListView expandableListViewOthers;
	ExpandableListView expandableListViewMain;
	final int noOfSubItems = 4;
	int vehicleType;
	
	boolean[] oldList;
	
//	static final String colors[] = { "F/R", "F/L", "R/R/In", "R/L/In", "R/L/Out", "R/R/Out" }; // Thisaru Guruge | 26/01/2016 | Edited Tyre types "R/R/L, R/R/R, R/L/R, R/L/L" to "R/R/in, R/R/out, R/L/in, R/L/out" as they required to change it to reduce confusions
	String colors[];
	String others[];

	static final String shades[][] = {
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" },
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" },
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" },
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" },
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" },
			// Shades of
			{ "Good", "", "Fair", "", "Bold", "", "Not Applicable", "" } };

	FormObject formObject;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		try {
			LOG.debug("ENTRY onCreate");
			super.onCreate(icicle);
			setContentView(R.layout.radiobutton_main);
			buttonarraylist = new GroupClass[30];

			formObject = Application.getFormObjectInstance();

			checkStatus = formObject.getselectTirecondition();
			oldList = checkStatus.clone();
			TextView tvOthers = findViewById(R.id.tvOthers);

            vehicleType = formObject.getvehicleType();
            if(vehicleType == 1 || vehicleType == 3){ // six wheels
            	String colors2[] = { "F/R", "F/L", "R/R/In", "R/L/In", "R/L/Out", "R/R/Out" };
				colors = colors2.clone();
			} else if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){ // four wheels
				tvOthers.setVisibility(View.VISIBLE);
            	String colors2[] = { "F/R", "F/L", "R/R", "R/L" };
            	String others2[] = {"R/R/Out", "R/L/Out"};
				colors = colors2.clone();
				others = others2.clone();
			}else if (vehicleType == 5){//three wheeler
				tvOthers.setVisibility(View.VISIBLE);
				String colors2 [] = { "F", "R/R", "R/L" };
				String others2[] = {"F/L", "R/R/Out", "R/L/Out"};
				colors = colors2.clone();
				others = others2.clone();
			}else if (vehicleType == 6 ){ //motor cycle
				tvOthers.setVisibility(View.VISIBLE);
				String  colors2[] = { "F", "R"};
				String others2[] = {"F/L", "R/L/In", "R/R/Out", "R/L/Out"};
				colors = colors2.clone();
				others = others2.clone();
			}else if (vehicleType == 8) { //hand tractor
				tvOthers.setVisibility(View.VISIBLE);
				String colors2[] = {"F/R", "F/L"};
				String others2[] = {"R/R/In", "R/L/In", "R/R/Out", "R/L/Out"};
				colors = colors2.clone();
				others = others2.clone();
			}else{
				String  colors2[] = { "F/R", "F/L", "R/R/In", "R/L/In", "R/L/Out", "R/R/Out" };
				colors = colors2.clone();
			}

			backCancelBtn = (Button) findViewById(R.id.button2);
			
			/*if(formObject.getisSEARCH()){
				backCancelBtn.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.back_button_xml));
			}else{
				backCancelBtn.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.cancel_button_xml));
			}*/

			expandableListViewMain = (ExpandableListView) findViewById(R.id.expandableListViewMain);
			HelpSimpleExpandableListAdapter expAdapter = new HelpSimpleExpandableListAdapter(
					this,
					createGroupList(), 		// groupData describes the first-level entries
					R.layout.radiobutton_group_row, // Layout for the first-level entries
					new String[] { "colorName" }, 	// Key in the groupData maps to display
					new int[] { R.id.childname }, 	// Data under "colorName" key goes into this TextView
					createChildList(), 				// childData describes second-level entries
					R.layout.radiobutton_child_row, // Layout for second-level entries
					new String[] { "shadeName", "rgb" }, 	// Keys in childData maps to display
					new int[] { R.id.childname, R.id.rgb } 	// Data under the keys above go into these TextViews
			);
			expandableListViewMain.setAdapter(expAdapter);

            expandableListViewMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {
                    try {
                        if (formObject.isEditable()) {
                            int position = noOfSubItems * (groupPosition) + childPosition;
                            vehicleType = formObject.getvehicleType();

                            GroupClass gc = buttonarraylist[noOfSubItems * (groupPosition)];
                            GroupClass gc1 = buttonarraylist[noOfSubItems * (groupPosition) + 1];
                            GroupClass gc2 = buttonarraylist[noOfSubItems * (groupPosition) + 2];
                            GroupClass gc3 = buttonarraylist[noOfSubItems * (groupPosition) + 3];

                            RadioButton rb = gc.rb;
                            RadioButton rb1 = gc1.rb;
                            RadioButton rb2 = gc2.rb;
                            RadioButton rb3 = gc3.rb;

                            if (position == noOfSubItems * (groupPosition)) {
                                rb.setChecked(true);
                                rb1.setChecked(false);
                                rb2.setChecked(false);
                                rb3.setChecked(false);

                                if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
                                    position = position + 4;
                                }else if (vehicleType == 6 && groupPosition == 1 ){ //motor cycle
                                    position = position + 4;
                                }else{
                                    //nothing as it is
                                }

                                checkStatus[position] = true;
                                checkStatus[position + 1] = false;
                                checkStatus[position + 2] = false;
                                checkStatus[position + 3] = false;
                            }
                            if (position == noOfSubItems * (groupPosition) + 1) {
                                rb.setChecked(false);
                                rb1.setChecked(true);
                                rb2.setChecked(false);
                                rb3.setChecked(false);

                                if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
                                    position = position + 4;
                                }else if (vehicleType == 6 && groupPosition == 1){ //motor cycle
                                    position = position + 4;
                                }else{
                                    //nothing as it is
                                }

                                checkStatus[position] = true;
                                checkStatus[position - 1] = false;
                                checkStatus[position + 1] = false;
                                checkStatus[position + 2] = false;
                            }
                            if (position == noOfSubItems * (groupPosition) + 2) {
                                rb.setChecked(false);
                                rb1.setChecked(false);
                                rb2.setChecked(true);
                                rb3.setChecked(false);

                                if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
                                    position = position + 4;
                                }else if (vehicleType == 6 && groupPosition == 1){ //motor cycle
                                    position = position + 4;
                                }else{
                                    //nothing as it is
                                }

                                checkStatus[position] = true;
                                checkStatus[position - 1] = false;
                                checkStatus[position - 2] = false;
                                checkStatus[position + 1] = false;
                            }
                            if (position == noOfSubItems * (groupPosition) + 3) {
                                rb.setChecked(false);
                                rb1.setChecked(false);
                                rb2.setChecked(false);
                                rb3.setChecked(true);

                                if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
                                    position = position + 4;
                                }else if (vehicleType == 6 && groupPosition == 1){ //motor cycle
                                    position = position + 4;
                                }else{
                                    //nothing as it is
                                }

                                checkStatus[position] = true;
                                checkStatus[position - 1] = false;
                                checkStatus[position - 2] = false;
                                checkStatus[position - 3] = false;
                            }
                        }
                        return false;
                    } catch (Exception e) {
                        LOG.error("onChildClick:10336");
                        if(e != null){
                            LOG.error("Message: " + e.getMessage());
                            LOG.error("StackTrace: " + e.getStackTrace());
                        }
                        return false;
                    }
                }
            });


			expandableListViewMain.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
					setListViewHeight(parent, groupPosition);
					return false;
				}
			});

			expandableListViewOthers = (ExpandableListView) findViewById(R.id.expandableListViewOthers);
			OthersSimpleExpandableListAdapter expAdapterOthers = new OthersSimpleExpandableListAdapter(
					this,
					createOthersGroupList(), 		// groupData describes the first-level entries
					R.layout.radiobutton_group_row, // Layout for the first-level entries
					new String[] { "colorName" }, 	// Key in the groupData maps to display
					new int[] { R.id.childname }, 	// Data under "colorName" key goes into this TextView
					createChildList(), 				// childData describes second-level entries
					R.layout.radiobutton_child_row, // Layout for second-level entries
					new String[] { "shadeName", "rgb" }, 	// Keys in childData maps to display
					new int[] { R.id.childname, R.id.rgb } 	// Data under the keys above go into these TextViews
			);
			expandableListViewOthers.setAdapter(expAdapterOthers);

			expandableListViewOthers.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView expandableListView, View v, int groupPosition, int childPosition, long l) {
					try {
						if (formObject.isEditable()) {
							int position = noOfSubItems * (groupPosition) + childPosition;
							vehicleType = formObject.getvehicleType();

							GroupClass gc = buttonarraylist[noOfSubItems * (groupPosition)];
							GroupClass gc1 = buttonarraylist[noOfSubItems * (groupPosition) + 1];
							GroupClass gc2 = buttonarraylist[noOfSubItems * (groupPosition) + 2];
							GroupClass gc3 = buttonarraylist[noOfSubItems * (groupPosition) + 3];

							RadioButton rb = gc.rb;
							RadioButton rb1 = gc1.rb;
							RadioButton rb2 = gc2.rb;
							RadioButton rb3 = gc3.rb;

							if (position == noOfSubItems * (groupPosition)) {
								rb.setChecked(true);
								rb1.setChecked(false);
								rb2.setChecked(false);
								rb3.setChecked(false);

								if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){//
									position = position + 16;
								}else if ((vehicleType == 5 || vehicleType == 6) && groupPosition == 0 ){ //Three wheeler or Motor cycle
									position = position + 4;
								}else if (vehicleType == 5){ //Three wheeler
									position = position + 12;
								}else if (vehicleType == 6 || vehicleType == 8){ //Motor Cycle or Hand Tractor
									position = position + 8;
								}else{
									//nothing as it is
								}

								checkStatus[position] = true;
								checkStatus[position + 1] = false;
								checkStatus[position + 2] = false;
								checkStatus[position + 3] = false;
							}
							if (position == noOfSubItems * (groupPosition) + 1) {
								rb.setChecked(false);
								rb1.setChecked(true);
								rb2.setChecked(false);
								rb3.setChecked(false);

								if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){//
									position = position + 16;
								}else if ((vehicleType == 5 || vehicleType == 6) && groupPosition == 0 ){ //Three wheeler or Motor cycle
									position = position + 4;
								}else if (vehicleType == 5){ //Three wheeler
									position = position + 12;
								}else if (vehicleType == 6 || vehicleType == 8){ //Motor Cycle or Hand Tractor
									position = position + 8;
								}else{
									//nothing as it is
								}

								checkStatus[position] = true;
								checkStatus[position - 1] = false;
								checkStatus[position + 1] = false;
								checkStatus[position + 2] = false;
							}
							if (position == noOfSubItems * (groupPosition) + 2) {
								rb.setChecked(false);
								rb1.setChecked(false);
								rb2.setChecked(true);
								rb3.setChecked(false);

								if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){//
									position = position + 16;
								}else if ((vehicleType == 5 || vehicleType == 6) && groupPosition == 0 ){ //Three wheeler or Motor cycle
									position = position + 4;
								}else if (vehicleType == 5){ //Three wheeler
									position = position + 12;
								}else if (vehicleType == 6 || vehicleType == 8){ //Motor Cycle or Hand Tractor
									position = position + 8;
								}else{
									//nothing as it is
								}

								checkStatus[position] = true;
								checkStatus[position - 1] = false;
								checkStatus[position - 2] = false;
								checkStatus[position + 1] = false;
							}
							if (position == noOfSubItems * (groupPosition) + 3) {
								rb.setChecked(false);
								rb1.setChecked(false);
								rb2.setChecked(false);
								rb3.setChecked(true);

								if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){//
									position = position + 16;
								}else if ((vehicleType == 5 || vehicleType == 6) && groupPosition == 0 ){ //Three wheeler or Motor cycle
									position = position + 4;
								}else if (vehicleType == 5){ //Three wheeler
									position = position + 12;
								}else if (vehicleType == 6 || vehicleType == 8){ //Motor Cycle or Hand Tractor
									position = position + 8;
								}else{
									//nothing as it is
								}

								checkStatus[position] = true;
								checkStatus[position - 1] = false;
								checkStatus[position - 2] = false;
								checkStatus[position - 3] = false;
							}
						}
						return false;
					} catch (Exception e) {
						LOG.error("onChildClick:10336");
						if(e != null){
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}
						return false;
					}
				}
			});

			expandableListViewOthers.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
					setListViewHeight(parent, groupPosition);
					return false;
				}
			});

			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			 LOG.error("onCreate:10335");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private void setListViewHeight(ExpandableListView listView,
								   int group) {
		ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
		int totalHeight = 60;
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
				View.MeasureSpec.EXACTLY);
		for (int i = 0; i < listAdapter.getGroupCount(); i++) {
			View groupItem = listAdapter.getGroupView(i, false, null, listView);
			groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

			totalHeight += groupItem.getMeasuredHeight();

			if (((listView.isGroupExpanded(i)) && (i != group))
					|| ((!listView.isGroupExpanded(i)) && (i == group))) {
				for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
					View listItem = listAdapter.getChildView(i, j, false, null,
							listView);
					listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

					totalHeight += listItem.getMeasuredHeight();

				}
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
		if (height < 10)
			height = 200;
		params.height = height;
		listView.setLayoutParams(params);
		listView.requestLayout();

	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void onResume() {
		LOG.debug("ENTRY onResume");
		super.onResume();
		LOG.debug("SUCCESS onResume");
	}

	public void onContentChanged() {
		super.onContentChanged();
		
	}

	/**
	 * Creates the group list out of the colors[] array according to the
	 * structure required by SimpleExpandableListAdapter. The resulting List
	 * contains Maps. Each Map contains one entry with key "colorName" and value
	 * of an entry in the colors[] array.
	 */
	private List createGroupList() {
		ArrayList result = new ArrayList();
		for (int i = 0; i < colors.length; ++i) {
			HashMap m = new HashMap();
			m.put("colorName", colors[i]);
			result.add(m);
		}
		return (List) result;
	}

	private List createOthersGroupList(){
		ArrayList result = new ArrayList();
		for(int i = 0; i<others.length; ++i){
			HashMap m = new HashMap();
			m.put("colorName", others[i]);
			result.add(m);
		}
		return result;
	}

	/**
	 * Creates the child list out of the shades[] array according to the
	 * structure required by SimpleExpandableListAdapter. The resulting List
	 * contains one list for each group. Each such second-level group contains
	 * Maps. Each such Map contains two keys: "shadeName" is the name of the
	 * shade and "rgb" is the RGB value for the shade.
	 */
	private List createChildList() {
		ArrayList result = new ArrayList();
		for (int i = 0; i < shades.length; ++i) {
			// Second-level lists
			ArrayList secList = new ArrayList();
			for (int n = 0; n < shades[i].length; n += 2) {
				HashMap child = new HashMap();
				child.put("shadeName", shades[i][n]);
				child.put("rgb", shades[i][n + 1]);
				secList.add(child);
			}
			result.add(secList);
		}
		return result;
	}

	
	public void radiobutton_back_button(View v) {
		try {
			if (!formObject.getisSEARCH()) {
				/*boolean[] a = new boolean[Application.getTyreConditionArrSize()];
				for (int i = 0; i < a.length; i++) {
					if (i % 4 == 0) {
						a[i + 3] = true;
					}
				}*/

				boolean isSame = Arrays.equals(oldList, checkStatus);
				
				if (!isSame) {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(RadioButtonListView.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setCancelable(false);
					alertbox.setMessage("Do you want to save changes to 'Tyre Condition'?");
					alertbox.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							formObject.setselectTirecondition(checkStatus);
							removeDialog(arg1);
							finish();
						}});
					alertbox.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							formObject.setselectTirecondition(oldList);
							removeDialog(arg1);
							finish();
						}});
					alertbox.show();
					return;
				}
			}
			finish();
		} catch (Exception e) {
			 LOG.error("radiobutton_back_button:10337");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void radiobutton_ok_button(View v) {
		try {
			// int countforTruearray=0;
			/*
			 * for(int i=0;i<18;i++){ if(checkStatus[i]){
			 * trueList[countforTruearray]= (i%3)+1; countforTruearray++; } }
			 */
			formObject.setselectTirecondition(checkStatus);

			Intent mIntent = new Intent(RadioButtonListView.this,
					VehicleDetailsActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			finish();
		} catch (Exception e) {
			 LOG.error("radiobutton_ok_button:10338");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public class HelpSimpleExpandableListAdapter extends SimpleExpandableListAdapter {
		Context c;
		int childlayout;
		RadioGroup rg;

		public HelpSimpleExpandableListAdapter(Context context,
				List<? extends Map<String, ?>> groupData, int groupLayout,
				String[] groupFrom, int[] groupTo,
				List<? extends List<? extends Map<String, ?>>> childData,
				int childLayout, String[] childFrom, int[] childTo) {
			super(context, groupData, groupLayout, groupFrom, groupTo,
					childData, childLayout, childFrom, childTo);
			this.c = context;
			childlayout = childLayout;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = super.getChildView(groupPosition, childPosition,
					isLastChild, convertView, parent);
			/*
			 * View view=null; if(convertView==null){ view=
			 * LayoutInflater.from(c).inflate(childlayout, parent); }
			 */

			RadioButton rb = (RadioButton) view.findViewById(R.id.radioButton1);

			if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +4]);
			}else if (vehicleType == 6 && groupPosition == 1){ //motor cycle
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +4]);
			}else{
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition]);
			}


			// rg.addView(rb,childPosition);
			GroupClass gc = new GroupClass();
			gc.rb = rb;
			gc.grouprow = groupPosition;
			gc.childrow = childPosition;
			int position = noOfSubItems * (groupPosition) + childPosition;
			buttonarraylist[position] = gc;

			// Log.d( LOG_TAG,
			// "getChildView: groupPosition: "+groupPosition+"; childPosition: "+childPosition+"; v: "+v
			// );
			return view;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View v = super.getGroupView(groupPosition, isExpanded, convertView,
					parent);
			//Log.d(LOG_TAG, "getGroupView: groupPosition: " + groupPosition
					//+ "; isExpanded: " + isExpanded + "; v: " + v);
			return v;
		}

		//private static final String LOG_TAG = "DebugSimpleExpandableListAdapter";
	}

	public class OthersSimpleExpandableListAdapter extends SimpleExpandableListAdapter {
		Context c;
		int childlayout;
		RadioGroup rg;

		public OthersSimpleExpandableListAdapter(Context context,
											   List<? extends Map<String, ?>> groupData, int groupLayout,
											   String[] groupFrom, int[] groupTo,
											   List<? extends List<? extends Map<String, ?>>> childData,
											   int childLayout, String[] childFrom, int[] childTo) {
			super(context, groupData, groupLayout, groupFrom, groupTo,
					childData, childLayout, childFrom, childTo);
			this.c = context;
			childlayout = childLayout;
		}

		public View getChildView(int groupPosition, int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {
			View view = super.getChildView(groupPosition, childPosition,
					isLastChild, convertView, parent);
			/*
			 * View view=null; if(convertView==null){ view=
			 * LayoutInflater.from(c).inflate(childlayout, parent); }
			 */

			RadioButton rb = (RadioButton) view.findViewById(R.id.radioButton1);

			if (vehicleType == 5 && (groupPosition == 1 || groupPosition == 2)){//three wheeler
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +4]);
			}else if (vehicleType == 6 && groupPosition == 1){ //motor cycle
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +4]);
			}else{
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition]);
			}


			if (vehicleType == 2 || vehicleType == 4 || vehicleType == 7){//
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +16]);
			}else if ((vehicleType == 5 || vehicleType == 6) && groupPosition == 0 ){ //Three wheeler or Motor cycle
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +4]);
			}else if (vehicleType == 5){ //Three wheeler
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +12]);
			}else if (vehicleType == 6 || vehicleType == 8){ //Motor Cycle or Hand Tractor
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition +8]);
			}else{
				rb.setChecked(checkStatus[noOfSubItems * (groupPosition) + childPosition]);
			}


			// rg.addView(rb,childPosition);
			GroupClass gc = new GroupClass();
			gc.rb = rb;
			gc.grouprow = groupPosition;
			gc.childrow = childPosition;
			int position = noOfSubItems * (groupPosition) + childPosition;
			buttonarraylist[position] = gc;

			// Log.d( LOG_TAG,
			// "getChildView: groupPosition: "+groupPosition+"; childPosition: "+childPosition+"; v: "+v
			// );
			return view;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			View v = super.getGroupView(groupPosition, isExpanded, convertView,
					parent);
			//Log.d(LOG_TAG, "getGroupView: groupPosition: " + groupPosition
			//+ "; isExpanded: " + isExpanded + "; v: " + v);
			return v;
		}

		//private static final String LOG_TAG = "DebugSimpleExpandableListAdapter";
	}
}

class GroupClass {
	RadioButton rb;
	int grouprow;
	int childrow;
}
