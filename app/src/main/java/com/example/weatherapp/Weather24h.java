package com.example.weatherapp;

public class Weather24h {
    String perDay1, perDay, statusPerDay, imagePerDay;
    String minTemp, maxTemp;

    public Weather24h(String perDay1, String perDay, String statusPerDay, String minTemp, String maxTemp, String imagePerDay) {
        this.perDay1 = perDay1;
        this.perDay = perDay;
        this.statusPerDay = statusPerDay;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.imagePerDay = imagePerDay;
    }

    public String getPerDay() {
        return perDay;
    }

    public void setPerDay(String perDay) {
        this.perDay = perDay;
    }

    public String getStatusPerDay() {
        return statusPerDay;
    }

    public void setStatusPerDay(String statusPerDay) {
        this.statusPerDay = statusPerDay;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getImagePerDay() {
        return imagePerDay;
    }

    public void setImagePerDay(String imagePerDay) {
        this.imagePerDay = imagePerDay;
    }

    public String getPerDay1() {
        return perDay1;
    }

    public void setPerDay1(String perDay1) {
        this.perDay1 = perDay1;
    }
}
