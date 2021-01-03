
package com.example.dtcmanager.ModelClass.VehcileDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVehcileDetail {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Vehicle_Details")
    @Expose
    private List<VehicleDetail> vehicleDetails = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<VehicleDetail> getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(List<VehicleDetail> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

}
