
package com.example.dtcmanager.ModelClass.GetEmployeeDetailMax;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubEmployee {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
