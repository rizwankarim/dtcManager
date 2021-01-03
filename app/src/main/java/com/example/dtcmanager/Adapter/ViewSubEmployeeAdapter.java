package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.EmployeeDetailMax.SubEmployee;
import com.example.dtcmanager.R;

import java.util.List;

public  class ViewSubEmployeeAdapter extends RecyclerView.Adapter<ViewSubEmployeeAdapter.ViewHolder> {
    Context context;
    List<SubEmployee> subEmployeeList;

    public ViewSubEmployeeAdapter(Context context, List<SubEmployee> subEmployeeList) {
        this.context = context;
        this.subEmployeeList = subEmployeeList;
    }

    @NonNull
    @Override
    public ViewSubEmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_multiple_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSubEmployeeAdapter.ViewHolder holder, int position) {
        final  SubEmployee subEmployee = subEmployeeList.get(position);
        holder.txtPreview.setText(subEmployee.getUserName());
    }

    @Override
    public int getItemCount() {
        return subEmployeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPreview = itemView.findViewById(R.id.txtPreview);
        }
    }
}
