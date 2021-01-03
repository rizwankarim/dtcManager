
package com.example.dtcmanager.ModelClass.UploadJoiningFile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadJoingFile {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_File_Id")
    @Expose
    private List<AddFileId> addFileId = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AddFileId> getAddFileId() {
        return addFileId;
    }

    public void setAddFileId(List<AddFileId> addFileId) {
        this.addFileId = addFileId;
    }

}
