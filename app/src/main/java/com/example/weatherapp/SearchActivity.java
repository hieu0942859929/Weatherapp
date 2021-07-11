package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ImageButton back;
    ImageView imageView;
    TextView temptv, time, longitude, latitude, humidity, pressure, wind, country, city_nam, max_temp, min_temp, feels;
    View scrollViewSearch;
    RecyclerView listView;
    Custom_Adapter_Weather custom_adapter_weather;
    List<Weather> list = new ArrayList<>();
    GridView gvPerDay1;
    Custom_Adapter_24h customAdapter24h;
    ArrayList<Weather24h> weather_per_days = new ArrayList<>();
    View decorView;
    String ApiKeyUser = "850d49e6018a05886ea43789072b2890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        temptv = findViewById(R.id.textView3);
        time = findViewById(R.id.textView2);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        country = findViewById(R.id.country);
        city_nam = findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);
        feels = findViewById(R.id.feels);
        scrollViewSearch = findViewById(R.id.scrollViewSearch);
        back = findViewById(R.id.back);
        gvPerDay1 = findViewById(R.id.gvPerDay1);
        listView = findViewById(R.id.lv7days);
        custom_adapter_weather = new Custom_Adapter_Weather(SearchActivity.this, list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(custom_adapter_weather);
        setClick();

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

    public void FindWeather() {
        final String city = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + ApiKeyUser + "&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            //find temperature
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                JSONObject object = jsonObjectList.getJSONObject("main");
                                final double temp = object.getDouble("temp");
                                temptv.setText("Nhiệt độ\n" + temp + "°C");


                                //find city, country
                                JSONObject objectCity = jsonObject.getJSONObject("city");
                                String city = objectCity.getString("name");
                                String count = objectCity.getString("country");
                                country.setText(count + "  :");
                                city_nam.setText(city);

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

                                //find icon
                                JSONArray jsonArray1 = jsonObjectList.getJSONArray("weather");
                                JSONObject obj = jsonArray1.getJSONObject(0);
                                String objMain = obj.getString("main");
                                String icon = obj.getString("icon");
                                Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageView);

                                //find date & time
                                Calendar calendar = Calendar.getInstance();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                                String date = std.format(calendar.getTime());
                                time.setText(date);


                                //find longitude
                                JSONObject object3 = objectCity.getJSONObject("coord");
                                double long_find = object3.getDouble("lon");
                                double lat_find = object3.getDouble("lat");
                                latitude.setText(lat_find + "°  N");
                                longitude.setText(long_find + "°  E");

                                //find humidity
                                JSONObject object4 = jsonObjectList.getJSONObject("main");
                                int humidity_find = object4.getInt("humidity");
                                String pressure_find = object4.getString("pressure");
                                double mintemp = object4.getDouble("temp_min");
                                double maxtemp = object4.getDouble("temp_max");
                                double feels_find = object4.getDouble("feels_like");
                                feels.setText(feels_find + " °C");
                                max_temp.setText("Nhiệt độ lớn nhất\n" + maxtemp + " °C");
                                min_temp.setText("Nhiệt độ thấp nhất\n" + mintemp + " °C");
                                pressure.setText(pressure_find + "  hPa");
                                humidity.setText(humidity_find + "  %");

                                JSONArray jsonArrayDes = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectDes = jsonArrayDes.getJSONObject(0);
                                String description = jsonObjectDes.getString("description");
                                String firstLetter = description.substring(0, 1);
                                String remainingLetters = description.substring(1);
                                firstLetter = firstLetter.toUpperCase();
                                description = firstLetter + remainingLetters;

                                DecimalFormat df = new DecimalFormat("#");
                                df.setRoundingMode(RoundingMode.CEILING);
                                String mintempFormat = df.format(mintemp);
                                String maxtempFormat = df.format(maxtemp);


                                JSONObject sysList = jsonObjectList.getJSONObject("sys");
                                String sys = sysList.getString("pod");

                                if (objMain.equals("Clear")) {
                                    scrollViewSearch.setBackgroundResource(R.drawable.capsun);
                                } else if (objMain.equals("Rain")) {
                                    scrollViewSearch.setBackgroundResource(R.drawable.caprain);
                                } else if (sys.equals("n")) {
                                    scrollViewSearch.setBackgroundResource(R.drawable.capnight);
                                } else if (objMain.equals("Clouds")) {
                                    scrollViewSearch.setBackgroundResource(R.drawable.capday);
                                }


                                //find wind speed
                                JSONObject object9 = jsonObjectList.getJSONObject("wind");
                                String wind_find = object9.getString("speed");
                                wind.setText(wind_find + "  km/h");

                                weather_per_days.add(new Weather24h(per1, per, description, mintempFormat, maxtempFormat, icon));
                                customAdapter24h = new Custom_Adapter_24h(SearchActivity.this, 0, weather_per_days);
                                gvPerDay1.setAdapter(customAdapter24h);

                                list.add(new Weather(temp, day, icon));

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
                Toast.makeText(SearchActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindWeather();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
