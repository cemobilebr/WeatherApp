package br.com.cemobilebr.weatherapp.core.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.cemobilebr.weatherapp.model.City;
import br.com.cemobilebr.weatherapp.model.Forecast;

/**
 * Class that contains weather forecast for 5 days with data every 3 hours.
 *
 * Created by cemobilebr.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastAggregation implements Serializable {

    @JsonProperty("city")
    private City city; // Forecast's city information

    @JsonProperty("cod")
    private String code; // internal parameter

    @JsonProperty("message")
    private float message; // internal parameter

    @JsonProperty("cnt")
    private int count; // Number of elements returned by this API call

    @JsonProperty("list")
    private List<Forecast> forecasts; // list of weather forecast for 5 days with data every 3 hours

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

}
