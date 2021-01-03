package com.example.dtcmanager.EmployeeDetailsFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.EmployeeAttendanceAdapter;
import com.example.dtcmanager.Comon.Comon;
import com.example.dtcmanager.ModelClass.EmployeeAttendanceModel.EmployeeAttendenceReport;
import com.example.dtcmanager.ModelClass.EmployeeAttendanceModel.GetEmployeeAttendance;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends Fragment {

    TextView noData;
    RecyclerView rvAttendance;
    List<EmployeeAttendenceReport> attendanceReports = new ArrayList<>();

    public AttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noData = view.findViewById(R.id.noData);

        rvAttendance = view.findViewById(R.id.rvAttendance);
        rvAttendance.setHasFixedSize(true);
        rvAttendance.setLayoutManager(new LinearLayoutManager(requireContext()));

        Activity activity = getActivity();

        if(activity != null){

            getEmployeeAttendance();

        }
    }

    private void getEmployeeAttendance() {

        Log.i("TAG", "getEmployeeAttendance: " + Comon.employeeId);

        Call<GetEmployeeAttendance> call = RetrofitClientClass.getInstance().getInterfaceInstance().employeeAttendance(Comon.employeeId);

        call.enqueue(new Callback<GetEmployeeAttendance>() {
            @Override
            public void onResponse(Call<GetEmployeeAttendance> call, Response<GetEmployeeAttendance> response) {

                int code = response.code();

                if (code == 200)
                {
                    attendanceReports = response.body().getEmployeeAttendenceReport();

                    if (attendanceReports.size() > 0)
                    {
                        noData.setVisibility(View.GONE);
                        rvAttendance.setVisibility(View.VISIBLE);

                        EmployeeAttendanceAdapter adapter = new EmployeeAttendanceAdapter(requireContext(),attendanceReports);
                        rvAttendance.setAdapter(adapter);
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        rvAttendance.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeAttendance> call, Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}