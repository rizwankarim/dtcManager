
package com.example.dtcmanager.ModelClass.GetAllVehcile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllVehicle {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("insurance_date_start")
    @Expose
    private String insuranceDateStart;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInsuranceDateStart() {
        return insuranceDateStart;
    }

    public void setInsuranceDateStart(String insuranceDateStart) {
        this.insuranceDateStart = insuranceDateStart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
