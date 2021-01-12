package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.Activities.ViewProjectDetail;
import com.example.dtcmanager.Activities.ViewerActivity;
import com.example.dtcmanager.ModelClass.DailyReportDeatil.ReportImage;
import com.example.dtcmanager.R;

import java.util.List;

public class DailyReportDetailAdapter extends RecyclerView.Adapter<DailyReportDetailAdapter.ViewHolder> {

    Context context;
    List<ReportImage> reportImageList;

    public DailyReportDetailAdapter(Context context, List<ReportImage> reportImageList) {
        this.context = context;
        this.reportImageList = reportImageList;
    }

    @NonNull
    @Override
    public DailyReportDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_daily_report_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportDetailAdapter.ViewHolder holder, int position) {
        final ReportImage reportImage = reportImageList.get(position);
        String image = "http://dtc.anstm.com/dtcAdmin/api/Employee/Report_Image/"+reportImage.getImage();


        holder.txtidFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewerActivity.class);
                intent.putExtra("orign", image);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return reportImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        Button txtidFile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtidFile = itemView.findViewById(R.id.txtidFile);
        }
    }
}
