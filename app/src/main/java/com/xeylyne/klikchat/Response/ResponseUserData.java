package com.xeylyne.klikchat.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("user_type_id")
    @Expose
    private Integer userTypeId;
    @SerializedName("division_id")
    @Expose
    private Integer divisionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("token_status")
    @Expose
    private Integer tokenStatus;
    @SerializedName("token_last_sent")
    @Expose
    private Object tokenLastSent;
    @SerializedName("admin")
    @Expose
    private Integer admin;
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
    @SerializedName("company")
    @Expose
    private ResponseCompany company;
    @SerializedName("division")
    @Expose
    private ResponseDivision division;
    @SerializedName("user_type")
    @Expose
    private ResponseUserUserType userType;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
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

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Integer getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(Integer tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Object getTokenLastSent() {
        return tokenLastSent;
    }

    public void setTokenLastSent(Object tokenLastSent) {
        this.tokenLastSent = tokenLastSent;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
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

    public ResponseUserUserType getUserType() {
        return userType;
    }

    public void setUserType(ResponseUserUserType userType) {
        this.userType = userType;
    }
}
