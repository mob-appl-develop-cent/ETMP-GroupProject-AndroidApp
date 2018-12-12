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
import com.example.rodrigosilva.enterpriseapp.model.Record;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.MyViewHolder> {

    private List<List<Record>> recordList;
    private Context context;

    public RecordListAdapter(List<List<Record>> recordList) {
        this.recordList = recordList;
    }

//    public void updateRecordsList(List<Record> newRecordList) {
//        recordList = newRecordList;
//        notifyDataSetChanged();
//    }

    public void updateRecordsList(List<List<Record>> newRecordList) {
        recordList = newRecordList;
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
        Record record = recordList.get(i).get(0);

        myViewHolder.nurseNameTextView.setText(record.getNurseName());
        myViewHolder.dateTextView.setText(record.getDate());
        myViewHolder.categoryTextView.setText(record.getCategory());
        myViewHolder.typeTextView.setText(record.getType());
        myViewHolder.detailsTextView.setText(record.getDetails());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.nurseNameTextView)
        TextView nurseNameTextView;

        @BindView(R.id.categoryTextView)
        TextView categoryTextView;

        @BindView(R.id.dateTextView)
        TextView dateTextView;

        @BindView(R.id.typeTextView)
        TextView typeTextView;

        @BindView(R.id.detailsTextView)
        TextView detailsTextView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
