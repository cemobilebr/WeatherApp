package br.com.cemobilebr.weatherapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.R;
import br.com.cemobilebr.weatherapp.model.CityWeatherCondition;
import br.com.cemobilebr.weatherapp.model.WeatherCondition;
import br.com.cemobilebr.weatherapp.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class to display current weather of cities.
 *
 * Created by cemobilebr.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private Context context;

    private List<CityWeatherCondition> cities;

    public WeatherListAdapter(Context context, List<CityWeatherCondition> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        CityWeatherCondition city = cities.get(position);
        if (city != null) {
            viewHolder.apply(city);
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void removeItem(int position) {
        cities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cities.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dateTimeTextView)
        TextView dateTimeTextView;

        @BindView(R.id.cityTextView)
        TextView cityTextView;

        @BindView(R.id.descriptionTextView)
        TextView descriptionTextView;

        @BindView(R.id.windTextView)
        TextView windTextView;

        @BindView(R.id.pressureTextView)
        TextView pressureTextView;

        @BindView(R.id.humidityTextView)
        TextView humidityTextView;

        @BindView(R.id.iconImageView)
        ImageView iconImageView;

        @BindView(R.id.temperatureTextView)
        TextView temperatureTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void apply(CityWeatherCondition city) {
            dateTimeTextView.setText(context.getString(R.string.last_update, Utils.convertUnixTimeUtcToDateString(city.getTimeDataCalculation(), Constants.DATE_FORMAT_WITH_TIME)));
            cityTextView.setText(city.getName());
            pressureTextView.setText(context.getString(R.string.pressure, String.valueOf(city.getMain().getPressure())));
            windTextView.setText(context.getString(R.string.wind, String.valueOf(city.getWind().getSpeed())));
            humidityTextView.setText(context.getString(R.string.humidity, String.valueOf(city.getMain().getHumidity())) + Constants.PERCENTAGE);
            temperatureTextView.setText(context.getString(R.string.temperature, String.valueOf(Math.round(city.getMain().getTemperature()))));

            WeatherCondition condition = city.getWeatherConditions().get(0);
            descriptionTextView.setText(condition.getDescription());
            Picasso.with(context)
                    .load(Utils.getDrawableResourceIdByName(context, Constants.ICON_PREFIX + condition.getIcon()))
                    .resize(100, 100)
                    .centerInside()
                    .into(iconImageView);
        }
    }

}
