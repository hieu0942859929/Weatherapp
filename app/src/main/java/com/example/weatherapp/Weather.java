package com.example.weatherapp;

public class Weather {
    String Status, DayOfWeek, Image, TempMax, TempMin;
    double Temp;

    public Weather(String status, double temp, String dayOfWeek, String image, String tempMax, String tempMin) {
        Status = status;
        Temp = temp;
        DayOfWeek = dayOfWeek;
        Image = image;
        TempMax = tempMax;
        TempMin = tempMin;
    }

    public Weather(double temp, String dayOfWeek, String image) {
        Temp = temp;
        DayOfWeek = dayOfWeek;
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


    public String getTempMax() {
        return TempMax;
    }

    public void setTempMax(String tempMax) {
        TempMax = tempMax;
    }

    public String getTempMin() {
        return TempMin;
    }

    public void setTempMin(String tempMin) {
        TempMin = tempMin;
    }
}
