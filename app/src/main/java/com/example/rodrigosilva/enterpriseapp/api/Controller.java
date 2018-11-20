package com.example.rodrigosilva.enterpriseapp.api;

import com.example.rodrigosilva.enterpriseapp.model.Patient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Patient>> {

    static final String BASE_URL = "https://etmp-group-project.herokuapp.com/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EnterpriseAPI enterpriseAPI = retrofit.create(EnterpriseAPI.class);

        Call<List<Patient>> call = enterpriseAPI.loadAllPatients();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
        if(response.isSuccessful()) {
            List<Patient> patients = response.body();
            if (patients != null) {
                for (Patient patient : patients) {
                    System.out.println(patient.getFirstName());
                }
            }

        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Patient>> call, Throwable t) {
        t.printStackTrace();
    }
}
