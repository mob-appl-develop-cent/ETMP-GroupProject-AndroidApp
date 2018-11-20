package com.example.rodrigosilva.enterpriseapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rodrigosilva.enterpriseapp.api.EnterpriseAPI;
import com.example.rodrigosilva.enterpriseapp.model.Patient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.rodrigosilva.enterpriseapp.AppConstants.BASE_URL;

public class CreatePatientActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    @BindView(R.id.firstName)
    TextInputEditText firstName;
    @BindView(R.id.lastName)
    TextInputEditText lastName;
    @BindView(R.id.age)
    TextInputEditText age;
    @BindView(R.id.address)
    TextInputEditText address;
    @BindView(R.id.roomNumber)
    TextInputEditText roomNumber;
    @BindView(R.id.department)
    TextInputEditText department;
    @BindView(R.id.emergencyNumber)
    TextInputEditText emergencyNumber;
    @BindView(R.id.doctor)
    TextInputEditText doctor;

    @BindView(R.id.createPatientButton)
    Button createPatientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.create_patient_activity_title);

        init();

        createPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPatient();
            }
        });
    }

    private void init() {
        firstName.setOnFocusChangeListener(this);
        lastName.setOnFocusChangeListener(this);
        age.setOnFocusChangeListener(this);
        address.setOnFocusChangeListener(this);
        roomNumber.setOnFocusChangeListener(this);
        department.setOnFocusChangeListener(this);
        emergencyNumber.setOnFocusChangeListener(this);
        doctor.setOnFocusChangeListener(this);
    }

    private void createPatient() {
        Patient patient = new Patient(firstName.getText().toString(),
                lastName.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                address.getText().toString(),
                roomNumber.getText().toString(),
                emergencyNumber.getText().toString(),
                department.getText().toString(),
                doctor.getText().toString());

        postPatient(patient);
    }

    private void postPatient(Patient patient) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EnterpriseAPI enterpriseAPI = retrofit.create(EnterpriseAPI.class);

        Call<ResponseBody> call = enterpriseAPI.createPatient(patient);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "AEHOOOOOO!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "FALHOOOU!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            TextInputEditText textInputEditText = (TextInputEditText) v;
            if (textInputEditText.getText().length() == 0) {
                textInputEditText.setError(getString(R.string.empty_field_error));
            }
        }
    }
}
