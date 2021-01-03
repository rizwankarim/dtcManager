
package com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllEmployeeCheckOutByDate {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Check_out_time")
    @Expose
    private List<EmployeeCheckOutTimeByDate> employeeCheckOutTime = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeCheckOutTimeByDate> getEmployeeCheckOutTime() {
        return employeeCheckOutTime;
    }

    public void setEmployeeCheckOutTime(List<EmployeeCheckOutTimeByDate> employeeCheckOutTime) {
        this.employeeCheckOutTime = employeeCheckOutTime;
    }

}
