
package com.example.dtcmanager.ModelClass.GetAllProject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllProject {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Projects")
    @Expose
    private List<AllProject> allProjects = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllProject> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<AllProject> allProjects) {
        this.allProjects = allProjects;
    }

}
