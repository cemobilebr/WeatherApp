package br.com.cemobilebr.weatherapp.ui.contract;

import java.util.List;

import br.com.cemobilebr.weatherapp.core.aggregation.WeatherAggregation;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;

/**
 * Created by cemobilebr.
 */
public interface WeatherListContract {

    interface View {

        void setupWeatherList(List<CityWeatherCondition> cities);

        void showLoadingLayout();

        void showErrorLayout();

        void showSuccessLayout();

        void showEmptyLayout();

        void checkSwipeRefreshing();

        void showWeatherForecast(CityWeatherCondition city);

        void findCity();

    }

    interface Presenter {

        WeatherAggregation onSaveInstanceState();

        void onLoadInstanceState(WeatherAggregation aggregation);

        void loadCities();

        void refreshUi();

        void retryButtonClick();

        void weatherItemClick(int position);

        void removeCity(int position);

    }
}
