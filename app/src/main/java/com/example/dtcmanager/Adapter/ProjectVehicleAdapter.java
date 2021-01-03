package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetProjectDetail.Vehicle;
import com.example.dtcmanager.R;

import java.util.List;

public class ProjectVehicleAdapter extends RecyclerView.Adapter<ProjectVehicleAdapter.ViewHolder> {

    Context context;
    List<Vehicle> vehicleList ;

    public ProjectVehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public ProjectVehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_multiple_layout,parent,false);
        return new   ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectVehicleAdapter.ViewHolder holder, int position) {
        final Vehicle vehicle = vehicleList.get(position);
        holder.txtPreview.setText(vehicle.getVehicleNumber() + " " + vehicle.getModel());

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPreview = itemView.findViewById(R.id.txtPreview);
        }
    }
}
