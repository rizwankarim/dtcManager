package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetProjectDetail.Employee;
import com.example.dtcmanager.R;

import java.util.List;

public class ProjectEmployeeAdapter extends RecyclerView.Adapter<ProjectEmployeeAdapter.ViewHolder> {
    Context context;
    List<Employee> employeeList ;

    public ProjectEmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ProjectEmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_multiple_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectEmployeeAdapter.ViewHolder holder, int position) {
        final  Employee employee = employeeList.get(position);
        holder.txtPreview.setText(employee.getUserName());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPreview = itemView.findViewById(R.id.txtPreview);
        }
    }
}
