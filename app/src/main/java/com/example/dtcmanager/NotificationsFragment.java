package com.example.dtcmanager;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.GetAllNotifactiondapter;
import com.example.dtcmanager.ModelClass.GetAllNotification.GetNotificationManager;
import com.example.dtcmanager.ModelClass.GetAllNotification.Notification;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    RecyclerView NotificationRecylerView;
    String manager_id;
    LinearLayout noData_layout;
    List<Notification> notificationList = new ArrayList<>();
    AlertDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());
        manager_id = Paper.book().read("user_id");
        noData_layout = view.findViewById(R.id.noData_layout);
        NotificationRecylerView = view.findViewById(R.id.NotificationRecylerView);
        NotificationRecylerView.setHasFixedSize(true);
        NotificationRecylerView.setLayoutManager(new LinearLayoutManager(requireContext()));


    }

    private void getData(String manager_id) {

        showLoadingDialog();
        Call<GetNotificationManager> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllNotification(manager_id);
        call.enqueue(new Callback<GetNotificationManager>() {
            @Override
            public void onResponse(Call<GetNotificationManager> call, Response<GetNotificationManager> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.GONE);
                    notificationList = response.body().getNotification();

                    GetAllNotifactiondapter getAllNotifactiondapter = new GetAllNotifactiondapter(requireContext(),notificationList);
                    NotificationRecylerView.setAdapter(getAllNotifactiondapter);

                }
                else if(response.code() ==404 ){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                }

                else if(response.code() ==400 ){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<GetNotificationManager> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(manager_id);
    }


    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(requireContext()).create();
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}