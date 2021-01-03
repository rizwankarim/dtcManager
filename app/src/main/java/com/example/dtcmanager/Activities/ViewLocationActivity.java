package com.example.dtcmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.CreateLocationActivity;
import com.example.dtcmanager.ModelClass.GetLocationDetail.GetLocationDetail;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLocationActivity extends AppCompatActivity {
    String id;
    AlertDialog loadingDialog;
    TextView txtTitle,txtaddress,txtlatitudes,txtlongitudes;   Toolbar ChildProfiletoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        txtTitle = findViewById(R.id.txtTitle);
        txtaddress = findViewById(R.id.txtaddress);
        txtlatitudes = findViewById(R.id.txtlatitudes);
        txtlongitudes = findViewById(R.id.txtlongitudes);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        id =getIntent().getStringExtra("id");
        LocationDetail(id);
    }
    private void LocationDetail(String id) {
        showLoadingDialog();
        Call<GetLocationDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().LocationDetail(id);
        call.enqueue(new Callback<GetLocationDetail>() {
            @Override
            public void onResponse(Call<GetLocationDetail> call, Response<GetLocationDetail> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    txtTitle.setText(response.body().getLocationDetails().get(0).gettitle());
                    txtaddress.setText(response.body().getLocationDetails().get(0).getName());
                    txtlatitudes.setText(response.body().getLocationDetails().get(0).getLatitudes());
                    txtlongitudes.setText(response.body().getLocationDetails().get(0).getLongitudes());
                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    Toast.makeText(ViewLocationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    Toast.makeText(ViewLocationActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetLocationDetail> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(ViewLocationActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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