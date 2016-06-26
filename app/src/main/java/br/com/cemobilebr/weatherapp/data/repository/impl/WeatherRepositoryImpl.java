package br.com.cemobilebr.weatherapp.data.repository.impl;

import java.util.HashSet;
import java.util.Set;

import br.com.cemobilebr.weatherapp.BuildConfig;
import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.core.aggregation.WeatherAggregation;
import br.com.cemobilebr.weatherapp.core.aggregation.WeatherForecastAggregation;
import br.com.cemobilebr.weatherapp.data.local.WeatherCache;
import br.com.cemobilebr.weatherapp.data.local.WeatherDataBase;
import br.com.cemobilebr.weatherapp.data.remote.WeatherService;
import br.com.cemobilebr.weatherapp.data.repository.WeatherRepository;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Class responsible to retrieve data from a local or remote repository.
 *
 * Created by cemobilebr.
 */
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String TAG = "WeatherRepositoryImpl";

    private WeatherService service;
    private WeatherCache cache;
    private Set<String> citiesIds;

    public WeatherRepositoryImpl(WeatherService service, WeatherCache cache) {
        this.service = service;
        this.cache = cache;

        citiesIds = WeatherDataBase.getCitiesIds();
        if (citiesIds == null) {
            citiesIds = new HashSet<>();
        }
    }

    @Override
    public void addCityId(String cityId) {
        citiesIds.add(cityId);
        WeatherDataBase.saveCitiesIds(citiesIds);
    }

    @Override
    public Set<String> getCitiesIds() {
        return WeatherDataBase.getCitiesIds();
    }

    @Override
    public void removeCityIdByPosition(int position) {
        WeatherDataBase.removeCityIdByPosition(position);
    }

    @Override
    public Observable<CityWeatherCondition> getCurrentWeatherByCityName(final String cityName) {
        return service.getCurrentWeatherByCityName(cityName, Constants.METRIC_VALUE, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<WeatherAggregation> getCitiesWeather() {
        return cache.get(Constants.WEATHER_KEY);
    }

    @Override
    public Observable<WeatherAggregation> synchronizeCityWeathers(String citiesId) {
        return service.getCurrentWeatherForSeveralCityIds(citiesId, Constants.METRIC_VALUE, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<WeatherAggregation, Observable<WeatherAggregation>>() {
                    @Override
                    public Observable<WeatherAggregation> call(WeatherAggregation aggregation) {
                        return cache.set(aggregation, Constants.WEATHER_KEY);
                    }
                });
    }

    public Observable<WeatherForecastAggregation> getWeatherForecast() {
        return cache.get(Constants.FORECAST_KEY);
    }

    @Override
    public Observable<WeatherForecastAggregation> synchronizeWeatherForecast(String cityId) {
        return service.getWeatherForecastByCityId(cityId, Constants.COUNT_VALUE+1, Constants.METRIC_VALUE, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<WeatherForecastAggregation, Observable<? extends WeatherForecastAggregation>>() {
                    @Override
                    public Observable<? extends WeatherForecastAggregation> call(WeatherForecastAggregation aggregation) {
                        return cache.set(aggregation, Constants.FORECAST_KEY);
                    }
                });
    }

}
