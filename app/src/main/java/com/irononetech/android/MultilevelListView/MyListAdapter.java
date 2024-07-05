package com.irononetech.android.MultilevelListView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.irononetech.android.claimsone.R;

import java.util.ArrayList;
/**
 * @author Vimosanan
 *
 */

public class MyListAdapter extends BaseExpandableListAdapter  {
    private Context context;
    private ArrayList<MainPart> mainPartsList;
    private ArrayList<MainPart> mainPartsOriginalList;


    public MyListAdapter(Context context, ArrayList<MainPart> nMainPartsList) {
        this.context = context;
        this.mainPartsList = new ArrayList<MainPart>();
        this.mainPartsList.addAll(nMainPartsList);
        this.mainPartsOriginalList = new ArrayList<MainPart>();
        this.mainPartsOriginalList.addAll(nMainPartsList);
    }

    @Override
    public int getGroupCount() {
        return mainPartsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<SubPart> subPartsList = mainPartsList.get(groupPosition).getSubParts();
        return subPartsList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainPartsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<SubPart> subPartsList = mainPartsList.get(groupPosition).getSubParts();
        return subPartsList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        MainPart mainPart = (MainPart) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.multilevel_expandable_list_group_2_row, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.group2name);
        heading.setText(mainPart.getName().trim());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        SubPart subPart = (SubPart) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.multilevel_expandable_list_child3_row, null);
        }

        TextView childName = (TextView) view.findViewById(R.id.childname);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox1);

        if(subPart.getType() == "FRONT"){
            childName.setTextColor(context.getResources().getColor(R.color.crimson));
        }else if(subPart.getType() == "REAR"){
            childName.setTextColor(context.getResources().getColor(R.color.blue_green));
        }else if(subPart.getType() == "RIGHT"){
            childName.setTextColor(context.getResources().getColor(R.color.orange));
        }else if(subPart.getType() == "LEFT"){
            childName.setTextColor(context.getResources().getColor(R.color.green));
        }else{
            childName.setTextColor(context.getResources().getColor(R.color.black));
        }

        childName.setText(subPart.getName().trim());
        checkBox.setChecked(subPart.getSelected());
        notifyDataSetChanged();
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){

        query = query.toLowerCase();
        mainPartsList.clear();

        if(query.isEmpty()){
            mainPartsList.addAll(mainPartsOriginalList);
        }
        else {
            for(MainPart mainPart: mainPartsOriginalList){

                ArrayList<SubPart> subParts = mainPart.getSubParts();
                ArrayList<SubPart> newList = new ArrayList<SubPart>();
                for(SubPart subPart: subParts){
                    if(subPart.getName().toLowerCase().contains(query.toLowerCase().trim())){
                        newList.add(subPart);
                    }
                }
                if(newList.size() > 0){
                    MainPart nMainPart = new MainPart(mainPart.getId(), mainPart.getName(),newList);
                    mainPartsList.add(nMainPart);
                }
            }
        }

        notifyDataSetChanged();

    }

    public void updateChild(int groupPosition, int childPosition, boolean isChecked) {
        ArrayList<SubPart> subPartsList = mainPartsList.get(groupPosition).getSubParts();
        SubPart subPart = subPartsList.get(childPosition);
        subPart.setSelected(isChecked);
        notifyDataSetChanged();
    }

    public String getChildName(int groupPosition, int childPosition){
        ArrayList<SubPart> subPartsList = mainPartsList.get(groupPosition).getSubParts();
        SubPart subPart = subPartsList.get(childPosition);
        return subPart.getName();
    }

    public void checkForDoubleItems(String name, boolean isChecked){
        for(int i= 0; i<getGroupCount(); i++){
            ArrayList<SubPart> subPartsList = mainPartsList.get(i).getSubParts();
            for(int j =0; j<subPartsList.size(); j++){
                SubPart subPart = subPartsList.get(j);
                if(subPart.getName() == name){
                    updateChild(i, j, isChecked);
                }
            }
        }
    }
}
