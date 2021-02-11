package com.example.dtcmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.ProjectEmployeeAdapter;
import com.example.dtcmanager.Adapter.ProjectVehicleAdapter;
import com.example.dtcmanager.Adapter.ViewVehicleLocationAdapter;
import com.example.dtcmanager.ModelClass.GetProjectDetail.Employee;
import com.example.dtcmanager.ModelClass.GetProjectDetail.GetProjectDetail;
import com.example.dtcmanager.ModelClass.GetProjectDetail.Location;
import com.example.dtcmanager.ModelClass.GetProjectDetail.Vehicle;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProjectDetail extends AppCompatActivity {
    String id;
    TextView txtProjectName,txtProjectValue,txtStartDate,txtEndDate,txtLocation;
             Button txtschedule_file,txtcontract_file;
    RecyclerView VehicleRecylerView,EmployeeRecylerViewl;
    List<Location> locationList = new ArrayList<>();
    List<Vehicle> vehicleList = new ArrayList<>();
    List<Employee> employeeList = new ArrayList<>();
    Toolbar ChildProfiletoolbar;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_detail);
        id = getIntent().getStringExtra("id");
        txtProjectName = findViewById(R.id.txtProjectName);
        txtProjectValue = findViewById(R.id.txtProjectValue);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtschedule_file = findViewById(R.id.txtschedule_file);
        txtcontract_file = findViewById(R.id.txtcontract_file);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        VehicleRecylerView =  findViewById(R.id.VehicleRecylerView);
        VehicleRecylerView.setHasFixedSize(true);
        VehicleRecylerView.setLayoutManager(new LinearLayoutManager(this));

        EmployeeRecylerViewl =  findViewById(R.id.EmployeeRecylerView);
        EmployeeRecylerViewl.setHasFixedSize(true);
        EmployeeRecylerViewl.setLayoutManager(new LinearLayoutManager(this));
        GetProjectDetail(id);
    }

    private void GetProjectDetail(String id) {
        showLoadingDialog();
        Call<GetProjectDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetProjectDetail(id);
        call.enqueue(new Callback<GetProjectDetail>() {
            @Override
            public void onResponse(Call<GetProjectDetail> call, Response<GetProjectDetail> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    txtProjectName.setText(response.body().getProjectDetail().get(0).getName());
                    txtProjectValue.setText(response.body().getProjectDetail().get(0).getValue());
                    txtStartDate.setText(response.body().getProjectDetail().get(0).getStartDate());
                    txtEndDate.setText(response.body().getProjectDetail().get(0).getDeadLine());
//                    txtcontract_file.setText(response.body().getProjectDetail().get(0).getContractFile());
//
//                    txtschedule_file.setText(response.body().getProjectDetail().get(0).getScheduleFile());
                    if(response.body().getProjectDetail().get(0).getLocation().size() > 0){
                        txtLocation.setText(response.body().getProjectDetail().get(0).getLocation().get(0).getName());
                    }
                    employeeList = response.body().getProjectDetail().get(0).getEmployee();
                    vehicleList = response.body().getProjectDetail().get(0).getVehicle();
                    ProjectVehicleAdapter projectVehicleAdapter = new ProjectVehicleAdapter(ViewProjectDetail.this, vehicleList);
                    VehicleRecylerView.setAdapter(projectVehicleAdapter);
                    ProjectEmployeeAdapter projectEmployeeAdapter = new ProjectEmployeeAdapter(ViewProjectDetail.this, employeeList);
                    EmployeeRecylerViewl.setAdapter(projectEmployeeAdapter);


                    String  Schedule_file = response.body().getProjectDetail().get(0).getScheduleFile();

                    txtschedule_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent intent = new Intent(ViewProjectDetail.this, ViewerActivity.class);
                            Intent browsefile = new Intent(Intent.ACTION_VIEW, Uri.parse(Schedule_file));
                            browsefile.putExtra("orign", Schedule_file);
                            startActivity(browsefile);

                        }
                    });
                    String Contractfile = response.body().getProjectDetail().get(0).getContractFile();

                    Log.i("TAG", "onResponse: "+ Contractfile);
                    txtcontract_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent intent = new Intent(ViewProjectDetail.this, ViewerActivity.class);
                            Intent browsefile= new Intent(Intent.ACTION_VIEW,Uri.parse(Contractfile));
                            browsefile.putExtra("orign", Contractfile);
                            startActivity(browsefile);
                        }
                    });


                }
                   else if(response.code() ==400)
                {
                    hideLoadingDialog();
                    Toast.makeText(ViewProjectDetail.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProjectDetail> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(ViewProjectDetail.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}