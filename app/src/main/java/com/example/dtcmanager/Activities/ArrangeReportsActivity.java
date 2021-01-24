package com.example.dtcmanager.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.EmployeeReportsAdapter;
import com.example.dtcmanager.Adapter.GetAllEmployeeReportbyDate;
import com.example.dtcmanager.Adapter.GetAllEmployeeRports;
import com.example.dtcmanager.Comon.Comon;
import com.example.dtcmanager.CreateNewEmployeeActivity;
import com.example.dtcmanager.EmployeeDetailsFragments.ReportFragment;
import com.example.dtcmanager.ModelClass.EmployeeDailyReportModel.EmployeeDailyReport;
import com.example.dtcmanager.ModelClass.EmployeeDailyReportModel.GetEmployeeDailyReport;
import com.example.dtcmanager.ModelClass.GetDailyReport.GetDailyReport;
import com.example.dtcmanager.ModelClass.GetEmployeeReportByDate.EmployeeDailyReportByDate;
import com.example.dtcmanager.ModelClass.GetEmployeeReportByDate.GetDailyReportByDate;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArrangeReportsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView noData;
    RecyclerView rvReports,rvReportsFilter;
    EditText edtDate,edtDate1,edtDate2;
    private DatePickerDialog dpd;
    Calendar now;
    List<EmployeeDailyReport> employeeDailyReports = new ArrayList<>();
    List<EmployeeDailyReportByDate> employeeDailyReportByDateList = new ArrayList<>();
    List<com.example.dtcmanager.ModelClass.GetDailyReport.EmployeeDailyReport> employeeDailyReportList = new ArrayList<>();
    RelativeLayout Date1,Date2;
    Button SelctSechDateModule;

    RelativeLayout daily;
    int check;
    LinearLayout DateRange;
    Date date;
    Toolbar ChildProfiletoolbar;
    String manager_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_reports);
        Paper.init(this);
        initViews();
        Calendar currentDate = Calendar.getInstance();
        date = currentDate.getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String current_date = sdp.format(date);
        edtDate.setText(current_date);

        clickAble();
    }

    public void initViews(){
        noData =  findViewById(R.id.noData);
        edtDate =  findViewById(R.id.edtDate);
        edtDate1 =  findViewById(R.id.edtDate1);
        edtDate2 =  findViewById(R.id.edtDate2);
        rvReports =  findViewById(R.id.rvReports);
        rvReportsFilter =  findViewById(R.id.rvReportsFilter);
//        SelctSechDateModule =  findViewById(R.id.SelctSechDateModule);
        DateRange =  findViewById(R.id.DateRange);
        daily =  findViewById(R.id.daily);
        rvReports.setHasFixedSize(true);
        rvReports.setLayoutManager(new LinearLayoutManager(this));
        rvReportsFilter.setHasFixedSize(true);
        rvReportsFilter.setLayoutManager(new LinearLayoutManager(this));
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        manager_id = Paper.book().read("user_id");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void clickAble() {
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 1;
                DatePicker();

            }
        });
        edtDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 2;
                DatePicker();

            }
        });
        edtDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = 3;
                DatePicker();

            }
        });

    }

    private void getEmployeeAttendance() {

        Log.i("TAG", "getEmployeeAttendance: " + Comon.employeeId);
        String date = edtDate.getText().toString();
        Call<GetDailyReport> call = RetrofitClientClass.getInstance().getInterfaceInstance()
                .GetDailyReport(manager_id, date);

        call.enqueue(new Callback<GetDailyReport>() {
            @Override
            public void onResponse(Call<GetDailyReport> call, Response<GetDailyReport> response) {

                int code = response.code();

                if (code == 200)
                {
                    employeeDailyReportList = response.body().getEmployeeDailyReport();

                    if (employeeDailyReportList.size() > 0)
                    {

                        Log.i("TAG", "onResponse: " + "Single Date Api");
                        noData.setVisibility(View.GONE);
                        rvReports.setVisibility(View.VISIBLE);

                        GetAllEmployeeRports adapter = new GetAllEmployeeRports(ArrangeReportsActivity.this,
                                employeeDailyReportList);
                        rvReports.setAdapter(adapter);
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        rvReports.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(ArrangeReportsActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDailyReport> call, Throwable t) {

                Toast.makeText(ArrangeReportsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getEmployeeAttendance();
    }

    private void DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                ArrangeReportsActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);

        if (check == 1) {
            dpd.setMaxDate(now);

        } else if (check == 2) {
            dpd.setMaxDate(now);
        } else if (check == 3) {
            dpd.setMaxDate(now);
        }

        dpd.setThemeDark(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        Calendar currentDate = Calendar.getInstance();
        Date currentDateTime = currentDate.getTime();

        date = now.getTime();
        Date selectedDate = now.getTime();

//        if (selectedDate.equals(currentDateTime)) {
//            current = true;
//        } else {
//            current = false;
//        }


        if (check == 1) {
            edtDate.setText(sdf.format(date));

        } else if (check == 2) {
            edtDate1.setText(sdf.format(date));
        } else if (check == 3) {
            edtDate2.setText(sdf.format(date));
        }

        String date = edtDate.getText().toString();
        if(!date.isEmpty())
        {
            getEmployeeAttendance();
        }
        String datefirs = edtDate1.getText().toString();
        String datelast = edtDate2.getText().toString();


        if((!datefirs.isEmpty()) && (!datelast.isEmpty())) {
            getEmployeeReportByDate();
        }
    }

    private void getEmployeeReportByDate() {

        String from_date = edtDate1.getText().toString();
        String to_date = edtDate2.getText().toString();
        Call<GetDailyReportByDate> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetDailyReportByDate(manager_id,from_date,to_date);
        call.enqueue(new Callback<GetDailyReportByDate>() {
            @Override
            public void onResponse(Call<GetDailyReportByDate> call, Response<GetDailyReportByDate> response) {
                int code = response.code();

                if (code == 200)
                {
                    employeeDailyReportByDateList = response.body().getEmployeeDailyReport();

                    if (employeeDailyReportByDateList.size() > 0)
                    {
                        noData.setVisibility(View.GONE);
                        rvReports.setVisibility(View.VISIBLE);

                        GetAllEmployeeReportbyDate adapter = new GetAllEmployeeReportbyDate(ArrangeReportsActivity.this,
                                employeeDailyReportByDateList);
                        rvReports.setAdapter(adapter);
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        rvReports.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(ArrangeReportsActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDailyReportByDate> call, Throwable t) {

                Toast.makeText(ArrangeReportsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}