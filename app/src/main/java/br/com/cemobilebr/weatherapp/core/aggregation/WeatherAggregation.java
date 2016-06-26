package br.com.cemobilebr.weatherapp.core.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;

/**
 * Created by celso on 17/06/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherAggregation implements Serializable {

    @JsonProperty("cnt")
    private int count;

    @JsonProperty("list")
    private List<CityWeatherCondition> citiesWeatherCondition;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CityWeatherCondition> getCitiesWeatherCondition() {
        return citiesWeatherCondition;
    }

    public void setCitiesWeatherCondition(List<CityWeatherCondition> citiesWeatherCondition) {
        this.citiesWeatherCondition = citiesWeatherCondition;
    }
}
