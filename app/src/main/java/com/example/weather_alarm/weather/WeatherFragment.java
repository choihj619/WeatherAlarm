package com.example.weather_alarm.weather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather_alarm.LoadingActivity;
import com.example.weather_alarm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    TextView temp_tv, dust_tv, humidity_tv, feel_like_tv;
    LinearLayout background;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        temp_tv = v.findViewById(R.id.temp_tv);
        dust_tv = v.findViewById(R.id.dust_tv);
        humidity_tv = v.findViewById(R.id.humidity_tv);
        feel_like_tv = v.findViewById(R.id.feel_like_tv);

        background = v.findViewById(R.id.background);

        String temp = String.valueOf(getArguments().getDouble("temp"));
        String feels_like = String.valueOf(getArguments().getDouble("feels_like"));
        String humidity = String.valueOf(getArguments().getDouble("humidity"));
        String weatherMain = getArguments().getString("weather");
        String pm10 = String.valueOf(getArguments().getDouble("pm10"));

        Log.d("weather", weatherMain);

        temp_tv.setText(temp + "℃");

        humidity_tv.setText("습도\n" + humidity + "%");
        feel_like_tv.setText("체감온도\n"+feels_like + "℃");
        dust_tv.setText("미세먼지\n"+pm10);

        setWeatherImage(weatherMain);


        return v;
    }

    private void setWeatherImage(String weather){
        if(weather.equals("Thunderstorm")){
            background.setBackgroundResource(R.drawable.thunderstorm);
        } else if(weather.equals("Rain")){
            background.setBackgroundResource(R.drawable.rain);
        } else if(weather.equals("Snow")){
            background.setBackgroundResource(R.drawable.snow);
        }else if(weather.equals("Clear")){
            background.setBackgroundResource(R.drawable.clear);
        }else if(weather.equals("Clouds")){
            background.setBackgroundResource(R.drawable.clouds);
        }

    }



}


