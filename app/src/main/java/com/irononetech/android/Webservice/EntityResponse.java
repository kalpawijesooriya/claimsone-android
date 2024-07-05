package com.irononetech.android.Webservice;

public class EntityResponse {
    private StatusObj Status;
    private ResultObj result;

    public StatusObj getStatus() {
        return Status;
    }

    public void setStatus(StatusObj status) {
        Status = status;
    }

    public ResultObj getResult() {
        return result;
    }

    public void setResult(ResultObj result) {
        this.result = result;
    }
}
