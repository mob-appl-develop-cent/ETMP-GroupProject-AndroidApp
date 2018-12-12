package com.example.rodrigosilva.enterpriseapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodrigosilva.enterpriseapp.R;
import com.example.rodrigosilva.enterpriseapp.model.Patient;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.MyViewHolder> {

    public interface PatientListListener {
        public void onPatientSelected(String patientId);
    }

    private List<Patient> patientList;
    private Context context;
    private PatientListListener listener;

    public PatientListAdapter(List<Patient> patientList, PatientListListener listener) {
        this.patientList = patientList;
        this.listener = listener;
    }

    public void updatePatientList(List<Patient> newPatientList) {
        patientList = newPatientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.patient_list_item, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Patient patient = patientList.get(i);
        myViewHolder.patientItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPatientSelected(patient.getId());
            }
        });

        myViewHolder.patientNameTextView.setText(context.getString(R.string.patient_name, patient.getFirstName(), patient.getLastName()));
        myViewHolder.detailsTextView.setText(context.getString(R.string.patient_details,
                patient.getAge(),
                patient.getAddress(),
                patient.getRoomNumber(),
                patient.getEmergencyNumber(),
                patient.getDepartment(),
                patient.getDoctor()));
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView patientNameTextView, detailsTextView;
        public CardView patientItemView;


        public MyViewHolder(View view) {
            super(view);
            patientItemView = (CardView) view;
            patientNameTextView = view.findViewById(R.id.nurseNameTextView);
            detailsTextView = view.findViewById(R.id.detailsTextView);
        }
    }

}
