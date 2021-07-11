package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Custom_Adapter_Weather extends RecyclerView.Adapter<Custom_Adapter_Weather.MyViewHolder> {
    Context context;
    List<Weather> arrayList;
    private final LayoutInflater mLayoutInflater;

    public Custom_Adapter_Weather(Context context, List<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dayOfWeek, temp;
        public ImageView imageStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayofweek);
            temp = itemView.findViewById(R.id.temp);
            imageStatus = itemView.findViewById(R.id.imageStatus);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_weather, parent, false);
        return new MyViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Custom_Adapter_Weather.MyViewHolder holder, int position) {
        Weather weather = arrayList.get(position);
        holder.dayOfWeek.setText(weather.DayOfWeek);
        holder.temp.setText(weather.Temp + "°C");
        Picasso.get().load("http://openweathermap.org/img/wn/" + weather.Image + "@2x.png").into(holder.imageStatus);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
