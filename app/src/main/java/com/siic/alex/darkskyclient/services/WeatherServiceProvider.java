package com.siic.alex.darkskyclient.services;

import android.util.Log;

import com.siic.alex.darkskyclient.MainActivity;
import com.siic.alex.darkskyclient.events.ErrorEvent;
import com.siic.alex.darkskyclient.events.WeatherEvent;
import com.siic.alex.darkskyclient.models.Currently;
import com.siic.alex.darkskyclient.models.Weather;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 22/2/2018.
 */

public class WeatherServiceProvider {
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private static final String BASE_URL = "https://api.darksky.net/forecast/61457cdf6691b6139db57957cf26c63a/";
    private Retrofit retrofit;

    private Retrofit getRetrofit(){
        if (this.retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getWeather(double lat, double lng){
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<Weather> weatherData = weatherService.getWeather(lat, lng);
        weatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                if (weather != null) {
                    Currently currently = weather.getCurrently();
                    Log.e(TAG, "Temperature = " + currently.getTemperature());
                    EventBus.getDefault().post(new WeatherEvent(weather));
                } else {

                    Log.e(TAG,"onFailure: Check secret key");
                    EventBus.getDefault().post(new ErrorEvent("No weather available"));

                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG,"onFailure: Unable to get weather data");
                EventBus.getDefault().post(new ErrorEvent("Unable to connect weather server"));

            }
        });
    }
}
