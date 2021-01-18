package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dtcmanager.ModelClass.SignIn.SignIn;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    ProgressBar progressBar1;
    EditText edtUserName, edtPassword;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);

        if(checkConnection())
        {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
            intView();
            clickable();

        }else
        {

            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }

    private void intView() {
        loginbtn = findViewById(R.id.loginbtn);
        progressBar1 = findViewById(R.id.progressBar1);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void clickable() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Signin();

            }
        });
    }

    private void Signin() {

        String user_name = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();

        if (user_name.isEmpty()) {
            edtUserName.setError("Please Enter Username");
            edtUserName.requestFocus();
        } else if (password.isEmpty()) {
            edtPassword.setError("Please Enter Password");
            edtPassword.requestFocus();
        } else {
//            progressBar1.setVisibility(View.VISIBLE);
            showLoadingDialog();
            Call<SignIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().SignIn(user_name, password);
            call.enqueue(new Callback<SignIn>() {
                @Override
                public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                    if (response.code() == 200) {
//                    progressBar1.setVisibility(View.GONE);
                        hideLoadingDialog();
                        if(response.body().getMsg().equals("Login Failed")){
                            Toast.makeText(LoginActivity.this, "Login failed. Userid or password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id = response.body().getManagerId().get(0).getId();
                            Paper.book().write("user_id", user_id);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }

                    } else if (response.code() == 404) {
                        hideLoadingDialog();

                    }
                }

                @Override
                public void onFailure(Call<SignIn> call, Throwable t) {
                    hideLoadingDialog();
//                    Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

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