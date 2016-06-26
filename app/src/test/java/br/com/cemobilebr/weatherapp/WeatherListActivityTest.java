package br.com.cemobilebr.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import br.com.cemobilebr.weatherapp.ui.activity.WeatherListActivity;

import static org.junit.Assert.*;

/**
 * Created by cemobilebr.
 */
public class WeatherListActivityTest extends ActivityInstrumentationTestCase2<WeatherListActivity> {

    public WeatherListActivityTest() {
        super(WeatherListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityInitialTouchMode(false);
    }


    @Test
    public void testSimpleConditions() {
        WeatherListActivity activity = this.getActivity();

        RecyclerView weatherRecyclerView = (RecyclerView) activity.findViewById(R.id.weatherRecyclerView);
        assertNotNull(weatherRecyclerView);

        assertTrue(weatherRecyclerView.getChildCount() > 0);
    }
}