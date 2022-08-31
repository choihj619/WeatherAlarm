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

    TextView temp_tv, min_max_tv, dust_tv, humidity_tv, feel_like_tv;
    ImageView weather_iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        temp_tv = v.findViewById(R.id.temp_tv);
        min_max_tv = v.findViewById(R.id.min_max_tv);
        dust_tv = v.findViewById(R.id.dust_tv);
        humidity_tv = v.findViewById(R.id.humidity_tv);
        feel_like_tv = v.findViewById(R.id.feel_like_tv);

        weather_iv = v.findViewById(R.id.weather_iv);

        String temp = String.valueOf(getArguments().getDouble("temp"));
        String temp_min = String.valueOf(getArguments().getDouble("temp_min"));
        String temp_max = String.valueOf(getArguments().getDouble("temp_max"));
        String feels_like = String.valueOf(getArguments().getDouble("feels_like"));
        String humidity = String.valueOf(getArguments().getDouble("humidity"));
        String weatherMain = getArguments().getString("weather");


            temp_tv.setText(temp);
            min_max_tv.setText(temp_min + " / " + temp_max);

            humidity_tv.setText(humidity);
            feel_like_tv.setText(feels_like);

            weather_iv.setImageDrawable(setWeatherImage(weatherMain));

        return v;
    }

    private Drawable setWeatherImage(String weather){
        String imageBaseUrl = "http://openweathermap.org/img/wn/";
        if(weather == "Thunderstorm"){
            return LoadImageFromWebOperations(imageBaseUrl + "11d@2x.png", weather);
        } else if(weather == "Drizzle"){
            return LoadImageFromWebOperations(imageBaseUrl + "09d@2x.png", weather);
        } else if(weather == "Rain"){
            return LoadImageFromWebOperations(imageBaseUrl + "10d@2x.png", weather);
        } else if(weather == "Snow"){
            return LoadImageFromWebOperations(imageBaseUrl + "13d@2x.png", weather);
        }else if(weather == "Atmosphere"){
            return LoadImageFromWebOperations(imageBaseUrl + "50d@2x.png", weather);
        }else if(weather == "Clear"){
            return LoadImageFromWebOperations(imageBaseUrl + "01d@2x.png", weather);
        }else if(weather == "Clouds"){
            return LoadImageFromWebOperations(imageBaseUrl + "02d@2x.png", weather);
        }
        return null;
    }

    public static Drawable LoadImageFromWebOperations(String url, String weather) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, weather);
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}


