package com.example.ikiler.transport2019.UI.fragement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.bean.Sense;

import java.util.ArrayList;
import java.util.List;

public class MyLogItemRecyclerViewAdapter extends RecyclerView.Adapter<MyLogItemRecyclerViewAdapter.ViewHolder> {

    private List<Sense> objects = new ArrayList<Sense>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_search_item, parent, false);
        return new ViewHolder(view);
    }

    public void setObjects(List<Sense> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sense sense= (Sense) objects.get(position);
        holder.tvCo2.setText(sense.getCo2()+"");
        holder.tvHum.setText(sense.getHumidity()+"");
        holder.tvLight.setText(sense.getLightIntensity()+"");
        holder.tvPm254.setText(sense.getPm25()+"");
        holder.tvTemp.setText(sense.getTemperature()+"");
        holder.tvTime.setText(sense.getTime());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCo2;
        private TextView tvCo2;
        private ImageView imgHum;
        private TextView tvHum;
        private ImageView imgPm25;
        private TextView tvPm254;
        private ImageView imgLight;
        private TextView tvLight;
        private ImageView imgTemp;
        private TextView tvTemp;
        private TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            imgCo2 = (ImageView) view.findViewById(R.id.img_co2);
            tvCo2 = (TextView) view.findViewById(R.id.tv_co2);
            imgHum = (ImageView) view.findViewById(R.id.img_hum);
            tvHum = (TextView) view.findViewById(R.id.tv_hum);
            imgPm25 = (ImageView) view.findViewById(R.id.img_pm25);
            tvPm254 = (TextView) view.findViewById(R.id.tv_pm254);
            imgLight = (ImageView) view.findViewById(R.id.img_light);
            tvLight = (TextView) view.findViewById(R.id.tv_light);
            imgTemp = (ImageView) view.findViewById(R.id.img_temp);
            tvTemp = (TextView) view.findViewById(R.id.tv_temp);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
