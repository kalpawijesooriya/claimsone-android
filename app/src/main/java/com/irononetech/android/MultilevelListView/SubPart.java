package com.irononetech.android.MultilevelListView;

/**
 * @author Vimosanan
 *
 */

public class SubPart {
    private String id;
    private String name;
    private Boolean isSelected;
    private String type;

    public SubPart(String id, String name, Boolean isSelected, String type) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
        this.type = type;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
