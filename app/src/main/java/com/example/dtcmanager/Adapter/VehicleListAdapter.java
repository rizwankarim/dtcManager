package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.Activities.ViewLocationActivity;
import com.example.dtcmanager.Activities.ViewVehicleActivity;
import com.example.dtcmanager.AddVehiclesActivity;
import com.example.dtcmanager.Interface.DeleteVehcile;
import com.example.dtcmanager.Interface.EditvehcileInterface;
import com.example.dtcmanager.ModelClass.GetAllVehcile.AllVehicle;
import com.example.dtcmanager.Models.ModelClass;
import com.example.dtcmanager.R;
import com.example.dtcmanager.VehiclesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    Context context;
    List<AllVehicle> allVehicleList;

    DeleteVehcile deleteVehcile;


    public VehicleListAdapter(Context context, List<AllVehicle> allVehicleList, DeleteVehcile deleteVehcile) {
        this.context = context;
        this.allVehicleList = allVehicleList;
        this.deleteVehcile = deleteVehcile;
    }

    @NonNull
    @Override
    public VehicleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.addvehicle_recycler_layout, parent, false);
        VehicleListAdapter.ViewHolder viewHolder = new VehicleListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AllVehicle allVehicle = allVehicleList.get(position);
        holder.txt_date_end.setText(allVehicle.getInsuranceDateStart());
        holder.txt_Vehicle_number.setText(allVehicle.getId());
        holder.txtmodel.setText(allVehicle.getModel());

        String Image =  allVehicle.getImage();

        Picasso.get().load("http://test.proglabs.org/DTC/api/Manager/Vehicle_Image/" + Image)
                .into(holder.navigation_img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.progressBar1 != null) {
                            holder.progressBar1.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.Vehicles_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                        dialog1.setMessage("Are you sure to remove this?");
                        dialog1.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteVehcile.DeleteVehcile(allVehicle.getId());


                            }
                        });
                        dialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog1.show();
                    }
                });
                dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, AddVehiclesActivity.class);
                        intent.putExtra("orign", "EditVehicle");
                        intent.putExtra("id", allVehicle.getId());
                        context.startActivity(intent);


                    }
                });
                dialog.setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, ViewVehicleActivity.class);
                        intent.putExtra("orign", "EditVehicle");
                        intent.putExtra("id", allVehicle.getId());
                        context.startActivity(intent);


                    }
                });
                dialog.show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return allVehicleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView navigation_img,btnMore;
        TextView txt_Vehicle_number,txtmodel,txt_dateStart,txt_date_end;
        ProgressBar progressBar1;
        LinearLayout Vehicles_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            navigation_img = itemView.findViewById(R.id.navigation_img);
            txt_Vehicle_number = itemView.findViewById(R.id.txt_Vehicle_number);
            txtmodel = itemView.findViewById(R.id.txtmodel);
            Vehicles_layout = itemView.findViewById(R.id.Vehicles_layout);
            txt_date_end = itemView.findViewById(R.id.txt_date_end);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
            btnMore =itemView.findViewById(R.id.btnMore);

        }
    }
}
