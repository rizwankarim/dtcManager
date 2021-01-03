
package com.example.dtcmanager.ModelClass.GetAllEmployeeAttendence;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllEmployeeAttendence {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Attendence_Report")
    @Expose
    private List<EmployeeAttendenceReport> employeeAttendenceReport = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeAttendenceReport> getEmployeeAttendenceReport() {
        return employeeAttendenceReport;
    }

    public void setEmployeeAttendenceReport(List<EmployeeAttendenceReport> employeeAttendenceReport) {
        this.employeeAttendenceReport = employeeAttendenceReport;
    }

}
