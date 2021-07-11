package com.example.weatherapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Recommendation extends AppCompatActivity {

    Button back, play_covid_5k, play_covid_hocsinh, play_covid_laodong, play_khuyen_cao_chung,
            play_thoiquencanthaydoi, play_hdcachlytainha, play_canlamgikhiho, play_phuongtiencongcong,
            play_huongdandeokhautrang, play_thamgiagiaothong, play_phanbiet, play_ruatay,
            play_tiemvaccin, play_cachlytainha;
    VideoView covid_5k, covid_hocsinh, covid_laodong, khuyen_cao_chung,
            thoiquencanthaydoi, hdcachlytainha, canlamgikhiho, phuongtiencongcong,
            huongdandeokhautrang, thamgiagiaothong, phanbiet, ruatay,
            tiemvaccin, cachlytainha;
    View decorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recommendation);
        Mapping();
        onClick();
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

    private void Mapping() {
        back = findViewById(R.id.back);

        play_covid_5k = findViewById(R.id.play_covid_5k);
        covid_5k = findViewById(R.id.covid_5k);

        play_covid_hocsinh = findViewById(R.id.play_covid_hocsinh);
        covid_hocsinh = findViewById(R.id.covid_hocsinh);

        play_covid_laodong = findViewById(R.id.play_covid_laodong);
        covid_laodong = findViewById(R.id.covid_laodong);

        play_khuyen_cao_chung = findViewById(R.id.play_khuyen_cao_chung);
        khuyen_cao_chung = findViewById(R.id.khuyen_cao_chung);

        play_thoiquencanthaydoi = findViewById(R.id.play_thoiquencanthaydoi);
        thoiquencanthaydoi = findViewById(R.id.thoiquencanthaydoi);

        play_hdcachlytainha = findViewById(R.id.play_hdcachlytainha);
        hdcachlytainha = findViewById(R.id.hdcachlytainha);

        play_canlamgikhiho = findViewById(R.id.play_canlamgikhiho);
        canlamgikhiho = findViewById(R.id.canlamgikhiho);

        play_phuongtiencongcong = findViewById(R.id.play_phuongtiencongcong);
        phuongtiencongcong = findViewById(R.id.phuongtiencongcong);

        play_huongdandeokhautrang = findViewById(R.id.play_huongdandeokhautrang);
        huongdandeokhautrang = findViewById(R.id.huongdandeokhautrang);

        play_thamgiagiaothong = findViewById(R.id.play_thamgiagiaothong);
        thamgiagiaothong = findViewById(R.id.thamgiagiaothong);

        play_phanbiet = findViewById(R.id.play_phanbiet);
        phanbiet = findViewById(R.id.phanbiet);

        play_ruatay = findViewById(R.id.play_ruatay);
        ruatay = findViewById(R.id.ruatay);

        play_tiemvaccin = findViewById(R.id.play_tiemvaccin);
        tiemvaccin = findViewById(R.id.tiemvaccin);

        play_cachlytainha = findViewById(R.id.play_cachlytainha);
        cachlytainha = findViewById(R.id.cachlytainha);

    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        play_covid_5k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covid_5k.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_5k));
                covid_5k.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(covid_5k);
                covid_5k.setMediaController(mediaController);
            }
        });

        play_covid_hocsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covid_hocsinh.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_hocsinh));
                covid_hocsinh.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(covid_hocsinh);
                covid_hocsinh.setMediaController(mediaController);
            }
        });

        play_covid_laodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covid_laodong.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_laodong));
                covid_laodong.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(covid_laodong);
                covid_laodong.setMediaController(mediaController);
            }
        });

        play_khuyen_cao_chung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khuyen_cao_chung.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.khuyen_cao_chung));
                khuyen_cao_chung.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(khuyen_cao_chung);
                khuyen_cao_chung.setMediaController(mediaController);
            }
        });

        play_thoiquencanthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoiquencanthaydoi.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_7thoiquencanthaydoi));
                thoiquencanthaydoi.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(thoiquencanthaydoi);
                thoiquencanthaydoi.setMediaController(mediaController);
            }
        });


        play_hdcachlytainha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hdcachlytainha.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_cachlytainha));
                hdcachlytainha.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(hdcachlytainha);
                hdcachlytainha.setMediaController(mediaController);
            }
        });
        play_canlamgikhiho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canlamgikhiho.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_canlamgikhiho));
                canlamgikhiho.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(canlamgikhiho);
                canlamgikhiho.setMediaController(mediaController);
            }
        });
        play_phuongtiencongcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phuongtiencongcong.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_cantranhkhisudungptcongcong));
                phuongtiencongcong.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(phuongtiencongcong);
                phuongtiencongcong.setMediaController(mediaController);
            }
        });
        play_huongdandeokhautrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huongdandeokhautrang.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_huongdandeokhautrang));
                huongdandeokhautrang.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(huongdandeokhautrang);
                huongdandeokhautrang.setMediaController(mediaController);
            }
        });
        play_thamgiagiaothong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thamgiagiaothong.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_nguoithamgiagiaothong));
                thamgiagiaothong.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(thamgiagiaothong);
                thamgiagiaothong.setMediaController(mediaController);
            }
        });
        play_phanbiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phanbiet.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_phanbietcovid));
                phanbiet.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(phanbiet);
                phanbiet.setMediaController(mediaController);
            }
        });
        play_ruatay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruatay.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_ruatay));
                ruatay.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(ruatay);
                ruatay.setMediaController(mediaController);
            }
        });
        play_tiemvaccin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiemvaccin.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_truocvasautiemvaccin));
                tiemvaccin.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(tiemvaccin);
                tiemvaccin.setMediaController(mediaController);
            }
        });
        play_cachlytainha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cachlytainha.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid_truonghopcachlytainha));
                cachlytainha.start();
                MediaController mediaController = new MediaController(Recommendation.this);
                mediaController.setMediaPlayer(cachlytainha);
                cachlytainha.setMediaController(mediaController);
            }
        });

    }
}
