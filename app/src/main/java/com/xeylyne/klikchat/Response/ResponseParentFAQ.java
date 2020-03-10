package com.xeylyne.klikchat.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseParentFAQ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("header")
    @Expose
    private String header;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("parent_uuid")
    @Expose
    private String parentUuid;
    @SerializedName("child")
    @Expose
    private Integer child;
    @SerializedName("company")
    @Expose
    private ResponseCompany company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public ResponseCompany getCompany() {
        return company;
    }

    public void setCompany(ResponseCompany company) {
        this.company = company;
    }

}
