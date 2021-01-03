
package com.example.dtcmanager.ModelClass.GetAllEmployeecheckout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeCheckOutTime {

    @SerializedName("check_out_time")
    @Expose
    private String checkOutTime;
    @SerializedName("Employee_name")
    @Expose
    private String employeeName;

    @SerializedName("name")
    @Expose
    private String name;

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
