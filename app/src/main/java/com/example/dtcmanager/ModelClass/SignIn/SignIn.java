
package com.example.dtcmanager.ModelClass.SignIn;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignIn {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Manager_Id")
    @Expose
    private List<ManagerId> managerId = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ManagerId> getManagerId() {
        return managerId;
    }

    public void setManagerId(List<ManagerId> managerId) {
        this.managerId = managerId;
    }

}
