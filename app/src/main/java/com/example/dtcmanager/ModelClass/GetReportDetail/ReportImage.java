
package com.example.dtcmanager.ModelClass.GetReportDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportImage {

    @SerializedName("image")
    @Expose
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
