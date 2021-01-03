package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.VehicleDetailMax.Location;
import com.example.dtcmanager.R;

import java.util.List;

 public  class ViewVehicleLocationAdapter extends RecyclerView.Adapter<ViewVehicleLocationAdapter.ViewHolder> {

    Context context ;
    List<Location> locationList;

    public ViewVehicleLocationAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewVehicleLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_multiple_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewVehicleLocationAdapter.ViewHolder holder, int position) {

        final  Location location = locationList.get(position);
        holder.txtPreview.setText(location.getName());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPreview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPreview = itemView.findViewById(R.id.txtPreview);
        }
    }
}
