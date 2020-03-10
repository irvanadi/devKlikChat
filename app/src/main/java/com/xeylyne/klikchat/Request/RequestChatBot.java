package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseCompany;
import com.xeylyne.klikchat.Response.ResponseDivision;

public class RequestChatBot {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("parent_uuid")
    @Expose
    private Object parentUuid;
    @SerializedName("create_ticket")
    @Expose
    private Integer createTicket;
    @SerializedName("division_id")
    @Expose
    private Integer divisionId;
    @SerializedName("child")
    @Expose
    private Integer child;
    @SerializedName("company")
    @Expose
    private ResponseCompany company;
    @SerializedName("division")
    @Expose
    private ResponseDivision division;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(Object parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getCreateTicket() {
        return createTicket;
    }

    public void setCreateTicket(Integer createTicket) {
        this.createTicket = createTicket;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
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

    public ResponseDivision getDivision() {
        return division;
    }

    public void setDivision(ResponseDivision division) {
        this.division = division;
    }

}
