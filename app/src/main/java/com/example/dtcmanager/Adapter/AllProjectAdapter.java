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

import com.example.dtcmanager.Activities.ViewProjectDetail;
import com.example.dtcmanager.AddVehiclesActivity;
import com.example.dtcmanager.CreateNewProjectActivity;
import com.example.dtcmanager.Interface.DeleteProject;
import com.example.dtcmanager.ModelClass.GetAllProject.AllProject;
import com.example.dtcmanager.R;

import java.util.List;

public class AllProjectAdapter extends RecyclerView.Adapter<AllProjectAdapter.ViewHolder> {
    Context context;
    List<AllProject> allProjectList;
    DeleteProject deleteProject;
    public AllProjectAdapter(Context context, List<AllProject> allProjectList, DeleteProject deleteProject) {
        this.context = context;
        this.allProjectList = allProjectList;
        this.deleteProject = deleteProject;
    }
    @NonNull
    @Override
    public AllProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_project_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProjectAdapter.ViewHolder holder, int position) {
        final  AllProject allProject = allProjectList.get(position);
        holder.txtProjectName.setText(allProject.getName());
        holder.txt_date_start.setText(allProject.getStartDate());
        holder.txt_date_end.setText(allProject.getDeadLine());
        holder.Project_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                        dialog1.setMessage("Are you sure to Remove This?");
                        dialog1.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteProject.DeleteProject(allProject.getId());
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
                        Intent intent = new Intent(context, CreateNewProjectActivity.class);
                        intent.putExtra("orign", "EditProject");
                        intent.putExtra("id", allProject.getId());
                        context.startActivity(intent);
                    }
                });
                dialog.setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ViewProjectDetail.class);
                        intent.putExtra("orign", "EditProject");
                        intent.putExtra("id", allProject.getId());
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return allProjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView navigation_img,btnMore;
        TextView txtProjectName,txt_date_start,txt_date_end;
        ProgressBar progressBar1;
        LinearLayout Project_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navigation_img = itemView.findViewById(R.id.navigation_img);
            txtProjectName = itemView.findViewById(R.id.txtProjectName);
            txt_date_start = itemView.findViewById(R.id.txt_date_start);
            txt_date_end = itemView.findViewById(R.id.txt_date_end);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
            btnMore =itemView.findViewById(R.id.btnMore);
            Project_layout = itemView.findViewById(R.id.Project_layout);
        }
    }
}
