package com.example.demo.model;

public class Participant {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private boolean isActive = true; // Status aktif peserta (default: aktif)

    public Participant(int id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isValid() {
        /*
         Memeriksa validitas data.
         Data dianggap valid jika:
         Nama tidak null, tidak kosong, dan hanya mengandung huruf, angka, dan spasi.
         Alamat tidak null, tidak kosong, dan tidak mengandung hanya spasi.
         Nomor telepon tidak null, tidak kosong, mengandung hanya angka,
         dan memiliki panjang 10 hingga 15 karakter.
        * */

        return name != null && !name.trim().isEmpty() &&
                address != null && !address.trim().isEmpty() &&
                phoneNumber != null && !phoneNumber.trim().isEmpty() &&
                phoneNumber.matches("\\d{10,15}") &&
                name.matches("^[a-zA-Z0-9 ]*$");
    }
}