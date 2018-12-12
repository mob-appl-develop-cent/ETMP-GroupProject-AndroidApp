package com.example.rodrigosilva.enterpriseapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rodrigosilva.enterpriseapp.adapter.PatientListAdapter;
import com.example.rodrigosilva.enterpriseapp.api.EnterpriseAPI;
import com.example.rodrigosilva.enterpriseapp.model.Patient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.rodrigosilva.enterpriseapp.AppConstants.BASE_URL;

public class MainActivity extends AppCompatActivity implements PatientListAdapter.PatientListListener, Callback<List<Patient>>{



    @BindView(R.id.patientListView) RecyclerView patientListView;
    @BindView(R.id.createPatientFAB) FloatingActionButton createPatientFAB;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.contentView) ConstraintLayout contentView;
    private PatientListAdapter adapter;
    private static final float ALPHA_VISIBLE = 1;
    private static final float ALPHA_HIDDEN = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setActionBar();

        init();
        start();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.patients_activity_title);
    }

    private void init() {
        patientListView.setHasFixedSize(true);
        patientListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientListAdapter(new ArrayList<Patient>(), this);
        patientListView.setAdapter(adapter);

        createPatientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreatePatientActivity.class));
            }
        });
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE :  View.GONE);
        contentView.setAlpha(show ? ALPHA_HIDDEN : ALPHA_VISIBLE);
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EnterpriseAPI enterpriseAPI = retrofit.create(EnterpriseAPI.class);

        showProgress(true);
        Call<List<Patient>> call = enterpriseAPI.getAllPatients();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
        showProgress(false);
        if(response.isSuccessful()) {
            List<Patient> patients = response.body();
            if (patients != null) {
                adapter.updatePatientList(patients);
            }

        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Patient>> call, Throwable t) {
        showProgress(false);
        t.printStackTrace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_item) {
            start();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    @Override
    public void onPatientSelected(String patientId) {
        Intent intent = new Intent(this, PatientRecordsActivity.class);
        intent.putExtra(AppConstants.PATIENT_ID_KEY, patientId);
        startActivity(intent);
    }
}
