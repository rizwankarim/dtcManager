
package com.example.dtcmanager.ModelClass.AddVechile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVechile {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_vehicle_Id")
    @Expose
    private Integer addVehicleId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddVehicleId() {
        return addVehicleId;
    }

    public void setAddVehicleId(Integer addVehicleId) {
        this.addVehicleId = addVehicleId;
    }

}
