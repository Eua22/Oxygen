package com.example.oxygen.activity.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oxygen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends Fragment {

    private TextView cityText,temp,cords,description,humity,windSpeed,windDeg;
    private ImageView imgView;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        cityText = (TextView) rootView.findViewById(R.id.txtCity);
        cords = (TextView) rootView.findViewById(R.id.txtCord);
        temp = (TextView) rootView.findViewById(R.id.txttemp);
        description = (TextView) rootView.findViewById(R.id.txtDescription);
        humity = (TextView) rootView.findViewById(R.id.txtHum);
        windSpeed = (TextView) rootView.findViewById(R.id.txtWind);
        windDeg = (TextView) rootView.findViewById(R.id.txtDeg);

        imgView = (ImageView) rootView.findViewById(R.id.condIcon);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        return rootView;
    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                new OpenWeatherAsyncTask("lat="+latti+"&lon="+longi).execute();
            } else {

                new OpenWeatherAsyncTask("lat=41.3069&lon=24.169").execute();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }



    private class OpenWeatherAsyncTask extends AsyncTask<Void, Void, String> {


        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        String dummyAppid = "44524b1c34c099fcc2da1f4c66e2c854";
        String queryWeather = null;
        String queryDummyKey = "&appid=" + dummyAppid;

        public OpenWeatherAsyncTask(String s) {
            queryWeather = "https://api.openweathermap.org/data/2.5/weather?"+s+"&units=metric";
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            String queryReturn;

            String query = null;
            try {
                query = queryWeather + queryDummyKey;
                Log.d("!!",query);
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }



            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            pdLoading.dismiss();
        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        private String ParseJSON(String json){

            try {
                final JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");
                if(cod != null){
                    if(cod.equals("200")){

                        Log.d("Weather", JsonObject+"");


                        final JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                        String weatherDes = null, weatherIcon=null;
                        final JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        if(weather != null){
                            for(int i=0; i<weather.length(); i++){
                                JSONObject thisWeather = weather.getJSONObject(i);
                                weatherDes  = jsonHelperGetString(thisWeather, "description");
                                weatherIcon  = jsonHelperGetString(thisWeather, "icon");
                            }
                        }
                        final JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        final JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");

                        if(Double.parseDouble(jsonHelperGetString(wind, "speed")) > 10){ //10
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    final Dialog fbDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                                    fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                                    fbDialogue.setContentView(R.layout.warning_pop_up_wind);
                                    fbDialogue.setCancelable(true);
                                    fbDialogue.show();
                                }
                            });
                        }
                        if(Double.parseDouble(jsonHelperGetString(main, "temp")) > 27 && Double.parseDouble(jsonHelperGetString(main, "humidity")) > 60){  //27 60
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    final Dialog fbDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                                    fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                                    fbDialogue.setContentView(R.layout.warning_pop_up_humidity);
                                    fbDialogue.setCancelable(true);
                                    fbDialogue.show();
                                }
                            });
                        }
                        if(Double.parseDouble(jsonHelperGetString(main, "temp")) < 16){
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    final Dialog fbDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                                    fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                                    fbDialogue.setContentView(R.layout.warning_pop_up_cold);
                                    fbDialogue.setCancelable(true);
                                    fbDialogue.show();
                                }
                            });
                        }

                        final String finalWeatherDes = weatherDes;
                        final String finalWeatherIcon = weatherIcon;
//                        URL newurl = new URL("http://openweathermap.org/img/w/"+finalWeatherIcon+".png");
//                        final Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                cityText.setText("Location: "+jsonHelperGetString(JsonObject, "name"));
                                cords.setText("{lon: "+jsonHelperGetString(coord, "lon")+" lat: "+jsonHelperGetString(coord, "lat")+"}");
//                                imgView.setImageBitmap(mIcon_val);
                                temp.setText(jsonHelperGetString(main, "temp")+"C");
                                description.setText(finalWeatherDes);
                                humity.setText(jsonHelperGetString(main, "humidity")+"%");
                                windSpeed.setText(jsonHelperGetString(wind, "speed")+"m/s");
                                String value = degToCompass(Double.parseDouble(jsonHelperGetString(wind, "deg")));
                                windDeg.setText(value);

                            }
                        });

                        //...incompleted

                    }else if(cod.equals("404")){
                        String message = jsonHelperGetString(JsonObject, "message");
                    }
                }else{
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "OK";
        }

        private String jsonHelperGetString(JSONObject obj, String k){
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }

        private String degToCompass(double deeger) {
            String directions[] = {"Βόρειος", "Βορειοανατολικός", "Ανατολικός", "Νοτιοανατολικός", "Νότιος", "Νοτιοδυτικός", "\n" +
                    "Δυτικός", "Βορειοδυτικός"};
            return directions[ (int)Math.round((  ((double)deeger % 360) / 45)) % 8 ];
        }

    }
}