package com.example.dtcmanager.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.AllEmployeeAttendenceAdapter;
import com.example.dtcmanager.Adapter.EmployeeListAdapter;
import com.example.dtcmanager.Adapter.GetAllEmployeecheckOutByDateAdapter;
import com.example.dtcmanager.Adapter.GetAllLeavingEmployee;
import com.example.dtcmanager.CreateNewEmployeeActivity;
import com.example.dtcmanager.Interface.DeleteEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate.EmployeeCheckOutTimeByDate;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate.GetAllEmployeeCheckOutByDate;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckout.EmployeeCheckOutTime;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckout.GetAllEmployeecheckOutTime;
import com.example.dtcmanager.ModelClass.RemoveEmployee.RemoveEmployee;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeesFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    List<EmployeeCheckOutTime> employeeCheckOutTimeList = new ArrayList<>();
    List<EmployeeCheckOutTimeByDate> employeeCheckOutTimeByDateList = new ArrayList<>();
    RecyclerView recyclerViewEmployees;
    ProgressBar progressBar1;
    String manager_id;
    AlertDialog loadingDialog;
    Date date;
    private DatePickerDialog dpd;
    TextView textnodata;
    Calendar now;

    Button SelctSechDateModule;

    RelativeLayout daily;
    int check;
    LinearLayout DateRange;
    EditText edtDate, edtDate1, edtDate2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employees, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());
        manager_id = Paper.book().read("user_id");
        recyclerViewEmployees = view.findViewById(R.id.recyclerViewEmployees);
        recyclerViewEmployees.setHasFixedSize(false);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        textnodata = view.findViewById(R.id.textnodata);
        edtDate = view.findViewById(R.id.edtDate);

        edtDate = view.findViewById(R.id.edtDate);
        edtDate1 = view.findViewById(R.id.edtDate1);
        edtDate2 = view.findViewById(R.id.edtDate2);
        DateRange = view.findViewById(R.id.DateRange);
        daily = view.findViewById(R.id.daily);
        SelctSechDateModule = view.findViewById(R.id.SelctSechDateModule);


        Calendar currentDate = Calendar.getInstance();
        date = currentDate.getTime();
        String currentDate1 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
//        Toast.makeText(requireContext(), ""+currentDate1, Toast.LENGTH_SHORT).show();
        edtDate.setText(currentDate1);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 1;
                DatePicker();

            }
        });
        edtDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 2;
                DatePicker();

            }
        });
        edtDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 3;
                DatePicker();

            }
        });


        SelctSechDateModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Daily", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daily.setVisibility(View.VISIBLE);
                        DateRange.setVisibility(View.GONE);
                    }
                });


                dialog.setNeutralButton("Date Range", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daily.setVisibility(View.GONE);
                        DateRange.setVisibility(View.VISIBLE);
                    }
                });
                dialog.show();
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkConnection())
        {
            Toast.makeText(getActivity(), "Connected to Internet", Toast.LENGTH_SHORT).show();
            getData();
        }else
        {
            Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {

        String date = edtDate.getText().toString();
        showLoadingDialog();
        Call<GetAllEmployeecheckOutTime> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().GetAllEmployeeCheckout(manager_id, date);
        call.enqueue(new Callback<GetAllEmployeecheckOutTime>() {
            @Override
            public void onResponse(Call<GetAllEmployeecheckOutTime> call, Response<GetAllEmployeecheckOutTime> response) {
                int code = response.code();
                if (code == 200) {
                    hideLoadingDialog();
                    employeeCheckOutTimeList = response.body().getEmployeeCheckOutTime();

                    if (employeeCheckOutTimeList.size() > 0) {
                        textnodata.setVisibility(View.GONE);
                        recyclerViewEmployees.setVisibility(View.VISIBLE);

                        GetAllLeavingEmployee getAllLeavingEmployee = new GetAllLeavingEmployee(requireContext(), employeeCheckOutTimeList);
                        recyclerViewEmployees.setAdapter(getAllLeavingEmployee);
                    } else {

                        textnodata.setVisibility(View.VISIBLE);
                        recyclerViewEmployees.setVisibility(View.GONE);
                    }
                } else {
                    hideLoadingDialog();

                    Toast.makeText(requireContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeecheckOutTime> call, Throwable t) {
                hideLoadingDialog();
                textnodata.setVisibility(View.VISIBLE);
                recyclerViewEmployees.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                EmployeesFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);

        if (check == 1) {
            dpd.setMaxDate(now);

        } else if (check == 2) {
            dpd.setMaxDate(now);
        } else if (check == 3) {
            dpd.setMaxDate(now);
        }

        dpd.setThemeDark(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getChildFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        Calendar currentDate = Calendar.getInstance();
        Date currentDateTime = currentDate.getTime();

        date = now.getTime();

//        if (selectedDate.equals(currentDateTime)) {
//            current = true;
//        } else {
//            current = false;
//        }
        if (check == 1) {

            edtDate.setText(sdf.format(date));

        } else if (check == 2) {
            edtDate1.setText(sdf.format(date));
        } else if (check == 3) {
            edtDate2.setText(sdf.format(date));
        }

        String date = edtDate.getText().toString();
        if (!date.isEmpty()) {

            if(checkConnection())
            {
                getData();
            }else
            {

            }
        }
        String datefirs = edtDate1.getText().toString();
        String datelast = edtDate2.getText().toString();
        if(checkConnection())
        {
            getAllEmployeecheckOutByDate();
        }
        else
        {

        }



    }

    private void getAllEmployeecheckOutByDate() {
        String from_date = edtDate1.getText().toString();
        String to_date = edtDate2.getText().toString();

        Call<GetAllEmployeeCheckOutByDate> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().GetAllEmployeeCheckOutByDate(manager_id, from_date, to_date);
        call.enqueue(new Callback<GetAllEmployeeCheckOutByDate>() {
            @Override
            public void onResponse(Call<GetAllEmployeeCheckOutByDate> call, Response<GetAllEmployeeCheckOutByDate> response) {
                int code = response.code();
                if (code == 200) {
                    hideLoadingDialog();

                    employeeCheckOutTimeByDateList = response.body().getEmployeeCheckOutTime();

                    if (employeeCheckOutTimeByDateList.size() > 0) {
                        textnodata.setVisibility(View.GONE);
                        recyclerViewEmployees.setVisibility(View.VISIBLE);

                        GetAllEmployeecheckOutByDateAdapter getAllEmployeecheckOutByDateAdapter = new GetAllEmployeecheckOutByDateAdapter(requireContext(), employeeCheckOutTimeByDateList);
                        recyclerViewEmployees.setAdapter(getAllEmployeecheckOutByDateAdapter);
                    } else {

                        textnodata.setVisibility(View.VISIBLE);
                        recyclerViewEmployees.setVisibility(View.GONE);
                    }
                } else {
                    hideLoadingDialog();

                    Toast.makeText(requireContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeCheckOutByDate> call, Throwable t) {
                hideLoadingDialog();

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(requireContext()).create();
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}
