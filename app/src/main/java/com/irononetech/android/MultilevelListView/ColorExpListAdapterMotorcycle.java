package com.irononetech.android.MultilevelListView;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import com.irononetech.android.Application.Application;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.claimsone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ColorExpListAdapterMotorcycle extends BaseExpandableListAdapter {

	Logger LOG = LoggerFactory.getLogger(ColorExpListAdapterMotorcycle.class);

	private Context context;
	private String listdesc[][][][];
	private LayoutInflater inflater;
	private ExpandableListView topExpList;
	private DebugExpandableListView listViewCache[];
	private static final String KEY_COLORNAME = "colorName";
	private static final String KEY_SHADENAME = "shadeName";
	private static final String KEY_RGB = "rgb";
	//private static final String LOG_TAG = "ColorExpListAdapter ";

	public ColorExpListAdapterMotorcycle(Context context,
			ExpandableListView topExpList, String listdesc[][][][]) {
		try{
			this.context = context;
			this.topExpList = topExpList;
			this.listdesc = listdesc;
			inflater = LayoutInflater.from(context);
			listViewCache = new DebugExpandableListView[listdesc.length];
		}
		catch (Exception e) {
			LOG.error("ColorExpListAdapterMotorcycle:10219");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public Object getChild(int groupPosition, int childPosition) {

		return listdesc[groupPosition][childPosition];
	}


	public long getChildId(int groupPosition, int childPosition) {
		return (long) (groupPosition * 1024 + childPosition);
	}


	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		try{
			View v = null;
			if (listViewCache[groupPosition] != null)
				v = listViewCache[groupPosition];
			else {
				DebugExpandableListView dev = new DebugExpandableListView(context);
				dev.setRows(calculateRowCount(groupPosition, null));
				dev.setAdapter(new DebugSimpleExpandableListAdapter(context,
						groupPosition, createGroupList(groupPosition), // groupData
						// describes
						// the
						// first-level
						// entries
						R.layout.multilevel_expandable_list_group_2_row, // Layout
						// for
						// the
						// first-level
						// entries
						new String[] { KEY_COLORNAME }, // Key in the groupData maps
						// to display
						new int[] { R.id.group2name }, // Data under "colorName" key
						// goes into this TextView
						createChildList(groupPosition), // childData describes
						// second-level entries
						R.layout.multilevel_expandable_list_child3_row, // Layout
						// for
						// second-level
						// entries
						new String[] { KEY_SHADENAME, KEY_RGB }, // Keys in
						// childData
						// maps to
						// display
						new int[] { R.id.childname, R.id.rgb } // Data under the
				// keys above go
				// into these
				// TextViews
						));
				dev.setOnGroupClickListener(new Level2GroupExpandListener(
						groupPosition));
				dev.setOnChildClickListener(new Level2ChildExpandListener(
						groupPosition));
				dev.setIndicatorBounds(30, 80);
				listViewCache[groupPosition] = dev;
				v = dev;
			}
			return v;
		}
		catch (Exception e) {
			LOG.error("getChildView:10220");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}


	public int getChildrenCount(int groupPosition) {
		return 1;
	}


	public Object getGroup(int groupPosition) {
		return listdesc[groupPosition][0][0][0];
	}


	public int getGroupCount() {
		return listdesc.length;
	}


	public long getGroupId(int groupPosition) {
		return (long) (groupPosition * 1024);
	}


	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		try{
			View v = null;
			if (convertView != null)
				v = convertView;
			else
				v = inflater.inflate(R.layout.multilevel_expandable_list_group_row, parent, false);
			String gt = (String) getGroup(groupPosition);
			TextView colorGroup = (TextView) v.findViewById(R.id.groupname);
			if (gt != null)
				colorGroup.setText(gt);
			return v;
		}
		catch (Exception e) {
			LOG.error("getGroupView:10229");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}


	public boolean hasStableIds() {
		return true;
	}


	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void onGroupCollapsed(int groupPosition) {
	}

	public void onGroupExpanded(int groupPosition) {
	}

	/**
	 * Creates a level2 group list out of the listdesc array according to the
	 * structure required by SimpleExpandableListAdapter. The resulting List
	 * contains Maps. Each Map contains one entry with key "colorName" and value
	 * of an entry in the listdesc array.
	 * 
	 * @param level1
	 *            Index of the level1 group whose level2 subgroups are listed.
	 */
	private List createGroupList(int level1) {
		ArrayList result = new ArrayList();
		try {
			for (int i = 0; i < listdesc[level1].length; ++i) {
				HashMap m = new HashMap();
				m.put(KEY_COLORNAME, listdesc[level1][i][0][1]);
				result.add(m);
			}
		} catch (Exception e) {
			LOG.error("getChildView:10352");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return (List) result;
	}

	/**
	 * Creates the child list out of the listdesc array according to the
	 * structure required by SimpleExpandableListAdapter. The resulting List
	 * contains one list for each group. Each such second-level group contains
	 * Maps. Each such Map contains two keys: "shadeName" is the name of the
	 * shade and "rgb" is the RGB value for the shade.
	 * 
	 * @param level1
	 *            Index of the level1 group whose level2 subgroups are included
	 *            in the child list.
	 */
	private List createChildList(int level1) {
		ArrayList result = new ArrayList();
		try {
			for (int i = 0; i < listdesc[level1].length; ++i) {
				// Second-level lists
				ArrayList secList = new ArrayList();
				for (int n = 1; n < listdesc[level1][i].length; ++n) {
					HashMap child = new HashMap();
					child.put(KEY_SHADENAME, listdesc[level1][i][n][0]);
					child.put(KEY_RGB, listdesc[level1][i][n][1]);
					secList.add(child);
				}
				result.add(secList);
			}
		} catch (Exception e) {
			LOG.error("createChildList:10361");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return result;
	}

	// Calculates the row count for a level1 expandable list adapter. Each
	// level2 group counts 1 row (group row) plus any child row that
	// belongs to the group
	private int calculateRowCount(int level1, ExpandableListView level2view) {
		int level2GroupCount = listdesc[level1].length;
		int rowCtr = 0;
		for (int i = 0; i < level2GroupCount; ++i) {
			++rowCtr; // for the group row
			if ((level2view != null) && (level2view.isGroupExpanded(i)))
				rowCtr += listdesc[level1][i].length - 1; // then add the
			// children too
			// (minus the group
			// descriptor)
		}
		return rowCtr;
	}

	class Level2GroupExpandListener implements
	ExpandableListView.OnGroupClickListener {
		public Level2GroupExpandListener(int level1GroupPosition) {
			this.level1GroupPosition = level1GroupPosition;
		}

		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			if (parent.isGroupExpanded(groupPosition))
				parent.collapseGroup(groupPosition);
			else
				parent.expandGroup(groupPosition);
			if (parent instanceof DebugExpandableListView) {
				DebugExpandableListView dev = (DebugExpandableListView) parent;
				dev.setRows(calculateRowCount(level1GroupPosition, parent));
			}
			//Log.d(LOG_TAG, "onGroupClick");
			topExpList.requestLayout();
			return true;
		}

		private int level1GroupPosition;
	}

	class Level2ChildExpandListener implements
	ExpandableListView.OnChildClickListener {
		public Level2ChildExpandListener(int level1GroupPosition) {
			this.level1GroupPosition = level1GroupPosition;
		}

		/*
		 * public boolean onGroupClick(ExpandableListView parent, View v, int
		 * groupPosition, long id) { if( parent.isGroupExpanded( groupPosition )
		 * ) parent.collapseGroup( groupPosition ); else parent.expandGroup(
		 * groupPosition ); if( parent instanceof DebugExpandableListView ) {
		 * DebugExpandableListView dev = (DebugExpandableListView)parent;
		 * dev.setRows( calculateRowCount( level1GroupPosition, parent ) ); }
		 * Log.d( LOG_TAG, "onGroupClick" ); topExpList.requestLayout(); return
		 * true; }
		 */

		private int level1GroupPosition;

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// int position = level1GroupPosition + groupPosition +
			// childPosition;
			try{
				FormObject formObject = Application.getFormObjectInstance();
				if (!formObject.isEditable()) {

				} else {
					GroupClass1 gc = DebugSimpleExpandableListAdapter.buttonarraylist[level1GroupPosition][groupPosition][childPosition];
					CheckBox cb = gc.cb;
					if(formObject.getisPreSelected()){  //PRE
						if (cb.isChecked()) {
							cb.setChecked(false);
							ExpList3_Motorcycle.booleanprelistMotorcycle[level1GroupPosition][groupPosition][childPosition][0] = false;
							//LogFile.d("\n***", "11111");
						} else {
							cb.setChecked(true);
							ExpList3_Motorcycle.booleanprelistMotorcycle[level1GroupPosition][groupPosition][childPosition][0] = true;
							//LogFile.d("\n***", "22222");
						}
					}
					else {
						if (cb.isChecked()) {
							cb.setChecked(false);
							ExpList3_Motorcycle.booleanlistMotorcycle[level1GroupPosition][groupPosition][childPosition][0] = false;
							//LogFile.d("\n***", "33333");
						} else {
							cb.setChecked(true);
							ExpList3_Motorcycle.booleanlistMotorcycle[level1GroupPosition][groupPosition][childPosition][0] = true;
							//LogFile.d("\n***", "44444");
						}
					}
				}
				return false;
			}
			catch (Exception e) {
				LOG.error("onChildClick:10230");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return false;
			}
		}
	}
}
