package com.example.dtcmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.ViewVehicleLocationAdapter;
import com.example.dtcmanager.AddVehiclesActivity;
import com.example.dtcmanager.ModelClass.VehcileDetail.GetVehcileDetail;
import com.example.dtcmanager.ModelClass.VehcileDetail.VehicleDetail;
import com.example.dtcmanager.ModelClass.VehicleDetailMax.GetVehicleDeailMy;
import com.example.dtcmanager.ModelClass.VehicleDetailMax.Location;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewVehicleActivity extends AppCompatActivity {
    String id;
    AlertDialog loadingDialog;
    Toolbar ChildProfiletoolbar;
    TextView txtVehicleNumber, txtModel, txtKilometer, txtInsuranceDateStart, txtInsuranceEndStart, txtLicenseDate, txtExamineDate, txtEmployee;
    ImageView VehicleImage;
    RecyclerView VehicleRecylerView;
    ProgressBar progressBar1;
    List<Location> locationList = new ArrayList<>();
    String iamgeurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtVehicleNumber = findViewById(R.id.txtVehicleNumber);
        txtModel = findViewById(R.id.txtModel);
        txtKilometer = findViewById(R.id.txtKilometer);
        txtInsuranceDateStart = findViewById(R.id.txtInsuranceDateStart);
        txtInsuranceEndStart = findViewById(R.id.txtInsuranceEndStart);
        txtLicenseDate = findViewById(R.id.txtLicenseDate);
        txtExamineDate = findViewById(R.id.txtExamineDate);
        txtEmployee = findViewById(R.id.txtEmployee);
        VehicleImage = findViewById(R.id.VehicleImage);
        progressBar1 = findViewById(R.id.progressBar1);
        VehicleRecylerView = findViewById(R.id.VehicleRecylerView);
        VehicleRecylerView.setHasFixedSize(true);
        VehicleRecylerView.setLayoutManager(new LinearLayoutManager(this));
        id = getIntent().getStringExtra("id");
        VehicleDetail(id);
    }

    private void VehicleDetail(String id) {
        showLoadingDialog();
        Call<GetVehicleDeailMy> call = RetrofitClientClass.getInstance().getInterfaceInstance().VehicleDetail(id);
        call.enqueue(new Callback<GetVehicleDeailMy>() {
            @Override
            public void onResponse(Call<GetVehicleDeailMy> call, Response<GetVehicleDeailMy> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    locationList = response.body().getVehicleDetails().get(0).getLocation();
                    txtVehicleNumber.setText(response.body().getVehicleDetails().get(0).getVehicleNumber());
                    txtModel.setText(response.body().getVehicleDetails().get(0).getModel());
                    txtKilometer.setText(response.body().getVehicleDetails().get(0).getKilometers());
                    txtInsuranceDateStart.setText(response.body().getVehicleDetails().get(0).getInsuranceDateStart());
                    txtInsuranceEndStart.setText(response.body().getVehicleDetails().get(0).getInsuranceDateEnd());
                    txtLicenseDate.setText(response.body().getVehicleDetails().get(0).getLicenseDateEnd());
                    txtExamineDate.setText(response.body().getVehicleDetails().get(0).getExaminationDate());

                    if (response.body().getVehicleDetails().get(0).getEmployee().size() > 0) {
                        txtEmployee.setText(response.body().getVehicleDetails().get(0).getEmployee().get(0).getUserName());
                    }
                    ViewVehicleLocationAdapter viewVehicleLocationAdapter = new ViewVehicleLocationAdapter(ViewVehicleActivity.this, locationList);
                    VehicleRecylerView.setAdapter(viewVehicleLocationAdapter);
                      iamgeurl = response.body().getVehicleDetails().get(0).getImage();
                    String image = "http://dtc.anstm.com/dtcAdmin/api/Manager/Vehicle_Image/" + response.body().getVehicleDetails().get(0).getImage();
                    Log.i("TAG", "onBindViewHolder:" + image);


                    Picasso.get().load(image).into(VehicleImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressBar1 != null) {
                                progressBar1.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                } else if (response.code() == 400) {

                }
                if(iamgeurl != null){
                    VehicleImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String image = "http://test.proglabs.org/DTC/api/Manager/Vehicle_Image/" + response.body().getVehicleDetails().get(0).getImage();

                            Intent intent = new Intent(ViewVehicleActivity.this, ViewerActivity.class);
                            intent.putExtra("orign", image);
                            startActivity(intent);


                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<GetVehicleDeailMy> call, Throwable t) {
                Toast.makeText(ViewVehicleActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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