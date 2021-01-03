
package com.example.dtcmanager.ModelClass.GetAllVehcile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllVechile {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Vehicles")
    @Expose
    private List<AllVehicle> allVehicles = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllVehicle> getAllVehicles() {
        return allVehicles;
    }

    public void setAllVehicles(List<AllVehicle> allVehicles) {
        this.allVehicles = allVehicles;
    }

}
