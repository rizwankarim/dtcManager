
package com.example.dtcmanager.ModelClass.ChangeStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStatus {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

}
