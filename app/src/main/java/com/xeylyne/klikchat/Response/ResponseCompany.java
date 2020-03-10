package com.xeylyne.klikchat.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCompany {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cs_image")
    @Expose
    private String csImage;
    @SerializedName("cs_name")
    @Expose
    private String csName;
    @SerializedName("cs_max_open")
    @Expose
    private String csMaxOpen;
    @SerializedName("info_image")
    @Expose
    private String infoImage;
    @SerializedName("info_name")
    @Expose
    private String infoName;
    @SerializedName("token_old")
    @Expose
    private Object tokenOld;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("token_status")
    @Expose
    private Integer tokenStatus;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCsImage() {
        return csImage;
    }

    public void setCsImage(String csImage) {
        this.csImage = csImage;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getCsMaxOpen() {
        return csMaxOpen;
    }

    public void setCsMaxOpen(String csMaxOpen) {
        this.csMaxOpen = csMaxOpen;
    }

    public String getInfoImage() {
        return infoImage;
    }

    public void setInfoImage(String infoImage) {
        this.infoImage = infoImage;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Object getTokenOld() {
        return tokenOld;
    }

    public void setTokenOld(Object tokenOld) {
        this.tokenOld = tokenOld;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(Integer tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
}
