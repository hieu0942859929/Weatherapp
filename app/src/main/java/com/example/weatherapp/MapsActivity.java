package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button button, buttonBack;
    ImageView imageFluctuation;
    TextView tvMap;
    View decorView;
    String ApiKeyUser = "850d49e6018a05886ea43789072b2890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        button = findViewById(R.id.button);
        imageFluctuation = findViewById(R.id.imageFluctuation);
        buttonBack = findViewById(R.id.buttonBack);
        tvMap = findViewById(R.id.tvMap);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });
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

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 0);
        double lon = intent.getDoubleExtra("lon", 0);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8));
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MapsActivity.this, button);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.one) {
                            tvMap.setText("Bản đồ áp suất");
                            googleMap.clear();
                            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                                @Override
                                public URL getTileUrl(int x, int y, int zoom) {
                                    String s = String.format(Locale.US, "https://tile.openweathermap.org/map/pressure_new/%d/%d/%d.png?appid=" + ApiKeyUser,
                                            zoom, x, y);
                                    imageFluctuation.setImageResource(R.drawable.pressure_fluctuation);
                                    try {
                                        return new URL(s);
                                    } catch (MalformedURLException e) {
                                        throw new AssertionError(e);
                                    }
                                }
                            };
                            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(tileProvider));
                        } else if (item.getItemId() == R.id.two) {
                            tvMap.setText("Bản đồ lượng mưa");
                            googleMap.clear();
                            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                                @Override
                                public URL getTileUrl(int x, int y, int zoom) {
                                    String s = String.format(Locale.US, "https://tile.openweathermap.org/map/precipitation_new/%d/%d/%d.png?appid=" + ApiKeyUser,
                                            zoom, x, y);
                                    imageFluctuation.setImageResource(R.drawable.transparent);
                                    try {
                                        return new URL(s);
                                    } catch (MalformedURLException e) {
                                        throw new AssertionError(e);
                                    }
                                }
                            };
                            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(tileProvider));
                        } else if (item.getItemId() == R.id.three) {
                            tvMap.setText("Bản đồ mây");
                            googleMap.clear();
                            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                                @Override
                                public URL getTileUrl(int x, int y, int zoom) {
                                    String s = String.format(Locale.US, "https://tile.openweathermap.org/map/clouds_new/%d/%d/%d.png?appid=" + ApiKeyUser,
                                            zoom, x, y);
                                    imageFluctuation.setImageResource(R.drawable.transparent);
                                    try {
                                        return new URL(s);
                                    } catch (MalformedURLException e) {
                                        throw new AssertionError(e);
                                    }
                                }
                            };
                            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(tileProvider));
                        } else if (item.getItemId() == R.id.four) {
                            tvMap.setText("Bản đồ gió");
                            googleMap.clear();
                            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                                @Override
                                public URL getTileUrl(int x, int y, int zoom) {
                                    String s = String.format(Locale.US, "https://tile.openweathermap.org/map/wind_new/%d/%d/%d.png?appid=" + ApiKeyUser,
                                            zoom, x, y);
                                    imageFluctuation.setImageResource(R.drawable.transparent);
                                    try {
                                        return new URL(s);
                                    } catch (MalformedURLException e) {
                                        throw new AssertionError(e);
                                    }
                                }
                            };
                            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(tileProvider));
                        } else if (item.getItemId() == R.id.five) {
                            tvMap.setText("Bản đồ nhiệt độ");
                            googleMap.clear();
                            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                                @Override
                                public URL getTileUrl(int x, int y, int zoom) {
                                    String s = String.format(Locale.US, "https://tile.openweathermap.org/map/temp_new/%d/%d/%d.png?appid=" + ApiKeyUser,
                                            zoom, x, y);
                                    imageFluctuation.setImageResource(R.drawable.temperature_fluctuations);
                                    try {
                                        return new URL(s);
                                    } catch (MalformedURLException e) {
                                        throw new AssertionError(e);
                                    }
                                }
                            };
                            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(tileProvider));
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
