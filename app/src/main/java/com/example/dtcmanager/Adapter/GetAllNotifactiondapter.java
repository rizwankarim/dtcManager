package com.example.dtcmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcmanager.ModelClass.GetAllNotification.Notification;
import com.example.dtcmanager.R;

import java.util.List;

public class GetAllNotifactiondapter extends RecyclerView.Adapter<GetAllNotifactiondapter.ViewHolder> {
    Context context;
    List<Notification> notificationList;

    public GetAllNotifactiondapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public GetAllNotifactiondapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllNotifactiondapter.ViewHolder holder, int position) {


        final Notification notification = notificationList.get(position);
        holder.txtDate.setText(notification.getDate());
        holder.txtEmployee_Name.setText(notification.getEmployeeName());
        holder.txtTime.setText(notification.getTime());
        holder.txtnotification.setText(notification.getNotifications());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployee_Name,txtTime,txtDate,txtnotification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnotification = itemView.findViewById(R.id.txtnotification);
            txtEmployee_Name = itemView.findViewById(R.id.txtEmployee_Name);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
