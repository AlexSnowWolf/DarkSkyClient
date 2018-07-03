package com.siic.alex.darkskyclient.events;

import com.siic.alex.darkskyclient.models.Weather;

/**
 * Created by Alex on 22/2/2018.
 */

public class WeatherEvent {

    private final Weather weather;

    public WeatherEvent(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }

}
