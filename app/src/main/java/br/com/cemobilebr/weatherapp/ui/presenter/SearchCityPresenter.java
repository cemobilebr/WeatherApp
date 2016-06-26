package br.com.cemobilebr.weatherapp.ui.presenter;

import android.content.Intent;
import android.util.Log;

import br.com.cemobilebr.weatherapp.data.repository.WeatherRepository;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import br.com.cemobilebr.weatherapp.ui.contract.SearchCityContract;
import rx.Subscriber;

/**
 * Created by cemobilebr.
 */
public class SearchCityPresenter implements SearchCityContract.Presenter {

    private static final String TAG = "SearchCityPresenter";

    private SearchCityContract.View view;

    private WeatherRepository repository;

    private CityWeatherCondition cityWeatherCondition;

    public SearchCityPresenter(SearchCityContract.View view, WeatherRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void handleIntent(Intent intent) {
        view.handleIntent(intent);
    }

    @Override
    public void loadCity(String cityName) {
        view.showLoadingLayout();
        repository.getCurrentWeatherByCityName(cityName).subscribe(new Subscriber<CityWeatherCondition>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "[onCompleted]");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CityWeatherCondition cityWeatherCondition) {
                SearchCityPresenter.this.cityWeatherCondition = cityWeatherCondition;
                refreshUi();
            }
        });
    }

    @Override
    public void refreshUi() {
        if (cityWeatherCondition == null) {
            view.showEmptyLayout();
        } else {
            view.showSuccessLayout();
            view.setupCurrentWeather(cityWeatherCondition);
        }
    }

    @Override
    public void addCity(CityWeatherCondition cityWeatherCondition) {
        repository.addCityId(String.valueOf(cityWeatherCondition.getId()));
        view.goBackToWeatherList();
    }
}
