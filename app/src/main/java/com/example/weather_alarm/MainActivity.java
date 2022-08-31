package com.example.weather_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_alarm.alarm.AlarmFragment;
import com.example.weather_alarm.setting.SettingFragment;
import com.example.weather_alarm.weather.WeatherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    WeatherFragment weatherFragment;
    AlarmFragment alarmFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherFragment = new WeatherFragment();
        alarmFragment = new AlarmFragment();
        settingFragment = new SettingFragment();
        bottomNavigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, weatherFragment).commit();

        Intent intent = getIntent();
        double temp = intent.getDoubleExtra("temp",0);
        double temp_min = intent.getDoubleExtra("temp_min",0);
        double temp_max = intent.getDoubleExtra("temp_max",0);
        double feels_like = intent.getDoubleExtra("feels_like",0);
        double humidity = intent.getDoubleExtra("humidity",0);
        String weather = intent.getStringExtra("weather");
        Bundle bundle = new Bundle();
        bundle.putDouble("temp", temp);
        bundle.putDouble("temp_min",temp_min);
        bundle.putDouble("temp_max",temp_max);
        bundle.putDouble("feels_like",feels_like);
        bundle.putDouble("humidity",humidity);
        bundle.putString("weather",weather);

        weatherFragment.setArguments(bundle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.weather_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, weatherFragment).commit();
                        return true;
                    case R.id.alarm_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragment).commit();
                        return true;
                    case R.id.setting_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

}