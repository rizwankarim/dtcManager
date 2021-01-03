
package com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeCheckOutTimeByDate {

    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("check_out_time")
    @Expose
    private String checkOutTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Employee_name")
    @Expose
    private String employeeName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

}
