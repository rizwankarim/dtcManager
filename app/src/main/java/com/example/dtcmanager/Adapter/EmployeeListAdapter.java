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

import com.example.dtcmanager.Activities.ViewEmployeeDetailActivity;
import com.example.dtcmanager.Comon.Comon;
import com.example.dtcmanager.CreateNewEmployeeActivity;
import com.example.dtcmanager.Interface.DeleteEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    Context context;
    List<AllEmployee> allEmployeeList;
    DeleteEmployee deleteEmployee;


    public EmployeeListAdapter(Context context, List<AllEmployee> allEmployeeList, DeleteEmployee deleteEmployee) {
        this.context = context;
        this.allEmployeeList = allEmployeeList;
        this.deleteEmployee = deleteEmployee;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.employees_recycler_layout,parent,false);
        EmployeeListAdapter.ViewHolder viewHolder = new EmployeeListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.ViewHolder holder, int position) {
        final AllEmployee allEmployee = allEmployeeList.get(position);
        holder.employee_name.setText(allEmployee.getUserName());
        holder.employee_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                        dialog1.setMessage("Are you sure to Remove this?");
                        dialog1.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 deleteEmployee.DeleteEmployee(allEmployee.getId());
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
                        Intent intent = new Intent(context, CreateNewEmployeeActivity.class);
                        intent.putExtra("orign", "EditEmployee");
                        intent.putExtra("id", allEmployee.getId());
                        context.startActivity(intent);

                    }
                });
                dialog.setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ViewEmployeeDetailActivity.class);
                        Comon.employeeId = allEmployee.getId();
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
        String Image =  allEmployee.getProfileImage();
        Picasso.get().load("http://dtc.anstm.com//dtcAdmin/api/Manager/Profile_Image/" + Image)
                .into(holder.employee_image, new com.squareup.picasso.Callback() {
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
    }
    @Override
    public int getItemCount() {
        return allEmployeeList.size();
    }

    public void filterlist(ArrayList<AllEmployee> filteredlist){
        allEmployeeList=filteredlist;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView employee_name;
        ImageView btnMore;
        CircleImageView employee_image;
        ProgressBar progressBar1;
        LinearLayout employee_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employee_name = itemView.findViewById(R.id.employee_name);
            btnMore = itemView.findViewById(R.id.btnMore);
            employee_image = itemView.findViewById(R.id.employee_image);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
            employee_layout = itemView.findViewById(R.id.employee_layout);
        }
    }
}
