package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.Activities.ViewLocationActivity;
import com.example.dtcmanager.AddVehiclesActivity;
import com.example.dtcmanager.CreateLocationActivity;
import com.example.dtcmanager.Interface.Removelocation;
import com.example.dtcmanager.ModelClass.GetAllLocation.AllLocation;
import com.example.dtcmanager.ModelClass.GetAllVehcile.AllVehicle;
import com.example.dtcmanager.Models.ModelClass;
import com.example.dtcmanager.R;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    Context context;
    List<AllLocation> allLocationList;
    Removelocation removelocation;

    public LocationListAdapter(Context context, List<AllLocation> allLocationList, Removelocation removelocation) {
        this.context = context;
        this.allLocationList = allLocationList;
        this.removelocation = removelocation;
    }

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.location_recycler_layout,parent,false);
        LocationListAdapter.ViewHolder viewHolder = new LocationListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder holder, int position) {
        final AllLocation allLocation = allLocationList.get(position);
        holder.txt_address.setText(allLocation.getName());
        holder.txt_tittle.setText(allLocation.gettitle());
        holder.locations_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removelocation.Removelocation(allLocation.getId());
                    }
                });
                dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, CreateLocationActivity.class);
                        intent.putExtra("orign", "EditLocation");
                        intent.putExtra("id", allLocation.getId());
                        context.startActivity(intent);
                    }
                });

                dialog.setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, ViewLocationActivity.class);
                        intent.putExtra("orign", "ViewLocation");
                        intent.putExtra("id", allLocation.getId());
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allLocationList.size();
    }

    public void filterlist(ArrayList<AllLocation> filteredlist){
        allLocationList=filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_address,txt_tittle;
        ImageView imgeMore;
        LinearLayout locations_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_address = itemView.findViewById(R.id.txt_address);
            imgeMore = itemView.findViewById(R.id.imgeMore);
            locations_layout = itemView.findViewById(R.id.locations_layout);
            txt_tittle = itemView.findViewById(R.id.txt_tittle);
        }
    }
}
