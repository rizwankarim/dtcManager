
package com.example.dtcmanager.ModelClass.GetLocationDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("latitudes")
    @Expose
    private String latitudes;
    @SerializedName("longitudes")
    @Expose
    private String longitudes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }



    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(String latitudes) {
        this.latitudes = latitudes;
    }

    public String getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(String longitudes) {
        this.longitudes = longitudes;
    }

}
