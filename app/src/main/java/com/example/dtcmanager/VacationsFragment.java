package com.example.dtcmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.GetAllVacationAdapter;
import com.example.dtcmanager.Common.Common;
import com.example.dtcmanager.Interface.LeaveApplicationInterface;
import com.example.dtcmanager.ModelClass.ChangeStatus.ChangeStatus;
import com.example.dtcmanager.ModelClass.GetAllVacations.EmployeeVacation;
import com.example.dtcmanager.ModelClass.GetAllVacations.GetAllVacations;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VacationsFragment extends Fragment {

    RecyclerView AllVacationRecylerView;
    ImageButton refresh;
    AlertDialog loadingDialog;
    String manager_id;
    TextView noData;
    List<EmployeeVacation> employeeVacationList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vacations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager_id = Paper.book().read("user_id");

        if(checkConnection())
        {
            //Toast.makeText(getActivity(), "Connected to Internet", Toast.LENGTH_SHORT).show();
            GetData();
        }else
        {
            Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        }

        AllVacationRecylerView = view.findViewById(R.id.AllVacationRecylerView);
        noData= view.findViewById(R.id.noData);
        refresh= view.findViewById(R.id.refresh);
        AllVacationRecylerView.setHasFixedSize(false);
        AllVacationRecylerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection())
                {
                    //Toast.makeText(getActivity(), "Connected to Internet", Toast.LENGTH_SHORT).show();
                    GetData();
                }else
                {
                    Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void GetData() {

        showLoadingDialog();
        Call<GetAllVacations> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllVacation(manager_id);
        call.enqueue(new Callback<GetAllVacations>() {
            @Override
            public void onResponse(Call<GetAllVacations> call, Response<GetAllVacations> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();

                    employeeVacationList = response.body().getEmployeeVacations();
                    if(employeeVacationList.size()>0){

                        noData.setVisibility(View.GONE);
                        AllVacationRecylerView.setVisibility(View.VISIBLE);

                        GetAllVacationAdapter getAllVacationAdapter = new GetAllVacationAdapter(requireContext(),
                                employeeVacationList, new LeaveApplicationInterface() {
                            @Override
                            public void LeaveApplicationInterface(String id, String emp_id) {
                                showLoadingDialog();
                                Call<ChangeStatus> call = RetrofitClientClass.getInstance().getInterfaceInstance().changeStatus(id,emp_id, Common.status);
                                call.enqueue(new Callback<ChangeStatus>() {
                                    @Override
                                    public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                                        if (response.code() == 200) {
                                            hideLoadingDialog();
//                                        Toast.makeText(requireContext(), "" + Common.status, Toast.LENGTH_SHORT).show();
                                            employeeVacationList.clear();
                                            GetData();
                                            hideLoadingDialog();


                                        } else if (response.code() == 400) {
                                            hideLoadingDialog();
                                            Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                                            hideLoadingDialog();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ChangeStatus> call, Throwable t) {

                                        Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        hideLoadingDialog();
                                    }
                                });

                            }
                        });

                        AllVacationRecylerView.setAdapter(getAllVacationAdapter);
                    }
                    else{
                        noData.setVisibility(View.VISIBLE);
                        AllVacationRecylerView.setVisibility(View.GONE);
                    }
                } else if (response.code() == 400) {
                    hideLoadingDialog();

                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetAllVacations> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void ChangeStatus(String id, String emp_id) {
        showLoadingDialog();

        Call<ChangeStatus> call = RetrofitClientClass.getInstance().getInterfaceInstance().changeStatus(id, emp_id ,Common.status);
        call.enqueue(new Callback<ChangeStatus>() {
            @Override
            public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                if (response.code() == 200) {
                    Toast.makeText(requireContext(), "" + Common.status, Toast.LENGTH_SHORT).show();

                    if(checkConnection())
                    {
                        GetData();
                    }else
                    {

                    }
                    hideLoadingDialog();


                } else if (response.code() == 400) {
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ChangeStatus> call, Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                hideLoadingDialog();
            }
        });
    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(requireContext()).create();
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }
    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}