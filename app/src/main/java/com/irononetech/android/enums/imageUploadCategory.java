package com.irononetech.android.enums;

public enum imageUploadCategory {
    CaptureImages("Accident Images", 1),
    CopyImages("Document Images", 2);

    private final String a;
    private final int b;
    imageUploadCategory(String a, int b){
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
