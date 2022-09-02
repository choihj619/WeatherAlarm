package com.example.weather_alarm;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadingActivity extends AppCompatActivity {

    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String DUST_URL = "http://api.openweathermap.org/data/2.5/air_pollution";
    private static final String API_KEY = "f39177e1dc29ea25501dbcf45c9a857e";

    String weatherMain="";
    double temp=0, feels_like=0, humidity=0, pm10=0;
    double curLat, curLon;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {

                                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(this, "위치 권한을 설정해주세요", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Location curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                curLat = curLocation.getLatitude();
                                curLon = curLocation.getLongitude();


                                new MyAsyncTask().execute();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                Location curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                curLat = curLocation.getLatitude();
                                curLon = curLocation.getLongitude();
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(this, "위치 권한을 설정해주세요", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                new MyAsyncTask().execute();
                            } else {
                                Toast.makeText(this, "위치 권한을 설정해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });


    }

    private void fetchData() {

        String apiUrl = WEATHER_URL + "?lat=" + curLat + "&lon=" + curLon + "&appid=" + API_KEY + "&units=metric";
        String dustUrl = DUST_URL + "?lat=" + curLat + "&lon=" + curLon + "&appid=" + API_KEY;


        try {
            JSONObject obj1 = readUrl(apiUrl);
            JSONObject obj2 = readUrl(dustUrl);

            JSONArray weatherList = (JSONArray) obj1.get("weather");
            JSONObject weatherObj = weatherList.getJSONObject(0);
            weatherMain = weatherObj.getString("main");

            JSONObject mainObj = obj1.getJSONObject("main");
            temp = mainObj.getDouble("temp");
            feels_like = mainObj.getDouble("feels_like");
            humidity = mainObj.getDouble("humidity");

            JSONArray dustList = (JSONArray) obj2.get("list");
            JSONObject dustObj = dustList.getJSONObject(0);
            JSONObject dustComponents = (JSONObject) dustObj.get("components");
            pm10 = dustComponents.getDouble("pm10");
            Log.d("pm10",  String.valueOf(pm10));
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    private JSONObject readUrl(String argUrl){
        try {
            URL url = new URL(argUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONObject obj = new JSONObject(jsonData);
            return obj;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            fetchData();

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.putExtra("temp", temp);
                intent.putExtra("feels_like", feels_like);
                intent.putExtra("humidity", humidity);
                intent.putExtra("weather", weatherMain);
                intent.putExtra("pm10", pm10);

                startActivity(intent);

        }
    }
}