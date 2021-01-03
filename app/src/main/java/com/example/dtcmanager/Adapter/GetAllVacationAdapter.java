package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.Common.Common;
import com.example.dtcmanager.Interface.LeaveApplicationInterface;
import com.example.dtcmanager.ModelClass.GetAllVacations.EmployeeVacation;
import com.example.dtcmanager.R;

import java.util.List;

public class GetAllVacationAdapter extends RecyclerView.Adapter<GetAllVacationAdapter.ViewHolder> {
    Context context;
    List<EmployeeVacation> employeeVacationList;
LeaveApplicationInterface leaveApplicationInterface;


    public GetAllVacationAdapter(Context context, List<EmployeeVacation> employeeVacationList, LeaveApplicationInterface leaveApplicationInterface) {
        this.context = context;
        this.employeeVacationList = employeeVacationList;
        this.leaveApplicationInterface = leaveApplicationInterface;
    }


    @NonNull
    @Override
    public GetAllVacationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.coutom_vacation_request_layout,parent,false);
        GetAllVacationAdapter.ViewHolder viewHolder = new GetAllVacationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllVacationAdapter.ViewHolder holder, int position) {

        final  EmployeeVacation employeeVacation = employeeVacationList.get(position);
        holder.txtEmployeeName.setText(employeeVacation.getEmployeeName());
        holder.txtStartingDate.setText(employeeVacation.getBeginningDate());
        holder.txtEndDate.setText(employeeVacation.getEndingDate());
        holder.txtReason.setText(employeeVacation.getReason());
        holder.txtbooking_number.setText(employeeVacation.getId());

        if(employeeVacation.getStatus().equals("Accepted")){
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnAccept.setText("Accepted");
            holder.btnReject.setVisibility(View.GONE);

        }
         else if(employeeVacation.getStatus().equals( "Rejected")){
              holder.btnReject.setVisibility(View.VISIBLE);

              holder.btnReject.setText("Rejected");
            holder.btnAccept.setVisibility(View.GONE);
        }
        else if(employeeVacation.getStatus().equals( "pending")){
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnAccept.setVisibility(View.VISIBLE);
        }
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.status = "Rejected";
                leaveApplicationInterface.LeaveApplicationInterface(employeeVacation.getId());
            }
        });

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.status = "Accepted";
                leaveApplicationInterface.LeaveApplicationInterface(employeeVacation.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeVacationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName,txtStartingDate,txtEndDate,txtReason,txtbooking_number;

        Button btnReject,btnAccept;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.txtEmployeeName);
            txtStartingDate = itemView.findViewById(R.id.txtStartingDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtReason = itemView.findViewById(R.id.txtReason);

            btnReject = itemView.findViewById(R.id.btnReject);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            txtbooking_number = itemView.findViewById(R.id.txtbooking_number);

        }
    }
}

