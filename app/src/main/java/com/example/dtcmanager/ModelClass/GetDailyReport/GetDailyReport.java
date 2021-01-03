
package com.example.dtcmanager.ModelClass.GetDailyReport;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDailyReport {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Daily_Report")
    @Expose
    private List<EmployeeDailyReport> employeeDailyReport = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeDailyReport> getEmployeeDailyReport() {
        return employeeDailyReport;
    }

    public void setEmployeeDailyReport(List<EmployeeDailyReport> employeeDailyReport) {
        this.employeeDailyReport = employeeDailyReport;
    }

}
