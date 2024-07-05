package com.irononetech.android.enums;

public enum uploadImageSelectionTwo {
    CaptureImages("Capture Image on Tab Camera", 1),
    CopyImages("Copy Image to the Tab", 2);

    private final String a;
    private final int b;
    uploadImageSelectionTwo(String a, int b){
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}