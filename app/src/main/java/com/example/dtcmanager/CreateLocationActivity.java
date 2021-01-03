package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Activities.SelectAddressActivity;
import com.example.dtcmanager.Common.Common;
import com.example.dtcmanager.ModelClass.AddLocation.AddLocation;
import com.example.dtcmanager.ModelClass.EditLocation.EditLocation;
import com.example.dtcmanager.ModelClass.GetLocationDetail.GetLocationDetail;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateLocationActivity extends AppCompatActivity {

    ImageButton backbtn;
    Button save_location_btn, search_location;
    Toolbar ChildProfiletoolbar;
    EditText edtLocation,estlocationTitle;
    ProgressBar progressBar1;
    String manager_id;
    String originCheck,id;
    AlertDialog loadingDialog;
    TextView txtlocationCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);
        edtLocation = findViewById(R.id.edtLocation);
        save_location_btn = findViewById(R.id.save_location_btn);
        progressBar1 = findViewById(R.id.progressBar1);
        search_location = findViewById(R.id.btnAddNewAddress);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        txtlocationCreate = findViewById(R.id.txtlocationCreate);
        estlocationTitle = findViewById(R.id.estlocationTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        originCheck = getIntent().getStringExtra("orign");

        Paper.init(this);
        manager_id = Paper.book().read("user_id");



        if (originCheck.equals("AddLocation")) {
            edtLocation.setText("");

                    save_location_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SaveLocation(); }});

        } else if (originCheck.equals("EditLocation")) {
            id =getIntent().getStringExtra("id");
//            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
            txtlocationCreate.setText("Edit Location");
            LocationDetail(id);
            save_location_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditLocation(id); }});
        }

        search_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateLocationActivity.this, SelectAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LocationDetail(String id) {
        showLoadingDialog();
        Call<GetLocationDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().LocationDetail(id);
        call.enqueue(new Callback<GetLocationDetail>() {
            @Override
            public void onResponse(Call<GetLocationDetail> call, Response<GetLocationDetail> response) {
                if(response.code() == 200){
                    hideLoadingDialog();


                    estlocationTitle.setText(response.body().getLocationDetails().get(0).gettitle());
                    edtLocation.setText(response.body().getLocationDetails().get(0).getName());
                }
                else if(response.code() == 404){
                    hideLoadingDialog();
//                    Toast.makeText(CreateLocationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                }
                else if(response.code() == 400){
                    hideLoadingDialog();
//                    Toast.makeText(CreateLocationActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetLocationDetail> call, Throwable t) {
                hideLoadingDialog();

//                Toast.makeText(CreateLocationActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void EditLocation(String id) {
        String name = edtLocation.getText().toString();
        if(name.isEmpty()){
            edtLocation.setError("Please Select Location");
            edtLocation.requestFocus();
        }
        else {
       showLoadingDialog();
        Call<EditLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().EditLocation(name,id,String.valueOf(Common.SelectedLati),String.valueOf(Common.Selectlongi));
        call.enqueue(new Callback<EditLocation>() {
            @Override
            public void onResponse(Call<EditLocation> call, Response<EditLocation> response) {
                if(response.code() == 200){
                   hideLoadingDialog();
//                    Toast.makeText(CreateLocationActivity.this, "Location Update Successfully", Toast.LENGTH_SHORT).show();
                    edtLocation.setText("");
                    finish();

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
//                    Toast.makeText(CreateLocationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditLocation> call, Throwable t) {
            hideLoadingDialog();
//                Toast.makeText(CreateLocationActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }}


    private void SaveLocation() {
        String name = edtLocation.getText().toString();
        String title = estlocationTitle.getText().toString();
        if(name.isEmpty()){
            edtLocation.setError("Please Select Location");
            edtLocation.requestFocus();
        }
        else if(title.isEmpty()){
            estlocationTitle.setError("Please Enter Title");
            estlocationTitle.requestFocus();
        }
        else {
         showLoadingDialog();
            Call<AddLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddLocation(name,title, manager_id, String.valueOf(Common.SelectedLati), String.valueOf(Common.Selectlongi));

            call.enqueue(new Callback<AddLocation>() {
                @Override
                public void onResponse(Call<AddLocation> call, Response<AddLocation> response) {
                    if (response.code() == 200) {
                      hideLoadingDialog();
//                        Toast.makeText(CreateLocationActivity.this, "Location Added Successfully", Toast.LENGTH_SHORT).show();
                        edtLocation.setText("");

                        finish();
                    } else if (response.code() == 404) {
                       hideLoadingDialog();
//                        Toast.makeText(CreateLocationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddLocation> call, Throwable t) {
                    hideLoadingDialog();
//                    Toast.makeText(CreateLocationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
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
        edtLocation.setText(Common.SlectedPlace);
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