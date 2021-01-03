
package com.example.dtcmanager.ModelClass.AddProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProject {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_project_Id")
    @Expose
    private Integer addProjectId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddProjectId() {
        return addProjectId;
    }

    public void setAddProjectId(Integer addProjectId) {
        this.addProjectId = addProjectId;
    }

}
