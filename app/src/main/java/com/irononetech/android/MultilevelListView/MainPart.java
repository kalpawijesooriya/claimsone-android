package com.irononetech.android.MultilevelListView;

import java.util.ArrayList;

/**
 * @author Vimosanan
 *
 */

public class MainPart {
    private String id;
    private String name;
    private ArrayList<SubPart> subParts = new ArrayList<SubPart>();

    public MainPart(String id, String name, ArrayList<SubPart> subParts) {
        this.id = id;
        this.name = name;
        this.subParts = subParts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SubPart> getSubParts() {
        return subParts;
    }

    public void setSubParts(ArrayList<SubPart> subParts) {
        this.subParts = subParts;
    }
}
