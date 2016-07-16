package br.com.cemobilebr.weatherapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.R;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import br.com.cemobilebr.weatherapp.model.Coordinates;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class responsible to display the list of cities specified by name.
 *
 * Created by cemobilebr.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private final Context context;

    private final List<CityWeatherCondition> cities;

    public CityListAdapter(Context context, List<CityWeatherCondition> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (cities.isEmpty()) {
            return;
        }

        CityWeatherCondition city = cities.get(position);
        if (city != null) {
            holder.apply(city);
        }

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cityCountryTextView)
        protected TextView cityCountryTextView;

        @BindView(R.id.coordinatesTextView)
        protected TextView coordinatesTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void apply(CityWeatherCondition city) {
            Coordinates coordinates = city.getCoordinates();

            cityCountryTextView.setText(city.getName() + Constants.SEPARATOR + city.getSystem().getCountryCode());
            coordinatesTextView.setText(coordinates.getLatitude() + Constants.SEPARATOR + coordinates.getLongitude());
        }
    }
}
