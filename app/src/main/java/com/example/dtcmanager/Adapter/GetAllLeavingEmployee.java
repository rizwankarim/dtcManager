package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetAllEmployeecheckout.EmployeeCheckOutTime;
import com.example.dtcmanager.R;

import java.util.List;

public class GetAllLeavingEmployee  extends RecyclerView.Adapter<GetAllLeavingEmployee.ViewHolder> {

    Context context;
    List<EmployeeCheckOutTime> employeeCheckOutTimeList;

    public GetAllLeavingEmployee(Context context, List<EmployeeCheckOutTime> employeeCheckOutTimeList) {
        this.context = context;
        this.employeeCheckOutTimeList = employeeCheckOutTimeList;
    }


    @NonNull
    @Override
    public GetAllLeavingEmployee.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_all_checkout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllLeavingEmployee.ViewHolder holder, int position) {

        final  EmployeeCheckOutTime employeeCheckOutTime = employeeCheckOutTimeList.get(position);
        holder.txtEmployeeName.setText(employeeCheckOutTime.getEmployeeName());
        holder.txtCheckOutTime.setText(employeeCheckOutTime.getCheckOutTime());
        holder.txtCheckoutLocation.setText(employeeCheckOutTime.getName());
    }

    @Override
    public int getItemCount() {
        return employeeCheckOutTimeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName,txtCheckOutTime,txtCheckoutLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.txtEmployeeName);
            txtCheckOutTime = itemView.findViewById(R.id.txtCheckOutTime);
            txtCheckoutLocation = itemView.findViewById(R.id.txtCheckoutLocation);

        }
    }
}
