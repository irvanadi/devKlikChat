package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestInsertDivision {

    @SerializedName("division")
    @Expose
    private ResponseDivision division;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseDivision getDivision() {
        return division;
    }

    public void setDivision(ResponseDivision division) {
        this.division = division;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
