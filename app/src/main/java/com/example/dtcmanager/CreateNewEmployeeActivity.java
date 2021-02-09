package com.example.dtcmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcmanager.Activities.AllEmplyeeActivity;
import com.example.dtcmanager.Activities.SelectAddressActivity;
import com.example.dtcmanager.Common.Common;
import com.example.dtcmanager.Common.FileUtils;
import com.example.dtcmanager.ModelClass.AddEmploye.AddEmployee;
import com.example.dtcmanager.ModelClass.EditEmloyee.EditEmployee;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.GetEmployeeDetail.GetEmployeeDetail;
import com.example.dtcmanager.ModelClass.UploadID.UploadID;
import com.example.dtcmanager.ModelClass.UploadJoingImage.UploadJoingImages;
import com.example.dtcmanager.ModelClass.UploadJoiningFile.UploadJoingFile;
import com.example.dtcmanager.ModelClass.UploadPassport.UploadPassport;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewEmployeeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageButton btnIdAttach, btnPassportAttach, btnContractAttach;
    ImageView btnattachPhoto;
    MultiSpinnerSearch employeeListSpinner;
    TextView txtidAttach, txtPassportAttach, txtContractAttach, txtAttachPhoto;
    EditText edtUsername, edtPasword, edtPostion, edtPhone, edtId, edtendDate,
            edtPassportnumber, edtPassortEndDate, edtJoinging, edtBasic, edtexpense,contract_end;
    Toolbar ChildProfiletoolbar;
    private static final int PICKFILE_RESULT_CODE = 2856;
    private static final int PICKPHOTO_RESULT_CODE = 2857;
    List<AllEmployee> allEmployeeList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    Button create_employee_btn;
    int check, requestCode, code, datecheck;
    String originCheck, id;
    String encodePDF;
    String manager_id;
    Uri imageUri, imageUri1, imageUri2, imageUri3;
    Calendar now;
    private DatePickerDialog dpd;
    ProgressBar progressBar1;
    FileUtils fileUtils;
    AlertDialog loadingDialog;
    CheckBox OverTime, WithoutOverTime;
    int over_time = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_employee);
        Paper.init(this);

        create_employee_btn = findViewById(R.id.create_employee_btn);
        originCheck = getIntent().getStringExtra("orign");
        manager_id = Paper.book().read("user_id");

        intView();
        Clickble();
        AllEmploye();

        if (originCheck.equals("AddEmployee")) {

            create_employee_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_name = edtUsername.getText().toString();
                    checkDuplicateEmployee(manager_id,user_name);
                }
            });

        } else if (originCheck.equals("EditEmployee")) {
            id = getIntent().getStringExtra("id");
//            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
            GetEmployeeDetail(id);
            create_employee_btn.setText("Update Employee");
            create_employee_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditEmployee(id);
                }
            });
        }
    }

    private void intView() {
        employeeListSpinner = findViewById(R.id.employeeListSpinner);
        btnIdAttach = findViewById(R.id.btnIdAttach);
        btnPassportAttach = findViewById(R.id.btnPassportAttach);
        btnContractAttach = findViewById(R.id.btnContractAttach);
        btnattachPhoto = findViewById(R.id.btnattachPhoto);
        txtidAttach = findViewById(R.id.txtidAttach);
        txtPassportAttach = findViewById(R.id.txtPassportAttach);
        txtContractAttach = findViewById(R.id.txtContractAttach);
        txtAttachPhoto = findViewById(R.id.txtAttachPhoto);
        edtUsername = findViewById(R.id.edtUsername);
        edtPasword = findViewById(R.id.edtPasword);
        edtPostion = findViewById(R.id.edtPostion);
        edtPhone = findViewById(R.id.edtPhone);
        edtId = findViewById(R.id.edtId);
        edtendDate = findViewById(R.id.edtendDate);
        edtPassportnumber = findViewById(R.id.edtPassportnumber);
        edtPassortEndDate = findViewById(R.id.edtPassortEndDate);
        edtJoinging = findViewById(R.id.edtJoinging);
        edtBasic = findViewById(R.id.edtBasic);
        edtexpense = findViewById(R.id.edtexpense);
        contract_end=findViewById(R.id.edtContractDate);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar1 = findViewById(R.id.progressBar1);
        OverTime = findViewById(R.id.OverTime);
        WithoutOverTime = findViewById(R.id.WithoutOverTime);

    }

    private void Clickble() {
        btnIdAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                requestCode = 2856;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
        btnPassportAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 2;
                requestCode = 2856;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
        btnContractAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 3;
                requestCode = 2856;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
        btnattachPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCode = 8605;
                verifyPermissions();
            }
        });
        edtendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datecheck = 1;
                DatePicker();

            }
        });

        edtPassortEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datecheck = 2;
                DatePicker();

            }
        });
        edtJoinging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datecheck = 3;
                DatePicker();

            }
        });

        contract_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datecheck = 4;
                DatePicker();
            }
        });
    }

    private void verifyPermissions() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            openGallery();

        } else {
            ActivityCompat.requestPermissions(CreateNewEmployeeActivity.this, permissions, 2857);

        }
    }

    private void checkDuplicateEmployee(String m_id, String user_name) {
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(m_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
                    allEmployeeList = response.body().getAllEmployees();
                    if(allEmployeeList.size()>0){
                        for (int i=0;i<allEmployeeList.size();i++){
                            if(allEmployeeList.get(i).getUserName().equals(user_name)){
                                Toast.makeText(CreateNewEmployeeActivity.this, "Same user name is already exist. Try with a new one..", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AddEmploye();
                            }
                            break;
                         }
                    }
                    else{
                        AddEmploye();
                    }

                }
                else if (response.code() == 404) {
                }
            }

            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
//                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void AddEmploye() {

        String user_name = edtUsername.getText().toString();
        String password = edtPasword.getText().toString();
        String position = edtPostion.getText().toString();
        String phone = edtPhone.getText().toString();
        String unique_id = edtId.getText().toString();
        String end_date = edtendDate.getText().toString();
        String passport_no = edtPassportnumber.getText().toString();
        String passport_end_date = edtPassortEndDate.getText().toString();
        String joining_date = edtJoinging.getText().toString();
        String contract_end_date= contract_end.getText().toString();
        String basic_salary = edtBasic.getText().toString();
        String expenses = edtexpense.getText().toString();

        if (user_name.isEmpty()) {
            edtUsername.setError("Please enter UserName");
            edtUsername.requestFocus();
        } else if (password.isEmpty()) {
            edtPasword.setError("Please enter Password");
            edtPasword.requestFocus();
        } else if (position.isEmpty()) {
            edtPostion.setError("Please enter Position");
            edtPostion.requestFocus();
        } else if (phone.isEmpty()) {
            edtPhone.setError("Please enter Contact Details");
            edtPhone.requestFocus();
        } else if (unique_id.isEmpty()) {
            edtId.setError("Please enter Iqama or DTC Id");
            edtId.requestFocus();
        } else if (end_date.isEmpty()) {
            edtendDate.setError("Please enter End Date");
            edtendDate.requestFocus();
        } else if (passport_no.isEmpty()) {
            edtPassportnumber.setError("Please enter Passport No");
            edtPassportnumber.requestFocus();
        } else if (passport_end_date.isEmpty()) {
            edtPassortEndDate.setError("Please enter Passport End Date");
            edtPassortEndDate.requestFocus();
        } else if (joining_date.isEmpty()) {
            edtJoinging.setError("Please enter Joining Date");
            edtJoinging.requestFocus();
        }else if (contract_end_date.isEmpty()) {
            edtJoinging.setError("Please enter Contract End Date");
            edtJoinging.requestFocus();
        } else if (basic_salary.isEmpty()) {
            edtBasic.setError("Please enter Basic Salary");
            edtBasic.requestFocus();
        } else if (expenses.isEmpty()) {
            edtexpense.setError("Please enter Allowances");
            edtexpense.requestFocus();
        } /*else if (employeeList.size() < 0) {
            Toast.makeText(this, "Please Select Sub Employee", Toast.LENGTH_SHORT).show();
        }*/ else if (imageUri == null) {
            Toast.makeText(this, "Please Add Image", Toast.LENGTH_SHORT).show();
        } else if (imageUri1 == null) {
            Toast.makeText(this, "Please Add ID File", Toast.LENGTH_SHORT).show();
        } else if (imageUri2 == null) {
            Toast.makeText(this, "Please Add Passport File", Toast.LENGTH_SHORT).show();
        } else if (imageUri3 == null) {
            Toast.makeText(this, "Please Add Joining File", Toast.LENGTH_SHORT).show();
        } else if (WithoutOverTime.isChecked()) {
            over_time = 0;
        } else {
//            List<String> sub_emp_id = new ArrayList<>();
//            sub_emp_id = Common.childsList;
            showLoadingDialog();

            Call<AddEmployee> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddEmployee(manager_id, user_name, password, position,
                    phone, basic_salary, expenses, String.valueOf(over_time), unique_id, end_date, passport_no,
                    passport_end_date, joining_date,contract_end_date, employeeList, "nullimage", "nulltoken",
                    "nullfile", "nulljoin", "nullpass", "nullimage","false");

            call.enqueue(new Callback<AddEmployee>() {
                @Override
                public void onResponse(Call<AddEmployee> call, Response<AddEmployee> response) {
                    if (response.code() == 200) {
//                        progressBar1.setVisibility(View.GONE);
//                        hideLoadingDialog();
                        int id = response.body().getAddEmployeeId();
                        String emp_id = String.valueOf(id);
                        UPloadId(emp_id);
                        //Toast.makeText(CreateNewEmployeeActivity.this, "Employee Add", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 404) {
//                        progressBar1.setVisibility(View.GONE);
                        hideLoadingDialog();
                        Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddEmployee> call, Throwable t) {
//                    progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void GetEmployeeDetail(String id) {
        showLoadingDialog();

        Call<EmployeeDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().EmployeeDetail(id);
        call.enqueue(new Callback<EmployeeDetail>() {
            @Override
            public void onResponse(Call<EmployeeDetail> call, Response<EmployeeDetail> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    String user_name = response.body().getEmployeeDetail().get(0).getUserName();
                    String password = response.body().getEmployeeDetail().get(0).getPassword();
                    String position = response.body().getEmployeeDetail().get(0).getPosition();
                    String phone = response.body().getEmployeeDetail().get(0).getPhone();
                    String unique_id = response.body().getEmployeeDetail().get(0).getEmployeeId();
                    String endDate = response.body().getEmployeeDetail().get(0).getEndDate();
                    String passportnumber = response.body().getEmployeeDetail().get(0).getPassportNo();
                    String passportEndDate = response.body().getEmployeeDetail().get(0).getPassportEndDate();
                    String joining_date = response.body().getEmployeeDetail().get(0).getJoiningDate();
                    String contract_end_date=response.body().getEmployeeDetail().get(0).getContract_end_date();
                    String basic_salary = response.body().getEmployeeDetail().get(0).getBasicSalary();
                    String expenses = response.body().getEmployeeDetail().get(0).getExpenses();
                    if (response.body().getEmployeeDetail().get(0).getOverTime().equals("1")) {
                        OverTime.setChecked(true);
                        WithoutOverTime.setChecked(false);
                    }
                    if (!response.body().getEmployeeDetail().get(0).getOverTime().equals("1")) {
                        WithoutOverTime.setChecked(true);
                        OverTime.setChecked(false);
                    }
                    edtUsername.setText(user_name);
                    edtPasword.setText(password);
                    edtPostion.setText(position);
                    edtPhone.setText(phone);
                    edtId.setText(unique_id);
                    edtendDate.setText(endDate);
                    edtPassportnumber.setText(passportEndDate);
                    edtPassportnumber.setText(passportnumber);
                    edtJoinging.setText(joining_date);
                    contract_end.setText(contract_end_date);
                    edtBasic.setText(basic_salary);
                    edtexpense.setText(expenses);
                    edtPassortEndDate.setText(passportEndDate);

                    edtUsername.setEnabled(false);

                    if (response.body().getEmployeeDetail().get(0).getSubEmployee().size() > 0) {
                        for (int i = 0; i < response.body().getEmployeeDetail().get(0).getSubEmployee().size(); i++) {
                            employeeList.add(response.body().getEmployeeDetail().get(0).getSubEmployee().get(i).getId());
                        }

                        AllEmploye();
                    }


                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(CreateNewEmployeeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(CreateNewEmployeeActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<EmployeeDetail> call, Throwable t) {
                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void EditEmployee(String id) {
//        edtUsername = findViewById(R.id.edtUsername);
//        edtPasword = findViewById(R.id.edtPasword);
//        edtPostion = findViewById(R.id.edtPostion);
//        edtPhone = findViewById(R.id.edtPhone);
//        edtId = findViewById(R.id.edtId);
//        edtendDate = findViewById(R.id.edtendDate);
//        edtPassportnumber = findViewById(R.id.edtPassportnumber);
//        edtPassortEndDate = findViewById(R.id.edtPassortEndDate);
//        edtJoinging = findViewById(R.id.edtJoinging);
//        edtBasic = findViewById(R.id.edtBasic);
//        edtexpense = findViewById(R.id.edtexpense);
        String user_name = edtUsername.getText().toString();
        String password = edtPasword.getText().toString();
        String position = edtPostion.getText().toString();
        String phone = edtPhone.getText().toString();
        String unique_id = edtId.getText().toString();
        String end_date = edtendDate.getText().toString();
        String passport_no = edtPassportnumber.getText().toString();
        String passport_end_date = edtPassortEndDate.getText().toString();
        String joining_date = edtJoinging.getText().toString();
        String contract_end_date= contract_end.getText().toString();
        String basic_salary = edtBasic.getText().toString();
        String expenses = edtexpense.getText().toString();

        if (user_name.isEmpty()) {
            edtUsername.setError("Please enter UserName");
            edtUsername.requestFocus();
        } else if (password.isEmpty()) {
            edtPasword.setError("Please enter Password");
            edtPasword.requestFocus();
        } else if (position.isEmpty()) {
            edtPostion.setError("Please enter Password");
            edtPostion.requestFocus();
        } else if (phone.isEmpty()) {
            edtPhone.setError("Please enter Password");
            edtPhone.requestFocus();
        } else if (unique_id.isEmpty()) {
            edtId.setError("Please enter Password");
            edtId.requestFocus();
        } else if (end_date.isEmpty()) {
            edtendDate.setError("Please enter Password");
            edtendDate.requestFocus();
        } else if (passport_no.isEmpty()) {
            edtPassportnumber.setError("Please enter Password");
            edtPassportnumber.requestFocus();
        } else if (passport_end_date.isEmpty()) {
            edtPassortEndDate.setError("Please enter Password");
            edtPassortEndDate.requestFocus();
        } else if (joining_date.isEmpty()) {
            edtJoinging.setError("Please enter Password");
            edtJoinging.requestFocus();
        } else if (basic_salary.isEmpty()) {
            edtBasic.setError("Please enter Password");
            edtBasic.requestFocus();
        } else if (expenses.isEmpty()) {
            edtexpense.setError("Please enter Password");
            edtexpense.requestFocus();
        } else if (employeeList.size() < 0) {
            Toast.makeText(this, "Please Select  Sub Employee", Toast.LENGTH_SHORT).show();
        }
//        else if (imageUri == null) {
//            Toast.makeText(this, "Please Add Image", Toast.LENGTH_SHORT).show();
//        } else if (imageUri1 == null) {
//            Toast.makeText(this, "Please Add ID File", Toast.LENGTH_SHORT).show();
//        } else if (imageUri2 == null) {
//            Toast.makeText(this, "Please Add Passport File", Toast.LENGTH_SHORT).show();
//        } else if (imageUri3 == null) {
//            Toast.makeText(this, "Please Add Joining File", Toast.LENGTH_SHORT).show();
//
//        }

        else {
//            List<String> sub_emp_id = new ArrayList<>();
//            sub_emp_id = Common.childsList;
//            progressBar1.setVisibility(View.VISIBLE);
            showLoadingDialog();
            Call<EditEmployee> call = RetrofitClientClass.getInstance().getInterfaceInstance().Editemployee(id, user_name, password, position,
                    phone, basic_salary, expenses, "1", unique_id, end_date, passport_no, passport_end_date, joining_date,contract_end_date, employeeList);
            call.enqueue(new Callback<EditEmployee>() {
                @Override
                public void onResponse(Call<EditEmployee> call, Response<EditEmployee> response) {
                    if (response.code() == 200) {

                        String emp_id = String.valueOf(id);
                        if (imageUri1 != null) {
                            UPloadId(emp_id);
                        } else if (imageUri2 != null) {
                            UploadPassport(emp_id);
                        } else if (imageUri3 != null) {
                            UploadJoiningfile(emp_id);
                        } else if (imageUri != null) {
                            UploadJoiningImage(emp_id);
                        }
                        hideLoadingDialog();
                        Toast.makeText(CreateNewEmployeeActivity.this, "Employee Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (response.code() == 404) {
                        hideLoadingDialog();
                        Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditEmployee> call, Throwable t) {
                    hideLoadingDialog();

//                    Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private void UPloadId(String emp_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri1));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri1)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("files", file.getName(), image);
//        progressBar1.setVisibility(View.VISIBLE);
        Call<UploadID> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadId(emp_id, files);
        call.enqueue(new Callback<UploadID>() {
            @Override
            public void onResponse(Call<UploadID> call, Response<UploadID> response) {
                if (response.code() == 200) {
//                    progressBar1.setVisibility(View.GONE);
                    //hideLoadingDialog();
                    if (originCheck.equals("AddEmployee")) {
                        UploadPassport(emp_id);
                    }

                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UploadID> call, Throwable t) {
//                progressBar1.setVisibility(View.GONE);
                hideLoadingDialog();
                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void UploadPassport(String emp_id) {

        File file = new File(fileUtils.getRealPath(this, imageUri2));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri2)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("files", file.getName(), image);
//        progressBar1.setVisibility(View.VISIBLE);
        Call<UploadPassport> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadPassport(emp_id, files);
        call.enqueue(new Callback<UploadPassport>() {
            @Override
            public void onResponse(Call<UploadPassport> call, Response<UploadPassport> response) {
                if (response.code() == 200) {
                    //progressBar1.setVisibility(View.GONE);

                    if (originCheck.equals("AddEmployee")) {
                        //hideLoadingDialog();
                        UploadJoiningfile(emp_id);
                    }

                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadPassport> call, Throwable t) {
//                progressBar1.setVisibility(View.GONE);
                hideLoadingDialog();
                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void UploadJoiningfile(String emp_id) {

        try{
            File file = new File(fileUtils.getRealPath(this, imageUri3));
            String filename= file.getName();
            Call<UploadJoingFile> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadJoiningfile(emp_id, filename,encodePDF);
            call.enqueue(new Callback<UploadJoingFile>() {
                @Override
                public void onResponse(Call<UploadJoingFile> call, Response<UploadJoingFile> response) {
                    if (response.code() == 200) {
                        //progressBar1.setVisibility(View.GONE);
                        if (originCheck.equals("AddEmployee")) {
                            //hideLoadingDialog();
                            UploadJoiningImage(emp_id);
                        }
                    } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
                        hideLoadingDialog();
                        Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UploadJoingFile> call, Throwable t) {
//                progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch(Exception e){
            hideLoadingDialog();
            Toast.makeText(this, "File should be selected from phone directory", Toast.LENGTH_SHORT).show();
        }

    }

    private void UploadJoiningImage(String emp_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        MultipartBody.Part picture = MultipartBody.Part.createFormData("picture", file.getName(), image);
//        progressBar1.setVisibility(View.VISIBLE);
        Call<UploadJoingImages> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadJoingImage(emp_id, picture);
        call.enqueue(new Callback<UploadJoingImages>() {
            @Override
            public void onResponse(Call<UploadJoingImages> call, Response<UploadJoingImages> response) {
                if (response.code() == 200) {
                    progressBar1.setVisibility(View.GONE);
                    if (originCheck.equals("AddEmployee")) {
                        hideLoadingDialog();
                        Toast.makeText(CreateNewEmployeeActivity.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(CreateNewEmployeeActivity.this, "Something Wrong while adding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadJoingImages> call, Throwable t) {
//                progressBar1.setVisibility(View.GONE);
                hideLoadingDialog();
                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {

            openGallery();

        } else {
            Toast.makeText(this, "You have to allow these permission in order to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
//        Intent intent= new Intent(CreateNewEmployeeActivity.this,AllEmplyeeActivity.class);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICKPHOTO_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE) {


            if (check == 1) {
                if (data != null) {
                    String FilePath = data.getData().getPath();
                    imageUri1 = data.getData();
                    txtidAttach.setText("File Attached");
                    txtidAttach.setTextColor(Color.parseColor("#68F965"));
                } else {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (check == 2) {
                if (data != null) {
                    String FilePath = data.getData().getPath();
                    imageUri2 = data.getData();
                    txtPassportAttach.setText("File Attached");
                    txtPassportAttach.setTextColor(Color.parseColor("#68F965"));
                } else {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (check == 3) {
                if (data != null) {
                    imageUri3 = data.getData();
                    encodePDF= getStringPdf(imageUri3);
                    txtContractAttach.setText("File Attached");
                    txtContractAttach.setTextColor(Color.parseColor("#68F965"));
                } else {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == PICKPHOTO_RESULT_CODE) {


            if (data != null) {

                imageUri = data.getData();
                btnattachPhoto.setImageURI(imageUri);

            }

        } else if (resultCode == Activity.RESULT_CANCELED) {

            Toast.makeText(CreateNewEmployeeActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            btnattachPhoto.setVisibility(View.GONE);

        }

    }

    public String getStringPdf (Uri filepath){
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream =  getContentResolver().openInputStream(filepath);

            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
    }


    public static String getRealPath(Context context, Uri fileUri) {

        String realPath;
        // SDK < API11
        if (Build.VERSION.SDK_INT < 11) {
            realPath = getRealPathFromURI_BelowAPI11(context, fileUri);
        }
        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19) {
            realPath = getRealPathFromURI_API11to18(context, fileUri);
        }
        // SDK > 19 (Android 4.4) and up
        else {
            realPath = getRealPathFromURI_API19(context, fileUri);
        }
        return realPath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = 0;
        String result = "";
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
            return result;
        }
        return result;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)) {
                    if (split.length > 1) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    } else {
                        return Environment.getExternalStorageDirectory() + "/";
                    }
                    // This is for checking SD Card
                } else {
                    return "storage" + "/" + docId.replace(":", "/");
                }

            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                String fileName = getFilePath(context, uri);
                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                }

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void AllEmploye() {
//        progressBar1.setVisibility(View.VISIBLE);
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(manager_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
                    allEmployeeList = response.body().getAllEmployees();
                    if (allEmployeeList.size() > 0) {
                        employeeListSpinner.setEnabled(true);
                    } else {
                        employeeListSpinner.setEnabled(true);
                        Toast.makeText(CreateNewEmployeeActivity.this, "Please Add Employee", Toast.LENGTH_SHORT).show();
                    }
                    setLocationInSpinner(allEmployeeList);
                } else if (response.code() == 404) {

                }
            }


            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
//                Toast.makeText(CreateNewEmployeeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setLocationInSpinner(List<AllEmployee> allEmployeeList) {
        List<KeyPairBoolData> data = new ArrayList<>();

        for (int i = 0; i < allEmployeeList.size(); i++) {

            KeyPairBoolData boolData = new KeyPairBoolData();
            boolData.setId(Integer.parseInt(allEmployeeList.get(i).getId()));
            boolData.setName(allEmployeeList.get(i).getUserName());
            boolData.setSelected(false);


            for (int j = 0; j < employeeList.size(); j++) {
                if (allEmployeeList.get(i).getId().equals(employeeList.get(j))) {

                    boolData.setSelected(true);
                }
            }

            data.add(boolData);

        }


        employeeListSpinner.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                employeeList.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    employeeList.add(String.valueOf(selectedItems.get(i).getId()));
//                    Toast.makeText(CreateNewProjectActivity.this, ""+employeeList, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void  DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CreateNewEmployeeActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);
        if (datecheck == 1) {
            dpd.setMinDate(now);

        } else if (datecheck == 2) {
            dpd.setMinDate(now);
        }

        dpd.setThemeDark(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        Calendar currentDate = Calendar.getInstance();
        Date currentDateTime = currentDate.getTime();

        Date selectedDate = now.getTime();

//        if (selectedDate.equals(currentDateTime)) {
//            current = true;
//        } else {
//            current = false;
//        }

        if (datecheck == 1) {
            edtendDate.setText(sdf.format(selectedDate));

        } else if (datecheck == 2) {
            edtPassortEndDate.setText(sdf.format(selectedDate));
        } else if (datecheck == 3) {
            edtJoinging.setText(sdf.format(selectedDate));
        } else if(datecheck == 4){
            contract_end.setText(sdf.format(selectedDate));
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