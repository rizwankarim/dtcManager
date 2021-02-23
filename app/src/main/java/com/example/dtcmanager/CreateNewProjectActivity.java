package com.example.dtcmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcmanager.Adapter.VehicleListAdapter;
import com.example.dtcmanager.Common.FileUtils;
import com.example.dtcmanager.Interface.DeleteVehcile;
import com.example.dtcmanager.ModelClass.AddProject.AddProject;
import com.example.dtcmanager.ModelClass.AddProjectImage.AddProjectImage;
import com.example.dtcmanager.ModelClass.AddVechileImage.AddVechileImage;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.GetAllLocation.AllLocation;
import com.example.dtcmanager.ModelClass.GetAllLocation.GettAllLocation;
import com.example.dtcmanager.ModelClass.GetAllVehcile.AllVehicle;
import com.example.dtcmanager.ModelClass.GetAllVehcile.GetAllVechile;
import com.example.dtcmanager.ModelClass.GetProjectDetail.GetProjectDetail;
import com.example.dtcmanager.ModelClass.UpdateProject.UpdateProject;
import com.example.dtcmanager.ModelClass.UploadProjectContract.UploadprojectContract;
import com.example.dtcmanager.ModelClass.UploadSchdualProject.Uploadprojectschedul;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.squareup.picasso.Picasso;
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

public class CreateNewProjectActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar ChildProfiletoolbar;
    ImageButton back;
    Button saveProject;
    String encodePDF;
    CardView addPhoto;
    ImageView imgParent;
    String encodePDF2;
    ProgressBar progressBar1;
    AlertDialog loadingDialog;
    Uri imageUri, imageUri1;
    EditText txtSchedulefile, txtContractfile;
    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int PICKFILE_RESULT_CODEOne = 2;
    EditText edtProjectName, edtProjectValue, edtStartDate, edtDeadline;
    int check, checkfile;
    Calendar now;
    String originCheck, id, manager_id;
    private DatePickerDialog dpd;
    Spinner ProjectLocationSpinner;
    MultiSpinnerSearch SpinnerEmployeeProject, SpinnerVehcileProject;
    List<AllEmployee> allEmployeeList = new ArrayList<>();
    List<AllLocation> allLocationList = new ArrayList<>();
    List<AllVehicle> allVehicleList = new ArrayList<>();
    ArrayAdapter<String> spinnerArrayAdapter;
    String locationId;
    List<String> locationIdList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    List<String> VehicleId = new ArrayList<>();
    //    List<String> employeeidList = new ArrayList<>();
    ArrayList<String> vehicleIdList = new ArrayList<>();
    //    List<String> vehicle_id = new ArrayList<>();
    ArrayList<String> employee_id = new ArrayList<>();
    FileUtils fileUtils;
    TextView txtCreateNewProject;
    String ProjectLocationId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project);
        saveProject = findViewById(R.id.save_Project_btn);
        txtCreateNewProject = findViewById(R.id.txtCreateNewProject);
        Paper.init(this);
        manager_id = Paper.book().read("user_id");
        originCheck = getIntent().getStringExtra("orign");

        initView();
        Cliclble();
        AllEmploye();
        getData();
        recylerviews();

        if (originCheck.equals("AddProject")) {
//            Toast.makeText(this, "New", Toast.LENGTH_SHORT).show();
            saveProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveProject();

                }
            });
        } else if (originCheck.equals("EditProject")) {
            id = getIntent().getStringExtra("id");
            txtCreateNewProject.setText("Edit Project");
            saveProject.setText("Update Project");
            GetProjectDetail(id);
            saveProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditProject(id);
                }
            });
        }
    }


    private void initView() {
        edtProjectName = findViewById(R.id.edtProjectName);
        edtProjectValue = findViewById(R.id.edtProjectValue);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtDeadline = findViewById(R.id.edtDeadline);
        ProjectLocationSpinner = findViewById(R.id.ProjectLocationSpinner);
        SpinnerEmployeeProject = findViewById(R.id.SpinnerEmployeeProject);
        SpinnerVehcileProject = findViewById(R.id.SpinnerVehcileProject);
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar1 = findViewById(R.id.progressBar1);
        txtSchedulefile = findViewById(R.id.txtSchedulefile);
        txtContractfile = findViewById(R.id.txtContractfile);
        addPhoto= findViewById(R.id.add_photo);
        imgParent= findViewById(R.id.imgParent);
    }

    private void Cliclble() {

//        txtSchedulefile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO Auto-generated method stub
//                checkfile = 1;
//                verifyPermissions(PICKFILE_RESULT_CODE);
//
//            }
//        });
//        txtContractfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkfile = 2;
//
//                verifyPermissions(PICKFILE_RESULT_CODEOne);
//            }
//        });
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                DatePicker();
            }
        });
        edtDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 2;
                DatePicker();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPermissions();
            }
        });

    }

    private void verifyPermissions(int requestCode) {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            intent= Intent.createChooser(intent,"Choose a PDF File");
            startActivityForResult(intent, requestCode);

        } else {
            ActivityCompat.requestPermissions(CreateNewProjectActivity.this, permissions, 2857);

        }
    }

    public void verifyPermissions(){
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            openGallery();

        } else {
            ActivityCompat.requestPermissions(CreateNewProjectActivity.this, permissions, 2857);

        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2857);
    }

    private void GetProjectDetail(String id) {

        Call<GetProjectDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetProjectDetail(id);
        call.enqueue(new Callback<GetProjectDetail>() {
            @Override
            public void onResponse(Call<GetProjectDetail> call, Response<GetProjectDetail> response) {
                if (response.code() == 200) {

                    edtProjectName.setText(response.body().getProjectDetail().get(0).getName());
                    edtProjectValue.setText(response.body().getProjectDetail().get(0).getValue());
                    edtStartDate.setText(response.body().getProjectDetail().get(0).getStartDate());
                    edtDeadline.setText(response.body().getProjectDetail().get(0).getDeadLine());
                    txtContractfile.setText(response.body().getProjectDetail().get(0).getContractFile());
                    txtSchedulefile.setText(response.body().getProjectDetail().get(0).getScheduleFile());
                    String image = "http://dtc.anstm.com/dtcAdmin/api/Manager/Project_Image/" + response.body().getProjectDetail().get(0).getImage();

                    if (response.body().getProjectDetail().get(0).getLocation().size() > 0) {
                        ProjectLocationId = response.body().getProjectDetail().get(0).getLocation().get(0).getId();
                        getData();
                    }

                    if (response.body().getProjectDetail().get(0).getEmployee().size() > 0) {
                        for (int i = 0; i < response.body().getProjectDetail().get(0).getEmployee().size(); i++) {
                            employee_id.add(response.body().getProjectDetail().get(0).getEmployee().get(i).getId());
                        }

                        AllEmploye();

                    }
                    if (response.body().getProjectDetail().get(0).getVehicle().size() > 0) {
                        for (int i = 0; i < response.body().getProjectDetail().get(0).getVehicle().size(); i++) {
                            vehicleIdList.add(response.body().getProjectDetail().get(0).getVehicle().get(i).getId());
                        }

                        recylerviews();

                    }
                    Picasso.get().load(image).into(imgParent, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressBar1 != null) {
                                progressBar1.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

                } else if (response.code() == 400) {
//                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetProjectDetail> call, Throwable t) {
//                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void getAllLocation() {


        Call<GettAllLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllLocation(manager_id);
        call.enqueue(new Callback<GettAllLocation>() {
            @Override
            public void onResponse(Call<GettAllLocation> call, Response<GettAllLocation> response) {
                allLocationList.clear();
                if (response.code() == 200) {
                    allLocationList = response.body().getAllLocations();

                } else if (response.code() == 404) {
                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GettAllLocation> call, Throwable t) {
                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void SaveProject() {

        String name = edtProjectName.getText().toString();
        String value = edtProjectValue.getText().toString();
        String start_date = edtStartDate.getText().toString();
        String dead_line = edtDeadline.getText().toString();
        String schedulefilelink= txtSchedulefile.getText().toString();
        String contractfilelink= txtContractfile.getText().toString();
        if (name.isEmpty()) {
            edtProjectName.setError("Please Enter Project Name");
            edtProjectName.requestFocus();
        }else if (schedulefilelink.isEmpty()) {
            txtSchedulefile.setError("Please Enter Project Name");
            txtSchedulefile.requestFocus();
        }else if (contractfilelink.isEmpty()) {
            txtContractfile.setError("Please Enter Project Name");
            txtContractfile.requestFocus();
        } else if (value.isEmpty()) {
            edtProjectValue.setError("Please enter Project Price");
            edtProjectValue.requestFocus();
        } else if (start_date.isEmpty()) {
            edtStartDate.setError("Please Enter Start Date");
            edtStartDate.requestFocus();
        } else if (dead_line.isEmpty()) {
            edtDeadline.setError("Please Enter Deadline");
            edtDeadline.requestFocus();
        }else if (imageUri==null){
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }else if (ProjectLocationId.isEmpty()) {
            Toast.makeText(this, "Please Select Location", Toast.LENGTH_SHORT).show();
        } else if (employee_id.size() < 1) {
            Toast.makeText(this, "Please Select Employee", Toast.LENGTH_SHORT).show();
        } /*else if (vehicleIdList.size() < 1) {
//            Toast.makeText(this, "Please Select Vehicles", Toast.LENGTH_SHORT).show();
        }*/
        else if (imageUri == null) {
            Toast.makeText(this, "Please Select Project Image", Toast.LENGTH_SHORT).show();
        }
       else {
           try{showLoadingDialog();

               Log.i("TAG", "SaveProject: " + ProjectLocationId + "\n" + employee_id + "\n" + vehicleIdList);

               Call<AddProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddProject(manager_id, name,
                       value, start_date,
                       dead_line, ProjectLocationId,
                       schedulefilelink, contractfilelink,
                       vehicleIdList, employee_id,"null");
               call.enqueue(new Callback<AddProject>() {
                   @Override
                   public void onResponse(Call<AddProject> call, Response<AddProject> response) {
                       if (response.code() == 200) {
                           int id1 = response.body().getAddProjectId();
                           //String id = String.valueOf(id1);
                           UploadProjectImage(id1);

                       } else if (response.code() == 404) {
                           hideLoadingDialog();
//                        Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                       }
                   }

                   @Override
                   public void onFailure(Call<AddProject> call, Throwable t) {
                       hideLoadingDialog();
                       Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                   }
               });
           }
           catch (Exception e) {
               Toast.makeText(this, "Something happen wrong..", Toast.LENGTH_SHORT).show();
           }
        }
    }

    private void Uploadschedule(String id) {
        try{
            File file = new File(fileUtils.getRealPath(this, imageUri));
            String filename= file.getName();
            Call<Uploadprojectschedul> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadSchedual(id, filename ,encodePDF);
            call.enqueue(new Callback<Uploadprojectschedul>() {
                @Override
                public void onResponse(Call<Uploadprojectschedul> call, Response<Uploadprojectschedul> response) {
                    if (response.code() == 200) {
                        if ((originCheck.equals("AddProject"))) {
                            UploadContract(id);
                        }
                        else if ((originCheck.equals("EditProject"))) {
                            hideLoadingDialog();
                            finish();
                        }

                    } else if (response.code() == 404) {
                        hideLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<Uploadprojectschedul> call, Throwable t) {
                    progressBar1.setVisibility(View.GONE);
                    Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            hideLoadingDialog();
            Toast.makeText(this, "Project is saved without uploaded files. Update files in project from your phone directory", Toast.LENGTH_SHORT).show();
        }



    }

    private void UploadContract(String id) {
        try {
            File file = new File(fileUtils.getRealPath(this, imageUri1));
            String filename2= file.getName();
            Call<UploadprojectContract> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadContract(id, filename2,encodePDF2);
            call.enqueue(new Callback<UploadprojectContract>() {
                @Override
                public void onResponse(Call<UploadprojectContract> call, Response<UploadprojectContract> response) {
                    if (response.code() == 200) {
                        hideLoadingDialog();

                        if ((originCheck.equals("AddProject"))) {
                            hideLoadingDialog();
                            Toast.makeText(CreateNewProjectActivity.this, "Project Added Succesfully..", Toast.LENGTH_SHORT).show();
                            finish();

                        } else if ((originCheck.equals("EditProject"))) {
                            hideLoadingDialog();
                        }

                    } else if (response.code() == 404) {
                        hideLoadingDialog();
//                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<UploadprojectContract> call, Throwable t) {
                    hideLoadingDialog();
//                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception e) {
            hideLoadingDialog();
            //Log.d("ErrorImage", e.getMessage());
            Toast.makeText(this, "Project is saved without uploaded files. Update files in project from your phone directory", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void UploadProjectImage(int id) {

        File file = new File(fileUtils.getRealPath(this, imageUri));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        MultipartBody.Part filemulti = MultipartBody.Part.createFormData("picture", file.getName(), image);

// /        RequestBody userID = RequestBody.create(MultipartBody.FORM, id);

        Call<AddProjectImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddProjectImage(String.valueOf(id), filemulti);
        call.enqueue(new Callback<AddProjectImage>() {
            @Override
            public void onResponse(Call<AddProjectImage> call, Response<AddProjectImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    Toast.makeText(CreateNewProjectActivity.this, "Project added Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddProjectImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2857) {

            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    imageUri = data.getData();
                    imgParent.setImageURI(imageUri);

                }

            } else if (resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(CreateNewProjectActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                imgParent.setVisibility(View.GONE);

            }

        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && data!=null ) {
//            if (checkfile == 1) {
//                String FilePath = data.getData().getPath();
//                imageUri = data.getData();
//                try{
//                    String ext= getfileExtension(imageUri);
//                    Log.d("URI",ext);
//                    if(ext.equals("pdf")){
//                        encodePDF= getStringPdf(imageUri);
//                        txtSchedulefile.setText("File Attached");
//                        txtSchedulefile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext.equals("docx")){
//                        txtSchedulefile.setText("File Attached");
//                        txtSchedulefile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext.equals("png")){
//                        txtSchedulefile.setText("File Attached");
//                        txtSchedulefile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext.equals("jpg")){
//                        txtSchedulefile.setText("File Attached");
//                        txtSchedulefile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext.equals("jpeg")){
//                        txtSchedulefile.setText("File Attached");
//                        txtSchedulefile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else{
//                        Toast.makeText(this, "Select jpg, jpeg, pdf or docx format..", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch(Exception e){
//                    Toast.makeText(this, "Select jpg, jpeg, pdf or docx format..", Toast.LENGTH_SHORT).show();
//                }
//
//
//            } else if (checkfile == 2) {
//                String FilePathOne = data.getData().getPath();
//                imageUri1 = data.getData();
//                try{
//                    String ext1= getfileExtension(imageUri1);
//                    Log.d("URI",ext1);
//                    if(ext1.equals("pdf")){
//                        encodePDF2=getStringPdf(imageUri1);
//                        txtContractfile.setText("File Attached");
//                        txtContractfile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext1.equals("docx")){
//                        txtContractfile.setText("File Attached");
//                        txtContractfile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext1.equals("png")){
//                        txtContractfile.setText("File Attached");
//                        txtContractfile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext1.equals("jpg")){
//                        txtContractfile.setText("File Attached");
//                        txtContractfile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else if(ext1.equals("jpeg")){
//                        txtContractfile.setText("File Attached");
//                        txtContractfile.setTextColor(Color.parseColor("#68F965"));
//                    }
//                    else{
//                        Toast.makeText(this, "Select jpg, jpeg, pdf or docx format..", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch(Exception e){
//                    Toast.makeText(this, "Select jpg, jpeg, pdf or docx format..", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

    private String getfileExtension(Uri uri)
    {
        String extension;
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        extension= mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    private void EditProject(String id) {
        String name = edtProjectName.getText().toString();
        String value = edtProjectValue.getText().toString();
        String start_date = edtStartDate.getText().toString();
        String dead_line = edtDeadline.getText().toString();
        String schedulefilelink= txtSchedulefile.getText().toString();
        String contractfilelink= txtContractfile.getText().toString();

        if (name.isEmpty()) {
            edtProjectName.setError("Please Enter Project Name");
            edtProjectName.requestFocus();
        }else if (schedulefilelink.isEmpty()) {
            txtSchedulefile.setError("Please Enter Project Name");
            txtSchedulefile.requestFocus();
        }else if (contractfilelink.isEmpty()) {
            txtContractfile.setError("Please Enter Project Name");
            txtContractfile.requestFocus();
        }else if (value.isEmpty()) {
            edtProjectValue.setError("Please enter Project Price");
            edtProjectValue.requestFocus();
        } else if (start_date.isEmpty()) {
            edtStartDate.setError("Please Enter Start Date");
            edtStartDate.requestFocus();
        } else if (dead_line.isEmpty()) {
            edtDeadline.setError("Please Enter Deadline");
            edtDeadline.requestFocus();
        }
//        else if (locationId.isEmpty()) {
//            Toast.makeText(this, "Please Select Location", Toast.LENGTH_SHORT).show();
//        } else if (employeeList.size() < 1) {
//            Toast.makeText(this, "Please Select Employee", Toast.LENGTH_SHORT).show();
//        } else if (VehicleId.size() < 1) {
//            Toast.makeText(this, "Please Select Vehicles", Toast.LENGTH_SHORT).show();
//        }

//        else if (imageUri == null) {
//            Toast.makeText(this, "Please Select Schedule File", Toast.LENGTH_SHORT).show();
//        } else if (imageUri1 == null) {
//            Toast.makeText(this, "Please Select Contract File", Toast.LENGTH_SHORT).show();
//        }


        else {
            showLoadingDialog();

            Call<UpdateProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().UpdateProject(id, name, value,
                    start_date, dead_line,schedulefilelink,contractfilelink, ProjectLocationId, vehicleIdList, employee_id);
            call.enqueue(new Callback<UpdateProject>() {
                @Override
                public void onResponse(Call<UpdateProject> call, Response<UpdateProject> response) {
                    if (response.code() == 200) {
                        if(imageUri!=null){
                            int id1= Integer.parseInt(id);
                            UploadProjectImage(id1);
                        }
                        else{
                            hideLoadingDialog();
                            Toast.makeText(CreateNewProjectActivity.this, "Project Updated Succesfully..", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    } else if (response.code() == 400) {
                        hideLoadingDialog();
//                        Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateProject> call, Throwable t) {
                    hideLoadingDialog();
//                    Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CreateNewProjectActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);
        if (check == 1) {
//            dpd.setMinDate(now);

        } else if (check == 2) {
//            dpd.setMinDate(now);
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

        if (check == 1) {
            edtStartDate.setText(sdf.format(selectedDate));

        } else if (check == 2) {
            edtDeadline.setText(sdf.format(selectedDate));
        }
    }

    private void AllEmploye() {
//        progressBar1.setVisibility(View.VISIBLE);
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(manager_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
                    allEmployeeList = response.body().getAllEmployees();
                    if (response.body().getAllEmployees().size() > 0) {
                        setLocationInSpinner(allEmployeeList);
                    } else if (response.body().getAllEmployees().size() < 1) {
                        SpinnerEmployeeProject.setVisibility(View.GONE);
//                        Toast.makeText(CreateNewProjectActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
//                    spinnerevents(allEmployeeList);
                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
//                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
//                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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

            for (int j = 0; j < employee_id.size(); j++) {
                if (allEmployeeList.get(i).getId().equals(employee_id.get(j))) {
                    boolData.setSelected(true);
                }
            }

            data.add(boolData);
        }

        SpinnerEmployeeProject.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                employee_id.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    employee_id.add(String.valueOf(selectedItems.get(i).getId()));

                }
            }
        });

    }
    private void getData() {
        Call<GettAllLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllLocation(manager_id);
        call.enqueue(new Callback<GettAllLocation>() {
            @Override
            public void onResponse(Call<GettAllLocation> call, Response<GettAllLocation> response) {

                if (response.code() == 200) {
                    allLocationList = response.body().getAllLocations();

//                    Toast.makeText(CreateNewProjectActivity.this, "No Location", Toast.LENGTH_SHORT).show();
                    spinnerevents(allLocationList);

                } else if (response.code() == 404) {
//                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GettAllLocation> call, Throwable t) {
//                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void spinnerevents(List<AllLocation> allLocationList) {
        List<String> locationList = new ArrayList<>();

        for (int w = 0; w < allLocationList.size(); w++) {
            locationList.add(allLocationList.get(w).gettitle());
        }

//        spinnerArrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        locationList); //selected item will look like a spinner set from XML

        spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spineer_layout,
                        locationList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spineer_layout);

        ProjectLocationSpinner.setAdapter(spinnerArrayAdapter);

        if (originCheck.equals("EditProject")) {
            for (int w = 0; w < allLocationList.size(); w++) {

                Log.i("TAG", "spinnerevents: " + ProjectLocationId);
                if (ProjectLocationId.equals(allLocationList.get(w).gettitle())) {

                    ProjectLocationSpinner.setSelection(spinnerArrayAdapter.getPosition(allLocationList.get(w).getName()));
                }
            }
        }


        ProjectLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (int w = 0; w < allLocationList.size(); w++) {
                    if (allLocationList.get(w).gettitle().equals(adapterView.getSelectedItem())) {
                        ProjectLocationId = allLocationList.get(w).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void recylerviews() {

        Call<GetAllVechile> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllVechile(manager_id);
        call.enqueue(new Callback<GetAllVechile>() {
            @Override
            public void onResponse(Call<GetAllVechile> call, Response<GetAllVechile> response) {
                if (response.code() == 200) {
                    allVehicleList = response.body().getAllVehicles();


                    setVehiclesSpinner(allVehicleList);


                } else if (response.code() == 404) {
//                    Toast.makeText(CreateNewProjectActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllVechile> call, Throwable t) {
//                Toast.makeText(CreateNewProjectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setVehiclesSpinner(List<AllVehicle> allVehicleList) {
        List<KeyPairBoolData> data = new ArrayList<>();


        for (int i = 0; i < allVehicleList.size(); i++) {
            KeyPairBoolData boolData = new KeyPairBoolData();
            boolData.setId(Integer.parseInt(allVehicleList.get(i).getId()));
            boolData.setName(allVehicleList.get(i).getModel());
            boolData.setSelected(false);

            for (int j = 0; j < vehicleIdList.size(); j++) {
                if (allVehicleList.get(i).getId().equals(vehicleIdList.get(j))) {
                    boolData.setSelected(true);
                }
            }

            data.add(boolData);
        }

        SpinnerVehcileProject.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                vehicleIdList.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    vehicleIdList.add(String.valueOf(selectedItems.get(i).getId()));
//                    Toast.makeText(CreateNewProjectActivity.this, "" + vehicleIdList, Toast.LENGTH_SHORT).show();

                }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, requestCode);

        } else {
            Toast.makeText(this, "You have to allow these permission in order to continue", Toast.LENGTH_SHORT).show();
        }
    }

}


