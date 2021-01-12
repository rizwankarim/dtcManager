package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.Activities.ReportDetailActivity;
import com.example.dtcmanager.ModelClass.GetEmployeeReportByDate.EmployeeDailyReportByDate;
import com.example.dtcmanager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetAllEmployeeReportbyDate extends RecyclerView.Adapter<GetAllEmployeeReportbyDate.ViewHolder> {
    Context context;
    List<EmployeeDailyReportByDate> employeeDailyReportList;

    public GetAllEmployeeReportbyDate(Context context, List<EmployeeDailyReportByDate> employeeDailyReportList) {
        this.context = context;
        this.employeeDailyReportList = employeeDailyReportList;
    }

    @NonNull
    @Override
    public GetAllEmployeeReportbyDate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emloyee_report_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllEmployeeReportbyDate.ViewHolder holder, int position) {
        EmployeeDailyReportByDate employeeDailyReport = employeeDailyReportList.get(position);
        holder.txtTarget.setText(employeeDailyReport.getTarget());
        holder.txtAchievement.setText(employeeDailyReport.getAchievement());
        holder.txtProblems.setText(employeeDailyReport.getProblems());
        holder.txtDateTime.setText(employeeDailyReport.getDateTime());

        String image = "http://dtc.anstm.com/dtcAdmin/api/Employee/Report_Image/"+employeeDailyReport.getReportImage();

        Picasso.get().load(image).into(holder.imgReport, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if (holder.progressbar != null) {
                    holder.progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReportDetailActivity.class);
                intent.putExtra("id", employeeDailyReport.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeeDailyReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTarget,txtAchievement,txtProblems,txtDateTime;
        ImageView imgReport;
        ProgressBar progressbar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTarget = itemView.findViewById(R.id.txtTarget);
            txtAchievement = itemView.findViewById(R.id.txtAchievement);
            txtProblems = itemView.findViewById(R.id.txtProblems);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            imgReport = itemView.findViewById(R.id.imgReport);
            progressbar = itemView.findViewById(R.id.progressbar);
        }
    }
}
