package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.EmployeeDailyReportModel.ReportImage;
import com.example.dtcmanager.R;

import java.util.List;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.ViewHolder> {
    Context context;
    List<ReportImage> reportImageList;

    public DailyReportAdapter(Context context, List<ReportImage> reportImageList) {
        this.context = context;
        this.reportImageList = reportImageList;
    }

    @NonNull
    @Override
    public DailyReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emloyee_report_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reportImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
