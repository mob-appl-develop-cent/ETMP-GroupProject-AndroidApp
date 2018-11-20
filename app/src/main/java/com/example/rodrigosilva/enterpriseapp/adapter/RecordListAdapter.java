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

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.MyViewHolder> {

    private List<Patient> patientList;
    private Context context;

    public RecordListAdapter(List<Patient> patientList) {
        this.patientList = patientList;
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
                .inflate(R.layout.record_list_item, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView reportTitleTextView, reportDetailsTextView;
        public CardView reportItemView;


        public MyViewHolder(View view) {
            super(view);
            reportItemView = (CardView) view;
            reportTitleTextView = view.findViewById(R.id.recordTitleTextView);
            reportDetailsTextView = view.findViewById(R.id.recordDetailsTextView);
        }
    }

}
