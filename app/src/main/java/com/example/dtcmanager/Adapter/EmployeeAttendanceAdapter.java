package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.EmployeeAttendanceModel.EmployeeAttendenceReport;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.SubEmployee;
import com.example.dtcmanager.R;

import java.util.List;

public  class EmployeeAttendanceAdapter extends RecyclerView.Adapter<EmployeeAttendanceAdapter.ViewHolder> {
    Context context;
    List<EmployeeAttendenceReport> attendanceReports;

    public EmployeeAttendanceAdapter(Context context, List<EmployeeAttendenceReport> attendanceReports) {
        this.context = context;
        this.attendanceReports = attendanceReports;
    }

    @NonNull
    @Override
    public EmployeeAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emloyee_attendance_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAttendanceAdapter.ViewHolder holder, int position) {

        EmployeeAttendenceReport attendance = attendanceReports.get(position);
        holder.txtCheckIn.setText(attendance.getCheckInTime());
        holder.txtCheckOut.setText(attendance.getCheckOutTime());
    }

    @Override
    public int getItemCount() {
        return attendanceReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCheckIn,txtCheckOut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
            txtCheckOut = itemView.findViewById(R.id.txtCheckOut);
        }
    }
}
