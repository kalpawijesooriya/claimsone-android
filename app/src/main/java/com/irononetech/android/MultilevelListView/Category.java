package com.irononetech.android.MultilevelListView;

import java.util.List;

public class Category {
    private String id;
    private String name;
    private List<MainPart> mainPartList;

    public Category(String id, String name, List<MainPart> mainPartList){
        this.id = id;
        this.name = name;
        this.mainPartList = mainPartList;
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

    public List<MainPart> getMainPartList() {
        return mainPartList;
    }

    public void setMainPartList(List<MainPart> mainPartList) {
        this.mainPartList = mainPartList;
    }
}
