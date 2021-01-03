
package com.example.dtcmanager.ModelClass.AddLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddLocation {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_location_Id")
    @Expose
    private Integer addLocationId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddLocationId() {
        return addLocationId;
    }

    public void setAddLocationId(Integer addLocationId) {
        this.addLocationId = addLocationId;
    }

}
