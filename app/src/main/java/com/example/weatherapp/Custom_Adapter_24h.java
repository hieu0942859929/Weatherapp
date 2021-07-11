package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Custom_Adapter_24h extends ArrayAdapter<Weather24h> {

    private final Context context;
    private final ArrayList<Weather24h> arrayList;

    public Custom_Adapter_24h(Context context, int resource, List<Weather24h> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = new ArrayList<>(objects);
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_weather_per_day, null);
        }
        Weather24h Weather24h = this.arrayList.get(position);
        TextView perDay = convertView.findViewById(R.id.perDay);
        TextView perDay1 = convertView.findViewById(R.id.perDay1);
        TextView minTemp = convertView.findViewById(R.id.minTemp);
        TextView maxTemp = convertView.findViewById(R.id.maxTemp);
        TextView statusPerDay = convertView.findViewById(R.id.statusPerDay);
        ImageView imagePerDay = convertView.findViewById(R.id.imagePerDay);

        perDay.setText(Weather24h.getPerDay());
        perDay1.setText(Weather24h.getPerDay1());
        minTemp.setText(Weather24h.getMinTemp() + "°C");
        maxTemp.setText(Weather24h.getMaxTemp() + "°C");
        statusPerDay.setText(Weather24h.getStatusPerDay());
        Picasso.get().load("http://openweathermap.org/img/wn/" + Weather24h.imagePerDay + "@2x.png").into(imagePerDay);

        return convertView;
    }
}
