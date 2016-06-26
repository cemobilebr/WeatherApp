package br.com.cemobilebr.weatherapp;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import br.com.cemobilebr.weatherapp.ui.activity.WeatherForecastActivity;
import br.com.cemobilebr.weatherapp.ui.activity.WeatherListActivity;

/**
 * Created by cemobilebr.
 */
public class WeatherForecastActivityRobotiumTest extends ActivityInstrumentationTestCase2<WeatherForecastActivity> {

    private Solo solo;

    public WeatherForecastActivityRobotiumTest() {
        super(WeatherForecastActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(this.getInstrumentation(), this.getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void test1WrongActivity() throws Exception {
        this.solo.assertCurrentActivity("Wrong Activity", WeatherForecastActivity.class);
    }

    public void test2BackToWeatherListActivityByBackButtonClick() throws Exception {
        this.solo.goBack();
        this.solo.sendKey(Solo.MENU);
        Assert.assertTrue(this.solo.waitForText(this.solo.getString(R.string.action_exit)));
    }

}