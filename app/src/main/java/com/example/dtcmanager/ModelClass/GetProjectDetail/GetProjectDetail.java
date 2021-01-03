
package com.example.dtcmanager.ModelClass.GetProjectDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProjectDetail {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Project_Detail")
    @Expose
    private List<ProjectDetail> projectDetail = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProjectDetail> getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(List<ProjectDetail> projectDetail) {
        this.projectDetail = projectDetail;
    }

}
