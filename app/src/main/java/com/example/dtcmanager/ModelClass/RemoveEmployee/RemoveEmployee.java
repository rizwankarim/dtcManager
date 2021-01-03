
package com.example.dtcmanager.ModelClass.RemoveEmployee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveEmployee {

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
