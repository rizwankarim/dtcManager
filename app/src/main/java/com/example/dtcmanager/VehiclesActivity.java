package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.VehicleListAdapter;
import com.example.dtcmanager.Interface.DeleteVehcile;
import com.example.dtcmanager.ModelClass.GetAllVehcile.AllVehicle;
import com.example.dtcmanager.ModelClass.GetAllVehcile.GetAllVechile;
import com.example.dtcmanager.ModelClass.Removevehicle.Removevehcile;
import com.example.dtcmanager.Models.ModelClass;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiclesActivity extends AppCompatActivity {

    ImageButton backbtn;
    ImageButton addvehicle;
    RecyclerView recyclerViewVehicle;
    List<ModelClass> modelClassList = new ArrayList<>();
    Toolbar ChildProfiletoolbar;
    List<AllVehicle> allVehicleList = new ArrayList<>();
    String manager_id;
    ProgressBar progressBar1;
    AlertDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);
        Paper.init(this);

        manager_id = Paper.book().read("user_id");


        initviews();
        clickevents();

    }
    private void initviews() {
        addvehicle = findViewById(R.id.addVehicle);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar1 = findViewById(R.id.progressBar1);

        recyclerViewVehicle = findViewById(R.id.vehicles_recycler);
        recyclerViewVehicle.setHasFixedSize(false);
        recyclerViewVehicle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


    }
    private void clickevents() {


        addvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(VehiclesActivity.this, AddVehiclesActivity.class);
                intent.putExtra("orign", "AddVehicle");
                startActivity(intent);

            }
        });
    }

    private void recylerviews() {
        showLoadingDialog();
        Call<GetAllVechile> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllVechile(manager_id);
        call.enqueue(new Callback<GetAllVechile>() {
            @Override
            public void onResponse(Call<GetAllVechile> call, Response<GetAllVechile> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    allVehicleList = response.body().getAllVehicles();
                    VehicleListAdapter vehicleListAdapter = new VehicleListAdapter(VehiclesActivity.this, allVehicleList, new DeleteVehcile() {
                        @Override
                        public void DeleteVehcile(String id) {
                             RemoveVechile(id);
                        }
                    });
                    recyclerViewVehicle.setAdapter(vehicleListAdapter);
                }
                else  {
                    hideLoadingDialog();
//                    Toast.makeText(VehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllVechile> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(VehiclesActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void RemoveVechile(String id) {
        showLoadingDialog();

        Call<Removevehcile> call = RetrofitClientClass.getInstance().getInterfaceInstance().Removevehcile(id);
        call.enqueue(new Callback<Removevehcile>() {
            @Override
            public void onResponse(Call<Removevehcile> call, Response<Removevehcile> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    recylerviews();
                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    Toast.makeText(VehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Removevehcile> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(VehiclesActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

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

    @Override
    protected void onResume() {
        super.onResume();
        recylerviews();

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