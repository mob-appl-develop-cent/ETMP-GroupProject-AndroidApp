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

import com.example.rodrigosilva.enterpriseapp.adapter.RecordListAdapter;
import com.example.rodrigosilva.enterpriseapp.api.EnterpriseAPI;
import com.example.rodrigosilva.enterpriseapp.model.Record;
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

public class PatientRecordsActivity extends AppCompatActivity implements Callback<List<Record>> {

    @BindView(R.id.recordsListView)
    RecyclerView recordListView;
    @BindView(R.id.createRecordFAB)
    FloatingActionButton createRecordFAB;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentView)
    ConstraintLayout contentView;
    private RecordListAdapter adapter;
    private static final float ALPHA_VISIBLE = 1;
    private static final float ALPHA_HIDDEN = 0.2f;

    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);
        ButterKnife.bind(this);

        patientId = getIntent().getIntExtra(AppConstants.PATIENT_ID_KEY, 0);

        setActionBar();

        init();
        start();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.records_activity_title);
    }

    private void init() {
        recordListView.setHasFixedSize(true);
        recordListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecordListAdapter(new ArrayList<Record>());
        recordListView.setAdapter(adapter);

        createRecordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRecordActivity.class);
                intent.putExtra(AppConstants.PATIENT_ID_KEY, patientId);
                startActivity(intent);
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
        Call<List<Record>> call = enterpriseAPI.getPatientRecords(patientId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
        showProgress(false);
        if(response.isSuccessful()) {
            List<Record> records = response.body();
            if (records != null) {
                adapter.updateRecordsList(records);
            }

        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Record>> call, Throwable t) {
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



}
