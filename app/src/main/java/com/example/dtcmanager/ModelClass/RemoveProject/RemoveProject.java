
package com.example.dtcmanager.ModelClass.RemoveProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveProject {

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
