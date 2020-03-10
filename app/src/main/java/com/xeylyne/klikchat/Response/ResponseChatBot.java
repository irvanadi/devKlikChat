package com.xeylyne.klikchat.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseChatBot {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("parent_uuid")
    @Expose
    private Object parentUuid;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("create_ticket")
    @Expose
    private String createTicket;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getCreateTicket() {
        return createTicket;
    }

    public void setCreateTicket(String createTicket) {
        this.createTicket = createTicket;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
