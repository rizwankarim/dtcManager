
package com.example.dtcmanager.ModelClass.GetEmployeeAttendenceByDate;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAttendenceByDate {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Attendence_Report")
    @Expose
    private List<EmployeeAttendenceReportByReport> employeeAttendenceReport = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeAttendenceReportByReport> getEmployeeAttendenceReport() {
        return employeeAttendenceReport;
    }

    public void setEmployeeAttendenceReport(List<EmployeeAttendenceReportByReport> employeeAttendenceReport) {
        this.employeeAttendenceReport = employeeAttendenceReport;
    }

}
