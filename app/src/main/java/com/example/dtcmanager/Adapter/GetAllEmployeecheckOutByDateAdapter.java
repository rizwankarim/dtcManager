package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate.EmployeeCheckOutTimeByDate;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckout.EmployeeCheckOutTime;
import com.example.dtcmanager.R;

import java.util.List;

public class GetAllEmployeecheckOutByDateAdapter  extends RecyclerView.Adapter<GetAllEmployeecheckOutByDateAdapter.ViewHolder> {

  Context context;
  List<EmployeeCheckOutTimeByDate> employeeCheckOutTimeByDateList;

    public GetAllEmployeecheckOutByDateAdapter(Context context, List<EmployeeCheckOutTimeByDate> employeeCheckOutTimeByDateList) {
        this.context = context;
        this.employeeCheckOutTimeByDateList = employeeCheckOutTimeByDateList;
    }

    @NonNull
    @Override
    public GetAllEmployeecheckOutByDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_all_checkout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllEmployeecheckOutByDateAdapter.ViewHolder holder, int position) {
        final EmployeeCheckOutTimeByDate employeeCheckOutTimeByDate = employeeCheckOutTimeByDateList.get(position);
        holder.txtEmployeeName.setText(employeeCheckOutTimeByDate.getEmployeeName());
        holder.txtCheckOutTime.setText(employeeCheckOutTimeByDate.getCheckOutTime());
        holder.txtCheckoutLocation.setText(employeeCheckOutTimeByDate.getName());
    }

    @Override
    public int getItemCount() {
        return employeeCheckOutTimeByDateList.size();
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
