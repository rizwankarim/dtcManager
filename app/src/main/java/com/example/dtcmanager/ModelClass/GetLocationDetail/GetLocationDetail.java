
package com.example.dtcmanager.ModelClass.GetLocationDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLocationDetail {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Location_Details")
    @Expose
    private List<LocationDetail> locationDetails = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LocationDetail> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(List<LocationDetail> locationDetails) {
        this.locationDetails = locationDetails;
    }

}
