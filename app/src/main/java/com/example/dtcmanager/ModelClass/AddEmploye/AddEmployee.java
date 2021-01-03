
package com.example.dtcmanager.ModelClass.AddEmploye;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddEmployee {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_employee_Id")
    @Expose
    private Integer addEmployeeId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddEmployeeId() {
        return addEmployeeId;
    }

    public void setAddEmployeeId(Integer addEmployeeId) {
        this.addEmployeeId = addEmployeeId;
    }

}
