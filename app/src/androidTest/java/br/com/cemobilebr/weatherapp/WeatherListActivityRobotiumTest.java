package br.com.cemobilebr.weatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import junit.framework.Assert;

import br.com.cemobilebr.weatherapp.ui.activity.WeatherListActivity;

/**
 * Created by cemobilebr.
 */
public class WeatherListActivityRobotiumTest extends ActivityInstrumentationTestCase2<WeatherListActivity> {

    private Solo solo;

    public WeatherListActivityRobotiumTest() {
        super(WeatherListActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(this.getInstrumentation(), this.getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void test1WrongActivity() throws Exception {
        this.solo.assertCurrentActivity("Wrong Activity", WeatherListActivity.class);
    }

    public void test2GoToWeatherForecastActivity() throws Exception {
        Condition condition = new Condition() {
            @Override
            public boolean isSatisfied() {
                View loadingContainer = solo.getView(R.id.loadingContainer);
                return loadingContainer.getVisibility() == View.GONE;
            }
        };

        this.solo.waitForCondition(condition, 20000);

        this.solo.clickInRecyclerView(1);

        Assert.assertTrue(this.solo.waitForActivity(WeatherListActivity.class, 10000));
    }

    public void test3ExitByMenu() throws Exception {
        this.solo.sendKey(Solo.MENU);

        Assert.assertTrue(this.solo.waitForText(this.solo.getString(R.string.action_exit)));
    }
}
