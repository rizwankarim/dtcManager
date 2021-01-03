
package com.example.dtcmanager.ModelClass.GetAllNotification;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNotificationManager {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Notification")
    @Expose
    private List<Notification> notification = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

}
