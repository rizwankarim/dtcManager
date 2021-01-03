package com.example.dtcmanager.Activities;

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

import com.example.dtcmanager.Adapter.EmployeeListAdapter;
import com.example.dtcmanager.CreateNewEmployeeActivity;
import com.example.dtcmanager.Interface.DeleteEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.RemoveEmployee.RemoveEmployee;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEmplyeeActivity extends AppCompatActivity {
    List<AllEmployee> allEmployeeList = new ArrayList<>();
    RecyclerView recyclerViewEmployees;
    ProgressBar progressBar1;
    String manager_id;
    AlertDialog loadingDialog;
    ImageButton addemployee;
    Toolbar ChildProfiletoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_emplyee);
        Paper.init(this);
        manager_id = Paper.book().read("user_id");
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getInfo();
        Clickable();

    }

    private void initView() {
        addemployee = findViewById(R.id.addemployee);
        progressBar1 = findViewById(R.id.progressBar1);
        recyclerViewEmployees = findViewById(R.id.addemployee_recycler);
        recyclerViewEmployees.setHasFixedSize(false);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
    private void Clickable() {
        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllEmplyeeActivity.this, CreateNewEmployeeActivity.class);
                intent.putExtra("orign", "AddEmployee");
                startActivity(intent);
            }
        });
    }
    private void getInfo() {
        showLoadingDialog();
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(manager_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    allEmployeeList = response.body().getAllEmployees();
                    EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(AllEmplyeeActivity.this, allEmployeeList, new DeleteEmployee() {
                        @Override
                        public void DeleteEmployee(String id) {
                            DeleteEmoployee(id);
                        }
                    });
                    recyclerViewEmployees.setAdapter(employeeListAdapter);
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AllEmplyeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
                hideLoadingDialog();
//                Toast.makeText(AllEmplyeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void DeleteEmoployee(String id) {
        showLoadingDialog();
        Call<RemoveEmployee> call = RetrofitClientClass.getInstance().getInterfaceInstance().RemoveEmployee(id);
        call.enqueue(new Callback<RemoveEmployee>() {
            @Override
            public void onResponse(Call<RemoveEmployee> call, Response<RemoveEmployee> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    allEmployeeList.clear();
                    getInfo();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AllEmplyeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RemoveEmployee> call, Throwable t) {
                hideLoadingDialog();
//                Toast.makeText(AllEmplyeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}