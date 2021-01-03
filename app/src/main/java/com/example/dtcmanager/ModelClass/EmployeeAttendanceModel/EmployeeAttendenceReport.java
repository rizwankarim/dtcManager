
package com.example.dtcmanager.ModelClass.EmployeeAttendanceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeAttendenceReport {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("check_in_time")
    @Expose
    private String checkInTime;
    @SerializedName("check_out_time")
    @Expose
    private String checkOutTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

}
