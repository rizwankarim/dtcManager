package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dtcmanager.Adapter.BookingDetailFragmentsAdapter;
import com.example.dtcmanager.TabsProfile.All_for_Profile_Fragment;
import com.example.dtcmanager.TabsProfile.Overtime_for_ProfileFragment;
import com.example.dtcmanager.TabsProfile.Permanence_for_ProfileFragment;
import com.example.dtcmanager.TabsProfile.Vacation_for_ProfileFragment;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {
    TabLayout TabLyout;
    ViewPager Viewpager;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TabLyout = findViewById(R.id.TabLyout);
        Viewpager = findViewById(R.id.Viewpager);
        backbtn = findViewById(R.id.back_profile);

        TabLyout.setupWithViewPager(Viewpager);

        setUpViewpager(Viewpager);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setUpViewpager(ViewPager viewPagerExplore) {

        BookingDetailFragmentsAdapter adapter = new BookingDetailFragmentsAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new All_for_Profile_Fragment(), "All");
        adapter.addFragment(new Permanence_for_ProfileFragment(), "Permance");
        adapter.addFragment(new Vacation_for_ProfileFragment(), "Vaction");
        adapter.addFragment(new Overtime_for_ProfileFragment(),"OverTime");
        viewPagerExplore.setAdapter(adapter);
    }
}