
package com.example.dtcmanager.ModelClass.GetEmployeeReportByDate;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDailyReportByDate {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Daily_Report")
    @Expose
    private List<EmployeeDailyReportByDate> employeeDailyReport = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeDailyReportByDate> getEmployeeDailyReport() {
        return employeeDailyReport;
    }

    public void setEmployeeDailyReport(List<EmployeeDailyReportByDate> employeeDailyReport) {
        this.employeeDailyReport = employeeDailyReport;
    }

}
