package com.siic.alex.darkskyclient.services;

import com.siic.alex.darkskyclient.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alex on 22/2/2018.
 */

public interface WeatherService {

    @GET("{lat},{lng}")
    Call<Weather> getWeather(@Path("lat") double lat,@Path("lng") double lng);
}
