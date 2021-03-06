package com.example.rodrigosilva.enterpriseapp.model;

import com.google.gson.annotations.SerializedName;

public class Record {

    private String date;

    @SerializedName("nurse_name")
    private String nurseName;

    private String type;
    private String category;
    private String details;

    public Record(String date, String nurseName, String type, String category, String details) {
        this.date = date;
        this.nurseName = nurseName;
        this.type = type;
        this.category = category;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
