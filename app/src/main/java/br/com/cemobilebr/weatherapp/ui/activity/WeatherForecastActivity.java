package br.com.cemobilebr.weatherapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.R;
import br.com.cemobilebr.weatherapp.core.aggregation.WeatherForecastAggregation;
import br.com.cemobilebr.weatherapp.data.local.WeatherCache;
import br.com.cemobilebr.weatherapp.data.remote.WeatherService;
import br.com.cemobilebr.weatherapp.data.repository.WeatherRepository;
import br.com.cemobilebr.weatherapp.data.repository.impl.WeatherRepositoryImpl;
import br.com.cemobilebr.weatherapp.model.Forecast;
import br.com.cemobilebr.weatherapp.model.WeatherCondition;
import br.com.cemobilebr.weatherapp.ui.contract.WeatherForecastContract;
import br.com.cemobilebr.weatherapp.ui.adapter.WeatherForecastAdapter;
import br.com.cemobilebr.weatherapp.ui.presenter.WeatherForecastPresenter;
import br.com.cemobilebr.weatherapp.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cemobilebr.
 */
public class WeatherForecastActivity extends AppCompatActivity implements WeatherForecastContract.View {

    @BindView(R.id.dateTextView)
    protected TextView dateTextView;

    @BindView(R.id.cityTextView)
    protected TextView cityTextView;

    @BindView(R.id.iconImageView)
    protected ImageView iconImageView;

    @BindView(R.id.descriptionTextView)
    protected TextView descriptionTextView;

    @BindView(R.id.windTextView)
    protected TextView windTextView;

    @BindView(R.id.humidityTextView)
    protected TextView humidityTextView;

    @BindView(R.id.pressureTextView)
    protected TextView pressureTextView;

    @BindView(R.id.temperatureTextView)
    protected TextView temperatureTextView;

    @BindView(R.id.weatherForecastGridView)
    protected GridView weatherForecastGridView;

    @BindView(R.id.successContainer)
    protected LinearLayout successContainer;

    @BindView(R.id.errorContainer)
    protected LinearLayout errorContainer;

    @BindView(R.id.loadingContainer)
    protected LinearLayout loadingContainer;

    @BindView(R.id.emptyContainer)
    protected LinearLayout emptyContainer;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private long cityId;

    private WeatherForecastContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        this.cityId = getIntent().getLongExtra(Constants.CITY_ID_EXTRA, -1);

        ButterKnife.bind(this);
        dependecyInjection();
        initialize(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void dependecyInjection() {
        WeatherService service = WeatherService.Builder.build();
        WeatherCache cache = new WeatherCache();
        WeatherRepository repository = new WeatherRepositoryImpl(service, cache);
        presenter = new WeatherForecastPresenter(this, repository);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.FORECAST_KEY, presenter.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    private void initialize(Bundle bundle) {
        if (bundle != null && bundle.containsKey(Constants.FORECAST_KEY)) {
            WeatherForecastAggregation aggregation = (WeatherForecastAggregation) bundle.getSerializable(Constants.FORECAST_KEY);
            presenter.onLoadInstanceState(aggregation);
            presenter.refreshUi();
        } else {
            presenter.loadForecasts(String.valueOf(cityId));
        }

    }

    @Override
    public void setupWeatherForecast(WeatherForecastAggregation aggregation) {
        if (aggregation != null) {
            LinearLayoutManager manager = new LinearLayoutManager(this);

            int size = aggregation.getForecasts().size();
            List<Forecast> forecasts = aggregation.getForecasts().subList(1, size);
            WeatherForecastAdapter adapter = new WeatherForecastAdapter(this, forecasts);
            weatherForecastGridView.setAdapter(adapter);

            Forecast currentForecast = aggregation.getForecasts().get(0);
            WeatherCondition condition = currentForecast.getWeatherConditions().get(0);

            cityTextView.setText(aggregation.getCity().getName());
            //dateTextView.setText(getString(R.string.last_update, Utils.convertUnixTimeUtcToDateString(currentForecast.getDate(), Constants.DATE_FORMAT_WITH_TIME)));
            descriptionTextView.setText(condition.getDescription());

            windTextView.setText(getString(R.string.wind, String.valueOf(currentForecast.getSpeed())));
            humidityTextView.setText(getString(R.string.humidity, String.valueOf(currentForecast.getHumidity())) + Constants.PERCENTAGE);
            pressureTextView.setText(getString(R.string.pressure, String.valueOf(currentForecast.getPressure())));
            temperatureTextView.setText(
                    getString(R.string.min_max_temperatures,
                            String.valueOf(Math.round(currentForecast.getTemperature().getMinimunDaily())),
                            String.valueOf(Math.round(currentForecast.getTemperature().getMaximunDaily()))));

            Picasso.with(this)
                    .load(Utils.getDrawableResourceIdByName(this, Constants.ICON_PREFIX + condition.getIcon()))
                    .resize(200, 200)
                    .centerInside()
                    .into(iconImageView);
        }
    }

    @Override
    public void showLoadingLayout() {
        successContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.VISIBLE);
        emptyContainer.setVisibility(View.GONE);
    }

    @Override
    public void showErrorLayout() {
        successContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        loadingContainer.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessLayout() {
        successContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyLayout() {
        successContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.VISIBLE);
    }


}
