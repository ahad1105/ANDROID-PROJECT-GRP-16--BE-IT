package com.example.myapplication1;

public class Crop {
    private int id;
    private String Latitude;
    private String Longitude;
    private String Name;
    private byte[] image;

    public Crop(int id, String latitude, String longitude, String name, byte[] image) {
        this.id = id;
        Latitude = latitude;
        Longitude = longitude;
        Name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
