package com.example.dtcmanager.EmployeeDetailsFragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Activities.ViewEmployeeDetailActivity;
import com.example.dtcmanager.Activities.ViewProjectDetail;
import com.example.dtcmanager.Activities.ViewerActivity;
import com.example.dtcmanager.Adapter.ViewSubEmployeeAdapter;
import com.example.dtcmanager.Comon.Comon;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail_;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.SubEmployee;
import com.example.dtcmanager.R;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailsFragment extends Fragment {

    public TextView txtUserName, txtPassword, txtposition, txtPhone, txtUiqueId, txtUniqueID, txtPassportNumber, txtPassportEndDate, txtJoiningDate, txtBasicSalary,
            txtExpenses, txtOverTime, txtContractDate;
    RecyclerView SubemployeeRecylerView;
    List<SubEmployee> subEmployeeList = new ArrayList<>();

    Button txtidFile, txtJoiningFile, txtImage, txtPassportFile, callemployee, resetPhone;
    ImageView emp_image;

    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emp_image= view.findViewById(R.id.emp_image);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtposition = view.findViewById(R.id.txtposition);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtUiqueId = view.findViewById(R.id.txtUiqueId);
        txtUniqueID = view.findViewById(R.id.txtUniqueID);
        txtPassportNumber = view.findViewById(R.id.txtPassportNumber);
        txtPassportEndDate = view.findViewById(R.id.txtPassportEndDate);
        txtJoiningDate = view.findViewById(R.id.txtJoiningDate);
        txtContractDate=view.findViewById(R.id.txtContractDate);
        txtBasicSalary = view.findViewById(R.id.txtBasicSalary);
        txtExpenses = view.findViewById(R.id.txtExpenses);
        txtOverTime = view.findViewById(R.id.txtOverTime);
        callemployee=view.findViewById(R.id.callbutton);
        resetPhone= view.findViewById(R.id.resetphone);

        SubemployeeRecylerView = view.findViewById(R.id.SubemployeeRecylerView);
        SubemployeeRecylerView.setHasFixedSize(true);
        SubemployeeRecylerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        txtidFile = view.findViewById(R.id.txtidFile);
        txtJoiningFile = view.findViewById(R.id.txtJoiningFile);
        //txtImage = view.findViewById(R.id.txtImage);
        txtPassportFile = view.findViewById(R.id.txtPassportFile);

        EmployeeDetail(Comon.employeeId);
    }

    private void EmployeeDetail(String id) {

        Call<EmployeeDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().EmployeeDetailMax(id);
        call.enqueue(new Callback<EmployeeDetail>() {
            @Override
            public void onResponse(Call<EmployeeDetail> call, Response<EmployeeDetail> response) {
                if (response.code() == 200) {
//                      txtUserName,txtPassword,txtposition,txtPhone,txtUiqueId,txtUniqueID,txtPassportNumber,txtPassportEndDate,
//                      txtJoiningDate,txtBasicSalary,
//                            txtExpenses,txtOverTime;
                    txtUserName.setText(response.body().getEmployeeDetail().get(0).getUserName());
                    txtPassword.setText(response.body().getEmployeeDetail().get(0).getPassword());
                    txtposition.setText(response.body().getEmployeeDetail().get(0).getPosition());
                    txtPhone.setText(response.body().getEmployeeDetail().get(0).getPhone());
                    txtUiqueId.setText(response.body().getEmployeeDetail().get(0).getEmployeeId());
                    txtUniqueID.setText(response.body().getEmployeeDetail().get(0).getEndDate());
                    txtPassportNumber.setText(response.body().getEmployeeDetail().get(0).getPassportNo());
                    txtPassportEndDate.setText(response.body().getEmployeeDetail().get(0).getPassportEndDate());
                    txtJoiningDate.setText(response.body().getEmployeeDetail().get(0).getJoiningDate());
                    txtContractDate.setText(response.body().getEmployeeDetail().get(0).getContract_end_date());
                    txtBasicSalary.setText(response.body().getEmployeeDetail().get(0).getBasicSalary());
                    txtExpenses.setText(response.body().getEmployeeDetail().get(0).getExpenses());
                    String File1 = response.body().getEmployeeDetail().get(0).getFile();
                    String Joining_File1 = response.body().getEmployeeDetail().get(0).getJoiningFile();
                    String Image1 = response.body().getEmployeeDetail().get(0).getImage();
                    String Passport_File1 = response.body().getEmployeeDetail().get(0).getPassportFile();


                    String File = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/ID/" + response.body().getEmployeeDetail().get(0).getFile();
                    String Joining_File = response.body().getEmployeeDetail().get(0).getJoiningFile();
                    String Image = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/Joining_Image/" + response.body().getEmployeeDetail().get(0).getImage();
                    String Passport_File = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/PassPort/" + response.body().getEmployeeDetail().get(0).getPassportFile();

                    Picasso.get().load(Image)
                    .into(emp_image);

                    callemployee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           makePhoneCall(txtPhone.getText().toString());
                        }
                    });

                    resetPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resetEmployeeDevice(id);
                        }
                    });

                    txtidFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try{
                                if (File1 == null || File1.equals("")){
                                    Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intent = new Intent(getContext(), ViewerActivity.class);
                                    intent.putExtra("orign", File);
                                    startActivity(intent);
                                }
                            }
                            catch(Exception e){
                                Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    txtJoiningFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if (Joining_File1== null || Joining_File1.equals("")){

                                    Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Intent browsefile = new Intent(Intent.ACTION_VIEW, Uri.parse(Joining_File));
                                    browsefile.putExtra("orign", Joining_File);
                                    startActivity(browsefile);
                                }
                            }
                            catch(Exception e){
                                Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
//                    txtImage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            if (Image1== null|| Image1.equals("")){
//
//                                Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else {
//
//                                Intent intent = new Intent(getContext(), ViewerActivity.class);
//                                intent.putExtra("orign", Image);
//                                startActivity(intent);
//                            }
//                        }
//                    });

                    txtPassportFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try{
                                if (Passport_File1== null  || Passport_File1.equals("")){

                                    Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();

                                }
                                else {

                                    Intent intent = new Intent(getContext(), ViewerActivity.class);
                                    intent.putExtra("orign", Passport_File);
                                    startActivity(intent);
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                    if (!response.body().getEmployeeDetail().get(0).getOverTime().isEmpty()) {
                        txtOverTime.setText("Allow");

                    }

                    subEmployeeList = response.body().getEmployeeDetail().get(0).getSubEmployee();
                    ViewSubEmployeeAdapter viewVehicleLocationAdapter = new ViewSubEmployeeAdapter(requireContext(), subEmployeeList);
                    SubemployeeRecylerView.setAdapter(viewVehicleLocationAdapter);

                } else if (response.code() == 400) {
                    Toast.makeText(requireContext(), "something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeDetail> call, Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void makePhoneCall(String number){
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),new String[]{
                        Manifest.permission.CALL_PHONE
                },1);
            }
            else{
                 String dial= "tel:"+number;
                 startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(requireContext(), "No phone number here..", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetEmployeeDevice(String id){
        Call<EmployeeDetail_> call = RetrofitClientClass.getInstance().getInterfaceInstance().updatel_status(id);
        call.enqueue(new Callback<EmployeeDetail_>() {
            @Override
            public void onResponse(Call<EmployeeDetail_> call, Response<EmployeeDetail_> response) {
                Toast.makeText(requireContext(), "Employee phone reset successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmployeeDetail_> call, Throwable t) {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall(txtPhone.getText().toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            EmployeeDetail(Comon.employeeId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}