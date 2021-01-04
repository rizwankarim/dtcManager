package com.example.dtcmanager.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcmanager.Activities.AllEmplyeeActivity;
import com.example.dtcmanager.Activities.ArrangeReportsActivity;
import com.example.dtcmanager.Activities.ViewEmployeeDetailActivity;
import com.example.dtcmanager.Comon.Comon;
import com.example.dtcmanager.CreateNewEmployeeActivity;
import com.example.dtcmanager.CreateNewProjectActivity;
import com.example.dtcmanager.LoginActivity;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.Notification.Notification;
import com.example.dtcmanager.ProjectsActivity;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.example.dtcmanager.SplashActivity;
import com.example.dtcmanager.VehiclesActivity;
import com.example.dtcmanager.HomeActivity;
import com.example.dtcmanager.R;
import com.example.dtcmanager.SearchLoactionsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    HomeActivity homeActivity;
    ImageButton btnlogout;
    MultiSpinnerSearch employeeListSpinner;
    String manager_id, employee_id;
    Button create_employee_btn,btnArchive;
    Window window;
    Timer timer;
    List<AllEmployee> allEmployeeList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    android.app.AlertDialog loadingDialog;
    EditText textNoti;
    LinearLayout ReportssHomebtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Paper.init(requireContext());
        manager_id = Paper.book().read("user_id");

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeActivity = new HomeActivity();


        LinearLayout layoutEmployeeBtn = view.findViewById(R.id.layoutEmployeeBtn);
        LinearLayout locationBtn = view.findViewById(R.id.locationsHomebtn);
        LinearLayout VehiclesBtn = view.findViewById(R.id.vehicles_btn);
        LinearLayout ProjectsBtn = view.findViewById(R.id.ProjectsHomebtn);
        ReportssHomebtn = view.findViewById(R.id.ReportssHomebtn);
        employeeListSpinner = view.findViewById(R.id.employeeListSpinner);
        create_employee_btn = view.findViewById(R.id.create_employee_btn);

        textNoti = view.findViewById(R.id.textNoti);
        btnlogout = view.findViewById(R.id.btnlogout);
        AllEmploye();

        layoutEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AllEmplyeeActivity.class);
                startActivity(intent);
            }
        });
        ReportssHomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ArrangeReportsActivity.class);
                startActivity(intent);
            }
        });

        create_employee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date time1 = Calendar.getInstance().getTime();
                SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                SimpleDateFormat sdp1 = new SimpleDateFormat(" hh:mm a", Locale.US);
                String date = sdp.format(time1);
                String time = sdp1.format(time1);
                String notifications = textNoti.getText().toString();
                if (notifications.isEmpty()) {
                    textNoti.setError("Please Enter Notification");
                    textNoti.requestFocus();
                } else if (employeeList.isEmpty()) {

                    Toast.makeText(requireContext(), "Please Select Employee", Toast.LENGTH_SHORT).show();
                } else {
                    sendNotificationToSubEmployee(notifications, date, time);
                    showLoadingDialog();
                }
            }

        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setMessage("Are you sure?");
                dialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Paper.book().destroy();

                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                dialog.show();
            }


        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireContext(), SearchLoactionsActivity.class);
                startActivity(intent);

            }
        });


        VehiclesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireContext(), VehiclesActivity.class);
                startActivity(intent);
            }
        });


        ProjectsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProjectsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void sendNotificationToSubEmployee(String notifications, String date, String time) {
        if (employeeList.size() > 0) {
//            for (int i = 0; i < employeeList.size(); i++) {
//                employee_id = employeeList.get(i);

            Call<Notification> call = RetrofitClientClass.getInstance().getInterfaceInstance()
                    .CreateNotification(manager_id, employeeList.get(0), "Manager", notifications, date, time);
            call.enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if (response.code() == 200) {

                        employeeList.remove(0);
                        sendNotificationToSubEmployee(notifications, date, time);

                    } else if (response.code() == 404) {
                        hideLoadingDialog();
//                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        hideLoadingDialog();
//                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    hideLoadingDialog();

                    Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }

        else {
            textNoti.setText("");
            hideLoadingDialog();
        }
    }


    private void AllEmploye() {
//        progressBar1.setVisibility(View.VISIBLE);
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(manager_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
                    allEmployeeList = response.body().getAllEmployees();
                    if (allEmployeeList.size() > 0) {

                     setLocationInSpinner(allEmployeeList);
                    } else {
                        Toast.makeText(requireContext(), "Please Add Employee", Toast.LENGTH_SHORT).show();
                    }

//                    spinnerevents(allEmployeeList);
                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
//                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setLocationInSpinner(List<AllEmployee> allEmployeeList) {
        List<KeyPairBoolData> data = new ArrayList<>();

        for (int i = 0; i < allEmployeeList.size(); i++) {

            KeyPairBoolData boolData = new KeyPairBoolData();
            boolData.setId(Integer.parseInt(allEmployeeList.get(i).getId()));
            boolData.setName(allEmployeeList.get(i).getUserName());
            boolData.setSelected(false);


            for (int j = 0 ; j < employeeList.size() ; j++)
            {
                if (allEmployeeList.get(i).getId().equals(employeeList.get(j)))
                {

                    boolData.setSelected(true);
                }
            }

            data.add(boolData);

        }



        employeeListSpinner.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                employeeList.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    employeeList.add(String.valueOf(selectedItems.get(i).getId()));
//                    Toast.makeText(CreateNewProjectActivity.this, ""+employeeList, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void showLoadingDialog() {
        loadingDialog = new android.app.AlertDialog.Builder(requireContext()).create();
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