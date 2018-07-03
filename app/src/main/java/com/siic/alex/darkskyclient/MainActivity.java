package com.siic.alex.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siic.alex.darkskyclient.events.ErrorEvent;
import com.siic.alex.darkskyclient.events.WeatherEvent;
import com.siic.alex.darkskyclient.models.Currently;
import com.siic.alex.darkskyclient.models.Weather;
import com.siic.alex.darkskyclient.services.WeatherService;
import com.siic.alex.darkskyclient.services.WeatherServiceProvider;
import com.siic.alex.darkskyclient.util.WeatherIconUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tempTV)
    TextView tempTV;

    @BindView(R.id.iconImageView)
    ImageView iconImageView;

    @BindView(R.id.summaryTextView)
    TextView summaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestCurrentWeather(53.634501,-2.552784);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent weatherEvent){
        Currently currently = weatherEvent.getWeather().getCurrently();
        tempTV.setText(String.valueOf(Math.round(currently.getTemperature())) + "°");
        summaryTextView.setText(currently.getSummary());

        iconImageView.setImageResource(WeatherIconUtil.ICONS.get(currently.getIcon()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(this,errorEvent.getErrorMessage(),Toast.LENGTH_SHORT).show();
    }

    private void requestCurrentWeather(double lat, double lng) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather(lat, lng);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
