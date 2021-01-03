
package com.example.dtcmanager.ModelClass.EditEmloyee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditEmployee {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
