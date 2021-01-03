
package com.example.dtcmanager.ModelClass.GetAllEmployee;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllEmploye {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Employees")
    @Expose
    private List<AllEmployee> allEmployees = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllEmployee> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<AllEmployee> allEmployees) {
        this.allEmployees = allEmployees;
    }

}
