
package com.example.dtcmanager.ModelClass.EmployeeDetailMax;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDetail {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Employee_Detail")
    @Expose
    private List<EmployeeDetail_> employeeDetail = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeDetail_> getEmployeeDetail() {
        return employeeDetail;
    }

    public void setEmployeeDetail(List<EmployeeDetail_> employeeDetail) {
        this.employeeDetail = employeeDetail;
    }

}
