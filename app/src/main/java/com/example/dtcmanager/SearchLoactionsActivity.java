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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.LocationListAdapter;
import com.example.dtcmanager.Interface.Removelocation;
import com.example.dtcmanager.ModelClass.GetAllLocation.AllLocation;
import com.example.dtcmanager.ModelClass.GetAllLocation.GettAllLocation;
import com.example.dtcmanager.ModelClass.RemoveLocation.RemoveLocation;
import com.example.dtcmanager.Models.ModelClass;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLoactionsActivity extends AppCompatActivity {

    ImageButton addlocationbtn, backbtn;
    RecyclerView recyclerViewaddresslist;
    List<ModelClass> modelClassList = new ArrayList<>();
    List<AllLocation> allLocationList = new ArrayList<>();
    Toolbar ChildProfiletoolbar;
    String manager_id;
    TextView noData;
    ProgressBar progressBar1;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_loactions);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Paper.init(this);
        manager_id = Paper.book().read("user_id");
        initview();
        clickevents();

    }

    private void initview() {
        progressBar1 = findViewById(R.id.progressBar1);
        addlocationbtn = findViewById(R.id.addlocation);
        noData=findViewById(R.id.noData);
        recyclerViewaddresslist = findViewById(R.id.location_recycler);
        recyclerViewaddresslist.setHasFixedSize(true);
        recyclerViewaddresslist.setLayoutManager(new LinearLayoutManager(this));

    }

    private void clickevents() {
        addlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchLoactionsActivity.this, CreateLocationActivity.class);
                intent.putExtra("orign", "AddLocation");
                startActivity(intent);
            }
        });
    }

    private void getData() {

        showLoadingDialog();
        Call<GettAllLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllLocation(manager_id);
        call.enqueue(new Callback<GettAllLocation>() {
            @Override
            public void onResponse(Call<GettAllLocation> call, Response<GettAllLocation> response) {

                hideLoadingDialog();

                if (response.code() == 200) {
                    allLocationList = response.body().getAllLocations();
                    if(allLocationList.size()>0){
                        noData.setVisibility(View.GONE);
                        recyclerViewaddresslist.setVisibility(View.VISIBLE);
                        LocationListAdapter locationListAdapter = new LocationListAdapter(SearchLoactionsActivity.this,
                                allLocationList, new Removelocation() {
                            @Override
                            public void Removelocation(String id) {
                                RemoveLocation(id);
                            }
                        });
                        recyclerViewaddresslist.setAdapter(locationListAdapter);
                    }
                    else{
                        noData.setVisibility(View.VISIBLE);
                        recyclerViewaddresslist.setVisibility(View.GONE);
                    }
                }
                else  {

//                    Toast.makeText(SearchLoactionsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GettAllLocation> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(SearchLoactionsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void RemoveLocation(String id) {
        showLoadingDialog();
        Call<RemoveLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().RemoveLocation(id);
        call.enqueue(new Callback<RemoveLocation>() {
            @Override
            public void onResponse(Call<RemoveLocation> call, Response<RemoveLocation> response) {
                hideLoadingDialog();
                if (response.code() == 200) {

                    getData();


                } else  {

                    Toast.makeText(SearchLoactionsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveLocation> call, Throwable t) {
              hideLoadingDialog();
                Toast.makeText(SearchLoactionsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
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