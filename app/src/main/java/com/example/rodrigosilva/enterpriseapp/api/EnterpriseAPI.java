package com.example.rodrigosilva.enterpriseapp.api;

import com.example.rodrigosilva.enterpriseapp.model.Patient;
import com.example.rodrigosilva.enterpriseapp.model.Record;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EnterpriseAPI {

    @GET("patients/")
    Call<List<Patient>> getAllPatients();

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("patients/")
    Call<ResponseBody> createPatient(@Body Patient patient);

    @GET("patients/{id}/records")
    Call<List<Record>> getPatientRecords(@Path("id") int patientId);

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("patients/{id}/records")
    Call<ResponseBody> createRecord(@Path("id") int patientId, @Body Record record);

}
