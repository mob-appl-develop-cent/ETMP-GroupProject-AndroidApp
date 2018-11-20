package com.example.rodrigosilva.enterpriseapp.api;

import com.example.rodrigosilva.enterpriseapp.model.Patient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EnterpriseAPI {

    @GET("patients/")
    Call<List<Patient>> loadAllPatients();

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("patients/")
    Call<ResponseBody> createPatient(@Body Patient patient);

}
