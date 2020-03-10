package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseCompany;

public class RequestCompany {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("company")
    @Expose
    private ResponseCompany company;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ResponseCompany getCompany() {
        return company;
    }

    public void setCompany(ResponseCompany company) {
        this.company = company;
    }
}
