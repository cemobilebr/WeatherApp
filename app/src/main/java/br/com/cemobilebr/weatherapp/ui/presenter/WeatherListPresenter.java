package br.com.cemobilebr.weatherapp.ui.presenter;

import java.util.Set;

import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.core.aggregation.WeatherAggregation;
import br.com.cemobilebr.weatherapp.data.repository.WeatherRepository;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import br.com.cemobilebr.weatherapp.ui.contract.WeatherListContract;
import rx.Subscriber;

/**
 * Created by cemobilebr.
 */
public class WeatherListPresenter implements WeatherListContract.Presenter {

    private static final String TAG = "WeatherListPresenter";

    private WeatherListContract.View view;

    private WeatherRepository repository;

    private WeatherAggregation aggregation;

    public WeatherListPresenter(WeatherListContract.View view, WeatherRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public WeatherAggregation onSaveInstanceState() {
        return aggregation;
    }

    @Override
    public void onLoadInstanceState(WeatherAggregation aggregation) {
        this.aggregation = aggregation;
    }

    @Override
    public void loadCities() {
        Set<String> citiesIds = repository.getCitiesIds();
        if (citiesIds != null) {
            view.showLoadingLayout();
            StringBuilder parameterCitiesIds = new StringBuilder();
            for (String cityId : citiesIds) {
                parameterCitiesIds.append(cityId + Constants.COMMA_SEPARATOR);
            }

            if (parameterCitiesIds.length() > 0) {
                parameterCitiesIds.deleteCharAt(parameterCitiesIds.length() - 1);
            }

            loadCitiesFromRepository(parameterCitiesIds.toString());
        }
    }

    private void loadCitiesFromRepository(String citiesIds) {
        repository.synchronizeCityWeathers(citiesIds).subscribe(new Subscriber<WeatherAggregation>() {
            @Override
            public void onCompleted() {
                view.checkSwipeRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showErrorLayout();
            }

            @Override
            public void onNext(WeatherAggregation aggregation) {
                WeatherListPresenter.this.aggregation = aggregation;
                refreshUi();
            }
        });
    }

    @Override
    public void refreshUi() {
        if (aggregation != null && aggregation.getCitiesWeatherCondition().isEmpty()) {
            view.showEmptyLayout();
        } else {
            view.showSuccessLayout();
            view.setupWeatherList(aggregation.getCitiesWeatherCondition());
        }
    }

    @Override
    public void retryButtonClick() {
        loadCities();
    }

    @Override
    public void weatherItemClick(int position) {
        CityWeatherCondition city = aggregation.getCitiesWeatherCondition().get(position);
        view.showWeatherForecast(city);
    }

    @Override
    public void removeCity(int position) {
        repository.removeCityIdByPosition(position);
        loadCities();
    }
}
