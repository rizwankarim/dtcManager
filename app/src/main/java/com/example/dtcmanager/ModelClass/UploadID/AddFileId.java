
package com.example.dtcmanager.ModelClass.UploadID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFileId {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
