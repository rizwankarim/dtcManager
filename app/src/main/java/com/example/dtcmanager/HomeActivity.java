package com.example.dtcmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dtcmanager.Fragments.EmployeesFragment;
import com.example.dtcmanager.Fragments.HomeFragment;
import com.example.dtcmanager.Fragments.MovementsFragment;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    LinearLayout notificationbtn, vactionbtn, employeesbtn, movementsbtn, homebtn;
    ImageView notificationIcon, vactionIcon, employeesIcon, movementsIcon, homeIcon;
    TextView txtnotifcation, txtvaction, txtemployees, txtmovments, txthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initviews();
        clickevents();
    }
    private void initviews() {

        notificationbtn = findViewById(R.id.noti_btn);
        vactionbtn = findViewById(R.id.vaction_btn);
        employeesbtn = findViewById(R.id.employees_btn);
        movementsbtn = findViewById(R.id.movment_btn);
        homebtn = findViewById(R.id.home_btn);
//        icons
        notificationIcon = findViewById(R.id.noti_icon);
        vactionIcon = findViewById(R.id.vac_icon);
        employeesIcon = findViewById(R.id.employee_icon);
        movementsIcon = findViewById(R.id.movment_icon);
        homeIcon = findViewById(R.id.home_icon);

//        textviews

        txtnotifcation = findViewById(R.id.txtnoti);
        txtvaction = findViewById(R.id.txtvac);
        txtemployees = findViewById(R.id.txtemployees);
        txtmovments = findViewById(R.id.txtmovments);
        txthome = findViewById(R.id.txthome);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.i("Hello", "getInstanceId failed", task.getException());
                            return;
                        }
                        Log.i("Hello", "onComplete: "+task.getResult().getToken());
                        UpdateToken(task.getResult().getToken());
                    }
                });

    }

    private void clickevents() {

        Fragment newFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.SRC_IN);
                employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);


                txthome.setVisibility(View.VISIBLE);
                txtemployees.setVisibility(View.GONE);
                txtmovments.setVisibility(View.GONE);
                txtnotifcation.setVisibility(View.GONE);
                txtvaction.setVisibility(View.GONE);


            }
        });

        employeesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEmployeeTab();
            }
        });

        movementsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new MovementsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.SRC_IN);
                notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);


                txthome.setVisibility(View.GONE);
                txtemployees.setVisibility(View.GONE);
                txtmovments.setVisibility(View.VISIBLE);
                txtnotifcation.setVisibility(View.GONE);
                txtvaction.setVisibility(View.GONE);

            }
        });

        vactionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new VacationsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.SRC_IN);


                txthome.setVisibility(View.GONE);
                txtemployees.setVisibility(View.GONE);
                txtmovments.setVisibility(View.GONE);
                txtnotifcation.setVisibility(View.GONE);
                txtvaction.setVisibility(View.VISIBLE);
            }
        });

        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new NotificationsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
                notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.SRC_IN);
                vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);


                txthome.setVisibility(View.GONE);
                txtemployees.setVisibility(View.GONE);
                txtmovments.setVisibility(View.GONE);
                txtnotifcation.setVisibility(View.VISIBLE);
                txtvaction.setVisibility(View.GONE);
            }
        });
    }

    public void showEmployeeTab() {

        Fragment newFragment = new EmployeesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorAppMain), android.graphics.PorterDuff.Mode.SRC_IN);


        txthome.setVisibility(View.GONE);
        txtemployees.setVisibility(View.VISIBLE);
        txtmovments.setVisibility(View.GONE);
        txtnotifcation.setVisibility(View.GONE);
        txtvaction.setVisibility(View.GONE);

    }


    private void UpdateToken(String token) {

        String userId = Paper.book().read("user_id");

        Call<ResponseBody> call = RetrofitClientClass.getInstance().getInterfaceInstance().updateToken(userId
                ,token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();

                if (code == 200)
                {
                    Log.i("OK", "onResponse: "+"Success");
                }
                else if (code == 400)
                {
                    Toast.makeText(HomeActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(HomeActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}