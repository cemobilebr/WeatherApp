package br.com.cemobilebr.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;

import org.junit.Test;

import br.com.cemobilebr.weatherapp.ui.activity.WeatherForecastActivity;
import br.com.cemobilebr.weatherapp.ui.activity.WeatherListActivity;
import br.com.cemobilebr.weatherapp.ui.adapter.WeatherForecastAdapter;

/**
 * Created by cemobilebr.
 */
public class WeatherForecastActivityTest extends ActivityInstrumentationTestCase2<WeatherForecastActivity> {

    public WeatherForecastActivityTest() {
        super(WeatherForecastActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityInitialTouchMode(false);
    }

    @Test
    public void testSimpleConditions() {
        WeatherForecastActivity activity = this.getActivity();

        GridView gridViewWeatherForecast = (GridView) activity.findViewById(R.id.weatherForecastGridView);
        assertNotNull(gridViewWeatherForecast);

        WeatherForecastAdapter adapter = (WeatherForecastAdapter) gridViewWeatherForecast.getAdapter();
        assertNotNull(adapter);

        assertTrue(adapter.getCount() > 0);

    }
}
