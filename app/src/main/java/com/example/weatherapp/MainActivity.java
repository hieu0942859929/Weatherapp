package com.example.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import io.paperdb.Paper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifImageButton;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.weatherapp.Notification_App.notificationApp;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBarTemp, mProgressBarRun, circle_progress_bar_rain;
    private final Handler handler = new Handler();
    ImageButton btnSearch, buttonTemp;
    GifImageButton imageBtnRadar;
    Button recommendation;
    ImageView imageView, imageInProgress, imageRain,
            ivCountry1, ivCountry2, ivCountry3,
            ivCountry4, ivCountry5, ivCountry6;
    TextClock time;
    TextView temptv, date,
            country, city, status,
            pop, humidity1, WindSpeed, visibility, SeaLevel, clouds,
            tvDirection, tvWindSpeed, tvPressure,
            tvCircleRun, tvCircleStatusRun,
            tvCircleRain, tvCircleOfTemp,
            airQuality, fQMatter, cQMatter, CO, NO, O3, NH3, NO2, SO2,
            UV,
            infectedWorld1, infectedVN1, deathOfWorld2, deathOfVN2, recoverOfWorld3, recoverOfVN3,
            infectedWorld4, infectedVN4, deathOfWorld5, deathOfVN5, recoverOfWorld6, recoverOfVN6,
            vaccine, tests, active, weatherWithCovid;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView listView;
    Custom_Adapter_Weather custom_adapter_weather;
    List<Weather> list = new ArrayList<>();
    View scrollView;
    GridView gvPerDay1;
    Custom_Adapter_24h customAdapter24h;
    ArrayList<Weather24h> weather_per_days = new ArrayList<>();

    View decorView;

    String ApiKeyUser = "9c1a8bcb1eb6a049d4ffe2630e2af750";

    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        setClick();
        notificationManager = NotificationManagerCompat.from(this);
        Paper.init(this);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        recommendation.setPaintFlags(recommendation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvCircleStatusRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaLogTemp dialog = new DiaLogTemp();
                dialog.show(getSupportFragmentManager(), "Test");

            }
        });

        CovidStatusOfWorld();
        callAPI1Day();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }

    private int hideSystemBar() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public void setClick() {

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Recommendation.class);
                startActivity(intent);
            }
        });
    }


    private void Mapping() {
        decorView = getWindow().getDecorView();

        listView = findViewById(R.id.lv7days);
        custom_adapter_weather = new Custom_Adapter_Weather(this, list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(custom_adapter_weather);

        imageView = findViewById(R.id.imageView);
        temptv = findViewById(R.id.textView3);
        time = findViewById(R.id.tvTime);
        date = findViewById(R.id.tvDate);
        tvDirection = findViewById(R.id.tvDirection);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvPressure = findViewById(R.id.tvPressure);
        mProgressBarTemp = findViewById(R.id.circle_progress_bar_temp);
        mProgressBarRun = findViewById(R.id.circle_progress_bar_run);
        country = findViewById(R.id.tvCountry);
        city = findViewById(R.id.tvCity);
        pop = findViewById(R.id.pop);
        humidity1 = findViewById(R.id.humidity1);
        WindSpeed = findViewById(R.id.WindSpeed);
        visibility = findViewById(R.id.visibility);
        SeaLevel = findViewById(R.id.SeaLevel);
        clouds = findViewById(R.id.clouds);
        gvPerDay1 = findViewById(R.id.gvPerDay1);
        tvCircleRun = findViewById(R.id.tvCircleRun);
        imageInProgress = findViewById(R.id.imageInProgress);
        tvCircleStatusRun = findViewById(R.id.tvCircleStatusRun);
        tvCircleRain = findViewById(R.id.tvCircleRain);
        circle_progress_bar_rain = findViewById(R.id.circle_progress_bar_rain);
        imageRain = findViewById(R.id.imageRain);
        tvCircleOfTemp = findViewById(R.id.tvCircleOfTemp);
        status = findViewById(R.id.status);
        airQuality = findViewById(R.id.airQuality);
        fQMatter = findViewById(R.id.fQMatter);
        cQMatter = findViewById(R.id.cQMatter);
        NO = findViewById(R.id.NO);
        NH3 = findViewById(R.id.NH3);
        O3 = findViewById(R.id.O3);
        CO = findViewById(R.id.CO);
        NO2 = findViewById(R.id.NO2);
        SO2 = findViewById(R.id.SO2);
        UV = findViewById(R.id.UV);
        imageBtnRadar = findViewById(R.id.imageBtnRadar);
        btnSearch = findViewById(R.id.btnSearch);
        scrollView = this.getWindow().getDecorView();


        ivCountry1 = findViewById(R.id.ivCountry1);
        ivCountry2 = findViewById(R.id.ivCountry2);
        ivCountry3 = findViewById(R.id.ivCountry3);
        ivCountry4 = findViewById(R.id.ivCountry4);
        ivCountry5 = findViewById(R.id.ivCountry5);
        ivCountry6 = findViewById(R.id.ivCountry6);

        infectedWorld1 = findViewById(R.id.infectedWorld1);
        infectedVN1 = findViewById(R.id.infectedVN1);
        deathOfWorld2 = findViewById(R.id.deathOfWorld2);
        deathOfVN2 = findViewById(R.id.deathOfVN2);
        recoverOfWorld3 = findViewById(R.id.recoverOfWorld3);
        recoverOfVN3 = findViewById(R.id.recoverOfVN3);

        infectedWorld4 = findViewById(R.id.infectedWorld4);
        infectedVN4 = findViewById(R.id.infectedVN4);
        deathOfWorld5 = findViewById(R.id.deathOfWorld5);
        deathOfVN5 = findViewById(R.id.deathOfVN5);
        recoverOfWorld6 = findViewById(R.id.recoverOfWorld6);
        recoverOfVN6 = findViewById(R.id.recoverOfVN6);

        recommendation = findViewById(R.id.recommendation);

        vaccine = findViewById(R.id.vaccine);
        tests = findViewById(R.id.tests);
        active = findViewById(R.id.active);

        weatherWithCovid = findViewById(R.id.weatherWithCovid);
        buttonTemp = findViewById(R.id.buttonTemp);
    }


    private void callAPI1Day() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            //hiển thị thông tin chi tiết
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            double lat1 = addresses.get(0).getLatitude();
                            double lon1 = addresses.get(0).getLongitude();

                            final String countryCode = addresses.get(0).getCountryCode();
                            final String lowerCaseCode = countryCode.toLowerCase();
                            @SuppressLint("DefaultLocale") final String lat = String.format("%.6f", lat1);
                            @SuppressLint("DefaultLocale") final String lon = String.format("%.6f", lon1);

                            String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&lang=vi&appid=" + ApiKeyUser + "&units=metric";

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onResponse(String response) {
                                            try {

                                                //lấy dữ liệu từ response
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                                for (int i = 1; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);

                                                    //lấy dữ liệu nhiệt độ
                                                    JSONObject jsonObjectList1 = jsonArray.getJSONObject(1);
                                                    JSONObject objectMain = jsonObjectList1.getJSONObject("main");
                                                    final double temp1 = objectMain.getDouble("temp");
                                                    temptv.setText(Math.round(temp1) + "°C");


                                                    JSONObject objectMain1 = jsonObjectList.getJSONObject("main");
                                                    final double temp = objectMain1.getDouble("temp");


                                                    //lấy dữ liệu dt và convert sang ngày tháng
                                                    String ngay = jsonObjectList.getString("dt");

                                                    long l1 = Long.parseLong(ngay);
                                                    Date date1 = new Date(l1 * 1000L);
                                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE HH:mm");
                                                    String day = simpleDateFormat.format(date1);

                                                    long l2 = Long.parseLong(ngay);
                                                    Date date2 = new Date(l2 * 1000L);
                                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                                                    String per = simpleDateFormat2.format(date2);

                                                    long l3 = Long.parseLong(ngay);
                                                    Date date3 = new Date(l3 * 1000L);
                                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("EEEE");
                                                    String per1 = simpleDateFormat3.format(date3);


                                                    //lấy đất nước
                                                    JSONObject objCity = jsonObject.getJSONObject("city");
                                                    String count = objCity.getString("country");
                                                    country.setText(count + "  |  ");


                                                    //lấy thành phố
                                                    String objName = objCity.getString("name");
                                                    city.setText(objName);

                                                    //lấy icon
                                                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                                    JSONObject obj = jsonArrayWeather.getJSONObject(0);
                                                    String icon = obj.getString("icon");
                                                    Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageView);

                                                    //lấy ngày giờ hiện tại
                                                    Calendar calendar = Calendar.getInstance();
                                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat std = new SimpleDateFormat("E, dd MMM yyyy");
                                                    String dateFormat = std.format(calendar.getTime());
                                                    date.setText(dateFormat);

                                                    //độ ẩm
                                                    int humidity_find = objectMain.getInt("humidity");
                                                    humidity1.setText(humidity_find + "%");

                                                    //tầm nhìn
                                                    final int visibility1 = jsonObjectList.getInt("visibility");

                                                    //lấy dữ liệu mây
                                                    JSONObject object7 = jsonObjectList.getJSONObject("clouds");
                                                    double clouds1 = object7.getDouble("all");
                                                    clouds.setText(clouds1 + "%");

                                                    //lấy dữ liệu sức ép
                                                    final double pressure_find = objectMain.getDouble("pressure");
                                                    tvPressure.setText(pressure_find + " hPa");


                                                    //xác định hướng gió và tốc độ
                                                    JSONObject objectWind = jsonObjectList.getJSONObject("wind");
                                                    final int wind_find = objectWind.getInt("speed");
                                                    visibility.setText(visibility1 / 1000 + "km/h");
                                                    tvWindSpeed.setText(wind_find + "km/h");
                                                    WindSpeed.setText(wind_find + "km/h");

                                                    double deg = objectWind.getDouble("deg");
                                                    if ((deg >= 348.75) && (deg <= 360) ||
                                                            (deg >= 0) && (deg <= 11.25)) {
                                                        tvDirection.setText("Bắc");
                                                    } else if ((deg >= 11.25) && (deg <= 33.75)) {
                                                        tvDirection.setText("Bắc Đông Bắc");
                                                    } else if ((deg >= 33.75) && (deg <= 56.25)) {
                                                        tvDirection.setText("Đông Bắc");
                                                    } else if ((deg >= 56.25) && (deg <= 78.75)) {
                                                        tvDirection.setText("Đông Đông Bắc");
                                                    } else if ((deg >= 78.75) && (deg <= 101.25)) {
                                                        tvDirection.setText("Đông");
                                                    } else if ((deg >= 101.25) && (deg <= 123.75)) {
                                                        tvDirection.setText("Đông Đông Nam");
                                                    } else if ((deg >= 123.75) && (deg <= 146.25)) {
                                                        tvDirection.setText("Đông Nam");
                                                    } else if ((deg >= 146.25) && (deg <= 168.75)) {
                                                        tvDirection.setText("Nam Đông Nam");
                                                    } else if ((deg >= 168.75) && (deg <= 191.25)) {
                                                        tvDirection.setText("Nam");
                                                    } else if ((deg >= 191.25) && (deg <= 213.75)) {
                                                        tvDirection.setText("Nam Tây Nam");
                                                    } else if ((deg >= 213.75) && (deg <= 236.25)) {
                                                        tvDirection.setText("Tây Nam");
                                                    } else if ((deg >= 236.25) && (deg <= 258.75)) {
                                                        tvDirection.setText("Tây Tây Nam");
                                                    } else if ((deg >= 258.75) && (deg <= 281.25)) {
                                                        tvDirection.setText("Tây");
                                                    } else if ((deg >= 281.25) && (deg <= 303.75)) {
                                                        tvDirection.setText("Tây Tây Bắc");
                                                    } else if ((deg >= 303.75) && (deg <= 326.25)) {
                                                        tvDirection.setText("Tây Bắc");
                                                    } else if ((deg >= 326.25) && (deg <= 348.75)) {
                                                        tvDirection.setText("Bắc Tây Bắc");
                                                    } else {
                                                        tvDirection.setText("...");
                                                    }

                                                    //lấy độ kết tủa
                                                    double objPop = jsonObjectList.getDouble("pop");
                                                    pop.setText(objPop + "%");

                                                    //lấy nhiệt độ thấp nhất
                                                    double mintemp = objectMain.getDouble("temp_min");

                                                    //lấy nhiệt độ cao nhất
                                                    double maxtemp = objectMain.getDouble("temp_max");

                                                    DecimalFormat df = new DecimalFormat("#");
                                                    df.setRoundingMode(RoundingMode.CEILING);
                                                    String mintempFormat = df.format(mintemp);
                                                    String maxtempFormat = df.format(maxtemp);

                                                    //lấy trạng thái thời tiết
                                                    JSONArray jsonArrayDes = jsonObjectList.getJSONArray("weather");
                                                    JSONObject jsonObjectDes = jsonArrayDes.getJSONObject(0);
                                                    String description = jsonObjectDes.getString("description");
                                                    String firstLetter = description.substring(0, 1);
                                                    String remainingLetters = description.substring(1);
                                                    firstLetter = firstLetter.toUpperCase();
                                                    description = firstLetter + remainingLetters;

                                                    //lấy main trong weather
                                                    String main = jsonObjectDes.getString("main");

                                                    //xác định sáng, ngày
                                                    JSONObject jsonObjectSys = jsonArray.getJSONObject(1);
                                                    JSONObject sysList = jsonObjectSys.getJSONObject("sys");
                                                    String sys = sysList.getString("pod");
                                                    if (sys.equals("d")) {
                                                        scrollView.setBackgroundResource(R.drawable.capday);
                                                    } else if (sys.equals("n")) {
                                                        scrollView.setBackgroundResource(R.drawable.capnight);
                                                    }

                                                    //lấy dữ liệu feels để gắn cho function
                                                    double feels_find = objectMain.getDouble("feels_like");

                                                    feelLike(temp, feels_find);
                                                    Exercise(temp, humidity_find, visibility1, main);

                                                    //lấy dữ liệu mực nước biển
                                                    int sea_level = objectMain.getInt("sea_level");
                                                    SeaLevel.setText(sea_level / 1000 + "km");

                                                    //xác định LAT,LON
                                                    JSONObject objectCoord = objCity.getJSONObject("coord");
                                                    final double lat = objectCoord.getDouble("lat");
                                                    final double lon = objectCoord.getDouble("lon");
                                                    imageBtnRadar.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                                            intent.putExtra("lat", lat);
                                                            intent.putExtra("lon", lon);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    //gán dữ liệu vào listview
                                                    weather_per_days.add(new Weather24h(per1, per, description, mintempFormat, maxtempFormat, icon));
                                                    customAdapter24h = new Custom_Adapter_24h(MainActivity.this, 0, weather_per_days);
                                                    gvPerDay1.setAdapter(customAdapter24h);

                                                    //gán dữ liệu vào recycleView
                                                    list.add(new Weather(temp, day, icon));

                                                    AirPollution(lat, lon);
                                                    OneCall(lat, lon);
                                                    CovidStatusOfCountry(lowerCaseCode, temp1, humidity_find);
                                                    Vaccine(lowerCaseCode);
                                                    DiaLog(visibility1, wind_find, pressure_find, sys, description, main);


                                                    //lấy lượng mưa
                                                    if (jsonObjectList1.has("rain")) {
                                                        JSONObject jsonObjectRain = jsonObjectList1.getJSONObject("rain");
                                                        double rain = jsonObjectRain.getDouble("3h");
                                                        Rain(rain, icon);
                                                    } else {
                                                        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageRain);
                                                        tvCircleRain.setText("Không có mưa");
                                                    }
                                                }
                                                customAdapter24h.notifyDataSetChanged();
                                                custom_adapter_weather.notifyDataSetChanged();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                            requestQueue.add(stringRequest);
                            requestQueue.getCache().clear();
                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            }, 10000);
        }
    }

    public void DiaLog(final double visibility1, final double wind_find,
                       final double pressure_find, final String sys, final String desciption,
                       final String main) {
        buttonTemp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonTemp);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.km:
                                visibility.setText(visibility1 / 1000 + " km/h");
                                tvWindSpeed.setText(wind_find + " km/h");
                                WindSpeed.setText(wind_find + " km/h");
                                break;
                            case R.id.m:
                                visibility.setText(visibility1 + " m/h");
                                tvWindSpeed.setText(wind_find * 1000 + " m/h");
                                WindSpeed.setText(wind_find * 1000 + " m/h");
                                break;
                            case R.id.mBar:
                                tvPressure.setText(pressure_find + " hPa");
                                break;
                            case R.id.bar:
                                tvPressure.setText(pressure_find * 1000 + " bar");
                                break;
                            case R.id.psi:
                                tvPressure.setText(pressure_find * 14500 + " psi");
                                break;
                            case R.id.inHg:
                                tvPressure.setText(pressure_find * 29500 + " inHg");
                                break;
                            case R.id.mmHg:
                                tvPressure.setText(pressure_find * 750000 + " mmHg");
                                break;
                            case R.id.turnOn:
                                if (sys.equals("d")) {
                                    scrollView.setBackgroundResource(R.drawable.capday);
                                } else if (sys.equals("n")) {
                                    scrollView.setBackgroundResource(R.drawable.capnight);
                                }
                                break;
                            case R.id.turnOff:
                                scrollView.setBackgroundResource(R.color.colorBackGround);
                                break;
                            case R.id.turnOn_No:
                                startService(main);
                                break;
                            case R.id.turnOff_No:
                                stopService();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    public void startService(String main) {
        Notification.Builder notification = new Notification.Builder(this, notificationApp);
        notification.setContentText("Dự báo thời tiết");
        notification.setSmallIcon(R.drawable.icon_global);
        notification.setContentTitle("Nhiệt độ hiện tại là " + temptv.getText().toString());
        notification.setContentText("Trạng thái: " + status.getText().toString());
        if (main.equals("Rain")) {
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.rain));
        } else if (main.equals("Clouds")) {
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.wind));
        } else {
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sun));
        }
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, notification.build());
    }

    public void stopService() {
        notificationManager.cancel(1);
    }


    //lấy dữ liệu cảm giác không khí
    public void feelLike(final double temp, final double feellike) {
        new Thread(new Runnable() {
            double i = 0;
            double progressStatus = 0.0;

            @SuppressLint("SetTextI18n")
            public void run() {
                if (temp > 60) {
                    progressStatus = 86;
                    tvCircleOfTemp.setText("Cực kỳ Nguy hiểm, cảm giác như " + feellike + "°C");
                } else if (temp > 50 && temp < 60) {
                    progressStatus = 72;
                    tvCircleOfTemp.setText("Nguy hiểm, cảm giác như " + feellike + "°C");
                } else if (temp > 40 && temp < 50) {
                    progressStatus = 58;
                    tvCircleOfTemp.setText("Cực kì chú ý, cảm giác như " + feellike + "°C");
                } else if (temp > 30 && temp < 40) {
                    tvCircleOfTemp.setText("Cẩn trọng, cảm giác như " + feellike + "°C");
                    progressStatus = 44;
                } else if (temp > 20 && temp < 30) {
                    progressStatus = 32;
                    tvCircleOfTemp.setText("Không có rủi ro nhiệt, cảm giác như " + feellike + "°C");
                } else if (temp > 10 && temp < 20) {
                    progressStatus = 18;
                    tvCircleOfTemp.setText("Cẩn trọng, cảm giác như " + feellike + "°C");
                } else {
                    progressStatus = 4;
                    tvCircleOfTemp.setText("Cực kỳ chú ý, cảm giác như " + feellike + "°C");
                }
                handler.post(new Runnable() {
                    public void run() {
                        mProgressBarTemp
                                .setProgress((int) progressStatus);
                        i++;
                    }
                });
            }
        }).start();
    }


    //Khả năng tập thể dục
    private void Exercise(final double temp, final double humidity,
                          final double visibility, final String main) {
        new Thread(new Runnable() {
            double i = 0;
            double progressStatus = 0.0;

            @SuppressLint("SetTextI18n")
            public void run() {
                //những trường hợp không thể chạy bộ
                if (temp > 60 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp < 60 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 60 && humidity < 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 60 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 50 && temp < 60 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 50 && temp < 60 && humidity < 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 50 && temp < 60 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 40 && temp < 50 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 40 && temp < 50 && humidity < 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 40 && temp < 50 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 30 && temp < 40 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    tvCircleRun.setText("Không thể chạy bộ");
                    progressStatus = 100;
                } else if (temp > 30 && temp < 40 && humidity < 90 && visibility > 7000 && main.contains("Rain")) {
                    tvCircleRun.setText("Không thể chạy bộ");
                    progressStatus = 100;
                } else if (temp > 30 && temp < 40 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    tvCircleRun.setText("Không thể chạy bộ");
                    progressStatus = 100;
                } else if (temp > 20 && temp < 30 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 20 && temp < 30 && humidity < 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity > 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity < 90 && visibility > 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity > 90 && visibility < 7000 && main.contains("Rain")) {
                    progressStatus = 100;
                    tvCircleRun.setText("Không thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không nên chạy bộ");
                } else if (temp > 60 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không nên chạy bộ");
                } else if (temp > 20 && temp < 30 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không nên chạy bộ");
                } else if (temp > 30 && temp < 40 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    tvCircleRun.setText("Không nên chạy bộ");
                    progressStatus = 66;
                } else if (temp > 40 && temp < 50 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không nên chạy bộ");
                } else if (temp > 50 && temp < 60 && humidity > 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 66;
                    tvCircleRun.setText("Không nên chạy bộ");
                }
                //Trường hợp có thể chạy bộ
                else if (temp > 40 && temp < 50 && humidity < 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 40 && temp < 50 && humidity > 90 && visibility < 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 30 && temp < 40 && humidity < 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 30 && temp < 40 && humidity > 90 && visibility < 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 20 && temp < 30 && humidity < 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 20 && temp < 30 && humidity > 90 && visibility < 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity < 90 && visibility > 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else if (temp > 10 && temp < 20 && humidity > 90 && visibility < 7000 && !main.contains("Rain")) {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                } else {
                    progressStatus = 33;
                    tvCircleRun.setText("Có thể chạy bộ");
                }
                handler.post(new Runnable() {
                    public void run() {
                        mProgressBarRun
                                .setProgress((int) progressStatus);
                        i++;
                    }
                });
            }
        }).start();
    }

    //lấy dữ liệu UV
    private void OneCall(final double lat, final double lon) {
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&lang=vi&exclude=hourly,daily&appid=" + ApiKeyUser;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject current = jsonObject.getJSONObject("current");
                    JSONArray objWeather = current.getJSONArray("weather");
                    JSONObject objIcon = objWeather.getJSONObject(0);
                    String description = objIcon.getString("description");
                    String firstLetter = description.substring(0, 1);
                    String remainingLetters = description.substring(1);
                    firstLetter = firstLetter.toUpperCase();
                    description = firstLetter + remainingLetters;
                    status.setText(description);
                    String icon = objIcon.getString("icon");
                    double uv = current.getDouble("uvi");
                    if (uv == 0) {
                        UV.setText("Nguy hại thấp, chỉ số UV " + uv);
                    } else if (uv > 0 && uv < 2.9) {
                        UV.setText("Nguy hại thấp, chỉ số UV " + uv);
                    } else if (uv > 3.0 && uv < 5.6) {
                        UV.setText("Nguy hại trung bình, chỉ số UV " + uv);
                    } else if (uv > 6.0 && uv < 7.9) {
                        UV.setText("Nguy hại cao, chỉ số UV " + uv);
                    } else if (uv > 8.0 && uv < 10.9) {
                        UV.setText("Nguy hại rất cao, chỉ số UV " + uv);
                    } else if (uv > 11.0) {
                        UV.setText("Nguy hại cực cao, chỉ số UV " + uv);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    //lấy dữ liệu mưa
    private void Rain(final double rain, final String icon) {
        new Thread(new Runnable() {
            double i = 0;
            double progressStatus = 0.0;

            @SuppressLint("SetTextI18n")
            public void run() {
                if (rain > 0 && rain < 3) {
                    progressStatus = 50;
                    tvCircleRain.setText("Mưa nhỏ, khoảng " + rain + "mm");
                } else if (rain >= 3 && rain < 7) {
                    progressStatus = 75;
                    tvCircleRain.setText("Mưa vừa, khoảng " + rain + "mm");
                } else if (rain >= 7) {
                    progressStatus = 100;
                    tvCircleRain.setText("Mưa lớn, khoảng " + rain + "mm");
                }
                handler.post(new Runnable() {
                    public void run() {
                        circle_progress_bar_rain
                                .setProgress((int) progressStatus);
                        i++;
                        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageRain);
                    }
                });
            }
        }).start();
    }

    //lấy dữ liệu không khí
    private void AirPollution(double lat, double lon) {
        String url = "http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + lat + "&lon=" + lon +
                "&appid=" + ApiKeyUser;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectList = jsonArray.getJSONObject(0);
                        JSONObject objectMain = jsonObjectList.getJSONObject("main");
                        int aqi = objectMain.getInt("aqi");
                        if (aqi == 1) {
                            airQuality.setText("Không khí tốt");
                        } else if (aqi == 2) {
                            airQuality.setText("Không khí dễ chịu");
                        } else if (aqi == 3) {
                            airQuality.setText("Không khí ôn hòa ");
                        } else if (aqi == 4) {
                            airQuality.setText("Không khí xấu");
                        } else if (aqi == 5) {
                            airQuality.setText("Không khí rất xấu");
                        }
                        JSONObject objectComponents = jsonObjectList.getJSONObject("components");
                        double objectfQMatter = objectComponents.getDouble("pm2_5");
                        fQMatter.setText(objectfQMatter + "μg/m3");
                        double objectcQMatter = objectComponents.getDouble("pm10");
                        cQMatter.setText(objectcQMatter + "μg/m3");
                        double objectCO = objectComponents.getDouble("co");
                        CO.setText(objectCO + "μg/m3");
                        double objectNO = objectComponents.getDouble("no");
                        NO.setText(objectNO + "μg/m3");
                        double objectNO2 = objectComponents.getDouble("no2");
                        NO2.setText(objectNO2 + "μg/m3");
                        double objectO3 = objectComponents.getDouble("o3");
                        O3.setText(objectO3 + "μg/m3");
                        double objectSO2 = objectComponents.getDouble("so2");
                        SO2.setText(objectSO2 + "μg/m3");
                        double objectNH3 = objectComponents.getDouble("nh3");
                        NH3.setText(objectNH3 + "μg/m3");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void CovidStatusOfWorld() {
        String url = "https://corona.lmao.ninja/v3/covid-19/all";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String cases = jsonObject.getString("cases");
                    infectedWorld1.setText(cases);

                    String death = jsonObject.getString("deaths");
                    deathOfWorld2.setText(death);

                    String recovered = jsonObject.getString("recovered");
                    recoverOfWorld3.setText(recovered);

                    String todayCase = jsonObject.getString("todayCases");
                    infectedWorld4.setText(todayCase);

                    String todayDeaths = jsonObject.getString("todayDeaths");
                    deathOfWorld5.setText(todayDeaths);

                    String todayRecovered = jsonObject.getString("todayRecovered");
                    recoverOfWorld6.setText(todayRecovered);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void CovidStatusOfCountry(final String country, final double temp,
                                      final int humidity) {
        String url = "https://corona.lmao.ninja/v2/countries/" + country;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String cases = jsonObject.getString("cases");
                    infectedVN1.setText(cases);

                    String death = jsonObject.getString("deaths");
                    deathOfVN2.setText(death);

                    String recovered = jsonObject.getString("recovered");
                    recoverOfVN3.setText(recovered);

                    String todayCase = jsonObject.getString("todayCases");
                    infectedVN4.setText(todayCase);

                    String todayDeath = jsonObject.getString("todayDeaths");
                    deathOfVN5.setText(todayDeath);

                    String todayRecovered = jsonObject.getString("todayRecovered");
                    recoverOfVN6.setText(todayRecovered);

                    String covidTest = jsonObject.getString("tests");
                    tests.setText(covidTest);

                    String covidActive = jsonObject.getString("active");
                    active.setText(covidActive);

                    if ((temp > 4 && temp < 30) && (humidity < 60 || humidity > 85)) {
                        weatherWithCovid.setText("Chưa phải điều kiện lý tưởng để diệt virus, không nên ra ngoài do nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if ((temp > 4 && temp < 30) && (humidity > 60 && humidity < 85)) {
                        weatherWithCovid.setText("Chưa phải điều kiện lý tưởng để diệt virus, không nên ra ngoài do nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if (temp < 4 && (humidity > 60 && humidity < 85)) {
                        weatherWithCovid.setText("Chưa phải điều kiện lý tưởng để diệt virus, không nên ra ngoài do nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if (temp > 30 && (humidity > 60 && humidity < 85)) {
                        weatherWithCovid.setText("Khả năng lây bệnh thấp, hạn chế ra ngoài do nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if ((temp > 4 && temp < 30) && humidity < 60) {
                        weatherWithCovid.setText("Chưa phải điều kiện lý tưởng để diệt virus, không nên ra ngoài nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if ((temp > 4 && temp < 30) && humidity > 85) {
                        weatherWithCovid.setText("Chưa phải điều kiện lý tưởng để diệt virus, không nên ra ngoài nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    } else if (temp > 30 && (humidity < 60 || humidity > 85)) {
                        weatherWithCovid.setText("Khả năng lây bệnh khá cao, hạn chế ra ngoài nhiệt độ là " + temp + "°C và độ ẩm là " + humidity + "%");
                    }

                    JSONObject jsonObjectCountry = jsonObject.getJSONObject("countryInfo");
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry1);
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry2);
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry3);
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry4);
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry5);
                    Picasso.get().load("https://disease.sh/assets/img/flags/" + country + ".png").into(ivCountry6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        requestQueue.getCache().

                clear();
    }

    private void Vaccine(String country) {
        String url = "https://corona.lmao.ninja/v3/covid-19/vaccine/coverage/countries/" + country + "?lastdays=1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectTimeLine = jsonObject.getJSONObject("timeline");
                    String k = jsonObjectTimeLine.keys().next();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObjectTimeLine);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String a = jsonArray.getString(i);
                        String jsonFormattedString = a.replaceAll("\\\\", "");
                        vaccine.setText(jsonObjectTimeLine.getString(k));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
}
