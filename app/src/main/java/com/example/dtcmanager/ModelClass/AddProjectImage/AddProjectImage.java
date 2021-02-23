
package com.example.dtcmanager.ModelClass.AddProjectImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProjectImage {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_Image_Id")
    @Expose
    private List<AddProjectImageId> addImageId = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AddProjectImageId> getAddImageId() {
        return addImageId;
    }

    public void setAddImageId(List<AddProjectImageId> addImageId) {
        this.addImageId = addImageId;
    }

}
