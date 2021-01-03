package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dtcmanager.ModelClass.GetEmployeeAttendenceByDate.EmployeeAttendenceReportByReport;
import com.example.dtcmanager.R;

import java.util.List;

public class AllEmployeeattendecebyDate extends RecyclerView.Adapter<AllEmployeeattendecebyDate.ViewHolder> {
    Context context;
    List<EmployeeAttendenceReportByReport> employeeAttendenceReportByReportList;

    public AllEmployeeattendecebyDate(Context context, List<EmployeeAttendenceReportByReport> employeeAttendenceReportByReportList) {
        this.context = context;
        this.employeeAttendenceReportByReportList = employeeAttendenceReportByReportList;
    }

    @NonNull
    @Override
    public AllEmployeeattendecebyDate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_all_employee_attendence,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEmployeeattendecebyDate.ViewHolder holder, int position) {
        final EmployeeAttendenceReportByReport employeeAttendenceReportByReport = employeeAttendenceReportByReportList.get(position);
        holder.txtEmployeeName.setText(employeeAttendenceReportByReport.getEmployeeName());
        holder.txtCheckInTime.setText(employeeAttendenceReportByReport.getCheckInTime());
        holder.txtLocation.setText(employeeAttendenceReportByReport.getName());
//        holder.txtCheckOutTime.setText(employeeAttendenceReport.getCheckOutTime());
    }

    @Override
    public int getItemCount() {
        return  employeeAttendenceReportByReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName,txtCheckInTime,txtCheckOutTime,txtLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.txtEmployeeName);
            txtCheckInTime = itemView.findViewById(R.id.txtCheckInTime);
            txtCheckOutTime = itemView.findViewById(R.id.txtCheckOutTime);
            txtLocation = itemView.findViewById(R.id.txtLocation);
        }
    }
}
