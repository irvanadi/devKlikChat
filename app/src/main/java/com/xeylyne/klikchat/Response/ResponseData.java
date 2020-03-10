package com.xeylyne.klikchat.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

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
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("admin")
    @Expose
    private Integer admin;
    @SerializedName("openTicket")
    @Expose
    private Integer openTicket;
    @SerializedName("company")
    @Expose
    private ResponseCompany company;
    @SerializedName("division")
    @Expose
    private ResponseDivision division;
    @SerializedName("usertype")
    @Expose
    private ResponseUserUserType usertype;

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

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getOpenTicket() {
        return openTicket;
    }

    public void setOpenTicket(Integer openTicket) {
        this.openTicket = openTicket;
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

    public ResponseUserUserType getUsertype() {
        return usertype;
    }

    public void setUsertype(ResponseUserUserType usertype) {
        this.usertype = usertype;
    }
}
