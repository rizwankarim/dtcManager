package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcmanager.Adapter.AllProjectAdapter;
import com.example.dtcmanager.Interface.DeleteProject;
import com.example.dtcmanager.ModelClass.GetAllEmployee.AllEmployee;
import com.example.dtcmanager.ModelClass.GetAllProject.AllProject;
import com.example.dtcmanager.ModelClass.GetAllProject.GetAllProject;
import com.example.dtcmanager.ModelClass.RemoveProject.RemoveProject;
import com.example.dtcmanager.RetrofitClient.RetrofitClientClass;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsActivity extends AppCompatActivity {

    ImageButton addproject, backbtn;
    MaterialSearchBar searchBar;
    Toolbar ChildProfiletoolbar;
    String manager_id = "";
    AllProjectAdapter allProjectAdapter;
    List<AllProject> allProjectList = new ArrayList<>();
    ProgressBar progressBar1;
    RecyclerView ProjctRecylerView;
    TextView noData;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Paper.init(this);
        manager_id = Paper.book().read("user_id");
        try{
            initView();
            Clickble();
        }
        catch (Exception e){
            Toast.makeText(ProjectsActivity.this, "No projects found", Toast.LENGTH_SHORT).show();
        }

    }



    private void initView() {
        try{
            addproject = findViewById(R.id.addproject);
            ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
            ChildProfiletoolbar.setTitle("");
            setSupportActionBar(ChildProfiletoolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            searchBar=findViewById(R.id.searchBars);
            progressBar1 = findViewById(R.id.progressBar1);
            ProjctRecylerView = findViewById(R.id.ProjctRecylerView);
            ProjctRecylerView.setHasFixedSize(false);
            noData= findViewById(R.id.noData);
            ProjctRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        }
        catch (Exception e){
            Toast.makeText(ProjectsActivity.this, "No projects found", Toast.LENGTH_SHORT).show();
        }

    }
    private void Clickble() {
        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectsActivity.this, CreateNewProjectActivity.class);
                intent.putExtra("orign", "AddProject");
                startActivity(intent);
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<AllProject> allProjects= new ArrayList<>();
        for(AllProject pro : allProjectList){
            if(pro.getName().toLowerCase().contains(text.toLowerCase())){
                allProjects.add(pro);
            }
        }
        allProjectAdapter.filterlist(allProjects);
    }


    private void getAllProject() {
        showLoadingDialog();
        Call<GetAllProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllProject(manager_id);
        call.enqueue(new Callback<GetAllProject>() {
            @Override
            public void onResponse(Call<GetAllProject> call, Response<GetAllProject> response) {
                if(response.code() == 200){
                    try
                    {
                        hideLoadingDialog();
                        allProjectList = response.body().getAllProjects();
                        if(allProjectList.size()>0){
                            noData.setVisibility(View.GONE);
                            ProjctRecylerView.setVisibility(View.VISIBLE);
                            searchBar.setEnabled(true);
                            allProjectAdapter = new AllProjectAdapter(ProjectsActivity.this, allProjectList, new DeleteProject() {
                                @Override
                                public void DeleteProject(String id) {
                                    deleteProject(id);
                                }
                            });
                            ProjctRecylerView.setAdapter(allProjectAdapter);
                        }

                        else{
                            noData.setVisibility(View.VISIBLE);
                            ProjctRecylerView.setVisibility(View.GONE);
                            searchBar.setEnabled(false);
                        }


                    }
                    catch (Exception e){
                        Toast.makeText(ProjectsActivity.this, "No projects found", Toast.LENGTH_SHORT).show();
                    }


//                    Toast.makeText(ProjectsActivity.this, "aa", Toast.LENGTH_SHORT).show();

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
//                    Toast.makeText(ProjectsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
//                    Toast.makeText(ProjectsActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllProject> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(ProjectsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void deleteProject(String id) {


        showLoadingDialog();
        Call<RemoveProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().RemoveProject(id);
        call.enqueue(new Callback<RemoveProject>() {
            @Override
            public void onResponse(Call<RemoveProject> call, Response<RemoveProject> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    getAllProject();

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
//                    Toast.makeText(ProjectsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveProject> call, Throwable t) {
                hideLoadingDialog();
//                Toast.makeText(ProjectsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllProject();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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