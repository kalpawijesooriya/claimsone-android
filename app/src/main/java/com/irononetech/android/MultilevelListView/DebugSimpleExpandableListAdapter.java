package com.irononetech.android.MultilevelListView;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleExpandableListAdapter;
import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.claimsone.R;

public class DebugSimpleExpandableListAdapter extends SimpleExpandableListAdapter {
	int level1Grouprow;
	static int p1 = Application.get4DArrSizeSec1();
	static int p2 = Application.get4DArrSizeSec2();
	static int p3 = Application.get4DArrSizeSec3();
	static GroupClass1 buttonarraylist[][][]= new GroupClass1[p1][p2][p3];
	FormObject formObject;
	
	public DebugSimpleExpandableListAdapter(Context context,int level1GroupRow,
			List<? extends Map<String, ?>> groupData, int groupLayout,
					String[] groupFrom, int[] groupTo,
					List<? extends List<? extends Map<String, ?>>> childData,
							int childLayout, String[] childFrom, int[] childTo
			) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
		this.level1Grouprow = level1GroupRow;

		formObject=  Application.getFormObjectInstance();
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View v = super.getChildView( groupPosition, childPosition, isLastChild, convertView, parent );

		CheckBox cb= (CheckBox) v.findViewById(R.id.checkBox1);

		if(formObject.getisPreSelected()) {   //PRE

			if (formObject.getvehicleType() == 1) {
				cb.setChecked(false);
				cb.setChecked(ExpList3_Bus.booleanprelistBus[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 2) {
				cb.setChecked(ExpList3_Car.booleanprelistCar[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 3) {
				cb.setChecked(ExpList3_Lorry.booleanprelistLorry[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 4) {
				cb.setChecked(ExpList3_Van.booleanprelistVan[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 5) {
				cb.setChecked(ExpList3_ThreeWheel.booleanprelistThreeWheel[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 6) {
				cb.setChecked(ExpList3_Motorcycle.booleanprelistMotorcycle[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 7) {
				cb.setChecked(ExpList3_Tractor4WD.booleanprelistTractor4WD[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 8) {
				cb.setChecked(ExpList3_HandTractor.booleanprelistHandTractor[level1Grouprow][groupPosition][childPosition][0]);
			}
		}
		else {   //NOT PRE

			if (formObject.getvehicleType() == 1) {
				cb.setChecked(false);
				cb.setChecked(ExpList3_Bus.booleanlistBus[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 2) {
				cb.setChecked(ExpList3_Car.booleanlistCar[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 3) {
				cb.setChecked(ExpList3_Lorry.booleanlistLorry[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 4) {
				cb.setChecked(ExpList3_Van.booleanlistVan[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 5) {
				cb.setChecked(ExpList3_ThreeWheel.booleanlistThreeWheel[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 6) {
				cb.setChecked(ExpList3_Motorcycle.booleanlistMotorcycle[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 7) {
				cb.setChecked(ExpList3_Tractor4WD.booleanlistTractor4WD[level1Grouprow][groupPosition][childPosition][0]);
			}
			else if (formObject.getvehicleType() == 8) {
				cb.setChecked(ExpList3_HandTractor.booleanlistHandTractor[level1Grouprow][groupPosition][childPosition][0]);
			}
		}
		//rg.addView(rb,childPosition);
		GroupClass1 gc = new GroupClass1();
		gc.cb = cb;
		gc.level1GroupRow= level1Grouprow;
		gc.grouprow= groupPosition;
		gc.childrow= childPosition;
		//int position =level1Grouprow+(groupPosition) + childPosition;
		buttonarraylist[level1Grouprow][groupPosition][childPosition]= gc;

		return v;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View v = super.getGroupView( groupPosition, isExpanded, convertView, parent );
		return v;
	}
}

class GroupClass1{
	CheckBox cb;
	int level1GroupRow;
	int grouprow;
	int childrow;
}
