package com.example.rodrigosilva.enterpriseapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rodrigosilva.enterpriseapp.api.EnterpriseAPI;
import com.example.rodrigosilva.enterpriseapp.model.Patient;
import com.example.rodrigosilva.enterpriseapp.model.Record;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.rodrigosilva.enterpriseapp.AppConstants.BASE_URL;

public class CreateRecordActivity extends AppCompatActivity {

    @BindView(R.id.datePickerButton)
    ImageButton datePickerButton;
    @BindView(R.id.nurseName)
    TextInputEditText nurseName;
    @BindView(R.id.type)
    TextInputEditText type;
    @BindView(R.id.category)
    TextInputEditText category;
    @BindView(R.id.createRecordButton)
    Button createRecordButton;

    static EditText dateEditText;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);

        patientId = getIntent().getIntExtra(AppConstants.PATIENT_ID_KEY, 0);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        dateEditText = findViewById(R.id.dateEditText);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        createRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecord();
            }
        });
    }

    private void createRecord() {
        Record record = new Record(dateEditText.getText().toString(),
                nurseName.getText().toString(),
                type.getText().toString(),
                category.getText().toString());

        postRecord(record);
    }

    private void postRecord(Record record) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EnterpriseAPI enterpriseAPI = retrofit.create(EnterpriseAPI.class);

        Call<ResponseBody> call = enterpriseAPI.createRecord(patientId, record);
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

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateEditText.setText(String.format(getString(R.string.date_string), day, month+1, year));
        }
    }
}
