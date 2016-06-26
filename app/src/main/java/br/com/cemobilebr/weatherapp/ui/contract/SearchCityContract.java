package br.com.cemobilebr.weatherapp.ui.contract;

import android.content.Intent;

import br.com.cemobilebr.weatherapp.model.City;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;

/**
 * Created by cemobilebr.
 */

public class SearchCityContract {

    public interface View {

        void setupCurrentWeather(CityWeatherCondition cityWeatherCondition);

        void handleIntent(Intent intent);

        void showLoadingLayout();

        void showErrorLayout();

        void showSuccessLayout();

        void showEmptyLayout();

        void goBackToWeatherList();

    }

    public interface Presenter {

        void handleIntent(Intent intent);

        void loadCity(String cityName);

        void refreshUi();

        void addCity(CityWeatherCondition cityWeatherCondition);

    }

}
