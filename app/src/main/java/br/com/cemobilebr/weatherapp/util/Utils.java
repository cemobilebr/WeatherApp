package br.com.cemobilebr.weatherapp.util;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.cemobilebr.weatherapp.Constants;

/**
 * Utility class responsible to several operations.
 *
 * Created by cemobilebr.
 */
public class Utils {

    // https://github.com/martykan/forecastie/blob/master/app/src/main/java/cz/martykan/forecastie/WeatherRecyclerAdapter.java
    // https://github.com/marcellogalhardo/Android-Code-Challenge/blob/master/app/src/main/java/br/com/mgalhardo/guidebook/ui/guidelist/GuideListPresenter.java
    // https://github.com/TomasKostadinov/AndroidWeatherApp
    // https://github.com/TylerMcCraw/android-weather

    /**
     * Returns a drawable resource id related to name informed.
     *
     * @param context - The context
     * @param name - drawable resource name
     * @return drawable resource id
     */
    public static int getDrawableResourceIdByName(Context context, String name) {
        return context.getResources().getIdentifier(name, Constants.DRAWABLE_RESOURCE, context.getPackageName());
    }

    /**
     * Converts the time of data calculation in unix (UTC) to date in string format dd/MM/yyyy hh:mm:ss.
     *
     * @param unixTimeUtc - time of data calculation in unix
     * @return date in String format dd/MM/yyyy hh:mm:ss
     */
    public static String convertUnixTimeUtcToDateString(long unixTimeUtc, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTimeUtc * Constants.SECONDS_TO_MILISECONDS); // multiply by 1000 to convert in miliseconds
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat(dateFormat); // date format: dd/MM/yyyy hh:mm:ss
        return simpleDataFormat.format(calendar.getTime());
    }

}
