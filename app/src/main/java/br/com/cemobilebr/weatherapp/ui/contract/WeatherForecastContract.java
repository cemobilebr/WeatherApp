package br.com.cemobilebr.weatherapp.ui.contract;

import android.content.Context;

import java.util.List;

import br.com.cemobilebr.weatherapp.core.aggregation.WeatherForecastAggregation;
import br.com.cemobilebr.weatherapp.model.Forecast;
import br.com.cemobilebr.weatherapp.model.Main;

/**
 * Created by cemobilebr.
 */
public interface WeatherForecastContract {

    public interface View {

        void setupWeatherForecast(WeatherForecastAggregation aggregation);

        void showLoadingLayout();

        void showErrorLayout();

        void showSuccessLayout();

        void showEmptyLayout();

    }

    public interface Presenter {

        WeatherForecastAggregation onSaveInstanceState();

        void onLoadInstanceState(WeatherForecastAggregation aggregation);

        void loadForecasts(String cityId);

        void refreshUi();

        void retryButtonClick(String cityId);

    }

}
