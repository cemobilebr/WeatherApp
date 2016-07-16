package br.com.cemobilebr.weatherapp.data.local;

import java.util.Set;

import br.com.cemobilebr.weatherapp.Constants;
import io.paperdb.Paper;

/**
 * Created by cemobilebr.
 */

public class WeatherDataBase {

    public static void saveCitiesIds(Set<String> citiesIds) {
        Paper.book().write(Constants.CITIES_IDS, citiesIds);
    }

    public static Set<String> getCitiesIds() {
        return Paper.book().read(Constants.CITIES_IDS);
    }

    public static void removeCityIdByPosition(int position) {
        Set<String> citiesIds = getCitiesIds();

        int i = 0;
        for (String cityId: citiesIds) {
            if (i == position) {
                citiesIds.remove(cityId);
                break;
            }
            i++;
        }

        saveCitiesIds(citiesIds);
    }

}
