package br.com.cemobilebr.weatherapp.ui.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.cemobilebr.weatherapp.Constants;
import br.com.cemobilebr.weatherapp.R;
import br.com.cemobilebr.weatherapp.model.Forecast;
import br.com.cemobilebr.weatherapp.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class to show 5 day weather forecast.
 *
 * Created by cemobilebr.
 */
public class WeatherForecastAdapter extends BaseAdapter {

    private Context context;

    private List<Forecast> forecasts;

    public WeatherForecastAdapter(Context context, List<Forecast> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater localInflater = getLayoutInflater(context);
            // get layout from gridview_weather_forecast_item.xml
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_weather_forecast_item, parent, false);
            //convertView = localInflater.inflate(R.layout.gridview_weather_forecast_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Forecast forecast = forecasts.get(position);
        holder.apply(forecast);

        return convertView;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        Context wrapped = new ContextWrapper(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.cloneInContext(wrapped);
    }

    @Override
    public int getCount() {
        return forecasts.size();
    }

    @Override
    public Forecast getItem(int position) {
        return forecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {

        @BindView(R.id.dateTextView)
        protected TextView dateTextView;

        @BindView(R.id.forecastIconImageView)
        protected ImageView forecastIconImageView;

        @BindView(R.id.minimumTemperatureTextView)
        protected TextView minimumTemperatureTextView;

        @BindView(R.id.maximumTemperatureTextView)
        protected TextView maximumTemperatureTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void apply(Forecast forecast) {
            dateTextView.setText(Utils.convertUnixTimeUtcToDateString(forecast.getDate(), Constants.DATE_FORMAT_WITHOUT_TIME));

            String minimumTemperature = String.valueOf(Math.round(forecast.getTemperature().getMinimunDaily()));
            minimumTemperatureTextView.setText(
                    context.getString(
                            R.string.temperature_configured,
                            minimumTemperature));

            String maximumTemperature = String.valueOf(Math.round(forecast.getTemperature().getMaximunDaily()));
            maximumTemperatureTextView.setText(
                    context.getString(
                            R.string.temperature_configured,
                            maximumTemperature));

            Picasso.with(context)
                    .load(Utils.getDrawableResourceIdByName(context, Constants.ICON_PREFIX + forecast.getWeatherConditions().get(0).getIcon()))
                    .resize(40, 40)
                    .centerInside()
                    .into(forecastIconImageView);
        }
    }
}
