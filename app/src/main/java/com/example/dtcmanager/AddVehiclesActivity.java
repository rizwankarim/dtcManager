package com.example.dtcmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.dtcmanager.Common.FileUtils;
import com.example.dtcmanager.ModelClass.AddVechile.AddVechile;
import com.example.dtcmanager.ModelClass.AddVechileImage.AddVechileImage;
import com.example.dtcmanager.ModelClass.EditVehicle.EditVehicle;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.GetAllLocation.AllLocation;
import com.example.dtcmanager.ModelClass.GetAllLocation.GettAllLocation;
import com.example.dtcmanager.ModelClass.VehcileDetail.GetVehcileDetail;
import com.example.dtcmanager.ModelClass.VehicleDetailMax.GetVehicleDeailMy;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
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

public class AddVehiclesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageButton backbtn;
    MultiSpinnerSearch spinner;
    Spinner chooseEmployeeSpinner;
    CardView addphoto;
    Uri imageUri;
    ProgressBar progressBar2;
    ImageView imgParent;
    EditText edtExaminDate, edtLicenseEndDate, edtInsuranceEndDate, edtInsuranceStartDate, edtKilometer, edtModel, edtVechilenumber;
    Toolbar ChildProfiletoolbar;
    Calendar now;
    private DatePickerDialog dpd;
    List<AllLocation> allLocationList = new ArrayList<>();
    int check;
    ArrayAdapter<String> spinnerArrayAdapter;
    List<AllEmployee> allEmployeeList = new ArrayList<>();
    ProgressBar progressBar1;
    AlertDialog loadingDialog;
    List<String> locationIdList = new ArrayList<>();
    String employeeId = "", manager_id;
    Button save_location_btn;
    FileUtils fileUtils;
    String imagePath;
    String originCheck, id;
    TextView txtCreateNewvehicle;
    private static final int PICKFILE_RESULT_CODE = 2856;
    private static final int PICKPHOTO_RESULT_CODE = 2857;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicles);

        Paper.init(this);
        manager_id = Paper.book().read("user_id");
        originCheck = getIntent().getStringExtra("orign");
        save_location_btn = findViewById(R.id.save_location_btn);
        txtCreateNewvehicle = findViewById(R.id.txtCreateNewvehicle);

        if (originCheck.equals("AddVehicle")) {
            save_location_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (imagePath != null || imageUri != null) {
                        addVechile();
                    } else {
                        Toast.makeText(AddVehiclesActivity.this, "Please add Image ", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        } else if (originCheck.equals("EditVehicle")) {
            id = getIntent().getStringExtra("id");
            txtCreateNewvehicle.setText("Edit Vehicle ");
            save_location_btn.setText("Update Vehicle");
//            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();

            VehicleDetail(id);
            save_location_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (imagePath != null || imageUri != null) {
                    EditVehicle(id);

//                    else {
//                        Toast.makeText(AddVehiclesActivity.this, "Please add Image ", Toast.LENGTH_SHORT).show();
//                    }

                }
            });


        }
        initviews();
        clickevents();
        getData();
        AllEmploye();


    }


    private void initviews() {
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.ChooseLocationSpinner);
        addphoto = findViewById(R.id.add_photo);
        imgParent = findViewById(R.id.imgParent);
        edtExaminDate = findViewById(R.id.edtExaminDate);
        edtLicenseEndDate = findViewById(R.id.edtLicenseEndDate);
        edtInsuranceEndDate = findViewById(R.id.edtInsuranceEndDate);
        edtInsuranceStartDate = findViewById(R.id.edtInsuranceStartDate);
        edtKilometer = findViewById(R.id.edtKilometer);
        edtModel = findViewById(R.id.edtModel);
        edtVechilenumber = findViewById(R.id.edtVechilenumber);
        chooseEmployeeSpinner = findViewById(R.id.chooseEmployeeSpinner);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
    }

    private void clickevents() {
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermissions();

            }
        });
        edtInsuranceStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 2;
                DatePicker();

            }
        });
        edtInsuranceEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 3;
                DatePicker();

            }
        });
        edtLicenseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 4;
                DatePicker();
            }
        });
        edtExaminDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 5;
                DatePicker();
            }
        });


    }

    private void VehicleDetail(String id) {
        showLoadingDialog();
        Call<GetVehicleDeailMy> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetVehcileDetail(id);
        call.enqueue(new Callback<GetVehicleDeailMy>() {
            @Override
            public void onResponse(Call<GetVehicleDeailMy> call, Response<GetVehicleDeailMy> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();

                    edtVechilenumber.setText(response.body().getVehicleDetails().get(0).getVehicleNumber());
                    edtModel.setText(response.body().getVehicleDetails().get(0).getModel());
                    edtKilometer.setText(response.body().getVehicleDetails().get(0).getKilometers());
                    edtInsuranceStartDate.setText(response.body().getVehicleDetails().get(0).getInsuranceDateStart());
                    edtInsuranceEndDate.setText(response.body().getVehicleDetails().get(0).getInsuranceDateEnd());
                    edtLicenseEndDate.setText(response.body().getVehicleDetails().get(0).getLicenseDateEnd());
                    edtExaminDate.setText(response.body().getVehicleDetails().get(0).getExaminationDate());
                    String image = "http://test.proglabs.org/DTC/api/Manager/Vehicle_Image/" + response.body().getVehicleDetails().get(0).getImage();
                    Log.i("TAG", "onBindViewHolder:" + image);

                    if (response.body().getVehicleDetails().get(0).getEmployee().size() > 0) {
                        employeeId = response.body().getVehicleDetails().get(0).getEmployee().get(0).getId();
                        AllEmploye();
                    }

                    if (response.body().getVehicleDetails().get(0).getLocation().size() > 0) {
                        for (int i = 0; i < response.body().getVehicleDetails().get(0).getLocation().size(); i++) {
                            locationIdList.add(response.body().getVehicleDetails().get(0).getLocation().get(i).getId());
                        }


                        getData();
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

                    hideLoadingDialog();
//                    Toast.makeText(AddVehiclesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();

//                    Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetVehicleDeailMy> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addVechile() {
        String vehicle_number = edtVechilenumber.getText().toString();
        String model = edtModel.getText().toString();
        String kilometers = edtKilometer.getText().toString();
        String insurance_date_start = edtInsuranceStartDate.getText().toString();
        String insurance_date_end = edtInsuranceEndDate.getText().toString();
        String license_date_end = edtLicenseEndDate.getText().toString();
        String examination_date = edtExaminDate.getText().toString();
        String employee_id = employeeId;

        if (vehicle_number.isEmpty()) {
            edtVechilenumber.setError("Please Enter Vehicle Number");
            edtVechilenumber.requestFocus();
        }
        if (model.isEmpty()) {
            edtModel.setError("Please Enter Vehicle Model");
            edtModel.requestFocus();
        } else if (kilometers.isEmpty()) {
            edtKilometer.setError("Please Enter Kilometers");
            edtKilometer.requestFocus();
        } else if (insurance_date_start.isEmpty()) {
            edtInsuranceStartDate.setError("Please Enter Insurance Start Date");
            edtInsuranceStartDate.requestFocus();
        } else if (insurance_date_end.isEmpty()) {
            edtInsuranceEndDate.setError("Please Enter Insurance End Date");
            edtInsuranceEndDate.requestFocus();
        } else if (license_date_end.isEmpty()) {
            edtLicenseEndDate.setError("Please Enter License End Date");
            edtLicenseEndDate.requestFocus();
        } else if (examination_date.isEmpty()) {
            edtExaminDate.setError("Please Enter examination Date");
            edtExaminDate.requestFocus();
        } else {
            showLoadingDialog();
            Call<AddVechile> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddVechile(manager_id, vehicle_number, model, kilometers, insurance_date_start,
                    insurance_date_end, license_date_end, examination_date, employee_id,"nullimage", (ArrayList<String>) locationIdList);

            call.enqueue(new Callback<AddVechile>() {
                @Override
                public void onResponse(Call<AddVechile> call, Response<AddVechile> response) {
                    if (response.code() == 200) {
                        int id = response.body().getAddVehicleId();
                        UploadVechileImage(id);

                    } else if (response.code() == 404) {
//                        progressBar1.setVisibility(View.GONE);
                        hideLoadingDialog();
//                        Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddVechile> call, Throwable t) {
//                    progressBar1.setVisibility(View.GONE);
                    hideLoadingDialog();
                    Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void EditVehicle(String id) {
        String vehicle_number = edtVechilenumber.getText().toString();
        String model = edtModel.getText().toString();
        String kilometers = edtKilometer.getText().toString();
        String insurance_date_start = edtInsuranceStartDate.getText().toString();
        String insurance_date_end = edtInsuranceEndDate.getText().toString();
        String license_date_end = edtLicenseEndDate.getText().toString();
        String examination_date = edtExaminDate.getText().toString();
        String employee_id = employeeId;
        if (vehicle_number.isEmpty()) {
            edtVechilenumber.setError("Please Enter Vehicle Number");
            edtVechilenumber.requestFocus();
        }
        if (model.isEmpty()) {
            edtModel.setError("Please Enter Vehicle Model");
            edtModel.requestFocus();
        } else if (kilometers.isEmpty()) {
            edtKilometer.setError("Please Enter Kilometers");
            edtKilometer.requestFocus();
        } else if (insurance_date_start.isEmpty()) {
            edtInsuranceStartDate.setError("Please Enter Insurance Start Date");
            edtInsuranceStartDate.requestFocus();
        } else if (insurance_date_end.isEmpty()) {
            edtInsuranceEndDate.setError("Please Enter Insurance End Date");
            edtInsuranceEndDate.requestFocus();
        } else if (license_date_end.isEmpty()) {
            edtLicenseEndDate.setError("Please Enter License End Date");
            edtLicenseEndDate.requestFocus();
        } else if (examination_date.isEmpty()) {
            edtExaminDate.setError("Please Enter examination Date");
            edtExaminDate.requestFocus();
        } else {
            showLoadingDialog();

            Call<EditVehicle> call = RetrofitClientClass.getInstance().getInterfaceInstance().EditVehcile(String.valueOf(id), vehicle_number, model, kilometers, insurance_date_start, insurance_date_end,
                    license_date_end, examination_date, employee_id, (ArrayList<String>) locationIdList);
            call.enqueue(new Callback<EditVehicle>() {
                @Override
                public void onResponse(Call<EditVehicle> call, Response<EditVehicle> response) {
                    if (response.code() == 200) {


                        if (imageUri != null) {

                            UploadVechileImage(Integer.parseInt(id));
                        } else {
                            hideLoadingDialog();
                            Toast.makeText(AddVehiclesActivity.this, "Update Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    } else {
                        hideLoadingDialog();
//                        Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditVehicle> call, Throwable t) {
                    hideLoadingDialog();
                    Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void UploadVechileImage(int id) {

        File file = new File(fileUtils.getRealPath(this, imageUri));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        MultipartBody.Part filemulti = MultipartBody.Part.createFormData("picture", file.getName(), image);

// /        RequestBody userID = RequestBody.create(MultipartBody.FORM, id);

        Call<AddVechileImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddVechileImage(String.valueOf(id), filemulti);
        call.enqueue(new Callback<AddVechileImage>() {
            @Override
            public void onResponse(Call<AddVechileImage> call, Response<AddVechileImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    Toast.makeText(AddVehiclesActivity.this, "Vehicle added Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddVechileImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getData() {


        Call<GettAllLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllLocation(manager_id);
        call.enqueue(new Callback<GettAllLocation>() {
            @Override
            public void onResponse(Call<GettAllLocation> call, Response<GettAllLocation> response) {
                allLocationList.clear();
                if (response.code() == 200) {
                    allLocationList = response.body().getAllLocations();
                    if (allLocationList.size() > 0) {
                        setLocationInSpinner(allLocationList);


                    } else {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(AddVehiclesActivity.this, "No Location", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GettAllLocation> call, Throwable t) {
                Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setLocationInSpinner(List<AllLocation> allLocationList) {


        List<KeyPairBoolData> data = new ArrayList<>();

        for (int i = 0; i < allLocationList.size(); i++) {
            KeyPairBoolData boolData = new KeyPairBoolData();
            boolData.setId(Integer.parseInt(allLocationList.get(i).getId()));
            boolData.setName(allLocationList.get(i).getName());
            boolData.setSelected(false);

            for (int j = 0; j < locationIdList.size(); j++) {
                if (allLocationList.get(i).getId().equals(locationIdList.get(j))) {
                    boolData.setSelected(true);
                }
            }

            data.add(boolData);
        }

        spinner.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                locationIdList.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    locationIdList.add(String.valueOf(selectedItems.get(i).getId()));

                }
            }
        });

    }

    private void AllEmploye() {
//        progressBar1.setVisibility(View.VISIBLE);
        Call<GetAllEmploye> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllEmployee(manager_id);
        call.enqueue(new Callback<GetAllEmploye>() {
            @Override
            public void onResponse(Call<GetAllEmploye> call, Response<GetAllEmploye> response) {
                if (response.code() == 200) {
//                    progressBar1.setVisibility(View.GONE);
                    allEmployeeList = response.body().getAllEmployees();

                    spinnerevents(allEmployeeList);
                } else if (response.code() == 404) {
//                    progressBar1.setVisibility(View.GONE);
//                    Toast.makeText(AddVehiclesActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<GetAllEmploye> call, Throwable t) {
//                Toast.makeText(AddVehiclesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void spinnerevents(List<AllEmployee> allEmployeeList) {

        List<String> employeeList = new ArrayList<>();

        for (int w = 0; w < allEmployeeList.size(); w++) {
            employeeList.add(allEmployeeList.get(w).getUserName());
        }
        spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spineer_layout,
                        employeeList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spineer_layout);
        chooseEmployeeSpinner.setAdapter(spinnerArrayAdapter);

        if (originCheck.equals("EditVehicle")) {
            for (int w = 0; w < allEmployeeList.size(); w++) {

                if (employeeId.equals(allEmployeeList.get(w).getId())) {
                    chooseEmployeeSpinner.setSelection(spinnerArrayAdapter.getPosition(allEmployeeList.get(w).getUserName()));
                }
            }
        }


        chooseEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (int w = 0; w < allEmployeeList.size(); w++) {
                    if (allEmployeeList.get(w).getUserName().equals(adapterView.getSelectedItem())) {
                        employeeId = allEmployeeList.get(w).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            ActivityCompat.requestPermissions(AddVehiclesActivity.this, permissions, 2857);

        }
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

    private void openGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2857);
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

                Toast.makeText(AddVehiclesActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                imgParent.setVisibility(View.GONE);

            }

        }


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

    private void DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                AddVehiclesActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);
        if (check == 3) {
            dpd.setMinDate(now);

        } else if (check == 4) {
            dpd.setMinDate(now);
        } else if (check == 5) {
            dpd.setMaxDate(now);
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

        if (check == 2) {
            edtInsuranceStartDate.setText(sdf.format(selectedDate));

        } else if (check == 3) {
            edtInsuranceEndDate.setText(sdf.format(selectedDate));
        } else if (check == 4) {
            edtLicenseEndDate.setText(sdf.format(selectedDate));
        } else if (check == 5) {
            edtExaminDate.setText(sdf.format(selectedDate));
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
