
package com.example.dtcmanager.ModelClass.DailyReportDeatil;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyReportDetail {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Report_Detail")
    @Expose
    private List<ReportDetail> reportDetail = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ReportDetail> getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(List<ReportDetail> reportDetail) {
        this.reportDetail = reportDetail;
    }

}
