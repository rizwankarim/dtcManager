
package com.example.dtcmanager.ModelClass.GetProjectDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("kilometers")
    @Expose
    private String kilometers;
    @SerializedName("insurance_date_start")
    @Expose
    private String insuranceDateStart;
    @SerializedName("insurance_date_end")
    @Expose
    private String insuranceDateEnd;
    @SerializedName("license_date_end")
    @Expose
    private String licenseDateEnd;
    @SerializedName("examination_date")
    @Expose
    private String examinationDate;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getInsuranceDateStart() {
        return insuranceDateStart;
    }

    public void setInsuranceDateStart(String insuranceDateStart) {
        this.insuranceDateStart = insuranceDateStart;
    }

    public String getInsuranceDateEnd() {
        return insuranceDateEnd;
    }

    public void setInsuranceDateEnd(String insuranceDateEnd) {
        this.insuranceDateEnd = insuranceDateEnd;
    }

    public String getLicenseDateEnd() {
        return licenseDateEnd;
    }

    public void setLicenseDateEnd(String licenseDateEnd) {
        this.licenseDateEnd = licenseDateEnd;
    }

    public String getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
