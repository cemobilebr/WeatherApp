package br.com.cemobilebr.weatherapp.data.repository;

import java.util.Set;

import br.com.cemobilebr.weatherapp.core.aggregation.WeatherAggregation;
import br.com.cemobilebr.weatherapp.core.aggregation.WeatherForecastAggregation;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import rx.Observable;

/**
 * Created by cemobilebr.
 */

public interface WeatherRepository {

    void addCityId(String cityId);

    Set<String> getCitiesIds();

    void removeCityIdByPosition(int position);

    Observable<CityWeatherCondition> getCurrentWeatherByCityName(String cityName);

    Observable<WeatherAggregation> getCitiesWeather();

    Observable<WeatherAggregation> synchronizeCityWeathers(String citiesId);

    Observable<WeatherForecastAggregation> getWeatherForecast();

    Observable<WeatherForecastAggregation> synchronizeWeatherForecast(String cityId);

}
