package com.example.rodrigosilva.enterpriseapp.model;

import com.google.gson.annotations.SerializedName;

public class Patient {

    @SerializedName(value="_id")
    private int id;
    @SerializedName(value="first_name")
    private String firstName;
    @SerializedName(value="last_name")
    private String lastName;
    private int age;
    private String address;
    @SerializedName(value="room_number")
    private String roomNumber;
    @SerializedName(value="emergency_number")
    private String emergencyNumber;
    private String department;
    private String doctor;

    public Patient(String firstName, String lastName, int age, String address, String roomNumber, String emergencyNumber, String department, String doctor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.roomNumber = roomNumber;
        this.emergencyNumber = emergencyNumber;
        this.department = department;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
