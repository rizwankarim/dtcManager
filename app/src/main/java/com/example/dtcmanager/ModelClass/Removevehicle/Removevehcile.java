
package com.example.dtcmanager.ModelClass.Removevehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Removevehcile {

    @SerializedName("Status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
