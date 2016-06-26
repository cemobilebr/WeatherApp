package br.com.cemobilebr.weatherapp;

import android.app.Application;

import io.paperdb.Paper;

/**
 * Created by cemobilebr.
 */

public class WeatherApplication extends Application {

    private static WeatherApplication instance;

    public static WeatherApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initialize();
    }

    private void initialize() {
        Paper.init(this);
    }
}
