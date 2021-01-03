package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetAllEmployeeAttendence.EmployeeAttendenceReport;
import com.example.dtcmanager.R;

import java.util.List;

public class AllEmployeeAttendenceAdapter extends RecyclerView.Adapter<AllEmployeeAttendenceAdapter.ViewHolder> {
    Context context;
    List<EmployeeAttendenceReport> employeeAttendenceReportList;

    public AllEmployeeAttendenceAdapter(Context context, List<EmployeeAttendenceReport> employeeAttendenceReportList) {
        this.context = context;
        this.employeeAttendenceReportList = employeeAttendenceReportList;
    }

    @NonNull
    @Override
    public AllEmployeeAttendenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_all_employee_attendence,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEmployeeAttendenceAdapter.ViewHolder holder, int position) {
        final  EmployeeAttendenceReport employeeAttendenceReport = employeeAttendenceReportList.get(position);
        holder.txtEmployeeName.setText(employeeAttendenceReport.getEmployeeName());
        holder.txtCheckInTime.setText(employeeAttendenceReport.getCheckInTime());
        holder.txtLocation.setText(employeeAttendenceReport.getName());
//        holder.txtCheckOutTime.setText(employeeAttendenceReport.getCheckOutTime());

    }

    @Override
    public int getItemCount() {
        return employeeAttendenceReportList.size();
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
