package com.example.dtcmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.FragmentAdapter;
import com.example.dtcmanager.Adapter.ViewSubEmployeeAdapter;
import com.example.dtcmanager.Adapter.ViewVehicleLocationAdapter;
import com.example.dtcmanager.EmployeeDetailsFragments.AttendanceFragment;
import com.example.dtcmanager.EmployeeDetailsFragments.EmployeeDetailsFragment;
import com.example.dtcmanager.EmployeeDetailsFragments.ReportFragment;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.SubEmployee;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEmployeeDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_detail);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), ViewEmployeeDetailActivity.this);
        fragmentAdapter.addFrag(new EmployeeDetailsFragment(), "Details");
        fragmentAdapter.addFrag(new AttendanceFragment(), "Attendance");
        fragmentAdapter.addFrag(new ReportFragment(), "Report");

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}